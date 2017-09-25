package mysso.authentication.principal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

/**
 * Created by pengyu.
 */
public class SimplePrincipal implements Principal {
    private String id;
    private Map<String, Object> attributes;

    public SimplePrincipal(String id) {
        this.id = id;
    }

    public SimplePrincipal(String id, Map<String, Object> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder(83, 31);
        builder.append(this.id.toLowerCase());
        return builder.toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final SimplePrincipal sp = (SimplePrincipal) obj;
        return StringUtils.equalsIgnoreCase(this.id, sp.id);
    }
}
