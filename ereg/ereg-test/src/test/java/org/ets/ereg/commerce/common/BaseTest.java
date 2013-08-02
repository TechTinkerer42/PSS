package org.ets.ereg.commerce.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.broadleafcommerce.common.extensibility.context.MergeClassPathXMLApplicationContext;
import org.broadleafcommerce.common.extensibility.context.StandardConfigLocations;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners(inheritListeners = false, value = {MergeDependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, MergeTransactionalTestExecutionListener.class})
public abstract class BaseTest{

	private static MergeClassPathXMLApplicationContext mergeContext = null;

	public static MergeClassPathXMLApplicationContext getContext() {
		try {
			if (mergeContext == null) {
				String[] contexts = StandardConfigLocations.retrieveAll(StandardConfigLocations.SERVICECONTEXTTYPE);
				String[] allContexts = new String[contexts.length + 11];
				String[] tempContexts = new String[] {
					"bl-open-admin-contentClient-applicationContext.xml",
                    "bl-open-admin-contentCreator-applicationContext.xml",
                    "bl-admin-applicationContext.xml",
                    "bl-oms-applicationContext.xml",          
                    "bl-framework-applicationContext-workflow.xml",
					"META-INF/ets-ereg-persistence-test.xml",
					//"ets-domain-applicationContext.xml",
					//"ets-applicationContext-test-security.xml",
					"ets-scheduling-applicationContext-test.xml",
					"ets-ereg-applicationContext-test.xml",					
					"applicationContext-test-email.xml",					
					"application-context-commerce.xml",
					"application-context-common-business.xml"
					
				};
				System.arraycopy(contexts, 0, allContexts, 0, contexts.length);
				System.arraycopy(tempContexts, 0, allContexts, contexts.length, tempContexts.length);
				mergeContext = new MergeClassPathXMLApplicationContext(allContexts, new String[]{});
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return mergeContext;
	}

	@PersistenceContext(unitName = "blPU")
    protected EntityManager em;

}