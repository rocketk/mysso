package mysso.authentication.handler;

import mysso.authentication.credential.UsernamePasswordCredential;
import mysso.authentication.credential.Credential;
import mysso.authentication.exception.AuthenticationException;
import mysso.authentication.exception.CredentialNotSupportedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by pengyu.
 */
@RunWith(Parameterized.class)
public class AcceptUsersAuthenticationHandlerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new UsernamePasswordCredential("jack", "123123"), true, true, null}, // valid credential
                {new UsernamePasswordCredential("jack", "123321"), true, false, null}, // wrong password
                {new UsernamePasswordCredential("peter", "123321"), true, false, null}, // user doesn't exist
                {new Credential() { // unsupported type
                    @Override
                    public String getId() {
                        return "jack";
                    }
                }, false, false, CredentialNotSupportedException.class}
        });
    }

    @Parameterized.Parameter
    public Credential credential;

    @Parameterized.Parameter(1)
    public boolean supports;

    @Parameterized.Parameter(2)
    public boolean success;

    @Parameterized.Parameter(3)
    public Class<? extends AuthenticationException> expectedException;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void verifySupports() {
        AuthenticationHandler handler = getAuthenticationHandler();
        boolean supports = handler.supports(credential);
        assertEquals(this.supports, supports);
    }

    @Test
    public void verifyAuthenticate() {
        if (expectedException != null) {
            thrown.expect(expectedException);
        }
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