package rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * ユーザー情報を取得する
     *
     * @return ユーザー情報のList
     * @throws DataAccessException
     */
    public List<User> query() throws DataAccessException {

        String sql = "select id, last_name, first_name, user_level, phone, email from users where user_level > 0 order by id";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<User> userList = new ArrayList<User>();

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery();

                // 取得した参加者種別情報を1件ずつ
                User user = null;
                while (rs.next()) {
                    user = new User(
                            rs.getString("id"),
                            null,
                            rs.getString("last_name"),
                            rs.getString("first_name"),
                            rs.getInt("user_level"),
                            rs.getString("phone"),
                            rs.getString("email"));

                    userList.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataAccessException("queryの実行に失敗しました。", e);
            } finally {
                // ResultSetのクローズ
                try {
                    dbHelper.closeResource(rs);
                } catch (Exception e) {
                    // SQLException以外の例外が発生
                    e.printStackTrace();
                    // TODO LOG
                }
                // PreparedStatementのクローズ
                try {
                    dbHelper.closeResource(pstmt);
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

        return userList;
    }
}
