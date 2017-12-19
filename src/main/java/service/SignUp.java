package service;

import net.sf.json.JSONObject;
import entity.service.Status;
import util.db.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.MessageFormat;

@WebServlet(name = "SignUp", urlPatterns = "/sign_up")
public class SignUp extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        JSONObject jsonRet;
        Status status = new Status();

        String schoolID = (request.getParameter("schoolID") == null) ? "" : request.getParameter("schoolID");
        String account = (request.getParameter("account") == null) ? "" : request.getParameter("account");
        String password = (request.getParameter("password") == null) ? "" : request.getParameter("password");
        String type = (request.getParameter("type") == null) ? "" : request.getParameter("type");

        if (type.equals("")) {
            type = "student";
        }

        if (account.equals("") || password.equals("") || schoolID.equals("") || type.equals("")) {
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            out.print(jsonRet.toString());
            return;
        }

        DBConnect dbConnect = new DBConnect();
        String stringFormat = "INSERT INTO user (id, password, account, name, info, contact, type) VALUES (\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\')";
        String sql = String.format(stringFormat, schoolID, password, account,"Junying", "", "", type);

        /*
        * 若schoolID通过验证，account唯一，则返回一个注册成功的反馈状态，否则，返回注册失败信息；*/
        try {
            dbConnect.executeUpdate(sql);
            status.setStatus(true);
            status.setInfo("注册成功");
        }catch (SQLException e) {
            status.setStatus(false);
            status.setInfo("注册失败");
            e.printStackTrace();
        }

        JSONObject jsonRetStatus = JSONObject.fromObject(status);
        out.print(jsonRetStatus.toString());
    }

}
