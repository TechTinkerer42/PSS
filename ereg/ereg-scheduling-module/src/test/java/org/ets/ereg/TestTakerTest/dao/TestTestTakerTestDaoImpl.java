package org.ets.ereg.TestTakerTest.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;
import org.ets.ereg.domain.booking.BookingImpl;
import org.ets.ereg.domain.common.LanguageTypeImpl;
import org.ets.ereg.domain.form.FormImpl;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.test.TestStatusType;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.model.test.id.TestVarianceId;
import org.ets.ereg.domain.scheduling.TestTakerTestImpl;
import org.ets.ereg.domain.test.TestStatusTypeImpl;
import org.ets.ereg.domain.test.TestVariationImpl;
import org.ets.ereg.scheduling.booking.service.BookingService;
import org.ets.ereg.scheduling.testtakertest.dao.TestTakerTestDao;
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

public class TestTestTakerTestDaoImpl {

	private static Logger logger = LoggerFactory.getLogger(TestTestTakerTestDaoImpl.class);

	@Resource(name="testTakerTestDao")
	private TestTakerTestDao testTakerTestdao;

	/*@Resource(name="testDao")
	private TestDao testDao;*/

    @Resource(name = "bookingService")
    private BookingService bookingBusinessService;

	@Test
	public void testSaveTestTakerTest()
	{
		TestTakerTest testTakerTst = new TestTakerTestImpl();

		Customer customer = new CustomerImpl();
		customer.setId(new Long(1));

		Form frm = new FormImpl();
		frm.setFormID(new Long(1));

		Set<Booking> bkngs = new HashSet<Booking>();

		Booking bkng1 = new BookingImpl();

		bkng1.setTestVariation(getTestVariation());
		bkng1.setForm(frm);
		bkng1.setAppointmentDateTime(new Date());
		bkng1.setTimeZoneOffsetQuantity(-5f);
		bkng1.setEtsApptID("123456713");
		LanguageType lang = new LanguageTypeImpl();
    	lang.setCode("EN");
    	bkngs.add(bkng1);

		testTakerTst.setCustomer(customer);
		testTakerTst.setBookings(bkngs);
		TestStatusType tst= new TestStatusTypeImpl();
		tst.setCode("SCDL");
		testTakerTst.setTestStatusCode(tst);

		TestTakerTest returnedObject = testTakerTestdao.save(testTakerTst);
		logger.debug("Test Taker Test Object {}",returnedObject);

		List<TestTakerTest> testtkTst = testTakerTestdao.getAll(null);

		TestTakerTest ttt1 = testtkTst.get(0);
		Set<Booking> bookSet = ttt1.getBookings();
		Iterator<Booking> itBooking = bookSet.iterator();
		Booking book1 = null;
		while(itBooking.hasNext())
		{
			book1 = itBooking.next();
		}

		Assert.assertEquals(new Long(1), ttt1.getTestTakerTestId());
		Assert.assertEquals("SCDL", ttt1.getTestStatusCode().getCode());
		Assert.assertEquals(1, ttt1.getBookings().size());
		Assert.assertEquals(new Long(1), ttt1.getCustomer().getId());
		Assert.assertEquals(new Long(1), book1.getTestTakerTestId().getTestTakerTestId());
		//Assert.assertEquals("123456713", book1.getEtsApptID());
	}
	private TestVariation getTestVariation(){
		TestVariation testVariation = new TestVariationImpl();
		TestVarianceId id = new TestVarianceId();
		id.setDeliveryModeCode("PBT");
		id.setLanguageCode("EN");
		id.setTestId(1L);
		testVariation.setId(id);

		return testVariation;

	}
}
