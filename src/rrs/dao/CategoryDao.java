package rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rrs.dto.Category;

public class CategoryDao {

    /**
     * カテゴリ情報を取得する
     * @return カテゴリ情報のList
     * @throws DataAccessException
     */
    public List<Category> query() throws DataAccessException {

        String sql = "select category_id, category_name from categories order by category_id";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Category> categoryList = new ArrayList<Category>();

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery();

                // 取得したカテゴリ情報を1件ずつ
                Category category = null;
                while (rs.next()) {
                    category = new Category(rs.getInt("category_id"), rs.getString("category_name"));

                    categoryList.add(category);
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

        return categoryList;
    }

}
