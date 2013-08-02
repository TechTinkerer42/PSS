package org.ets.ereg.common.web.taglib;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

import org.ets.ereg.common.web.util.Constant;
import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;
import org.springframework.web.servlet.tags.Param;
import org.springframework.web.servlet.tags.ParamAware;
import org.springframework.web.util.ExpressionEvaluationUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;
import org.springframework.web.util.TagUtils;
import org.springframework.web.util.UriUtils;

@Configurable
@EnableAspectJAutoProxy
public class EncodingTagHandler extends HtmlEscapingAwareTag implements ParamAware {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static Logger log = LoggerFactory.getLogger(EncodingTagHandler.class);


    @Autowired
    private GenerateBase64StringBusinessService generateBase64StringBusinessService;

    private static final String URL_TEMPLATE_DELIMITER_PREFIX = "{";
    private static final String URL_TEMPLATE_DELIMITER_SUFFIX = "}";
    private static final String URL_TYPE_ABSOLUTE = "://";
    private static final String DEFAULT_ARGUMENT_SEPARATOR = ",";
    private static final String HYPERLINK_START_TAG = "<a href=\"";
    private static final String HYPERLINK_ANCHOR_END_TAG = "</a>";
    private static final String HYPERLINK_END_TAG = ">";
    private static final String HYPERLINK_END_STRING = "\"";
    private static final String HYPERLINK_TARGET = " target=\"";
    private static final String HYPERLINK_STYLE_CLASS = " class=\"";
    private static final String HYPERLINK_STYLE = " style=\"";
    private static final String HYPERLINK_ONCLICK = " onclick=\"";
    private static final String ENCODABLE = "&encodable=";
    private static final String CODE = "code";
    private static final String TEXT = "text";

    private String out;
    private boolean encodable = true;
    private String argumentSeparator = DEFAULT_ARGUMENT_SEPARATOR;
    private List<Param> params;
    private Set<String> templateParams;
    private UrlType type;
    private String context;
    private String var;
    private String hrefCode;
    private String hrefStyleClass;
    private String hrefStyle;
    private String hrefTarget;
    private String hrefOnclick;
    private int scope = PageContext.PAGE_SCOPE;
    private boolean javaScriptEscape = false;
    private Object message;
    private String hrefText;
    private Object arguments;



    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        if (out.contains(URL_TYPE_ABSOLUTE)) {
            this.type = UrlType.ABSOLUTE;
            this.out = out;
        }
        else if (out.startsWith(Constant.FORWARD_SLASH)) {
            this.type = UrlType.CONTEXT_RELATIVE;
            this.out = out;
        }
        else {
            this.type = UrlType.RELATIVE;
            this.out = out;
        }
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public Set<String> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(Set<String> templateParams) {
        this.templateParams = templateParams;
    }

    public UrlType getType() {
        return type;
    }

