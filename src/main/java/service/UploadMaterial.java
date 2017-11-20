package service;

import entity.service.Status;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="UploadMaterial", urlPatterns="/upload_material")
public class UploadMaterial extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doPost(request,response);
    }

    //TODO: 技术重点——寻找并参照上传文件的代码，需要完全重写
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doGet(request,response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String userToken = request.getParameter("userToken");
        String courseIDStr = request.getParameter("courseID");
        String material = request.getParameter("material");
        if (userToken == null) {
            userToken = "";
        }
        if (courseIDStr == null) {
            courseIDStr = "NAN";
        }
        if (material == null) {
            material = "";
        }

        boolean nan = false;
        try {
            Integer.parseInt(courseIDStr);
        } catch (Exception e) {
            nan = true;
        }

        JSONObject jsonRet;
        if (nan||courseIDStr.equals("NAN")||userToken.equals("")||material.equals("")) {
            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("materialID",-1);
        } else {
            int courseID = Integer.parseInt(courseIDStr);
            Status status = new Status();
            status.setStatus(true);
            status.setInfo("上传文件成功");
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("materialID",courseID);
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

}
