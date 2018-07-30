package com.cashequitydb.cashequitydb.implementation;

import com.cashequitydb.cashequitydb.model.OrderInfo;
import com.cashequitydb.cashequitydb.model.SecurityInformation;
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

    //sudhanshu
    @Override
    public List<SecurityInformation> SecurityFetch() {
        String sql="select COMPANY_NAME,SECTOR,SYMBOL,ISIN from security_master";
        List <SecurityInformation> security_list =jdbcTemplate.query(sql,new BeanPropertyRowMapper(SecurityInformation.class));
        return security_list;
    }

    @Override
    public List<OrderInfo> FetchUnexecutedOrder(String clientCode) {
        String sql="select sm.company_name,neo.isin,direction,quantity,limit_price,order_id from not_executed_order neo " +
                "left join security_master sm on neo.isin = sm.isin  where client_code=?";

        List <OrderInfo> unexecuted_order =jdbcTemplate.query(sql,new Object[]{clientCode},new BeanPropertyRowMapper(OrderInfo.class));
        //System.out.println(unexecuted_order);
        return unexecuted_order;
    }

    @Override
    public List<OrderInfo> FetchAllOrder(String clientCode) {
        String sql="select sm.company_name,o.isin,direction,quantity,limit_price,order_id from `order` o " +
                "left join security_master sm on o.isin = sm.isin  where client_code=?";

        List <OrderInfo> executed_order =jdbcTemplate.query(sql,new Object[]{clientCode},new BeanPropertyRowMapper(OrderInfo.class));
        //System.out.println(executed_order);
        return executed_order;
    }
}