    public void setType(UrlType type) {
        this.type = type;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public boolean isEncodable() {
        return encodable;
    }

    public void setEncodable(boolean encodable) {
        this.encodable = encodable;
    }


    public String getHrefCode() {
        return hrefCode;
    }

    public void setHrefCode(String hrefCode) {
        this.hrefCode = hrefCode;
    }

    public String getHrefText() {
        return hrefText;
    }

    public void setHrefText(String hrefText) {
        this.hrefText = hrefText;
    }

    public String getHrefStyleClass() {
        return hrefStyleClass;
    }

    public void setHrefStyleClass(String hrefStyleClass) {
        this.hrefStyleClass = hrefStyleClass;
    }

    public String getHrefTarget() {
        return hrefTarget;
    }

    public void setHrefTarget(String hrefTarget) {
        this.hrefTarget = hrefTarget;
    }

    public String getHrefStyle() {
        return hrefStyle;
    }

    public void setHrefStyle(String hrefStyle) {
        this.hrefStyle = hrefStyle;
    }

    /**
     * Set the variable name to expose the URL under. Defaults to rendering the
     * URL to the current JspWriter
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * Set the scope to export the URL variable to. This attribute has no
     * meaning unless var is also defined.
     */
    public void setScope(String scope) {
        this.scope = TagUtils.getScope(scope);
    }

    /**
     * Set JavaScript escaping for this tag, as boolean value.
     * Default is "false".
     */
    public void setJavaScriptEscape(String javaScriptEscape) throws JspException {
        this.javaScriptEscape =
                ExpressionEvaluationUtils.evaluateBoolean("javaScriptEscape", javaScriptEscape, pageContext);
    }

    /**
     * Set the context path for the URL. Defaults to the current context
     */
    public void setContext(String context) {
        if (context.startsWith(Constant.FORWARD_SLASH)) {
            this.context = context;
        }
        else {
            this.context = Constant.FORWARD_SLASH + context;
        }
    }

    public void addParam(Param param) {
        this.params.add(param);
    }

    public String getHrefOnclick() {
        return hrefOnclick;
    }

    public void setHrefOnclick(String hrefOnclick) {
        this.hrefOnclick = hrefOnclick;
    }

    /**
     * Use the current RequestContext's application context as MessageSource.
     */
    protected MessageSource getMessageSource() {
        return getRequestContext().getMessageSource();
    }

    /** Set optional message arguments for this tag, as a comma-delimited
    * String (each String argument can contain JSP EL), an Object array
    * (used as argument array), or a single Object (used as single argument).
    */
   public void setArguments(Object arguments) {
       this.arguments = arguments;
   }

   /**
    * Set the MessageSourceResolvable for this tag.
    * Accepts a direct MessageSourceResolvable instance as well as a JSP
    * expression language String that points to a MessageSourceResolvable.
    * <p>If a MessageSourceResolvable is specified, it effectively overrides
    * any code, arguments or text specified on this tag.
    */
   public void setMessage(Object message) {
       this.message = message;
   }

   /**
    * Set the separator to use for splitting an arguments String.
    * Default is a comma (",").
    * @see #setArguments
    */
   public void setArgumentSeparator(String argumentSeparator) {
       this.argumentSeparator = argumentSeparator;
   }


    @Override
    public int doStartTagInternal() throws JspException {
        this.params = new LinkedList<Param>();
        this.templateParams = new HashSet<String>();
        return EVAL_BODY_INCLUDE;
    }


    private String createEncodeUrl(String url){
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String username = request.getUserPrincipal().getName();
        String controllerUrl = "";
        log.debug("URL before encoding is {}", url);
        if(url.contains("?")){
            controllerUrl = url.substring(0, url.indexOf("?"));
            if(!controllerUrl.endsWith(Constant.FORWARD_SLASH)){
                controllerUrl += Constant.FORWARD_SLASH;
            }
            url = generateBase64StringBusinessService.encodeBase64String(username+Constant.ENCODING_DELIMITTER+url.substring(url.indexOf("?")));
        } else {
            url = generateBase64StringBusinessService.encodeBase64String(username+Constant.ENCODING_DELIMITTER+url.substring(url.lastIndexOf(Constant.FORWARD_SLASH)+1));
            controllerUrl = url.substring(0, url.lastIndexOf(Constant.FORWARD_SLASH)+1);
        }

        url = controllerUrl + Constant.URL_DECODE_IDENTIFIER + url;
        log.debug("Encoded URL is {}",  url);
        return url;
    }




    @Override
    public int doEndTag() throws JspException {
        String url = createUrl();

        if(!isEncodable()){
            url = url + ENCODABLE +isEncodable();
        } else {
            url = createEncodeUrl(url);
        }

        RequestDataValueProcessor processor = getRequestContext().getRequestDataValueProcessor();
        ServletRequest request = this.pageContext.getRequest();
        if ((processor != null) && (request instanceof HttpServletRequest)) {
            url = processor.processUrl((HttpServletRequest) request, url);
        }

        if (this.var == null) {
            // print the url to the writer
            try {
                url = createHyperLink(url);
                pageContext.getOut().print(url);
            }
            catch (IOException e) {
                throw new JspException(e);
            }
        }
        else {
            // store the url as a variable
            pageContext.setAttribute(var, url, scope);
        }
        return EVAL_PAGE;
    }


    private String createHyperLink(String url) {
        if(hrefCode!=null || hrefText!=null){
            String hrefBody = "";
            if(hrefTarget!=null){
                hrefBody = HYPERLINK_TARGET+ hrefTarget + HYPERLINK_END_STRING;
            }
            if(hrefStyleClass!=null){
                hrefBody += HYPERLINK_STYLE_CLASS + hrefStyleClass + HYPERLINK_END_STRING;
            }
            if(hrefStyle!=null){
                hrefBody += HYPERLINK_STYLE + hrefStyle + HYPERLINK_END_STRING;
            }
            if(hrefOnclick!=null){
                hrefBody += HYPERLINK_ONCLICK + hrefOnclick + HYPERLINK_END_STRING;
            }
            try {
                url =  HYPERLINK_START_TAG + url + HYPERLINK_END_STRING + hrefBody + HYPERLINK_END_TAG + resolveMessage() + HYPERLINK_ANCHOR_END_TAG;
            } catch (NoSuchMessageException e) {
                log.error("Exception while getting message from property file for {} or {}" + e.getMessage(), hrefText, hrefCode);
            } catch (JspException e) {
                log.error("JSPException while getting message from property file for {} or {}" + e.getMessage(), hrefText, hrefCode);
            }
        }
        return url;

    }

    /**
     * Build the URL for the tag from the tag attributes and parameters.
     * @return the URL value as a String
     * @throws JspException
     */
    private String createUrl() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        StringBuilder url = new StringBuilder();
        if (this.type == UrlType.CONTEXT_RELATIVE) {
            // add application context to url
            if (this.context == null) {
                url.append(request.getContextPath());
            }
            else {
                url.append(this.context);
            }
        }
        if (this.type != UrlType.RELATIVE && this.type != UrlType.ABSOLUTE && !this.out.startsWith(Constant.FORWARD_SLASH)) {
            url.append(Constant.FORWARD_SLASH);
        }
        url.append(replaceUriTemplateParams(this.out, this.params, this.templateParams));
        url.append(createQueryString(this.params, this.templateParams, (url.indexOf("?") == -1)));

        String urlStr = url.toString();
        if (this.type != UrlType.ABSOLUTE) {
            // Add the session identifier if needed
            // (Do not embed the session identifier in a remote link!)
            urlStr = response.encodeURL(urlStr);
        }

        // HTML and/or JavaScript escape, if demanded.
        urlStr = isHtmlEscape() ? HtmlUtils.htmlEscape(urlStr) : urlStr;
        urlStr = this.javaScriptEscape ? JavaScriptUtils.javaScriptEscape(urlStr) : urlStr;

        return urlStr;
    }

