package json.json_wrappers.connection;

import json.wrapper_utilities.WrapperClass;

/**
 * The class HelloClient is for json wrapping the protocol HelloClient
 */
public class HelloClient extends WrapperClass {

    private String protocol;

    /**
     * constructor of HelloClient
     *
     * @param protocol : String protocol version
     */
    public HelloClient(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

}
