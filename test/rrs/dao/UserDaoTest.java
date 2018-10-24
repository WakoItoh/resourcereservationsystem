package rrs.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import rrs.dto.User;

public class UserDaoTest {

    @Test
    public void testAuthenticate() throws DataAccessException {

        // IDとパスワードが正しいとき、ユーザー情報が正しく取得される
        // 事前条件：insert1.sqlのデータがusersテーブルに挿入されている
        // 期待結果：Userが返却される
        // 期待結果：Userにid="u0000001"/password="pa55w0rd"/userLevel=2のデータが格納されている

        String id = "u0000001";
        String password = "pa55w0rd";

        UserDao dao = new UserDao();
        User loginUser = dao.authenticate(id, password);
        assertThat(loginUser, notNullValue());
        System.out.println(loginUser.getId() + " " + loginUser.getPassword() + " " + loginUser.getUserLevel());
    }

    @Test
    public void testAuthenticateIdNull() throws DataAccessException {

        // IDが正しくないとき、ユーザー情報が取得されない
        // 事前条件：insert.sqlのデータがusersテーブルに挿入されている
        // 期待結果：nullが返却される

        String id = null;
        String password = "pa55w0rd";

        UserDao dao = new UserDao();
        User loginUser = dao.authenticate(id, password);
        assertThat(loginUser, nullValue());
    }

    @Test
    public void testAuthenticatePasswordNull() throws DataAccessException {

        // パスワードが正しくないとき、ユーザー情報が取得されない
        // 事前条件：insert.sqlのデータがusersテーブルに挿入されている
        // 期待結果：nullが返却される

        String id = "u0000001";
        String password = null;

        UserDao dao = new UserDao();
        User loginUser = dao.authenticate(id, password);
        assertThat(loginUser, nullValue());
    }

    @Test
    public void testAuthenticateIdInvalid() throws DataAccessException {

        // IDが正しくないとき、ユーザー情報が取得されない
        // 事前条件：insert.sqlのデータがusersテーブルに挿入されている
        // 期待結果：nullが返却される

        String id = "u0000000";
        String password = "pa55w0rd";

        UserDao dao = new UserDao();
        User loginUser = dao.authenticate(id, password);
        assertThat(loginUser, nullValue());
    }

    @Test
    public void testAuthenticatePasswordInvalid() throws DataAccessException {

        // パスワードが正しくないとき、ユーザー情報が取得されない
        // 事前条件：insert.sqlのデータがusersテーブルに挿入されている
        // 期待結果：nullが返却される

        String id = "u0000001";
        String password = "pa55w0rt";

        UserDao dao = new UserDao();
        User loginUser = dao.authenticate(id, password);
        assertThat(loginUser, nullValue());
    }

    @Test
    public void testAuthenticateUnauthorized() throws DataAccessException {

        // IDとパスワードが正しく、権限が無効(0)のとき、ユーザー情報が取得されない
        // 事前条件：insert.sqlのデータがusersテーブルに挿入されている
        // 期待結果：nullが返却される

        String id = "u0000004";
        String password = "pa55w0rd";

        UserDao dao = new UserDao();
        User loginUser = dao.authenticate(id, password);
        assertThat(loginUser, nullValue());
    }

}
