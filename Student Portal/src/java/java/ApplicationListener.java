package java.java;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author Dhruval
 */
@WebListener()
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context=sce.getServletContext();
        DatabaseManipulator database=new DatabaseManipulator();
        context.setAttribute("database", database);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
