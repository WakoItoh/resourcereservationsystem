package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rrs.dao.DataAccessException;
import rrs.dto.Reservation;
import rrs.dto.Resource;
import rrs.dto.User;
import rrs.model.ReservationRegisterService;

public class ReservationRegisterNowHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        // 画面入力があるので、パラメータを取得し、チェックを実行
        String paramUseStartDate = request.getParameter("use_start_date");
        String paramUseStartTime = request.getParameter("use_start_time");
        String paramUseEndTime = request.getParameter("use_end_time");
        String paramResource = request.getParameter("resource");
        String paramMeetingName = request.getParameter("meeting_name");
        String paramReservator = request.getParameter("reservator");
        String paramAttendanceCount = request.getParameter("attendance_count");

        // 入力データを保持
        request.setAttribute("paramUseStartDate", paramUseStartDate);
        request.setAttribute("paramUseStartTime", paramUseStartTime);
        request.setAttribute("paramUseEndTime", paramUseEndTime);
        request.setAttribute("paramResource", paramResource);
        request.setAttribute("paramMeetingName", paramMeetingName);
        request.setAttribute("paramReservator", paramReservator);
        request.setAttribute("paramAttendanceCount", paramAttendanceCount);

        Reservation reservation = new Reservation();

        List<String> errorMessageList = new ArrayList<String>();
        CommonValidator commonValidator = new CommonValidator();

        // 利用開始日時のチェック
        if (commonValidator.notSetOn(paramUseStartDate) || commonValidator.notSetOn(paramUseStartTime) || commonValidator.notSetOn(paramUseEndTime)) {
            errorMessageList.add(EM_RSV_006);
        } else {
            if (commonValidator.notValidDate(paramUseStartDate)) {
                errorMessageList.add(EM_RSV_007);
            } else {
                if (commonValidator.notValidDate(paramUseStartDate, paramUseStartTime)) {
                    errorMessageList.add(EM_RSV_009);
                } else {
                    reservation.setUseStart(commonValidator.getDateVal());
                }
                if (commonValidator.notValidDateExcept24(paramUseStartDate, paramUseEndTime)) {
                    errorMessageList.add(EM_RSV_010);
                } else {
                    reservation.setUseEnd(commonValidator.getDateVal());
                }
            }
        }

        // リソースのチェック
        if (commonValidator.notSetOn(paramResource)) {
            errorMessageList.add(EM_RSV_017);
        } else {
            if (commonValidator.notNumericOn(paramResource)) {
                errorMessageList.add(EM_RSV_017);
            } else {
                reservation.setResource(new Resource(commonValidator.getIntVal(), null, null, 0, null, null, null, null, null, 0, 0));
            }
        }

        // 会議名のチェック
        if (commonValidator.notSetOn(paramMeetingName)) {
            errorMessageList.add(EM_RSV_019);
        } else {
            reservation.setMeetingName(paramMeetingName);
        }

        // 予約者のチェック
        if (commonValidator.notSetOn(paramReservator)) {
            errorMessageList.add(EM_RSV_021);
        } else {
            reservation.setReservator(new User(paramReservator, null, null, null, 0, null, null));
        }

        // 利用人数のチェック
        if (commonValidator.notSetOn(paramAttendanceCount)) {
            errorMessageList.add(EM_RSV_023);
        } else {
            if (commonValidator.notNumericOn(paramAttendanceCount)) {
                errorMessageList.add(EM_RSV_024);
            } else {
                reservation.setAttendanceCount(commonValidator.getIntVal());
            }
        }

        // チェックOKならModelを生成してvalidate、executeを呼ぶ
        if (errorMessageList.size() > 0) {
            request.setAttribute("errorMessageList", errorMessageList);
            return CTRL_RSV_103;
        }

        // Modelの生成
        ReservationRegisterService service = new ReservationRegisterService(reservation);

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
            reservation.setReservationId(service.getReservationId());
            if (reservation.getReservationId() == 0) {
                errorMessageList.add(EM_RSV_031);
                request.setAttribute("errorMessageList", errorMessageList);
                return CTRL_RSV_103;
            } else if (reservation.getReservationId() == -1) {
                errorMessageList.add(EM_RSV_030);
                request.setAttribute("errorMessageList", errorMessageList);
                return CTRL_RSV_103;
            }
        } else {
            errorMessageList = service.getErrorMessageList();
            request.setAttribute("errorMessageList", errorMessageList);
            return CTRL_RSV_103;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("message", PM_RSV_001);

        return CTRL_RSV_002 + "?reservation_id=" + reservation.getReservationId();
    }

}
