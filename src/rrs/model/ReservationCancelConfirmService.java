package rrs.model;

import rrs.dao.DataAccessException;
import rrs.dao.ReservationDao;
import rrs.dto.Reservation;
import rrs.dto.User;

public class ReservationCancelConfirmService implements Service {

    // 参照データ
    private User loginUser;

    // 入力データ
    private int reservationId;

    // 入力データチェック結果のメッセージ
    // なし

    // 処理結果
    private Reservation reservation;

    public ReservationCancelConfirmService(User loginUser, int reservationId) {
        this.loginUser = loginUser;
        this.reservationId = reservationId;
    }


    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        ReservationDao dao = new ReservationDao();

        reservation = dao.queryByReservationId(reservationId, loginUser.getId());
    }

    public Reservation getReservation() {
        return reservation;
    }

}
