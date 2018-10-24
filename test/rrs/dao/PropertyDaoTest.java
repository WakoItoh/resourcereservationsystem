package rrs.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import rrs.dto.Property;

public class PropertyDaoTest {

    @Test
    public void testQuery() throws DataAccessException {

        // 特性情報が1件以上存在するとき、特性情報が取得される
        // 事前条件：insert1.sqlのデータがpropertiesテーブルに挿入されている
        // 期待結果：長さ1のList<Property>が返却される
        // 期待結果：List<Property>にpropertyId=1のデータが格納されている

        int size = 1;

        PropertyDao dao = new PropertyDao();
        List<Property> propertyList = dao.query();
        assertThat(propertyList, notNullValue());
        assertThat(propertyList.size(), is(size));
        for (Property property : propertyList) {
            System.out.println(property.getPropertyId() + " " + property.getPropertyName());
        }
    }

}
