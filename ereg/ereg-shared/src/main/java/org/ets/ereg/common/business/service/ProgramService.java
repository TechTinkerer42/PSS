package org.ets.ereg.common.business.service;

import java.util.List;

import javax.annotation.Nonnull;

import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.test.Test;

public interface ProgramService {

	/**
	 * Method to get list of all programs
	 *
	 * @return
	 */
	@Nonnull
	List<ProgramType> getAll();

	/**
	 * Method to get program by using primary key
	 *
	 * @param primaryKey
	 * @return
	 */
	@Nonnull
	ProgramType getProgramByPrimaryKey(String primaryKey);

	/**
	 * Method to get all tests for a program
	 * @param programCode
	 * @return
	 */
	List<Test> getAllTests(String programCode);
}
