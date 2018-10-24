package rrs.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import rrs.dao.DataAccessException;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;
import rrs.dto.Resource;

public class ResourceRegisterServiceTestException {

    @Test(expected = DataAccessException.class)
    public void testExecute() throws DataAccessException {

        // データベース接続に失敗したとき、例外が発生する
        // 事前条件：データベースが停止している
        // 期待結果：DataAccessExceptionが発生する

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜A");
        resourceRegistered.setCategory(new Category(2, null));
        resourceRegistered.setCapacity(100);
        resourceRegistered.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceRegistered.setPropertyList(propertyList);
        resourceRegistered.setNote("単体テスト");
        resourceRegistered.setSuspendStart(null);
        resourceRegistered.setSuspendEnd(null);

        ResourceRegisterService service = new ResourceRegisterService(resourceRegistered);
        service.execute();
    }

}
