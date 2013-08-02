package org.ets.ereg.common.business.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("generateUniqueCode")
public class GenerateUniqueCode implements InitializingBean{

    @Resource(name = "applicationConfigurationService")
    private ApplicationConfigurationService applicationConfigurationService;

    private static Logger logger = LoggerFactory.getLogger(GenerateUniqueCode.class);

    private String generatePasswordInputString;
    private int randomUpperCaseLength;
    private int randomLowerCaseLength;
    private int randomNumberLength;
    private int randomSpecialCaseLength;
    private int randomPBTNumberLength;
    private int randomCBTNumberLength;


    private int randomStartUpperCaseIndex;
    private int randomEndUpperCaseIndex;
    private int randomStartLowerCaseIndex;
    private int randomEndLowerCaseIndex;
    private int randomStartNumberIndex;
    private int randomEndNumberIndex;
    private int randomStartSpecialIndex;
    private int randomEndSpecialIndex;

    public ApplicationConfigurationService getApplicationConfigurationService() {
        return applicationConfigurationService;
    }

    public void setApplicationConfigurationService(
            ApplicationConfigurationService applicationConfigurationService) {
        this.applicationConfigurationService = applicationConfigurationService;
    }

    /**
     * Method to pre-initialize values from database during application startup
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        generatePasswordInputString = getApplicationConfigurationvalue(Constant.GENERATE_PASSWORD_IDENTIFIER);

        String startUpperCaseLetter = getApplicationConfigurationvalue(Constant.START_UPPER_CASE_LETTER);
        String endUpperCaseLetter = getApplicationConfigurationvalue(Constant.END_UPPER_CASE_LETTER);
        String startLowerCaseLetter = getApplicationConfigurationvalue(Constant.START_LOWER_CASE_LETTER);
        String endLowerCaseLetter = getApplicationConfigurationvalue(Constant.END_LOWER_CASE_LETTER);
        String startNumber = getApplicationConfigurationvalue(Constant.START_NUMBER);
        String endNumber = getApplicationConfigurationvalue(Constant.END_NUMBER);

        randomUpperCaseLength = Integer.parseInt(getApplicationConfigurationvalue(Constant.RANDOM_UPPER_CASE_LENGTH));
        randomLowerCaseLength = Integer.parseInt(getApplicationConfigurationvalue(Constant.RANDOM_LOWER_CASE_LENGTH));
        randomNumberLength = Integer.parseInt(getApplicationConfigurationvalue(Constant.RANDOM_NUMBER_LENGTH));
        randomSpecialCaseLength = Integer.parseInt(getApplicationConfigurationvalue(Constant.RANDOM_SPECIAL_LENGTH));
        randomStartUpperCaseIndex = generatePasswordInputString.indexOf(startUpperCaseLetter);
        randomEndUpperCaseIndex = generatePasswordInputString.indexOf(endUpperCaseLetter);
        randomStartLowerCaseIndex = generatePasswordInputString.indexOf(startLowerCaseLetter);
        randomEndLowerCaseIndex = generatePasswordInputString.indexOf(endLowerCaseLetter);
        randomStartNumberIndex = generatePasswordInputString.indexOf(startNumber);
        randomEndNumberIndex = generatePasswordInputString.indexOf(endNumber);
        randomStartSpecialIndex = randomEndNumberIndex + 1;
        randomEndSpecialIndex = generatePasswordInputString.length()-1;

        randomPBTNumberLength = Integer.parseInt(getApplicationConfigurationvalue(Constant.RANDOM_PBT_LENGTH));
        randomCBTNumberLength = Integer.parseInt(getApplicationConfigurationvalue(Constant.RANDOM_CBT_LENGTH));

    }

    /**
     *
     * Method to get ApplicationConfiguration value from database
     *
     * @param key
     * @return
     */
    private String getApplicationConfigurationvalue(String key) {
        return applicationConfigurationService.findApplicationConfigurationValue(key);
    }


    public String generateNewPassword() {
         String randomUpper = RandomStringUtils.random(randomUpperCaseLength, randomStartUpperCaseIndex, randomEndUpperCaseIndex, false, false, generatePasswordInputString.toCharArray());
         String randomLower = RandomStringUtils.random(randomLowerCaseLength, randomStartLowerCaseIndex, randomEndLowerCaseIndex, false, false, generatePasswordInputString.toCharArray());
         String randomNumber = RandomStringUtils.random(randomNumberLength, randomStartNumberIndex, randomEndNumberIndex, false, false, generatePasswordInputString.toCharArray());
         String randomSpecial = RandomStringUtils.random(randomSpecialCaseLength, randomStartSpecialIndex, randomEndSpecialIndex, false, false, generatePasswordInputString.toCharArray());
         String randomStr = randomUpper.concat(randomLower).concat(randomNumber).concat(randomSpecial);
         randomStr = shuffle(randomStr);
         logger.debug(randomStr);
         return randomStr;
    }

    /**
     * Method to shuffle the generated password in random order
     *
     * @param input
     * @return
     */
    public String shuffle(String input){
        List<Character> characters = new ArrayList<Character>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        int randPicker;
        while(characters.size()!=0){
            randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();

   }

    public String generateRandomString() {
        String randomUpper = RandomStringUtils.random(3, randomStartUpperCaseIndex, randomEndUpperCaseIndex, false, false,
                generatePasswordInputString.toCharArray());
        String randomNumber = RandomStringUtils.random(5,randomStartNumberIndex, randomEndNumberIndex, false, false,
                generatePasswordInputString.toCharArray());
        String randomStr = randomUpper.concat(randomNumber);
        return randomStr;
    }

    public String generateAppointmentNumber(String testType) {
        int randonNumberLength = Constant.PBT_TEST_TYPE.equalsIgnoreCase(testType) ? randomPBTNumberLength : randomCBTNumberLength;
        String randomNumber = null;
        randomNumber = RandomStringUtils.random(randonNumberLength, randomStartNumberIndex, randomEndNumberIndex, false, false,
                generatePasswordInputString.toCharArray());
        randomNumber = shuffle(randomNumber);
        while(true){
            if (randomNumber.charAt(0) != '0'){
                break;
            }
            randomNumber = shuffle(randomNumber);

        }

        if(Constant.PBT_TEST_TYPE.equalsIgnoreCase(testType)){
            randomNumber = randomNumber + new DateTime().toString(Constant.YEAR_YY);
            if(randomNumber.length() < Constant.APPOINTMENT_NUMBER_LENGTH){
                randomNumber = Constant.ZEROS + randomNumber;
            }
        }
        return randomNumber;
    }

    public String generateRandomStringForBase64() {
        return generateNewPassword();
    }

}
