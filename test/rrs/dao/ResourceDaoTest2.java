package rrs.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;
import rrs.dto.Resource;

public class ResourceDaoTest2 {

    @Test
    public void testRegister() throws DataAccessException {

        // リソース情報が適切であるとき、リソース情報が正しく挿入される
        // 事前条件：insert2.sqlのデータがresourcesテーブルに挿入されている
        // 期待結果：3が返却される
        // 期待結果：query()の結果、返却されたList<Resource>にresourceId=1,resourceId=2,resourceId=3のデータが格納されている

        Resource resourceRegistered = new Resource();
        resourceRegistered.setResourceName("新横浜A");
        resourceRegistered.setCategory(new Category(2, null));
        resourceRegistered.setCapacity(100);
        resourceRegistered.setOffice(new Office(5, null, null));
        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(new Property(2, null));
        resourceRegistered.setPropertyList(propertyList);
        resourceRegistered.setNote("単体テスト");
        resourceRegistered.setSuspendStart(toDate("2018-12-01 00:00"));
        resourceRegistered.setSuspendEnd(toDate("2018-12-31 23:00"));

        ResourceDao dao = new ResourceDao();
        int result = dao.register(resourceRegistered);
        assertThat(result, not(0));
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

    private Date toDate(String stringDate) {

        Date date = null;

        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setLenient(false);

        try {
            date = formatter.parse(stringDate);
        } catch (NullPointerException | ParseException e) {
            return null;
        }

        return date;
    }

}
