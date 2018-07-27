package com.cashequitydb.cashequitydb.implementation;

import com.cashequitydb.cashequitydb.model.OrderModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortImplementation {

    public List<OrderModel> buysort(List<OrderModel> listbuyorder){

        //logic to sort with high buy cost and high quantity

        Collections.sort(listbuyorder, new Comparator<OrderModel>() {
            @Override
            public int compare(OrderModel o1, OrderModel o2) {
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
        return listbuyorder;
    }



    public List<OrderModel> sellsort(List<OrderModel> listsellorder){

        //code sell sort with low cost and more quantity

        Collections.sort(listsellorder, new Comparator<OrderModel>() {
            @Override
            public int compare(OrderModel o1, OrderModel o2) {
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
        return listsellorder;
    }

}
