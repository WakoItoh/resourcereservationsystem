package rrs.model;

import java.util.List;

import rrs.dao.CategoryDao;
import rrs.dao.DataAccessException;
import rrs.dao.OfficeDao;
import rrs.dto.Category;
import rrs.dto.Office;

public class ReservableResourceService implements Service {

    // 処理結果
    private List<Category> categoryList;
    private List<Office> officeList;

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        CategoryDao categoryDao = new CategoryDao();

        categoryList = categoryDao.query();

        OfficeDao officeDao = new OfficeDao();

        officeList = officeDao.query();
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<Office> getOfficeList() {
        return officeList;
    }

}
