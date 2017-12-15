package service;

import entity.service.Status;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import util.db.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;

@WebServlet(name="UploadMaterial", urlPatterns="/upload_material")
public class UploadMaterial extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doGet(request,response);
        request.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        //1. 获取request所有item，准备重新解析，并将各item设置为request的Attribute
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List requestItems = null;
        try {
            requestItems = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        //2. 解析request内容，重写参数为request的Attribute
        FileItem fileItem = null;
        String name = null;
        String path = null;
        String type = null;
        Iterator iter = requestItems.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            if (item.isFormField()) {
                //是普通的key-value参数
                String fieldName = item.getFieldName();
                String value = item.getString();
                request.setAttribute(fieldName, value);
            } else {
                int index;
                //是文件
                fileItem = item;
                name = item.getName();
                index = name.lastIndexOf(".");
                type = name.substring(index + 1);
                index = name.lastIndexOf("\\");
                name = name.substring(index + 1);
            }
        }

        //3. 致此，参数已处理过，接下来重新通过getAttribute拿参数，并确保非空，否则视为出错并return
        JSONObject jsonRet;
        String account = (String) request.getAttribute("account");
        String userToken = (String) request.getAttribute("userToken");
        String courseIDStr = (String) request.getAttribute("courseID");
        if (account == null) {
            account = "";
        }
        if (userToken == null) {
            userToken = "";
        }
        if (courseIDStr == null || courseIDStr.length() <= 0) {
            courseIDStr = "NAN";
        }

        boolean nan = false;
        try {
            Integer.parseInt(courseIDStr);
        } catch (Exception e) {
            nan = true;
        }

        if (nan||userToken.equals("")||account.equals("")||fileItem == null) {
            Status status = new Status();
            status.setStatus(false);
            status.setInfo("空参数");
            jsonRet = JSONObject.fromObject(status);
            jsonRet.put("materialID", -1);
            PrintWriter out = response.getWriter();
            out.print(jsonRet.toString());
            return;
        }

        //4. 若没有空参数，开始文件写入
        Status status = new Status();
        String realPath = request.getRealPath("/");
        realPath = realPath + "/uploadedFiles/" + courseIDStr;
        File dir = new File(realPath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        File fileTemp = new File((realPath + "/" + name));
        boolean existed = fileTemp.exists();

        path = "uploadedFiles/" + courseIDStr + "/" + name;
        File file = new File(realPath, name);
        try {
            fileItem.write(file);
        } catch (Exception e) {
            e.printStackTrace();
            status.setStatus(false);
            status.setInfo("写入上传文件失败");
        }

        //5. 若没有空参数，还要进行数据写入
        int courseID = Integer.parseInt(courseIDStr);
        DBConnect connect = new DBConnect();
        String update;
        PreparedStatement pst;
        try {
            if (!existed) {
                update = "INSERT INTO resource(courseID, name, path, type) VALUES (?, ?, ?, ?);";
                pst = connect.prepareStatement(update);
                pst.setInt(1, courseID);
                pst.setString(2, name);
                pst.setString(3, path);
                pst.setString(4, type);
                pst.executeUpdate();
            }
            status.setStatus(true);
            status.setInfo("上传文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            status.setStatus(false);
            status.setInfo("写入上传文件数据失败");
        }

        jsonRet = JSONObject.fromObject(status);
        PrintWriter out = response.getWriter();
        out.print(jsonRet.toString());
    }

}
