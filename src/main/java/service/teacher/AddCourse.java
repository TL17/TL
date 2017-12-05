package service.teacher;

import entity.service.Status;
import net.sf.json.JSONObject;
import util.db.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name="AddCourse", urlPatterns="/course/add")
public class AddCourse extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        Status status = new Status();
        String url = "jdbc:mysql://123.207.6.234:3306/tl?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String dbPwd = "root";

        DBConnect dbConnect = new DBConnect(url, user, dbPwd);


        String userToken = request.getParameter("userToken");
        String courseName = request.getParameter("courseName");
        String courseInfo = request.getParameter("courseInfo");
        String coursePlan = request.getParameter("coursePlan");
        if (userToken == null) {
            userToken = "";
        }
        if (courseName == null) {
            courseName = "";
        }
        if (courseInfo == null) {
            courseInfo = "";
        }
        if (coursePlan == null) {
            coursePlan = "";
        }

        //here is the sql statement
        String querySting = "INSERT INTO course (name, info, plan, teacherid) VALUES (?,?,?,?) ";
        PreparedStatement preparedStatement = dbConnect.prepareStatement(querySting);
        JSONObject jsonRet;
        if (userToken.equals("")||courseName.equals("")||courseInfo.equals("")||coursePlan.equals("")) {
//            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("courseID", -1);
        } else {
            try {
                preparedStatement.setString(1,courseName);
                preparedStatement.setString(2,courseInfo);
                preparedStatement.setString(3,coursePlan);
                preparedStatement.setString(4,userToken);
                ResultSet rs = preparedStatement.executeQuery(querySting);
//            Status status = new Status();
                status.setStatus(true);
                status.setInfo("新建课程成功");
                jsonRet = JSONObject.fromObject(status);
                jsonRet.put("courseID", rs.getString("id"));
            } catch (SQLException e){
                status.setStatus(false);
                status.setInfo("空参数 "+e.getMessage());
                jsonRet = JSONObject.fromObject(status);
                jsonRet.put("courseID", -1);
                e.printStackTrace();
            }

        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

}
