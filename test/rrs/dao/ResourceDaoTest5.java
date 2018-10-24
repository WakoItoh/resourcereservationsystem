package rrs.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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

public class ResourceDaoTest5 {

    @Test
    public void testEditNull() throws DataAccessException {

        // リソース情報がnullのとき、リソース情報が正しく更新されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：0が返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されている

        Resource resourceEdited = null;

        ResourceDao dao = new ResourceDao();
        int result = dao.edit(resourceEdited);
        assertThat(result, is(0));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test(expected = DataAccessException.class)
    public void testEditResourceNameNull() throws DataAccessException {

        // リソース情報のリソース名がnullのとき、リソース情報が正しく更新されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=1/resource_name=nullのデータが格納されていない

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(1);
        resourceEdited.setResourceName(null);
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
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test(expected = DataAccessException.class)
    public void testEditCategoryNull() throws DataAccessException {

        // リソース情報のカテゴリがnullのとき、リソース情報が正しく更新されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=1/category_id=nullのデータが格納されていない

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(1);
        resourceEdited.setResourceName("新横浜B");
        resourceEdited.setCategory(null);
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
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test(expected = DataAccessException.class)
    public void testEditCategoryInvalid() throws DataAccessException {

        // リソース情報のカテゴリが正しくないとき、リソース情報が正しく挿入されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=1/category_id=5のデータが格納されていない

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(1);
        resourceEdited.setResourceName("新横浜C");
        resourceEdited.setCategory(new Category(5, null));
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
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test(expected = DataAccessException.class)
    public void testEditOfficeNull() throws DataAccessException {

        // リソース情報の事業所がnullのとき、リソース情報が正しく更新されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=1/office_id=nullのデータが格納されていない

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(1);
        resourceEdited.setResourceName("新横浜D");
        resourceEdited.setCategory(new Category(2, null));
        resourceEdited.setCapacity(100);
        resourceEdited.setOffice(null);
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceEdited.setPropertyList(propertyList);
        resourceEdited.setNote("単体テスト");
        resourceEdited.setSuspendStart(toDate("2018-10-01 00:00"));
        resourceEdited.setSuspendEnd(toDate("2018-10-31 23:00"));

        ResourceDao dao = new ResourceDao();
        dao.edit(resourceEdited);
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test(expected = DataAccessException.class)
    public void testEditOfficeInvalid() throws DataAccessException {

        // リソース情報の事業所が正しくないとき、リソース情報が正しく挿入されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=1/office_id=9のデータが格納されていない

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(1);
        resourceEdited.setResourceName("新横浜D");
        resourceEdited.setCategory(new Category(2, null));
        resourceEdited.setCapacity(100);
        resourceEdited.setOffice(new Office(9, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceEdited.setPropertyList(propertyList);
        resourceEdited.setNote("単体テスト");
        resourceEdited.setSuspendStart(toDate("2018-10-01 00:00"));
        resourceEdited.setSuspendEnd(toDate("2018-10-31 23:00"));

        ResourceDao dao = new ResourceDao();
        dao.edit(resourceEdited);
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test
    public void testEditPropertyNull() throws DataAccessException {

        // リソース情報の特性がnullのとき、リソース情報が正しく更新される
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：2が返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1/resource_properties.property_id=null,resourceId=2のデータが格納されている

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(1);
        resourceEdited.setResourceName("新横浜F");
        resourceEdited.setCategory(new Category(2, null));
        resourceEdited.setCapacity(100);
        resourceEdited.setOffice(new Office(5, null, null));
        List<Property> propertyList = null;
        resourceEdited.setPropertyList(propertyList);
        resourceEdited.setNote("単体テスト");
        resourceEdited.setSuspendStart(toDate("2018-10-01 00:00"));
        resourceEdited.setSuspendEnd(toDate("2018-10-31 23:00"));

        ResourceDao dao = new ResourceDao();
        int result = dao.edit(resourceEdited);
        assertThat(result, is(2));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test(expected = DataAccessException.class)
    public void testEditPropertyInvalid() throws DataAccessException {

        // リソース情報の特性が正しくないとき、リソース情報が正しく挿入されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=1/resource_properties.property_id=7のデータが格納されていない

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(1);
        resourceEdited.setResourceName("新横浜F");
        resourceEdited.setCategory(new Category(2, null));
        resourceEdited.setCapacity(100);
        resourceEdited.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(7, null));
        resourceEdited.setPropertyList(propertyList);
        resourceEdited.setNote("単体テスト");
        resourceEdited.setSuspendStart(toDate("2018-10-01 00:00"));
        resourceEdited.setSuspendEnd(toDate("2018-10-31 23:00"));

        ResourceDao dao = new ResourceDao();
        dao.edit(resourceEdited);
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test
    public void testEditNoteNull() throws DataAccessException {

        // リソース情報の補足がnullのとき、リソース情報が正しく更新される
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：3が返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1/note=null,resourceId=2のデータが格納されている

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(1);
        resourceEdited.setResourceName("新横浜H");
        resourceEdited.setCategory(new Category(2, null));
        resourceEdited.setCapacity(100);
        resourceEdited.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceEdited.setPropertyList(propertyList);
        resourceEdited.setNote(null);
        resourceEdited.setSuspendStart(toDate("2018-10-01 00:00"));
        resourceEdited.setSuspendEnd(toDate("2018-10-31 23:00"));

        ResourceDao dao = new ResourceDao();
        int result = dao.edit(resourceEdited);
        assertThat(result, is(3));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test
    public void testEditSuspendStartNull() throws DataAccessException {

        // リソース情報の利用停止開始日時がnullのとき、リソース情報が正しく更新される
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：3が返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1/suspend_start=null,resourceId=2のデータが格納されている

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(1);
        resourceEdited.setResourceName("新横浜I");
        resourceEdited.setCategory(new Category(2, null));
        resourceEdited.setCapacity(100);
        resourceEdited.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceEdited.setPropertyList(propertyList);
        resourceEdited.setNote("単体テスト");
        resourceEdited.setSuspendStart(null);
        resourceEdited.setSuspendEnd(toDate("2018-10-31 23:00"));

        ResourceDao dao = new ResourceDao();
        int result = dao.edit(resourceEdited);
        assertThat(result, is(3));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test
    public void testEditSuspendEndNull() throws DataAccessException {

        // リソース情報の利用停止終了日時がnullのとき、リソース情報が正しく更新される
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：3が返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1/suspend_end=null,resourceId=2のデータが格納されている

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(1);
        resourceEdited.setResourceName("新横浜J");
        resourceEdited.setCategory(new Category(2, null));
        resourceEdited.setCapacity(100);
        resourceEdited.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceEdited.setPropertyList(propertyList);
        resourceEdited.setNote("単体テスト");
        resourceEdited.setSuspendStart(toDate("2018-10-01 00:00"));
        resourceEdited.setSuspendEnd(null);

        ResourceDao dao = new ResourceDao();
        int result = dao.edit(resourceEdited);
        assertThat(result, is(3));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test
    public void testEditResourceIdInvalid() throws DataAccessException {

        // リソース情報のリソースIDが正しくないとき、リソース情報が正しく更新されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：0が返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=3のデータが格納されていない

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(3);
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
        int result = dao.edit(resourceEdited);
        assertThat(result, is(0));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test
    public void testEditDeleted() throws DataAccessException {

        // リソース情報のリソースIDが削除済みのとき、リソース情報が正しく更新されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：0が返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=2/resource_name="新横浜A"のデータが格納されていない

        Resource resourceEdited = new Resource();
        resourceEdited.setResourceId(2);
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
        int result = dao.edit(resourceEdited);
        assertThat(result, is(0));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    private String toString(Resource resource) {

        String result = resource.getResourceId()
                + " " + resource.getResourceName()
                + " " + resource.getCategory().getCategoryId()
                + " " + resource.getCategory().getCategoryName()
                + " " + resource.getCapacity()
                + " " + resource.getOffice().getOfficeId()
                + " " + resource.getOffice().getOfficeName()
                + " " + toString(resource.getPropertyList())
                + " " + resource.getNote()
                + " " + resource.getSuspendStart()
                + " " + resource.getSuspendEnd()
                + " " + resource.getDeleted()
                + " " + resource.getStatus();

        return result;
    }

    private String toString(List<Property> propertyList) {

        StringBuffer result = new StringBuffer();

        result.append("{ ");

        if (propertyList != null) {
            for (Property property : propertyList) {
                result.append(property.getPropertyId());
                result.append(" ");
                result.append(property.getPropertyName());
                result.append(", ");
            }
        }

        result.append("}");

        return result.toString();
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
