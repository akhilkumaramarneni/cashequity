package com.cashequitydb.cashequitydb.implementation;

import com.cashequitydb.cashequitydb.model.NotExecutedOrderModel;
import com.cashequitydb.cashequitydb.model.OrderModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortImplementationNotExecuteOrder {

    public List<NotExecutedOrderModel> buysort(List<NotExecutedOrderModel> buyorders){

        //logic to sort with high buy cost and high quantity

        Collections.sort(buyorders, new Comparator<NotExecutedOrderModel>() {
            @Override
            public int compare(NotExecutedOrderModel o1, NotExecutedOrderModel o2) {
                if(o1.getLimit_price()<o2.getLimit_price()){
                    return 1;
                }
                else if(o1.getLimit_price()==o2.getLimit_price()){
                    if(o1.getQuantity()<o2.getQuantity()){
                        return 1;
                    }
                    else if(o1.getQuantity()>o2.getQuantity()) {
                        return -1;
                    }
                    else
                        return 0;
                }
                return -1;
            }
        });
        return buyorders;
    }



    public List<NotExecutedOrderModel> sellsort(List<NotExecutedOrderModel> sellorders){

        //code sell sort with low cost and more quantity

        Collections.sort(sellorders, new Comparator<NotExecutedOrderModel>() {
            @Override
            public int compare(NotExecutedOrderModel o1, NotExecutedOrderModel o2) {
                if(o1.getLimit_price()<o2.getLimit_price()){
                    return -1;
                }
                else if(o1.getLimit_price()==o2.getLimit_price()){
                    if(o1.getQuantity()<o2.getQuantity()){
                        return 1;
                    }
                    else if(o1.getQuantity()>o2.getQuantity()){
                        return -1;
                    }
                    return 0;
                }
                return 1;
            }
        });
        return sellorders;
    }

}
