package service;

import entity.service.Status;
import net.sf.json.JSONObject;
import util.db.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "SelectCourse", urlPatterns = "/course/select/*")
public class SelectCourse extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");
        DBConnect dbConnect = new DBConnect();

        // get the courseId
        String URI = request.getRequestURI();
        int index = URI.lastIndexOf('/');
        String courseIDStr = URI.substring(index + 1);
        courseIDStr = URLDecoder.decode(courseIDStr, "utf-8");

        // get the account
        String userToken = (request.getParameter("userToken") == null) ? "" : request.getParameter("userToken");
        String account = (request.getParameter("account") == null) ? "" : request.getParameter("account");

        Status status = new Status();
        if (!Widgets.isInteger(courseIDStr) || courseIDStr.equals("") || userToken.equals("") || account.equals("")) {
            status.setStatus(false);
            status.setInfo("选课参数出问题");
        } else {
            try {
//                String studentId = Widgets.getStudentIdByAccount(dbConnect,account);
                String sql = "INSERT INTO selection (courseid, studentid,score) VALUES (?, ?, '0');";
                PreparedStatement pstm = dbConnect.prepareStatement(sql);
                pstm.setInt(1, Integer.parseInt(courseIDStr));
                pstm.setString(2, account);
                pstm.executeUpdate();
                status.setStatus(true);
                status.setInfo("选课成功");
            } catch (SQLException e) {
                e.printStackTrace();
                status.setStatus(false);
                status.setInfo("选课失败");
            }
        }

        JSONObject jsonRet = JSONObject.fromObject(status);
        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
        dbConnect.close();
    }

}
