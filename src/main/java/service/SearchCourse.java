package service;

import entity.service.Course;
import entity.service.Status;
import net.sf.json.JSONObject;
import util.db.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "SearchCourse", urlPatterns = "/course/search")
public class SearchCourse extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String keyword = (request.getParameter("keyword") == null) ? "" : request.getParameter("keyword");

        JSONObject jsonRet = null;
        Status status = new Status();
        if (keyword.equals("")) {
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            Course[] courses = new Course[0];
            jsonRet.put("courses", courses);
            PrintWriter out = response.getWriter();
            out.print(jsonRet.toString());
            return;
        }

        // course : id name info plan teacherid
        DBConnect dbConnect = new DBConnect();
        String sql = "SELECT * FROM course WHERE name LIKE ? ;";
        PreparedStatement pstm  = dbConnect.prepareStatement(sql);

        try {
            pstm.setString(1, "%"+keyword+"%");
            ResultSet rs = pstm.executeQuery();
            rs.last();
            int rowCount = rs.getRow();
            Course[] courses = new Course[rowCount];
            rs.first();

            int start= 0;
            if (rowCount != 0) {
                do {
                    String c_id = rs.getString(1);
                    String c_name = rs.getString(2);
                    String c_info = rs.getString(3);
                    String c_plan = rs.getString(4);

                    courses[start] = new Course();

                    courses[start].setCourseID(Integer.parseInt(c_id));
                    courses[start].setCourseName(c_name);
                    courses[start].setCourseInfo(c_info);
                    courses[start].setCoursePlan(c_plan);
                    start++;
                } while (rs.next());
            }

            status.setStatus(true);
            status.setInfo("课程搜索成功");
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("courses", courses);

        } catch (SQLException e) {
            status.setStatus(false);
            status.setInfo("操作失败");
            e.printStackTrace();
        }


        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doGet(request,response);
    }

}
