package rrs.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import rrs.dao.DataAccessException;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;
import rrs.dto.Resource;

public class ResourceEditServiceTest {

    @Test
    public void testValidate() {

        // リソース情報が適切であるとき、入力データチェック結果が取得される
        // 期待結果：trueが返却される
        // 期待結果：getterの結果、長さ0のList<String>が返却される

        int size = 0;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(true));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
    }

    @Test
    public void testValidateNull() {

        // リソース情報がnullであるとき、フィールドにエラーメッセージがセットされ、入力データチェック結果が取得される
        // 期待結果：falseが返却される
        // 期待結果：getterの結果、長さ1のList<String>が返却される
        // 期待結果：getterの結果、List<String>にEM_RSC_022が格納されている

        int size = 1;
        Resource resource = null;

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(false));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
        for (String errorMessage : errorMessageList) {
            System.out.println(errorMessage);
        }
    }

    @Test
    public void testValidateResourceName() {

        // リソース情報のリソース名が30文字以下であるとき、入力データチェック結果が取得される
        // 期待結果：trueが返却される
        // 期待結果：getterの結果、長さ0のList<String>が返却される

        int size = 0;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(true));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
    }

    @Test
    public void testValidateResourceNameGreater() {

        // リソース情報のリソース名が30文字超であるとき、フィールドにエラーメッセージがセットされ、入力データチェック結果が取得される
        // 期待結果：falseが返却される
        // 期待結果：getterの結果、長さ1のList<String>が返却される
        // 期待結果：getterの結果、List<String>にEM_RSC_004が格納されている

        int size = 1;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(false));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
        for (String errorMessage : errorMessageList) {
            System.out.println(errorMessage);
        }
    }

    @Test
    public void testValidateCapacityMin() {

        // リソース情報の定員が0以上であるとき、入力データチェック結果が取得される
        // 期待結果：trueが返却される
        // 期待結果：getterの結果、長さ0のList<String>が返却される

        int size = 0;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(0);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(true));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
    }

    @Test
    public void testValidateCapacityMinLess() {

        // リソース情報の定員が0未満であるとき、フィールドにエラーメッセージがセットされ、入力データチェック結果が取得される
        // 期待結果：falseが返却される
        // 期待結果：getterの結果、長さ1のList<String>が返却される
        // 期待結果：getterの結果、List<String>にEM_RSC_009が格納されている

        int size = 1;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(-1);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(false));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
        for (String errorMessage : errorMessageList) {
            System.out.println(errorMessage);
        }
    }

    @Test
    public void testValidateCapacityMax() {

        // リソース情報の定員が999以下であるとき、入力データチェック結果が取得される
        // 期待結果：trueが返却される
        // 期待結果：getterの結果、長さ0のList<String>が返却される

        int size = 0;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(999);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(true));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
    }

    @Test
    public void testValidateCapacityMaxGrater() {

        // リソース情報の定員が999超であるとき、フィールドにエラーメッセージがセットされ、入力データチェック結果が取得される
        // 期待結果：falseが返却される
        // 期待結果：getterの結果、長さ1のList<String>が返却される
        // 期待結果：getterの結果、List<String>にEM_RSC_009が格納されている

        int size = 1;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(1000);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(false));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
        for (String errorMessage : errorMessageList) {
            System.out.println(errorMessage);
        }
    }

    @Test
    public void testValidateNote() {

        // リソース情報の補足が500文字以下であるとき、入力データチェック結果が取得される
        // 期待結果：trueが返却される
        // 期待結果：getterの結果、長さ0のList<String>が返却される

        int size = 0;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九百一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九百一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九百一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九百一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九百");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(true));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
    }

    @Test
    public void testValidateNoteGreater() {

        // リソース情報の補足が500文字超であるとき、フィールドにエラーメッセージがセットされ、入力データチェック結果が取得される
        // 期待結果：falseが返却される
        // 期待結果：getterの結果、長さ1のList<String>が返却される
        // 期待結果：getterの結果、List<String>にEM_RSC_013が格納されている

        int size = 1;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九百一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九百一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九百一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九百一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九百一");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(false));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
        for (String errorMessage : errorMessageList) {
            System.out.println(errorMessage);
        }
    }

    @Test
    public void testValidateSuspendStartNullSuspendEndNull() {

        // リソース情報の利用停止開始日時がnullかつ使用停止終了日時がnullであるとき、入力データチェック結果が取得される
        // 期待結果：trueが返却される
        // 期待結果：getterの結果、長さ0のList<String>が返却される

        int size = 0;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(null);
        resource.setSuspendEnd(null);

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(true));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
    }

    @Test
    public void testValidateSuspendStartNull() {

        // リソース情報の利用停止開始日時がnullかつ使用停止終了日時がnullでないとき、フィールドにエラーメッセージがセットされ、入力データチェック結果が取得される
        // 期待結果：falseが返却される
        // 期待結果：getterの結果、長さ1のList<String>が返却される
        // 期待結果：getterの結果、List<String>にEM_RSC_016が格納されている

        int size = 1;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(null);
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(false));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
        for (String errorMessage : errorMessageList) {
            System.out.println(errorMessage);
        }
    }

    @Test
    public void testValidateSuspendEndNull() {

        // リソース情報の利用停止開始日時がnullでないかつ使用停止終了日時がnullのとき、フィールドにエラーメッセージがセットされ、入力データチェック結果が取得される
        // 期待結果：falseが返却される
        // 期待結果：getterの結果、長さ1のList<String>が返却される
        // 期待結果：getterの結果、List<String>にEM_RSC_019が格納されている

        int size = 1;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-31 23:00"));
        resource.setSuspendEnd(null);

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(false));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
        for (String errorMessage : errorMessageList) {
            System.out.println(errorMessage);
        }
    }

    @Test
    public void testValidateSuspendStartLessThanSuspendEnd() {

        // リソース情報の利用停止開始日時が利用停止終了日時未満であるとき、入力データチェック結果が取得される
        // 期待結果：trueが返却される
        // 期待結果：getterの結果、長さ0のList<String>が返却される

        int size = 0;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-31 22:59"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(true));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
    }

    @Test
    public void testValidateSuspendStartGreaterThanEqualSuspendEnd() {

        // リソース情報の利用停止開始日時が利用停止終了日時以上であるとき、フィールドにエラーメッセージがセットされ、入力データチェック結果が取得される
        // 期待結果：falseが返却される
        // 期待結果：getterの結果、長さ1のList<String>が返却される
        // 期待結果：getterの結果、List<String>にEM_RSC_020が格納されている

        int size = 1;
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-31 23:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        assertThat(service.validate(), is(false));
        List<String> errorMessageList = service.getErrorMessageList();
        assertThat(errorMessageList, notNullValue());
        assertThat(errorMessageList.size(), is(size));
        for (String errorMessage : errorMessageList) {
            System.out.println(errorMessage);
        }
    }

    @Test
    public void testExecute() throws DataAccessException {

        // リソース情報が適切であるとき、フィールドに更新件数がセットされる
        // 事前条件：insert2.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、3が返却される

        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        service.execute();
        int count = service.getCount();
        assertThat(count, is(3));
    }

    @Test
    public void testExecuteNull() throws DataAccessException {

        // リソース情報がnullであるとき、フィールドに更新件数がセットされない
        // 事前条件：insert2.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、0が返却される

        Resource resource = null;

        ResourceEditService service = new ResourceEditService(resource);
        service.execute();
        int count = service.getCount();
        assertThat(count, is(0));
    }

    @Test
    public void testExecuteResourceIdInvalid() throws DataAccessException {

        // リソース情報のリソースIDが正しくないとき、フィールドに更新件数がセットされる
        // 事前条件：insert2.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、0が返却される

        Resource resource = new Resource();
        resource.setResourceId(3);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        service.execute();
        int count = service.getCount();
        assertThat(count, is(0));
    }

    @Test
    public void testExecuteDeleted() throws DataAccessException {

        // リソース情報のリソースIDが削除済みのとき、フィールドに更新件数がセットされる
        // 事前条件：insert2.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、0が返却される

        Resource resource = new Resource();
        resource.setResourceId(2);
        resource.setResourceName("新横浜A");
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        service.execute();
        int count = service.getCount();
        assertThat(count, is(0));
    }

    @Test(expected = DataAccessException.class)
    public void testExecuteInvalid() throws DataAccessException {

        // リソース情報が適切でないとき、フィールドに新しいリソースIDがセットされない
        // 事前条件：insert2.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：DataAccessExceptionが発生する

        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setResourceName(null);
        resource.setCategory(new Category(2, null));
        resource.setCapacity(100);
        resource.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resource.setPropertyList(propertyList);
        resource.setNote("単体テスト");
        resource.setSuspendStart(toDate("2018-12-01 00:00"));
        resource.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceEditService service = new ResourceEditService(resource);
        service.execute();
//        int resourceId = service.getResourceId();
//        assertThat(resourceId, is(0));
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
