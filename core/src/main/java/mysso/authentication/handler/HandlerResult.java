package mysso.authentication.handler;

/**
 * Created by pengyu on 2017/8/16.
 */
public class HandlerResult {
    private boolean success;
    private String message;

    public HandlerResult() {
    }

    public HandlerResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
