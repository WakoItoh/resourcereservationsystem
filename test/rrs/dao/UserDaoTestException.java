package rrs.dao;

import org.junit.Test;

public class UserDaoTestException {

    @Test(expected = DataAccessException.class)
    public void testAuthenticate() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

        String id = "u0000001";
        String password = "pa55w0rd";
        //int userLevel = 2;

        UserDao dao = new UserDao();
        dao.authenticate(id, password);
    }

}
