package com.cashequitydb.cashequitydb.repository;

import com.cashequitydb.cashequitydb.model.OrderModel;

import java.util.List;

public interface OrderExecuteInterface {

    String OrderMatching(List<OrderModel> orderlist);
}
