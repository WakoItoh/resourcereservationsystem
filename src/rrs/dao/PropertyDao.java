package rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rrs.dto.Property;

public class PropertyDao {

    public List<Property> query() throws DataAccessException {

        String sql = "select property_id, property_name from properties order by property_id";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Property> propertyList = new ArrayList<Property>();

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery();

                // 取得した特性情報を1件ずつ
                Property property = null;
                while (rs.next()) {
                    property = new Property(rs.getInt("property_id"), rs.getString("property_name"));

                    propertyList.add(property);
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

        return propertyList;
    }

}
