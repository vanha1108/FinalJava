package com.example.finalJava.Service;

import com.example.finalJava.dto.User;

public interface IUserService {

    void login(String email, String password);

    User getUserByEmail(String email);
}
