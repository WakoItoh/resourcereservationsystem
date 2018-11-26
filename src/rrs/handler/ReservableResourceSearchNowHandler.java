package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import rrs.dao.DataAccessException;
import rrs.dto.AvailableResource;
import rrs.model.ReservableResourceSearchService;

public class ReservableResourceSearchNowHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        // 画面入力があるので、パラメータを取得し、チェックを実行
        Date start = null;
        Date end = null;
        long hours = 0;
        int capacity = 0;
        boolean noCapacity = false;
        int officeId = 0;
        String paramCapacity = request.getParameter("capacity");
        String paramNoCapacity = request.getParameter("no_capacity");
        String paramOffice = request.getParameter("office");

        boolean searched = false;
        if (paramCapacity != null
                || paramNoCapacity != null
                || paramOffice != null) {
            searched = true;
        }

        // 入力データを保持
        request.setAttribute("paramCapacity", paramCapacity);
        request.setAttribute("paramNoCapacity", paramNoCapacity);
        request.setAttribute("paramOffice", paramOffice);

        List<String> errorMessageList = new ArrayList<String>();
        CommonValidator commonValidator = new CommonValidator();

        // 利用日時
        // 利用時間数
        if (searched) {
            // 初期表示以外
            AvailableResource availableResource = (AvailableResource) request.getAttribute("availableResource");
            if (availableResource == null) {
                errorMessageList.add(EM_RSV_006);
            } else {
                start = availableResource.getAvailableStart();
                end = availableResource.getAvailableEnd();
                hours = end.getTime() - start.getTime();
            }
        }

        // 利用人数のチェック
        if (commonValidator.notSetOn(paramCapacity)) {
            errorMessageList.add(EM_RSV_023);
        } else {
            if (commonValidator.notNumericOn(paramCapacity)) {
                errorMessageList.add(EM_RSV_024);
            } else {
                capacity = commonValidator.getIntVal();
            }
        }

        // 定員0の備品のみ検索する
        noCapacity = commonValidator.checkOn(paramNoCapacity);

        // 事業所のチェック
        if (!commonValidator.notSetOn(paramOffice)) {
            if (commonValidator.notNumericOn(paramOffice)) {
                errorMessageList.add(EM_RSC_011);
            } else {
                officeId = commonValidator.getIntVal();
            }
        }

        // チェックOKならModelを生成してvalidate、executeを呼ぶ
        if (searched) {
            // 初期表示以外
            if (errorMessageList.size() > 0) {
                request.setAttribute("errorMessageList", errorMessageList);
                return PAGE_RSV_103;
            }
        }

        List<AvailableResource> availableResourceList = null;

        // Modelの生成
        ReservableResourceSearchService service = new ReservableResourceSearchService(start, end, hours, null, 0, capacity, noCapacity, officeId);

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
            errorMessageList = service.getErrorMessageList();
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_RSV_103;
        }

        // 処理結果の取得
        availableResourceList = service.getAvailableResourceList();
        if (searched) {
            // 初期表示以外
            if (availableResourceList == null || availableResourceList.size() == 0) {
                errorMessageList.add(EM_COMM_003);
                request.setAttribute("errorMessageList", errorMessageList);
            }
        }

        request.setAttribute("availableResourceList", availableResourceList);
        request.setAttribute("paramAttendanceCount", paramCapacity);

        return PAGE_RSV_103;
    }

}
