package rrs.model;

import java.util.List;

import rrs.dao.DataAccessException;
import rrs.dao.ResourceDao;
import rrs.dto.Resource;
import rrs.dto.User;

public class ResourceService implements Service {

    // 参照データ
    private User loginUser;

    // 処理結果
    private List<Resource> resourceList;

    public ResourceService(User loginUser) {
        this.loginUser = loginUser;
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

        resourceList = dao.query(notDeleted);
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

}
