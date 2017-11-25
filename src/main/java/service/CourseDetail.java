package service;

import entity.service.Course;
import entity.service.Status;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

@WebServlet(name="CourseDetail", urlPatterns="/course/detail/*")
public class CourseDetail extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        doPost(request,response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

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

        JSONObject jsonRet;
        if (nan||courseIDStr.equals("")||courseIDStr.equals("detail")) {
            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            Course course = new Course();
            course.setCourseID("sdfghjk");
            course.setCourseName("");
            course.setCourseInfo("");
            course.setCoursePlan("");
            jsonRet.put("course",course);
        } else {
            int courseID = Integer.parseInt(courseIDStr);
            Status status = new Status();
            status.setStatus(true);
            status.setInfo("课程搜索成功");
            jsonRet = JSONObject.fromObject(status);
            Course course = new Course();
            course.setCourseID("asdfghjkl");
            course.setCourseName("课程名称"+courseID);
            course.setCourseInfo("课程介绍"+courseID);
            course.setCoursePlan("课程大纲"+courseID);
            jsonRet.put("course",course);
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doGet(request,response);
    }

}
