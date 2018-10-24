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

public class ResourceDaoTest3 {

    @Test
    public void testRegisterNull() throws DataAccessException {

        // リソース情報がnullのとき、リソース情報が正しく挿入されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：0が返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=3(またはそれ以上)のデータが格納されていない

        Resource resourceRegistered = null;

        ResourceDao dao = new ResourceDao();
        int result = dao.register(resourceRegistered);
        assertThat(result, is(0));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test(expected = DataAccessException.class)
    public void testRegisterResourceNameNull() throws DataAccessException {

        // リソース情報のリソース名がnullのとき、リソース情報が正しく挿入されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=3(またはそれ以上)/resource_name=nullのデータが格納されていない

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName(null);
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
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test(expected = DataAccessException.class)
    public void testRegisterCategoryNull() throws DataAccessException {

        // リソース情報のカテゴリがnullのとき、リソース情報が正しく挿入されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=3(またはそれ以上)/category_id=nullのデータが格納されていない

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜B");
        resourceRegistered.setCategory(null);
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
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test(expected = DataAccessException.class)
    public void testRegisterCategoryInvalid() throws DataAccessException {

        // リソース情報のカテゴリが正しくないとき、リソース情報が正しく挿入されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=3(またはそれ以上)/category_id=5のデータが格納されていない

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜C");
        resourceRegistered.setCategory(new Category(5, null));
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
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test(expected = DataAccessException.class)
    public void testRegisterOfficeNull() throws DataAccessException {

        // リソース情報の事業所がnullのとき、リソース情報が正しく挿入されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=3(またはそれ以上)/office_id=nullのデータが格納されていない

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜D");
        resourceRegistered.setCategory(new Category(2, null));
        resourceRegistered.setCapacity(100);
        resourceRegistered.setOffice(null);
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceRegistered.setPropertyList(propertyList);
        resourceRegistered.setNote("単体テスト");
        resourceRegistered.setSuspendStart(toDate("2018-12-01 00:00"));
        resourceRegistered.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceDao dao = new ResourceDao();
        dao.register(resourceRegistered);
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test(expected = DataAccessException.class)
    public void testRegisterOfficeInvalid() throws DataAccessException {

        // リソース情報の事業所が正しくないとき、リソース情報が正しく挿入されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=3(またはそれ以上)/office_id=9のデータが格納されていない

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜E");
        resourceRegistered.setCategory(new Category(2, null));
        resourceRegistered.setCapacity(100);
        resourceRegistered.setOffice(new Office(9, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceRegistered.setPropertyList(propertyList);
        resourceRegistered.setNote("単体テスト");
        resourceRegistered.setSuspendStart(toDate("2018-12-01 00:00"));
        resourceRegistered.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceDao dao = new ResourceDao();
        dao.register(resourceRegistered);
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test
    public void testRegisterPropertyNull() throws DataAccessException {

        // リソース情報の特性がnullのとき、リソース情報が正しく挿入される
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：リソースIDが返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2,resourceId=3(またはそれ以上)/resource_properties.property_id=nullのデータが格納されている

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜F");
        resourceRegistered.setCategory(new Category(2, null));
        resourceRegistered.setCapacity(100);
        resourceRegistered.setOffice(new Office(5, null, null));
        List<Property> propertyList = null;
        resourceRegistered.setPropertyList(propertyList);
        resourceRegistered.setNote("単体テスト");
        resourceRegistered.setSuspendStart(toDate("2018-12-01 00:00"));
        resourceRegistered.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceDao dao = new ResourceDao();
        int result = dao.register(resourceRegistered);
        assertThat(result, not(0));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test(expected = DataAccessException.class)
    public void testRegisterPropertyInvalid() throws DataAccessException {

        // リソース情報の特性が正しくないとき、リソース情報が正しく挿入されない
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する
        //// 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=3(またはそれ以上)/resource_properties.property_id=7のデータが格納されていない

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜G");
        resourceRegistered.setCategory(new Category(2, null));
        resourceRegistered.setCapacity(100);
        resourceRegistered.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(7, null));
        resourceRegistered.setPropertyList(propertyList);
        resourceRegistered.setNote("単体テスト");
        resourceRegistered.setSuspendStart(toDate("2018-12-01 00:00"));
        resourceRegistered.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceDao dao = new ResourceDao();
        dao.register(resourceRegistered);
//        List<Resource> resourceList = dao.query(false);
//        for (Resource resource : resourceList) {
//            System.out.println(toString(resource));
//        }
    }

    @Test
    public void testRegisterNoteNull() throws DataAccessException {

        // リソース情報の補足がnullのとき、リソース情報が正しく挿入される
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：リソースIDが返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2,resourceId=3(またはそれ以上)/note=nullのデータが格納されている

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜H");
        resourceRegistered.setCategory(new Category(2, null));
        resourceRegistered.setCapacity(100);
        resourceRegistered.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceRegistered.setPropertyList(propertyList);
        resourceRegistered.setNote(null);
        resourceRegistered.setSuspendStart(toDate("2018-12-01 00:00"));
        resourceRegistered.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceDao dao = new ResourceDao();
        int result = dao.register(resourceRegistered);
        assertThat(result, not(0));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test
    public void testRegisterSuspendStartNull() throws DataAccessException {

        // リソース情報の利用停止開始日時がnullのとき、リソース情報が正しく挿入される
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：リソースIDが返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2,resourceId=3(またはそれ以上)/suspend_start=nullのデータが格納されている

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜I");
        resourceRegistered.setCategory(new Category(2, null));
        resourceRegistered.setCapacity(100);
        resourceRegistered.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceRegistered.setPropertyList(propertyList);
        resourceRegistered.setNote("単体テスト");
        resourceRegistered.setSuspendStart(null);
        resourceRegistered.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceDao dao = new ResourceDao();
        int result = dao.register(resourceRegistered);
        assertThat(result, not(0));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test
    public void testRegisterSuspendEndNull() throws DataAccessException {

        // リソース情報の利用停止終了日時がnullのとき、リソース情報が正しく挿入される
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：リソースIDが返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2,resourceId=3(またはそれ以上)/suspend_end=nullのデータが格納されている

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜J");
        resourceRegistered.setCategory(new Category(2, null));
        resourceRegistered.setCapacity(100);
        resourceRegistered.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceRegistered.setPropertyList(propertyList);
        resourceRegistered.setNote("単体テスト");
        resourceRegistered.setSuspendStart(toDate("2018-12-01 00:00"));
        resourceRegistered.setSuspendEnd(null);

        ResourceDao dao = new ResourceDao();
        int result = dao.register(resourceRegistered);
        assertThat(result, not(0));
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
