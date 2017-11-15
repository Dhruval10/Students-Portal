package java.java;
/**
 *
 * @author Administrator
 */

import java.sql.*;

public class DatabaseManipulator {
    	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
        
        public DatabaseManipulator()
        {
            try
            {
                //My SQL Database Connection
                /*
                // This will load the MySQL driver, each DB has its own driver
                Class.forName("com.mysql.jdbc.Driver");

                // Setup the connection with the DB cricopedia
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/seecs_portal?" + "user=root&password=msas12345");
                */
                
                //Oracle Database Connection
                // This will load the Oracle driver, each DB has its own driver
                Class.forName("oracle.jdbc.driver.OracleDriver");

                // Setup the connection with the DB cricopedia
                connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:SAS", "scott", "scott");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

	public int checkLoginStatus(String email, String password)
	{
            int result=-1;
            try
            {
                //convert email to lowercase as email is case insensitive
                email=email.toLowerCase();
                
                //Create Query
                String query="Select UserID from Users where Email= ? and Password= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                // preparedStatements can use variables and are more efficient
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next())
                    result= resultSet.getInt(1);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }
        
        public boolean examStatus()
        {
            boolean result=false;
            try
            {
                //Create Query
                String query="Select * from CheckExams";
                preparedStatement=connect.prepareStatement(query);

                // preparedStatements can use variables and are more efficient
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next())
                {
                    String examsInProgress=resultSet.getString(1);
                    if(examsInProgress.toUpperCase().equals("Y"))
                    {
                        result=true;
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }
        
        public void toggleExamStatus()
        {
            try
            {
                //If exam schedule is in progess, switch to routine schedule
                if(examStatus())
                {
                    //Truncate the table
                    preparedStatement.executeUpdate("TRUNCATE Table CheckExams");
                    
                    //Create Query
                    String query="Insert into CheckExams values('N')";
                    preparedStatement=connect.prepareStatement(query);

                    // preparedStatements can use variables and are more efficient
                    preparedStatement.executeUpdate();
                }
                //If routine schedule is in progress, switch to exam schedule
                else
                {
                    //Truncate the table
                    preparedStatement.executeUpdate("TRUNCATE Table CheckExams");
                    
                    //Create Query
                    String query="Insert into CheckExams values('Y')";
                    preparedStatement=connect.prepareStatement(query);

                    // preparedStatements can use variables and are more efficient
                    preparedStatement.executeUpdate();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public ResultSet examSchedule(int userID)
        {
            try
            {
                //Get user registration number from user ID
                String registrationNo=getUserRegistrationNo(userID);
                
                //Create Query
                String query="Select * from ExamScheduleView where RegistrationNo= ?";
                preparedStatement=connect.prepareStatement(query);

                //Set Query Parameters
                preparedStatement.setString(1, registrationNo);

                // preparedStatements can use variables and are more efficient
                resultSet = preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public ResultSet getCompleteExamSchedule()
        {
            try
            {
                //Create Query
                String query="Select RegistrationNo, TO_CHAR(\"Date\", 'DD-MON-YY'), Subject, Timeslot, ClassRoomNo from SeatingPlan";
                preparedStatement=connect.prepareStatement(query);

                // preparedStatements can use variables and are more efficient
                resultSet = preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public void addExamSchedule(String regNo, String date, String timeslot, String subject, String classroom)
        {
            try
            {
                //Create Query
                String query="Insert into SeatingPlan values(?, ?, ?, ?, ?)";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setString(1, regNo);
                preparedStatement.setString(2, date);
                preparedStatement.setString(3, subject);
                preparedStatement.setString(4, timeslot);
                preparedStatement.setString(5, classroom);

                // preparedStatements can use variables and are more efficient
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public void removeExamSchedule(String regNo, String date, String timeslot)
        {
            try
            {
                //Create Query
                String query="Delete from SeatingPlan Where RegistrationNo= ? and \"Date\"= '"+date+"' and Timeslot= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setString(1, regNo);
                preparedStatement.setString(2, timeslot);

                // preparedStatements can use variables and are more efficient
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public ResultSet routineSchedule(int userID)
        {
            try
            {
                //Get user class info from user ID
                String userClass=getUserClass(userID);

                //Create Query
                String query="Select * from DailyScheduleView where Class= ?";
                preparedStatement=connect.prepareStatement(query);

                //Set Query Parameters
                preparedStatement.setString(1, userClass);

                // preparedStatements can use variables and are more efficient
                resultSet = preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public ResultSet getCompleteRoutineSchedule()
        {
            try
            {
                //Create Query
                String query="Select * from Schedule";
                preparedStatement=connect.prepareStatement(query);

                // preparedStatements can use variables and are more efficient
                resultSet = preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public void addRoutineSchedule(String sClass, String subjectCode, String subject, String day, String timeslot, String classroom)
        {
            try
            {
                //Create Query
                String query="Insert into Schedule values(?, ?, ?, ?, ?, ?)";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setString(1, sClass);
                preparedStatement.setString(2, subjectCode);
                preparedStatement.setString(3, subject);
                preparedStatement.setString(4, day);
                preparedStatement.setString(5, timeslot);
                preparedStatement.setString(6, classroom);

                // preparedStatements can use variables and are more efficient
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public void removeRoutineSchedule(String sClass, String day, String timeslot)
        {
            try
            {
                //Create Query
                String query="Delete from Schedule Where Class= ? and Day= ? and Timeslot= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setString(1, sClass);
                preparedStatement.setString(2, day);
                preparedStatement.setString(3, timeslot);

                // preparedStatements can use variables and are more efficient
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public String getUserRegistrationNo(int userID)
        {
            String result=null;
            try
            {
                //Create Query
                String query="Select RegistrationNo from Users where UserID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, userID);

                // preparedStatements can use variables and are more efficient
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next())
                    result= resultSet.getString(1);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }
        
        public String getUserClass(int userID)
        {
            String result=null;
            try
            {
                //Create Query
                String query="Select class from Users where UserID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, userID);

                // preparedStatements can use variables and are more efficient
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next())
                    result= resultSet.getString(1);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }
        
        public ResultSet noticeBoard()
        {
            try
            {
                //Create Query
                String query="Select * from NoticeBoardView";
                preparedStatement=connect.prepareStatement(query);
                
                resultSet = preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public ResultSet getAllNotices()
        {
            try
            {
                //Create Query
                String query="Select * from NoticeBoard";
                preparedStatement=connect.prepareStatement(query);
                
                resultSet = preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public void removeNotice(int noticeID)
        {
            try
            {
                //Create Query
                String query="Delete From NoticeBoard where MessageID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, noticeID);
                
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public void addNotice(String notice)
        {
            try
            {
                //Create Query
                String query="Insert into NoticeBoard(Message, \"Date\") values (?, (Select SysDate from Dual))";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setString(1, notice);
                
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public ResultSet sportsGoods()
        {
            try
            {
                //Create Query
                String query="Select * from SportsView";
                preparedStatement=connect.prepareStatement(query);
                
                resultSet = preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public ResultSet readSportsItems()
        {
            try
            {
                //Create Query
                String query="Select * from Sports";
                preparedStatement=connect.prepareStatement(query);
                
                resultSet = preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public void addSportsItem(String name, int quantity)
        {
            try
            {
                //Create Query
                String query="Insert into Sports(itemname, quantity, availability) values(?, ?, 'Y')";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, quantity);
                
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public void issueSportsItem(int itemID, String registrationNo)
        {
            try
            {
                //If unable to find record then return
                if(registrationNo==null)
                    return;
                
                //Create Query
                String query="Update Sports Set availability='N', StudentRegNo= ?, \"Date\"= (Select Sysdate from dual) Where ItemID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setString(1, registrationNo);
                preparedStatement.setInt(2, itemID);
                
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public void returnSportsItem(int itemID)
        {
            try
            {
                //Create Query
                String query="Update Sports Set availability='Y' Where ItemID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, itemID);
                
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public void removeSportsItem(int itemID)
        {
            try
            {
                //Create Query
                String query="Delete From Sports Where ItemID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, itemID);
                
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public ResultSet readTempUserData(int userID)
        {
            try
            {
                //Create Query
                String query="Select * from TempUsers where UserID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, userID);
                
                resultSet = preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public ResultSet readAllTempUserData()
        {
            try
            {
                //Create Query
                String query="Select * from TempUsers";
                preparedStatement=connect.prepareStatement(query);
                
                resultSet = preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public ResultSet readAllUsersData()
        {
            try
            {
                //Create Query
                String query="Select * from Users";
                preparedStatement=connect.prepareStatement(query);
                
                resultSet = preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public boolean addUser(String registrationNo, String name, String sClass, String email, String password, String address, String phone, String picture)
        {
            try
            {
                //Convert email to lowercase as email is case insensitive
                email=email.toLowerCase();
                
                //Create Query
                String query="Insert into TempUsers(registrationNo, name, class, email, password, address, phone, picture) values (?, ?, ?, ?, ?, ?, ?, ?)";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setString(1, registrationNo);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, sClass);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, password);
                preparedStatement.setString(6, address);
                preparedStatement.setString(7, phone);
                preparedStatement.setString(8, picture);
                
                int rows=preparedStatement.executeUpdate();
                
                //Check if data inserted successfully
                if(rows==1)
                {
                    return true;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return false;
        }
        
        public void removeTempUser(int userID)
        {
            try
            {
                //Create Query
                String query="Delete from TempUsers where UserID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, userID);
                
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public void removeUser(int userID)
        {
            try
            {
                //Create Query
                String query="Delete from Users where UserID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, userID);
                
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public boolean approveUser(String registrationNo, String name, String sClass, String email, String password, String address, String phone, String picture)
        {
            try
            {
                //Create Query
                String query="Insert into Users(registrationNo, name, class, email, password, address, phone, picture) values (?, ?, ?, ?, ?, ?, ?, ?)";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setString(1, registrationNo);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, sClass);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, password);
                preparedStatement.setString(6, address);
                preparedStatement.setString(7, phone);
                preparedStatement.setString(8, picture);
                
                int rows=preparedStatement.executeUpdate();
                
                //Check if data inserted successfully
                if(rows==1)
                {
                    return true;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return false;
        }
        
        public boolean addForumThread(int userID, String threadTitle, String threadText)
        {
            try
            {
                //Create Query
                String query="Insert into Threads(UserID, ThreadTitle, ThreadText) values (?, ?, ?)";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, userID);
                preparedStatement.setString(2, threadTitle);
                preparedStatement.setString(3, threadText);
                
                int rows=preparedStatement.executeUpdate();
                
                //Check if data inserted successfully
                if(rows==1)
                {
                    return true;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return false;
        }
        
        public boolean addForumThreadComment(int threadID, int userID, String comment)
        {
            try
            {
                //Create Query
                String query="Insert into ThreadComments(ThreadID, UserID, CommentText) values (?, ?, ?)";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, threadID);
                preparedStatement.setInt(2, userID);
                preparedStatement.setString(3, comment);
                
                int rows=preparedStatement.executeUpdate();
                
                //Check if data inserted successfully
                if(rows==1)
                {
                    return true;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return false;
        }
        
        public ResultSet getAllTreads()
        {
            try
            {
                //Create Query
                String query="Select ThreadID, UserID, ThreadTitle from Threads";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                
                resultSet=preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public ResultSet getFullTread(int threadID)
        {
            try
            {
                //Create Query
                String query="Select * from Threads where ThreadID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, threadID);
                
                resultSet=preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public ResultSet getTreadComments(int threadID)
        {
            try
            {
                //Create Query
                String query="Select * from ThreadComments where ThreadID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, threadID);
                
                resultSet=preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public ResultSet getFullComment(int commentID)
        {
            try
            {
                //Create Query
                String query="Select * from ThreadComments where CommentID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, commentID);
                
                resultSet=preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public ResultSet getUserData(int userID)
        {
            try
            {
                //Create Query
                String query="Select Name, Picture from Users where UserID = ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, userID);
                
                resultSet=preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public ResultSet getTempUserData(int userID)
        {
            try
            {
                //Create Query
                String query="Select Name, Picture from TempUsers where UserID = ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, userID);
                
                resultSet=preparedStatement.executeQuery();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resultSet;
        }
        
        public void deleteThread(int threadID)
        {
            try
            {
                //Delete all comments for that thread
                deleteAllCommentsOfThread(threadID);
                
                //Delete Thread
                
                //Create Query
                String query="Delete from Threads where ThreadID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, threadID);
                
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public void deleteAllCommentsOfThread(int threadID)
        {
            try
            {
                //Create Query
                String query="Delete from ThreadComments where ThreadID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, threadID);
                
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public void deleteComment(int commentID)
        {
            try
            {
                //Create Query
                String query="Delete from ThreadComments where CommentID= ?";
                preparedStatement=connect.prepareStatement(query);
                
                //Set Query Parameters
                preparedStatement.setInt(1, commentID);
                
                preparedStatement.executeUpdate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize(); //To change body of generated methods, choose Tools | Templates.
            if(resultSet != null)
                resultSet.close();
            if(preparedStatement != null)
                preparedStatement.close();
            if(connect != null)
                connect.close();
        }
}