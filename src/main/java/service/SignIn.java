package service;

import net.sf.json.JSONObject;
import entity.service.Status;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="SignIn", urlPatterns="/sign_in")
public class SignIn extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String account = request.getParameter("account");
        String password = request.getParameter("password");
        if (account == null) {
            account = "";
        }
        if (password == null) {
            password = "";
        }

        JSONObject jsonRet;
        if (account.equals("")||password.equals("")) {
            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("userToken","");
        } else {
            Status status = new Status();
            status.setStatus(true);
            status.setInfo("登陆成功");
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("userToken", account + password);
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

}
