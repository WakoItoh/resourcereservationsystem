package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import rrs.dao.DataAccessException;
import rrs.dto.AvailableResource;
import rrs.dto.Office;
import rrs.model.ReservationNowService;

public class ReservationNowHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        AvailableResource availableResource = new AvailableResource();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, (int) (calendar.get(Calendar.MINUTE) / 15) * 15);
        availableResource.setAvailableStart(calendar.getTime());
        calendar.add(Calendar.MINUTE, 30);
        availableResource.setAvailableEnd(calendar.getTime());

        List<String> errorMessageList = new ArrayList<String>();

        List<Office> officeList = null;

        // Modelの生成
        ReservationNowService service = new ReservationNowService(availableResource);

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
            return CTRL_RSV_101;
        }

        // 選択肢Listの取得
        officeList = service.getOfficeList();

        request.setAttribute("officeList", officeList);
        request.setAttribute("availableResource", availableResource);

        return CTRL_RSV_105;
    }

}
