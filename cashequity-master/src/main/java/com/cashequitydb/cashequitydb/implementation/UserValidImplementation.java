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
    public List<UserInformation> SecurityFetch() {
        String sql="select COMPANY_NAME,SECTOR,SYMBOL,ISIN from security_master";
        List <UserInformation> list =jdbcTemplate.query(sql,new BeanPropertyRowMapper(UserInformation.class));
        return list;
    }
}
