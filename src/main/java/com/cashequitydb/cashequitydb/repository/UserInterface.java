package com.cashequitydb.cashequitydb.repository;

import com.cashequitydb.cashequitydb.model.OrderInfo;
import com.cashequitydb.cashequitydb.model.UserInformation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public interface UserInterface {

    String ValidateLoginUser(UserInformation userinfo);
    public List<UserInformation> SecurityFetch();
    public List<OrderInfo> FetchUnexecutedOrder(String x);
    public List<OrderInfo> FetchAllOrder(String x);


}
