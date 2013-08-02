/**
 * 
 */
package org.ets.pss.persistence.impl;

import java.util.List;

import javax.persistence.Query;

import org.ets.pss.persistence.dao.MdaMimeTypDao;
import org.ets.pss.persistence.model.MdaMimeTyp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author SSINGH007
 *
 */
@Repository
@Qualifier("mdaMimeTypDao")
public class MdaMimeTypDaoImpl extends GenericDaoImpl<MdaMimeTyp> implements
		MdaMimeTypDao {

	public MdaMimeTyp getByMimeTypeName(String mimeType)
	{
		Query q = em().createQuery("SELECT x FROM MdaMimeTyp x where x.mimeTypNam = ?1");
		q.setParameter(1, mimeType);
		@SuppressWarnings("unchecked")
		List<MdaMimeTyp> mimeTypes = (List<MdaMimeTyp>) q.getResultList();
		
		if(mimeTypes != null && mimeTypes.size()>0)
		{
			return mimeTypes.get(0);
		}
		else
		{
			return null;
		}
	}
}
