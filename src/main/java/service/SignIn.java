package service;

import net.sf.json.JSONObject;
import entity.service.Status;
import util.db.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

@WebServlet(name = "SignIn", urlPatterns = "/sign_in")
public class SignIn extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");
        Status status = new Status();


        String account = request.getParameter("account");
        String password = request.getParameter("password");
        if (account == null) {
            account = "";
        }
        if (password == null) {
            password = "";
        }
        //TODO: how to get the url+user+dbpwd ?
        String url = "jdbc:mysql://123.207.6.234:3306/tl?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String dbPwd = "root";

        DBConnect dbConnect = new DBConnect(url, user, dbPwd);
        String stringFormat  = "SELECT password FROM user WHERE account=\'%s\'";
        String sql = String.format(stringFormat, account);
        ResultSet rs = dbConnect.executeQuery(sql);

        try {
            String rs_pwd = "";
            while (rs.next()) {
                rs_pwd = rs.getString(1);
                break;// should return only one record
            }
            if (rs_pwd.equals(password)) {
                status.setStatus(true);
                status.setInfo("登陆成功");
            } else {
                status.setStatus(false);
                status.setInfo("密码与用户名不匹配");
            }
        } catch (SQLException e) {
            status.setStatus(false);
            status.setInfo(e.getMessage());
            e.printStackTrace();
        }

        JSONObject jsonRet;
        jsonRet = JSONObject.fromObject(status);
        jsonRet.put("userToken", account + password);


        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

}
