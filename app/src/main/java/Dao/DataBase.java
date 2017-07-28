package Dao;

import android.util.Log;

import com.guet.zigbee.Data;
import com.guet.zigbee.MainActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Pony on 2017/7/28.
 */

public class DataBase {
    private static String URL="jdbc:mysql://119.23.45.116:3306/band?";
    private static String USER="band";
    private static String PASSWORD="band";

    //sql语句
    private static String sql="select heart,x,y,lowblood,heightblood from data";

    //Data的集合
    ArrayList<Data> DataList=new ArrayList<Data>();

public ArrayList<Data> TheSqlConnection()
{
    Connection conn;
    try {
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获得链接
        conn= DriverManager.getConnection(URL,USER,PASSWORD);
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next())
        {
            Data data=new Data();
            String s=rs.getString(1);
            data.setHeart(s);
            data.setX(String.valueOf(rs.getFloat(2)));
            data.setY(String.valueOf(rs.getFloat(3)));
            data.setBloodlow(rs.getInt(4));
            data.setBloodhigh(rs.getInt(5));

            //添加到Data集合
            DataList.add(data);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return DataList;
}
}
