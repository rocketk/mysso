package mysso.authentication.credential;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by pengyu on 2017/8/3.
 */
public class UsernamePasswordCredential implements Credential {
    private String username;
    private String password;

    public UsernamePasswordCredential() {
    }

    public UsernamePasswordCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getId() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UsernamePasswordCredential that = (UsernamePasswordCredential) o;

        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }

        if (username != null ? !username.equals(that.username) : that.username != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(username).append(password).toHashCode();
    }
}
