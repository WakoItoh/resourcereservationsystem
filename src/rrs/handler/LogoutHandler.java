package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        HttpSession session = request.getSession(true);

        session.removeAttribute("loginUser");

        session.setAttribute("message", PM_AUTH_001);

        return PAGE_AUTH_001;
    }

}
