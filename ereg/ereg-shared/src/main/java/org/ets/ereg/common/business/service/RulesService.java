package org.ets.ereg.common.business.service;

import java.util.List;

import org.ets.ereg.common.business.vo.RulesResponseVo;

public interface RulesService<RequestVo> {

	public List<RulesResponseVo> applyRules(RequestVo requestVo);
}
