package mysso.protocol1.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by pengyu.
 */
public class PrincipalDto implements Serializable {
    private String id;
    private Map<String, Object> attributes;

    public PrincipalDto() {
    }

    public PrincipalDto(String id) {
        this.id = id;
    }

    public PrincipalDto(String id, Map<String, Object> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
