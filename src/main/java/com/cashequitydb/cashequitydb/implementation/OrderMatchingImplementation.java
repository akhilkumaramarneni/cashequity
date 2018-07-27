package com.cashequitydb.cashequitydb.implementation;

import com.cashequitydb.cashequitydb.model.NotExecutedOrderModel;
import com.cashequitydb.cashequitydb.model.OrderModel;
import com.cashequitydb.cashequitydb.repository.OrderExecuteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Repository
public class OrderMatchingImplementation implements OrderExecuteInterface {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String OrderMatching(List<OrderModel> orderlist) {

        int orderlistsize = orderlist.size();

        //for new order generate a new order_id.....................................
        //insert new in orderlist table.............................................

        //insert all in orders table and set id to them################# static later change
        //int currentorderscount=10;

        String firstsql = "select count(*) from orders";
        List<OrderModel> changelater = jdbcTemplate.query(firstsql,new BeanPropertyRowMapper<>(OrderModel.class));
        int currentorderscount = changelater.size();
        for(int i=0;i<orderlistsize;i++){
            orderlist.get(i).setId(String.valueOf(currentorderscount+(i+1)));
        }

        //current trade_date and trade_type are null change later for future use
        String insertsql="insert into orders values (?,?,?,?,?,?,?,?,?)";
        String defaulttrade_date = "19-Jul";
        String defaulttrade_type = "LIMIT";
        for(int insert=0;insert<orderlistsize;insert++){

            jdbcTemplate.update(insertsql,new Object[]{orderlist.get(insert).getClient_code(),
            orderlist.get(insert).getIsin(),orderlist.get(insert).getTrade_time(),orderlist.get(insert).getQuantity(),
                    orderlist.get(insert).getLimit_price(),orderlist.get(insert).getDirection(),
                    orderlist.get(insert).getId(),defaulttrade_type,defaulttrade_date});

        }

        //for orders already in orders table below code

        String sql="select * from not_executed_orders";
        List<OrderModel> checkfirstorder=jdbcTemplate.query(sql,new BeanPropertyRowMapper(OrderModel.class));
        //first order checking condition

        if(checkfirstorder.size()==0 && (orderlistsize ==1)){
            //insert directly into not executed table...............................
            sql="insert into not_executed_order values(?,?,?,?,?,?)";
            jdbcTemplate.update(sql,new Object[]{orderlist.get(0).getClient_code(),orderlist.get(0).getIsin(),
                    orderlist.get(0).getQuantity(),orderlist.get(0).getQuantity(),orderlist.get(0).getDirection(),
                    orderlist.get(0).getId(),orderlist.get(0).getLimit_price()});
            return "first order";
        }
        else{

            //function to sort(according to price and quantity) both sells and buys
            //for copying purpose bothlist created
            List<OrderModel> newselllsit = new ArrayList<>();
            List<OrderModel> newbuyylist = new ArrayList<>();
            //all json buy and sell list sort them
            for(int i=0;i<orderlistsize;i++){
                if(orderlist.get(i).getDirection().equals("B"))
                    newbuyylist.add(orderlist.get(i));
                else
                    newselllsit.add(orderlist.get(i));

            }

            //creating object for sorting
            SortImplementation sortobject = new SortImplementation();

            //sending objects for sorting and fetch

            //check for size is one later add condition
            List<OrderModel> newbuylist = sortobject.buysort(newbuyylist);
            List<OrderModel> newselllist = sortobject.sellsort(newselllsit);

            //if any buys are there insert them into not_executed table and check last
            //code for checking each sell order from newselllist
            int lengthofselllist = newselllist.size();
            //first insert the all current buy order in table not executed check them also
            //keep now later optimize the code in insertion..............................
            InsertingAllDirections insertingAllDirections = new InsertingAllDirections();
            insertingAllDirections.insertall(newbuylist,jdbcTemplate);


            //code for sells starts.......................
            //take one sell order from newwselllst and execute it
            CheckSellExecute checkSellExecute = new CheckSellExecute();
            checkSellExecute.execute(newselllist,jdbcTemplate);

//             System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//
//            for(int i=0;i<newselllist.size();i++){
//                System.out.println(newselllist.get(i).getClient_code()+"   : client_code");
//                System.out.println(newselllist.get(i).getIsin()+"   : isin");
//                System.out.println(newselllist.get(i).getLimit_price()+"   : limit_price");
//                System.out.println(newselllist.get(i).getQuantity()+"   : quantity");
//                //System.out.println(comparenotbutorders.get(i).getTrade_time()+"   : client_code");
//                System.out.println(newselllist.get(i).getDirection()+"   : direction");
//                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//            }



            //Sell excution is done remove the orders from newbuylist that are executed.......
            //sql check or code we can do

            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

            String sqlst="";
            for(int check=0;check<newbuylist.size();check++){
                sqlst = "select * from not_executed_orders where id=?";
                List<NotExecutedOrderModel> entirelist = jdbcTemplate.query(sqlst,
                        new Object[]{newbuylist.get(check).getId()},
                        new BeanPropertyRowMapper<>(NotExecutedOrderModel.class));

                if(entirelist.size()==0)
                {
                    newbuyylist.remove(check);
                    check=check-1;
                }


            }
            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

            //checek any buy list is there
            if(newbuylist.size()==0)
                return "no buys";

            System.out.print("sell complete..............");

            //##############################################
            //for buy incoming code starts

            //inserting remaining sells into not executed orders............................
            if(newselllist.size()!=0)
                insertingAllDirections.insertall(newselllist,jdbcTemplate);
            CheckBuyExecute checkBuyExecute = new CheckBuyExecute();
            checkBuyExecute.executeBuy(newbuylist,jdbcTemplate);

            //##############################################
            //buy execution complete

            // NOOOOOO NEED ALREDAY DOES finally remaining only (sell orders) not executed orders insert into tables


        }
        //or.get(0).getTime();
        return "ok change later";
    }
}
