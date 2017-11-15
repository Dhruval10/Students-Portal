package java.java;

 
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dhruval
 */
@WebServlet(urlPatterns = {"/servlets/admin/AdminScheduleServlet"})
public class AdminScheduleServlet extends HttpServlet {

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
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/admin/AdminPageServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-home\"></span> Home</button>\n" +
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/admin/AdminUsersServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-user\"></span> Users</button>\n" +
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/ForumsServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-pencil\"></span> Forums</button>\n" +
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/admin/AdminScheduleServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-time\"></span> Schedule</button>\n" +
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/admin/AdminSportsServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-tower\"></span> Sports</button>\n" +
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/admin/AdminNoticeBoardServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-th-list\"></span> Notice Board</button>\n" +
"      <button type=\"submit\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/LogoutServlet\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-off\"></span> Logout</button>\n" +             
"    </form>   \n" +
"  </div>\n" +
"</nav>");
            
            out.println("<br/><br/><br/><br/>");

            out.println("<DIV class=\"container well\">");

            out.println("<DIV class=\"row\">");
            out.println("<DIV class=\"col-md-3\"></DIV>");
            out.println("<DIV class=\"col-md-6\" style=\"text-align: center; background-color: black; color: white\">Schedule Management</DIV></DIV>");

            out.println("<DIV class=\"row\"><br/></DIV>");
            
            //Exam Week Toggle Button
            out.println("<DIV class=\"row\">");
            out.println("<DIV class=\"col-md-3\"></DIV>");
            out.println("<DIV class=\"col-md-6\" style=\"text-align: center;\">");
            out.println("<FORM action=\"http://localhost:8080/SEECS_Portal/servlets/admin/ToggleSchedule\">");
            //If exams are in progress
            if(database.examStatus())
            {
                out.println("<BUTTON type=\"submit\" class=\"btn btn-primary\">");
                out.println("<span class=\"glyphicon glyphicon-random\"></span> Swtich to Routine Schedule</BUTTON>");
            }
            //If routine schedule is in progress
            else
            {
                out.println("<BUTTON type=\"submit\" class=\"btn btn-danger\">");
                out.println("<span class=\"glyphicon glyphicon-random\"></span> Switch to Exam Schedule</BUTTON>");
            }
            
            out.println("</FORM>");
            out.println("</DIV></DIV>");
            
            out.println("<DIV class=\"row\"><br/></DIV>");
                    
            //Add Routine Schedule Button
            out.println("<DIV class=\"row\">");
            out.println("<DIV class=\"col-md-3\"></DIV>");
            out.println("<DIV class=\"col-md-6\" style=\"text-align: center;\">");
            out.println("<FORM>");
            out.println("<BUTTON type=\"submit\" class=\"btn btn-default\" formaction=\"http://localhost:8080/SEECS_Portal/AddRoutineSchedule.html\">");
            out.println("<span class=\"glyphicon glyphicon-plus\"></span> Add Routine Schedule</BUTTON>");
            out.println("</FORM>");
            out.println("</DIV></DIV>");
            
            out.println("<DIV class=\"row\"><br/></DIV>");
            
            //Add Exam Schedule Button
            out.println("<DIV class=\"row\">");
            out.println("<DIV class=\"col-md-3\"></DIV>");
            out.println("<DIV class=\"col-md-6\" style=\"text-align: center;\">");
            out.println("<FORM>");
            out.println("<BUTTON type=\"submit\" class=\"btn btn-default\" formaction=\"http://localhost:8080/SEECS_Portal/AddExamSchedule.html\">");
            out.println("<span class=\"glyphicon glyphicon-plus\"></span> Add Exam Schedule</BUTTON>");
            out.println("</FORM>");
            out.println("</DIV></DIV>");
            
            out.println("<DIV class=\"row\"><br/></DIV>");
            
            //Display Routine Schedule Button
            out.println("<DIV class=\"row\">");
            out.println("<DIV class=\"col-md-3\"></DIV>");
            out.println("<DIV class=\"col-md-6\" style=\"text-align: center;\">");
            out.println("<FORM>");
            out.println("<BUTTON type=\"submit\" class=\"btn btn-default\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/admin/AdminRoutineSchedule\">");
            out.println("<span class=\"glyphicon glyphicon-th-list\"></span> View Routine Schedule</BUTTON>");
            out.println("</FORM>");
            out.println("</DIV></DIV>");
            
            out.println("<DIV class=\"row\"><br/></DIV>");
            
            //Display Exam Schedule Button
            out.println("<DIV class=\"row\">");
            out.println("<DIV class=\"col-md-3\"></DIV>");
            out.println("<DIV class=\"col-md-6\" style=\"text-align: center;\">");
            out.println("<FORM>");
            out.println("<BUTTON type=\"submit\" class=\"btn btn-default\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/admin/AdminExamSchedule\">");
            out.println("<span class=\"glyphicon glyphicon-th-list\"></span> View Exam Schedule</BUTTON>");
            out.println("</FORM>");
            out.println("</DIV></DIV>");
            
            out.println("<DIV class=\"row\"><br/></DIV>");

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
