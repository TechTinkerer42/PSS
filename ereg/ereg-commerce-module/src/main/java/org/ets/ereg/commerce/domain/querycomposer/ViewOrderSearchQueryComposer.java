/**
 * 
 */
package org.ets.ereg.commerce.domain.querycomposer;

import java.util.Calendar;

import org.ets.ereg.commerce.order.vo.ViewOrderSearchCriteriaVO;
import org.ets.ereg.common.business.util.Constant;
import org.ets.ereg.common.querycomposer.AbstractQueryComposer;
import org.ets.ereg.common.querycomposer.ComposedQuery;
import org.ets.ereg.common.vo.AbstractSearchCriteriaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewOrderSearchQueryComposer extends AbstractQueryComposer {

	private static Logger logger = LoggerFactory
			.getLogger(ViewOrderSearchQueryComposer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ets.ereg.common.web.AbstractQueryComposer#compose(org.ets.ereg.common
	 * .web.AbstractSearchCriteriaVo)
	 */
	/**
	 * @param criteria
	 */
	public ViewOrderSearchQueryComposer(AbstractSearchCriteriaVO criteria) {
		super(criteria);
	}

	@Override
	public ComposedQuery compose(AbstractSearchCriteriaVO criteria) {

		getComposedQuery().setQuery(
				composeSelect() + composeFrom() + composeWhere());

		//addParameter("Max", criteria.getTotalRowsPerPage());
		//addParameter("Min", criteria.getFirstRow());

		return getComposedQuery();
	}

	public ComposedQuery composeCount(AbstractSearchCriteriaVO criteria) {

		getComposedQuery().setQuery(
				composeCountSelect() + composeFrom() + composeWhere());

		return getComposedQuery();

	}

	/**
	 * @return
	 */
	private String composeCountSelect() {
		return "select count(1)   from ( select bo.order_number,bo.order_total,bo.submit_date,t.tst_nam,b.apntmt_dt ";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ereg.common.web.AbstractQueryComposer#composeSelect()
	 */
	protected String composeSelect() {
		return "select bo.order_number,bo.order_total,bo.submit_date,t.tst_nam,b.apntmt_dt ";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ereg.common.web.AbstractQueryComposer#composeFrom()
	 */
	protected String composeFrom() {
		return " from  blc_order bo, tst_tkr_tst tt ,bkng b,tst t,tst_vartn tv,blc_order_item boi,tst_dscrt_ord_itm_bkng td ";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ereg.common.web.AbstractQueryComposer#composeWhere()
	 */
	protected String composeWhere() {

		ViewOrderSearchCriteriaVO testTakerRosterCritaria = (ViewOrderSearchCriteriaVO) getCriteria();

		StringBuffer whereClause = new StringBuffer(
				//" where  bo.customer_id = tt.customer_id and tt.tst_tkr_tst_id = b.tst_tkr_tst_id and b.tst_cde=t.tst_cde and b.pgm_cde=t.pgm_cde and bo.order_id = boi.order_id and boi.order_item_id = td.order_item_id and td.bkng_id = b.bkng_id and b.bkng_sts_typ_cde ='RSRVD'and bo.order_status='SUBMITTED' ");
				//remove and b.bkng_sts_typ_cde ='RSRVD'
				" where  bo.customer_id = tt.customer_id and tt.tst_tkr_tst_id = b.tst_tkr_tst_id and b.tst_id=t.tst_id and b.tst_id = tv.tst_id and b.dlvy_mde_cde = tv.dlvy_mde_cde and b.lang_cde = tv.lang_cde and tv.tst_id = t.tst_id and bo.order_id = boi.order_id and boi.order_item_id = td.order_item_id and td.bkng_id = b.bkng_id and bo.order_status='SUBMITTED' ");

		whereClause.append(" and ");
		appendEquals(whereClause, "bo.customer_id",
				testTakerRosterCritaria.getCustomerId());

		Calendar currentYear = Calendar.getInstance();
		currentYear.add(Calendar.YEAR, 0);

		Calendar oneYear = Calendar.getInstance();
		oneYear.add(Calendar.YEAR, -1);

		if (Constant.RECENT_ORDER.equals(testTakerRosterCritaria.getViewOrder())) {
			whereClause
					.append(" and bo.submit_date between add_months (sysdate, -6) and sysdate ");
		} else if (String.valueOf(currentYear.get(Calendar.YEAR)).equals(
				testTakerRosterCritaria.getViewOrder())) {
			whereClause.append("  and  to_char(bo.SUBMIT_DATE,'yyyy') = "+ String.valueOf(currentYear.get(Calendar.YEAR)));
		}else if (String.valueOf(oneYear.get(Calendar.YEAR)).equals(
				testTakerRosterCritaria.getViewOrder())) {
			whereClause.append("  and  to_char(bo.SUBMIT_DATE,'yyyy') = "+ String.valueOf(oneYear.get(Calendar.YEAR)));
		}
		
		whereClause.append(" order by  bo.submit_date,b.apntmt_dt");
		

		return whereClause.toString();
	}
}
