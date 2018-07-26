package com.cashequitydb.cashequitydb.controller;

import com.cashequitydb.cashequitydb.implementation.UserValidImplementation;
import com.cashequitydb.cashequitydb.model.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    UserValidImplementation userValidImplementation;

    @RequestMapping(value="/getdata")
    public List<UserInformation> fetchsecuritydata(){

        List<UserInformation> security=userValidImplementation.SecurityFetch();

        return security;

    }

}
