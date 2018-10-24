package rrs.model;

import org.junit.Test;

import rrs.dao.DataAccessException;

public class ResourceDeleteConfirmServiceTestException {

    @Test(expected = DataAccessException.class)
    public void testExecute() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

        int resourceId = 1;

        ResourceDeleteConfirmService service = new ResourceDeleteConfirmService(resourceId);
        service.execute();
    }

}
