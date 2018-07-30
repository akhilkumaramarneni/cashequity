package com.cashequitydb.cashequitydb.controller;

import com.cashequitydb.cashequitydb.implementation.OrderMatchingImplementation;
import com.cashequitydb.cashequitydb.implementation.UserValidImplementation;
import com.cashequitydb.cashequitydb.model.OrderInfo;
import com.cashequitydb.cashequitydb.model.OrderModel;
import com.cashequitydb.cashequitydb.model.SecurityInformation;
import com.cashequitydb.cashequitydb.model.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(allowedHeaders =
        {"Orgin", "X-Requested-With", "Content-Type", "Accept", "Authorization"},
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE}
)

public class MyController {

    @Autowired
    //UserValidImplementation objectvalidation;
    UserValidImplementation userValidImplementation;
    
    @Autowired
    OrderMatchingImplementation ordermatchingimplementation;
    //sudhanshu
    Integer len, order_no;
    OrderInfo order, unexe_order;
    //....

    @RequestMapping(value = "/validateuser")
    public String Validate(@RequestBody UserInformation userinfo) {
        return userValidImplementation.ValidateLoginUser(userinfo);

    }

    @RequestMapping(value = "/matchorder")
    public String check_execution(@RequestBody List<OrderModel> order) {
        return ordermatchingimplementation.OrderMatching(order);

    }

    @RequestMapping(value = "/getsecurity")
    public List<SecurityInformation> fetchsecuritydata() {

        List<SecurityInformation> security = userValidImplementation.SecurityFetch();

        return security;

    }

    @RequestMapping(value = "/getmyorder/{clientCode}")
    public Map<String, List<OrderInfo>> fetchmyorder(@PathVariable String clientCode) {

        List<OrderInfo> all_order = userValidImplementation.FetchAllOrder(clientCode);
        List<OrderInfo> unexecuted_order = userValidImplementation.FetchUnexecutedOrder(clientCode);
        Map<String, List<OrderInfo>> myorder = new HashMap<String, List<OrderInfo>>();
        Map<Integer, OrderInfo> exe_order_with_orderid = new HashMap<Integer, OrderInfo>();

        len = all_order.size();

        //add the all order in map with order_id as key
        for (int i = 0; i < len; i++) {
            exe_order_with_orderid.put(all_order.get(i).getOrder_id(), all_order.get(i));
        }

        len = unexecuted_order.size();
        //to get the executed order
        for (int i = 0; i < len; i++) {

            unexe_order = unexecuted_order.get(i);
            order_no = unexecuted_order.get(i).getOrder_id();
            order = exe_order_with_orderid.get(order_no);

            //check if unexecuted order is the complete order by client and delete it
            if (unexe_order.getQuantity() == order.getQuantity()) {
                exe_order_with_orderid.remove(order_no);
            } else {
                order.setQuantity(order.getQuantity() - unexe_order.getQuantity());
                exe_order_with_orderid.remove(order_no);
                exe_order_with_orderid.put(order_no, order);
            }
        }

        //executed_order.clear();
        List<OrderInfo> executed_order = new ArrayList<>();

        //create the list of executed order from exe_order_with_orderid map
        for (Map.Entry<Integer, OrderInfo> entry : exe_order_with_orderid.entrySet()) {
            executed_order.add(entry.getValue());
        }

        myorder.put("executed_order", executed_order);
        myorder.put("unexecuted_order", unexecuted_order);

        return myorder;

    }


}
