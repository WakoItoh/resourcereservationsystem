package rrs.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import rrs.dao.DataAccessException;
import rrs.dto.Property;
import rrs.dto.Resource;

public class ResourceDeleteConfirmServiceTest {

    @Test
    public void testValidate() {

        // 入力データチェック結果が取得される
        // 期待結果：trueが返却される

        int resourceId = 1;

        ResourceDeleteConfirmService service = new ResourceDeleteConfirmService(resourceId);
        assertThat(service.validate(), is(true));
    }

    @Test
    public void testExecute() throws DataAccessException {

        // リソースIDが正しいとき、フィールドにリソース情報が正しくセットされる
        // 事前条件：insert1.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、Resourceが返却される
        // 期待結果：getterの結果、ResourceにresourceId=1/deleted=0のデータが格納されている

        int resourceId = 1;

        ResourceDeleteConfirmService service = new ResourceDeleteConfirmService(resourceId);
        service.execute();
        Resource resource = service.getResource();
        assertThat(resource, notNullValue());
        System.out.println(toString(resource));
    }

    @Test
    public void testExecuteResourceIdInvalid() throws DataAccessException {

        // リソースIDが正しくないとき、フィールドにリソース情報がセットされない
        // 事前条件：insert1.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、nullが返却される

        int resourceId = 3;

        ResourceDeleteConfirmService service = new ResourceDeleteConfirmService(resourceId);
        service.execute();
        Resource resource = service.getResource();
        assertThat(resource, nullValue());
    }

    @Test
    public void testExecuteResourceIdDeleted() throws DataAccessException {

        // リソースIDが削除済みのとき、フィールドにリソース情報がセットされない
        // 事前条件：insert1.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、nullが返却される

        int resourceId = 2;

        ResourceDeleteConfirmService service = new ResourceDeleteConfirmService(resourceId);
        service.execute();
        Resource resource = service.getResource();
        assertThat(resource, nullValue());
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
