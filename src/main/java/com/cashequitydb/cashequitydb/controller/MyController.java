package com.cashequitydb.cashequitydb.controller;

import com.cashequitydb.cashequitydb.implementation.UserValidImplementation;
import com.cashequitydb.cashequitydb.model.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(allowedHeaders =
        {"Orgin","X-Requested-With", "Content-Type", "Accept", "Authorization"},
        methods ={RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,RequestMethod.DELETE}
)

public class MyController {

    @Autowired
    UserValidImplementation objectvalidation;

    @RequestMapping(value = "/validateuser")
    public String Validate(@RequestBody UserInformation userinfo){
        return objectvalidation.ValidateLoginUser(userinfo);

    }
}
