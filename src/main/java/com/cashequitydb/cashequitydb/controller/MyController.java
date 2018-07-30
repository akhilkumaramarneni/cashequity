package com.cashequitydb.cashequitydb.controller;

import com.cashequitydb.cashequitydb.implementation.OrderMatchingImplementation;
import com.cashequitydb.cashequitydb.implementation.UserValidImplementation;
import com.cashequitydb.cashequitydb.model.OrderModel;
import com.cashequitydb.cashequitydb.model.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    OrderMatchingImplementation ordermatchingimplementation;
    @RequestMapping(value = "/matchorder")
    public String check_execution(@RequestBody List<OrderModel> order){
        return ordermatchingimplementation.OrderMatching(order);

        }


}
