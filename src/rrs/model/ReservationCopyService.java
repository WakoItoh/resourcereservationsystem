package rrs.model;

import rrs.dao.DataAccessException;
import rrs.dao.ReservationDao;
import rrs.dto.Reservation;

public class ReservationCopyService implements Service {

    // 入力データ
    private int reservationId;

    // 入力データチェック結果のメッセージ
    // なし

    // 処理結果
    private Reservation reservation;

    public ReservationCopyService(int reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        ReservationDao dao = new ReservationDao();

        reservation = dao.queryByReservationId(reservationId);
    }

    public Reservation getReservation() {
        return reservation;
    }

}
