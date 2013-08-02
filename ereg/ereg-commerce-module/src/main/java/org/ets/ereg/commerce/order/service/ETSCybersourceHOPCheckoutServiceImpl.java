package org.ets.ereg.commerce.order.service;

import com.broadleafcommerce.vendor.cybersource.service.payment.CybersourceSilentPostCheckoutServiceImpl;
import com.broadleafcommerce.vendor.cybersource.service.payment.type.MessageConstants;
import org.broadleafcommerce.core.order.domain.Order;
import org.ets.ereg.commerce.order.service.type.ETSCybersourceConstants;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.util.Constant;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

import javax.annotation.Resource;

public class ETSCybersourceHOPCheckoutServiceImpl extends CybersourceSilentPostCheckoutServiceImpl
        implements ETSCybersourceHOPCheckoutService, InitializingBean {

    @Resource(name = "applicationConfigurationService")
    private ApplicationConfigurationService applicationConfigurationService;

    protected String serverUrlIhop;

    protected String serverUrlCfhop;

    @Override
    public void afterPropertiesSet() throws Exception {
        serverUrlIhop = getApplicationConfigurationvalue(Constant.HOP_INTERNAL_SERVER_URL);
        serverUrlCfhop = getApplicationConfigurationvalue(Constant.HOP_CLIENT_FACING_SERVER_URL);

    }

    private String getApplicationConfigurationvalue(String key) {
        return applicationConfigurationService.findApplicationConfigurationValue(key);
    }


    public Map<String, String> constructAuthorizeAndDebitFields(Order order, boolean customerFacing, String sessionId) {
        Map<String, String> hopFields = super.constructAuthorizeAndDebitFields(order);

        hopFields.put(ETSCybersourceConstants.ORIGINATINGSYSTEM_SESSIONID, sessionId + "_" + order.getId());

        if (customerFacing) {
            hopFields.put(MessageConstants.CYBERSOURCE_SERVER_URL, getServerUrlCfhop());
            hopFields.put(ETSCybersourceConstants.CONTEXT, ETSCybersourceConstants.CONTEXT_CUSTOMER_FACING);
        } else {
            hopFields.put(MessageConstants.CYBERSOURCE_SERVER_URL, getServerUrlIhop());
            hopFields.put(ETSCybersourceConstants.CONTEXT, ETSCybersourceConstants.CONTEXT_INTERNAL_FACING);

        }

        return hopFields;
    }

    public String getServerUrlIhop() {
        return serverUrlIhop;
    }

    public void setServerUrlIhop(String serverUrlIhop) {
        this.serverUrlIhop = serverUrlIhop;
    }

    public String getServerUrlCfhop() {
        return serverUrlCfhop;
    }

    public void setServerUrlCfhop(String serverUrlCfhop) {
        this.serverUrlCfhop = serverUrlCfhop;
    }

}
