package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import rrs.dao.DataAccessException;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;
import rrs.dto.Resource;
import rrs.model.ResourceEditorService;

public class ResourceEditorHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        // URLパラメータ・画面入力があるので、パラメータを取得し、チェックを実行
        int resourceId = 0;
        String paramResourceId = request.getParameter("resource_id");

        boolean edited = false;
        if (request.getAttribute("errorMessageList") != null) {
            edited = true;
        }

        List<String> errorMessageList = new ArrayList<String>();
        CommonValidator commonValidator = new CommonValidator();

        // リソースIDのチェック
        if (commonValidator.notSetOn(paramResourceId)) {
            errorMessageList.add(EM_RSC_001);
        } else {
            if (commonValidator.notNumericOn(paramResourceId)) {
                errorMessageList.add(EM_RSC_001);
            } else {
                resourceId = commonValidator.getIntVal();
            }
        }

        // チェックOKならModelを生成してvalidate、executeを呼ぶ
        if (errorMessageList.size() > 0) {
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_RSC_002;
        }

        Resource resource = null;
        List<Category> categoryList = null;
        List<Office> officeList = null;
        List<Property> propertyList = null;

        // Modelの生成
        ResourceEditorService service = new ResourceEditorService(resourceId);

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

        // 処理結果の取得
        resource = service.getResource();
        if (resource == null) {
            errorMessageList.add(EM_RSC_002);
            request.setAttribute("errorMessageList", errorMessageList);
            return PAGE_RSC_002;
        }
        if (!edited) {
            // 初期表示
            request.setAttribute("paramResourceName", resource.getResourceName());
            request.setAttribute("paramCategory", String.valueOf(resource.getCategory().getCategoryId()));
            request.setAttribute("paramCapacity", resource.getCapacity());
            request.setAttribute("paramOffice", String.valueOf(resource.getOffice().getOfficeId()));
            if (resource.getPropertyList() != null) {
                int size = resource.getPropertyList().size();
                String[] properties = new String[size];
                for (int i = 0; i < size; i++) {
                    properties[i] = String.valueOf(resource.getPropertyList().get(i).getPropertyId());
                }
                request.setAttribute("paramProperties", properties);
            }
            request.setAttribute("paramNote", resource.getNote());
            SimpleDateFormat dateFormatter = new SimpleDateFormat(CommonValidator.DATE_FORMAT_PATTERN);
            SimpleDateFormat timeFormatter = new SimpleDateFormat(CommonValidator.TIME_FORMAT_PATTERN);
            if (resource.getSuspendStart() != null) {
                request.setAttribute("paramSuspendStartDate", dateFormatter.format(resource.getSuspendStart()));
                request.setAttribute("paramSuspendStartTime", timeFormatter.format(resource.getSuspendStart()));
            }
            if (resource.getSuspendEnd() != null) {
                request.setAttribute("paramSuspendEndDate", dateFormatter.format(resource.getSuspendEnd()));
                request.setAttribute("paramSuspendEndTime", timeFormatter.format(resource.getSuspendEnd()));
            }
        }

        request.setAttribute("paramResourceId", resourceId);

        // 選択肢Listの取得
        categoryList = service.getCategoryList();
        officeList = service.getOfficeList();
        propertyList = service.getPropertyList();

        request.setAttribute("categoryList", categoryList);
        request.setAttribute("officeList", officeList);
        request.setAttribute("propertyList", propertyList);

        return PAGE_RSC_201;

    }

}
