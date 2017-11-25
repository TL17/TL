package util.db;

import entity.service.Course;
import entity.service.Status;
import net.sf.json.JSONObject;

import java.sql.*;

/**
 * Data Base connector 第二代
 */
public class DBConnect {
    private final String name = "com.mysql.cj.jdbc.Driver";

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

    public static void main(String[] args) {
        DBConnect dbConnect = new DBConnect();
        String sql = "SELECT * FROM course WHERE name LIKE ? ;";
        PreparedStatement pstm = dbConnect.prepareStatement(sql);

        JSONObject jsonRet = null;
        Status status = new Status();
        try {
            pstm.setString(1, "%net%");
            ResultSet rs = pstm.executeQuery();
            rs.last();
            int rowCount = rs.getRow();
            Course[] courses = new Course[rowCount];;

            rs.first();
            int start= 0;

            do {

                String c_id = rs.getString(1);
                String c_name = rs.getString(2);
                String c_info = rs.getString(3);
                String c_plan = rs.getString(4);
                System.out.println("asdf");

                courses[start] = new Course();
                System.out.println(c_name);
                courses[start].setCourseID(c_id);
                courses[start].setCourseName(c_name);
                courses[start].setCourseInfo(c_info);
                courses[start].setCoursePlan(c_plan);
                start++;
            }while (rs.next());

            status.setStatus(true);
            status.setInfo("课程搜索成功");
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("courses", courses);

        } catch (SQLException e) {
            status.setStatus(false);
            status.setInfo("操作失败");
            e.printStackTrace();
        }

        System.out.print(jsonRet.toString());

    }


}
