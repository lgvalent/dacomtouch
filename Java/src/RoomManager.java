
import java.util.LinkedList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafael
 */
public class RoomManager {

    private List<Room> rooms = new LinkedList<>();

    public RoomManager() {
    }

    public void newRoom(String name, List<Interface> interfaceRoom) {
        Room room = new Room(name, interfaceRoom);
        this.rooms.add(room);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        rooms.remove(room.getName());
    }

    @Override
    public String toString() {
        return "Measurements{" + "rooms=" + rooms + '}';
    }

}
