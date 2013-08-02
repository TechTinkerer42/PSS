package org.ets.ereg.common.business.rules;

import org.ets.ereg.common.business.rule.querycomposer.AbstractDatabaseQueryRule;
import org.ets.ereg.common.business.vo.RetakeRulesRequestVo;

public class LifetimeLimitRule extends
		AbstractDatabaseQueryRule<RetakeRulesRequestVo> {

	@Override
	public String getRuleSqlQuery(RetakeRulesRequestVo retakeRulesRequestVo) {
		// TODO Auto-generated method stub


		String sql = " LEFT JOIN (select b.APNTMT_DT APNTMT_DT1,'LTL' ruleCode1,b.EVNT_OTCM_STS_TYP_CDE  from BKNG b,TST_TKR_TST tt,FRM f,TST t where "
				+ " b.TST_TKR_TST_ID = tt.TST_TKR_TST_ID "
				+ " and b.frm_id = f.frm_id "
				/*+ " and f.pgm_cde = t.pgm_cde "*/
				+ " and f.tst_id = t.tst_id"
				+ " and t.tst_id = 'MATH' "
				+ " and  b.TST_TKR_TST_ID =1 "
				+ " ) rule2 ON "
				+ " (rule2. APNTMT_DT1 between (dates.d - 21) and dates.d + 21) ";

		return sql;
	}


	@Override
	public  String geSelectQuery(){
		return "";
	}

}
