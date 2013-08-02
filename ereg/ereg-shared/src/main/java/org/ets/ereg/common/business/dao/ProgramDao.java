package org.ets.ereg.common.business.dao;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.test.Test;

public interface ProgramDao extends Dao<ProgramType>{

    /**
     * Method to get program using primary key
     *
     * @param primaryKey
     * @return
     */
    ProgramType findByPrimaryKey(Object primaryKey);

    /**
     * Method to get all programs from database
     * @return
     */
    List<ProgramType> getAll();
    
    /**
     * Method to get all tests for a program
     * @param programCode
     * @return
     */
    List<Test> getAllTests(String programCode);
}
