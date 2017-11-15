package java.java;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(urlPatterns = {"/servlets/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DatabaseManipulator database = (DatabaseManipulator)request.getServletContext().getAttribute("database");
        String username=request.getParameter("email");
        String password=request.getParameter("password");
        int userID = database.checkLoginStatus(username, password);
        
        if(userID != -1)
        {
            HttpSession session=request.getSession(true);
            session.setAttribute("userID", userID);
            
            //If the user is admin i.e. the first user in the database
            if(userID==1)
            {
                session.setAttribute("admin", "true");
                RequestDispatcher requestD=request.getRequestDispatcher("/servlets/admin/AdminPageServlet");
                requestD.forward(request, response);
            }

            RequestDispatcher requestD=request.getRequestDispatcher("/servlets/ForumsServlet");
            requestD.forward(request, response);
        }
        else
        {
            RequestDispatcher requestD=request.getRequestDispatcher("/index.html");
            requestD.forward(request, response);
        }
    }
}