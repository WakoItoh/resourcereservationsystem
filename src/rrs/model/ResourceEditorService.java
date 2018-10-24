package rrs.model;

import java.util.List;

import rrs.dao.CategoryDao;
import rrs.dao.DataAccessException;
import rrs.dao.OfficeDao;
import rrs.dao.PropertyDao;
import rrs.dao.ResourceDao;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;
import rrs.dto.Resource;

public class ResourceEditorService implements Service {

    // 入力データ
    private int resourceId;

    // 入力データチェック結果のメッセージ
    // なし

    // 処理結果
    private Resource resource;
    private List<Category> categoryList;
    private List<Office> officeList;
    private List<Property> propertyList;

    public ResourceEditorService(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() throws DataAccessException {

        ResourceDao dao = new ResourceDao();

        resource = dao.queryByResourceId(resourceId, true);

        CategoryDao categoryDao = new CategoryDao();

        categoryList = categoryDao.query();

        OfficeDao officeDao = new OfficeDao();

        officeList = officeDao.query();

        PropertyDao propertyDao = new PropertyDao();

        propertyList = propertyDao.query();
    }

    public Resource getResource() {
        return resource;
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
