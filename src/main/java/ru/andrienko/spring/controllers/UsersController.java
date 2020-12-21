package ru.andrienko.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andrienko.spring.dao.UsersDAO;
import ru.andrienko.spring.exceptions.DBException;
import ru.andrienko.spring.models.User;
import ru.andrienko.spring.service.DBService;

import javax.validation.Valid;

@Controller

public class UsersController {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private DBService dbService;

    @Autowired
    public UsersController(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    //таблица всех пользователей
    @GetMapping("/allUsers")
    public String getAllUsers(Model model) throws DBException {
        model.addAttribute("users", dbService.getAllUsers());
        return "allUsers";
    }

    //создание нового пользователя
    @GetMapping("/newUser")
    public String newUser(@ModelAttribute("user") User user) {
        return "newUser";
    }

    @PostMapping("/newUser")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) throws DBException {
        if (bindingResult.hasErrors())
            return "newUser";
        dbService.addUser(user);
        return "redirect:/allUsers";
    }

//    удаление пользователя
    @GetMapping("/deleteUser/{id}")
    public String delete(@PathVariable("id") long id) {
        dbService.deleteUser(id);
        return "redirect:/allUsers";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) throws DBException {
        model.addAttribute("user", dbService.getUserById(id));
        return "showUser";
    }

}