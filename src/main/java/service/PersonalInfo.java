package service;

import entity.service.Person;
import entity.service.Status;
import net.sf.json.JSONObject;
import util.db.DBConnect;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name="PersonalInfo", urlPatterns="/account/*")
public class PersonalInfo extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        Status status = new Status();

        DBConnect dbConnect = new DBConnect();

        Person person = new Person();
        String URI = request.getRequestURI();
        int index = URI.lastIndexOf('/');
        String account = URI.substring(index + 1);
        account = URLDecoder.decode(account,"utf-8");

        String userToken = request.getParameter("userToken");
        if (userToken == null) {
            userToken = "";
        }

        JSONObject jsonRet;

        //here is the sql statement
        String querySting = "SELECT * FROM user WHERE account=?";
        PreparedStatement preparedStatement = dbConnect.prepareStatement(querySting);

        if (account.equals("")||account.equals("account")||userToken.equals("")) {
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            person.setName("");
            person.setInfo("");
            person.setContact("");
            jsonRet.put("perInfo",person);
        } else {
            try {
                preparedStatement.setString(1,account);
                ResultSet rs = preparedStatement.executeQuery();
                status.setStatus(true);
                status.setInfo("获取信息成功");
                jsonRet = JSONObject.fromObject(status);
                while (rs.next()) {
                    person.setName(rs.getString("name"));
                    person.setInfo(rs.getString("info"));
                    person.setContact(rs.getString("contact"));
                }
                jsonRet.put("perInfo", person);

            } catch (SQLException e){
                status.setStatus(false);
                status.setInfo("空参数");
                jsonRet = JSONObject.fromObject(status);
                person.setName("");
                person.setInfo(e.getMessage());
                person.setContact("");
                jsonRet.put("perInfo",person);
                e.printStackTrace();

            }
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        Status status = new Status();

        DBConnect dbConnect = new DBConnect();

        Person person = new Person();

        String URI = request.getRequestURI();
        int index = URI.lastIndexOf('/');
        String account = URI.substring(index + 1);
        account = URLDecoder.decode(account,"utf-8");

        String userToken = request.getParameter("userToken");
        String name = request.getParameter("name");
        String info = request.getParameter("info");
        String contact = request.getParameter("contact");

        JSONObject jsonRet;

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

        status.setStatus(true);
        status.setInfo("更改成功");
        if (account.equals("")||account.equals("account")||userToken.equals("")||name.equals("")||info.equals("")||contact.equals("")) {
            status.setStatus(false);
            status.setInfo("空参数");
        } else {

            try{
                String queryString;
                PreparedStatement preparedStatement;
                ResultSet rs;

                //here is the sql statement
                queryString = "UPDATE user SET name=? ,info=?, contact=?  WHERE account = ?";
                preparedStatement = dbConnect.prepareStatement(queryString);
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,info);
                preparedStatement.setString(3,contact);
                preparedStatement.setString(4,account);
                preparedStatement.executeUpdate();

//                queryString = "SELECT * FROM user WHERE account = ?";
//                preparedStatement = dbConnect.prepareStatement(queryString);
//                preparedStatement.setString(1,account);
//                rs = preparedStatement.executeQuery();
//                while (rs.next()){
//                    person.setName(rs.getString("name"));
//                    person.setInfo(rs.getString("info"));
//                    person.setContact(rs.getString("contact"));
//                }
//                rs.close();
                status.setStatus(true);
                status.setInfo("updated info successfully");

                jsonRet = JSONObject.fromObject(status);
                jsonRet.put("perInfo", person);

            } catch (SQLException e){
                status.setStatus(false);
                status.setInfo("用户信息更新失败");
                jsonRet = JSONObject.fromObject(status);
//                person.setName("");
//                person.setInfo(e.getMessage());
//                person.setContact("");
//                jsonRet.put("perInfo",person);
                e.printStackTrace();
            }
        }

        JSONObject jsonRetStatus = JSONObject.fromObject(status);
        PrintWriter out = response.getWriter();
        out.print(jsonRetStatus.toString());
    }

}
