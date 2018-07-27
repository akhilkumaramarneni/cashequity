package com.cashequitydb.cashequitydb.repository;

import com.cashequitydb.cashequitydb.model.UserInformation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public interface UserInterface {

    String ValidateLoginUser(UserInformation userinfo);


}
