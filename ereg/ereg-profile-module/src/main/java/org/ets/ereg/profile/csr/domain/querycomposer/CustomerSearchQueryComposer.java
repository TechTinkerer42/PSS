package org.ets.ereg.profile.csr.domain.querycomposer;

import org.apache.commons.lang.StringUtils;
import org.ets.ereg.common.querycomposer.AbstractQueryComposer;
import org.ets.ereg.common.querycomposer.ComposedQuery;
import org.ets.ereg.common.vo.AbstractSearchCriteriaVO;
import org.ets.ereg.domain.interfaces.model.common.LinkageType;
import org.ets.ereg.profile.domain.vo.CustomerSearchCriteriaVO;

public class CustomerSearchQueryComposer extends AbstractQueryComposer {

	public CustomerSearchQueryComposer(AbstractSearchCriteriaVO criteria) {
		super(criteria);
	}

	@Override
	public ComposedQuery compose(AbstractSearchCriteriaVO criteria) {
		getComposedQuery().setQuery(composeSelect() + composeFrom() + composeWhere()+ " ) a where rownum <= :Max)  where rnum  > :Min");
		addParameter("Max", criteria.getTotalRowsPerPage());
		addParameter("Min", criteria.getFirstRow());
		return getComposedQuery();
	}

	@Override
	public ComposedQuery composeCount(AbstractSearchCriteriaVO criteria) {
		getComposedQuery().setQuery(composeCountSelect() + composeFrom() + composeWhere() + ")");
		return getComposedQuery();
	}
	
