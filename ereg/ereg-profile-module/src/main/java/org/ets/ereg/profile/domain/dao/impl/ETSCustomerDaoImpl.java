package org.ets.ereg.profile.domain.dao.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.broadleafcommerce.profile.core.dao.CustomerDaoImpl;
import org.broadleafcommerce.profile.core.domain.CustomerAddressImpl;
import org.broadleafcommerce.profile.core.domain.CustomerPhoneImpl;
import org.ets.ereg.common.business.vo.CustomerVO;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.domain.booking.BookingImpl;
import org.ets.ereg.domain.common.ETSPhoneImpl;
import org.ets.ereg.domain.interfaces.model.common.CustomerLinkage;
import org.ets.ereg.domain.interfaces.model.common.CustomerProgramInterest;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.common.LinkageType;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.profile.CustomerLinkageImpl;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.ets.ereg.domain.profile.admin.TestCenterAdminImpl;
import org.ets.ereg.domain.scheduling.TestTakerTestImpl;
import org.ets.ereg.profile.model.dao.ETSCustomerDao;
import org.ets.ereg.profile.vo.ProfileVO;
import org.hibernate.ejb.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("etsCustomerDao")
public class ETSCustomerDaoImpl extends CustomerDaoImpl implements ETSCustomerDao {
	
	
	private static Logger log = LoggerFactory.getLogger(ETSCustomerDaoImpl.class);
	
	private static final String FIRST_NAME="firstName";
	private static final String LAST_NAME="lastName";
	private static final String MIDDLE_INITIAL="middleInitial";
	private static final String DATE_OF_BIRTH="dateOfBirth";
	private static final String SOCIAL_SECURITY="socialSecurity";
	private static final String EMAIL_ADDRESS="emailAddress";
	private static final String CITY="city";
	private static final String ADDRESS="address";
	private static final String STATE="state";
	private static final String ABBREVIATION="abbreviation";
	private static final String NAME="name";
	private static final String COUNTRY="country";
	private static final String POSTAL_CODE="postalCode";
	private static final String PHONE_NUMBER="phoneNumber";
	private static final String PHONE="phone";
	private static final String ID="id";
	private static final String CUSTOMER="customer";
	private static final String CUSTOMER_TYPE="customerType";
	
	private static final String COUNTRY_CODE_PRIMARY="countrycodeprimary";
	private static final String PHONE_NUMBER_PRIMARY="phoneNumberprimary";
	
	private static final String COUNTRY_CODE_SECONDARY="countrycodesecondary";
	private static final String PHONE_NUMBER_SECONDARY="phoneNumbersecondary";		
	
	private static final String FIRST_NAME1="firstName1";
	private static final String LAST_NAME1="lastName1";
	private static final String DATE_OF_BIRTH1="dateOfBirth1";

	private static final String FIRST_NAME2="firstName2";
	private static final String LAST_NAME2="lastName2";
	private static final String DATE_OF_BIRTH2="dateOfBirth2";
	
	private static final String CUST_ID1="CUST_ID1";
	private static final String CUST_ID2="CUST_ID2";
	private static final String CUST_ID3="CUST_ID3";
	private static final String CUST_ID4="CUST_ID4";
	
	
	private List<Predicate> addCustomerCriteria(ETSCustomer customer,List<Predicate> criteria,
    		Root<ETSCustomerImpl> etsCustomerRoot,CriteriaBuilder cb){
    	
    	if(!StringUtils.isEmpty(customer.getFirstName())){
    		ParameterExpression<String> p =cb.parameter(String.class,FIRST_NAME);
    		Expression<String> str=etsCustomerRoot.get(FIRST_NAME);
    		criteria.add(cb.like(cb.upper(str),p));
		}
		if(!StringUtils.isEmpty(customer.getLastName())){
			ParameterExpression<String> p =cb.parameter(String.class,LAST_NAME);
			Expression<String> str=etsCustomerRoot.get(LAST_NAME);
			criteria.add(cb.like(cb.upper(str), p));
		}
		if(!StringUtils.isEmpty(customer.getMiddleInitial())){
			ParameterExpression<String> p =cb.parameter(String.class,MIDDLE_INITIAL);
			Expression<String> str=etsCustomerRoot.get(MIDDLE_INITIAL);
			criteria.add(cb.like(cb.upper(str), p));
		}
		if(customer.getDateOfBirth()!=null){
			ParameterExpression<Date> d = cb.parameter(Date.class,DATE_OF_BIRTH);
			criteria.add(cb.equal( etsCustomerRoot.get(DATE_OF_BIRTH),d));
		}
		if(!StringUtils.isEmpty(customer.getSocialSecurity())){
			ParameterExpression<String> p =cb.parameter(String.class,SOCIAL_SECURITY);
			criteria.add(cb.equal(etsCustomerRoot.get(SOCIAL_SECURITY), p));
		} 
		if(!StringUtils.isEmpty(customer.getEmailAddress())){
			ParameterExpression<String> p =cb.parameter(String.class,EMAIL_ADDRESS);
			Expression<String> str=etsCustomerRoot.get(EMAIL_ADDRESS);
			criteria.add(cb.equal(cb.upper(str), p));	
		}
    	
    	return criteria;
    }
	
