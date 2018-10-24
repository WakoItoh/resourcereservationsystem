package rrs.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import rrs.dao.DataAccessException;
import rrs.dto.User;

public class LoginServiceTest {

    @Test
    public void testValidate() {

        // 入力データチェック結果が取得される
        // 期待結果：trueが返却される

        String id = "u0000001";
        String password = "pa55w0rd";

        LoginService service = new LoginService(id, password);
        assertThat(service.validate(), is(true));
    }

    @Test
    public void testExecute() throws DataAccessException {

        // IDとパスワードが正しいとき、フィールドにユーザー情報が正しくセットされる
        // 事前条件：insert1.sqlのデータがusersテーブルに挿入されている
        // 期待結果：getterの結果、id="u0000001"/password="pa55w0rd"/userLevel=2のUserが返却される

        String id = "u0000001";
        String password = "pa55w0rd";

        LoginService service = new LoginService(id, password);
        service.execute();
        User loginUser = service.getLoginUser();
        assertThat(loginUser, notNullValue());
        System.out.println(loginUser.getId() + " " + loginUser.getPassword() + " " + loginUser.getUserLevel());
    }

    @Test
    public void testExecuteInvalid() throws DataAccessException {

        // IDまたはパスワードが正しくないとき、フィールドにユーザー情報がセットされない
        // 事前条件：insert1.sqlのデータがusersテーブルに挿入されている
        // 期待結果：getterの結果、nullが返却される

        String id = "u0000000";
        String password = "pa55w0rd";

        LoginService service = new LoginService(id, password);
        service.execute();
        User loginUser = service.getLoginUser();
        assertThat(loginUser, nullValue());
    }

}
