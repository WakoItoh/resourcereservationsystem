package rrs.model;

import java.util.Date;
import java.util.List;

import rrs.dao.AttendanceTypeDao;
import rrs.dao.AvailableResourceDao;
import rrs.dao.DataAccessException;
import rrs.dao.ResourceDao;
import rrs.dao.UserDao;
import rrs.dto.AttendanceType;
import rrs.dto.AvailableResource;
import rrs.dto.Resource;
import rrs.dto.User;

public class ReservationRegistratorService implements Service {

    // 入力データ
    private Date useStart;
    private Date useEnd;
    private int resourceId;

    // 入力データチェック結果のメッセージ
    // なし

    // 処理結果
    private Resource resource;
    private List<AvailableResource> availableResourceList;
    private List<User> userList;
    private List<AttendanceType> attendanceTypeList;

    public ReservationRegistratorService(Date useStart, Date useEnd, int resourceId) {
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

        ResourceDao resourceDao = new ResourceDao();

        resource = resourceDao.queryByResourceId(resourceId, true);

        AvailableResourceDao availableResourceDao = new AvailableResourceDao();

        availableResourceList = availableResourceDao.query(useStart, useEnd, useHours, resourceId);

        UserDao userDao = new UserDao();

        userList = userDao.query();

        AttendanceTypeDao attendanceTypeDao = new AttendanceTypeDao();

        attendanceTypeList = attendanceTypeDao.query();
    }

    public Resource getResource() {
        return resource;
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
