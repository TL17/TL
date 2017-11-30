package service;

import entity.service.Course;
import entity.service.Status;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="ManageCourse", urlPatterns="/course/manage")
public class ManageCourse extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        doPost(request,response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String userToken = request.getParameter("userToken");
        if (userToken == null) {
            userToken = "";
        }

        JSONObject jsonRet;
        if (userToken.equals("")) {
            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            Course[] courses = new Course[0];
            jsonRet.put("courses",courses);
        } else {
            Status status = new Status();
            status.setStatus(true);
            status.setInfo("获取信息成功");
            jsonRet = JSONObject.fromObject(status);
            Course[] courses = new Course[5];
            for (int i=0;i<courses.length;i++) {
                courses[i] = new Course();
                courses[i].setCourseID("sdfghjk");
                courses[i].setCourseName("课程名称"+(i+1));
                courses[i].setCourseInfo("课程介绍"+(i+1));
                courses[i].setCoursePlan("课程大纲"+(i+1));
            }
            jsonRet.put("courses",courses);
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doGet(request,response);
    }

}
