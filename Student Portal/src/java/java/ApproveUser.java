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
@WebServlet(urlPatterns = {"/servlets/admin/ApproveUser"})
public class ApproveUser extends HttpServlet {

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
        
        //Get the user data who has been approved
        String userID=request.getParameter("userID");
        
        PrintWriter out = response.getWriter();
        try 
        {
            ResultSet previousDataSet=database.readTempUserData(Integer.parseInt(userID));
            
            if(previousDataSet.next())
            {
                //Read data of the user who is approved
                String registrationNo=previousDataSet.getString(2);
                String name=previousDataSet.getString(3);
                String sClass=previousDataSet.getString(4);
                String email=previousDataSet.getString(5);
                String password=previousDataSet.getString(6);
                String address=previousDataSet.getString(7);
                String phone=previousDataSet.getString(8);
                String picture=previousDataSet.getString(9);

                //Move his data in the table of approved user
                database.approveUser(registrationNo, name, sClass, email, password, address, phone, picture);

                //Delete User Data from Temp User Table
                database.removeTempUser(Integer.parseInt(userID));

                response.sendRedirect("/SEECS_Portal/servlets/admin/AdminPageServlet");
            }
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        finally 
        {
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
