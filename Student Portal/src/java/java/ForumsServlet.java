package java.java;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dhruval
 */
@WebServlet(urlPatterns = {"/servlets/ForumsServlet"})
public class ForumsServlet extends HttpServlet {

    /*
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
        
        String serverURL="http://localhost:8080/SEECS_Portal/";
        
        //Retrieve the Session object
        HttpSession session=request.getSession();
        int userID=(Integer)session.getAttribute("userID");
        
        //If user is Admin
        String admin=(String)session.getAttribute("admin");
        
        //Connect to Database for retrieving information
        DatabaseManipulator database = (DatabaseManipulator)request.getServletContext().getAttribute("database");
        
        //Get the user data
        String threadID=(String)request.getParameter("threadID");
        
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

            if(admin==null)
            {
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
            }
            else
            {
                out.println("<nav class=\"navbar navbar-default\" role=\"navigation\" style=\"background-color: #193A59;\">\n" +
"  <div class=\"container-fluid\">\n" +
"    <!-- Brand and toggle get grouped for better mobile display -->\n" +
"    <div class=\"navbar-header\">\n" +
"      <a class=\"navbar-brand\" style=\"color:white; font-size: 30px; font-weight: 900;\" href=\"#\">Facebook</a>\n" +
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
            }
            
            out.println("<br/><br/><br/><br/>");

            out.println("<DIV class=\"container well\">");
            
            out.println("<FORM>");
            
            //Place Threads Data in the page
            try
            {
                if(threadID==null)
                {
                    out.println("<DIV class=\"row\">");
                    out.println("<DIV class=\"col-md-3\"></DIV>");
                    out.println("<DIV class=\"col-md-6\" style=\"text-align: center; background-color: black; color: white\">Threads</DIV></DIV>");

                    out.println("<DIV class=\"row\"><br/></DIV>");
                    
                    //Add Thread Button
                    out.println("<DIV class=\"row\">");
                    out.println("<DIV class=\"col-md-3\"></DIV>");
                    out.println("<DIV class=\"col-md-6\" style=\"text-align: center;\">");
                    out.println("<FORM>");
                    out.println("<BUTTON type=\"submit\" class=\"btn btn-default\" formaction=\"http://localhost:8080/SEECS_Portal/AddThread.html\">");
                    out.println("<span class=\"glyphicon glyphicon-plus\"></span> Add New Thread</BUTTON>");
                    out.println("</FORM>");
                    out.println("</DIV></DIV>");
                    
                    ResultSet set=database.getAllTreads();
                    while(set.next())
                    {
                        out.println("<DIV class=\"row\">");
                        out.println("<DIV class=\"col-md-3\"></DIV>");
                        int threadUserID=set.getInt(2);
                        
                        //Obtain User Data for the Thread Owner like picture etc
                        ResultSet userData=database.getUserData(threadUserID);
                        
                        //Creating Links
                        out.println("<DIV class=\"col-md-6\">");
                        
                        out.println("<div class=\"media\">\n" +
"    			<a class=\"pull-left\" href=\"#\">\n");
                        
                        //If user image found
                        if(userData.next())
                        {
                            String image=serverURL;
                            image+=userData.getString(2);
                            
                            out.println("<img class=\"media-object\" src=\""+image+"\" width=\"65px\">\n");
                        }
                        
                        out.println("</a>\n" +
"    			<div class=\"media-body\">\n" +
"    				<h4 class=\"media-heading\">");
                        out.println(set.getString(3)+"</h4>");
                        
                        out.println("<FORM>");
                        
                        //Save the ThreadID
                        out.println("<input type=\"hidden\" name=\"threadID\" value="+set.getString(1)+">");
                        
                        //Create View Link
                        out.println("<BUTTON type=\"submit\" class=\"btn btn-default\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/ForumsServlet\">");
                        out.println("<span class=\"glyphicon glyphicon-pencil\"></span> View</BUTTON>");
                        
                        //If current user created the thread, allow delete
                        if(threadUserID==userID || admin!=null)
                        {
                            out.println("<br/><BUTTON type=\"submit\" class=\"btn btn-default\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/DeleteForumData\">");
                            out.println("<span class=\"glyphicon glyphicon-trash\"></span> Remove</BUTTON>");
                        }
                        
                        out.println("</FORM>");

                        out.println("</div>\n");
                        out.println("</div>\n");
                        out.println("</DIV>");
                        out.println("</DIV>");
                    }
                }
                else
                {
                    ResultSet set=database.getFullTread(Integer.parseInt(threadID));
                    if(set.next())
                    {
                        //Thread Title
                        out.println("<DIV class=\"row\">");
                        out.println("<DIV class=\"col-md-3\"></DIV>");
                        out.println("<DIV class=\"col-md-6\" style=\"text-align: center; background-color: black; color: white\">"+set.getString(3)+"</DIV></DIV>");

                        out.println("<DIV class=\"row\"><br/></DIV>");

                        //Thread Text
                        out.println("<DIV class=\"row\">");
                        out.println("<DIV class=\"col-md-3\"></DIV>");
                        out.println("<DIV class=\"col-md-6\" style=\"text-align: center;\">");
                        out.println(set.getString(4));
                        out.println("</DIV>");
                        out.println("</DIV>");
                        
                        out.println("<DIV class=\"row\"><br/></DIV>");
                        out.println("<DIV class=\"row\"><br/></DIV>");
                        
                        //Thread Comments
                        ResultSet threadComments=database.getTreadComments(Integer.parseInt(threadID));
                        
                        while(threadComments.next())
                        {
                            out.println("<DIV class=\"row\">");
                            out.println("<DIV class=\"col-md-3\"></DIV>");
                            int commentUserID=threadComments.getInt(3);

                            //Obtain User Data for the Comment Owner
                            ResultSet userData=database.getUserData(commentUserID);

                            //Creating Links
                            out.println("<DIV class=\"col-md-6\">");

                            out.println("<div class=\"media\">\n" +
    "    			<a class=\"pull-left\" href=\"#\">\n");

                            //If user data found
                            if(userData.next())
                            {
                                String image=serverURL;
                                image+=userData.getString(2);

                                out.println("<img class=\"media-object\" src=\""+image+"\" width=\"65px\">\n");
                            }

                            out.println("</a>\n" +
    "    			<div class=\"media-body\">\n");
                            out.println("<h4 class=\"media-heading\">");
                            out.println(userData.getString(1)+"</h4>");
                            
                            out.println(threadComments.getString(4));

                            out.println("<FORM>");

                            //Save the CommentID
                            out.println("<input type=\"hidden\" name=\"commentID\" value="+threadComments.getString(1)+">");

                            //If current user owns the comment, allow delete
                            if(commentUserID==userID || admin!=null)
                            {
                                out.println("<BUTTON type=\"submit\" class=\"btn btn-default\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/DeleteForumData\">");
                                out.println("<span class=\"glyphicon glyphicon-trash\"></span> Remove</BUTTON>");
                            }

                            out.println("</FORM>");

                            out.println("</div>");
                            out.println("</div>");
                            out.println("</DIV>");
                            out.println("</DIV>");
                        }
                        out.println("<DIV class=\"row\"><br/></DIV>");
                        
                        //Add comment box
                        //Obtain User Data for the Comment User for comment box
                        ResultSet userData=database.getUserData(userID);
                        if(userData.next())
                        {
                            out.println("<DIV class=\"row\">");
                            out.println("<DIV class=\"col-md-3\"></DIV>");
                            
                            out.println("<DIV class=\"col-md-6\">");
                            out.println("<div class=\"media\">\n" +
    "    			<a class=\"pull-left\" href=\"#\">\n");
                            
                            String image=serverURL;
                            image+=userData.getString(2);

                            out.println("<img class=\"media-object\" src=\""+image+"\" width=\"65px\">\n");
                            
                            out.println("</a>\n" +
    "    			<div class=\"media-body\">\n");
                            
                            out.println("<FORM>");
                            out.println("<INPUT type=\"hidden\" name=\"threadID\" value=\""+threadID+"\">");
                            out.println("<TEXTAREA name=\"comment\" rows=\"3\" cols=\"50\">Your Comment</TEXTAREA>");
                            
                            out.println("<BUTTON type=\"submit\" class=\"btn btn-default\" formaction=\"http://localhost:8080/SEECS_Portal/servlets/AddForumData\">");
                            out.println("<span class=\"glyphicon glyphicon-comment\"></span> Comment</BUTTON>");
                            
                            out.println("</FORM>");
                            
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</DIV>");
                            out.println("</DIV>");
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
            out.println("</FORM>");

            out.println("</DIV>");

            out.println("<DIV class=\"myFooter\">Created using HTML, JavaScript, jQuery and Bootstrap<br/>All Rights Reserved by : Shoaib Ahmed Siddiqui</DIV>");

            out.println("</BODY>");
            
            out.println("</HEAD>");

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /*
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

    /*
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

    /*
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
