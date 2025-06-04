package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class MySQL_db implements AutoCloseable {
    private Connection MySqlConnection;
    public PreparedStatement statement;
    public ResultSet result;

    public MySQL_db(){
        try{
            String url = "jdbc:mysql://localhost:3306/hotel_reservation_system";
            String user = "root";
            String password = "";

            MySqlConnection = DriverManager.getConnection(url, user, password);
            statement = null;
            result = null;

        }catch (SQLException e)
        {
            if(e.getErrorCode() == 0) {
                System.out.println("Unable to connect to database");
            }
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            if(MySqlConnection != null){
                MySqlConnection.close();
                MySqlConnection = null;
            }

            if(statement != null){
                statement.close();
                statement = null;
            }

            if(result != null){
                result.close();
                result = null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void prepareStatement(String query){
        try
        {
            statement = MySqlConnection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void QueryUpdate(){
        try {
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void QueryResult(){
        try {
            result = statement.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}