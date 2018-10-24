package rrs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;
import rrs.dto.Resource;

public class ResourceDaoTestException {

    @Test(expected = DataAccessException.class)
    public void testQuery() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

        boolean notDeleted = false;

        ResourceDao dao = new ResourceDao();
        dao.query(notDeleted);
    }

    @Test(expected = DataAccessException.class)
    public void testQueryByResourceId() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

        int resourceId = 2;
        boolean notDeleted = false;

        ResourceDao dao = new ResourceDao();
        dao.queryByResourceId(resourceId, notDeleted);
    }

    @Test(expected = DataAccessException.class)
    public void testRegister() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜A");
        resourceRegistered.setCategory(new Category(2, null));
        resourceRegistered.setCapacity(100);
        resourceRegistered.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceRegistered.setPropertyList(propertyList);
        resourceRegistered.setNote("単体テスト");
        resourceRegistered.setSuspendStart(toDate("2018-12-01 00:00"));
        resourceRegistered.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceDao dao = new ResourceDao();
        dao.register(resourceRegistered);
    }

    @Test(expected = DataAccessException.class)
    public void testEdit() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(1);
        resourceEdited.setResourceName("新横浜A");
        resourceEdited.setCategory(new Category(2, null));
        resourceEdited.setCapacity(100);
        resourceEdited.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceEdited.setPropertyList(propertyList);
        resourceEdited.setNote("単体テスト");
        resourceEdited.setSuspendStart(toDate("2018-10-01 00:00"));
        resourceEdited.setSuspendEnd(toDate("2018-10-31 23:00"));

        ResourceDao dao = new ResourceDao();
        dao.edit(resourceEdited);
    }

    @Test(expected = DataAccessException.class)
    public void testEditToDeleted() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

        int resourceId = 1;

        ResourceDao dao = new ResourceDao();
        dao.editToDeleted(resourceId);
    }

    private Date toDate(String stringDate) {

        Date date = null;

        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setLenient(false);

        try {
            date = formatter.parse(stringDate);
        } catch (NullPointerException | ParseException e) {
            return null;
        }

        return date;
    }

}
