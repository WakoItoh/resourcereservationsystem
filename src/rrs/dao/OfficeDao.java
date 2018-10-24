package rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rrs.dto.Office;

public class OfficeDao {

    public List<Office> query() throws DataAccessException {

        String sql = "select office_id, office_name, office_location from offices order by office_id";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Office> officeList = new ArrayList<Office>();

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery();

                // 取得した事業所情報を1件ずつ
                Office office = null;
                while (rs.next()) {
                    office = new Office(rs.getInt("office_id"), rs.getString("office_name"), rs.getString("office_location"));

                    officeList.add(office);
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

        return officeList;
    }

}
