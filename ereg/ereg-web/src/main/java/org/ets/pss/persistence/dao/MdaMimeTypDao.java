/**
 * 
 */
package org.ets.pss.persistence.dao;

import org.ets.pss.persistence.model.MdaMimeTyp;

/**
 * @author asampath
 *
 */
public interface MdaMimeTypDao extends GenericDao<MdaMimeTyp> {

	MdaMimeTyp getByMimeTypeName(String mimeType);
}
