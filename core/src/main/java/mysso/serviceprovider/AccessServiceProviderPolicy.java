package mysso.serviceprovider;

/**
 * Created by pengyu on 2017/8/9.
 */
public enum AccessServiceProviderPolicy {
    ACCESS_ALL("ACCESS_ALL", 0), NEED_ANY("NEED_ANY", 1), NEED_ALL("NEED_ALL", 2);

    private String name;
    private int index;

    private AccessServiceProviderPolicy(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public String getName(int index) {
        for (AccessServiceProviderPolicy item : AccessServiceProviderPolicy.values()) {
            if (item.index == index) {
                return item.getName();
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
