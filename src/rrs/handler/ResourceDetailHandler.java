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
import rrs.model.ResourceDetailService;

public class ResourceDetailHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User loginUser = (User) session.getAttribute("loginUser");

        // URLパラメータ入力があるので、パラメータを取得し、チェックを実行
        int resourceId = 0;
        String paramResourceId = request.getParameter("resource_id");

        List<String> errorMessageList = new ArrayList<String>();
        CommonValidator commonValidator = new CommonValidator();

        // リソースIDのチェック
        if (commonValidator.notSetOn(paramResourceId)) {
            errorMessageList.add(EM_RSC_001);
        } else {
            if (commonValidator.notNumericOn(paramResourceId)) {
                errorMessageList.add(EM_RSC_001);
            } else {
                resourceId = commonValidator.getIntVal();
            }
        }

        // チェックOKならModelを生成してvalidate、executeを呼ぶ
        if (errorMessageList.size() > 0) {
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_RSC_002;
        }

        Resource resource = null;

        // Modelの生成
        ResourceDetailService service = new ResourceDetailService(loginUser, resourceId);

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

        // 処理結果の取得
        resource = service.getResource();
        if (resource == null) {
            errorMessageList.add(EM_RSC_001);
            request.setAttribute("errorMessageList", errorMessageList);
        }

        request.setAttribute("resource", resource);

        return PAGE_RSC_002;
    }

}
