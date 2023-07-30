package com.ims.service.impl;

import com.ims.dao.UsersDAO;
import com.ims.entity.Users;

import java.util.List;

public class UsersServiceImpl {
    private UsersDAO usersDAO;

    public UsersServiceImpl() {
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
