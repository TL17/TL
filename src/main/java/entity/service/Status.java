package entity.service;

/**
 * <h1>Status Class</h1>
 * This class has the information about the status.
 * The getters and setters can be called for necessary information.
 * Mainly the status of all the API will be saved by the help of this class.
 * This class will help to debug by providing the current status.
 *
 * @version 1.0
 * @since 2017/12/06
 *
 * */

public class Status {
    private boolean status;
    private String info;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
