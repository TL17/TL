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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="ManageCourse", urlPatterns="/course/manage")
public class ManageCourse extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String userToken = request.getParameter("userToken");
        String account = request.getParameter("account");
        if (userToken == null) {
            userToken = "";
        }
        if (account == null) {
            account = "";
        }

        Status status = new Status();
        JSONObject jsonRet;
        if (userToken.equals("")||account.equals("")) {
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            Course[] courses = new Course[0];
            jsonRet.put("courses",courses);
            PrintWriter out = response.getWriter();
            out.print(jsonRet.toString());
            return;
        }

        boolean isTeacher = false;
        DBConnect dbConnect = new DBConnect();

        String querySting;
        PreparedStatement preparedStatement;

        try {
            querySting = "SELECT * FROM course WHERE teacherid = ?";
            preparedStatement = dbConnect.prepareStatement(querySting);
            preparedStatement.setString(1,account);
            ResultSet rs = preparedStatement.executeQuery();
            List<Course> courses = new ArrayList<Course>();
            status.setStatus(true);
            status.setInfo("获取信息成功");
            jsonRet = JSONObject.fromObject(status);
            while (rs.next()){
                Course course = new Course();
                course.setCourseID(rs.getInt("id"));
                course.setCourseName(rs.getString("name"));
                course.setCourseInfo(rs.getString("info"));
                course.setCoursePlan(rs.getString("plan"));
                courses.add(course);
            }
            jsonRet.put("courses", courses);
            isTeacher = courses.size() > 0;
        } catch (SQLException e){
            status.setStatus(false);
            status.setInfo("数据库查询错误 "+ e.getMessage());
            jsonRet = JSONObject.fromObject(status);
            Course[] courses = new Course[0];
            jsonRet.put("courses",courses);
//                e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.print(jsonRet.toString());
            return;
        }

        if (isTeacher) {
            PrintWriter out = response.getWriter();
            out.print(jsonRet.toString());
            return;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //if not a teacher or teacher have no courses, do following:
        try {
            querySting = "SELECT c.id AS id, c.name AS name, c.info AS info, c.plan AS plan" +
                    " FROM course AS c, selection AS s" +
                    " WHERE s.studentid = ? AND s.courseid = c.id";
            preparedStatement = dbConnect.prepareStatement(querySting);
            preparedStatement.setString(1, account);
            ResultSet rs = preparedStatement.executeQuery();
            List<Course> courses = new ArrayList<Course>();

            while (rs.next()){
                Course course = new Course();
                course.setCourseID(rs.getInt("id"));
                course.setCourseName(rs.getString("name"));
                course.setCourseInfo(rs.getString("info"));
                course.setCoursePlan(rs.getString("plan"));
                courses.add(course);
            }

            status.setStatus(true);
            status.setInfo("获取信息成功");

            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("courses", courses);
        } catch (SQLException e) {
            status.setStatus(false);
            status.setInfo("数据库查询错误 "+ e.getMessage());
            jsonRet = JSONObject.fromObject(status);
            Course[] courses = new Course[0];
            jsonRet.put("courses",courses);
//                e.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        doGet(request,response);
    }

}
