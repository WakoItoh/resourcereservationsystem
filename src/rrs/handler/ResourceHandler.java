package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rrs.dao.DataAccessException;
import rrs.dto.Resource;
import rrs.dto.User;
import rrs.model.ResourceService;

public class ResourceHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User loginUser = (User) session.getAttribute("loginUser");

        List<String> errorMessageList = new ArrayList<String>();

        List<Resource> resourceList = null;

        // Modelの生成
        ResourceService service = new ResourceService(loginUser);

        // validateとexecuteを呼び出す
        if (service.validate()) {

            try {
                service.execute();
            } catch (DataAccessException e) {
                // DataAccessExceptionの発生はシステムエラー
                e.printStackTrace();
                errorMessageList.add(EM_COMM_001);
                request.setAttribute("errorMessageList", errorMessageList);
                return PAGE_ERR_001;
            }
        } else {
            // 到達不能
            return PAGE_ERR_001;
        }

        // Listの取得
        resourceList = service.getResourceList();
        if (resourceList == null || resourceList.size() == 0) {
            errorMessageList.add(EM_COMM_003);
            request.setAttribute("errorMessageList", errorMessageList);
        }

        request.setAttribute("resourceList", resourceList);

        return PAGE_RSC_001;
    }

}
