package java.java;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(urlPatterns = {"/servlets/AddForumThread"})
public class AddForumThread extends HttpServlet {

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
        
        //Retrieve the Session object
        HttpSession session=request.getSession();
        int userID=(Integer)session.getAttribute("userID");
        
        //Connect to Database for retrieving information
        DatabaseManipulator database = (DatabaseManipulator)request.getServletContext().getAttribute("database");
        
        PrintWriter out = response.getWriter();
        try {
            //Get Data from the Form
            String title=request.getParameter("title");
            String content=request.getParameter("content");
            
            //Insert the Forum Thread into the Database
            boolean threadAdded = database.addForumThread(userID, title, content);
            
            //Redirect the user to the threads page
            //Generate User Response
            out.println("<HTML><HEAD><TITLE>Lab 11</TITLE>");
            out.println("<STYLE>body{background-size:cover;}</STYLE></HEAD>");
            out.println("<BODY background=\"http://localhost:8080/SEECS_Portal/wallpaper.jpg\">");
            out.println("<DIV style=\"text-align: center; font-size: 20px;\">");
            out.println("<br/><br/><br/><br/><br/><br/><br/>");

            if(threadAdded)
            {
                out.println("Thread Added Successfully");
            }
            else
            {
                out.println("Unable to Add Thread");
            }
            
            out.println("<br/>");
            out.println("<a href=\"/SEECS_Portal/servlets/ForumsServlet\">Continue</a>");
            out.println("</DIV></BODY></HTML>");
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
