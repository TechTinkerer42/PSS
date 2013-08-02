/**
 *
 */
package org.ets.ereg.profile.csr.domain.querycomposer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ets.ereg.common.querycomposer.AbstractQueryComposer;
import org.ets.ereg.common.querycomposer.ComposedQuery;
import org.ets.ereg.common.vo.AbstractSearchCriteriaVO;
import org.ets.ereg.profile.domain.vo.TestTakerRosterSearchCriteriaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Seach Candidates by Appointment Query Composer
 *
 * @author Mangesh Pardesi
 *
 * @version 1.0
 *
 * @since e-Reg Release 1.0 - Feb 18, 2013
 */
public class TestTakerRosterSearchQueryComposer extends AbstractQueryComposer {

	private static Logger logger = LoggerFactory.getLogger(TestTakerRosterSearchQueryComposer.class);

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
	public TestTakerRosterSearchQueryComposer(AbstractSearchCriteriaVO criteria) {
		super(criteria);
	}

	@Override
	public ComposedQuery compose(AbstractSearchCriteriaVO criteria) {

		getComposedQuery().setQuery(
				composeSelect() + composeFrom() + composeWhere()
						+ " ) a where rownum <= :Max)  where rnum  > :Min");

		addParameter("Max", criteria.getTotalRowsPerPage());
		addParameter("Min", criteria.getFirstRow());

		return getComposedQuery();
	}

	public ComposedQuery composeCount(AbstractSearchCriteriaVO criteria) {

		getComposedQuery().setQuery(
				composeCountSelect() + composeFrom() + composeWhere() + ")");

		return getComposedQuery();

	}

