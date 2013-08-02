package org.ets.ereg.csr.web.form.profile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.ets.ereg.csr.web.util.Constant;
import org.ets.ereg.profile.admin.vo.TCAProfileVO;


public class TCAProfileForm {

    private static final String DATE_FIELD_SEPARATOR = "-";
    private static final int MIN_YEAR = 0;
    private static final int MAX_YEAR = 9999;

    public static final int STATUS_OK = 0;
    public static final int STATUS_NOT_OK = -1;

    private String step;
    private TCAProfileVO tcaProfile;
    private String birthDay;
    private String birthMonth;
    private String birthYear;


    public TCAProfileForm() {
        step = Constant.STEP_PERSONAL_INFO;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public TCAProfileVO getTcaProfile() {
        return tcaProfile;
    }

    public void setTcaProfile(TCAProfileVO tcaProfile) {
        this.tcaProfile = tcaProfile;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public void transfereFromFormDateOfBirth(){
        final SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.CREATE_PROFILE_DATE_PATTERN);
        Date dateOfBirth = null;
        if( null != birthYear && birthYear.trim().length() > 0 &&
            null != birthMonth && birthMonth.trim().length() > 0 &&
            null != birthDay && birthDay.trim().length() > 0
        ){
            int year = Integer.parseInt(birthYear);
            if(year > TCAProfileForm.MIN_YEAR && year <= TCAProfileForm.MAX_YEAR){
                try {
                    dateOfBirth = dateFormat.parse(birthMonth + TCAProfileForm.DATE_FIELD_SEPARATOR + birthDay + TCAProfileForm.DATE_FIELD_SEPARATOR + birthYear);
                }
                catch(ParseException e){

                }
            }
        }
        tcaProfile.getAdminUser().setDateOfBirth(dateOfBirth);
    }

    public void transfereToFormDateOfBirth(){
        Date dateOfBirth = tcaProfile.getAdminUser().getDateOfBirth();
        if(null == dateOfBirth){
            birthDay = "";
            birthMonth = "";
            birthYear = "";
        }
        else{
            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            cal.setTime(dateOfBirth);
            birthDay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
            birthMonth = Integer.toString(cal.get(Calendar.MONTH)+1);
            birthYear = Integer.toString(cal.get(Calendar.YEAR));
        }
    }



}
