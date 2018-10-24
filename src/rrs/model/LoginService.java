package rrs.model;

import rrs.dao.DataAccessException;
import rrs.dao.UserDao;
import rrs.dto.User;

public class LoginService implements Service {

    // 入力データ
    private String id;
    private String password;

    // 入力データチェック結果のメッセージ
    // なし

    // 処理結果
    private User loginUser;

    public LoginService(String id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        UserDao dao = new UserDao();

        loginUser = dao.authenticate(id, password);
    }

    public User getLoginUser() {
        return loginUser;
    }

}
