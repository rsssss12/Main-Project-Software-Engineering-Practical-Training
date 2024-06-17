package json.json_wrappers.connection;

import json.wrapper_utilities.WrapperClass;

/**
 * The class HelloServer is for json wrapping the protocol HelloServer
 */
public class HelloServer extends WrapperClass {

    private String group;
    private boolean isAI;
    private String protocol;

    transient private int clientID;

    /**
     * constructor of HelloServer
     *
     * @param group    : String group name
     * @param isAI     : boolean if AI
     * @param protocol : String protocol version
     */
    public HelloServer(String group, boolean isAI, String protocol) {
        this.group = group;
        this.isAI = isAI;
        this.protocol = protocol;
    }

    /**
     * constructor of HelloServer
     *
     * @param group    : String group name
     * @param isAI     : boolean if AI
     * @param protocol : String protocol version
     * @param clientID : int ID of client
     */
    public HelloServer(String group, boolean isAI, String protocol, int clientID) {
        this.group = group;
        this.isAI = isAI;
        this.protocol = protocol;
        this.clientID = clientID;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
