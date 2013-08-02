package org.ets.ereg.common.web.util;

public class Constant {

    public static final String URL_DECODE_IDENTIFIER = "tWcszZ9i17cP6wAudlndgw";
    public static final String FORWARD_SLASH = "/";
    public static final String SIGNIN_PAGE = "/public/signin";
    public static final String DECODE_URL_REGEX = "(.*)(\\d+)(.*)";
    public static final String BASE64_REGEX = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4})$";
    public static final String ENCODING_DELIMITTER = "|";
    public static final String ZEROS = "0000000";
    public static final String AFTER_UPDATE_PROFILE_URL_SESSION_ATTR="afterUpdateURL";
    public static final String IS_NEW_CUSTOMER = "isNewCustomer";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String RECENT_ORDER = "RECENTORDER";
    public static final String LAST_SIX_MONTH_ORDER ="Last 6 Months";
    public static final String ALL ="All";
    public static final String YES ="Y";
    public static final String NO ="N";
    public static final String CRITERIA_UPDATED = "criteriaUpdated";
    public static final String SELECT_VIEW_ORDER =  "selectedViewOrder";
    public static final String VIEW_ORDER_RESULT=  "viewOrderResult";
    public static final String MODEL_ATTR_BOOKINGS=  "bookings";
    public static final String MODEL_ATTR_ORDER=  "orderVO";
    public static final String MODEL_ATTR_MEMBERSHIP_AMOUNT=  "memberShipAmount";
    public static final String MODEL_ATTR_CUSTOMERID=  "customerIdStr";
    public static final String MODEL_ATTR_ORDERHISTORY= "orderHistory";
    public static final String MODEL_ATTR__CREDITCARD_LAST_FOUR_DIGITS=  "creditCardLastFourDigits";
    public static final String APPOINTMENT_DETAILS_PAGE = "/appointmentdetails";    
    public static final String APPOINTMENT_DETAILS_INSTRUCTION_URL = "/loadinstructions";
    public static final String CART_NAME = "cartName";
    
    
    public static class Context{
    	public static final String USER_CONTEXT = "globalContextUser";
    	public static final String CUSTOMER_CONTEXT = "globalContextCustomer";
    }
    
    
}
