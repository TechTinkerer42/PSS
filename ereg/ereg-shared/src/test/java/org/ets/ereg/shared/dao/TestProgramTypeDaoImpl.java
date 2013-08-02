package org.ets.ereg.shared.dao;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.ets.ereg.common.business.dao.ProgramDao;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestProgramTypeDaoImpl {

    @Resource(name="programDao")
    private ProgramDao programDao;

    @Test
    public void testProgramTypeGetAll() {
        List<ProgramType> types = programDao.getAll();
        Assert.assertEquals(9, types.size());
    }

    @Test
    public void testProgramTypeGetEntityByPrimaryKey() {
        ProgramType program = programDao.findByPrimaryKey("PSAT");
        Assert.assertNotNull(program);
        Assert.assertEquals("PSAT", program.getCode());
        Assert.assertEquals("Preliminary SAT", program.getDescription());
    }

}
