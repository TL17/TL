package service.teacher;

import entity.service.Status;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="AddCourse", urlPatterns="/course/add")
public class AddCourse extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

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

        JSONObject jsonRet;
        if (userToken.equals("")||courseName.equals("")||courseInfo.equals("")||coursePlan.equals("")) {
            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("courseID", -1);
        } else {
            Status status = new Status();
            status.setStatus(true);
            status.setInfo("新建课程成功");
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("courseID", 123);
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

}
