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
@WebServlet(urlPatterns = {"/servlets/DeleteForumData"})
public class DeleteForumData extends HttpServlet {

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
        
        //Retrieve the Session object
        HttpSession session=request.getSession();
        int userID=(Integer)session.getAttribute("userID");
        
        //If user is Admin
        String admin=(String)session.getAttribute("admin");
        
        //Get the user data
        String threadID=(String)request.getParameter("threadID");
        String commentID=(String)request.getParameter("commentID");
        
        PrintWriter out = response.getWriter();
        try {
            //If user wants to delete the current thread
            if(threadID !=  null)
            {
                //Check if the user is the founder of the thread and has premission to delete the thread
                ResultSet set=database.getFullTread(Integer.parseInt(threadID));
                if(set.next())
                {
                    Integer threadUserID=Integer.parseInt(set.getString(2));
                    if(threadUserID==userID || admin!=null)
                    {
                        database.deleteThread(Integer.parseInt(threadID));
                        response.sendRedirect("/SEECS_Portal/servlets/ForumsServlet");
                    }
                }
            }
            
            //If user wants to delete the current comment
            if(commentID !=  null)
            {
                //Check if the user is the founder of the comment and has premission to delete the comment
                ResultSet set=database.getFullComment(Integer.parseInt(commentID));
                if(set.next())
                {
                    Integer commentUserID=Integer.parseInt(set.getString(3));
                    if(commentUserID==userID || admin!=null)
                    {
                        database.deleteComment(Integer.parseInt(commentID));
                        response.sendRedirect("/SEECS_Portal/servlets/ForumsServlet");
                    }
                }
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
