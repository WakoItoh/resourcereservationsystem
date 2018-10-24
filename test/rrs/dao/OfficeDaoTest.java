package rrs.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import rrs.dto.Office;

public class OfficeDaoTest {

    @Test
    public void testQuery() throws DataAccessException {

        // 事業所情報が1件以上存在するとき、事業所情報が取得される
        // 事前条件：insert1.sqlのデータがofficesテーブルに挿入されている
        // 期待結果：長さ1のList<Office>が返却される
        // 期待結果：List<Office>にofficeId=1のデータが格納されている

        int size = 1;

        OfficeDao dao = new OfficeDao();
        List<Office> officeList = dao.query();
        assertThat(officeList, notNullValue());
        assertThat(officeList.size(), is(size));
        for (Office office : officeList) {
            System.out.println(office.getOfficeId() + " " + office.getOfficeName() + " " + office.getOfficeLocation());
        }
    }

}
