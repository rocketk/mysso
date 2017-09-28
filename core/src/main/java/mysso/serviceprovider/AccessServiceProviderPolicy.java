package mysso.serviceprovider;

/**
 * Created by pengyu.
 */
public enum AccessServiceProviderPolicy {
    ALLOW_ALL("ALLOW_ALL", 0), NEED_ANY_ATTRIBUTE("NEED_ANY_ATTRIBUTE", 1), NEED_ALL_ATTRIBUTES("NEED_ALL_ATTRIBUTES", 2);

    private String name;
    private int index;

    private AccessServiceProviderPolicy(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static AccessServiceProviderPolicy valueOf(int index) {
        for (AccessServiceProviderPolicy item : AccessServiceProviderPolicy.values()) {
            if (item.index == index) {
                return item;
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
