package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import rrs.dao.DataAccessException;
import rrs.dto.Reservation;
import rrs.model.ReservationCopyService;

public class ReservationCopyHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        // URLパラメータ入力があるので、パラメータを取得し、チェックを実行
        int reservationId = 0;
        String paramReservationId = request.getParameter("reservation_id");

        List<String> errorMessageList = new ArrayList<String>();
        CommonValidator commonValidator = new CommonValidator();

        // 予約IDのチェック
        if (commonValidator.notSetOn(paramReservationId)) {
            errorMessageList.add(EM_RSV_029);
        } else {
            if (commonValidator.notNumericOn(paramReservationId)) {
                errorMessageList.add(EM_RSV_029);
            } else {
                reservationId = commonValidator.getIntVal();
            }
        }

        // チェックOKならModelを生成してvalidate、executeを呼ぶ
        if (errorMessageList.size() > 0) {
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_RSV_002;
        }

        Reservation reservation = null;

        // Modelの生成
        ReservationCopyService service = new ReservationCopyService(reservationId);

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
        reservation = service.getReservation();
        if (reservation == null) {
            errorMessageList.add(EM_RSV_029);
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_RSV_002;
        } else {
            request.setAttribute("paramUseStartDate", reservation.getUseStartDate());
            request.setAttribute("paramUseStartTime", reservation.getUseStartTime());
            request.setAttribute("paramUseEndTime", reservation.getUseEndTime());
            request.setAttribute("paramResource", String.valueOf(reservation.getResource().getResourceId()));
            request.setAttribute("paramMeetingName", reservation.getMeetingName());
            request.setAttribute("paramCoReservator", String.valueOf(reservation.getCoReservator().getId()));
            request.setAttribute("paramAttendanceCount", String.valueOf(reservation.getAttendanceCount()));
            request.setAttribute("paramAttendanceType", String.valueOf(reservation.getAttendanceType().getAttendanceTypeId()));
            request.setAttribute("paramNote", reservation.getNote());
        }

        request.setAttribute("reservation", reservation);

        return CTRL_RSV_102;
    }

}
