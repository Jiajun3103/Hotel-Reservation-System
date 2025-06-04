package Payment;
import Database.MySQL_db;

import java.sql.SQLException;

public class Bank_Payment implements Payment{
    public int payment_id;
    public double amount;
    public String payment_method = "Bank";
    private int user_id;
    private int room_id;
    MySQL_db db = new MySQL_db();

    // Can be "Bank Transfer" or "Credit Card"
    public Bank_Payment(int user_id, int room_id){
        this.user_id = user_id;
        this.room_id = room_id;
    }

    public Bank_Payment(int user_id, int room_id, double amount){
        int payment_id = 0;
        try {
            db.prepareStatement("SELECT count(*) AS total FROM payment");
            db.QueryResult();
            db.result.next();
            payment_id = db.result.getInt("total") + 1;
        } catch (SQLException e){
            System.out.println("Error in getting Payment Id");
        }

        this.payment_id = payment_id;
        this.amount = amount;
        this.payment_method = "Bank";
        this.user_id = user_id;
        this.room_id = room_id;
    }


    @Override
    public void ProcessPayment(){
        try {
            db.prepareStatement("INSERT INTO payment (payment_id, amount, payment_method, user_id, room_id)" +
                    " VALUES (?,?,?,?, ?)");
            db.statement.setInt(1, payment_id);
            db.statement.setDouble(2, amount);
            db.statement.setString(3, payment_method);
            db.statement.setInt(4, user_id);
            db.statement.setInt(5, room_id);
            db.statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Something error in Process Payment");
        }
    }

    @Override
    public void RefundPayment(){

    }
}
