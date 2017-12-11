package util.db;

import entity.service.Course;
import entity.service.Status;
import net.sf.json.JSONObject;

import java.sql.*;

/**
 * <h1>DBConnect Class</h1>
 * This class has the information about the database.
 * All the sensitive info including the username and password are saved in this class.
 * This class helps to connect the database.
 *
 * @ version 2.0
 * @ since 2017/12/06
 *
 * */
public class DBConnect {
    private final String name = "com.mysql.jdbc.Driver";

    private String url = "jdbc:mysql://123.207.6.234:3306/tl?useSSL=false&serverTimezone=UTC";
    private String user = "root";
    private String password = "root";

    private Connection conn = null;
    private Statement stmt;
    private PreparedStatement pst;
    private ResultSet rs;

    public DBConnect() {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DBConnect(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            stmt = conn.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement prepareStatement(String sql) {
        try {
            pst = conn.prepareStatement(sql);
            return pst;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String sql) throws SQLException {

        stmt.executeUpdate(sql);

    }

    public void executeUpdate(PreparedStatement pst) {
        try {
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) {
        try {
            rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet executeQuery(PreparedStatement pst) {
        try {
            rs = pst.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
                rs = null;
            }
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
                stmt = null;
            }
            if (pst != null && !pst.isClosed()) {
                pst.close();
                pst = null;
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
