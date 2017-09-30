package mysso.web.admin.authentication;

import java.util.Date;

/**
 * Created by pengyu.
 */
public class AdminAuthentication {
    private AdminPrincipal adminPrincipal;
    private Date loginDate;

    public AdminPrincipal getAdminPrincipal() {
        return adminPrincipal;
    }

    public void setAdminPrincipal(AdminPrincipal adminPrincipal) {
        this.adminPrincipal = adminPrincipal;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
