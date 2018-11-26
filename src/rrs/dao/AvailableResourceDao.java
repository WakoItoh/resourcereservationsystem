package rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rrs.dto.AvailableResource;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Resource;

public class AvailableResourceDao {

    /**
     * 検索条件を指定して利用可能なリソース情報を取得する
     *
     * @param availableStart 利用可能日時開始
     * @param availableEnd 利用可能日時終了
     * @param availableHours 利用可能時間数（ミリ秒）
     * @param resourceName リソース名（部分一致）
     * @param categoryId カテゴリID
     * @param capacity 定員（以上）
     * @param noCapacity 定員0（備品）のみ検索するか否か
     * @param officeId 事業所ID
     * @return 利用可能リソース情報のList
     * @throws DataAccessException
     */
    public List<AvailableResource> query(Date availableStart, Date availableEnd, long availableHours, String resourceName, int categoryId, int capacity, boolean noCapacity, int officeId) throws DataAccessException {

        int index = 10;
        int resourceNameIndex = 0;
        int categoryIdIndex = 0;
        int capacityIndex = 0;
        int officeIdIndex = 0;
        String unavailableResources = "select resources.resource_id, resources.resource_name, resources.capacity, resources.note, resources.deleted,"
                + " categories.category_id, categories.category_name, offices.office_id, offices.office_name,"
                + " reservations.reservation_id, reservations.use_start unavailable_start, reservations.use_end unavailable_end"
                + " from resources"
                + " inner join categories on resources.category_id = categories.category_id"
                + " inner join offices on resources.office_id = offices.office_id"
                + " inner join reservations on resources.resource_id = reservations.resource_id"
                + " where resources.deleted != 1"
                + " union "
                + "select resources.resource_id, resources.resource_name, resources.capacity, resources.note, resources.deleted,"
                + " categories.category_id, categories.category_name, offices.office_id, offices.office_name,"
                + " null reservation_id, resources.suspend_start unavailable_start, resources.suspend_end unavailable_end"
                + " from resources"
                + " inner join categories on resources.category_id = categories.category_id"
                + " inner join offices on resources.office_id = offices.office_id"
                + " where resources.deleted != 1";
        String availableResources = "select b.resource_id, b.resource_name, b.category_id, b.category_name, b.capacity, b.office_id, b.office_name, b.note, b.deleted,"
                + " (select max(a.unavailable_end) from (" + unavailableResources + ") a where b.resource_id = a.resource_id and a.unavailable_end <= b.unavailable_start) available_start, b.unavailable_start available_end"
                + " from (" + unavailableResources + ") b"
                + " where b.unavailable_start is not null"
                + " union "
                + "select d.resource_id, d.resource_name, d.category_id, d.category_name, d.capacity, d.office_id, d.office_name, d.note, d.deleted,"
                + " (select max(c.unavailable_end) from (" + unavailableResources + ") c where d.resource_id = c.resource_id) available_start, null available_end"
                + " from (" + unavailableResources + ") d";
        String sql = "select resource_id, resource_name, category_id, category_name, capacity, office_id, office_name, note, deleted,"
                + " case when available_start is null or available_start <= ? then ? else available_start end available_start,"
                + " case when available_end is null or ? <= available_end then ? else available_end end available_end"
                + " from (" + availableResources + ") available_resources"
                + " where 1 = 1"
                + " and (available_start is null or available_start <= ? or cast (? as interval) <= (? - available_start))"
                + " and (available_end is null or ? <= available_end or cast (? as interval) <= (available_end - ?))";
        if (resourceName != null) {
            sql = sql + " and resource_name like ?";
            index++;
            resourceNameIndex = index;
        }
        if (categoryId != 0) {
            sql = sql + " and category_id = ?";
            index++;
            categoryIdIndex = index;
        }
        if (capacity != 0) {
            sql = sql + " and capacity >= ?";
            index++;
            capacityIndex = index;
        }
        if (noCapacity) {
            sql = sql + " and capacity = 0";
        }
        if (officeId != 0) {
            sql = sql + " and office_id = ?";
            index++;
            officeIdIndex = index;
        }
        sql = sql + " order by resource_id, available_start";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<AvailableResource> availableResourceList = new ArrayList<AvailableResource>();

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                if (availableStart != null && availableEnd != null && availableHours != 0) {
                    pstmt = con.prepareStatement(sql);
                    pstmt.setTimestamp(1, new Timestamp(availableStart.getTime()));
                    pstmt.setTimestamp(2, new Timestamp(availableStart.getTime()));
                    pstmt.setTimestamp(3, new Timestamp(availableEnd.getTime()));
                    pstmt.setTimestamp(4, new Timestamp(availableEnd.getTime()));
                    pstmt.setTimestamp(5, new Timestamp(availableStart.getTime()));
                    pstmt.setString(6, availableHours + " milliseconds");
                    pstmt.setTimestamp(7, new Timestamp(availableEnd.getTime()));
                    pstmt.setTimestamp(8, new Timestamp(availableEnd.getTime()));
                    pstmt.setString(9, availableHours + " milliseconds");
                    pstmt.setTimestamp(10, new Timestamp(availableStart.getTime()));
                    if (resourceNameIndex != 0) {
                        pstmt.setString(resourceNameIndex, "%" + resourceName + "%");
                    }
                    if (categoryIdIndex != 0) {
                        pstmt.setInt(categoryIdIndex, categoryId);
                    }
                    if (capacityIndex != 0) {
                        pstmt.setInt(capacityIndex, capacity);
                    }
                    if (officeIdIndex != 0) {
                        pstmt.setInt(officeIdIndex, officeId);
                    }
                    rs = pstmt.executeQuery();

                    // 取得したリソース情報を1件ずつ
                    AvailableResource availableResource = null;
                    while (rs.next()) {
                        availableResource = new AvailableResource();
                        availableResource.setAvailableStart(rs.getTimestamp("available_start"));
                        availableResource.setAvailableEnd(rs.getTimestamp("available_end"));
                        availableResource.setResource(new Resource());
                        availableResource.getResource().setResourceId(rs.getInt("resource_id"));
                        availableResource.getResource().setResourceName(rs.getString("resource_name"));
                        availableResource.getResource().setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
                        availableResource.getResource().setCapacity(rs.getInt("capacity"));
                        availableResource.getResource().setPropertyList(null);
                        availableResource.getResource().setOffice(new Office(rs.getInt("office_id"), rs.getString("office_name"), null));
                        availableResource.getResource().setNote(rs.getString("note"));
                        availableResource.getResource().setSuspendStart(null);
                        availableResource.getResource().setSuspendEnd(null);
                        availableResource.getResource().setDeleted(rs.getInt("deleted"));
                        availableResource.getResource().setStatus(0);

                        availableResourceList.add(availableResource);
                    }
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

        return availableResourceList;
    }

    /**
     * 日時・時間数・リソースを指定して利用可能なリソース情報を取得する
     *
     * @param availableStart 利用可能日時開始
     * @param availableEnd 利用可能日時終了
     * @param availableHours 利用可能時間数（ミリ秒）
     * @param resourceId リソースID
     * @return 利用可能リソース情報のList
     * @throws DataAccessException
     */
    public List<AvailableResource> query(Date availableStart, Date availableEnd, long availableHours, int resourceId) throws DataAccessException {

        int index = 10;
        int resourceIdIndex = 0;
        String unavailableResources = "select resources.resource_id, resources.resource_name, resources.capacity, resources.note, resources.deleted,"
                + " categories.category_id, categories.category_name, offices.office_id, offices.office_name,"
                + " reservations.reservation_id, reservations.use_start unavailable_start, reservations.use_end unavailable_end"
                + " from resources"
                + " inner join categories on resources.category_id = categories.category_id"
                + " inner join offices on resources.office_id = offices.office_id"
                + " inner join reservations on resources.resource_id = reservations.resource_id"
                + " where resources.deleted != 1"
                + " union "
                + "select resources.resource_id, resources.resource_name, resources.capacity, resources.note, resources.deleted,"
                + " categories.category_id, categories.category_name, offices.office_id, offices.office_name,"
                + " null reservation_id, resources.suspend_start unavailable_start, resources.suspend_end unavailable_end"
                + " from resources"
                + " inner join categories on resources.category_id = categories.category_id"
                + " inner join offices on resources.office_id = offices.office_id"
                + " where resources.deleted != 1";
        String availableResources = "select b.resource_id, b.resource_name, b.category_id, b.category_name, b.capacity, b.office_id, b.office_name, b.note, b.deleted,"
                + " (select max(a.unavailable_end) from (" + unavailableResources + ") a where b.resource_id = a.resource_id and a.unavailable_end <= b.unavailable_start) available_start, b.unavailable_start available_end"
                + " from (" + unavailableResources + ") b"
                + " where b.unavailable_start is not null"
                + " union "
                + "select d.resource_id, d.resource_name, d.category_id, d.category_name, d.capacity, d.office_id, d.office_name, d.note, d.deleted,"
                + " (select max(c.unavailable_end) from (" + unavailableResources + ") c where d.resource_id = c.resource_id) available_start, null available_end"
                + " from (" + unavailableResources + ") d";
        String sql = "select resource_id, resource_name, category_id, category_name, capacity, office_id, office_name, note, deleted,"
                + " case when available_start is null or available_start <= ? then ? else available_start end available_start,"
                + " case when available_end is null or ? <= available_end then ? else available_end end available_end"
                + " from (" + availableResources + ") available_resources"
                + " where 1 = 1"
                + " and (available_start is null or available_start <= ? or cast (? as interval) <= (? - available_start))"
                + " and (available_end is null or ? <= available_end or cast (? as interval) <= (available_end - ?))";
        if (resourceId != 0) {
            sql = sql + " and resource_id = ?";
            index++;
            resourceIdIndex = index;
        }
        sql = sql + " order by resource_id, available_start";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<AvailableResource> availableResourceList = new ArrayList<AvailableResource>();

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                if (availableStart != null && availableEnd != null && availableHours != 0) {
                    pstmt = con.prepareStatement(sql);
                    pstmt.setTimestamp(1, new Timestamp(availableStart.getTime()));
                    pstmt.setTimestamp(2, new Timestamp(availableStart.getTime()));
                    pstmt.setTimestamp(3, new Timestamp(availableEnd.getTime()));
                    pstmt.setTimestamp(4, new Timestamp(availableEnd.getTime()));
                    pstmt.setTimestamp(5, new Timestamp(availableStart.getTime()));
                    pstmt.setString(6, availableHours + " milliseconds");
                    pstmt.setTimestamp(7, new Timestamp(availableEnd.getTime()));
                    pstmt.setTimestamp(8, new Timestamp(availableEnd.getTime()));
                    pstmt.setString(9, availableHours + " milliseconds");
                    pstmt.setTimestamp(10, new Timestamp(availableStart.getTime()));
                    if (resourceIdIndex != 0) {
                        pstmt.setInt(resourceIdIndex, resourceId);
                    }
                    rs = pstmt.executeQuery();

                    // 取得したリソース情報を1件ずつ
                    AvailableResource availableResource = null;
                    while (rs.next()) {
                        availableResource = new AvailableResource();
                        availableResource.setAvailableStart(rs.getTimestamp("available_start"));
                        availableResource.setAvailableEnd(rs.getTimestamp("available_end"));
                        availableResource.setResource(new Resource());
                        availableResource.getResource().setResourceId(rs.getInt("resource_id"));
                        availableResource.getResource().setResourceName(rs.getString("resource_name"));
                        availableResource.getResource().setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
                        availableResource.getResource().setCapacity(rs.getInt("capacity"));
                        availableResource.getResource().setPropertyList(null);
                        availableResource.getResource().setOffice(new Office(rs.getInt("office_id"), rs.getString("office_name"), null));
                        availableResource.getResource().setNote(rs.getString("note"));
                        availableResource.getResource().setSuspendStart(null);
                        availableResource.getResource().setSuspendEnd(null);
                        availableResource.getResource().setDeleted(rs.getInt("deleted"));
                        availableResource.getResource().setStatus(0);

                        availableResourceList.add(availableResource);
                    }
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

        return availableResourceList;
    }

}
