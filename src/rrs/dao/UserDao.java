package rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rrs.dto.User;

public class UserDao {

    /**
     * IDとパスワードによりユーザーの認証を行う
     *
     * @param id ID
     * @param password パスワード
     * @return ログインユーザー情報
     * @throws DataAccessException
     */
    public User authenticate(String id, String password) throws DataAccessException {

        String sql = "select * from users where id = ? and password = ? and user_level > 0";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        User loginUser = null;

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, id);
                pstmt.setString(2, password);
                rs = pstmt.executeQuery();

                // 取得したユーザー情報1件
                if(rs.next()) {
                    loginUser = new User(
                            rs.getString("id"),
                            rs.getString("password"),
                            rs.getString("last_name"),
                            rs.getString("first_name"),
                            rs.getInt("user_level"),
                            rs.getString("phone"),
                            rs.getString("email"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataAccessException("queryの実行に失敗しました。", e);
            } finally {
                // PreparedStatementのクローズ
                try {
                    dbHelper.closeResource(pstmt);
                } catch (Exception e) {
                    // SQLException以外の例外が発生
                    e.printStackTrace();
                    // TODO LOG
                }
                // ResultSetのクローズ
                try {
                    dbHelper.closeResource(rs);
                } catch (Exception e) {
                    // SQLException以外の例外が発生
                    e.printStackTrace();
                    // TODO LOG
                }

                dbHelper.closeDb();
            }
        } else {
            throw new DataAccessException("DB接続に失敗しました");
        }

        return loginUser;
    }
}
