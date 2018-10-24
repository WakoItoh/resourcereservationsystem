package rrs.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import rrs.dao.DataAccessException;

public class ResourceDeleteServiceTest {

    @Test
    public void testValidate() {

        // 入力データチェック結果が取得される
        // 期待結果：trueが返却される

        int resourceId = 1;

        ResourceDeleteService service = new ResourceDeleteService(resourceId);
        assertThat(service.validate(), is(true));
    }

    @Test
    public void testExecute() throws DataAccessException {

        // リソースIDが正しいとき、フィールドに更新件数が正しくセットされる
        // 事前条件：insert2.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、1が返却される

        int count = 1;
        int resourceId = 1;

        ResourceDeleteService service = new ResourceDeleteService(resourceId);
        service.execute();
        assertThat(service.getCount(), is(count));
    }

    @Test
    public void testExecuteResourceIdInvalid() throws DataAccessException {

        // リソースIDが正しくないとき、フィールドに更新件数が正しくセットされる
        // 事前条件：insert2.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、0が返却される

        int count = 0;
        int resourceId = 3;

        ResourceDeleteService service = new ResourceDeleteService(resourceId);
        service.execute();
        assertThat(service.getCount(), is(count));
    }

    @Test
    public void testExecuteResourceIdDeleted() throws DataAccessException {

        // リソースIDが削除済みのとき、フィールドに更新件数が正しくセットされる
        // 事前条件：insert2.sqlのデータがcategoriesテーブル、officesテーブル、propertiesテーブル、resourcesテーブルに挿入されている
        // 期待結果：getterの結果、1が返却される

        int count = 1;
        int resourceId = 2;

        ResourceDeleteService service = new ResourceDeleteService(resourceId);
        service.execute();
        assertThat(service.getCount(), is(count));
    }

}
