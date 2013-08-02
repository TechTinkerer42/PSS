package org.ets.ereg.shared.dao;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.ets.ereg.common.business.dao.ReferenceEntityDao;
import org.ets.ereg.domain.interfaces.model.common.AddressType;
import org.ets.ereg.domain.interfaces.model.common.CustomerType;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.EthnicityType;
import org.ets.ereg.domain.interfaces.model.common.Gender;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.common.MilitaryStatusType;
import org.ets.ereg.domain.interfaces.model.common.PhoneType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestReferenceEntityDaoImpl {

    @Resource(name="referenceEntityDao")
	private ReferenceEntityDao referenceEntityDao;

	@Test
    public void testAddressTypeGetAll() {
        List<AddressType> types = referenceEntityDao.getAll(AddressType.class, null, false);
        Assert.assertEquals(1, types.size());
    }

	@Test
	public void testCustomerTypeGetAll() {
		List<CustomerType> types = referenceEntityDao.getAll(CustomerType.class, null, false);
		Assert.assertEquals(1, types.size());
	}

	@Test
    public void testDeliveryModeTypeGetAll() {
        List<DeliveryModeType> types = referenceEntityDao.getAll(DeliveryModeType.class, null, false);
        Assert.assertEquals(2, types.size());
    }

	@Test
    public void testEthnicityTypeGetAll() {
        List<EthnicityType> types = referenceEntityDao.getAll(EthnicityType.class, null, false);
        Assert.assertEquals(1, types.size());
    }

	@Test
    public void testGenderGetAll() {
        List<Gender> types = referenceEntityDao.getAll(Gender.class, null, false);
        Assert.assertEquals(1, types.size());
    }

	@Test
    public void testLanguageTypeGetAll() {
        List<LanguageType> types = referenceEntityDao.getAll(LanguageType.class, null, false);
        Assert.assertEquals(2, types.size());
    }

	@Test
    public void testMilitaryStatusTypeGetAll() {
        List<MilitaryStatusType> types = referenceEntityDao.getAll(MilitaryStatusType.class, null, false);
        Assert.assertEquals(1, types.size());
    }

	@Test
    public void testPhoneTypeGetAll() {
        List<PhoneType> types = referenceEntityDao.getAll(PhoneType.class, null, false);
        Assert.assertEquals(2, types.size());
    }

	@Test
    public void testAddressTypeGetEntityByPrimaryKey() {
        AddressType address = referenceEntityDao.getEntityByPrimaryKey(AddressType.class, "HOME");
        Assert.assertNotNull(address);
        Assert.assertEquals("HOME", address.getCode());
        Assert.assertEquals("Home address", address.getDescription());

        address = referenceEntityDao.getEntityByPrimaryKey(AddressType.class, "1");
        Assert.assertNull(address);

    }

    @Test
    public void testCustomerTypeGetEntityByPrimaryKey() {
        CustomerType customer = referenceEntityDao.getEntityByPrimaryKey(CustomerType.class, "T1");
        Assert.assertNotNull(customer);
        Assert.assertEquals("T1", customer.getCode());
        Assert.assertEquals("Type 1", customer.getDescription());

        customer = referenceEntityDao.getEntityByPrimaryKey(CustomerType.class, "1");
        Assert.assertNull(customer);
    }

    @Test
    public void testDeliveryModeTypeGetEntityByPrimaryKey() {
        DeliveryModeType deliveryMode = referenceEntityDao.getEntityByPrimaryKey(DeliveryModeType.class, "PBT");
        Assert.assertNotNull(deliveryMode);
        Assert.assertEquals("PBT", deliveryMode.getCode());
        Assert.assertEquals("Paper-Based Test", deliveryMode.getDescription());

        deliveryMode = referenceEntityDao.getEntityByPrimaryKey(DeliveryModeType.class, "1");
        Assert.assertNull(deliveryMode);
    }

    @Test
    public void testEthnicityTypeGetEntityByPrimaryKey() {
        EthnicityType ethnicity = referenceEntityDao.getEntityByPrimaryKey(EthnicityType.class, "NATIV");
        Assert.assertNotNull(ethnicity);
        Assert.assertEquals("NATIV", ethnicity.getCode());
        Assert.assertEquals("American Indian or Alaska Native", ethnicity.getDescription());

        ethnicity = referenceEntityDao.getEntityByPrimaryKey(EthnicityType.class, "1");
        Assert.assertNull(ethnicity);
    }

    @Test
    public void testGenderGetEntityByPrimaryKey() {
        Gender gender = referenceEntityDao.getEntityByPrimaryKey(Gender.class, "M");
        Assert.assertNotNull(gender);
        Assert.assertEquals("M", gender.getCode());
        Assert.assertEquals("Male", gender.getDescription());

        gender = referenceEntityDao.getEntityByPrimaryKey(Gender.class, "1");
        Assert.assertNull(gender);
    }

    @Test
    public void testLanguageTypeGetEntityByPrimaryKey() {
        LanguageType language = referenceEntityDao.getEntityByPrimaryKey(LanguageType.class, "EN");
        Assert.assertNotNull(language);
        Assert.assertEquals("EN", language.getCode());
        Assert.assertEquals("English", language.getDescription());

        language = referenceEntityDao.getEntityByPrimaryKey(LanguageType.class, "1");
        Assert.assertNull(language);
    }

    @Test
    public void testMilitaryStatusTypeGetEntityByPrimaryKey() {
        MilitaryStatusType militaryStatus = referenceEntityDao.getEntityByPrimaryKey(MilitaryStatusType.class, "NOTAM");
        Assert.assertNotNull(militaryStatus);
        Assert.assertEquals("NOTAM", militaryStatus.getCode());
        Assert.assertEquals("Not a Member", militaryStatus.getDescription());

        militaryStatus = referenceEntityDao.getEntityByPrimaryKey(MilitaryStatusType.class, "1");
        Assert.assertNull(militaryStatus);
    }

    @Test
    public void testPhoneTypeGetEntityByPrimaryKey() {
        PhoneType phoneType = referenceEntityDao.getEntityByPrimaryKey(PhoneType.class, "M");
        Assert.assertNotNull(phoneType);
        Assert.assertEquals("M", phoneType.getCode());
        Assert.assertEquals("Mobile", phoneType.getDescription());

        phoneType = referenceEntityDao.getEntityByPrimaryKey(PhoneType.class, "1");
        Assert.assertNull(phoneType);
    }
}
