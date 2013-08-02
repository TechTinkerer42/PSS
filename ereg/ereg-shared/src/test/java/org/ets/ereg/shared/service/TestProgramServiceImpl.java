package org.ets.ereg.shared.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.ets.ereg.common.business.dao.ProgramDao;
import org.ets.ereg.common.business.service.impl.ProgramServiceImpl;
import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestProgramServiceImpl {
	private static ProgramServiceImpl programService = new ProgramServiceImpl();

	private static Mockery mockingContext = new Mockery();
	private static ProgramDao mockedProgramDao;

	@BeforeClass
	public static void setup() {
		setupMockObjects();
		programService.setProgramDao(mockedProgramDao);
	}

	private static void setupMockObjects() {
	    mockedProgramDao = mockingContext.mock(ProgramDao.class);
	}


	@Test
	public void testProgramGetAllRecords() {
		//setup test data
	    final List<ProgramType> programTypeList = new ArrayList<ProgramType>();

	    ProgramType programType = new ProgramTypeImpl();
	    programType.setCode("GRE");
	    programType.setDescription("Graduate Record Examinations");
	    programTypeList.add(programType);

	    programType = new ProgramTypeImpl();
	    programType.setCode("PSAT");
	    programType.setDescription("Preliminary SAT");
	    programTypeList.add(programType);

        programType = new ProgramTypeImpl();
        programType.setCode("CLEP");
        programType.setDescription("College Level Examination Program");
        programTypeList.add(programType);

		mockingContext.checking(new Expectations() {
		   {
             one(mockedProgramDao).getAll();
             will(returnValue(programTypeList));
           }
		});

		List<ProgramType> statusList = programService.getAll();
		Assert.assertEquals(3, statusList.size());
		Assert.assertNotNull(statusList);

		mockingContext.assertIsSatisfied();
	}

	@Test
    public void testMilitaryStatusGetRecordByPrimarykey() {

	    final ProgramType programType = new ProgramTypeImpl();
        programType.setCode("GRE");
        programType.setDescription("Graduate Record Examinations");

        mockingContext.checking(new Expectations() {
            {
              one(mockedProgramDao).findByPrimaryKey("GRE");
              will(returnValue(programType));
            }
        });

        ProgramType status = programService.getProgramByPrimaryKey("GRE");
        Assert.assertNotNull(status);

        mockingContext.assertIsSatisfied();

	}

}
