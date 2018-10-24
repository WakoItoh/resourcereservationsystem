package rrs.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import rrs.dao.DataAccessException;
import rrs.dto.Property;
import rrs.dto.Resource;
import rrs.dto.User;

public class ResourceDetailServiceTest {

    @Test
    public void testValidate() {

        // 入力データチェック結果が取得される
        // 期待結果：trueが返却される

        String id = "u0000001";
        String password = "pa55w0rd";
        String lastName = "仲町台";
        String firstName = "太郎";
        int userLevel = 2;
        String phone = "0123456789";
        String email = "u0000001@example.com";
        User loginUser = new User(id, password, lastName, firstName, userLevel, phone, email);
        int resourceId = 2;

        ResourceDetailService service = new ResourceDetailService(loginUser, resourceId);
        assertThat(service.validate(), is(true));
    }

    @Test
    public void testExecute() throws DataAccessException {

        // リソースIDが正しく、ユーザー情報の権限がリソース管理者(2)のとき、フィールドにリソース情報が正しくセットされる
        // 事前条件：insert1.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、Resourceが返却される
        // 期待結果：getterの結果、ResourceにresourceId=2/deleted=1のデータが格納されている

        String id = "u0000001";
        String password = "pa55w0rd";
        String lastName = "仲町台";
        String firstName = "太郎";
        int userLevel = 2;
        String phone = "0123456789";
        String email = "u0000001@example.com";
        User loginUser = new User(id, password, lastName, firstName, userLevel, phone, email);
        int resourceId = 2;

        ResourceDetailService service = new ResourceDetailService(loginUser, resourceId);
        service.execute();
        Resource resource = service.getResource();
        assertThat(resource, notNullValue());
        System.out.println(toString(resource));
    }

    @Test
    public void testExecuteNotAdmin() throws DataAccessException {

        // リソースIDが正しく、ユーザー情報の権限が利用者(1)のとき、フィールドにリソース情報が正しくセットされる
        // 事前条件：insert1.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、Resourceが返却される
        // 期待結果：getterの結果、ResourceにresourceId=2/deleted=1のデータが格納されている

        String id = "u0000002";
        String password = "pa55w0rd";
        String lastName = "都築";
        String firstName = "花子";
        int userLevel = 1;
        String phone = "0987654321";
        String email = "u0000002@example.com";
        User loginUser = new User(id, password, lastName, firstName, userLevel, phone, email);
        int resourceId = 1;

        ResourceDetailService service = new ResourceDetailService(loginUser, resourceId);
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

        String id = "u0000001";
        String password = "pa55w0rd";
        String lastName = "仲町台";
        String firstName = "太郎";
        int userLevel = 2;
        String phone = "0123456789";
        String email = "u0000001@example.com";
        User loginUser = new User(id, password, lastName, firstName, userLevel, phone, email);
        int resourceId = 3;

        ResourceDetailService service = new ResourceDetailService(loginUser, resourceId);
        service.execute();
        Resource resource = service.getResource();
        assertThat(resource, nullValue());
    }

    @Test
    public void testExecuteResourceIdDeleted() throws DataAccessException {

        // リソースIDが削除済みで、ユーザー情報の権限が利用者(1)のとき、フィールドにリソース情報がセットされない
        // 事前条件：insert1.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、nullが返却される

        String id = "u0000002";
        String password = "pa55w0rd";
        String lastName = "都築";
        String firstName = "花子";
        int userLevel = 1;
        String phone = "0987654321";
        String email = "u0000002@example.com";
        User loginUser = new User(id, password, lastName, firstName, userLevel, phone, email);
        int resourceId = 2;

        ResourceDetailService service = new ResourceDetailService(loginUser, resourceId);
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
