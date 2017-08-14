package mysso.util;

import java.util.UUID;

/**
 * Created by pengyu on 2017/8/5.
 */
public class UUIDGenerator implements UniqueIdGenerator {
    private String prefix;
    private String suffix;

    public UUIDGenerator() {
    }

    public UUIDGenerator(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public String getNewId() {
        StringBuilder newId = new StringBuilder();
        if (prefix != null) {
            newId.append(prefix);
        }
        newId.append(UUID.randomUUID().toString().replace("-", ""));
        if (suffix != null) {
            newId.append(suffix);
        }
        return newId.toString();
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
