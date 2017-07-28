package Dao;

import com.guet.zigbee.Data;

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
    private String sql="select heart,x,y,lowblood,heightblood from data";

    //Data的集合
    ArrayList<Data> DataList=new ArrayList<Data>();

public void TheSqlConnection()
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
            data.setHeart(rs.getString(1));
            data.setX(String.valueOf(rs.getFloat(2)));
            data.setY(String.valueOf(rs.getFloat(3)));
            data.setBloodlow(String.valueOf(rs.getInt(4)));
            data.setBloodhigh(String.valueOf(rs.getInt(5)));

            //添加到Data集合
            DataList.add(data);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
