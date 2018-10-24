package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rrs.dao.DataAccessException;
import rrs.dto.User;
import rrs.model.LoginService;

public class LoginHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        // 画面入力があるので、パラメータを取得し、チェックを実行
        String id = "";
        String password = "";
        String paramId = request.getParameter("id");
        String paramPassword = request.getParameter("password");

        List<String> errorMessageList = new ArrayList<String>();
        CommonValidator commonValidator = new CommonValidator();

        // IDのチェック
        if (commonValidator.notSetOn(paramId)) {
            errorMessageList.add(EM_AUTH_001);
        } else {
            id = paramId;
        }

        // パスワードのチェック
        if (commonValidator.notSetOn(paramPassword)) {
            errorMessageList.add(EM_AUTH_002);
        } else {
            password = paramPassword;
        }

        // チェックOKならModelを生成してvalidate、executeを呼ぶ
        if (errorMessageList.size() > 0) {
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_AUTH_001;
        }

        User loginUser = null;

        // Modelの生成
        LoginService service = new LoginService(id, password);

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

            // 認証結果の取得
            loginUser = service.getLoginUser();
            if (loginUser == null) {
                errorMessageList.add(EM_AUTH_003);
                request.setAttribute("errorMessageList", errorMessageList);
                return PAGE_AUTH_001;
            }
        } else {
            // 到達不能
            return PAGE_ERR_001;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("loginUser", loginUser);

        return CTRL_RSV_001;
    }

}