    public ETSCustomer getCustomerByEmail(String email){
        List<ETSCustomer> list=(List<ETSCustomer>) em.createQuery("from ETSCustomerImpl where lower(emailAddress) = lower(?1)  ")
        .setParameter(1, email).getResultList();
        if(!list.isEmpty()){
              return list.get(0);
        }
        return null;
        }

    
    private List<Predicate> addAddressPhoneCriteria(ETSAddress address, ETSPhone phone,List<Predicate> criteria,
    		 Root<CustomerAddressImpl> etsAddressRoot,Root<CustomerPhoneImpl> etsPhoneRoot,CriteriaBuilder cb){
		if(!StringUtils.isEmpty(address.getCity())){
			ParameterExpression<String> p =cb.parameter(String.class,CITY);
			criteria.add(cb.equal(etsAddressRoot.get(ADDRESS).get(CITY), p));	
		}
		if(address.getState()!=null  && !StringUtils.isEmpty(address.getState().getAbbreviation())){
			ParameterExpression<String> p =cb.parameter(String.class,STATE);
			criteria.add(cb.equal(etsAddressRoot.get(ADDRESS).get(STATE).get(ABBREVIATION), p));	
		}
		if(address.getCountry()!=null && !StringUtils.isEmpty(address.getCountry().getAbbreviation())){
			ParameterExpression<String> p =cb.parameter(String.class,COUNTRY);
			criteria.add(cb.equal(etsAddressRoot.get(ADDRESS).get(COUNTRY).get(ABBREVIATION), p));
		}
		if(address.getPostalCode()!=null && !"".equals(address.getPostalCode())){
			ParameterExpression<String> p =cb.parameter(String.class,POSTAL_CODE);
			criteria.add(cb.equal(etsAddressRoot.get(ADDRESS).get(POSTAL_CODE), p));	
		}
		
		if(!StringUtils.isEmpty(phone.getPhoneNumber())){
			ParameterExpression<String> p =cb.parameter(String.class,PHONE_NUMBER);		
			criteria.add(cb.equal(etsPhoneRoot.get(PHONE).get(PHONE_NUMBER), p));
		}
    	return criteria;
    }
 
    public TypedQuery setParameters(TypedQuery etsCustomerTQ,ETSCustomer customer,
    		ETSAddress address, ETSPhone phone,String linkDispSeq){
		if(!StringUtils.isEmpty(customer.getFirstName())){
			etsCustomerTQ.setParameter(FIRST_NAME, customer.getFirstName().toUpperCase()+"%");
		}
		if(!StringUtils.isEmpty(customer.getLastName())){
			etsCustomerTQ.setParameter(LAST_NAME, customer.getLastName().toUpperCase()+"%");
		}
		if(!StringUtils.isEmpty(customer.getMiddleInitial())){
			etsCustomerTQ.setParameter(MIDDLE_INITIAL, customer.getMiddleInitial().toUpperCase()+"%");
		}
		if(customer.getDateOfBirth()!=null){
			etsCustomerTQ.setParameter(DATE_OF_BIRTH, customer.getDateOfBirth());
		}
		if(!StringUtils.isEmpty(customer.getSocialSecurity())){
			etsCustomerTQ.setParameter(SOCIAL_SECURITY, customer.getSocialSecurity());
		} 
		if(!StringUtils.isEmpty(customer.getEmailAddress())){
			etsCustomerTQ.setParameter(EMAIL_ADDRESS, customer.getEmailAddress().toUpperCase());
		} 
		if(!StringUtils.isEmpty(address.getCity())){
			etsCustomerTQ.setParameter(CITY, address.getCity());
		}
		if(address.getState()!=null && !StringUtils.isEmpty(address.getState().getAbbreviation())){
			etsCustomerTQ.setParameter(STATE, address.getState().getAbbreviation());	
		}
		if(address.getCountry()!=null && !StringUtils.isEmpty(address.getCountry().getAbbreviation())){
			etsCustomerTQ.setParameter(COUNTRY, address.getCountry().getAbbreviation());	
		}
		if(!StringUtils.isEmpty(address.getPostalCode())){
			etsCustomerTQ.setParameter(POSTAL_CODE, address.getPostalCode());
		}
		if(!StringUtils.isEmpty(phone.getPhoneNumber())){
			etsCustomerTQ.setParameter(PHONE_NUMBER, phone.getPhoneNumber());
		}
		if(!StringUtils.isEmpty(linkDispSeq)){
			etsCustomerTQ.setParameter("linkageKey", linkDispSeq.toUpperCase());
		}
		
		return etsCustomerTQ;
    }
    
    
    private CriteriaQuery<Object[]> getCriteriaQuery(ETSCustomer customer,ETSAddress address, ETSPhone phone, String linkDispSeq,Long adminId,Long apptNumber
    		){
    	CriteriaBuilder cb=em.getCriteriaBuilder();
    	CriteriaQuery<Object[]> countCQ=cb.createQuery(Object[].class);
		 Root<CustomerPhoneImpl> etsPhoneRoot=null;
		 Root<CustomerLinkageImpl> custLinkageRoot=null;
		 List<Predicate> criteria = new ArrayList<Predicate>();
		 Root<ETSCustomerImpl> etsCustomerRoot=countCQ.from(ETSCustomerImpl.class);
		 Root<CustomerAddressImpl> etsAddressRoot=countCQ.from(CustomerAddressImpl.class);
		 if(!StringUtils.isEmpty(phone.getPhoneNumber())){
			 etsPhoneRoot=countCQ.from(CustomerPhoneImpl.class);	
		 }
		 if(!StringUtils.isEmpty(linkDispSeq)){
			 custLinkageRoot=countCQ.from(CustomerLinkageImpl.class);
			 
		 }
		 
		 if(adminId!=0 || apptNumber!=null){
			 Root<TestCenterAdminImpl> testCenterAdminRoot=null;
			 Root<TestTakerTestImpl> testTakerTestRoot=null;
			 Root<BookingImpl> bookingRoot=null;
			 testCenterAdminRoot=countCQ.from(TestCenterAdminImpl.class);
			 testTakerTestRoot=countCQ.from(TestTakerTestImpl.class);
			 bookingRoot=countCQ.from(BookingImpl.class);
			 criteria.add(cb.equal(testCenterAdminRoot.get("testCenterAdminUserId").get("testCenterId"), bookingRoot.get("testCenter").get("id")));
			 criteria.add(cb.equal(etsCustomerRoot.get(ID), testTakerTestRoot.get(CUSTOMER).get(ID)));
			 criteria.add(cb.equal(testTakerTestRoot.get("TTTid"), bookingRoot.get("testTakerTest").get("TTTid")));
			 
			 if(apptNumber!=null && apptNumber!=0L){
				 ParameterExpression<String> param = cb.parameter(String.class,
							"apptNumber");
				 criteria.add(cb.equal(bookingRoot.get("etsApptID"), param));
			 }
			 
			 if(adminId!=0){
				ParameterExpression<Long> param = cb.parameter(Long.class,
						"etsAdminUserId");
				criteria.add(cb.equal(
						testCenterAdminRoot.get("testCenterAdminUserId").get(
								"etsAdminUserId"), param));
				}
		    
	     }
		 
		 
		
		 criteria.add(cb.equal(etsCustomerRoot.get(ID), etsAddressRoot.get(CUSTOMER).get(ID)));
		 criteria.add(cb.notEqual(etsCustomerRoot.get(CUSTOMER_TYPE).get("code"), "OTHER"));
		 if(!StringUtils.isEmpty(phone.getPhoneNumber())){
			 criteria.add(cb.equal(etsCustomerRoot.get(ID), etsPhoneRoot.get(CUSTOMER).get(ID)));
			 criteria.add(cb.equal(etsAddressRoot.get(CUSTOMER).get(ID), etsPhoneRoot.get(CUSTOMER).get(ID)));
		 }
		 if(!StringUtils.isEmpty(linkDispSeq)){
			criteria.add(cb.equal(etsCustomerRoot.get(ID), custLinkageRoot.get(CUSTOMER).get(ID)));
		    ParameterExpression<String> p =cb.parameter(String.class,"linkageKey");
			criteria.add(cb.equal(custLinkageRoot.get("linkageKey"), p));
		 }
		 
		 criteria=addCustomerCriteria(customer,criteria,etsCustomerRoot,cb);
		 criteria=addAddressPhoneCriteria(address,phone,criteria,etsAddressRoot,etsPhoneRoot,cb);
		if(criteria.size()==2){
			log.info("Please enter a criteria");
		}
		if(criteria.size()==1){
			countCQ.where(criteria.get(0));
			
		}else{
			countCQ.where(cb.and(criteria.toArray(new Predicate[0])));
			
		}
    	return countCQ;
    }
    
