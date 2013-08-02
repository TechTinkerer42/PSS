/**
 * 
 */
package org.ets.pss.persistence.impl;

import java.util.ArrayList;
import java.util.List;

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
		List<AsgndTsk> videoList = new ArrayList<AsgndTsk>();
		
		@SuppressWarnings("unchecked")
		List<AsgndTsk> videos = 
	    		(List<AsgndTsk>) em().createQuery("select x from AsgndTsk x").getResultList();
	                      
		
		if(videos != null && videos.size()>0)
		{
			for(int i=0;i<videos.size();i++)
			{
				AsgndTsk video=(AsgndTsk)videos.get(i);
			
				videoList.add( video);
			}
		}

		return videoList;
	}
}