	/**
	 * @return
	 */
	private String composeCountSelect() {
		return "select count( candidateId)   from  (select distinct blCust.CUSTOMER_ID as candidateId,blCust.DEACTIVATED as deactivated,etsCust.SSN_LST_4 as socialSecurity," +
				"etsCust.BRTH_DTE as dateOfBirth,etsCust.MID_NAM as middleIntial,blCust.FIRST_NAME as firstName,blCust.LAST_NAME as lastName,blAdd.ADDRESS_LINE1 as addressLine1," +
				"blAdd.CITY as city,blAdd.STATE_PROV_REGION as state,blAdd.POSTAL_CODE as postalCode   ";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ereg.common.web.AbstractQueryComposer#composeSelect()
	 */
	protected String composeSelect() {
		return "select  candidateId,deactivated,firstName,lastName,socialSecurity,dateOfBirth,middleIntial,addressLine1,city,state,postalCode   from ( select  a.*, rownum rnum from" +
				" (select distinct blCust.CUSTOMER_ID as candidateId,blCust.DEACTIVATED as deactivated ,blCust.FIRST_NAME as firstName,blCust.LAST_NAME as lastName,etsCust.SSN_LST_4 as socialSecurity," +
				"etsCust.BRTH_DTE as dateOfBirth,etsCust.MID_NAM as middleIntial,blAdd.ADDRESS_LINE1 as addressLine1,blAdd.CITY as city, blAdd.STATE_PROV_REGION as state,blAdd.POSTAL_CODE as postalCode   ";
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ereg.common.web.AbstractQueryComposer#composeFrom()
	 */
	protected String composeFrom() {
		CustomerSearchCriteriaVO criteria=(CustomerSearchCriteriaVO)getCriteria();
		StringBuilder fromClause= new StringBuilder();
		fromClause.append("  FROM BLC_CUSTOMER blCust, ETS_CUST etsCust, BLC_CUSTOMER_ADDRESS cadd ");
		fromClause.append(" , BLC_ADDRESS blAdd ");
		if(!StringUtils.isEmpty(criteria.getPhone())){
			 fromClause.append(", BLC_CUSTOMER_PHONE custPh , BLC_PHONE blcPhone ");		
		}
		if(!StringUtils.isEmpty(criteria.getTestTakerId())){
			fromClause.append(", ETS_CUST_LNKG custLink ");	
			
		}
		
		if((criteria.getAdminId()!=null && criteria.getAdminId()!=0)||!StringUtils.isEmpty(criteria.getAppointmentNumber())){
			fromClause.append(", TST_TKR_TST ttt ");
			fromClause.append(", BKNG book ");
			fromClause.append(", TST_CNTR_ADMIN tcAdmin ");		
		}
		
		return  fromClause.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ets.ereg.common.web.AbstractQueryComposer#composeWhere()
	 */
	protected String composeWhere() {
		CustomerSearchCriteriaVO criteria=(CustomerSearchCriteriaVO)getCriteria();
		StringBuffer whereClause = new StringBuffer(" where blCust.CUSTOMER_ID=etsCust.CUSTOMER_ID and etsCust.CUST_TYP_CDE!='OTHER' " +
				" and blCust.CUSTOMER_ID=cadd.CUSTOMER_ID and blAdd.ADDRESS_ID=cadd.ADDRESS_ID ");
		if(!StringUtils.isEmpty(criteria.getPhone())){
			whereClause.append(" and blCust.CUSTOMER_ID=custPh.CUSTOMER_ID and blcPhone.PHONE_ID=custPh.PHONE_ID and ");
			appendEquals(whereClause,"blcPhone.PHONE_NUMBER",criteria.getPhone());
		}
        if(!StringUtils.isEmpty(criteria.getFirstName())){
        	whereClause.append(" and ");
        	appendLike(whereClause,"UPPER(blCust.FIRST_NAME)","blCust.FIRST_NAME",criteria.getFirstName().toUpperCase());
        }
        if(!StringUtils.isEmpty(criteria.getLastName())){
        	whereClause.append(" and ");
        	appendLike(whereClause,"UPPER(blCust.LAST_NAME)","blCust.LAST_NAME",criteria.getLastName().toUpperCase());
        }
        if(!StringUtils.isEmpty(criteria.getMiddleInitial())){
        	whereClause.append(" and ");
        	appendEquals(whereClause,"etsCust.MID_NAM",criteria.getMiddleInitial());
        }
        if(!StringUtils.isEmpty(criteria.getSsn())){
        	whereClause.append(" and ");
        	appendEquals(whereClause,"etsCust.SSN_LST_4",criteria.getSsn());
        }
        if(criteria.getDateOfBirth()!=null){
        	whereClause.append(" and ");
        	appendEquals(whereClause,"etsCust.BRTH_DTE",criteria.getDateOfBirth());
        }
        if(!StringUtils.isEmpty(criteria.getEmail())){
        	whereClause.append(" and ");
        	appendLike(whereClause,"UPPER(blCust.EMAIL_ADDRESS)","blCust.EMAIL_ADDRESS",criteria.getEmail().toUpperCase());
        }
        if(!StringUtils.isEmpty(criteria.getCountry())){
        	whereClause.append(" and ");
        	appendEquals(whereClause,"blAdd.COUNTRY",criteria.getCountry());
        }
        if(!StringUtils.isEmpty(criteria.getCity())){
        	whereClause.append(" and ");
        	appendEquals(whereClause,"UPPER(blAdd.CITY)","blAdd.CITY",criteria.getCity().toUpperCase());
        }
        if(!StringUtils.isEmpty(criteria.getState())){ 
        	whereClause.append(" and ");
        	appendEquals(whereClause,"blAdd.STATE_PROV_REGION",criteria.getState());
        }
        if(!StringUtils.isEmpty(criteria.getPostalCode())){
        	whereClause.append(" and ");
        	appendEquals(whereClause,"blAdd.POSTAL_CODE",criteria.getPostalCode());
        }
        if(!StringUtils.isEmpty(criteria.getTestTakerId())){
        	whereClause.append(" and blCust.CUSTOMER_ID=custLink.CUSTOMER_ID AND  custLink.LNKG_TYP_CDE='"+LinkageType.USER_DISP_ID+"'  and ");
        	appendEquals(whereClause,"custLink.LNKG_KY",criteria.getTestTakerId());
        }
        if((!StringUtils.isEmpty(criteria.getAppointmentNumber())||(criteria.getAdminId()!=null && criteria.getAdminId()!=0)) 
        		&& StringUtils.isEmpty(criteria.getTestTakerId())){
        	
        	whereClause.append(" and blCust.CUSTOMER_ID=ttt.CUSTOMER_ID and book.TST_TKR_TST_ID=ttt.TST_TKR_TST_ID and tcAdmin.TST_CNTR_ID_NO=book.TST_CNTR_ID_NO  ");
        	if(!StringUtils.isEmpty(criteria.getAppointmentNumber())){
        		whereClause.append(" and ");
        		appendEquals(whereClause,"book.ETS_APNTMT_ID",criteria.getAppointmentNumber());
        	}
        	if(criteria.getAdminId()!=null && criteria.getAdminId()!=0 
        			&& StringUtils.isEmpty(criteria.getAppointmentNumber())){
        		whereClause.append(" and ");
        		appendEquals(whereClause,"tcAdmin.ADMIN_USER_ID",criteria.getAdminId());
        	}
        }
        
     
		return whereClause.toString();

	}

}
