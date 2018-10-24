package rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;
import rrs.dto.Resource;

public class ResourceDao {

    /**
     * リソース情報を取得する
     *
     * @param notDeleted 削除済みを除くかどうか
     * @return リソース情報のList
     * @throws DataAccessException
     */
    public List<Resource> query(boolean notDeleted) throws DataAccessException {

        String sql = "select resource_id, resource_name, capacity, note, suspend_start, suspend_end, deleted,"
                + " case when deleted = 1 then 3 when suspend_start is not null and suspend_start < now() and suspend_end is not null and now() < suspend_end then 2 else 1 end status,"
                + " categories.category_id, categories.category_name, offices.office_id, offices.office_name"
                + " from resources"
                + " inner join categories on resources.category_id = categories.category_id"
                + " inner join offices on resources.office_id = offices.office_id";
        if (notDeleted) {
            sql = sql + " where deleted = 0";
        }
        sql = sql + " order by status, offices.office_id, categories.category_id, resource_id";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Resource> resourceList = new ArrayList<Resource>();

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery();

                // 取得したリソース情報を1件ずつ
                Resource resource = null;
                while (rs.next()) {
                    resource = new Resource();
                    resource.setResourceId(rs.getInt("resource_id"));
                    resource.setResourceName(rs.getString("resource_name"));
                    resource.setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
                    resource.setCapacity(rs.getInt("capacity"));
                    resource.setPropertyList(null);
                    resource.setOffice(new Office(rs.getInt("office_id"), rs.getString("office_name"), null));
                    resource.setNote(rs.getString("note"));
                    resource.setSuspendStart(rs.getTimestamp("suspend_start"));
                    resource.setSuspendEnd(rs.getTimestamp("suspend_end"));
                    resource.setDeleted(rs.getInt("deleted"));
                    resource.setStatus(rs.getInt("status"));

                    resourceList.add(resource);
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

        return resourceList;
    }

    /**
     * リソースIDを指定してリソース情報を取得する
     *
     * @param resourceId リソースID
     * @param notDeleted 削除済みを除くかどうか
     * @return リソース情報
     * @throws DataAccessException
     */
    public Resource queryByResourceId(int resourceId, boolean notDeleted) throws DataAccessException {

        String sql = "select r.resource_id, r.resource_name, r.category_id, r.category_name, r.capacity, r.office_id, r.office_name, r.note, r.suspend_start, r.suspend_end, r.deleted, r.status,"
                + " p.property_id, p.property_name"
                + " from ("
                + "  select resource_id, resource_name, capacity, note, suspend_start, suspend_end, deleted,"
                + "   case when deleted = 1 then 3 when suspend_start is not null and suspend_start < now() and suspend_end is not null and now() < suspend_end then 2 else 1 end status,"
                + "   categories.category_id, categories.category_name, offices.office_id, offices.office_name"
                + "   from resources"
                + "   inner join categories on resources.category_id = categories.category_id"
                + "   inner join offices on resources.office_id = offices.office_id"
                + " ) r"
                + " left outer join ("
                + "  select resource_id, properties.property_id, properties.property_name"
                + "   from resource_properties"
                + "   inner join properties on properties.property_id = resource_properties.property_id"
                + " ) p on r.resource_id = p.resource_id"
                + " where r.resource_id = ?";
        if (notDeleted) {
            sql = sql + " and r.deleted = 0";
        }
        sql = sql + " order by p.property_id";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Resource resource = null;

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, resourceId);
                rs = pstmt.executeQuery();

                // 取得したリソース情報を1件ずつ
                Property property = null;
                while (rs.next()) {
                    // 最初の1件
                    if (resource == null) {
                        resource = new Resource();
                        resource.setResourceId(rs.getInt("resource_id"));
                        resource.setResourceName(rs.getString("resource_name"));
                        resource.setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
                        resource.setCapacity(rs.getInt("capacity"));
                        resource.setPropertyList(new ArrayList<Property>());
                        resource.setOffice(new Office(rs.getInt("office_id"), rs.getString("office_name"), null));
                        resource.setNote(rs.getString("note"));
                        resource.setSuspendStart(rs.getTimestamp("suspend_start"));
                        resource.setSuspendEnd(rs.getTimestamp("suspend_end"));
                        resource.setDeleted(rs.getInt("deleted"));
                        resource.setStatus(rs.getInt("status"));
                    }

                    if (rs.getString("property_name") != null) {
                        property = new Property(rs.getInt("property_id"),rs.getString("property_name"));
                        resource.getPropertyList().add(property);
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

        return resource;
    }

    /**
     * リソース情報を登録する
     *
     * @param resource リソース情報
     * @return 登録時に割り当てられたリソースID
     * @throws DataAccessException
     */
    public int register(Resource resource) throws DataAccessException {

        String insertResource = "insert into resources (resource_name, category_id, capacity, office_id, note, suspend_start, suspend_end) values (?, ?, ?, ?, ?, ?, ?)";
        String selectResourceId = "select max(resource_id) current_resource_id from resources";
        String insertResourceProperty = "insert into resource_properties (resource_id, property_id) values (?, ?)";
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        ResultSet rs = null;
        int count = 0;

        int resourceId = 0;

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                // トランザクション開始
                con.setAutoCommit(false);
                con.commit();

                if (resource != null) {
                    pstmt1 = con.prepareStatement(insertResource);
                    pstmt1.setString(1, resource.getResourceName());
                    if (resource.getCategory() != null) {
                        pstmt1.setInt(2, resource.getCategory().getCategoryId());
                    } else {
                        pstmt1.setNull(2, java.sql.Types.INTEGER);
                    }
                    pstmt1.setInt(3, resource.getCapacity());
                    if (resource.getOffice() != null) {
                        pstmt1.setInt(4, resource.getOffice().getOfficeId());
                    } else {
                        pstmt1.setNull(4, java.sql.Types.INTEGER);
                    }
                    pstmt1.setString(5, resource.getNote());
                    if (resource.getSuspendStart() != null) {
                        pstmt1.setTimestamp(6, new Timestamp(resource.getSuspendStart().getTime()));
                    } else {
                        pstmt1.setNull(6, java.sql.Types.TIMESTAMP);
                    }
                    if (resource.getSuspendEnd() != null) {
                        pstmt1.setTimestamp(7, new Timestamp(resource.getSuspendEnd().getTime()));
                    } else {
                        pstmt1.setNull(7, java.sql.Types.TIMESTAMP);
                    }
                    count = pstmt1.executeUpdate();

                    if (count == 1) {
                        pstmt2 = con.prepareStatement(selectResourceId);
                        rs = pstmt2.executeQuery();
                        // 取得したリソースIDを1件
                        if (rs.next()) {
                            resourceId = rs.getInt("current_resource_id");
                        }
                    }

                    if (resourceId != 0 && resource.getPropertyList() != null) {
                        // 同じSQL文を繰り返し
                        pstmt3 = con.prepareStatement(insertResourceProperty);
                        for (Property property : resource.getPropertyList()) {
                            pstmt3.setInt(1, resourceId);
                            pstmt3.setInt(2, property.getPropertyId());
                            count += pstmt3.executeUpdate();
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
                    dbHelper.closeResource(pstmt3);
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

        return resourceId;
    }

    /**
     * リソース情報を更新する
     * 削除済みは更新不可
     *
     * @param resource リソース情報
     * @return 更新件数
     * @throws DataAccessException
     */
    public int edit(Resource resource) throws DataAccessException {

        String updateResource = "update resources set"
                + " resource_name = ?,"
                + " category_id = ?,"
                + " capacity = ?,"
                + " office_id = ?,"
                + " note = ?,"
                + " suspend_start = ?,"
                + " suspend_end = ?"
                + " where resource_id = ?"
                + " and deleted = 0";
        String deleteResourceProperty = "delete from resource_properties where resource_id = ?";
        String insertResourceProperty = "insert into resource_properties (resource_id, property_id) values (?, ?)";
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        int count = 0;

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                // トランザクション開始
                con.setAutoCommit(false);
                con.commit();

                if (resource != null) {
                    pstmt1 = con.prepareStatement(updateResource);
                    pstmt1.setString(1, resource.getResourceName());
                    if (resource.getCategory() != null) {
                        pstmt1.setInt(2, resource.getCategory().getCategoryId());
                    } else {
                        pstmt1.setNull(2, java.sql.Types.INTEGER);
                    }
                    pstmt1.setInt(3, resource.getCapacity());
                    if (resource.getOffice() != null) {
                        pstmt1.setInt(4, resource.getOffice().getOfficeId());
                    } else {
                        pstmt1.setNull(4, java.sql.Types.INTEGER);
                    }
                    pstmt1.setString(5, resource.getNote());
                    if (resource.getSuspendStart() != null) {
                        pstmt1.setTimestamp(6, new Timestamp(resource.getSuspendStart().getTime()));
                    } else {
                        pstmt1.setTimestamp(6, null);
                    }
                    if (resource.getSuspendEnd() != null) {
                        pstmt1.setTimestamp(7, new Timestamp(resource.getSuspendEnd().getTime()));
                    } else {
                        pstmt1.setTimestamp(7, null);
                    }
                    pstmt1.setInt(8, resource.getResourceId());
                    count = pstmt1.executeUpdate();

                    if (count == 1) {
                        pstmt2 = con.prepareStatement(deleteResourceProperty);
                        pstmt2.setInt(1, resource.getResourceId());
                        count += pstmt2.executeUpdate();
                    }

                    if (count >= 1 && resource.getPropertyList() != null) {
                        // 同じSQL文を繰り返し
                        pstmt3 = con.prepareStatement(insertResourceProperty);
                        for (Property property : resource.getPropertyList()) {
                            pstmt3.setInt(1, resource.getResourceId());
                            pstmt3.setInt(2, property.getPropertyId());
                            count += pstmt3.executeUpdate();
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
                // PreparedStatementのクローズ
                try {
                    dbHelper.closeResource(pstmt1);
                    dbHelper.closeResource(pstmt2);
                    dbHelper.closeResource(pstmt3);
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
     * リソース情報を削除済みに更新する
     *
     * @param resourceId リソースID
     * @return 更新件数
     * @throws DataAccessException
     */
    public int editToDeleted(int resourceId) throws DataAccessException {

        String updateResource = "update resources set"
                + " deleted = 1"
                + " where resource_id = ?";
        PreparedStatement pstmt = null;
        int count = 0;

        DBHelper dbHelper = new DBHelper();

        Connection con = dbHelper.connectDb();

        if (con != null) {
            try {
                // トランザクション開始
                con.setAutoCommit(false);
                con.commit();

                pstmt = con.prepareStatement(updateResource);
                pstmt.setInt(1, resourceId);
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
