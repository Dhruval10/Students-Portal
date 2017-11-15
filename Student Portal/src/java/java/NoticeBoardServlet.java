package java.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dhruval
 */
@WebServlet(urlPatterns = {"/servlets/NoticeBoardServlet"})
public class NoticeBoardServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //Connect to Database for retrieving information
        DatabaseManipulator database = (DatabaseManipulator)request.getServletContext().getAttribute("database");
        
        //Get the user data
        int userID=(Integer)request.getSession().getAttribute("userID");
        
        PrintWriter out = response.getWriter();
        try {
            out.println("<HTML>");
            
            out.println("<HEAD>");

            out.println("<TITLE>SEECS Portal</TITLE>");

            out.println("<link href=\"http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css\" rel=\"stylesheet\">");
            out.println("<script src=\"http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js\"></script>");
            out.println("<script src=\"http://code.jquery.com/jquery-1.11.0.min.js\"></script>");

            out.println("<STYLE type=\"text/css\">");
            out.println("body	{");
            out.println("background-color: white;");
            out.println("color: black;");
            out.println("background-size: cover;");
            out.println("background-image: url('http://localhost:8080/SEECS_Portal/wallpaper.jpg');");
            out.println("text-align: left;		}");

            out.println(".head 	{");
            out.println("background-color: #193A59;");
            out.println("color: white;");
            out.println("font-size: 50px;");
            out.println("font-weight: 700;");
            out.println("text-align:center;		}");

            out.println(".myFooter	{");
            out.println("position: fixed;");
            out.println("bottom: 0;");
            out.println("width: 100%;");
            out.println("background-color: black;");
            out.println("color: white;");
            out.println("text-align:center;		}");

            out.println("</STYLE>");

            out.println("</HEAD>");

            out.println("<BODY>");

            out.println("<nav class=\"navbar navbar-default\" role=\"navigation\" style=\"background-color: #193A59;\">\n" +
"  <div class=\"container-fluid\">\n" +
"    <!-- Brand and toggle get grouped for better mobile display -->\n" +
"    <div class=\"navbar-header\">\n" +
"      <a class=\"navbar-brand\" style=\"color:white; font-size: 30px; font-weight: 900;\" href=\"#\">SEECS Student Portal</a>\n" +
"    </div>\n" +
"    <form class=\"navbar-form navbar-right\" role=\"search\">\n" +
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/ForumsServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-pencil\"></span> Forums</button>\n" +
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/DailyScheduleServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-time\"></span> Schedule</button>\n" +
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/SportsServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-tower\"></span> Sports</button>\n" +
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/NoticeBoardServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-th-list\"></span> Notice Board</button>\n" +
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/LogoutServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-off\"></span> Logout</button>\n" +             
"    </form>   \n" +
"  </div>\n" +
"</nav>");
            
            out.println("<br/><br/><br/><br/>");

            out.println("<DIV class=\"container well\">");

            out.println("<DIV class=\"row\">");
            out.println("<DIV class=\"col-md-3\"></DIV>");
            out.println("<DIV class=\"col-md-6\" style=\"text-align: center; background-color: black; color: white\">Today's Notices</DIV></DIV>");

            out.println("<DIV class=\"row\"><br/></DIV>");
            
            //Place Notice Board Data in the page
            try
            {
                ResultSet set=database.noticeBoard();
                while(set.next())
                {
                    //Time
                    out.println("<DIV class=\"row\">");
                    out.println("<DIV class=\"col-md-3\"></DIV>");
                    out.println("<DIV class=\"col-md-3\">");
                    out.println("Time : </DIV>");
                    out.println("<DIV class=\"col-md-3\">");
                    out.println(set.getString(2));
                    out.println("</DIV>");
                    out.println("</DIV>");
                    
                    //Notice
                    out.println("<DIV class=\"row\">");
                    out.println("<DIV class=\"col-md-3\"></DIV>");
                    out.println("<DIV class=\"col-md-3\">");
                    out.println(set.getString(1));
                    out.println("</DIV>");
                    out.println("</DIV>");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            out.println("</DIV>");

            out.println("<DIV class=\"myFooter\">Created using HTML, JavaScript, jQuery and Bootstrap<br/>All Rights Reserved by : Shoaib Ahmed Siddiqui</DIV>");

            out.println("</BODY>");
            
            out.println("</HEAD>");

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
