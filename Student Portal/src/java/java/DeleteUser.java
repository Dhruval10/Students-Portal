package java.java;

import java.io.File;
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
@WebServlet(urlPatterns = {"/servlets/admin/DeleteUser"})
public class DeleteUser extends HttpServlet {

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
        String userID=request.getParameter("userID");
        String tempUserID=request.getParameter("tempUserID");
        
        //Image path
        String filePath="C:\\Users\\Shoaib Ahmed\\Documents\\NetBeansProjects\\SEECS Portal\\web\\";
        
        PrintWriter out = response.getWriter();
        try {
            
            //If delete user button is pressed
            if(userID!=null)
            {
                int userIDint=Integer.parseInt(userID);
                
                ResultSet set=database.getUserData(userIDint);
                if(set.next())
                {
                    //Delete User Picture
                    String fileName=set.getString(2);
                    File file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")+1));
                    file.delete();

                    //Delete user from database
                    database.removeUser(userIDint);
                }
                response.sendRedirect("/SEECS_Portal/servlets/admin/AdminUsersServlet");
            }
            
            //If delete temp user button is pressed
            if(tempUserID!=null)
            {
                int userIDint=Integer.parseInt(tempUserID);
                
                ResultSet set=database.getTempUserData(userIDint);
                if(set.next())
                {
                    //Delete User Picture
                    String fileName=set.getString(2);
                    File file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")+1));
                    file.delete();

                    //Delete user from database
                    database.removeUser(userIDint);
                }
                response.sendRedirect("/SEECS_Portal/servlets/admin/AdminUsersServlet");
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
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