    public Long getCountForCustomersSearch(ETSCustomer customer,ETSAddress address
    		, ETSPhone phone, String linkDispSeq,Long adminId,Long apptNumber){
    	CriteriaBuilder cb=em.getCriteriaBuilder();
    	CriteriaQuery<Object[]> countCQ=getCriteriaQuery(customer,address, phone, linkDispSeq,adminId,apptNumber);
    	
    	Set<Root<?>> set=countCQ.getRoots();

    	for(Root val:set){
    		if("ETSCustomerImpl".equals(val.getJavaType().getSimpleName())){	
    			countCQ.multiselect(cb.countDistinct(val));
    		}
    	}
    
    	
		TypedQuery<Object[]> countTQ=em.createQuery(countCQ);
	    if(adminId!=0){
	    	countTQ.setParameter("etsAdminUserId", adminId);
    	}
		 if(apptNumber!=null && apptNumber!=0L){
			 countTQ.setParameter("apptNumber", apptNumber);
		 }
	    
		countTQ=setParameters(countTQ,customer,address,phone,linkDispSeq);
		Object obj=countTQ.getSingleResult();
    	return (Long)obj;
    }
    
    private CustomerVO populateCustomerResult(Object[] t,Long adminId){
    	CustomerVO custVO = new CustomerVO();
		Date dateOfBirth=null;
		String lastName =(String)t[0];
		String firstName = (String)t[1];
		String middleInitial = (String)t[2];
		if(t[3]!=null){
			dateOfBirth=(Date)t[3];
		}
		String socialSecurity =(String)t[4];
		Long id = (Long)t[5];
		String address1=(String)t[6];
		String city= (String)t[7];
		String state= (String)t[8];
		String postalCode= (String)t[9];
		Boolean deactivated=null;
		if(t[10]!=null){
			deactivated=(Boolean)t[10];
		}
		lastName=(lastName==null)?"":lastName;
		firstName=(firstName==null)?"":firstName;
		middleInitial=(middleInitial==null)?"":middleInitial;
		custVO.setName(lastName+","+firstName+" "+middleInitial);
		custVO.setCandidateId(id);
		custVO.setDateOfBirth(dateOfBirth);
		if(adminId==0L)
		{
			custVO.setSocialSecurity(socialSecurity==null?"":socialSecurity);
		}
		String []address=new String[4];
		address[0]=address1==null?"":address1;
		address[1]=city==null?"":city;
		address[2]=state==null?"":state;
		address[3]=postalCode==null?"":postalCode;
		custVO.setAddress(address);
		if(deactivated!=null && deactivated){
			custVO.setAccountStatus("In Active");
		}else if(deactivated!=null){
			custVO.setAccountStatus("Active");
		}
		return custVO;
    	
    }

