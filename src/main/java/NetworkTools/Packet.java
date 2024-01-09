package NetworkTools;

import java.io.Serializable;


public class Packet implements Serializable{
    private PacketType type;
    private Object payload;

    public Packet(PacketType type, Object payload){
        this.type = type;
        this.payload = payload;
    }

    public PacketType getType(){
        return type;
    }

    public Object getPayload(){
        return payload;
    }
}
