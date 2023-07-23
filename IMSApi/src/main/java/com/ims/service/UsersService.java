package com.ims.service;

import com.ims.dao.UsersDAO;
import com.ims.model.Users;

import java.util.List;

public class UsersService {
    private UsersDAO usersDAO;

    public UsersService() {
        usersDAO = new UsersDAO();
    }

    public void addUser(Users user) {
        usersDAO.addUser(user);
    }

    public List<Users> getAllUsers() {
        return usersDAO.getAllUsers();
    }

    // Implement other methods as needed

    // ...
}
