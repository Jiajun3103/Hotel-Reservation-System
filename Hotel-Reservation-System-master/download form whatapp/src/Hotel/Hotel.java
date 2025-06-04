package Hotel;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Integer> hotels_id = new ArrayList<>();
    private List<String> hotels_name = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    Room room = new StandardRoom(0, "--Please select a Room Number--", 0);

    public Hotel(){
        hotels_id.add(0);
        hotels_name.add("--Please select a Hotel--");
        rooms.add(room);
    }

    public void AddHotelId(int hotel_id){
        hotels_id.add(hotel_id);
    }

    public void AddRoom(Room room){
        rooms.add(room);
    }

    public void ClearRoom(){
        rooms.clear();
        rooms.add(room);
    }

    public void AddHotel(String hotel_name){
        hotels_name.add(hotel_name);
    }

    public Room GetRoom(int option){
        return rooms.get(option);
    }

    public List<Room> GetAvailableRooms(){
        List<Room> availableRooms = new ArrayList<>();
        for(Room room : rooms){
            availableRooms.add(room);
        }
        return availableRooms;
    }

    public List<String> GetAvailableHotels(){
        List<String> availableHotels = new ArrayList<>();
        for(String hotel : hotels_name){
            availableHotels.add(hotel);
        }
        return availableHotels;
    }


    public int GetHotelId(int option){ return hotels_id.get(option); }

    public String GetName(int option){ return hotels_name.get(option); }

}
