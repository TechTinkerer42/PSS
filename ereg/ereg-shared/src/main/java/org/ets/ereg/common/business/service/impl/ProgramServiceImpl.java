package org.ets.ereg.common.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.ProgramDao;
import org.ets.ereg.common.business.service.ProgramService;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.springframework.stereotype.Service;

@Service("programService")
public class ProgramServiceImpl implements  ProgramService{

	@Resource(name = "programDao")
    private ProgramDao programDao;

	public ProgramDao getProgramDao() {
        return programDao;
    }

    public void setProgramDao(ProgramDao programDao) {
        this.programDao = programDao;
    }

    /*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.service.ProgramService#getAll()
	 */
	@Override
	public List<ProgramType> getAll() {
		return programDao.getAll();

	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.common.business.service.ProgramService#getProgramByPrimaryKey(java.lang.String)
	 */
	@Override
	public ProgramType getProgramByPrimaryKey(String primaryKey) {
		return programDao.findByPrimaryKey(primaryKey);
	}

	@Override
	public List<Test> getAllTests(String programCode) {
		return programDao.getAllTests(programCode);
	}

}
