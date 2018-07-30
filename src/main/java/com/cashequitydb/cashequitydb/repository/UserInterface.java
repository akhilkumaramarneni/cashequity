package com.cashequitydb.cashequitydb.repository;

import com.cashequitydb.cashequitydb.model.*;

import java.util.List;


public interface UserInterface {

    String ValidateLoginUser(UserInformation userinfo);

    //sudhanshu
    public List<SecurityInformation> SecurityFetch();
    public List<OrderInfo> FetchUnexecutedOrder(String x);
    public List<OrderInfo> FetchAllOrder(String x);

    //Deepak
    List<SecurityValue> getsecvalue(String isin);
    List<List <UnExecOrder>> getunexecorder(String isin);

}
