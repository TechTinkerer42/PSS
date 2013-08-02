package org.ets.ereg.common.business.rules;

import org.ets.ereg.common.business.rule.querycomposer.AbstractDatabaseQueryRule;
import org.ets.ereg.common.business.util.MappedRuleParameter;
import org.ets.ereg.common.business.vo.RetakeRulesRequestVo;

public class RetakeTestMaxTestAttemptRule extends
		AbstractDatabaseQueryRule<RetakeRulesRequestVo> {

	private String select =  " CASE WHEN (APNTMT_DT2 IS NOT  NULL) THEN 'Yes' ELSE 'No' END APNTMT_DT2, RULECODE2 ";
	@MappedRuleParameter(dbCode = "MTP")
	Integer maxAttempts = 3;

	@Override
	public String getRuleSqlQuery(RetakeRulesRequestVo retakeRulesRequestVo) {

		String sql = " LEFT JOIN (SELECT B.APNTMT_DT APNTMT_DT2,'MTA' RULECODE2 "
				+ " FROM BKNG B,TST_TKR_TST TT,FRM F,TST T "
				+ " WHERE " + COMMON_JOIN
				+ " AND T.TST_ID = :T.TST_ID1 "
				+ " AND TT.CUSTOMER_ID = :TT.CUSTOMER_ID1"
				+ " ) RULE2 ON  "
				+ getMaxAttempts()
				+ " <= (SELECT COUNT (1) FROM BKNG B,TST_TKR_TST TT,FRM F,TST T "
				+ " WHERE " + COMMON_JOIN
				+ " AND T.TST_ID = :T.TST_ID2 "
				+ " AND TT.CUSTOMER_ID = :TT.CUSTOMER_ID2"
				+ " AND TO_CHAR(RULE2.APNTMT_DT2,'YYYY') = TO_CHAR(DATES.D,'YYYY')) ";

		getParameterNames().add("TT.CUSTOMER_ID1");
		getParameterValues().add(retakeRulesRequestVo.getCustomerId());
		getParameterNames().add("T.TST_ID1");
		getParameterValues().add(retakeRulesRequestVo.getTestId());
		getParameterNames().add("TT.CUSTOMER_ID2");
		getParameterValues().add(retakeRulesRequestVo.getCustomerId());
		getParameterNames().add("T.TST_ID2");
		getParameterValues().add(retakeRulesRequestVo.getTestId());

		return sql;
	}

	@Override
	public  String geSelectQuery(){
		return select;
	}

	public Integer getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(Integer maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

}
