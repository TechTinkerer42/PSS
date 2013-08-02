package org.ets.ereg.common.business.rules;

import org.ets.ereg.common.business.rule.querycomposer.AbstractDatabaseQueryRule;
import org.ets.ereg.common.business.util.MappedRuleParameter;
import org.ets.ereg.common.business.vo.RetakeRulesRequestVo;

public class RetakeTestWaitPeriodRule extends
		AbstractDatabaseQueryRule<RetakeRulesRequestVo> {

	private String select = " CASE WHEN (APNTMT_DT IS NOT  NULL) THEN 'Yes' ELSE 'No' END APNTMT_DT, RULECODE ";

	@MappedRuleParameter(dbCode = "WPP")
	Integer waitPeriod = 21;

	@Override
	public String getRuleSqlQuery(RetakeRulesRequestVo retakeRulesRequestVo) {

		String sql = " LEFT JOIN (SELECT B.APNTMT_DT APNTMT_DT,'WPR' RULECODE FROM "
				+ " BKNG B,TST_TKR_TST TT,FRM F,TST T "
				+ " WHERE " + COMMON_JOIN
				+ " AND T.TST_ID = :T.TST_ID "
				+ " AND TT.CUSTOMER_ID = :TT.CUSTOMER_ID"
				+ " ) RULE1 ON "
				+ " (RULE1.APNTMT_DT BETWEEN (DATES.D - "
				+ getWaitPeriod()
				+ ") AND DATES.D + "
				+ getWaitPeriod()
				+ ") ";


		getParameterNames().add("TT.CUSTOMER_ID");
		getParameterValues().add(retakeRulesRequestVo.getCustomerId());
		getParameterNames().add("T.TST_ID");
		getParameterValues().add(retakeRulesRequestVo.getTestId());

		return sql;
	}

	@Override
	public  String geSelectQuery(){
		return select;
	}

	public Integer getWaitPeriod() {
		return waitPeriod;
	}

	public void setWaitPeriod(Integer waitPeriod) {
		this.waitPeriod = waitPeriod;
	}




}
