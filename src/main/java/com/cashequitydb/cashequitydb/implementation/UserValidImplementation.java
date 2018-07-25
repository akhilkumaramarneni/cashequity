package com.cashequitydb.cashequitydb.implementation;

import com.cashequitydb.cashequitydb.model.UserInformation;
import com.cashequitydb.cashequitydb.repository.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserValidImplementation implements UserInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String ValidateLoginUser(UserInformation userinfo) {

        String sql="select * from client_master where client_code=? and password=?";
        List<UserInformation> uservalidatelist=null;
        try {
            uservalidatelist = jdbcTemplate.query(sql,
                    new Object[]{userinfo.getClient_code(),
                            userinfo.getPassword()}, new BeanPropertyRowMapper(UserInformation.class));
        }
        catch (Exception e){
            return "connection went wrong";
        }
        if(uservalidatelist.size()==1&& uservalidatelist!=null)
            return "success";
        else
            return "invalid user";
    }
}
