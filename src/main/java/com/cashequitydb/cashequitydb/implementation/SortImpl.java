package com.cashequitydb.cashequitydb.implementation;

import com.cashequitydb.cashequitydb.model.UnExecOrder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Deepak
public class SortImpl {

    public List<UnExecOrder> buysort(List<UnExecOrder> listbuyorder){

        //logic to sort with high buy cost and high quantity

        Collections.sort(listbuyorder, new Comparator<UnExecOrder>() {
            @Override
            public int compare(UnExecOrder o1, UnExecOrder o2) {
                if(o1.getPrice()<o2.getPrice()){
                    return 1;
                }
                else if(o1.getPrice()==o2.getPrice()){
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



    public List<UnExecOrder> sellsort(List<UnExecOrder> listsellorder){

        //code sell sort with low cost and more quantity

        Collections.sort(listsellorder, new Comparator<UnExecOrder>() {
            @Override
            public int compare(UnExecOrder o1, UnExecOrder o2) {
                if(o1.getPrice()<o2.getPrice()){
                    return -1;
                }
                else if(o1.getPrice()==o2.getPrice()){
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
