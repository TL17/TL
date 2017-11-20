package service;

import entity.service.Status;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

@WebServlet(name="SelectCourse", urlPatterns="/course/select/*")
public class SelectCourse extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doGet(request,response);
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

        String userToken = request.getParameter("userToken");
        if (userToken == null) {
            userToken = "";
        }

        JSONObject jsonRet;
        if (nan||courseIDStr.equals("")||courseIDStr.equals("detail")||userToken.equals("")) {
            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
        } else {
            int courseID = Integer.parseInt(courseIDStr);
            Status status = new Status();
            status.setStatus(true);
            status.setInfo("选课成功");
            jsonRet = JSONObject.fromObject(status);
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

}
