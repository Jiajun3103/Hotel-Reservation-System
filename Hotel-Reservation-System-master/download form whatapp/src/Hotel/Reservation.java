package Hotel;
import Database.MySQL_db;
import Payment.Bank_Payment;
import Payment.Cash_Payment;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Reservation {
    private int reservation_id;
    private User customer;
    private Room room;
    private LocalDate check_in_date;
    private LocalDate check_out_date;
    MySQL_db db = new MySQL_db();

    public Reservation(User guest, Room room){
        int new_reservation_id = 0;

        try {
            db.prepareStatement("SELECT count(*) AS total FROM reservation");
            db.QueryResult();
            db.result.next();
            new_reservation_id = db.result.getInt("total") + 1;
        } catch (SQLException e){
            System.out.println("Error in getting Reservation Id");
        }

        this.reservation_id = new_reservation_id;
        this.customer = guest;
        this.room = room;
    }

    public Reservation(User guest, Room room, String checkIn, String checkOut){
        this.customer = guest;
        this.room = room;
        check_in_date = LocalDate.parse(checkIn);
        check_out_date = LocalDate.parse(checkOut);
    }

    public boolean check_valid_date(String checkIn, String checkOut){
        try{
            check_in_date = LocalDate.parse(checkIn);
            check_out_date = LocalDate.parse(checkOut);

            // Check is the date logically
            if(check_out_date.isBefore(check_in_date) || check_in_date.isEqual(check_out_date)){
                return false;
            }

            return true;
        } catch (DateTimeParseException e){
            return false;
        }
    }

    public boolean Make_Reservation(Cash_Payment cash_payment){
        try {
            // Insert into Reservation
            db.prepareStatement("INSERT INTO reservation (user_id, room_id, checkIn_date, checkOut_date)" +
                    " VALUES (?,?,?,?)");
            db.statement.setInt(1, customer.user_id);
            db.statement.setInt(2, room.GetRoomId());
            db.statement.setDate(3, Date.valueOf(check_in_date));
            db.statement.setDate(4, Date.valueOf(check_out_date));
            db.QueryUpdate();

            // Change the status of the room from 'Vacant' to 'Reserved'
            db.prepareStatement("UPDATE room SET status = 'Reserved' WHERE room_id = ?");
            db.statement.setInt(1, room.GetRoomId());
            db.QueryUpdate();

            cash_payment.ProcessPayment();

            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean Make_Reservation(Bank_Payment bank_payment){
        try {
            // Insert into Reservation
            db.prepareStatement("INSERT INTO reservation (user_id, room_id, checkIn_date, checkOut_date)" +
                    " VALUES (?,?,?,?)");
            db.statement.setInt(1, customer.user_id);
            db.statement.setInt(2, room.GetRoomId());
            db.statement.setDate(3, Date.valueOf(check_in_date));
            db.statement.setDate(4, Date.valueOf(check_out_date));
            db.QueryUpdate();

            // Change the status of the room from 'Vacant' to 'Reserved'
            db.prepareStatement("UPDATE room SET status = 'Reserved' WHERE room_id = ?");
            db.statement.setInt(1, room.GetRoomId());
            db.QueryUpdate();

            bank_payment.ProcessPayment();

            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void Cancel_Reservation(){
        try {
            // Get and delete the last payment id for that reservation
            db.prepareStatement("SELECT payment_id FROM payment WHERE user_id = ? AND room_id = ? " +
                    "ORDER BY payment_id DESC LIMIT 1;");
            db.statement.setInt(1, customer.user_id);
            db.statement.setInt(2, room.GetRoomId());
            db.QueryResult();
            db.result.next();
            int payment_id = db.result.getInt("payment_id");

            db.prepareStatement("DELETE FROM payment WHERE payment_id = ?");
            db.statement.setInt(1, payment_id);
            db.QueryUpdate();

            // Change the status of the room from 'Reserved' to 'Vacant'
            db.prepareStatement("UPDATE room SET status = 'Vacant' WHERE room_id = ?");
            db.statement.setInt(1, room.GetRoomId());
            db.QueryUpdate();

            // Delete the reservation
            db.prepareStatement("DELETE FROM reservation WHERE user_id = ? AND room_id = ?");
            db.statement.setInt(1, customer.user_id);
            db.statement.setInt(2, room.GetRoomId());
            db.QueryUpdate();

        } catch (SQLException e){
            System.out.println("Something error in Canceling Reservation!");
        }
    }

    /*public void Modify_Reservation(Date new_check_in_date, Date new_check_out_date){

    }*/

}
