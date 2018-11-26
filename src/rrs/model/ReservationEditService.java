package rrs.model;

import static rrs.handler.MessageHolder.*;

import java.util.ArrayList;
import java.util.List;

import rrs.dao.AvailableResourceDao;
import rrs.dao.DataAccessException;
import rrs.dao.ReservationDao;
import rrs.dto.AvailableResource;
import rrs.dto.Reservation;
import rrs.dto.User;

public class ReservationEditService implements Service {

    // 参照データ
    private User loginUser;

    // 入力データ
    private Reservation reservation;

    // 入力データチェック結果のメッセージ
    private List<String> errorMessageList = new ArrayList<String>();

    // 処理結果
    private int count;

    public ReservationEditService(User loginUser, Reservation reservation) {
        this.loginUser = loginUser;
        this.reservation = reservation;
    }

    @Override
    public boolean validate() {

        if (reservation == null) {
            errorMessageList.add(EM_RSV_032);
        }

        // 利用日時のチェック
        if (reservation.getUseStart() != null && reservation.getUseEnd() != null) {
            if (reservation.getUseStart().getTime() >= reservation.getUseEnd().getTime()) {
                errorMessageList.add(EM_RSV_011);
            }
        }

        // 会議名のチェック
        if (reservation.getMeetingName() != null) {
            if (reservation.getMeetingName().length() > 30) {
                errorMessageList.add(EM_RSV_020);
            }
        }

        // 予約者のチェック
        if (reservation.getReservator() != null) {
            if (reservation.getReservator().getId().length() > 8 ) {
                errorMessageList.add(EM_RSV_021);
            }
        }

        // 共同予約者のチェック
        if (reservation.getCoReservator() != null) {
            if (reservation.getCoReservator().getId().length() > 8 ) {
                errorMessageList.add(EM_RSV_022);
            }
        }

        // 利用人数のチェック
        if (reservation.getAttendanceCount() < 0 || reservation.getAttendanceCount() > 999) {
            errorMessageList.add(EM_RSV_025);
        }

        // 補足のチェック
        if (reservation.getNote() != null) {
            if (reservation.getNote().length() > 500) {
                errorMessageList.add(EM_RSV_027);
            }
        }

        if (errorMessageList.size() > 0) {
            return false;
        }

        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        long useHours = reservation.getUseEnd().getTime() - reservation.getUseStart().getTime();

        AvailableResourceDao aResourceDao = new AvailableResourceDao();

        List<AvailableResource> availableResourceList = aResourceDao.query(reservation.getUseStart(), reservation.getUseEnd(), useHours, reservation.getResource().getResourceId());

        if (availableResourceList == null || availableResourceList.size() == 0) {
            count = -1;
        } else {
            ReservationDao dao = new ReservationDao();

            count = dao.edit(reservation, loginUser.getId());
        }
    }

    public List<String> getErrorMessageList() {
        return errorMessageList;
    }

    public int getCount() {
        return count;
    }

}
