package edu.ifpb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ifpb.utils.DatabaseUtil;

public abstract class GenericDAO<T> {

    protected static Connection getConnection() {
        return DatabaseUtil.getConnection();
    }

    public static void query (String query, Object... parametros) {
        try{
            PreparedStatement ps =
                    getConnection().prepareStatement(query);
            for(int i = 0; i < parametros.length; i++){
                ps.setObject(i+1, parametros[i]);
            }
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static ResultSet select(String query, Object... parametros) {
        ResultSet rs = null;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }

            rs = ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public static void update(String query, Object... parametros) {
        query(query, parametros);
    }

    public static void delete(String query, Object... parametros) {
        query(query, parametros);
    }
}