    /**
     * Build the query string from available parameters that have not already
     * been applied as template params.
     * <p>The names and values of parameters are URL encoded.
     * @param params the parameters to build the query string from
     * @param usedParams set of parameter names that have been applied as
     * template params
     * @param includeQueryStringDelimiter true if the query string should start
     * with a '?' instead of '&'
     * @return the query string
     */
    protected String createQueryString(List<Param> params, Set<String> usedParams, boolean includeQueryStringDelimiter)
            throws JspException {

        String encoding = pageContext.getResponse().getCharacterEncoding();
        StringBuilder qs = new StringBuilder();
        for (Param param : params) {
            if (!usedParams.contains(param.getName()) && StringUtils.hasLength(param.getName())) {
                if (includeQueryStringDelimiter && qs.length() == 0) {
                    qs.append("?");
                }
                else {
                    qs.append("&");
                }
                try {
                    qs.append(UriUtils.encodeQueryParam(param.getName(), encoding));
                    if (param.getValue() != null) {
                        qs.append("=");
                        qs.append(UriUtils.encodeQueryParam(param.getValue(), encoding));
                    }
                }
                catch (UnsupportedEncodingException ex) {
                    throw new JspException(ex);
                }
            }
        }
        return qs.toString();
    }

    /**
     * Replace template markers in the URL matching available parameters. The
     * name of matched parameters are added to the used parameters set.
     * <p>Parameter values are URL encoded.
     * @param uri the URL with template parameters to replace
     * @param params parameters used to replace template markers
     * @param usedParams set of template parameter names that have been replaced
     * @return the URL with template parameters replaced
     */
    protected String replaceUriTemplateParams(String uri, List<Param> params, Set<String> usedParams)
            throws JspException {

        String encoding = pageContext.getResponse().getCharacterEncoding();
        for (Param param : params) {
            String template = URL_TEMPLATE_DELIMITER_PREFIX + param.getName() + URL_TEMPLATE_DELIMITER_SUFFIX;
            if (uri.contains(template)) {
                usedParams.add(param.getName());
                try {
                    uri = uri.replace(template, UriUtils.encodePath(param.getValue(), encoding));
                }
                catch (UnsupportedEncodingException ex) {
                    throw new JspException(ex);
                }
            }
        }
        return uri;
    }

