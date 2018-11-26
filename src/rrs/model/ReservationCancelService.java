package rrs.model;

import rrs.dao.DataAccessException;
import rrs.dao.ReservationDao;
import rrs.dto.User;

public class ReservationCancelService implements Service {

    // 参照データ
    private User loginUser;

    // 入力データ
    private int reservationId;

    // 入力データチェック結果のメッセージ
    // なし

    // 処理結果
    private int count;

    public ReservationCancelService(User loginUser, int reservationId) {
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

        count = dao.delete(reservationId, loginUser.getId());
    }

    public int getCount() {
        return count;
    }

}