	/**
	 * @return
	 */
	private String composeCountSelect() {
		return "select count( customer_id)   from  (select  distinct ec.customer_id, bc.first_name,bc.last_name,ec.brth_dte,bp.phone_number,bc.email_address,t.tst_nam,b.bkng_id,b.apntmt_dt,b.ets_apntmt_id,dl.dlvy_mde_dsc,NVL(f.frm_dsc, 'Not Assigned'),eost.evnt_otcm_sts_typ_dsc,case when (select count(1) from bkng_acmdtn bc where bc.bkng_id = b.bkng_id) > 0 then 'Yes' else 'No' end accomodation    ";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.ets.ereg.common.web.AbstractQueryComposer#composeSelect()
	 */
	protected String composeSelect() {
		return "select  customer_id, first_name,last_name,brth_dte,phone_number,email_address,tst_nam,bkng_id,apntmt_dt,ets_apntmt_id,dlvy_mde_dsc,frm_dsc,evnt_otcm_sts_typ_dsc,accomodation   from ( select  a.*, rownum rnum from    (select  distinct ec.customer_id, bc.first_name,bc.last_name,ec.brth_dte,bp.phone_number,bc.email_address,t.tst_nam,b.bkng_id,b.apntmt_dt,b.ets_apntmt_id,dl.dlvy_mde_dsc,NVL(f.frm_dsc, 'Not Assigned') as frm_dsc,eost.evnt_otcm_sts_typ_dsc,case when (select count(1) from bkng_acmdtn bc where bc.bkng_id = b.bkng_id) > 0 then 'Yes' else 'No' end accomodation     ";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.ets.ereg.common.web.AbstractQueryComposer#composeFrom()
	 */
	protected String composeFrom() {
		return "  from ets_cust ec, blc_customer bc ,blc_customer_phone bcp ,blc_phone bp, bkng b left join frm f on  b.frm_id = f.frm_id, tst_tkr_tst tt, tst t ,dlvy_mde dl,tst_cntr_admin tcd,tst_cntr tc,evnt_otcm_sts_typ eost, tst_cntr_pgm tcp  ";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.ets.ereg.common.web.AbstractQueryComposer#composeWhere()
	 */
	protected String composeWhere() {

		TestTakerRosterSearchCriteriaVO testTakerRosterCritaria = (TestTakerRosterSearchCriteriaVO) getCriteria();

		StringBuffer whereClause = new StringBuffer(
				" where ec.customer_id = bc.customer_id and bc.customer_id = bcp.customer_id "
						+ " and bcp.phone_id = bp.phone_id and  b.tst_tkr_tst_id = tt.tst_tkr_tst_id and bc.customer_id = tt.customer_id and "
						+ " b.TST_ID = t.TST_ID and eost.evnt_otcm_sts_typ_cde = b.evnt_otcm_sts_typ_cde and b.tst_id = t.tst_id and b.dlvy_mde_cde = dl.dlvy_mde_cde and b.tst_cntr_id_no = tcd.tst_cntr_id_no and tcd.tst_cntr_id_no = tc.tst_cntr_id_no and tcp.tst_cntr_id_no = tc.tst_cntr_id_no and t.pgm_cde = tcp.pgm_cde ");

		whereClause.append(" and ");
		appendEquals(whereClause, "bcp.phone_name", "primary");
		whereClause.append(" and ");
		appendEquals(whereClause, "tcd.tst_cntr_id_no",
				testTakerRosterCritaria.getTestCenterId());

		whereClause.append(" and ");

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
			Date fromDate = sdf.parse(testTakerRosterCritaria
					.getAppointmentMonthFrom()
					+ "-"
					+ testTakerRosterCritaria.getAppointmentDayFrom()
					+ "-"
					+ testTakerRosterCritaria.getAppointmentYearFrom() + " 00:00:00" );

			java.sql.Timestamp sqlFromDate = new java.sql.Timestamp(fromDate.getTime());

			appendGreaterThanEqual(whereClause, "b.apntmt_dt",
					"b.apntmt_dt_gt", sqlFromDate);

			whereClause.append(" and ");

			Date toDate = sdf.parse(testTakerRosterCritaria
					.getAppointmentMonthTo()
					+ "-"
					+ testTakerRosterCritaria.getAppointmentDayTo()
					+ "-"
					+ testTakerRosterCritaria.getAppointmentYearTo() + " 24:00:00");

			java.sql.Timestamp sqlToDate = new java.sql.Timestamp(toDate.getTime());

			appendLessThanEqual(whereClause, "b.apntmt_dt", "b.apntmt_dt_lt",
					sqlToDate);
		} catch (Exception e) {
			logger.error("exception occurred",e);
		}

		if (testTakerRosterCritaria.getSelectedCheckInStatusCodes().length > 0) {
			String checkInStatusCodes[] = testTakerRosterCritaria
					.getSelectedCheckInStatusCodes();
			int c = 0;
			for (int i = 0; i < checkInStatusCodes.length; i++) {
				String code = checkInStatusCodes[i];
				if (code != null) {
					if (c == 0) {
						whereClause.append(" and( ");
						appendEquals(whereClause, "b.evnt_otcm_sts_typ_cde",
								"b.evnt_otcm_sts_typ_cde"+i, code);
					} else {
						whereClause.append(" or ");
						appendEquals(whereClause, "b.evnt_otcm_sts_typ_cde",
								"b.evnt_otcm_sts_typ_cde"+i, code);
					}
					++c;
				}
				if (c > 0 && i == (checkInStatusCodes.length - 1)) {
					whereClause.append(" ) ");
				}

			}

		}

		if (testTakerRosterCritaria.getTestId() != null
				&& (!"".equals(testTakerRosterCritaria.getTestId()))) {
			whereClause.append(" and ");
			appendEquals(whereClause, "t.tst_id",
					testTakerRosterCritaria.getTestId());

		}

		if (testTakerRosterCritaria.getTestFormCode() != null
				&& (!"".equals(testTakerRosterCritaria.getTestFormCode()))) {
			whereClause.append(" and ");
			appendEquals(whereClause, "f.PARNT_FRM_ID",
					testTakerRosterCritaria.getTestFormCode());

		}

		if (testTakerRosterCritaria.getSelectedDeliveryModeType().length > 0) {
			String deliveryModeType[] = testTakerRosterCritaria
					.getSelectedDeliveryModeType();
			int c = 0;
			for (int i = 0; i < deliveryModeType.length; i++) {
				String code = deliveryModeType[i];
				if (code != null) {
					if (c == 0) {
						whereClause.append(" and( ");
						appendEquals(whereClause, "b.dlvy_mde_cde",
								"b.dlvy_mde_cde1", code.trim());
					} else {
						whereClause.append(" or ");
						appendEquals(whereClause, "b.dlvy_mde_cde",
								"b.dlvy_mde_cde2", code.trim());
					}
					++c;
				}
				if (c > 0 && i == (deliveryModeType.length - 1)) {
					whereClause.append(" ) ");
				}

			}

		}

		if (testTakerRosterCritaria.getSelectedAccomodations().length > 0) {
			String accomodations[] = testTakerRosterCritaria
					.getSelectedAccomodations();
			int c = 0;
			for (int i = 0; i < accomodations.length; i++) {
				String code = accomodations[i];
				if (code != null) {
					if (c == 0) {
						whereClause.append(" and( ");
						if ("Yes".equals(code)) {

							whereClause
									.append("b.bkng_id In (select bkng_id from bkng_acmdtn) ");
						} else {
							whereClause
									.append("b.bkng_id Not In (select bkng_id from bkng_acmdtn) ");
						}
					} else {
						whereClause.append(" or ");
						if ("Yes".equals(code)) {
							whereClause
									.append("b.bkng_id In (select bkng_id from bkng_acmdtn) ");
						} else {
							whereClause
									.append("b.bkng_id Not In (select bkng_id from bkng_acmdtn) ");
						}
					}
					++c;
				}
				if (c > 0 && i == (accomodations.length - 1)) {
					whereClause.append(" ) ");
				}

			}

		}

		if (testTakerRosterCritaria.getAdminId() != 0L) {
			whereClause.append(" and ");

			appendEquals(whereClause, "tcd.admin_user_id",
					testTakerRosterCritaria.getAdminId());
		}

		if (testTakerRosterCritaria.getAgencyId() != 0L) {
			whereClause.append(" and ");

			appendEquals(whereClause, "tcp.agncy_id_no",
					testTakerRosterCritaria.getAgencyId());
		}
		return whereClause.toString();
	}
}
