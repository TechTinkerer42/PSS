package org.ets.ereg.web.taglib;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.ets.ereg.web.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

@Configurable
@EnableAspectJAutoProxy
public class EncodingTagHandler extends RequestContextAwareTag {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static Logger log = LoggerFactory.getLogger(EncodingTagHandler.class);


    @Resource(name = "generateBase64StringBusinessService")
    private GenerateBase64StringBusinessService generateBase64StringBusinessService;

    private String out;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }


    @Override
    protected int doStartTagInternal() throws Exception {
        try {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            String username = request.getUserPrincipal().getName();
            JspWriter output = pageContext.getOut();
            String controllerUrl = "";
            if(out.contains("?")){
                controllerUrl = out.substring(0, out.indexOf("?"));
                if(!controllerUrl.endsWith("/")){
                    controllerUrl += Constant.FORWARD_SLASH;
                }
                log.debug("test null: {}", generateBase64StringBusinessService);
                out = generateBase64StringBusinessService.encodeBase64String(username+Constant.ENCODING_DELIMITTER+out.substring(out.indexOf("?")));
            } else {
                out = generateBase64StringBusinessService.encodeBase64String(username+Constant.ENCODING_DELIMITTER+out.substring(out.lastIndexOf(Constant.FORWARD_SLASH)+1));
                controllerUrl = out.substring(0, out.lastIndexOf(Constant.FORWARD_SLASH)+1);
            }

            out = request.getContextPath() + controllerUrl + Constant.URL_DECODE_IDENTIFIER + out;
            log.debug("Encoded url is : " + out);
            output.println(out);

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
        return SKIP_BODY;
    }

}
