package db;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.sqlite.JDBC;

public class DataDB {

    private static DataDB instance ;
    private Connection connect;

    public static synchronized DataDB getInstance() throws SQLException {
        if (instance == null)
            instance = new DataDB();
        return instance;
    }

    public DataDB() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        String bdPath = System.getProperty("user.dir");
//        System.out.println(bdPath);
//        String bdPath1 = null;
//        try {
//            bdPath1 = DataDB.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        System.out.println(bdPath1);

//            this.connect = DriverManager.getConnection("jdbc:sqlite:jcalc_base.db");
            this.connect = DriverManager.getConnection("jdbc:sqlite:" + bdPath + "/base/jcalc_base.db");
    }

    public ArrayList DB_Read (String table, String who, boolean unique) throws SQLException {
        ArrayList<String> temp = new ArrayList<>();
        String sql_table = table;
        String sql_who = who;
        String sql_unique = "";
        if (unique) sql_unique = "DISTINCT ";
        Statement statmt = this.connect.createStatement();
        ResultSet resS = statmt.executeQuery("SELECT " + sql_unique + sql_who + " FROM " + sql_table + ";");
        while (resS.next()){
            temp.add(resS.getString(sql_who));
        }
        return temp;
    }

    public ArrayList DB_Read (String table, String who, String where, String where_who) throws SQLException {
        ArrayList<String> temp = new ArrayList<>();
        String sql_table = table;
        String sql_who = who;
        String sql_where = where;
        String sql_where_who = where_who;

        Statement statmt = this.connect.createStatement();
        ResultSet resS = statmt.executeQuery("SELECT " + sql_who + " FROM " + sql_table + " WHERE " + sql_where + " = '" + sql_where_who + "' ;");
        while (resS.next()){
            temp.add(resS.getString(sql_who));
        }
        return temp;
    }

    HashMap DB_Read (String table,HashMap<String,Object> question, String where, String where_who) {
        HashMap<String,Object> returnedHushMap = new HashMap();

            String[] keys = question.keySet().toArray(new String[0]);

//        System.out.println(keys.length);
            String sql_table = table;
            String sql_where = where;
            String sql_where_who = where_who;

            String sqlQ = "SELECT";
            for (int i =0; i<keys.length; i++){
                sqlQ = sqlQ + " " + keys[i];// + ", typeof(" + keys[i] + ")";
                if (i<keys.length-1){
                    sqlQ = sqlQ + ", ";
                } else sqlQ = sqlQ + " ";
            }
            sqlQ = sqlQ + "FROM " + sql_table ;
            if (!where.equals("")){
                sqlQ = sqlQ + " WHERE " + sql_where  + " = '" + sql_where_who + "'";
            }
            sqlQ = sqlQ + ";";
//        System.out.println(sqlQ);
        try {
            Statement statmt = this.connect.createStatement();
            ResultSet resS = statmt.executeQuery(sqlQ);
            for (String i: keys){
                String dd = resS.getString(i);
//                System.out.println(i + " * " + dd);
                if (i.contains("model") || i.contains("name")){
                    returnedHushMap.put(i, dd);
                }
                else{
                    returnedHushMap.put(i, Double.valueOf(dd));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return returnedHushMap;
    }

}
