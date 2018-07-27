package com.cashequitydb.cashequitydb.implementation;

import com.cashequitydb.cashequitydb.model.NotExecutedOrderModel;
import com.cashequitydb.cashequitydb.model.OrderModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;
import java.util.List;

public class CheckSellExecute {

    public void execute(List<OrderModel> orders, JdbcTemplate objectjdbc){

        CheckSellExecute s =new CheckSellExecute();
        SortImplementationNotExecuteOrder objectbuysort =new SortImplementationNotExecuteOrder();


        String orderdirection="B";
        for(int y=0;y<orders.size();y++) {
            String sql = "select * from not_executed_orders where direction = ? and isin = ? and limit_price >=?";
            List<NotExecutedOrderModel> comparenotbuyorders = objectjdbc.query(sql,
                    new Object[]{orderdirection, orders.get(y).getIsin(),orders.get(y).getLimit_price()},
                    new BeanPropertyRowMapper<>(NotExecutedOrderModel.class));
            if(comparenotbuyorders.size()==0)
                continue;

            //we got list of all buy orders in sorting manner
            comparenotbuyorders = objectbuysort.buysort(comparenotbuyorders);

            //check till complete sell execution
           String statussellorder = s.subpart(comparenotbuyorders,orders.get(y),objectjdbc);
           if(statussellorder.equals("executed")) {
               //sell order here from list
               orders.remove(y);
               y = y - 1;
           }

        }

    }


    public String subpart(List<NotExecutedOrderModel> comparenot,OrderModel order,JdbcTemplate subobjectjdbc){
        int entirelength = comparenot.size();
        int checkbuylistlength =0;
        String statement="";

        for(checkbuylistlength=0;checkbuylistlength < entirelength;checkbuylistlength++){
            if(order.getQuantity() == comparenot.get(checkbuylistlength).getQuantity()){

                //executed.. both delete buyorder from not_execute table and ,
                statement = "delete from not_executed_orders where id=?";
                subobjectjdbc.update(statement,
                        new Object[]{comparenot.get(checkbuylistlength).getId()}, new int[]{Types.INTEGER});

                //System.out.println("first case..............");
                return "executed";
            }
            else if(order.getQuantity()<comparenot.get(checkbuylistlength).getQuantity()){
                //modify buy order in not_executed with quantity
                //buy.q =buy.q-sell.q;
                statement = "update not_executed_orders set quantity =? where id =?";
                subobjectjdbc.update(statement,
                        new Object[]{comparenot.get(checkbuylistlength).getQuantity()-order.getQuantity(),
                                comparenot.get(checkbuylistlength).getId()});
               // System.out.println("second case..............");
                return "executed";
            }
            else{
                //sell.q is more
                //buy order remove from not_execute table and remove from list(object) later
                //modify sell order in list
                //sell order update
                //System.out.println(order.getQuantity()+"%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                order.setQuantity(order.getQuantity()-comparenot.get(checkbuylistlength).getQuantity());
                //System.out.println(order.getQuantity()+"%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                statement = "delete from not_executed_orders where id=?";
                subobjectjdbc.update(statement,
                        new Object[]{comparenot.get(checkbuylistlength).getId()},new int[] {Types.INTEGER});

                //System.out.println("third case..............");

            }

        }
        if(checkbuylistlength == entirelength && order.getQuantity()!=0)
            return "pending";
        else if(order.getQuantity()==0)
            return "executed";
        else
            return "this return will not execute";
    }

}
