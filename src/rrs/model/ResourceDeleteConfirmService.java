package rrs.model;

import java.util.Date;
import java.util.List;

import rrs.dao.DataAccessException;
import rrs.dao.ReservationDao;
import rrs.dao.ResourceDao;
import rrs.dto.Reservation;
import rrs.dto.Resource;

public class ResourceDeleteConfirmService implements Service {

    // 入力データ
    private int resourceId;

    // 入力データチェック結果のメッセージ
    // なし

    // 処理結果
    private Resource resource;
    private List<Reservation> reservationList;

    public ResourceDeleteConfirmService(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        ResourceDao dao = new ResourceDao();

        resource = dao.queryByResourceId(resourceId, true);

        Date now = new Date();

        ReservationDao reservationDao = new ReservationDao();

        reservationList = reservationDao.query(now, null, resourceId);
    }

    public Resource getResource() {
        return resource;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

}
