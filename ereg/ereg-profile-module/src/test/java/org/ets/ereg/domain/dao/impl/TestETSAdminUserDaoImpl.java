package org.ets.ereg.domain.dao.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.profile.ETSAdminUserImpl;
import org.ets.ereg.profile.domain.dao.impl.ETSAdminUserDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestETSAdminUserDaoImpl {
	
	private static Logger logger = LoggerFactory.getLogger(TestETSAdminUserDaoImpl.class);

    @Resource(name="etsAdminUserDao")
	private ETSAdminUserDaoImpl etsAdminUserDao;

   /* @PersistenceContext(unitName = "blPU")
    protected EntityManager em;*/

	@Test
	public void testInsertAdminUser(){
		ETSAdminUser adminUser = new ETSAdminUserImpl();
		adminUser.setEmail("rdash0012@ets.org");
		adminUser.setFirstName("Rakesh");
		//adminUser.setMiddleName("M");
		adminUser.setLastName("Dash");
		adminUser.setLogin("rdash001");
		adminUser.setPassword("rdash001");
		adminUser = (ETSAdminUser) etsAdminUserDao.saveAdminUser(adminUser);
		Assert.assertNotNull(adminUser);
	}

	@Test
    public void testSearchAdminUserByUserName(){
        ETSAdminUser adminUser = (ETSAdminUser) etsAdminUserDao.readAdminUserByUserName("rdash");
        Assert.assertNotNull(adminUser);
        Assert.assertNotNull(adminUser.getEmail());
        Assert.assertNotNull(adminUser.getFirstName());
        Assert.assertNotNull(adminUser.getLastName());
        Assert.assertNotNull(adminUser.getLogin());
        Assert.assertNotNull(adminUser.getPassword());
    }

	@Test
    public void testSearchAdminUserByEmail(){
        List<AdminUser> adminUsers = etsAdminUserDao.readAdminUserByEmail("rdash0012@ets.org");
        ETSAdminUser etsAdminUser = null;
        Assert.assertNotNull(adminUsers);
        for (AdminUser adminUser : adminUsers) {
            etsAdminUser = (ETSAdminUser) adminUser;
            Assert.assertNotNull(etsAdminUser);
            Assert.assertNotNull(etsAdminUser.getFirstName());
            Assert.assertNotNull(etsAdminUser.getLastName());
            Assert.assertNotNull(etsAdminUser.getLogin());
            Assert.assertNotNull(etsAdminUser.getPassword());
        }

    }

	@Test
    public void testSearchAdminUserById(){
        ETSAdminUser adminUser = (ETSAdminUser) etsAdminUserDao.readAdminUserById(1l);
        Assert.assertNotNull(adminUser);
        Assert.assertNotNull(adminUser.getFirstName());
        Assert.assertNotNull(adminUser.getLastName());
        Assert.assertNotNull(adminUser.getLogin());
        Assert.assertNotNull(adminUser.getPassword());
    }

	@Test
    public void testSearchAllAdminUser(){
        List<AdminUser> adminUsers =  etsAdminUserDao.readAllAdminUsers();
        ETSAdminUser etsAdminUser = null;
        Assert.assertNotNull(adminUsers);
        Assert.assertEquals(5, adminUsers.size());
        for (AdminUser adminUser : adminUsers) {
            etsAdminUser = (ETSAdminUser) adminUser;
            Assert.assertNotNull(etsAdminUser);
            Assert.assertNotNull(etsAdminUser.getFirstName());
            Assert.assertNotNull(etsAdminUser.getLastName());
            Assert.assertNotNull(etsAdminUser.getLogin());
            Assert.assertNotNull(etsAdminUser.getPassword());
        }
	}

	@Test
    @Transactional
    public void testDeleteAdminUser(){
        AdminUser adminUser = etsAdminUserDao.readAdminUserByUserName("rdash");
        Assert.assertNotNull(adminUser);
        logger.debug("admin user id {}",adminUser.getId());
        etsAdminUserDao.deleteAdminUser(adminUser);
    }

    @Test
    public void testSelectDeletedAdminUser(){
        ETSAdminUser etsAdminUser = (ETSAdminUser) etsAdminUserDao.readAdminUserByUserName("rdash");
        Assert.assertNotNull(etsAdminUser);
        Assert.assertNotNull(etsAdminUser.getLogin());
    }
    
	@Test
    public void testSearchAdminUserByGuId(){
        ETSAdminUser adminUser = (ETSAdminUser) etsAdminUserDao.findUserByGuId("jsmithguid");
        assertNotNull(adminUser);
        assertThat(adminUser.getId(), equalTo(1L));
        adminUser = (ETSAdminUser) etsAdminUserDao.findUserByGuId("CXCXCXCXC");
        assertNull(adminUser);
    }
	
	@Test
    public void testSearchAdminUserByUsernameAndInternalFlag(){
        ETSAdminUser adminUser = (ETSAdminUser) etsAdminUserDao.findUserByUsernameAndInternalFlag("john_smith",Boolean.TRUE);
        assertNotNull(adminUser);
        assertThat(adminUser.getId(), equalTo(1L));
        adminUser = (ETSAdminUser) etsAdminUserDao.findUserByUsernameAndInternalFlag("john_smith",Boolean.FALSE);
        assertNull(adminUser);
    }
}
