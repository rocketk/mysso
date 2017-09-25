package mysso.authentication.principal;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by pengyu.
 */
public interface Principal extends Serializable {

    String getId();

    Map<String, Object> getAttributes();
}
