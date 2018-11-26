package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rrs.dao.DataAccessException;
import rrs.dto.User;
import rrs.model.ReservationCancelService;

public class ReservationCancelHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User loginUser = (User) session.getAttribute("loginUser");

        // URLパラメータ・画面入力があるので、パラメータを取得し、チェックを実行
        int reservationId = 0;
        String paramReservationId = request.getParameter("reservation_id");

        List<String> errorMessageList = new ArrayList<String>();
        CommonValidator commonValidator = new CommonValidator();

        // リソースIDのチェック
        if (commonValidator.notSetOn(paramReservationId)) {
            errorMessageList.add(EM_RSV_002);
        } else {
            if (commonValidator.notNumericOn(paramReservationId)) {
                errorMessageList.add(EM_RSV_002);
            } else {
                reservationId = commonValidator.getIntVal();
            }
        }

        // チェックOKならModelを生成してvalidate、executeを呼ぶ
        if (errorMessageList.size() > 0) {
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_RSV_002;
        }

        // Modelの生成
        ReservationCancelService service = new ReservationCancelService(loginUser, reservationId);

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
                errorMessageList.add(EM_RSV_002);
                request.setAttribute("errorMessageList", errorMessageList);
                return CTRL_RSV_002;
            }
        } else {
            // 到達不能
            return PAGE_ERR_001;
        }

        //HttpSession session = request.getSession(true);
        session.setAttribute("message", PM_RSV_003);

        return CTRL_RSV_001;
    }

}
