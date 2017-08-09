package mysso.util;

import java.util.UUID;

/**
 * Created by pengyu on 2017/8/5.
 */
public class UUIDGenerator implements UniqueIdGenerator {
    private String prefix;
    private String suffix;
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
        return null;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
