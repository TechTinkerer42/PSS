package org.ets.ereg.common.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

@Component("eregMessageResource")
public class ERegMessageResourceUtil implements MessageSourceAware{

    @Autowired
    private MessageSource messageSource;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String messageCode, Locale locale){
       return messageSource.getMessage(messageCode, null, locale);
    }


}
