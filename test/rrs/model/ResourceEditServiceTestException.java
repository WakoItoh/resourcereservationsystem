package rrs.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import rrs.dao.DataAccessException;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;
import rrs.dto.Resource;

public class ResourceEditServiceTestException {

    @Test(expected = DataAccessException.class)
    public void testExecute() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

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
        service.execute();
    }

}
