package org.ets.ereg.shared.dao;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.util.Constant;
import org.ets.ereg.common.business.util.GenerateUniqueCode;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestGenerateUniqueCode {

    private static Logger logger = LoggerFactory.getLogger(TestGenerateUniqueCode.class);

    @Resource(name = "applicationConfigurationService")
    private ApplicationConfigurationService applicationConfigurationService;

    @Resource(name = "generateUniqueCode")
    private GenerateUniqueCode generateUniqueCode;


    private void initializeCommon() {
        generateUniqueCode.setApplicationConfigurationService(applicationConfigurationService);
    }

    @Test
    public void testGenerateNewPassword() {

        String newPassword = generateUniqueCode.generateNewPassword();
        logger.debug(newPassword);
        Assert.assertNotNull(newPassword);
        Assert.assertEquals(newPassword.length(), 8);

    }

    @Test
    public void testGenerateAppointNumber(){
        initializeCommon();
        String randomNumberPBT = generateUniqueCode.generateAppointmentNumber(Constant.PBT_TEST_TYPE);
        String randomNumberCBT = generateUniqueCode.generateAppointmentNumber(Constant.CBT_TEST_TYPE);
        Assert.assertNotNull(randomNumberPBT);
        logger.debug(randomNumberPBT);
        Assert.assertNotNull(randomNumberCBT);
        logger.debug(randomNumberCBT);
        Assert.assertEquals(randomNumberPBT.length(), 16);
        Assert.assertEquals(randomNumberCBT.length(), 16);
        Assert.assertEquals(randomNumberPBT.substring(14), new DateTime().toString(Constant.YEAR_YY));
    }

    @Test
    public void testRandomString() {
        initializeCommon();
        String randomString = generateUniqueCode.generateRandomString();
        Assert.assertNotNull(randomString);
        Assert.assertEquals(randomString.length(), 8);
        logger.debug(randomString);
    }

}
