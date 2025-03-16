package edu.ifpb.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static String url = "jdbc:mysql://localhost:3306/biblioteca";
    private static  String user = "root";
    private static String password = "";

    public static Connection getConnection() {
        Connection con;
        try{
            con = DriverManager.getConnection(url, user, password);
            return con;
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Erro ao conectar");
        }
        return null;

    }

    public static void closeConnection(Connection conn) {
        try{
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

}
