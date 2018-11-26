package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import rrs.dao.DataAccessException;
import rrs.dto.Office;
import rrs.model.ReservationService;

public class ReservationHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        List<String> errorMessageList = new ArrayList<String>();

        List<Office> officeList = null;

        // Modelの生成
        ReservationService service = new ReservationService();

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
        officeList = service.getOfficeList();

        request.setAttribute("officeList", officeList);

        return CTRL_RSV_003;
    }

}
