package mysso.authentication.principal;

import mysso.authentication.UsernamePasswordCredential;
import mysso.authentication.exception.AuthenticationException;
import mysso.authentication.exception.CredentialNotSupportedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pengyu on 2017/8/16.
 */
@RunWith(Parameterized.class)
public class SimplePrincipalResolverTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new UsernamePasswordCredential("jack", ""), true, true, null}, // 正常用户，有attributes
                {new UsernamePasswordCredential("peter", ""), true, false, null}, // 用户没有attributes
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
    public boolean hasAttributes;

    @Parameterized.Parameter(3)
    public Class<? extends AuthenticationException> expectedException;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void verifySupports() {
        SimplePrincipalResolver resolver = getResolver();
        assertEquals(supports, resolver.supports(credential));
    }

    @Test
    public void verifyResolve() {
        if (expectedException != null) {
            thrown.expect(expectedException);
        }
        SimplePrincipalResolver resolver = getResolver();
        Principal principal = resolver.resolve(credential);
        assertNotNull(principal);
        assertEquals(credential.getId(), principal.getId());
        assertEquals(hasAttributes, principal.getAttributes() != null);
    }

    private SimplePrincipalResolver getResolver() {
        SimplePrincipalResolver resolver = new SimplePrincipalResolver();
        AttributeRepository repository = mock(AttributeRepository.class);
        when(repository.getAttributeById("jack")).thenReturn(mock(Map.class));
        when(repository.getAttributeById("peter")).thenReturn(null);
        resolver.setAttributeRepository(repository);
        return resolver;
    }

}