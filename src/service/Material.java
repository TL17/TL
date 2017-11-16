package service;

import entity.service.Status;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

@WebServlet(name="Material", urlPatterns="/material/*")
public class Material extends javax.servlet.http.HttpServlet {

    //TODO: 技术重点——寻找并参照获取文件的代码，需要完全重写
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doPost(request,response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String URI = request.getRequestURI();
        int index = URI.lastIndexOf('/');
        String materialIDStr = URI.substring(index + 1);
        materialIDStr = URLDecoder.decode(materialIDStr,"utf-8");
        boolean nan = false;
        try {
            Integer.parseInt(materialIDStr);
        } catch (Exception e) {
            nan = true;
        }

        String userToken = request.getParameter("userToken");
        if (userToken == null) {
            userToken = "";
        }

        JSONObject jsonRet;
        if (nan||materialIDStr.equals("")||materialIDStr.equals("material")||userToken.equals("")) {
            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
        } else {
            int materialID = Integer.parseInt(materialIDStr);
            Status status = new Status();
            status.setStatus(true);
            status.setInfo("获取文件成功");
            jsonRet = JSONObject.fromObject(status);
        }

        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doGet(request,response);
    }

}
