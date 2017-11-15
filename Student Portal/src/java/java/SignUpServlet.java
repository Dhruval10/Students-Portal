package java.java;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Dhruval
 */
@WebServlet(urlPatterns = {"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private String filePath;
    private final int maxFileSize = 5 * 1024 * 1024;        //5 MB max file size
    private final int maxMemSize = 1 * 1024 * 1024;         //Max size in memory
    private File file;
    DiskFileItemFactory factory;
    ServletFileUpload upload;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        
        filePath="C:\\Users\\Shoaib Ahmed\\Documents\\NetBeansProjects\\SEECS Portal\\web\\";
        factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));

        // Create a new file upload handler
        upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );
    }
    
    
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String registrationNo=null, name=null, sClass=null, email=null, password=null, address=null, phone=null;
            
            boolean writeSuccessful=false;
            String fileName=null;
            
            DatabaseManipulator database = (DatabaseManipulator)request.getServletContext().getAttribute("database");
            
            // Parse the request to get file items
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            while ( i.hasNext () ) 
            {
                FileItem fi = (FileItem)i.next();
                
                //If form field i.e. something to be filled
                if ( fi.isFormField() )
                {
                    if(fi.getFieldName().equals("registrationNo"))
                    {
                        registrationNo=fi.getString();
                    }
                    else if(fi.getFieldName().equals("name"))
                    {
                        name=fi.getString();
                    }
                    else if(fi.getFieldName().equals("class"))
                    {
                        sClass=fi.getString();
                    }
                    else if(fi.getFieldName().equals("email"))
                    {
                        email=fi.getString();
                    }
                    else if(fi.getFieldName().equals("password"))
                    {
                        password=fi.getString();
                    }
                    else if(fi.getFieldName().equals("address"))
                    {
                        address=fi.getString();
                    }
                    else if(fi.getFieldName().equals("phone"))
                    {
                        phone=fi.getString();
                    }
                }
                //If not a Form field i.e. if an upload field
                else if ( !fi.isFormField () )	
                {
                    // Get the uploaded file parameters
                    fileName = fi.getName();

                    // Write the file
                    file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")+1));
                    
                    fi.write( file ) ;
                    writeSuccessful=true;
                }
            }
            
            boolean userAdded=false;
            if(writeSuccessful)
            {
                userAdded=database.addUser(registrationNo, name, sClass, email, password, address, phone, fileName);
            }
            
            //Generate User Response
            out.println("<HTML><HEAD><TITLE>Lab 11</TITLE>");
            out.println("<STYLE>body{background-size:cover;}</STYLE></HEAD>");
            out.println("<BODY background=\"http://localhost:8080/SEECS_Portal/wallpaper.jpg\">");
            out.println("<DIV style=\"text-align: center; font-size: 20px;\">");
            out.println("<br/><br/><br/><br/><br/><br/><br/>");

            if(userAdded && writeSuccessful)
            {
                out.println("User Added Successfully");
            }
            else
            {
                out.println("Unable to Add User");
            }
            
            out.println("<br/>");
            out.println("<a href=\"/SEECS_Portal/index.html\">Continue</a>");
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
        try 
        {
            processRequest(request, response);
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
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
        try 
        {
            processRequest(request, response);
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
