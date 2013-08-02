package org.ets.ereg.session.facade.program.service;

import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.service.ProgramService;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.springframework.stereotype.Service;

@Service("programBusinessService")
public class ProgramBusinessServiceImpl implements ProgramBusinessService{

	@Resource(name = "programService")
    private ProgramService programService;

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.session.facade.program.service.ProgramBusinessService#getAll()
	 */
	@Override
	public List<ProgramType> getAll() {
		return programService.getAll();
	}

	/*
	 * (non-Javadoc)
	 * @see org.ets.ereg.session.facade.program.service.ProgramBusinessService#getProgramByPrimaryKey(java.lang.String)
	 */
	@Override
	public ProgramType getProgramByPrimaryKey(String primaryKey){
			return programService.getProgramByPrimaryKey(primaryKey);

	}

}
