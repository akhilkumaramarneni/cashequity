package com.cashequitydb.cashequitydb.implementation;

import com.cashequitydb.cashequitydb.model.NotExecutedOrderModel;
import com.cashequitydb.cashequitydb.model.OrderModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;
import java.util.List;

public class CheckBuyExecute {

    public void executeBuy(List<OrderModel> remainbuy, JdbcTemplate passedjdbcobject){

        SortImplementationNotExecuteOrder objectsellsort =new SortImplementationNotExecuteOrder();
        String orderdirection="S";
        CheckBuyExecute s_take =new CheckBuyExecute();

        for(int y=0;y<remainbuy.size();y++) {

            String sql = "select * from not_executed_orders where direction = ? and isin = ? and limit_price <=?";
            List<NotExecutedOrderModel> comparenotsellorders = passedjdbcobject.query(sql,
                    new Object[]{orderdirection, remainbuy.get(y).getIsin(),remainbuy.get(y).getLimit_price()},
                    new BeanPropertyRowMapper<>(NotExecutedOrderModel.class));

            if(comparenotsellorders.size()==0)
                continue;

            //we got list of all sell orders in sorting manner
            comparenotsellorders = objectsellsort.sellsort(comparenotsellorders);

            //check till complete buy execution
            String statusbuyorder = s_take.subsellpart(comparenotsellorders,remainbuy.get(y), passedjdbcobject);
            if(statusbuyorder.equals("executed")) {
                //sell order here from list
                remainbuy.remove(y);
                y = y - 1;
            }

        }

    }


    public String subsellpart(List<NotExecutedOrderModel> comparenotsells,OrderModel orderbuy,JdbcTemplate subobjectjdbcbuy){

        int selllength = comparenotsells.size();
        int checkselllistlength =0;
        String statement="";

        for(checkselllistlength=0;checkselllistlength < selllength;checkselllistlength++){
            if(orderbuy.getQuantity() == comparenotsells.get(checkselllistlength).getQuantity()){

                //executed buy.. both delete sellorder from not_execute table and return executed,

                //removing only sell
                statement = "delete from not_executed_orders where id=?";
                subobjectjdbcbuy.update(statement,
                        new Object[]{comparenotsells.get(checkselllistlength).getId()}, new int[]{Types.INTEGER});

                //statement = "delete from not_executed_orders where id=?";
                subobjectjdbcbuy.update(statement,
                        new Object[]{orderbuy.getId()}, new int[]{Types.INTEGER});

                //remove buy also from note execute table

                //System.out.println("first case..............");
                return "executed";

            }
            else if(orderbuy.getQuantity()<comparenotsells.get(checkselllistlength).getQuantity()){
                //modify sell order in not_executed with quantity
                //sell.q =sell.q-buy.q;
                statement = "update not_executed_orders set quantity =? where id =?";
                subobjectjdbcbuy.update(statement,
                        new Object[]{comparenotsells.get(checkselllistlength).getQuantity()-orderbuy.getQuantity(),
                                comparenotsells.get(checkselllistlength).getId()});

                //buy order remove from not execute table

                statement = "delete from not_executed_orders where id=?";
                subobjectjdbcbuy.update(statement,
                        new Object[]{orderbuy.getId()}, new int[]{Types.INTEGER});

                // System.out.println("second case..............");
                return "executed";
            }
            else {
                //buy.q is more
                //buy order remove from not_execute table and remove from list(object) later
                //modify sell order in list
                //sell order update
                //System.out.println(order.getQuantity()+"%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                int changevalue = orderbuy.getQuantity()-comparenotsells.get(checkselllistlength).getQuantity();
                orderbuy.setQuantity(changevalue);
                //System.out.println(order.getQuantity()+"%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                statement = "delete from not_executed_orders where id=?";
                subobjectjdbcbuy.update(statement,
                        new Object[]{comparenotsells.get(checkselllistlength).getId()},new int[] {Types.INTEGER});

                //update there for buy order

                statement = "update not_executed_orders set quantity =? where id =?";
                subobjectjdbcbuy.update(statement, new Object[]{changevalue, orderbuy.getId()});
                //System.out.println("third case..............");
            }

        }

        if(checkselllistlength == selllength && orderbuy.getQuantity()!=0)
            return "pending";
        else if(orderbuy.getQuantity()==0)
            return "executed";
        else
            return "this return will not execute";
    }
}
