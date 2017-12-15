package service;

import com.sun.org.apache.regexp.internal.RE;
import entity.service.Material;
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

@WebServlet(name="GetMaterial", urlPatterns="/material")
public class GetMaterial extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String courseIDStr = request.getParameter("courseID");
        boolean nan = false;
        try {
            Integer.parseInt(courseIDStr);
        } catch (Exception e) {
            nan = true;
        }

        String userToken = request.getParameter("userToken");
        String account = request.getParameter("account");
        if (userToken == null) {
            userToken = "";
        }
        if (account == null) {
            account = "";
        }

        JSONObject jsonRet;
        Status status = new Status();
        if (nan||courseIDStr.equals("")||userToken.equals("")||account.equals("")) {
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
        } else {
            int courseID = Integer.parseInt(courseIDStr);
            List<Material> materials = new ArrayList<Material>();
            try {
                DBConnect connect = new DBConnect();
                String query;
                PreparedStatement pst;
                ResultSet rs;

                query = "SELECT * FROM resource WHERE courseID = ?";
                pst = connect.prepareStatement(query);
                pst.setInt(1, courseID);

                rs = pst.executeQuery();
                while (rs.next()) {
                    Material m = new Material();
                    m.setName(rs.getString("name"));
                    m.setPath(rs.getString("path"));
                    m.setType(rs.getString("type"));
                    materials.add(m);
                }
                rs.close();

                status.setStatus(true);
                status.setInfo("获取文件成功");
            } catch (SQLException e) {
                e.printStackTrace();
                status.setStatus(false);
                status.setInfo("数据库错误");
            }
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("materials",materials);
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doGet(request,response);
    }

}
