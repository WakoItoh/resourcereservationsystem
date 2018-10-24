package rrs.model;

import org.junit.Test;

import rrs.dao.DataAccessException;
import rrs.dto.User;

public class ResourceDetailServiceTestException {

    @Test(expected = DataAccessException.class)
    public void testExecute() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

        String id = "u0000001";
        String password = "pa55w0rd";
        String lastName = "仲町台";
        String firstName = "太郎";
        int userLevel = 2;
        String phone = "0123456789";
        String email = "u0000001@example.com";
        User loginUser = new User(id, password, lastName, firstName, userLevel, phone, email);
        int resourceId = 2;

        ResourceDetailService service = new ResourceDetailService(loginUser, resourceId);
        service.execute();
    }

}
