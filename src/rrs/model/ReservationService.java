package rrs.model;

import java.util.List;

import rrs.dao.DataAccessException;
import rrs.dao.OfficeDao;
import rrs.dto.Office;

public class ReservationService implements Service {

    // 処理結果
    private List<Office> officeList;

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        OfficeDao officeDao = new OfficeDao();

        officeList = officeDao.query();
    }

    public List<Office> getOfficeList() {
        return officeList;
    }

}
