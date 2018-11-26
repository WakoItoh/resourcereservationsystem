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

public class ReservableResourceSearchHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        // 画面入力があるので、パラメータを取得し、チェックを実行
        Date start = null;
        Date end = null;
        long hours = 0;
        String resourceName = null;
        int categoryId = 0;
        int capacity = 0;
        boolean noCapacity = false;
        int officeId = 0;
        String paramStartDate = request.getParameter("start_date");
        String paramStartTime = request.getParameter("start_time");
        String paramEndTime = request.getParameter("end_time");
        String paramHours = request.getParameter("hours");
        String paramResourceName = request.getParameter("resource_name");
        String paramCategory = request.getParameter("category");
        String paramCapacity = request.getParameter("capacity");
        String paramNoCapacity = request.getParameter("no_capacity");
        String paramOffice = request.getParameter("office");

        boolean searched = false;
        if (paramStartDate != null
                || paramStartTime != null
                || paramEndTime != null
                || paramHours != null
                || paramResourceName != null
                || paramCategory != null
                || paramCapacity != null
                || paramNoCapacity != null
                || paramOffice != null) {
            searched = true;
        }

        // 入力データを保持
        request.setAttribute("paramStartDate", paramStartDate);
        request.setAttribute("paramStartTime", paramStartTime);
        request.setAttribute("paramEndTime", paramEndTime);
        request.setAttribute("paramHours", paramHours);
        request.setAttribute("paramResourceName", paramResourceName);
        request.setAttribute("paramCategory", paramCategory);
        request.setAttribute("paramCapacity", paramCapacity);
        request.setAttribute("paramNoCapacity", paramNoCapacity);
        request.setAttribute("paramOffice", paramOffice);

        List<String> errorMessageList = new ArrayList<String>();
        CommonValidator commonValidator = new CommonValidator();

        // 利用日時のチェック
        if (commonValidator.notSetOn(paramStartDate) || commonValidator.notSetOn(paramStartTime) || commonValidator.notSetOn(paramEndTime)) {
            errorMessageList.add(EM_RSV_006);
        } else {
            if (commonValidator.notValidDate(paramStartDate)) {
                errorMessageList.add(EM_RSV_007);
            } else {
                if (commonValidator.notValidDate(paramStartDate, paramStartTime)) {
                    errorMessageList.add(EM_RSV_009);
                } else {
                    start = commonValidator.getDateVal();
                }
                if (commonValidator.notValidDateExcept24(paramStartDate, paramEndTime)) {
                    errorMessageList.add(EM_RSV_010);
                } else {
                    end = commonValidator.getDateVal();
                }
            }
        }

        // 利用時間数のチェック
        if (commonValidator.notSetOn(paramHours)) {
            errorMessageList.add(EM_RSV_013);
        } else {
            if (commonValidator.notValidHoursExcept24(paramHours)) {
                errorMessageList.add(EM_RSV_014);
            } else {
                hours = commonValidator.getHoursVal();
            }
        }

        // リソース名のチェック
        if (!commonValidator.notSetOn(paramResourceName)) {
            resourceName = paramResourceName;
        }

        // カテゴリのチェック
        if (!commonValidator.notSetOn(paramCategory)) {
            if (commonValidator.notNumericOn(paramCategory)) {
                errorMessageList.add(EM_RSC_006);
            } else {
                categoryId = commonValidator.getIntVal();
            }
        }

        // 定員のチェック
        if (!commonValidator.notSetOn(paramCapacity)) {
            if (commonValidator.notNumericOn(paramCapacity)) {
                errorMessageList.add(EM_RSC_008);
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
                return PAGE_RSV_101;
            }
        }

        List<AvailableResource> availableResourceList = null;

        // Modelの生成
        ReservableResourceSearchService service = new ReservableResourceSearchService(start, end, hours, resourceName, categoryId, capacity, noCapacity, officeId);

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
            return PAGE_RSV_101;
        }

        // 処理結果の取得
        availableResourceList = service.getAvailableResourceList();
        if (searched) {
            // 初期表示以外
            if (availableResourceList == null || availableResourceList.size() == 0) {
                errorMessageList.add(EM_COMM_003);
                request.setAttribute("errorMessageList", errorMessageList);
            }
        } else {
            request.setAttribute("paramStartTime", "00:00");
            request.setAttribute("paramEndTime", "24:00");
            request.setAttribute("paramHours", "00:15");
        }

        request.setAttribute("availableResourceList", availableResourceList);

        return PAGE_RSV_101;
    }

}
