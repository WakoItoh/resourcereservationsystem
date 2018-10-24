package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import rrs.dao.DataAccessException;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;
import rrs.model.ResourceRegistratorService;

public class ResourceRegistratorHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        List<String> errorMessageList = new ArrayList<String>();

        List<Category> categoryList = null;
        List<Office> officeList = null;
        List<Property> propertyList = null;

        // Modelの生成
        ResourceRegistratorService service = new ResourceRegistratorService();

        // validateとexecuteを呼び出す
        if (service.validate()) {

            try {
                service.execute();
            } catch (DataAccessException e) {
                // DataAccessExceptionの発生はシステムエラー
                e.printStackTrace();
                errorMessageList.add(EM_COMM_001);
                request.setAttribute("errorMessageList", errorMessageList);
                return PAGE_ERR_001;
            }
        } else {
            // 到達不能
            return PAGE_ERR_001;

        }

        // 選択肢Listの取得
        categoryList = service.getCategoryList();
        officeList = service.getOfficeList();
        propertyList = service.getPropertyList();

        request.setAttribute("categoryList", categoryList);
        request.setAttribute("officeList", officeList);
        request.setAttribute("propertyList", propertyList);

        return PAGE_RSC_101;
    }

}
