package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import rrs.dao.DataAccessException;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.model.ReservableResourceService;

public class ReservableResourceHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        List<String> errorMessageList = new ArrayList<String>();

        List<Category> categoryList = null;
        List<Office> officeList = null;

        // Modelの生成
        ReservableResourceService service = new ReservableResourceService();

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

        // 選択肢Listの取得
        categoryList = service.getCategoryList();
        officeList = service.getOfficeList();

        List<String> timeList = new ArrayList<String>();
        int hour = 0;
        int minute = 0;
        while (hour < 24 || minute < 15) {
            timeList.add(String.format("%02d:%02d", hour, minute));
            minute += 15;
            if (minute == 60) {
                minute = 0;
                hour += 1;
            }
        }

        request.setAttribute("categoryList", categoryList);
        request.setAttribute("officeList", officeList);
        request.setAttribute("timeList", timeList);

        return CTRL_RSV_104;
    }

}
