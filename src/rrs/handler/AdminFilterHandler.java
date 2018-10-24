package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rrs.dao.DataAccessException;
import rrs.dao.UserDao;
import rrs.dto.User;

public class AdminFilterHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        List<String> errorMessageList = new ArrayList<String>();

        HttpSession session = request.getSession(false);
        if (session == null) {
            errorMessageList.add(EM_COMM_002);
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_ERR_001;
        }

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            errorMessageList.add(EM_COMM_002);
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_ERR_001;
        }

        try {
            UserDao dao = new UserDao();
            loginUser = dao.authenticate(loginUser.getId(), loginUser.getPassword());
            if (loginUser == null) {
                errorMessageList.add(EM_COMM_004);
                request.setAttribute("errorMessageList", errorMessageList);
                return PAGE_ERR_001;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            errorMessageList.add(EM_COMM_001);
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_ERR_001;
        }

        session.setAttribute("loginUser", loginUser);

        if (loginUser.getUserLevel() != 2) {
            errorMessageList.add(EM_COMM_005);
            request.setAttribute("errorMessageList", errorMessageList);
            return CTRL_RSC_001;
        }

        return null;
    }

}
