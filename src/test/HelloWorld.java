package test;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

@WebServlet(name="HelloWorld", urlPatterns="/HelloWorld/*")
public class HelloWorld extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String URI = request.getRequestURI();
        int index = URI.lastIndexOf('/');
        String name = URI.substring(index + 1);
        name = URLDecoder.decode(name,"utf-8");
        PrintWriter out = response.getWriter();
        out.print("{\"msg\":\"Hello " + name + "!\"}");
    }
}
