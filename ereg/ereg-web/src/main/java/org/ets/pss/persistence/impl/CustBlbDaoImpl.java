package org.ets.pss.persistence.impl;


import java.util.ArrayList;
import java.util.List;

import org.ets.pss.persistence.dao.CustBlbDao;
import org.ets.pss.persistence.model.CrBlb;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author asampath
 *
 */
@Repository
@Qualifier("custBlbDao")
public class CustBlbDaoImpl extends GenericDaoImpl<CrBlb> implements CustBlbDao {

	
	public List<String> getCurrentVideoEntriesForPrompt(long promptId,long taskId,long custId)
	{
		List<String> videoList = new ArrayList<String>();
		
		@SuppressWarnings("unchecked")
		List<CrBlb> videos = 
	    		(List<CrBlb>) em().createQuery("select blb from CrBlb blb where customer_id=?1 and prmpt_id=?2 and tsk_id=?3")
	                      .setParameter(1,custId).setParameter(2, promptId).setParameter(3,taskId).getResultList();
		
		if(videos != null && videos.size()>0)
		{
			for(int i=0;i<videos.size();i++)
			{
				CrBlb video=(CrBlb)videos.get(i);
			
				videoList.add( video.getMediaId());
				
				
				if(video.getRspSrcLctnNam()!=null)
				{
					
				videoList.add(video.getRspSrcLctnNam());//new stuff ashwin 
				}
			}
		}

		return videoList;
	}

}
