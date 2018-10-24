package rrs.model;

import static rrs.handler.MessageHolder.*;

import java.util.ArrayList;
import java.util.List;

import rrs.dao.DataAccessException;
import rrs.dao.ResourceDao;
import rrs.dto.Resource;

public class ResourceEditService implements Service {

    // 入力データ
    private Resource resource;

    // 入力データチェック結果のメッセージ
    private List<String> errorMessageList = new ArrayList<String>();

    // 処理結果
    private int count;

    public ResourceEditService(Resource resource) {
        this.resource = resource;
    }

    @Override
    public boolean validate() {

        if (resource == null) {
            errorMessageList.add(EM_RSC_022);
            return false;
        }

        // リソース名のチェック
        if (resource.getResourceName().length() > 30) {
            errorMessageList.add(EM_RSC_004);
        }

        // 定員のチェック
        if (resource.getCapacity() < 0 || resource.getCapacity() > 999) {
            errorMessageList.add(EM_RSC_009);
        }
        // 補足のチェック
        if (resource.getNote().length() > 500) {
            errorMessageList.add(EM_RSC_013);
        }

        // 利用停止開始日時のチェック
        // 利用停止終了日時のチェック
        if (resource.getSuspendStart() != null && resource.getSuspendEnd() != null) {
            if (resource.getSuspendStart().getTime() >= resource.getSuspendEnd().getTime()) {
                errorMessageList.add(EM_RSC_020);
            }
        } else if (resource.getSuspendStart() == null && resource.getSuspendEnd() != null) {
            errorMessageList.add(EM_RSC_016);
        } else if (resource.getSuspendStart() != null && resource.getSuspendEnd() == null) {
            errorMessageList.add(EM_RSC_019);
        }

        if (errorMessageList.size() > 0) {
            return false;
        }

        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        ResourceDao dao = new ResourceDao();

        count = dao.edit(resource);

    }

    public List<String> getErrorMessageList() {
        return errorMessageList;
    }

    public int getCount() {
        return count;
    }

}
