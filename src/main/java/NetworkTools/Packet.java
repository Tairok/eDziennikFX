package NetworkTools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * The {@code Packet} class represents a packet for communication between the client and server.
 */
public class Packet implements Serializable {



    private PacketType type;
    private Object payload;

    /**
     * Creates a new packet with the specified type and payload.
     *
     * @param type    The type of the packet.
     * @param payload The payload of the packet.
     */
    public Packet(PacketType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    /**
     * Gets the type of the packet.
     *
     * @return The type of the packet.
     */
    public PacketType getType() {
        return type;
    }

    /**
     * Gets the payload of the packet.
     *
     * @return The payload of the packet.
     */
    public Object getPayload() {
        return payload;
    }


}
