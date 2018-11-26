package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import rrs.dao.DataAccessException;
import rrs.dto.Reservation;
import rrs.dto.Resource;
import rrs.model.ResourceDeleteConfirmService;

public class ResourceDeleteConfirmHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        // URLパラメータ・画面入力があるので、パラメータを取得し、チェックを実行
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
        List<Reservation> reservationList = null;

        // Modelの生成
        ResourceDeleteConfirmService service = new ResourceDeleteConfirmService(resourceId);

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
            errorMessageList.add(EM_RSC_002);
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_RSC_002;
        }

        reservationList = service.getReservationList();
        if (reservationList != null && reservationList.size() != 0) {
            request.setAttribute("message", PM_RSC_005);
            request.setAttribute("reservationList", reservationList);
        }

        request.setAttribute("resource", resource);

        return PAGE_RSC_301;
    }

}
