package Hotel;

public abstract class Room {
    private int room_id;
    private String roomNumber;
    private String roomType;
    private double price;
    private String status;

    public Room(int room_id, String roomNumber, String roomType, double price){
        this.room_id = room_id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
    }

    public Room(int room_id, String roomNumber,double price){
        this.room_id = room_id;
        this.roomNumber = roomNumber;
        this.price = price;
    }

    public boolean CheckAvailability(){
        return "Available".equalsIgnoreCase(status);
    }

    public void ReserveRoom(){
        if(CheckAvailability()){ this.status = "Reserved"; }
        else { System.out.println("Room not available for reservation"); }
    }

    public int GetRoomId(){ return room_id; }
    public String GetRoomNumber() { return roomNumber; }
    public String GetRoomType(){ return roomType; }
    public double GetPrice() { return price; }
    public String GetStatus() { return status; }

    public void SetRoomNumber(String roomNumber){ this.roomNumber = roomNumber; }
    public void SetRoomType(String roomType){ this.roomType = roomType; }
    public void SetPrice(double price){ this.price = price; }
    public void SetStatus(String status){ this.roomNumber = status; }

    @Override
    public String toString(){
        return roomNumber;
    }
}
