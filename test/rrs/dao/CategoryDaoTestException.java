package rrs.dao;

import org.junit.Test;

public class CategoryDaoTestException {

    @Test(expected = DataAccessException.class)
    public void testQuery() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

        CategoryDao dao = new CategoryDao();
        dao.query();
    }

}
