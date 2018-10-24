package rrs.model;

import rrs.dao.DataAccessException;
import rrs.dao.ResourceDao;
import rrs.dto.Resource;

public class ResourceDeleteConfirmService implements Service {

    // 入力データ
    private int resourceId;

    // 入力データチェック結果のメッセージ
    // なし

    // 処理結果
    private Resource resource;

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
    }

    public Resource getResource() {
        return resource;
    }

}
