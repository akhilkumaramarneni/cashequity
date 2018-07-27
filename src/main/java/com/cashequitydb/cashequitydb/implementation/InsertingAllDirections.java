package com.cashequitydb.cashequitydb.implementation;

import com.cashequitydb.cashequitydb.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InsertingAllDirections {


    public String insertall(List<OrderModel> insertdata,JdbcTemplate jdbcobject){
       String sql="insert into not_executed_orders values(?,?,?,?,?,?)";
       int len = insertdata.size();

       for(int i=0;i<len;i++) {
           jdbcobject.update(sql, new Object[]{(insertdata.get(i)).getClient_code(), (insertdata.get(i)).getIsin(), (insertdata.get(i)).getQuantity(), insertdata.get(i).getDirection(),
                   ( insertdata.get(i)).getId(), (insertdata.get(i)).getLimit_price()});

       }

       return "inserted";

    }
}
