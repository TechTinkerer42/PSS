/**
 * 
 */
package org.ets.pss.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import org.ets.ereg.security.user.ERegUser;
import org.ets.pss.persistence.dao.AsgndTskDao;
import org.ets.pss.persistence.model.AsgndTsk;
import org.ets.pss.persistence.model.CrBlb;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author SSINGH007
 *
 */
@Repository("asgndTskDao")
public class AsgndTskDaoImpl extends GenericDaoImpl<AsgndTsk> implements AsgndTskDao {
	public List<AsgndTsk> getCurrentTasks()
	{
		List<AsgndTsk> taskList = new ArrayList<AsgndTsk>();
		
		@SuppressWarnings("unchecked")
		List<AsgndTsk> tasks = 
	    		(List<AsgndTsk>) em().createQuery("select x from AsgndTsk x").getResultList();
	                      
		
		if(tasks != null && tasks.size()>0)
		{
			for(int i=0;i<tasks.size();i++)
			{
				AsgndTsk task=(AsgndTsk)tasks.get(i);
			
			
				taskList.add( task);
			}
		}

		return taskList;
	}
}
