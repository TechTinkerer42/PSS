package org.ets.ereg.web.taglib;

import javax.servlet.jsp.JspWriter;

import org.ets.ereg.web.util.Constant;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

public class AppointmentUtility extends RequestContextAwareTag {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String out;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }



    @Override
    protected int doStartTagInternal() throws Exception {
        JspWriter output = pageContext.getOut();
        if(out.contains(Constant.ZEROS) && out.substring(0, 7).equals(Constant.ZEROS)){
            out = out.substring(7);
        }

        output.println(out);
        return SKIP_BODY;
    }

}
