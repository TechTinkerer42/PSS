package org.ets.ereg.shared.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.ets.ereg.common.business.dao.ReferenceEntityDao;
import org.ets.ereg.common.business.service.impl.ReferenceServiceImpl;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria.ReferenceTypeOrderBy;
import org.ets.ereg.domain.common.MilitaryStatusTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.MilitaryStatusType;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestReferenceServiceImpl {
	private static ReferenceServiceImpl referenceService = new ReferenceServiceImpl();

	private static Mockery mockingContext = new Mockery();
	private static ReferenceEntityDao mockedReferenceEntityDao;

	@BeforeClass
	public static void setup() {
		setupMockObjects();
		referenceService.setReferenceEntityDao(mockedReferenceEntityDao);
	}

	private static void setupMockObjects() {
	    mockedReferenceEntityDao = mockingContext.mock(ReferenceEntityDao.class);
	}


	@Test
	public void testMilitaryStatusGetAllRecords() {
		//setup test data
	    final List<MilitaryStatusType> militaryStatusList = new ArrayList<MilitaryStatusType>();

	    MilitaryStatusType militaryStatus = new MilitaryStatusTypeImpl();
	    militaryStatus.setCode("NOTAM");
	    militaryStatus.setDescription("Not a Member");
	    militaryStatus.setDisplaySequence(1l);
	    militaryStatus.setDisplayable(false);
	    militaryStatusList.add(militaryStatus);

	    militaryStatus = new MilitaryStatusTypeImpl();
	    militaryStatus.setCode("ACTIV");
        militaryStatus.setDescription("Active");
        militaryStatus.setDisplaySequence(2l);
        militaryStatus.setDisplayable(true);
        militaryStatusList.add(militaryStatus);

        militaryStatus = new MilitaryStatusTypeImpl();
        militaryStatus.setCode("RESRV");
        militaryStatus.setDescription("Reserve");
        militaryStatus.setDisplaySequence(3l);
        militaryStatus.setDisplayable(false);
        militaryStatusList.add(militaryStatus);

        militaryStatus = new MilitaryStatusTypeImpl();
        militaryStatus.setCode("VETRN");
        militaryStatus.setDescription("Veteran");
        militaryStatus.setDisplaySequence(4l);
        militaryStatus.setDisplayable(true);
	    militaryStatusList.add(militaryStatus);

	    final ReferenceTypeCriteria criteria = new ReferenceTypeCriteria();

        ReferenceTypeOrderBy firstOrder = new ReferenceTypeOrderBy();
        ReferenceTypeOrderBy secondOrder = new ReferenceTypeOrderBy();
        ReferenceTypeOrderBy thirdOrder = new ReferenceTypeOrderBy();

        List<ReferenceTypeOrderBy> orderByList = new ArrayList<ReferenceTypeOrderBy>();

        firstOrder.setAscending(true);
        firstOrder.setOrderByAttribute(ReferenceTypeCriteria.ORDER_BY_CODE_COLUMN);
        orderByList.add(firstOrder);

        secondOrder.setAscending(false);
        secondOrder.setOrderByAttribute(ReferenceTypeCriteria.ORDER_BY_DESCRIPTION_COLUMN);
        orderByList.add(secondOrder);

        thirdOrder.setAscending(true);
        thirdOrder.setOrderByAttribute(ReferenceTypeCriteria.ORDER_BY_SEQUENCE_COLUMN);
        orderByList.add(thirdOrder);

        criteria.setOrderbyList(orderByList);

		mockingContext.checking(new Expectations() {
		   {
             one(mockedReferenceEntityDao).getAll(MilitaryStatusType.class, criteria, false);
             will(returnValue(militaryStatusList));
           }
		});

		List<MilitaryStatusType> statusList = referenceService.getAll(MilitaryStatusType.class, criteria, false);
		Assert.assertEquals(4, statusList.size());
		Assert.assertNotNull(statusList);

		mockingContext.assertIsSatisfied();
	}

	@Test
    public void testMilitaryStatusGetRecordByPrimarykey() {
	  //setup test data

        final MilitaryStatusType militaryStatus = new MilitaryStatusTypeImpl();
        militaryStatus.setCode("NOTAM");
        militaryStatus.setDescription("Not a Member");
        militaryStatus.setDisplaySequence(1l);
        militaryStatus.setDisplayable(false);

        mockingContext.checking(new Expectations() {
            {
              one(mockedReferenceEntityDao).getEntityByPrimaryKey(MilitaryStatusType.class, "NOTAM");
              will(returnValue(militaryStatus));
            }
        });

        MilitaryStatusType status = referenceService.getEntityByPrimaryKey(MilitaryStatusType.class, "NOTAM");
        Assert.assertNotNull(status);

        mockingContext.assertIsSatisfied();

	}

}