	@Override
	public List<CustomerVO> searchCustomerByCriteria(ETSCustomer customer,ETSAddress address, ETSPhone phone,SearchParameters searchParams,String linkDispSeq,Long adminId, Long apptNumber) {
		String sortBy=searchParams.getSortBy();
		String des=searchParams.getDes();
		int numofRows=searchParams.getNumofRows();
		int pageSize=searchParams.getPageSize();
		CriteriaBuilder cb=em.getCriteriaBuilder();
		CriteriaQuery<Object[]> etsCustomerCQ=getCriteriaQuery(customer,address, phone,linkDispSeq,adminId,apptNumber);
		Set<Root<?>> set=etsCustomerCQ.getRoots();
		Root<ETSCustomerImpl> etsCustomerRoot=null;
		Root<CustomerAddressImpl> etsAddressRoot=null;
	    for(Root val:set){
	    	if("ETSCustomerImpl".equals(val.getJavaType().getSimpleName())){
	    			etsCustomerRoot=val;
	    	}else if("CustomerAddressImpl".equals(val.getJavaType().getSimpleName())){
	    			etsAddressRoot=val;
	    	}
	    }
	    	etsCustomerCQ.multiselect(etsCustomerRoot.get(LAST_NAME).alias(LAST_NAME),etsCustomerRoot.get(FIRST_NAME).alias(FIRST_NAME),etsCustomerRoot.get(MIDDLE_INITIAL).alias(MIDDLE_INITIAL),
				 etsCustomerRoot.get(DATE_OF_BIRTH).alias(DATE_OF_BIRTH), etsCustomerRoot.get(SOCIAL_SECURITY).alias(SOCIAL_SECURITY),etsCustomerRoot.get(ID).alias("candidateId"),
				 etsAddressRoot.get(ADDRESS).get("addressLine1").alias(ADDRESS),etsAddressRoot.get(ADDRESS).get(CITY).alias(CITY),etsAddressRoot.get(ADDRESS).get(STATE).get(NAME).alias(STATE),
				 etsAddressRoot.get(ADDRESS).get(POSTAL_CODE).alias(POSTAL_CODE),etsCustomerRoot.get("deactivated").alias("deactivated"));
	    	etsCustomerCQ.distinct(true);
	
		if(StringUtils.isEmpty(sortBy) && StringUtils.isEmpty(des)){
			etsCustomerCQ.orderBy(cb.asc(etsCustomerRoot.get(LAST_NAME))
					,cb.asc(etsCustomerRoot.get(FIRST_NAME)),cb.asc(etsCustomerRoot.get(MIDDLE_INITIAL)));
		}else if(!StringUtils.isEmpty(sortBy) &&  StringUtils.isEmpty(des)){
			etsCustomerCQ.orderBy(cb.asc(etsCustomerRoot.get(sortBy)));
		}else if(!StringUtils.isEmpty(sortBy) &&  !StringUtils.isEmpty(des)){
			etsCustomerCQ.orderBy(cb.desc(etsCustomerRoot.get(sortBy)));
		}
		TypedQuery<Object[]> etsCustomerTQ=em.createQuery(etsCustomerCQ);
		etsCustomerTQ=setParameters(etsCustomerTQ,customer,address,phone,linkDispSeq);
		etsCustomerTQ.setFirstResult(numofRows);
		etsCustomerTQ.setMaxResults(pageSize);
	    if(adminId!=0){
	    	etsCustomerTQ.setParameter("etsAdminUserId", adminId);
    	}
		 if(apptNumber!=null && apptNumber!=0L){
			 etsCustomerTQ.setParameter("apptNumber", apptNumber.toString());
		 }
		
	
	    List<Object[]> custTuples=etsCustomerTQ.getResultList();
		List<CustomerVO> customers = new ArrayList<CustomerVO>();
		for (Object[] t : custTuples) {
			CustomerVO custVO=populateCustomerResult(t,adminId);
			customers.add(custVO);
		}
		log.debug("Customers Size: {}",custTuples.size());
		return customers;
	}
	@Override
	public DupCheckResponseObject checkDuplicateProfile(ProfileVO profile) {

		String strFirstName = profile.getCustomer().getFirstName().toUpperCase().trim();
		String strLastName = profile.getCustomer().getLastName().toUpperCase().trim();
		String strEmail = profile.getCustomer().getEmailAddress().toUpperCase().trim();
		Date strDOB = (Date) profile.getCustomer().getDateOfBirth();
		String strZip = profile.getAddress().getPostalCode().toUpperCase().trim();
		String strPrimaryCntryCode = (profile.getPrimaryPhone().getCountry().getAbbreviation()).toUpperCase().trim();
		String strPrimaryPhone = (profile.getPrimaryPhone().getPhoneNumber()).toUpperCase().trim();
		String strSecondaryCntryCode = (profile.getAlternatePhone().getCountry().getAbbreviation()).toUpperCase().trim();
		String strSecondaryPhone = (profile.getAlternatePhone().getPhoneNumber()).toUpperCase().trim();
		String strSSN = profile.getCustomer().getSocialSecurity().toUpperCase().trim();

		StringBuffer sbTables = new StringBuffer();
		StringBuffer sbJoins = new StringBuffer();
		StringBuffer sbSelectQuery = new StringBuffer();
		StringBuffer sbWhereQuery = new StringBuffer();
		StringBuffer sbMainQuery = new StringBuffer();

		sbTables.append(" FROM blc_customer blcc, blc_address blca, ETS_CUST etsc, BLC_CUSTOMER_ADDRESS blcca");
		sbJoins.append(" WHERE blcca.address_id = blca.address_id AND blcc.customer_id = blcca.customer_id AND blcc.customer_id =ETSC.CUSTOMER_ID");

		// checking for the duplicate name, dob zip or ssn
		sbSelectQuery.append(" select 'Duplicate Name or Date of Birth or Zip code'");		
		
		sbWhereQuery.append(" and upper(blcc.first_name)= :strFirstName and upper(blcc.last_name)= :strLastName " +
				"and trunc(etsc.BRTH_DTE) = :strDOB and blca.postal_code = :strZip");
		
		sbMainQuery.append(sbSelectQuery.toString()).append(sbTables.toString()).append(sbJoins.toString()).append(sbWhereQuery.toString());

		// check if SSN is there, if not check for the phone numbers
		if(!strSSN.equals(""))
		{
			sbSelectQuery=new StringBuffer();
			sbWhereQuery = new StringBuffer();
			sbTables = new StringBuffer();
			sbJoins = new StringBuffer();			
			
			sbTables.append(" FROM blc_customer blcc, blc_address blca, ETS_CUST etsc, BLC_CUSTOMER_ADDRESS blcca");
			sbJoins.append(" WHERE blcca.address_id = blca.address_id AND blcc.customer_id = blcca.customer_id AND blcc.customer_id =ETSC.CUSTOMER_ID");
			
			sbSelectQuery.append(" select 'Duplicate Name or Date of Birth or SSN'");	
			
			sbWhereQuery.append(" and upper(blcc.first_name)= :strFirstName and upper(blcc.last_name)= :strLastName " +
					"and trunc(etsc.BRTH_DTE) = :strDOB and etsc.SSN_LST_4=:strSSN ");

			sbMainQuery.append(" Union");
			sbMainQuery.append(sbSelectQuery.toString()).append(sbTables.toString()).append(sbJoins.toString()).append(sbWhereQuery.toString());
		}
		else
		{
			sbSelectQuery=new StringBuffer();
			sbWhereQuery = new StringBuffer();
			sbTables = new StringBuffer();
			sbJoins = new StringBuffer();				
		
			sbTables.append(" FROM blc_customer blcc, blc_address blca, blc_phone blcp, ETS_CUST etsc, BLC_CUSTOMER_ADDRESS blcca, BLC_CUSTOMER_PHONE blccp,ets_phone etsp");
			sbJoins.append(" WHERE blcca.address_id = blca.address_id AND blcc.customer_id = blcca.customer_id AND blcp.phone_id =blccp.phone_id AND blccp.CUSTOMER_ID = blcc.customer_id AND blcc.customer_id =ETSC.CUSTOMER_ID AND etsp.phone_id = blccp.phone_id");
			
			sbSelectQuery.append(" select 'Duplicate Name or Date of Birth or Phone'");	
			
			sbWhereQuery.append(" and upper(blcc.first_name)= :strFirstName and upper(blcc.last_name)=:strLastName " +
					"and trunc(etsc.BRTH_DTE) = :strDOB and (blcp.PHONE_NUMBER = :strPrimaryPhone or blcp.PHONE_NUMBER = :strSecondaryPhone ) and (etsp.CNTRY_CDE = :strPrimaryCntryCode or etsp.CNTRY_CDE = :strSecondaryCntryCode )");
			
			sbMainQuery.append(" Union");
			sbMainQuery.append(sbSelectQuery.toString()).append(sbTables.toString()).append(sbJoins.toString()).append(sbWhereQuery.toString());
		}
		// check if the email is there and if its matching to any email address in the customer table
		if (!strEmail.equals(""))
		{
			sbSelectQuery=new StringBuffer();
			sbWhereQuery = new StringBuffer();
		
			sbMainQuery.append(" Union");			
			sbSelectQuery.append(" select 'Duplicate Email' from blc_customer blcc ");
			sbWhereQuery.append(" where upper(blcc.email_address) = :strEmail ");
			// creation of the complete query
			sbMainQuery.append(sbSelectQuery.toString()).append(sbWhereQuery.toString());
		}
		log.debug(sbMainQuery.toString());
		
		Query query = em.createNativeQuery(sbMainQuery.toString());
		query.setParameter("strFirstName", strFirstName);
		query.setParameter("strLastName", strLastName);
		query.setParameter("strZip", strZip);
		query.setParameter("strDOB", strDOB);
		if(!strSSN.equals(""))
		{
			query.setParameter("strSSN", strSSN);
		}
		else
		{
			query.setParameter("strPrimaryPhone", strPrimaryPhone);
			query.setParameter("strSecondaryPhone", strSecondaryPhone);		
			query.setParameter("strPrimaryCntryCode", strPrimaryCntryCode);
			query.setParameter("strSecondaryCntryCode", strSecondaryCntryCode);
		}
		
		if (!strEmail.equals("")){
			query.setParameter("strEmail", strEmail);
		}
		
		
		List<String> results = query.getResultList();
		
		DupCheckResponseObject dcro = new DupCheckResponseObject(results);
		if(results.isEmpty())
		{
			dcro.setDuplicate(false);
		}
		else
		{
			dcro.setDuplicate(true);
		}
		
		return dcro;
	}

