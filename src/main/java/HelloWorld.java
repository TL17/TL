import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

@WebServlet(name="HelloWorld", urlPatterns="/HelloWorld/*")
public class HelloWorld extends javax.servlet.http.HttpServlet {

//    假设当前url：http://localhost:8080/CarsiLogCenter_new/idpstat.jsp?action=idp.sptopn
//    request.getRequestURL() http://localhost:8080/CarsiLogCenter_new/idpstat.jsp
//    request.getRequestURI() /CarsiLogCenter_new/idpstat.jsp
//    request.getContextPath()/CarsiLogCenter_new
//    request.getServletPath() /idpstat.jsp
//    request.getQueryString()action=idp.sptopn

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String URI = request.getRequestURI();
        int index = URI.lastIndexOf('/');
        String name = URI.substring(index + 1);
        name = URLDecoder.decode(name,"utf-8");

        String date = request.getParameter("date");
        if (date == null) {
            date = "";
        }

        String outStr = "{" +
                "\"msg\":\"Hello " + name + "!\"," +
                "\"date\":\""+ date +"\"" +
                "}";
        PrintWriter out = response.getWriter();
        out.print(outStr);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request,response);
    }

}
