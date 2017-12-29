package service;


import entity.service.Evaluation;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CourseEvaluation", urlPatterns = "/course/evaluation/*")
public class CourseEvaluation extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");
        DBConnect dbConnect = new DBConnect();

        // get the info from request
        String URI = request.getRequestURI();
        int index = URI.lastIndexOf('/');
        String courseIDStr = URI.substring(index + 1);
        courseIDStr = URLDecoder.decode(courseIDStr, "utf-8");
        String userToken = (request.getParameter("userToken") == null) ? "" : request.getParameter("userToken");
        String account = (request.getParameter("account") == null) ? "" : request.getParameter("account");

        JSONObject jsonRet;
        Status status = new Status();
        List<Evaluation> evas = new ArrayList<Evaluation>();
        if (!Widgets.isInteger(courseIDStr) || courseIDStr.equals("") || userToken.equals("")) {
            status.setStatus(false);
            status.setInfo("评教参数出问题");
        } else {
            try {
//                String studentId = Widgets.getStudentIdByAccount(dbConnect, account);
                String sql = "Select * From selection WHERE courseid = ?;";
                PreparedStatement pstm = dbConnect.prepareStatement(sql);
                pstm.setInt(1, Integer.parseInt(courseIDStr));
                ResultSet rs = pstm.executeQuery();
                while(rs.next()) {
                    Evaluation eva = new Evaluation();
                    eva.setComment(rs.getString("comment"));
                    eva.setScore("" + rs.getInt("score"));
                    evas.add(eva);
                }
                status.setStatus(true);
                status.setInfo("获取评教成功");
                rs.close();
            } catch (SQLException e) {
                status.setStatus(false);
                status.setInfo("获取评教失败");
            }
        }

        jsonRet = JSONObject.fromObject(status);
        jsonRet.put("evaluations",evas);
        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");
        DBConnect dbConnect = new DBConnect();

        // get the info from request
        String URI = request.getRequestURI();
        int index = URI.lastIndexOf('/');
        String courseIDStr = URI.substring(index + 1);
        courseIDStr = URLDecoder.decode(courseIDStr, "utf-8");
        String userToken = (request.getParameter("userToken") == null) ? "" : request.getParameter("userToken");
        String account = (request.getParameter("account") == null) ? "" : request.getParameter("account");
        String score = (request.getParameter("score") == null) ? "" : request.getParameter("score");
        String comment = (request.getParameter("comment") == null) ? "" : request.getParameter("comment");

        JSONObject jsonRet;
        Status status = new Status();
        if (!Widgets.isInteger(courseIDStr) || courseIDStr.equals("") || userToken.equals("") || !Widgets.isInteger(score) || comment.equals("")) {
            status.setStatus(false);
            status.setInfo("评教参数出问题");
        } else {
            try {
//                String studentId = Widgets.getStudentIdByAccount(dbConnect, account);
                String sql = "UPDATE selection SET score = ?, comment = ? WHERE courseid = ? and studentid = ?;";
                PreparedStatement pstm = dbConnect.prepareStatement(sql);

                pstm.setInt(3, Integer.parseInt(courseIDStr));
                pstm.setString(4, account);
                pstm.setInt(1, Integer.parseInt(score));
                pstm.setString(2, comment);
                pstm.executeUpdate();
                status.setStatus(true);
                status.setInfo("评教成功");
            } catch (SQLException e) {
                e.printStackTrace();
                status.setStatus(false);
                status.setInfo("评教失败");
            }
        }

        jsonRet = JSONObject.fromObject(status);
        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

}
