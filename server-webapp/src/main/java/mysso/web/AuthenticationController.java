package mysso.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pengyu on 2017/8/5.
 */
public class AuthenticationController {


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {

        return "login.jsp";
    }

}
