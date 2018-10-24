package rrs.model;

import rrs.dao.DataAccessException;
import rrs.dao.ResourceDao;

public class ResourceDeleteService implements Service {

    // 入力データ
    private int resourceId;

    // 入力データチェック結果のメッセージ
    // なし

    // 処理結果
    private int count;

    public ResourceDeleteService(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        ResourceDao dao = new ResourceDao();

        count = dao.editToDeleted(resourceId);

    }

    public int getCount() {
        return count;
    }

}
