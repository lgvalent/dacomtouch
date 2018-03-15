
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
public class Room {

    private String name;
    private List<Interface> interfaces = new LinkedList<>();

    public Room() {
    }

    public Room(String name, List<Interface> interfaceRoom) {
        this.name = name;
        this.interfaces = interfaceRoom;
    }

    public void addInterfaces(List<Interface> interfaceRoom) {
        this.interfaces = interfaceRoom;
    }

    public void addInterfaces(Interface InterfaceRoom) {
        interfaces.add(InterfaceRoom);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Interface> getInterfaces() {
        return interfaces;
    }

    @Override
    public String toString() {
        return "Room{" + "name=" + name + ", interfaces=" + interfaces + '}';
    }

}
