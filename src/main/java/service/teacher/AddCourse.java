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
        //doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        Status status = new Status();
        DBConnect dbConnect = new DBConnect();

        String account = request.getParameter("account");
        String userToken = request.getParameter("userToken");
        String courseName = request.getParameter("courseName");
        String courseInfo = request.getParameter("courseInfo");
        String coursePlan = request.getParameter("coursePlan");

        if (account == null) {
            account = "";
        }
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

        JSONObject jsonRet;
        if (userToken.equals("")||courseName.equals("")||courseInfo.equals("")||coursePlan.equals("")) {
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("courseID", -1);
        } else {
            try {
                String querySting;
                PreparedStatement preparedStatement;
                ResultSet rs;

                //here is the sql statement
                querySting = "SELECT * FROM user WHERE account = ?";
                preparedStatement = dbConnect.prepareStatement(querySting);
                preparedStatement.setString(1,account);
                rs = preparedStatement.executeQuery();
                rs.next();
                String teacherId = rs.getString("id");
                rs.close();

                querySting = "INSERT INTO course (name, info, plan, teacherid) VALUES (?,?,?,?) ";
                preparedStatement = dbConnect.prepareStatement(querySting);
                preparedStatement.setString(1,courseName);
                preparedStatement.setString(2,courseInfo);
                preparedStatement.setString(3,coursePlan);
                preparedStatement.setString(4,teacherId);
                preparedStatement.executeUpdate();

                querySting = "SELECT MAX(id) AS max_id FROM course WHERE name = ?";
                preparedStatement = dbConnect.prepareStatement(querySting);
                preparedStatement.setString(1,courseName);
                rs = preparedStatement.executeQuery();
                rs.next();
                String courseId = rs.getString("max_id");
                rs.close();

                status.setStatus(true);
                status.setInfo("新建课程成功");

                jsonRet = JSONObject.fromObject(status);
                jsonRet.put("courseID", courseId);

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
