package rrs.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import rrs.dao.DataAccessException;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;

public class ResourceRegistratorServiceTest {

    @Test
    public void testValidate() {

        // 入力データチェック結果が取得される
        // 期待結果：trueが返却される

        ResourceRegistratorService service = new ResourceRegistratorService();
        assertThat(service.validate(), is(true));
    }

    @Test
    public void testExecute() throws DataAccessException {

        // フィールドにカテゴリ情報、事業所情報、特性情報が正しくセットされる
        // 事前条件：insert1.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブルに挿入されている
        // 期待結果：getterの結果、長さ1のList<Category>が返却される
        // 期待結果：getterの結果、List<Category>にcategoryId=1のデータが格納されている
        // 期待結果：getterの結果、長さ1のList<Office>が返却される
        // 期待結果：getterの結果、List<Office>にofficeId=1のデータが格納されている
        // 期待結果：getterの結果、長さ1のList<Property>が返却される
        // 期待結果：getterの結果、List<Property>にpropertyId=1のデータが格納されている

        int size = 1;

        ResourceRegistratorService service = new ResourceRegistratorService();
        service.execute();
        List<Category> categoryList = service.getCategoryList();
        List<Office> officeList = service.getOfficeList();
        List<Property> propertyList = service.getPropertyList();
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

}
