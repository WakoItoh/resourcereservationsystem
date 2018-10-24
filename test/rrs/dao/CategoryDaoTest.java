package rrs.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import rrs.dto.Category;

public class CategoryDaoTest {

    @Test
    public void testQuery() throws DataAccessException {

        // カテゴリ情報が1件以上存在するとき、カテゴリ情報が取得される
        // 事前条件：insert1.sqlのデータがcategoriesテーブルに挿入されている
        // 期待結果：長さ1のList<Category>が返却される
        // 期待結果：List<Category>にcategoryId=1のデータが格納されている

        int size = 1;

        CategoryDao dao = new CategoryDao();
        List<Category> categoryList = dao.query();
        assertThat(categoryList, notNullValue());
        assertThat(categoryList.size(), is(size));
        for (Category category : categoryList) {
            System.out.println(category.getCategoryId() + " " + category.getCategoryName());
        }
    }

}
