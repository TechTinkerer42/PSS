package org.ets.ereg.common.business.rule.querycomposer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDatabaseQueryRule <RequestVo> {


	public static String COMMON_JOIN = "  B.TST_TKR_TST_ID = TT.TST_TKR_TST_ID "
			+ " AND B.FRM_ID = F.FRM_ID "
			/*+ " AND F.PGM_CDE = T.PGM_CDE "*/
			+ " AND F.TST_ID = T.TST_ID "
			+ " AND (B.EVNT_OTCM_STS_TYP_CDE ='CHI' OR B.EVNT_OTCM_STS_TYP_CDE ='NCI' ) "
			+ " AND B.BKNG_STS_TYP_CDE = 'RSRVD' ";

	/**
	 * Parameter names which will be used to bind parameters as required by the rule
	 */
	private List<String> parameterNames = new ArrayList<String>();

	/**
	 * Values for such parameters (could be single or collections)
	 */
	private List<Object> parameterValues = new ArrayList<Object>();

	//abstratc method
	public String getRuleSql(RequestVo requestVo) {

		return getRuleSqlQuery(requestVo);
	}

	protected abstract String getRuleSqlQuery(RequestVo requestVo);

	protected abstract String geSelectQuery();


	public List<String> getParameterNames() {
		return parameterNames;
	}


	public void setParameterNames(List<String> parameterNames) {
		this.parameterNames = parameterNames;
	}


	public List<Object> getParameterValues() {
		return parameterValues;
	}


	public void setParameterValues(List<Object> parameterValues) {
		this.parameterValues = parameterValues;
	}


	protected String uniqueParameterName(String name) {

		return this.getClass().getSimpleName() + "_" + name;
	}


	protected void addParameterName(String param) {

		if(getParameterNames() == null) {
			setParameterNames(new ArrayList<String>());
		}

		getParameterNames().add(uniqueParameterName(param));
	}

	protected void addParameterValue(Object value) {

		if(getParameterValues() == null) {
			setParameterValues(new ArrayList<Object>());
		}

		getParameterValues().add(value);
	}

	protected void addParameter(String name, Object value) {
		addParameterName(name);
		addParameterValue(value);
	}
}
