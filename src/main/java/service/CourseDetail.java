package service;

import entity.service.Course;
import entity.service.Status;
import net.sf.json.JSONObject;
import util.db.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name="CourseDetail", urlPatterns="/course/detail/*")
public class CourseDetail extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        Status status = new Status();
        String url = "jdbc:mysql://123.207.6.234:3306/tl?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String dbPwd = "root";

        DBConnect dbConnect = new DBConnect(url, user, dbPwd);

        String URI = request.getRequestURI();
        int index = URI.lastIndexOf('/');
        String courseIDStr = URI.substring(index + 1);
        courseIDStr = URLDecoder.decode(courseIDStr,"utf-8");

        boolean nan = false;
        try {
            Integer.parseInt(courseIDStr);
        } catch (Exception e) {
            nan = true;
        }
        //here is the sql statement
        String querySting = "SELECT * FROM course where id=?";
        PreparedStatement preparedStatement = dbConnect.prepareStatement(querySting);
        JSONObject jsonRet;
        if (nan||courseIDStr.equals("")||courseIDStr.equals("detail")) {
//            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            Course course = new Course();
            course.setCourseID(-1);
            course.setCourseName("");
            course.setCourseInfo("");
            course.setCoursePlan("");
            jsonRet.put("course",course);
        } else {
            try {
                int courseID = Integer.parseInt(courseIDStr);
                preparedStatement.setInt(1, courseID);
                ResultSet rs = preparedStatement.executeQuery();
                //            Status status = new Status();
                status.setStatus(true);
                status.setInfo("课程搜索成功");
                jsonRet = JSONObject.fromObject(status);
                Course course = new Course();
                course.setCourseID(rs.getInt(1));
                course.setCourseName("课程名称" + rs.getString(2));
                course.setCourseInfo("课程介绍" + rs.getString(3));
                course.setCoursePlan("课程大纲" + rs.getString(4));
                jsonRet.put("course", course);
            } catch (SQLException e){
                status.setStatus(false);
                status.setInfo("空参数");
                jsonRet = JSONObject.fromObject(status);
                Course course = new Course();
                course.setCourseID(-1);
                course.setCourseName("");
                course.setCourseInfo(e.getMessage());
                course.setCoursePlan("");
                jsonRet.put("course",course);
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
