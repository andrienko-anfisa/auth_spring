package ru.andrienko.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ru.andrienko.spring.dao.UsersDAO;
import ru.andrienko.spring.exceptions.DBException;
import ru.andrienko.spring.models.User;
import ru.andrienko.spring.service.DBService;

import java.util.List;

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

    @GetMapping("/allUsers")
    public String getAllUsers(Model model) throws DBException {
        model.addAttribute("users",dbService.getAllUsers());
        return "allUsers";
    }
}
