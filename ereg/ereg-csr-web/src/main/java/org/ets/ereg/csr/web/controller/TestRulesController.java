package org.ets.ereg.csr.web.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ets.ereg.common.business.service.RulesService;
import org.ets.ereg.common.business.vo.RetakeRulesRequestVo;
import org.ets.ereg.common.business.vo.RulesResponseVo;
import org.ets.ereg.common.exception.ERegCheckedException;
import org.ets.ereg.csr.web.form.TestRulesForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/common/testRules")
public class TestRulesController {

	private static Logger logger = LoggerFactory.getLogger(TestRulesController.class);

	@Resource(name = "reTakeTestRulesService")
	RulesService<RetakeRulesRequestVo> reTakeTestRulesService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	protected String search(
			@ModelAttribute("testRulesForm") TestRulesForm testRulesForm) {
		return "testRules";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	protected @ResponseBody  List<RulesResponseVo> searchTestTakerRosterByCriteria(
			@ModelAttribute("testRulesForm") TestRulesForm testRulesForm,
			BindingResult errors, HttpServletRequest request,
			HttpServletResponse response) throws ERegCheckedException {
		List<RulesResponseVo> listRulesResponseVo = null;
/*		logger.debug("Start Date :" + testRulesForm.getStartDate());
		logger.debug("End Date :" + testRulesForm.getEndDate());
*/
		try {
			RetakeRulesRequestVo retakeRulesRequestVo = new RetakeRulesRequestVo();
			retakeRulesRequestVo.setCustomerId(new Long(testRulesForm.getCustomerId()));
			retakeRulesRequestVo.setTestId(testRulesForm.getTestId());
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date stDate = dateFormat.parse(testRulesForm.getStartDate());
			Timestamp startDate = new Timestamp(stDate.getTime());
			Date enDate = dateFormat.parse(testRulesForm.getEndDate());
			Timestamp endDate = new Timestamp(enDate.getTime());
			retakeRulesRequestVo.setRuleStartDate(startDate);
			retakeRulesRequestVo.setRuleEndDate(endDate);
			listRulesResponseVo = reTakeTestRulesService
					.applyRules(retakeRulesRequestVo);
			for (RulesResponseVo rulesResponseVo : listRulesResponseVo) {
				logger.debug("rulesResponseVo: {}",rulesResponseVo);
			}


		} catch (Exception e) {
			logger.error("exception occurred",e);
		}
		return listRulesResponseVo;

	}

}
