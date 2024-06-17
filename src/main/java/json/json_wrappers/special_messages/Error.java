package json.json_wrappers.special_messages;

import json.wrapper_utilities.WrapperClass;

/**
 * The class Error is for json wrapping the protocol Error
 */
public class Error extends WrapperClass {

    private String error;

    /**
     * constructor of Error
     *
     * @param error : String error message
     */
    public Error(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
