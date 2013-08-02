package org.ets.ereg.session.facade.program.service;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.common.ProgramType;

public interface ProgramBusinessService {

	/**
	 * Method to get list of programs.
	 *
	 * @return
	 */
	List<ProgramType> getAll();

	/**
	 * Method to get program by providing Program Id/Code
	 *
	 * @param primaryKey
	 * @return
	 */
	ProgramType getProgramByPrimaryKey(String primaryKey);

}
