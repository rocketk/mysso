package mysso.authentication.handler;

import mysso.authentication.UsernamePasswordCredential;
import mysso.authentication.principal.Credential;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by pengyu on 2017/8/16.
 */
@RunWith(Parameterized.class)
public class AcceptUsersAuthenticationHandlerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new UsernamePasswordCredential("jack", "123123"), true, true}, // valid credential
                {new UsernamePasswordCredential("jack", "123321"), true, false}, // wrong password
                {new UsernamePasswordCredential("peter", "123321"), true, false}, // user doesn't exist
                {new Credential() { // unsupported type
                    @Override
                    public String getId() {
                        return "jack";
                    }
                }, false, false}
        });
    }

    @Parameterized.Parameter
    public Credential credential;

    @Parameterized.Parameter(1)
    public boolean supports;

    @Parameterized.Parameter(2)
    public boolean success;

    @Test
    public void verifySupports() {
        AuthenticationHandler handler = getAuthenticationHandler();
        boolean supports = handler.supports(credential);
        assertEquals(this.supports, supports);
    }

    @Test
    public void verifyAuthenticate() {
        AuthenticationHandler handler = getAuthenticationHandler();
        HandlerResult result = handler.authenticate(credential);
        assertEquals(success, result.isSuccess());
    }

    private AuthenticationHandler getAuthenticationHandler() {
        AcceptUsersAuthenticationHandler authenticationHandler = new AcceptUsersAuthenticationHandler();
        authenticationHandler.setPasswordEncoder(new PlainTextPasswordEncoder());
        Map<String, String> users = new HashMap<>();
        users.put("jack", "123123");
        authenticationHandler.setUsers(users);
        return authenticationHandler;
    }
}