package rrs.model;

import rrs.dao.DataAccessException;
import rrs.dao.ResourceDao;
import rrs.dto.Resource;
import rrs.dto.User;

public class ResourceDetailService implements Service {

    // 参照データ
    private User loginUser;

    // 入力データ
    private int resourceId;

    // 入力データチェック結果のメッセージ
    // なし

    // 処理結果
    private Resource resource;

    public ResourceDetailService(User loginUser, int resourceId) {
        this.loginUser = loginUser;
        this.resourceId = resourceId;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        boolean notDeleted = true;
        if (loginUser.getUserLevel() == 2) {
            notDeleted = false;
        }

        ResourceDao dao = new ResourceDao();

        resource = dao.queryByResourceId(resourceId, notDeleted);
    }

    public Resource getResource() {
        return resource;
    }

}
