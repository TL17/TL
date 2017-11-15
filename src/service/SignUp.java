package service;

import net.sf.json.JSONObject;
import entity.service.Status;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="SignUp", urlPatterns="/sign_up")
public class SignUp extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String schoolID = request.getParameter("schoolID");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        if (schoolID == null) {
            schoolID = "";
        }
        if (account == null) {
            account = "";
        }
        if (password == null) {
            password = "";
        }

        Status retStatus = new Status();
        retStatus.setStatus(true);
        retStatus.setInfo("注册成功");
        if (schoolID.equals("")||account.equals("")||password.equals("")) {
            retStatus.setStatus(false);
            retStatus.setInfo("空参数");
        }

        JSONObject jsonRetStatus = JSONObject.fromObject(retStatus);
        PrintWriter out = response.getWriter();
        out.print(jsonRetStatus.toString());
    }

}
