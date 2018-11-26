package rrs.model;

import static rrs.handler.MessageHolder.*;

import java.util.ArrayList;
import java.util.List;

import rrs.dao.DataAccessException;
import rrs.dao.OfficeDao;
import rrs.dto.AvailableResource;
import rrs.dto.Office;

public class ReservationNowService implements Service {

    // 参照データ
    private AvailableResource availableResource;

    // チェック結果のメッセージ
    private List<String> errorMessageList = new ArrayList<String>();

    // 処理結果
    private List<Office> officeList;

    public ReservationNowService(AvailableResource availableResource) {
        this.availableResource = availableResource;
    }

    @Override
    public boolean validate() {

        // 利用日時のチェック
        if (!(availableResource.getAvailableStartDate()).equals(availableResource.getAvailableEndDate())) {
            errorMessageList.add(EM_RSV_028);
        }

        if (errorMessageList.size() > 0) {
            return false;
        }

        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        OfficeDao officeDao = new OfficeDao();

        officeList = officeDao.query();
    }

    public List<String> getErrorMessageList() {
        return errorMessageList;
    }

    public List<Office> getOfficeList() {
        return officeList;
    }

}
