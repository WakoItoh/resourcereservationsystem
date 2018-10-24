package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rrs.dao.DataAccessException;
import rrs.model.ResourceDeleteService;

public class ResourceDeleteHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        // 画面入力があるので、パラメータを取得し、チェックを実行
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

        // Modelの生成
        ResourceDeleteService service = new ResourceDeleteService(resourceId);

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

            // 処理結果の取得
            if (service.getCount() == 0) {
                errorMessageList.add(EM_RSC_023);
                request.setAttribute("errorMessageList", errorMessageList);
                return CTRL_RSC_002;
            }
        } else {
            // 到達不能
            return PAGE_ERR_001;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("message", PM_RSC_003);

        return CTRL_RSC_001;
    }

}
