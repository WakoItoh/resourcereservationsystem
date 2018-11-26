package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rrs.dao.DataAccessException;
import rrs.dto.AttendanceType;
import rrs.dto.AvailableResource;
import rrs.dto.Reservation;
import rrs.dto.User;
import rrs.model.ReservationEditorService;

public class ReservationEditorHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User loginUser = (User) session.getAttribute("loginUser");

        // URLパラメータ・画面入力があるので、パラメータを取得し、チェックを実行
        int reservationId = 0;
        Date useStart = null;
        Date useEnd = null;
        int resourceId = 0;
        String paramReservationId = request.getParameter("reservation_id");
        String paramUseStartDate = request.getParameter("use_start_date");
        //String paramUseStartTime = request.getParameter("use_start_time");
        //String paramUseEndTime = request.getParameter("use_end_time");
        String paramResource = request.getParameter("resource");

        boolean edited = false;
        if (request.getAttribute("errorMessageList") != null) {
            edited = true;
        }

        List<String> errorMessageList = new ArrayList<String>();
        CommonValidator commonValidator = new CommonValidator();

        // 予約IDのチェック
        if (commonValidator.notSetOn(paramReservationId)) {
            errorMessageList.add(EM_RSV_001);
        } else {
            if (commonValidator.notNumericOn(paramReservationId)) {
                errorMessageList.add(EM_RSV_001);
            } else {
                reservationId = commonValidator.getIntVal();
            }
        }

        // 利用日のチェック
        if (commonValidator.notSetOn(paramUseStartDate)) {
            //errorMessageList.add(EM_RSV_016);
        } else {
            if (commonValidator.notValidDate(paramUseStartDate, CommonValidator.TIME_00)) {
                //errorMessageList.add(EM_RSV_016);
            } else {
                useStart = commonValidator.getDateVal();
            }
            if (commonValidator.notValidDateExcept24(paramUseStartDate, CommonValidator.TIME_24)) {
                //errorMessageList.add(EM_RSV_016);
            } else {
                useEnd = commonValidator.getDateVal();
            }
        }

        // リソースIDのチェック
        if (commonValidator.notSetOn(paramResource)) {
            //errorMessageList.add(EM_RSV_017);
        } else {
            if (commonValidator.notNumericOn(paramResource)) {
                //errorMessageList.add(EM_RSV_017);
            } else {
                resourceId = commonValidator.getIntVal();
            }
        }

        // チェックOKならModelを生成してvalidate、executeを呼ぶ
        if (errorMessageList.size() > 0) {
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_RSV_002;
        }

        Reservation reservation = null;
        List<AvailableResource> availableResourceList = null;
        List<User> userList = null;
        List<AttendanceType> attendanceTypeList = null;

        // Modelの生成
        ReservationEditorService service = new ReservationEditorService(loginUser, reservationId, useStart, useEnd, resourceId);

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
            errorMessageList.add(EM_RSV_002);
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_RSV_002;
        }
        if (!edited) {
            // 初期表示
            request.setAttribute("paramUseStartDate", reservation.getUseStartDate());
            request.setAttribute("paramUseStartTime", reservation.getUseStartTime());
            request.setAttribute("paramUseEndTime", reservation.getUseEndTime());
            //request.setAttribute("paramResource", reservation.getResource().getResourceId());
            request.setAttribute("paramMeetingName", reservation.getMeetingName());
            //request.setAttribute("paramReservator", reservation.getReservator().getId());
            if (reservation.getCoReservator() != null) {
                request.setAttribute("paramCoReservator", reservation.getCoReservator().getId());
            }
            request.setAttribute("paramAttendanceCount", String.valueOf(reservation.getAttendanceCount()));
            if (reservation.getAttendanceType() != null) {
                request.setAttribute("paramAttendanceType", reservation.getAttendanceType().getAttendanceTypeId());
            }
            request.setAttribute("paramNote", reservation.getNote());
        }

        request.setAttribute("reservation", reservation);

        // 選択肢Listの取得
        availableResourceList = service.getAvailableResourceList();
        userList = service.getUserList();
        attendanceTypeList = service.getAttendanceTypeList();

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

        request.setAttribute("timeList", timeList);
        request.setAttribute("availableResourceList", availableResourceList);
        request.setAttribute("userList", userList);
        request.setAttribute("attendanceTypeList", attendanceTypeList);

        return PAGE_RSV_201;
    }

}
