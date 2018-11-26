package rrs.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rrs.dao.AttendanceTypeDao;
import rrs.dao.AvailableResourceDao;
import rrs.dao.DataAccessException;
import rrs.dao.ReservationDao;
import rrs.dao.UserDao;
import rrs.dto.AttendanceType;
import rrs.dto.AvailableResource;
import rrs.dto.Reservation;
import rrs.dto.User;

public class ReservationEditorService implements Service {

    // 参照データ
    private User loginUser;

    // 入力データ
    private int reservationId;
    private Date useStart;
    private Date useEnd;
    private int resourceId;

    // 入力データチェック結果のメッセージ
    // なし

    // 処理結果
    private Reservation reservation;
    private List<AvailableResource> availableResourceList;
    private List<User> userList;
    private List<AttendanceType> attendanceTypeList;

    public ReservationEditorService(User loginUser, int reservationId, Date useStart, Date useEnd, int resourceId) {
        this.loginUser = loginUser;
        this.reservationId = reservationId;
        this.useStart = useStart;
        this.useEnd = useEnd;
        this.resourceId = resourceId;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        long useHours = 15 * 60 * 1000;

        ReservationDao dao = new ReservationDao();

        reservation = dao.queryByReservationId(reservationId, loginUser.getId());

        if (useStart == null || useStart == null) {
            if (reservation != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(reservation.getUseStart());
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                useStart = calendar.getTime();
                calendar.add(Calendar.HOUR, 24);
                useEnd = calendar.getTime();
            }
        }
        if (resourceId == 0) {
            if (reservation != null) {
                resourceId = reservation.getResource().getResourceId();
            }
        }

        AvailableResourceDao availableResourceDao = new AvailableResourceDao();

        availableResourceList = availableResourceDao.query(useStart, useEnd, useHours, resourceId);

        UserDao userDao = new UserDao();

        userList = userDao.query();

        AttendanceTypeDao attendanceTypeDao = new AttendanceTypeDao();

        attendanceTypeList = attendanceTypeDao.query();
    }

    public Reservation getReservation() {
        return reservation;
    }

    public List<AvailableResource> getAvailableResourceList() {
        return availableResourceList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<AttendanceType> getAttendanceTypeList() {
        return attendanceTypeList;
    }

}
