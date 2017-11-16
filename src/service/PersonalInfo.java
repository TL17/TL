package service;

import entity.service.Person;
import entity.service.Status;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

@WebServlet(name="PersonalInfo", urlPatterns="/account/*")
public class PersonalInfo extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        doPost(request,response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String URI = request.getRequestURI();
        int index = URI.lastIndexOf('/');
        String account = URI.substring(index + 1);
        account = URLDecoder.decode(account,"utf-8");

        String userToken = request.getParameter("userToken");
        if (userToken == null) {
            userToken = "";
        }

        JSONObject jsonRet;
        if (account.equals("")||account.equals("account")||userToken.equals("")) {
            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            Person person = new Person();
            person.setName("");
            person.setInfo("");
            person.setContact("");
            jsonRet.put("perInfo",person);
        } else {
            Status status = new Status();
            status.setStatus(true);
            status.setInfo("获取信息成功");
            jsonRet = JSONObject.fromObject(status);
            Person person = new Person();
            person.setName("测试者1");
            person.setInfo("自我介绍1");
            person.setContact("110");
            jsonRet.put("perInfo",person);
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String URI = request.getRequestURI();
        int index = URI.lastIndexOf('/');
        String account = URI.substring(index + 1);
        account = URLDecoder.decode(account,"utf-8");

        String userToken = request.getParameter("userToken");
        String name = request.getParameter("name");
        String info = request.getParameter("info");
        String contact = request.getParameter("contact");
        if (userToken == null) {
            userToken = "";
        }
        if (name == null) {
            name = "";
        }
        if (info == null) {
            info = "";
        }
        if (contact == null) {
            contact = "";
        }

        Status retStatus = new Status();
        retStatus.setStatus(true);
        retStatus.setInfo("更改成功");
        if (account.equals("")||account.equals("account")||userToken.equals("")||name.equals("")||info.equals("")||contact.equals("")) {
            retStatus.setStatus(false);
            retStatus.setInfo("空参数");
        }

        JSONObject jsonRetStatus = JSONObject.fromObject(retStatus);
        PrintWriter out = response.getWriter();
        out.print(jsonRetStatus.toString());
    }

}
