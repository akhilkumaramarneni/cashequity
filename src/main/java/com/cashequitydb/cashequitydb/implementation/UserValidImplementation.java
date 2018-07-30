package com.cashequitydb.cashequitydb.implementation;

import com.cashequitydb.cashequitydb.model.*;
import com.cashequitydb.cashequitydb.repository.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Repository
public class UserValidImplementation implements UserInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String ValidateLoginUser(UserInformation userinfo) {

        String sql="select * from client_master where client_code=? and password=?";
        List<UserInformation> uservalidatelist=null;
        try {
            uservalidatelist = jdbcTemplate.query(sql,
                    new Object[]{userinfo.getClient_code(),
                            userinfo.getPassword()}, new BeanPropertyRowMapper(UserInformation.class));
        }
        catch (Exception e){
            return "connection went wrong";
        }
        if(uservalidatelist.size()==1&& uservalidatelist!=null)
            return "success";
        else
            return "invalid user";
    }

    //sudhanshu
    @Override
    public List<SecurityInformation> SecurityFetch() {
        String sql="select COMPANY_NAME,SECTOR,SYMBOL,ISIN from security_master";
        List<SecurityInformation> security_list =jdbcTemplate.query(sql,new BeanPropertyRowMapper(SecurityInformation.class));
        return security_list;
    }

    @Override
    public List<OrderInfo> FetchUnexecutedOrder(String clientCode) {
        String sql="select sm.company_name,neo.isin,direction,quantity,limit_price,order_id from not_executed_order neo " +
                "left join security_master sm on neo.isin = sm.isin  where client_code=?";

        List <OrderInfo> unexecuted_order =jdbcTemplate.query(sql,new Object[]{clientCode},new BeanPropertyRowMapper(OrderInfo.class));
        //System.out.println(unexecuted_order);
        return unexecuted_order;
    }

    @Override
    public List<OrderInfo> FetchAllOrder(String clientCode) {
        String sql="select sm.company_name,o.isin,direction,quantity,limit_price,order_id from `order` o " +
                "left join security_master sm on o.isin = sm.isin  where client_code=?";

        List <OrderInfo> executed_order =jdbcTemplate.query(sql,new Object[]{clientCode},new BeanPropertyRowMapper(OrderInfo.class));
        //System.out.println(executed_order);
        return executed_order;
    }

    //Deepak

    @Override
    public List<SecurityValue> getsecvalue(String isin)
    {
        String SQL="select * from security_master where isin=?";

        List<CompanySymbol> list=jdbcTemplate.query(SQL, new Object[]{isin}, new RowMapper< CompanySymbol >() {
            @Override
            public CompanySymbol mapRow(ResultSet rs, int rownumber) throws SQLException {
                CompanySymbol companySymbol = new CompanySymbol();
                companySymbol.setName(rs.getString("symbol"));
                return companySymbol;
            }

        });
        //    System.out.println("************************deeepak*******************************");
        //System.out.println(list.get(0).getName());
        SimpleDateFormat sdfDate=new SimpleDateFormat("HH:mm");
        String limit ="15:30";
        Date limitt=null;
        try {
            limitt=sdfDate.parse(limit);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        String start="9:15";
        Date startt=null;
        try {
            startt=sdfDate.parse(start);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        String user_time=getCurrentTimeStamp();
        Date now=null;
        try {
            now=sdfDate.parse(user_time);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
//
//   if(now.after(limitt)||now.before(startt))
//   {
//       System.out.println("market is closed");
//       return null;
//   }
        // System.out.println(user_time);
        // System.out.println(now);
        String name_db=list.get(0).getName();
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTime(now);
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
//        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
//
        String sql="select * from " +name_db+" where hours=?";
        //System.out.println(sql);
        //System.out.println(hour);
        List<secprice> list1=jdbcTemplate.query(sql, new Object[]{hour}, new RowMapper<secprice>() {
            @Override
            public secprice mapRow(ResultSet rs, int rowNum) throws SQLException {
                secprice sec=new secprice();
                sec.setSecurity_price(rs.getString("Last Traded Price"));
                sec.setHour(rs.getString("hours"));
                sec.setMin(rs.getString("minutes"));
                sec.setSymbol(rs.getString("symbol"));
                //sec.se
                return sec;

            }
        });
        //System.out.println(list1);
        String value="";
        for(secprice e :  list1)
        {
            String str1=e.getHour()+":"+e.getMin();
            //   System.out.println(str1);
            Date database_time=null;
            try {
                database_time=sdfDate.parse(str1);
            }
            catch (ParseException ex)
            {
                ex.printStackTrace();
            }
            // System.out.println(database_time);
            // System.out.println(now);

            if(database_time.after(now))
            {
                //value=e.getSecurity_price();
                //  System.out.println("deepa");
                // System.out.println(database_time);
                // System.out.println(now);
                // System.out.println(value);
                break;
            }
            value=e.getSecurity_price();

        }

        // System.out.println(value);
        SecurityValue secu=new SecurityValue();
        secu.setSec_value(value);
        //  System.out.println(secu);
        List<SecurityValue> list2=new ArrayList();
        list2.add(secu);
//     for (SecurityValue e :list2)
//     {
//        System.out.println("dededede");
//        System.out.println(e.getSec_value());
//     }
//     //System.out.println(secu);
        //System.out.println(list2);
        //if(list2!=null)
        return list2;
        //else
        //return null;
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate=new SimpleDateFormat("HH:mm");
        Date now=new Date();
        String strDate=sdfDate.format(now);
        return strDate;



    }

    @Override
    public List<List<UnExecOrder>> getunexecorder(String isin)
    {
        String sql="select * from not_executed where security_isin=? ";
        System.out.println(sql);
        System.out.println("*********************deepak1**************");

        List<UnExecOrder> list=jdbcTemplate.query(sql, new Object[]{isin}, new RowMapper<UnExecOrder>() {
            @Override
            public UnExecOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
                UnExecOrder unExecOrder=new UnExecOrder();
                unExecOrder.setPrice(rs.getDouble("price"));
                unExecOrder.setQuantity(rs.getInt("quantity"));
                unExecOrder.setClient_order(rs.getString("client_order"));
                unExecOrder.setDirection(rs.getString("direction"));
                unExecOrder.setIsin(rs.getString("security_isin"));

                return unExecOrder;
            }
        });

//        for (UnExecOrder e: list)
//        {
//            System.out.println(e);
//        }
//

        List<UnExecOrder> buylist=new ArrayList<>();
        List<UnExecOrder> selllist=new ArrayList<>();

        for(UnExecOrder e :list)
        {
            if(e.getDirection().equals("B"))
            {
                buylist.add(e);
            }
            else
            {
                selllist.add(e);
            }
        }

        SortImpl sortobject=new SortImpl();
        buylist=sortobject.buysort(buylist);
        selllist=sortobject.sellsort(selllist);

        List<List<UnExecOrder>> list1=new ArrayList<List<UnExecOrder>>();
        list1.add(buylist);
        list1.add(selllist);


        return list1;

    }

}
