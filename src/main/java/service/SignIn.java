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


        DBConnect dbConnect = new DBConnect();
        String stringFormat  = "SELECT password FROM user WHERE account=\'%s\'";
        String sql = String.format(stringFormat, account);
        ResultSet rs = dbConnect.executeQuery(sql);
        /*return : {userToken: String, status: boolean, info: String} userToken：服务器对应该账号的token，用于验证身份，与用户一一对应*/
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
            status.setInfo("操作失败");
            e.printStackTrace();
        }

        JSONObject jsonRet;
        jsonRet = JSONObject.fromObject(status);
        jsonRet.put("userToken", account + password);


        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

}
