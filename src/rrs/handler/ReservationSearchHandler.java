package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rrs.dao.DataAccessException;
import rrs.dto.Reservation;
import rrs.dto.User;
import rrs.model.ReservationSearchService;

public class ReservationSearchHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User loginUser = (User) session.getAttribute("loginUser");

        // 画面入力があるので、パラメータを取得し、チェックを実行
        Date useStart = null;
        Date useEnd = null;
        String meetingName = null;
        int office = 0;
        boolean allReservator = false;
        boolean ended = false;
        String paramUseStart = request.getParameter("use_start");
        String paramUseEnd = request.getParameter("use_end");
        String paramMeetingName = request.getParameter("meeting_name");
        String paramOffice = request.getParameter("office");
        String paramAllReservator = request.getParameter("all_reservator");
        String paramEnded = request.getParameter("ended");

        boolean searched = false;
        if (paramUseStart != null
                || paramUseEnd != null
                || paramMeetingName != null
                || paramOffice != null
                || paramAllReservator != null
                || paramEnded != null) {
            searched = true;
        }

        // 入力データを保持
        request.setAttribute("paramUseStart", paramUseStart);
        request.setAttribute("paramUseEnd", paramUseEnd);
        request.setAttribute("paramMeetingName", paramMeetingName);
        request.setAttribute("paramOffice", paramOffice);
        request.setAttribute("paramAllReservator", paramAllReservator);
        request.setAttribute("paramEnded", paramEnded);

        List<String> errorMessageList = new ArrayList<String>();
        CommonValidator commonValidator = new CommonValidator();

        // 予約日開始のチェック
        if (!commonValidator.notSetOn(paramUseStart)) {
            if (commonValidator.notValidDate(paramUseStart)) {
                errorMessageList.add(EM_RSV_003);
            } else {
                useStart = commonValidator.getDateVal();
            }
        }

        // 予約日終了のチェック
        if (!commonValidator.notSetOn(paramUseEnd)) {
            if (commonValidator.notValidDate(paramUseEnd)) {
                errorMessageList.add(EM_RSV_004);
            } else {
                useEnd = commonValidator.getDateVal();
            }
        }

        // 会議名
        if (!commonValidator.notSetOn(paramMeetingName)) {
            meetingName = paramMeetingName;
        }

        // 事業所のチェック
        if (!commonValidator.notSetOn(paramOffice)) {
            if (commonValidator.notNumericOn(paramOffice)) {
                errorMessageList.add(EM_RSC_011);
            } else {
                office = commonValidator.getIntVal();
            }
        }

        // 自分以外の予約も表示する
        allReservator = commonValidator.checkOn(paramAllReservator);

        // 終了した予約も表示する
        ended = commonValidator.checkOn(paramEnded);

        // チェックOKならModelを生成してvalidate、executeを呼ぶ
        if (errorMessageList.size() > 0) {
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_RSV_001;
        }

        List<Reservation> reservationList = null;

        // Modelの生成
        ReservationSearchService service = new ReservationSearchService(loginUser, useStart, useEnd, meetingName, office, allReservator, ended);

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
            return PAGE_RSV_001;
        }

        // 処理結果の取得
        reservationList = service.getReservationList();
        if (searched) {
            // 初期表示以外
            if (reservationList == null || reservationList.size() == 0) {
                errorMessageList.add(EM_COMM_003);
                request.setAttribute("errorMessageList", errorMessageList);
            }
        }

        request.setAttribute("reservationList", reservationList);

        return PAGE_RSV_001;
    }

}