    /**
     * Internal enum that classifies URLs by type.
     */
    private enum UrlType {
        CONTEXT_RELATIVE, RELATIVE, ABSOLUTE
    }

    /**
     * Resolve the specified message into a concrete message String.
     * The returned message String should be unescaped.
     */
    protected String resolveMessage() throws JspException, NoSuchMessageException {
        MessageSource messageSource = getMessageSource();
        if (messageSource == null) {
            throw new JspTagException("No corresponding MessageSource found");
        }

        // Evaluate the specified MessageSourceResolvable, if any.
        MessageSourceResolvable resolvedMessage = null;
        if (this.message instanceof MessageSourceResolvable) {
            resolvedMessage = (MessageSourceResolvable) this.message;
        }
        else if (this.message != null) {
            String expr = this.message.toString();
            resolvedMessage = (MessageSourceResolvable)
                    ExpressionEvaluationUtils.evaluate("message", expr, MessageSourceResolvable.class, pageContext);
        }

        if (resolvedMessage != null) {
            // We have a given MessageSourceResolvable.
            return messageSource.getMessage(resolvedMessage, getRequestContext().getLocale());
        }

        String resolvedCode = ExpressionEvaluationUtils.evaluateString(CODE, this.hrefCode, pageContext);
        String resolvedText = ExpressionEvaluationUtils.evaluateString(TEXT, this.hrefText, pageContext);

        if (resolvedCode != null || resolvedText != null) {
            // We have a code or default text that we need to resolve.
            Object[] argumentsArray = resolveArguments(this.arguments);
            if (resolvedText != null) {
                // We have a fallback text to consider.
                return messageSource.getMessage(
                        resolvedCode, argumentsArray, resolvedText, getRequestContext().getLocale());
            }
            else {
                // We have no fallback text to consider.
                return messageSource.getMessage(
                        resolvedCode, argumentsArray, getRequestContext().getLocale());
            }
        }

        // All we have is a specified literal text.
        return resolvedText;
    }

    /**
     * Return default exception message.
     */
    protected String getNoSuchMessageExceptionDescription(NoSuchMessageException ex) {
        return ex.getMessage();
    }




    /**
     * Resolve the given arguments Object into an arguments array.
     * @param arguments the specified arguments Object
     * @return the resolved arguments as array
     * @throws JspException if argument conversion failed
     * @see #setArguments
     */
    protected Object[] resolveArguments(Object arguments) throws JspException {
        if (arguments instanceof String) {
            String[] stringArray =
                    StringUtils.delimitedListToStringArray((String) arguments, this.argumentSeparator);
            if (stringArray.length == 1) {
                Object argument = ExpressionEvaluationUtils.evaluate("argument", stringArray[0], pageContext);
                if (argument != null && argument.getClass().isArray()) {
                    return ObjectUtils.toObjectArray(argument);
                }
                else {
                    return new Object[] {argument};
                }
            }
            else {
                Object[] argumentsArray = new Object[stringArray.length];
                for (int i = 0; i < stringArray.length; i++) {
                    argumentsArray[i] =
                            ExpressionEvaluationUtils.evaluate("argument[" + i + "]", stringArray[i], pageContext);
                }
                return argumentsArray;
            }
        }
        else if (arguments instanceof Object[]) {
            return (Object[]) arguments;
        }
        else if (arguments instanceof Collection) {
            return ((Collection) arguments).toArray();
        }
        else if (arguments != null) {
            // Assume a single argument object.
            return new Object[] {arguments};
        }
        else {
            return null;
        }
    }
}
