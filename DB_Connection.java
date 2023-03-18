package HoliAssessment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Examination_System","vivek","");

            return con;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;

    }
}
