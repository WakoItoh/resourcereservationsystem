package rrs.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import rrs.dto.Property;
import rrs.dto.Resource;

public class ResourceDaoTest6 {

    @Test
    public void testEditToDeleted() throws DataAccessException {

        // リソースIDが正しいとき、リソース情報が正しく更新される
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：1が返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1/deleted=1,resourceId=2のデータが格納されている

        int resourceId = 1;

        ResourceDao dao = new ResourceDao();
        int result = dao.editToDeleted(resourceId);
        assertThat(result, is(1));
        List<Resource> resourceList = dao.query(false);
        for (Resource resource : resourceList) {
            System.out.println(toString(resource));
        }
    }

    @Test
    public void testEditToDeletedResourceIdInvalid() throws DataAccessException {

        // リソースIDが正しくないとき、リソース情報が正しく更新される
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：0が返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2のデータが格納されており、resourceId=3/deleted=1のデータが格納されていない

        int resourceId = 3;

        ResourceDao dao = new ResourceDao();
        int result = dao.editToDeleted(resourceId);
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

}
