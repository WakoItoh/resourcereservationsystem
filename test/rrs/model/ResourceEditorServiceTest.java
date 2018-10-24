package rrs.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import rrs.dao.DataAccessException;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;
import rrs.dto.Resource;

public class ResourceEditorServiceTest {

    @Test
    public void testValidate() {

        // 入力データチェック結果が取得される
        // 期待結果：trueが返却される

        int resourceId = 1;

        ResourceEditorService service = new ResourceEditorService(resourceId);
        assertThat(service.validate(), is(true));
    }

    @Test
    public void testExecute() throws DataAccessException {

        // リソースIDが正しいとき、フィールドにリソース情報、カテゴリ情報、事業所情報、特性情報が正しくセットされる
        // 事前条件：insert1.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、Resourceが返却される
        // 期待結果：getterの結果、ResourceにresourceId=1/deleted=0のデータが格納されている
        // 期待結果：getterの結果、長さ1のList<Category>が返却される
        // 期待結果：getterの結果、List<Category>にcategoryId=1のデータが格納されている
        // 期待結果：getterの結果、長さ1のList<Office>が返却される
        // 期待結果：getterの結果、List<Office>にofficeId=1のデータが格納されている
        // 期待結果：getterの結果、長さ1のList<Property>が返却される
        // 期待結果：getterの結果、List<Property>にpropertyId=1のデータが格納されている

        int size = 1;
        int resourceId = 1;

        ResourceEditorService service = new ResourceEditorService(resourceId);
        service.execute();
        Resource resource = service.getResource();
        List<Category> categoryList = service.getCategoryList();
        List<Office> officeList = service.getOfficeList();
        List<Property> propertyList = service.getPropertyList();
        assertThat(resource, notNullValue());
        System.out.println(toString(resource));
        assertThat(categoryList, notNullValue());
        assertThat(categoryList.size(), is(size));
        for (Category category : categoryList) {
            System.out.println(category.getCategoryId() + " " + category.getCategoryName());
        }
        assertThat(officeList, notNullValue());
        assertThat(officeList.size(), is(size));
        for (Office office : officeList) {
            System.out.println(office.getOfficeId() + " " + office.getOfficeName() + " " + office.getOfficeLocation());
        }
        assertThat(propertyList, notNullValue());
        assertThat(propertyList.size(), is(size));
        for (Property property : propertyList) {
            System.out.println(property.getPropertyId() + " " + property.getPropertyName());
        }
    }

    @Test
    public void testExecuteResourceIdInvalid() throws DataAccessException {

        // リソースIDが正しくないとき、フィールドにリソース情報がセットされない
        // 事前条件：insert1.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、nullが返却される
        // 期待結果：getterの結果、長さ1のList<Category>が返却される
        // 期待結果：getterの結果、List<Category>にcategoryId=1のデータが格納されている
        // 期待結果：getterの結果、長さ1のList<Office>が返却される
        // 期待結果：getterの結果、List<Office>にofficeId=1のデータが格納されている
        // 期待結果：getterの結果、長さ1のList<Property>が返却される
        // 期待結果：getterの結果、List<Property>にpropertyId=1のデータが格納されている

        int size = 1;
        int resourceId = 3;

        ResourceEditorService service = new ResourceEditorService(resourceId);
        service.execute();
        Resource resource = service.getResource();
        List<Category> categoryList = service.getCategoryList();
        List<Office> officeList = service.getOfficeList();
        List<Property> propertyList = service.getPropertyList();
        assertThat(resource, nullValue());
        assertThat(categoryList, notNullValue());
        assertThat(categoryList.size(), is(size));
        for (Category category : categoryList) {
            System.out.println(category.getCategoryId() + " " + category.getCategoryName());
        }
        assertThat(officeList, notNullValue());
        assertThat(officeList.size(), is(size));
        for (Office office : officeList) {
            System.out.println(office.getOfficeId() + " " + office.getOfficeName() + " " + office.getOfficeLocation());
        }
        assertThat(propertyList, notNullValue());
        assertThat(propertyList.size(), is(size));
        for (Property property : propertyList) {
            System.out.println(property.getPropertyId() + " " + property.getPropertyName());
        }
    }

    @Test
    public void testExecuteResourceIdDeleted() throws DataAccessException {

        // リソースIDが削除済みのとき、フィールドにリソース情報がセットされない
        // 事前条件：insert1.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、nullが返却される
        // 期待結果：getterの結果、長さ1のList<Category>が返却される
        // 期待結果：getterの結果、List<Category>にcategoryId=1のデータが格納されている
        // 期待結果：getterの結果、長さ1のList<Office>が返却される
        // 期待結果：getterの結果、List<Office>にofficeId=1のデータが格納されている
        // 期待結果：getterの結果、長さ1のList<Property>が返却される
        // 期待結果：getterの結果、List<Property>にpropertyId=1のデータが格納されている

        int size = 1;
        int resourceId = 2;

        ResourceEditorService service = new ResourceEditorService(resourceId);
        service.execute();
        Resource resource = service.getResource();
        List<Category> categoryList = service.getCategoryList();
        List<Office> officeList = service.getOfficeList();
        List<Property> propertyList = service.getPropertyList();
        assertThat(resource, nullValue());
        assertThat(categoryList, notNullValue());
        assertThat(categoryList.size(), is(size));
        for (Category category : categoryList) {
            System.out.println(category.getCategoryId() + " " + category.getCategoryName());
        }
        assertThat(officeList, notNullValue());
        assertThat(officeList.size(), is(size));
        for (Office office : officeList) {
            System.out.println(office.getOfficeId() + " " + office.getOfficeName() + " " + office.getOfficeLocation());
        }
        assertThat(propertyList, notNullValue());
        assertThat(propertyList.size(), is(size));
        for (Property property : propertyList) {
            System.out.println(property.getPropertyId() + " " + property.getPropertyName());
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

}
