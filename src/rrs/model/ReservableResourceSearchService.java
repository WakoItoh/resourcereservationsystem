package rrs.model;

import static rrs.handler.MessageHolder.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rrs.dao.AvailableResourceDao;
import rrs.dao.DataAccessException;
import rrs.dto.AvailableResource;

public class ReservableResourceSearchService implements Service {

    // 入力データ
    private Date start;
    private Date end;
    private long hours;
    private String resourceName;
    private int categoryId;
    private int capacity;
    private boolean noCapacity;
    private int officeId;

    // 入力データチェック結果のメッセージ
    private List<String> errorMessageList = new ArrayList<String>();

    // 処理結果
    private List<AvailableResource> availableResourceList;

    public ReservableResourceSearchService(Date start, Date end, long hours, String resourceName, int categoryId, int capacity, boolean noCapacity, int officeId) {
        this.start = start;
        this.end = end;
        this.hours = hours;
        this.resourceName = resourceName;
        this.categoryId = categoryId;
        this.capacity = capacity;
        this.noCapacity = noCapacity;
        this.officeId = officeId;
    }

    @Override
    public boolean validate() {

        // 利用日時のチェック
        // 利用時間数のチェック
        Date now = new Date();
        if (start != null && end != null && hours != 0) {
            if (start.getTime() >= end.getTime()) {
                errorMessageList.add(EM_RSV_011);
            }
            if (now.getTime() >= end.getTime()) {
                errorMessageList.add(EM_RSV_012);
            }
            if (hours > (end.getTime() - start.getTime())) {
                errorMessageList.add(EM_RSV_015);
            }
        }

        // 定員のチェック
        if (capacity < 0 || capacity > 999) {
            errorMessageList.add(EM_RSC_009);
        }

        if (errorMessageList.size() > 0) {
            return false;
        }

        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        AvailableResourceDao dao = new AvailableResourceDao();

        availableResourceList = dao.query(start, end, hours, resourceName, categoryId, capacity, noCapacity, officeId);
    }

    public List<String> getErrorMessageList() {
        return errorMessageList;
    }

    public List<AvailableResource> getAvailableResourceList() {
        return availableResourceList;
    }

}