	@Override
	public CustomerProgramInterest getCustomerProgramInterest(
			ETSCustomer customer,
			ProgramType programType) {
		CustomerProgramInterest cpi;
		TypedQuery<CustomerProgramInterest> query = em.createNamedQuery("Customer.findCustomerProgramInterest", CustomerProgramInterest.class);
		query.setParameter("customerId", customer.getId());
		query.setParameter("programCode", programType.getCode());
		try{
			List<CustomerProgramInterest> result =  query.getResultList();
			if(result.isEmpty()){
				cpi = null;
			}
			else{
				cpi = result.get(0);
			}
		} catch(NoResultException nre) {
			log.info("No result found");
			cpi = null;
		}
		return cpi;
	}
	
	public List<String> getCustomerProgramInterests( Long customerId ) {
		
		List<String> ret = null;
		TypedQuery<String> query = em.createNamedQuery(CustomerProgramInterest.CUSTOMER_PROGRAM_INTERESTS, String.class);
		query.setParameter("customerId", customerId);

		try{
			
			ret =  query.getResultList();
			
		} catch(NoResultException nre) {
			log.info("No result found");
			ret = new ArrayList<String>();
		}
		return ret;
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public CustomerProgramInterest addCustomerProgramInterest(ETSCustomer customer,
			ProgramType programType) {
		CustomerProgramInterest cpi;
		cpi = getCustomerProgramInterest(customer, programType);
		if(null == cpi){
			cpi = (CustomerProgramInterest) entityConfiguration.createEntityInstance(CustomerProgramInterest.class.getName());
			cpi.getId().setCustomerId(customer.getId());
			cpi.getId().setProgramCode(programType.getCode());
			cpi.setProgramType(programType);
			cpi.setCustomer(customer);
			em.persist(cpi);
		}
		return cpi;
	}

	@Override
	public CustomerLinkage getCustomerLinkage(Long custid,String linkageType) {
		CustomerLinkage cl;
		TypedQuery<CustomerLinkage> query = em.createNamedQuery("Customer.findCustomerLinkage", CustomerLinkage.class);
		query.setParameter("customerId", custid);
		query.setParameter("linkTypeCode",linkageType);
		try{
			List<CustomerLinkage> result =  query.getResultList();
			if(result.isEmpty()){
				cl = null;
			}
			else{
				cl = result.get(0);
			}
		} catch(NoResultException nre) {
			log.info("No result found");
			cl = null;
		}
		return cl;
	}

	@Override
	public CustomerLinkage addCustomerLinkage(ETSCustomer customer,
			LinkageType linkageType, String linkageKey) {
		CustomerLinkage cl;
		cl = getCustomerLinkage(customer.getId(), linkageType.getCode());
		if(null == cl){
			cl = (CustomerLinkage) entityConfiguration.createEntityInstance(CustomerLinkage.class.getName());
			cl.getId().setCustomerId(customer.getId());
			cl.getId().setLinkTypeCode(linkageType.getCode());
			cl.setLinkageType(linkageType);
			cl.setLinkageKey(linkageKey);
			cl.setCustomer(customer);
			cl = em.merge(cl);
		}
		return cl;
	}
	
	@Override
	public Boolean hasDuplicateProfiles(ProfileVO profile,boolean currentloggedCust)
	{
		return (getDuplicateProfiles(profile, currentloggedCust).size() == 0)? false:true;
	}

	@Override
	public List<CustomerVO> getDuplicateProfiles(ProfileVO profile, boolean currentloggedCust)
	{
		try{
		CriteriaBuilder cb=em.getCriteriaBuilder();
		
		ETSCustomer customer = profile.getCustomer();
		ETSAddress address = profile.getAddress(); 
		ETSPhone primaryPhone = profile.getPrimaryPhone();
		ETSPhone alternativePhone = profile.getAlternatePhone();
		
		String strSSN = checkNull(profile.getCustomer().getSocialSecurity()).toUpperCase().trim();

		// criteria builder code
		CriteriaQuery<Object[]> countCQ=cb.createQuery(Object[].class);
		
		Root<CustomerPhoneImpl> etsPhoneRoot=null;
		Root<ETSCustomerImpl> etsCustomerRoot=countCQ.from(ETSCustomerImpl.class);
		Root<CustomerAddressImpl> etsAddressRoot=countCQ.from(CustomerAddressImpl.class);
		
		Root<ETSPhoneImpl> etsPhoneConCodeRoot=countCQ.from(ETSPhoneImpl.class);
		
		if(!StringUtils.isEmpty(primaryPhone.getPhoneNumber()) || primaryPhone.getPhoneNumber()!= null 
				|| !StringUtils.isEmpty(alternativePhone.getPhoneNumber()) || alternativePhone.getPhoneNumber()!= null){
			etsPhoneRoot=countCQ.from(CustomerPhoneImpl.class);
			etsPhoneConCodeRoot=countCQ.from(ETSPhoneImpl.class);
		}
		
		List<Predicate> criteria = new ArrayList<Predicate>();
		//List<Predicate> NameZipCriteria = new ArrayList<Predicate>();
		//List<Predicate> EmailCriteria = new ArrayList<Predicate>();
		//List<Predicate> NamePhonePrimarySSNCriteria = new ArrayList<Predicate>();
		//List<Predicate> NamePhoneSecSSNCriteria = new ArrayList<Predicate>();
		
		criteria.add(cb.equal(etsCustomerRoot.get("id"), etsAddressRoot.get("customer").get("id")));
		
		if(!StringUtils.isEmpty(primaryPhone.getPhoneNumber()) || !StringUtils.isEmpty(alternativePhone.getPhoneNumber()))
		{
			 criteria.add(cb.equal(etsCustomerRoot.get("id"), etsPhoneRoot.get("customer").get("id")));
			 criteria.add(cb.equal(etsAddressRoot.get("customer").get("id"), etsPhoneRoot.get("customer").get("id")));
			 
			 criteria.add(cb.equal(etsPhoneRoot.get("phone").get("id"), etsPhoneConCodeRoot.get("id")));
		}
		
		List<Predicate> NameZipCriteria = addNameZipCriteria(customer,criteria,etsAddressRoot,etsCustomerRoot,cb,address, currentloggedCust);
			
		List<Predicate> EmailCriteria = addEmailCriteria(customer, criteria, etsCustomerRoot, cb, currentloggedCust);
		
		if (!strSSN.equals(""))
		{
			List<Predicate> NamePhonePrimarySSNCriteria = addNameSSNCriteria(customer,etsCustomerRoot,criteria,cb, currentloggedCust);
			countCQ.where(cb.or(cb.and(NameZipCriteria.toArray(new Predicate[0])),cb.and(EmailCriteria.toArray(new Predicate[0])),cb.and(NamePhonePrimarySSNCriteria.toArray(new Predicate[0]))));
		}
		else
		{
			List<Predicate> NamePhonePrimarySSNCriteria = addNamePhonePrimaryCriteria(customer,etsCustomerRoot,etsPhoneRoot, primaryPhone, criteria,cb, etsPhoneConCodeRoot, currentloggedCust);
			List<Predicate> NamePhoneSecSSNCriteria = addNamePhoneSecCriteria(customer,etsCustomerRoot,etsPhoneRoot, alternativePhone, criteria,cb, etsPhoneConCodeRoot, currentloggedCust);
			countCQ.where(cb.or(cb.and(NameZipCriteria.toArray(new Predicate[0])),cb.and(EmailCriteria.toArray(new Predicate[0])),cb.and(NamePhonePrimarySSNCriteria.toArray(new Predicate[0])),cb.and(NamePhoneSecSSNCriteria.toArray(new Predicate[0]))));
		}	
		
		countCQ.multiselect(etsCustomerRoot.get("lastName").alias("lastName"),etsCustomerRoot.get("firstName").alias("firstName"),etsCustomerRoot.get("middleInitial").alias("middleInitial"),
			 etsCustomerRoot.get("dateOfBirth").alias("dateOfBirth"), etsCustomerRoot.get("socialSecurity").alias("socialSecurity"),etsCustomerRoot.get("id").alias("candidateId"),
			 etsAddressRoot.get("address").get("addressLine1").alias("address"),etsAddressRoot.get("address").get("city").alias("city"),etsAddressRoot.get("address").get("state").get("name").alias("state"),
			 etsAddressRoot.get("address").get("postalCode").alias("postalCode"),etsCustomerRoot.get("deactivated").alias("deactivated"));
		
		countCQ.distinct(true);

		TypedQuery<Object[]> etsCustomerTQ=em.createQuery(countCQ);
		etsCustomerTQ=setDupParameters(etsCustomerTQ,customer,address,primaryPhone, currentloggedCust);
		
		List<Object[]> custTuples=etsCustomerTQ.getResultList();
		
		List<CustomerVO> customers = new ArrayList<CustomerVO>();
		for (Object[] t : custTuples) {
			CustomerVO custVO=populateCustomerResult(t,0L);
			customers.add(custVO);
		}
		//logger.debug("Customers Size: {}",custTuples.size());
		return customers;
		}
		catch(Exception e)
		{
			log.error("Exception in getDuplicateProfiles() - {}",e);
			return null;
		}
	}	
	
    private List<Predicate> addNameZipCriteria(ETSCustomer customer,List<Predicate> criteria,Root<CustomerAddressImpl> etsAddressRoot,
    		Root<ETSCustomerImpl> etsCustomerRoot,CriteriaBuilder cb,ETSAddress address, boolean currentloggedCust)
   	{
    	List<Predicate> nameCriteria = new ArrayList<Predicate>();
    	
    	nameCriteria.addAll(criteria);
    	
		ParameterExpression<String> pFName =cb.parameter(String.class,FIRST_NAME);
		Expression<String> strFName=etsCustomerRoot.get(FIRST_NAME);
		nameCriteria.add(cb.equal(cb.upper(strFName),pFName));    		

		ParameterExpression<String> pLName =cb.parameter(String.class,LAST_NAME);
		Expression<String> strLName=etsCustomerRoot.get(LAST_NAME);
		nameCriteria.add(cb.equal(cb.upper(strLName), pLName));
		
		ParameterExpression<Date> d = cb.parameter(Date.class,DATE_OF_BIRTH);
		nameCriteria.add(cb.equal( etsCustomerRoot.get(DATE_OF_BIRTH),d));
		
		ParameterExpression<String> p =cb.parameter(String.class,POSTAL_CODE);
		nameCriteria.add(cb.equal(etsAddressRoot.get(ADDRESS).get(POSTAL_CODE), p));	
		
		if (currentloggedCust == true)
		{
			ParameterExpression<Long> pCustID =cb.parameter(Long.class,CUST_ID1);
			nameCriteria.add(cb.notEqual(etsCustomerRoot.get(ID), pCustID));
		}

		return nameCriteria;
    }
    
    private List<Predicate> addEmailCriteria(ETSCustomer customer,List<Predicate> criteria, Root<ETSCustomerImpl> etsCustomerRoot,CriteriaBuilder cb, boolean currentloggedCust)
   	{
    	List<Predicate> emailCriteria = new ArrayList<Predicate>();
    	
    	emailCriteria.addAll(criteria);
    	
		ParameterExpression<String> p =cb.parameter(String.class,EMAIL_ADDRESS);
		Expression<String> str=etsCustomerRoot.get(EMAIL_ADDRESS);
		emailCriteria.add(cb.equal(cb.upper(str), p));	
		
		if (currentloggedCust == true)
		{
			ParameterExpression<Long> pCustID =cb.parameter(Long.class,CUST_ID2);
			emailCriteria.add(cb.notEqual(etsCustomerRoot.get(ID), pCustID));
		}
	
    	return emailCriteria;
    }
    
    private List<Predicate> addNamePhonePrimaryCriteria(ETSCustomer customer,Root<ETSCustomerImpl> etsCustomerRoot, Root<CustomerPhoneImpl> etsPhoneRoot,ETSPhone primaryPhone,
    		List<Predicate> criteria, CriteriaBuilder cb, Root<ETSPhoneImpl> etsPhoneConCodeRoot, boolean currentloggedCust)
   	{
    	List<Predicate> phonePrimaryCriteria = new ArrayList<Predicate>(criteria); 

		ParameterExpression<String> pFName =cb.parameter(String.class,FIRST_NAME1);
		Expression<String> strFName=etsCustomerRoot.get(FIRST_NAME);
		phonePrimaryCriteria.add(cb.equal(cb.upper(strFName),pFName));
		
		ParameterExpression<String> pLName =cb.parameter(String.class,LAST_NAME1);
		Expression<String> strLName=etsCustomerRoot.get(LAST_NAME);
		phonePrimaryCriteria.add(cb.equal(cb.upper(strLName), pLName));
		
		ParameterExpression<Date> d = cb.parameter(Date.class,DATE_OF_BIRTH1);
		phonePrimaryCriteria.add(cb.equal( etsCustomerRoot.get(DATE_OF_BIRTH),d));
		
		ParameterExpression<String> pPPhone =cb.parameter(String.class,PHONE_NUMBER_PRIMARY);
		phonePrimaryCriteria.add(cb.equal(etsPhoneRoot.get(PHONE).get(PHONE_NUMBER), pPPhone));
		
		ParameterExpression<String> pPConCode =cb.parameter(String.class,COUNTRY_CODE_PRIMARY);
		phonePrimaryCriteria.add(cb.equal(etsPhoneConCodeRoot.get(COUNTRY).get(ABBREVIATION), pPConCode));
		
		if (currentloggedCust == true)
		{
			ParameterExpression<Long> pCustID =cb.parameter(Long.class,CUST_ID3);
			phonePrimaryCriteria.add(cb.notEqual(etsCustomerRoot.get(ID), pCustID));
		}
		
		return phonePrimaryCriteria;
    }
    
    private List<Predicate> addNamePhoneSecCriteria(ETSCustomer customer,Root<ETSCustomerImpl> etsCustomerRoot, Root<CustomerPhoneImpl> etsPhoneRoot ,ETSPhone secondaryPhone,
    		List<Predicate> criteria, CriteriaBuilder cb, Root<ETSPhoneImpl> etsPhoneConCodeRoot, boolean currentloggedCust)
   	{
    	List<Predicate> phoneSecCriteria = new ArrayList<Predicate>(criteria); 

		ParameterExpression<String> pFName =cb.parameter(String.class,FIRST_NAME2);
		Expression<String> strFName=etsCustomerRoot.get(FIRST_NAME);
		phoneSecCriteria.add(cb.equal(cb.upper(strFName),pFName));
		
		ParameterExpression<String> pLName =cb.parameter(String.class,LAST_NAME2);
		Expression<String> strLName=etsCustomerRoot.get(LAST_NAME);
		phoneSecCriteria.add(cb.equal(cb.upper(strLName), pLName));
		
		ParameterExpression<Date> d = cb.parameter(Date.class,DATE_OF_BIRTH2);
		phoneSecCriteria.add(cb.equal( etsCustomerRoot.get(DATE_OF_BIRTH),d));
		
		ParameterExpression<String> pSPhone =cb.parameter(String.class,PHONE_NUMBER_SECONDARY);
		phoneSecCriteria.add(cb.equal(etsPhoneRoot.get(PHONE).get(PHONE_NUMBER), pSPhone));
		
		ParameterExpression<String> pSConCode =cb.parameter(String.class,COUNTRY_CODE_SECONDARY);
		phoneSecCriteria.add(cb.equal(etsPhoneConCodeRoot.get(COUNTRY).get(ABBREVIATION), pSConCode));
		
		if (currentloggedCust == true)
		{
			ParameterExpression<Long> pCustID =cb.parameter(Long.class,CUST_ID4);
			phoneSecCriteria.add(cb.notEqual(etsCustomerRoot.get(ID), pCustID));
		}
		
		return phoneSecCriteria;
    }
    private List<Predicate> addNameSSNCriteria(ETSCustomer customer,Root<ETSCustomerImpl> etsCustomerRoot, List<Predicate> criteria, CriteriaBuilder cb, boolean currentloggedCust)
   	{
    	List<Predicate> ssnCriteria = new ArrayList<Predicate>(criteria);
    	
		ParameterExpression<String> pFName =cb.parameter(String.class,FIRST_NAME1);
		Expression<String> strFName=etsCustomerRoot.get(FIRST_NAME);
		ssnCriteria.add(cb.equal(cb.upper(strFName),pFName));

		ParameterExpression<String> pLName =cb.parameter(String.class,LAST_NAME1);
		Expression<String> strLName=etsCustomerRoot.get(LAST_NAME);
		ssnCriteria.add(cb.equal(cb.upper(strLName), pLName));
		
		ParameterExpression<Date> d = cb.parameter(Date.class,DATE_OF_BIRTH1);
		ssnCriteria.add(cb.equal( etsCustomerRoot.get(DATE_OF_BIRTH),d));
		
		ParameterExpression<String> p =cb.parameter(String.class,SOCIAL_SECURITY);
		ssnCriteria.add(cb.equal(etsCustomerRoot.get(SOCIAL_SECURITY), p));
    	
		if (currentloggedCust == true)
		{
			ParameterExpression<Long> pCustID =cb.parameter(Long.class,CUST_ID3);
			ssnCriteria.add(cb.notEqual(etsCustomerRoot.get(ID), pCustID));
		}
		
		return ssnCriteria;
    }
    
	@Override
	public boolean checkForCustomerLinkageKey(String linkageKey) {
		TypedQuery<CustomerLinkage> query = em.createNamedQuery("CUSTOMER_LINKAGE_BY_KEY", CustomerLinkage.class);
		query.setParameter("linkageKey", linkageKey);
		List<CustomerLinkage> customerLinkages = query.getResultList();
	    if (customerLinkages != null && !customerLinkages.isEmpty()) {
	    	return true;
	    }
	    return false;
	}
    
    
    public TypedQuery setDupParameters(TypedQuery etsCustomerTQ,ETSCustomer customer,
    		ETSAddress address, ETSPhone phone, boolean currentloggedCust){

		etsCustomerTQ.setParameter(FIRST_NAME, checkNull(customer.getFirstName()).toUpperCase());
		etsCustomerTQ.setParameter(LAST_NAME, checkNull(customer.getLastName()).toUpperCase());
		etsCustomerTQ.setParameter(DATE_OF_BIRTH, customer.getDateOfBirth());
		etsCustomerTQ.setParameter(POSTAL_CODE, checkNull(address.getPostalCode()));
		
		etsCustomerTQ.setParameter(FIRST_NAME1, checkNull(customer.getFirstName()).toUpperCase());
		etsCustomerTQ.setParameter(LAST_NAME1, checkNull(customer.getLastName()).toUpperCase());
		etsCustomerTQ.setParameter(DATE_OF_BIRTH1, customer.getDateOfBirth());
		
		if(!"".equals(checkNull(customer.getSocialSecurity())))
		{
			etsCustomerTQ.setParameter(SOCIAL_SECURITY, (customer.getSocialSecurity()));
		}
		else
		{
			etsCustomerTQ.setParameter(COUNTRY_CODE_PRIMARY, checkNull(phone.getCountry().getAbbreviation()));
			etsCustomerTQ.setParameter(PHONE_NUMBER_PRIMARY, checkNull(phone.getPhoneNumber()));
			
			etsCustomerTQ.setParameter(FIRST_NAME2, checkNull(customer.getFirstName()).toUpperCase());
			etsCustomerTQ.setParameter(LAST_NAME2, checkNull(customer.getLastName()).toUpperCase());
			etsCustomerTQ.setParameter(DATE_OF_BIRTH2, customer.getDateOfBirth());
			etsCustomerTQ.setParameter(COUNTRY_CODE_SECONDARY, checkNull(phone.getCountry().getAbbreviation()));
			etsCustomerTQ.setParameter(PHONE_NUMBER_SECONDARY, checkNull(phone.getPhoneNumber()));
		}
		
		if(currentloggedCust == true)
		{
			etsCustomerTQ.setParameter(CUST_ID1, customer.getId());
			etsCustomerTQ.setParameter(CUST_ID2, customer.getId());
			etsCustomerTQ.setParameter(CUST_ID3, customer.getId());
			if ("".equals(checkNull(customer.getSocialSecurity())))
			{
				etsCustomerTQ.setParameter(CUST_ID4, customer.getId());
			}
		}
		
		etsCustomerTQ.setParameter(EMAIL_ADDRESS, checkNull(customer.getEmailAddress().toUpperCase()));


		return etsCustomerTQ;
    }
    
    private String checkNull(String str)
    {
    	return (str == null)?"":str;
    }

	@Override
	public String getGuidID(ETSCustomer customer, LinkageType linkageType) {
		TypedQuery<CustomerLinkage> query = em.createNamedQuery("GIUD_BY_TYPE_CUSTOMER", CustomerLinkage.class);
		query.setParameter("linkageType", linkageType);
		query.setParameter("customer", customer);
		CustomerLinkage customerLinkages = (CustomerLinkage) query.getResultList();
		return customerLinkages.getLinkageKey();
	}

	@Override
	public ETSCustomer findCustomerByUsernameAndInternalFlag(String username, Boolean internalUserFlag) {
		TypedQuery<ETSCustomer> query = em.createNamedQuery(FIND_BY_USERNAMEANDFLAG,
				ETSCustomer.class);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		query.setParameter("internalUserFlag", internalUserFlag);
		query.setParameter("username", username);
		List<ETSCustomer> users = query.getResultList();
		if (!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}
  
}


