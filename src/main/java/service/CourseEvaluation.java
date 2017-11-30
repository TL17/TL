package service;

import entity.service.Course;
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

@WebServlet(name="CourseEvaluation", urlPatterns="/course/evaluation/*")
public class CourseEvaluation extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        doPost(request,response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        Status status = new Status();
        String url = "jdbc:mysql://123.207.6.234:3306/tl?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String dbPwd = "root";

        DBConnect dbConnect = new DBConnect(url, user, dbPwd);

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

        //here is the sql statement
        String querySting = "SELECT * FROM course where id=?";
        PreparedStatement preparedStatement = dbConnect.prepareStatement(querySting);
        JSONObject jsonRet;
        if (nan||courseIDStr.equals("")||courseIDStr.equals("evaluation")||userToken.equals("")) {
//            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            Evaluation[] evaluations = new Evaluation[0];
            jsonRet.put("evaluations",evaluations);
        } else {
            try {
                ResultSet rs = preparedStatement.executeQuery();
                int courseID = Integer.parseInt(courseIDStr);
//                Status status = new Status();
                status.setStatus(true);
                status.setInfo("获取评教成功");
                jsonRet = JSONObject.fromObject(status);
                Evaluation[] evaluations = new Evaluation[5];
                for (int i = 0; i < evaluations.length; i++) {
                    evaluations[i] = new Evaluation();
                    evaluations[i].setScore(rs.getString("score") + ".0");
                    evaluations[i].setComment(rs.getString("comment") + "课的评论" + (i + 1));
                }
                jsonRet.put("evaluations", evaluations);
            } catch (SQLException e){
                status.setStatus(false);
                status.setInfo("空参数 "+ e.getMessage());
                jsonRet = JSONObject.fromObject(status);
                Evaluation[] evaluations = new Evaluation[0];
                jsonRet.put("evaluations",evaluations);
                e.printStackTrace();
            }
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doGet(request,response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        Status status = new Status();
        String url = "jdbc:mysql://123.207.6.234:3306/tl?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String dbPwd = "root";

        DBConnect dbConnect = new DBConnect(url, user, dbPwd);

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
        String score = request.getParameter("score");
        String comment = request.getParameter("comment");
        if (userToken == null) {
            userToken = "";
        }
        if (score == null) {
            score = "";
        }
        if (comment == null) {
            comment = "";
        }

        //here is the sql statement
        String querySting = "INSERT INTO evauation (id, score, comment) VALUES (?,?,?) ";
        PreparedStatement preparedStatement = dbConnect.prepareStatement(querySting);
        JSONObject jsonRet;
        if (nan||courseIDStr.equals("")||courseIDStr.equals("evaluation")||userToken.equals("")||score.equals("")||comment.equals("")) {
//            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
        } else {
            try {
                preparedStatement.setString(1,userToken);
                preparedStatement.setString(2,score);
                preparedStatement.setString(3,comment);
                preparedStatement.executeQuery();
                int courseID = Integer.parseInt(courseIDStr);
                //            Status status = new Status();
                status.setStatus(true);
                status.setInfo("评教成功");
                jsonRet = JSONObject.fromObject(status);
            } catch (SQLException e){
                status.setStatus(false);
                status.setInfo("空参数 "+e.getMessage());
                jsonRet = JSONObject.fromObject(status);
                e.printStackTrace();
            }
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

}
