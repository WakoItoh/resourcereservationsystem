package rrs.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import rrs.dto.Property;
import rrs.dto.Resource;

public class ResourceDaoTest1 {

    @Test
    public void testQuery() throws DataAccessException {

        // 削除済みを除くかどうかがfalseのとき、リソース情報が正しく取得される
        // 事前条件：insert1.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：長さ2のList<Resource>が返却される
        // 期待結果：List<Resource>にresourceId=1/deleted=0,resourceId=2/deleted=1のデータが格納されている

        int size = 2;
        boolean notDeleted = false;

        ResourceDao dao = new ResourceDao();
        List<Resource> resourceList = dao.query(notDeleted);
        assertThat(resourceList, notNullValue());
        assertThat(resourceList.size(), is(size));
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test
    public void testQueryNotDeleted() throws DataAccessException {

        // 削除済みを除くかどうかがtrueのとき、リソース情報が正しく取得される
        // 事前条件：insert1.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：長さ1のList<Resource>が返却される
        // 期待結果：List<Resource>にresourceId=1/deleted=0のデータが格納されている

        int size = 1;
        boolean notDeleted = true;

        ResourceDao dao = new ResourceDao();
        List<Resource> resourceList = dao.query(notDeleted);
        assertThat(resourceList, notNullValue());
        assertThat(resourceList.size(), is(size));
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test
    public void testQueryByResourceId() throws DataAccessException {

        // リソースIDが正しく、削除済みを除くかどうかがfalseのとき、リソース情報が正しく取得される
        // 事前条件：insert1.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：Resourceが返却される
        // 期待結果：ResourceにresourceId=2/deleted=1のデータが格納されている

        int resourceId = 2;
        boolean notDeleted = false;

        ResourceDao dao = new ResourceDao();
        Resource resource = dao.queryByResourceId(resourceId, notDeleted);
        assertThat(resource, notNullValue());
        System.out.println(toString(resource));
    }

    @Test
    public void testQueryByResourceIdNotDeleted() throws DataAccessException {

        // リソースIDが正しく、削除済みを除くかどうかがtrueのとき、リソース情報が正しく取得される
        // 事前条件：insert1.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：Resourceが返却される
        // 期待結果：ResourceにresourceId=1/deleted=0のデータが格納されている

        int resourceId = 1;
        boolean notDeleted = true;

        ResourceDao dao = new ResourceDao();
        Resource resource = dao.queryByResourceId(resourceId, notDeleted);
        assertThat(resource, notNullValue());
        System.out.println(toString(resource));
    }

    @Test
    public void testQueryByResourceIdInvalid() throws DataAccessException {

        // リソースIDが正しくないとき、リソース情報が正しく取得される
        // 事前条件：insert1.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：nullが返却される

        int resourceId = 3;
        boolean notDeleted = false;

        ResourceDao dao = new ResourceDao();
        Resource resource = dao.queryByResourceId(resourceId, notDeleted);
        assertThat(resource, nullValue());
    }

    @Test
    public void testQueryByResourceIdDeletedNotDeleted() throws DataAccessException {

        // リソースIDが削除済みで、削除済みを除くかどうかがtrueのとき、リソース情報が正しく取得される
        // 事前条件：insert1.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：nullが返却される

        int resourceId = 2;
        boolean notDeleted = true;

        ResourceDao dao = new ResourceDao();
        Resource resource = dao.queryByResourceId(resourceId, notDeleted);
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
