package service;

import entity.service.Course;
import entity.service.Status;
import net.sf.json.JSONObject;
import util.db.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name="ManageCourse", urlPatterns="/course/manage")
public class ManageCourse extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        doPost(request,response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");


        Status status = new Status();
        String url = "jdbc:mysql://123.207.6.234:3306/tl?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String dbPwd = "root";

        DBConnect dbConnect = new DBConnect(url, user, dbPwd);

        String userToken = request.getParameter("userToken");
        if (userToken == null) {
            userToken = "";
        }

        //here is the sql statement
        String querySting = "select * from course where account=?";
        PreparedStatement preparedStatement = dbConnect.prepareStatement(querySting);

        JSONObject jsonRet;
        if (userToken.equals("")) {
//            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            Course[] courses = new Course[0];
            jsonRet.put("courses",courses);
        } else {
//            Status status = new Status();
            try {
                preparedStatement.setString(1,userToken);
                ResultSet rs = preparedStatement.executeQuery(querySting);
                Course[] courses = new Course[rs.getFetchSize()];
                status.setStatus(true);
                status.setInfo("获取信息成功");
                jsonRet = JSONObject.fromObject(status);

                for (int i = 0; i < courses.length; i++) {
                    courses[i] = new Course();
                    courses[i].setCourseID(i + 1);
                    courses[i].setCourseName("课程名称" + rs.getString("name"));
                    courses[i].setCourseInfo("课程介绍" + rs.getString("info"));
                    courses[i].setCoursePlan("课程大纲" + rs.getString("plan"));
                }
                jsonRet.put("courses", courses);
            } catch (SQLException e){
                status.setStatus(false);
                status.setInfo("空参数 "+ e.getMessage());
                jsonRet = JSONObject.fromObject(status);
                Course[] courses = new Course[0];
                jsonRet.put("courses",courses);
                e.printStackTrace();
            }

        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doGet(request,response);
    }

}
