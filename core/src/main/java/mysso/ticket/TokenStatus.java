package mysso.ticket;

/**
 * Created by pengyu on 2017/8/17.
 */
public enum TokenStatus {
    VALID(0, "valid token"), VALID_BUT_EXPIRED(1, "valid but expired (haven't been markExpired)"), INVALID(2, "invalid token");

    private int index;
    private String desc;

    private TokenStatus(int index, String desc){
        this.index = index;
        this.desc = desc;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
