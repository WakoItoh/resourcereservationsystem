package rrs.model;

import static rrs.handler.MessageHolder.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rrs.dao.DataAccessException;
import rrs.dao.ReservationDao;
import rrs.dto.Reservation;
import rrs.dto.User;

public class ReservationSearchService implements Service {

    // 参照データ
    private User loginUser;

    // 入力データ
    private Date useStart;
    private Date useEnd;
    private String meetingName;
    private int office;
    private boolean allReservator;
    private boolean ended;

    // 入力データチェック結果のメッセージ
    private List<String> errorMessageList = new ArrayList<String>();

    // 処理結果
    private List<Reservation> reservationList;

    public ReservationSearchService(User loginUser, Date useStart, Date useEnd, String meetingName, int office, boolean allReservator, boolean ended) {
        this.loginUser = loginUser;
        this.useStart = useStart;
        this.useEnd = useEnd;
        this.meetingName = meetingName;
        this.office = office;
        this.allReservator = allReservator;
        this.ended = ended;
    }

    @Override
    public boolean validate() {

        // 予約日開始のチェック
        // 予約日終了のチェック
        if (useStart != null && useEnd != null) {
            if (useStart.getTime() > useEnd.getTime()) {
                errorMessageList.add(EM_RSV_005);
            }
        }

        if (errorMessageList.size() > 0) {
            return false;
        }

        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        String reservatorId = null;
        if (!allReservator) {
            reservatorId = loginUser.getId();
        }

        boolean notEnded = !ended;

        ReservationDao dao = new ReservationDao();

        reservationList = dao.query(useStart, useEnd, meetingName, office, reservatorId, notEnded);
    }

    public List<String> getErrorMessageList() {
        return errorMessageList;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

}
