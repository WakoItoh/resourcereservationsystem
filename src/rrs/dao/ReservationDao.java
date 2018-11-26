package rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rrs.dto.AttendanceType;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Reservation;
import rrs.dto.Resource;
import rrs.dto.User;

public class ReservationDao {

    /**
     * 予約情報を取得する
     *
     * @return 予約情報のList
     * @throws DataAccessException
     */
    public List<Reservation> query() throws DataAccessException {

        String sql = "select reservation_id, use_start, use_end, meeting_name, attendance_count, reservations.note,"
                + " resources.resource_id, resources.resource_name, resources.deleted,"
                + " categories.category_id, categories.category_name,"
                + " offices.office_id, offices.office_name,"
                + " reservator_id, reservators.last_name reservator_last_name, reservators.first_name reservator_first_name, reservators.user_level reservator_level, reservators.phone reservator_phone, reservators.email reservator_email,"
                + " co_reservator_id, co_reservators.last_name co_reservator_last_name, co_reservators.first_name co_reservator_first_name, co_reservators.user_level co_reservator_level, co_reservators.phone co_reservator_phone, co_reservators.email co_reservator_email,"
                + " attendance_types.attendance_type_id, attendance_types.attendance_type_name"
                + " from reservations"
                + " inner join resources on reservations.resource_id = resources.resource_id"
                + " inner join categories on resources.category_id = categories.category_id"
                + " inner join offices on resources.office_id = offices.office_id"
                + " inner join users reservators on reservations.reservator_id = reservators.id"
                + " inner join users co_reservators on reservations.co_reservator_id = co_reservators.id"
                + " inner join attendance_types on reservations.attendance_type_id = attendance_types.attendance_type_id";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Reservation> reservationList = new ArrayList<Reservation>();

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery();

                // 取得した予約情報を1件ずつ
                Reservation reservation = null;
                while (rs.next()) {
                    reservation = new Reservation();
                    reservation.setReservationId(rs.getInt("reservation_id"));
                    reservation.setUseStart(rs.getTimestamp("use_start"));
                    reservation.setUseEnd(rs.getTimestamp("use_end"));
                    reservation.setResource(new Resource(rs.getInt("resource_id"), rs.getString("resource_name"), new Category(rs.getInt("category_id"), rs.getString("category_name")), 0, new Office(rs.getInt("office_id"), rs.getString("office_name"), null), null, null, null, null, rs.getInt("deleted"), 0));
                    reservation.setMeetingName(rs.getString("meeting_name"));
                    reservation.setReservator(new User(rs.getString("reservator_id"), null, rs.getString("reservator_last_name"), rs.getString("reservator_first_name"), rs.getInt("reservator_level"), rs.getString("reservator_phone"), rs.getString("reservator_email")));
                    reservation.setCoReservator(new User(rs.getString("co_reservator_id"), null, rs.getString("co_reservator_last_name"), rs.getString("co_reservator_first_name"), rs.getInt("co_reservator_level"), rs.getString("co_reservator_phone"), rs.getString("co_reservator_email")));
                    reservation.setAttendanceCount(rs.getInt("attendance_count"));
                    reservation.setAttendanceType(new AttendanceType(rs.getInt("attendance_type_id"), rs.getString("attendance_type_name")));
                    reservation.setNote(rs.getString("note"));

                    reservationList.add(reservation);
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

        return reservationList;
    }

    /**
     * 日時・リソースを指定して予約情報を取得する
     *
     * @param start 日時開始（利用終了日時）
     * @param end 日時終了（利用開始日時）
     * @param resourceId リソースID
     * @return 予約情報のList
     * @throws DataAccessException
     */
    public List<Reservation> query(Date start, Date end, int resourceId) throws DataAccessException {

        int index = 0;
        int startIndex = 0;
        int endIndex = 0;
        int resourceIdIndex = 0;
        String sql = "select reservation_id, use_start, use_end, meeting_name, attendance_count, reservations.note,"
                + " resources.resource_id, resources.resource_name, resources.deleted,"
                + " categories.category_id, categories.category_name,"
                + " offices.office_id, offices.office_name,"
                + " reservator_id, reservators.last_name reservator_last_name, reservators.first_name reservator_first_name, reservators.user_level reservator_level, reservators.phone reservator_phone, reservators.email reservator_email,"
                + " co_reservator_id, co_reservators.last_name co_reservator_last_name, co_reservators.first_name co_reservator_first_name, co_reservators.user_level co_reservator_level, co_reservators.phone co_reservator_phone, co_reservators.email co_reservator_email,"
                + " attendance_types.attendance_type_id, attendance_types.attendance_type_name"
                + " from reservations"
                + " inner join resources on reservations.resource_id = resources.resource_id"
                + " inner join categories on resources.category_id = categories.category_id"
                + " inner join offices on resources.office_id = offices.office_id"
                + " inner join users reservators on reservations.reservator_id = reservators.id"
                + " left outer join users co_reservators on reservations.co_reservator_id = co_reservators.id"
                + " left outer join attendance_types on reservations.attendance_type_id = attendance_types.attendance_type_id"
                + " where 1 = 1";
        if (start != null) {
            sql = sql + " and ? < use_end";
            index++;
            startIndex = index;
        }
        if (end != null) {
            sql = sql + " and use_start < ?";
            index++;
            endIndex = index;
        }
        if (resourceId != 0) {
            sql = sql + " and resources.resource_id = ?";
            index++;
            resourceIdIndex = index;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Reservation> reservationList = new ArrayList<Reservation>();

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                if (startIndex != 0) {
                    pstmt.setTimestamp(startIndex, new Timestamp(start.getTime()));
                }
                if (endIndex != 0) {
                    pstmt.setTimestamp(endIndex, new Timestamp(end.getTime()));
                }
                if (resourceIdIndex != 0) {
                    pstmt.setInt(resourceIdIndex, resourceId);
                }
                rs = pstmt.executeQuery();

                // 取得した予約情報を1件ずつ
                Reservation reservation = null;
                while (rs.next()) {
                    reservation = new Reservation();
                    reservation.setReservationId(rs.getInt("reservation_id"));
                    reservation.setUseStart(rs.getTimestamp("use_start"));
                    reservation.setUseEnd(rs.getTimestamp("use_end"));
                    reservation.setResource(new Resource(rs.getInt("resource_id"), rs.getString("resource_name"), new Category(rs.getInt("category_id"), rs.getString("category_name")), 0, new Office(rs.getInt("office_id"), rs.getString("office_name"), null), null, null, null, null, rs.getInt("deleted"), 0));
                    reservation.setMeetingName(rs.getString("meeting_name"));
                    reservation.setReservator(new User(rs.getString("reservator_id"), null, rs.getString("reservator_last_name"), rs.getString("reservator_first_name"), rs.getInt("reservator_level"), rs.getString("reservator_phone"), rs.getString("reservator_email")));
                    reservation.setCoReservator(new User(rs.getString("co_reservator_id"), null, rs.getString("co_reservator_last_name"), rs.getString("co_reservator_first_name"), rs.getInt("co_reservator_level"), rs.getString("co_reservator_phone"), rs.getString("co_reservator_email")));
                    reservation.setAttendanceCount(rs.getInt("attendance_count"));
                    reservation.setAttendanceType(new AttendanceType(rs.getInt("attendance_type_id"), rs.getString("attendance_type_name")));
                    reservation.setNote(rs.getString("note"));

                    reservationList.add(reservation);
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

        return reservationList;
    }

    /**
     * 検索条件を指定して予約情報を取得する
     *
     * @param start 日時開始（利用終了日時）
     * @param end 日時終了（利用開始日時）
     * @param meetingName 会議名（部分一致）
     * @param officeId 事業所ID
     * @param reservatorId 予約者・共同予約者ID
     * @param notEnded 終了後の予約を除くかどうか
     * @return 予約情報のList
     * @throws DataAccessException
     */
    public List<Reservation> query(Date start, Date end, String meetingName, int officeId, String reservatorId, boolean notEnded) throws DataAccessException {

        int index = 0;
        int startIndex = 0;
        int endIndex = 0;
        int meetingNameIndex = 0;
        int officeIdIndex = 0;
        int reservatorIdIndex = 0;
        String sql = "select reservation_id, use_start, use_end, meeting_name, attendance_count, reservations.note,"
                + " resources.resource_id, resources.resource_name, resources.deleted,"
                + " categories.category_id, categories.category_name,"
                + " offices.office_id, offices.office_name,"
                + " reservator_id, reservators.last_name reservator_last_name, reservators.first_name reservator_first_name, reservators.user_level reservator_level, reservators.phone reservator_phone, reservators.email reservator_email,"
                + " co_reservator_id, co_reservators.last_name co_reservator_last_name, co_reservators.first_name co_reservator_first_name, co_reservators.user_level co_reservator_level, co_reservators.phone co_reservator_phone, co_reservators.email co_reservator_email,"
                + " attendance_types.attendance_type_id, attendance_types.attendance_type_name"
                + " from reservations"
                + " inner join resources on reservations.resource_id = resources.resource_id"
                + " inner join categories on resources.category_id = categories.category_id"
                + " inner join offices on resources.office_id = offices.office_id"
                + " inner join users reservators on reservations.reservator_id = reservators.id"
                + " left outer join users co_reservators on reservations.co_reservator_id = co_reservators.id"
                + " left outer join attendance_types on reservations.attendance_type_id = attendance_types.attendance_type_id"
                + " where 1 = 1";
        if (start != null) {
            sql = sql + " and ? < use_end";
            index++;
            startIndex = index;
        }
        if (end != null) {
            sql = sql + " and use_start < ?";
            index++;
            endIndex = index;
        }
        if (meetingName != null) {
            sql = sql + " and meeting_name like ?";
            index++;
            meetingNameIndex = index;
        }
        if (officeId != 0) {
            sql = sql + " and offices.office_id = ?";
            index++;
            officeIdIndex = index;
        }
        if (reservatorId != null) {
            sql = sql + " and (reservator_id = ? or co_reservator_id = ?)";
            index += 2;
            reservatorIdIndex = index;
        }
        if (notEnded) {
            sql = sql + " and now() < use_end";
        }
        sql = sql + " order by use_start, offices.office_id, categories.category_id, resources.resource_id";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Reservation> reservationList = new ArrayList<Reservation>();

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                if (startIndex != 0) {
                    pstmt.setTimestamp(startIndex, new Timestamp(start.getTime()));
                }
                if (endIndex != 0) {
                    pstmt.setTimestamp(endIndex, new Timestamp(end.getTime()));
                }
                if (meetingNameIndex != 0) {
                    pstmt.setString(meetingNameIndex, "%" + meetingName + "%");
                }
                if (officeIdIndex != 0) {
                    pstmt.setInt(officeIdIndex, officeId);
                }
                if (reservatorIdIndex != 0) {
                    pstmt.setString(reservatorIdIndex - 1, reservatorId);
                    pstmt.setString(reservatorIdIndex, reservatorId);
                }
                rs = pstmt.executeQuery();

                // 取得した予約情報を1件ずつ
                Reservation reservation = null;
                while (rs.next()) {
                    reservation = new Reservation();
                    reservation.setReservationId(rs.getInt("reservation_id"));
                    reservation.setUseStart(rs.getTimestamp("use_start"));
                    reservation.setUseEnd(rs.getTimestamp("use_end"));
                    reservation.setResource(new Resource(rs.getInt("resource_id"), rs.getString("resource_name"), new Category(rs.getInt("category_id"), rs.getString("category_name")), 0, new Office(rs.getInt("office_id"), rs.getString("office_name"), null), null, null, null, null, rs.getInt("deleted"), 0));
                    reservation.setMeetingName(rs.getString("meeting_name"));
                    reservation.setReservator(new User(rs.getString("reservator_id"), null, rs.getString("reservator_last_name"), rs.getString("reservator_first_name"), rs.getInt("reservator_level"), rs.getString("reservator_phone"), rs.getString("reservator_email")));
                    reservation.setCoReservator(new User(rs.getString("co_reservator_id"), null, rs.getString("co_reservator_last_name"), rs.getString("co_reservator_first_name"), rs.getInt("co_reservator_level"), rs.getString("co_reservator_phone"), rs.getString("co_reservator_email")));
                    reservation.setAttendanceCount(rs.getInt("attendance_count"));
                    reservation.setAttendanceType(new AttendanceType(rs.getInt("attendance_type_id"), rs.getString("attendance_type_name")));
                    reservation.setNote(rs.getString("note"));

                    reservationList.add(reservation);
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

        return reservationList;
    }

    /**
     * 予約IDを指定して予約情報を取得する
     *
     * @param reservationId 予約ID
     * @return 予約情報
     * @throws DataAccessException
     */
    public Reservation queryByReservationId(int reservationId) throws DataAccessException {

        String sql = "select reservation_id, use_start, use_end, meeting_name, attendance_count, reservations.note,"
                + " resources.resource_id, resources.resource_name, resources.deleted,"
                + " reservator_id, reservators.last_name reservator_last_name, reservators.first_name reservator_first_name, reservators.user_level reservator_level, reservators.phone reservator_phone, reservators.email reservator_email,"
                + " co_reservator_id, co_reservators.last_name co_reservator_last_name, co_reservators.first_name co_reservator_first_name, co_reservators.user_level co_reservator_level, co_reservators.phone co_reservator_phone, co_reservators.email co_reservator_email,"
                + " attendance_types.attendance_type_id, attendance_types.attendance_type_name"
                + " from reservations"
                + " inner join resources on reservations.resource_id = resources.resource_id"
                + " inner join users reservators on reservations.reservator_id = reservators.id"
                + " left outer join users co_reservators on reservations.co_reservator_id = co_reservators.id"
                + " left outer join attendance_types on reservations.attendance_type_id = attendance_types.attendance_type_id"
                + " where reservation_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Reservation reservation = null;

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, reservationId);
                rs = pstmt.executeQuery();

                // 取得した予約情報を1件
                if (rs.next()) {
                    reservation = new Reservation();
                    reservation.setReservationId(rs.getInt("reservation_id"));
                    reservation.setUseStart(rs.getTimestamp("use_start"));
                    reservation.setUseEnd(rs.getTimestamp("use_end"));
                    reservation.setResource(new Resource(rs.getInt("resource_id"), rs.getString("resource_name"), null, 0, null, null, null, null, null, rs.getInt("deleted"), 0));
                    reservation.setMeetingName(rs.getString("meeting_name"));
                    reservation.setReservator(new User(rs.getString("reservator_id"), null, rs.getString("reservator_last_name"), rs.getString("reservator_first_name"), rs.getInt("reservator_level"), rs.getString("reservator_phone"), rs.getString("reservator_email")));
                    reservation.setCoReservator(new User(rs.getString("co_reservator_id"), null, rs.getString("co_reservator_last_name"), rs.getString("co_reservator_first_name"), rs.getInt("co_reservator_level"), rs.getString("co_reservator_phone"), rs.getString("co_reservator_email")));
                    reservation.setAttendanceCount(rs.getInt("attendance_count"));
                    reservation.setAttendanceType(new AttendanceType(rs.getInt("attendance_type_id"), rs.getString("attendance_type_name")));
                    reservation.setNote(rs.getString("note"));
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

        return reservation;
    }

    /**
     * 予約ID・予約者IDを指定して予約情報を取得する
     *
     * @param reservationId 予約ID
     * @param reservatorId 予約者ID/共同予約者ID
     * @return 予約情報
     * @throws DataAccessException
     */
    public Reservation queryByReservationId(int reservationId, String reservatorId) throws DataAccessException {

        String sql = "select reservation_id, use_start, use_end, meeting_name, attendance_count, reservations.note,"
                + " resources.resource_id, resources.resource_name, resources.deleted,"
                + " reservator_id, reservators.last_name reservator_last_name, reservators.first_name reservator_first_name, reservators.user_level reservator_level, reservators.phone reservator_phone, reservators.email reservator_email,"
                + " co_reservator_id, co_reservators.last_name co_reservator_last_name, co_reservators.first_name co_reservator_first_name, co_reservators.user_level co_reservator_level, co_reservators.phone co_reservator_phone, co_reservators.email co_reservator_email,"
                + " attendance_types.attendance_type_id, attendance_types.attendance_type_name"
                + " from reservations"
                + " inner join resources on reservations.resource_id = resources.resource_id"
                + " inner join users reservators on reservations.reservator_id = reservators.id"
                + " left outer join users co_reservators on reservations.co_reservator_id = co_reservators.id"
                + " left outer join attendance_types on reservations.attendance_type_id = attendance_types.attendance_type_id"
                + " where reservation_id = ?"
                + " and (reservator_id = ? or co_reservator_id = ?)";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Reservation reservation = null;

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, reservationId);
                pstmt.setString(2, reservatorId);
                pstmt.setString(3, reservatorId);
                rs = pstmt.executeQuery();

                // 取得した予約情報を1件
                if (rs.next()) {
                    reservation = new Reservation();
                    reservation.setReservationId(rs.getInt("reservation_id"));
                    reservation.setUseStart(rs.getTimestamp("use_start"));
                    reservation.setUseEnd(rs.getTimestamp("use_end"));
                    reservation.setResource(new Resource(rs.getInt("resource_id"), rs.getString("resource_name"), null, 0, null, null, null, null, null, rs.getInt("deleted"), 0));
                    reservation.setMeetingName(rs.getString("meeting_name"));
                    reservation.setReservator(new User(rs.getString("reservator_id"), null, rs.getString("reservator_last_name"), rs.getString("reservator_first_name"), rs.getInt("reservator_level"), rs.getString("reservator_phone"), rs.getString("reservator_email")));
                    reservation.setCoReservator(new User(rs.getString("co_reservator_id"), null, rs.getString("co_reservator_last_name"), rs.getString("co_reservator_first_name"), rs.getInt("co_reservator_level"), rs.getString("co_reservator_phone"), rs.getString("co_reservator_email")));
                    reservation.setAttendanceCount(rs.getInt("attendance_count"));
                    reservation.setAttendanceType(new AttendanceType(rs.getInt("attendance_type_id"), rs.getString("attendance_type_name")));
                    reservation.setNote(rs.getString("note"));
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

        return reservation;
    }

    /**
     * 予約情報を登録する
     *
     * @param reservation 予約情報
     * @return 登録時に割り当てられた予約ID
     * @throws DataAccessException
     */
    public int register(Reservation reservation) throws DataAccessException {

        String insertReservation = "insert into reservations (use_start, use_end, resource_id, meeting_name, reservator_id, co_reservator_id, attendance_count, attendance_type_id, note)"
                + " values (?, ?, (select resource_id from resources where resource_id = ? and deleted = 0), ?, (select id from users where id = ? and user_level > 0), (select id from users where id = ? and user_level > 0), ?, ?, ?)";
        String selectReservationId = "select max(reservation_id) current_reservation_id from reservations";
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        int count = 0;

        int reservationId = 0;

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                // トランザクション開始
                con.setAutoCommit(false);
                con.commit();

                if (reservation != null) {
                    pstmt1 = con.prepareStatement(insertReservation);
                    if (reservation.getUseStart() != null) {
                        pstmt1.setTimestamp(1, new Timestamp(reservation.getUseStart().getTime()));
                    } else {
                        pstmt1.setNull(1, java.sql.Types.TIMESTAMP);
                    }
                    if (reservation.getUseEnd() != null) {
                        pstmt1.setTimestamp(2, new Timestamp(reservation.getUseEnd().getTime()));
                    } else {
                        pstmt1.setNull(2, java.sql.Types.TIMESTAMP);
                    }
                    if (reservation.getResource() != null) {
                        pstmt1.setInt(3, reservation.getResource().getResourceId());
                    } else {
                        pstmt1.setNull(3, java.sql.Types.INTEGER);
                    }
                    pstmt1.setString(4, reservation.getMeetingName());
                    if (reservation.getReservator() != null) {
                        pstmt1.setString(5, reservation.getReservator().getId());
                    } else {
                        pstmt1.setNull(5, java.sql.Types.VARCHAR);
                    }
                    if (reservation.getCoReservator() != null) {
                        pstmt1.setString(6, reservation.getCoReservator().getId());
                    } else {
                        pstmt1.setNull(6, java.sql.Types.VARCHAR);
                    }
                    pstmt1.setInt(7, reservation.getAttendanceCount());
                    if (reservation.getAttendanceType() != null) {
                        pstmt1.setInt(8, reservation.getAttendanceType().getAttendanceTypeId());
                    } else {
                        pstmt1.setNull(8, java.sql.Types.INTEGER);
                    }
                    pstmt1.setString(9, reservation.getNote());
                    count = pstmt1.executeUpdate();

                    if (count == 1) {
                        pstmt2 = con.prepareStatement(selectReservationId);
                        rs = pstmt2.executeQuery();
                        // 取得した予約IDを1件
                        if (rs.next()) {
                            reservationId = rs.getInt("current_reservation_id");
                        }
                    }
                }

                // トランザクション終了
                con.commit();
            } catch (SQLException e) {
                // 巻き戻し
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

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
                    dbHelper.closeResource(pstmt1);
                    dbHelper.closeResource(pstmt2);
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

        return reservationId;
    }

    /**
     * 予約情報を更新する
     *
     * @param reservation 予約情報
     * @param reservatorId 予約者ID/共同予約者ID
     * @return 更新件数
     * @throws DataAccessException
     */
    public int edit(Reservation reservation, String reservatorId) throws DataAccessException {

        String updateReservation ="update reservations set"
                + " use_start = ?,"
                + " use_end = ?,"
                + " resource_id = ?,"
                + " meeting_name = ?,"
                + " reservator_id = ?,"
                + " co_reservator_id = ?,"
                + " attendance_count = ?,"
                + " attendance_type_id = ?,"
                + " note = ?"
                + " where reservation_id = ?"
                + " and (reservator_id = ? or co_reservator_id = ?)";
        PreparedStatement pstmt = null;
        int count = 0;

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                // トランザクション開始
                con.setAutoCommit(false);
                con.commit();

                if (reservation != null) {
                    pstmt = con.prepareStatement(updateReservation);
                    if (reservation.getUseStart() != null) {
                        pstmt.setTimestamp(1, new Timestamp(reservation.getUseStart().getTime()));
                    } else {
                        pstmt.setNull(1, java.sql.Types.TIMESTAMP);
                    }
                    if (reservation.getUseEnd() != null) {
                        pstmt.setTimestamp(2, new Timestamp(reservation.getUseEnd().getTime()));
                    } else {
                        pstmt.setNull(2, java.sql.Types.TIMESTAMP);
                    }
                    if (reservation.getResource() != null) {
                        pstmt.setInt(3, reservation.getResource().getResourceId());
                    } else {
                        pstmt.setNull(3, java.sql.Types.INTEGER);
                    }
                    pstmt.setString(4, reservation.getMeetingName());
                    if (reservation.getReservator() != null) {
                        pstmt.setString(5, reservation.getReservator().getId());
                    } else {
                        pstmt.setNull(5, java.sql.Types.VARCHAR);
                    }
                    if (reservation.getCoReservator() != null) {
                        pstmt.setString(6, reservation.getCoReservator().getId());
                    } else {
                        pstmt.setNull(6, java.sql.Types.VARCHAR);
                    }
                    pstmt.setInt(7, reservation.getAttendanceCount());
                    if (reservation.getAttendanceType() != null) {
                        pstmt.setInt(8, reservation.getAttendanceType().getAttendanceTypeId());
                    } else {
                        pstmt.setNull(8, java.sql.Types.INTEGER);
                    }
                    pstmt.setString(9, reservation.getNote());
                    pstmt.setInt(10, reservation.getReservationId());
                    pstmt.setString(11, reservatorId);
                    pstmt.setString(12, reservatorId);
                    count = pstmt.executeUpdate();
                }

                // トランザクション終了
                con.commit();
            } catch (SQLException e) {
                // 巻き戻し
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

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

                dbHelper.closeDb();
            }
        } else {
            throw new DataAccessException("DB接続に失敗しました");
        }

        return count;
    }

    /**
     * 予約情報を削除する
     *
     * @param reservationId 予約ID
     * @param reservatorId 予約者ID/共同予約者ID
     * @return 削除件数
     * @throws DataAccessException
     */
    public int delete(int reservationId, String reservatorId) throws DataAccessException {
        String deleteReservation = "delete from reservations"
                + " where reservation_id = ?"
                + " and (reservator_id = ? or co_reservator_id = ?)";
        PreparedStatement pstmt = null;
        int count = 0;

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                // トランザクション開始
                con.setAutoCommit(false);
                con.commit();

                pstmt = con.prepareStatement(deleteReservation);
                pstmt.setInt(1, reservationId);
                pstmt.setString(2, reservatorId);
                pstmt.setString(3, reservatorId);
                count = pstmt.executeUpdate();

                // トランザクション終了
                con.commit();
            } catch (SQLException e) {
                // 巻き戻し
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

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

                dbHelper.closeDb();
            }
        } else {
            throw new DataAccessException("DB接続に失敗しました");
        }

        return count;
    }

}
