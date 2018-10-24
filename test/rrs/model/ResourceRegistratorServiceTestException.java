package rrs.model;

import org.junit.Test;

import rrs.dao.DataAccessException;

public class ResourceRegistratorServiceTestException {

    @Test(expected = DataAccessException.class)
    public void testExecute() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

        ResourceRegistratorService service = new ResourceRegistratorService();
        service.execute();
    }

}
