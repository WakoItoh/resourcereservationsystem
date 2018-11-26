package rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rrs.dto.AttendanceType;

public class AttendanceTypeDao {

    /**
     * 参加者種別情報を取得する
     * @return 参加者種別情報のList
     * @throws DataAccessException
     */
    public List<AttendanceType> query() throws DataAccessException {

        String sql = "select attendance_type_id, attendance_type_name from attendance_types order by attendance_type_id";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<AttendanceType> attendanceTypeList = new ArrayList<AttendanceType>();

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery();

                // 取得した参加者種別情報を1件ずつ
                AttendanceType attendanceType = null;
                while (rs.next()) {
                    attendanceType = new AttendanceType(rs.getInt("attendance_type_id"), rs.getString("attendance_type_name"));

                    attendanceTypeList.add(attendanceType);
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

        return attendanceTypeList;
    }

}
