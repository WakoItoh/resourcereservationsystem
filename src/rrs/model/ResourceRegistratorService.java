package rrs.model;

import java.util.List;

import rrs.dao.CategoryDao;
import rrs.dao.DataAccessException;
import rrs.dao.OfficeDao;
import rrs.dao.PropertyDao;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;

public class ResourceRegistratorService implements Service {

    // 処理結果
    private List<Category> categoryList;
    private List<Office> officeList;
    private List<Property> propertyList;

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

        PropertyDao propertyDao = new PropertyDao();

        propertyList = propertyDao.query();
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<Office> getOfficeList() {
        return officeList;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

}
