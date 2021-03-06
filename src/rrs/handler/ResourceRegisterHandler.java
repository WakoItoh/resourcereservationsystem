package rrs.handler;

import static rrs.handler.MessageHolder.*;
import static rrs.handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rrs.dao.DataAccessException;
import rrs.dto.Category;
import rrs.dto.Office;
import rrs.dto.Property;
import rrs.dto.Resource;
import rrs.model.ResourceRegisterService;

public class ResourceRegisterHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        // 画面入力があるので、パラメータを取得し、チェックを実行
        String paramResourceName = request.getParameter("resource_name");
        String paramCategory = request.getParameter("category");
        String paramCapacity = request.getParameter("capacity");
        String paramOffice = request.getParameter("office");
        String[] paramProperties = request.getParameterValues("property");
        String paramNote = request.getParameter("note");
        String paramSuspendStartDate = request.getParameter("suspend_start_date");
        String paramSuspendStartTime = request.getParameter("suspend_start_time");
        String paramSuspendEndDate = request.getParameter("suspend_end_date");
        String paramSuspendEndTime = request.getParameter("suspend_end_time");

        // 入力データを保持
        request.setAttribute("paramResourceName", paramResourceName);
        request.setAttribute("paramCategory", paramCategory);
        request.setAttribute("paramCapacity", paramCapacity);
        request.setAttribute("paramOffice", paramOffice);
        request.setAttribute("paramProperties", paramProperties);
        request.setAttribute("paramNote", paramNote);
        request.setAttribute("paramSuspendStartDate", paramSuspendStartDate);
        request.setAttribute("paramSuspendStartTime", paramSuspendStartTime);
        request.setAttribute("paramSuspendEndDate", paramSuspendEndDate);
        request.setAttribute("paramSuspendEndTime", paramSuspendEndTime);

        Resource resource = new Resource();

        List<String> errorMessageList = new ArrayList<String>();
        CommonValidator commonValidator = new CommonValidator();

        // リソース名のチェック
        if (commonValidator.notSetOn(paramResourceName)) {
            errorMessageList.add(EM_RSC_003);
        } else {
            resource.setResourceName(paramResourceName);
        }

        // カテゴリのチェック
        if (commonValidator.notSetOn(paramCategory)) {
            errorMessageList.add(EM_RSC_005);
        } else {
            if (commonValidator.notNumericOn(paramCategory)) {
                errorMessageList.add(EM_RSC_006);
            } else {
                resource.setCategory(new Category(commonValidator.getIntVal(), null));
            }
        }

        // 定員のチェック
        if (commonValidator.notSetOn(paramCapacity)) {
            errorMessageList.add(EM_RSC_007);
        } else {
            if (commonValidator.notNumericOn(paramCapacity)) {
                errorMessageList.add(EM_RSC_008);
            } else {
                resource.setCapacity(commonValidator.getIntVal());
            }
        }

        // 事業所のチェック
        if (commonValidator.notSetOn(paramOffice)) {
            errorMessageList.add(EM_RSC_010);
        } else {
            if (commonValidator.notNumericOn(paramOffice)) {
                errorMessageList.add(EM_RSC_011);
            } else {
                resource.setOffice(new Office(commonValidator.getIntVal(), null, null));
            }
        }

        // 特性のチェック
        List<Property> propertyList = new ArrayList<Property>();
        if (paramProperties != null) {
            for (String paramProperty : paramProperties) {
                if (commonValidator.notNumericOn(paramProperty)) {
                    errorMessageList.add(EM_RSC_012);
                } else {
                    propertyList.add(new Property(commonValidator.getIntVal(), null));
                }
            }

        }
        resource.setPropertyList(propertyList);

        // 補足
        resource.setNote(paramNote);

        // 利用停止開始日時のチェック
        if (!commonValidator.notSetOn(paramSuspendStartDate) || !commonValidator.notSetOn(paramSuspendStartTime)) {
            if (commonValidator.notValidDate(paramSuspendStartDate)) {
                errorMessageList.add(EM_RSC_014);
            } else {
                if (commonValidator.notValidDate(paramSuspendStartDate, paramSuspendStartTime)) {
                    errorMessageList.add(EM_RSC_015);
                } else {
                    resource.setSuspendStart(commonValidator.getDateVal());
                }
            }
        }

        // 利用停止終了日時のチェック
        if (!commonValidator.notSetOn(paramSuspendEndDate) || !commonValidator.notSetOn(paramSuspendEndTime)) {
            if (commonValidator.notValidDate(paramSuspendEndDate)) {
                errorMessageList.add(EM_RSC_017);
            } else {
                if (commonValidator.notValidDate(paramSuspendEndDate, paramSuspendEndTime)) {
                    errorMessageList.add(EM_RSC_018);
                } else {
                    resource.setSuspendEnd(commonValidator.getDateVal());
                }
            }
        }

        // チェックOKならModelを生成してvalidate、executeを呼ぶ
        if (errorMessageList.size() > 0) {
            request.setAttribute("errorMessageList", errorMessageList);
            return CTRL_RSC_101;
        }

        // Modelの生成
        ResourceRegisterService service = new ResourceRegisterService(resource);

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

            // 処理結果の取得
            resource.setResourceId(service.getResourceId());
            if (resource.getResourceId() == 0) {
                errorMessageList.add(EM_RSC_021);
                request.setAttribute("errorMessageList", errorMessageList);
                return CTRL_RSC_101;
            }
        } else {
            errorMessageList = service.getErrorMessageList();
            request.setAttribute("errorMessageList", errorMessageList);
            return CTRL_RSC_101;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("message", PM_RSC_001);

        return CTRL_RSC_002 + "?resource_id=" + resource.getResourceId();
    }

}
