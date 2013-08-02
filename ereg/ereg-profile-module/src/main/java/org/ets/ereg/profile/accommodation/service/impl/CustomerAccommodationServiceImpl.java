package org.ets.ereg.profile.accommodation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.ets.ereg.common.business.dao.AccommodationTypeValueDao;
import org.ets.ereg.common.business.dao.ProgramAccommodationDeliveryModeDao;
import org.ets.ereg.common.business.dao.TestDao;
import org.ets.ereg.common.business.util.AccommodationStatus;
import org.ets.ereg.domain.accommodation.CustomerAccommodationTestImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationTypeValue;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.accommodation.ProgramAccommodationDeliveryMode;
import org.ets.ereg.domain.interfaces.model.accommodation.id.CustomerAccommodationTestId;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.profile.accommodation.dao.CustomerAccommodationTestDao;
import org.ets.ereg.profile.accommodation.service.CustomerAccommodationService;
import org.ets.ereg.profile.accommodation.vo.AccommodationVO;
import org.ets.ereg.profile.accommodation.vo.CustomerAccommodationVO;
import org.ets.ereg.profile.accommodation.vo.CustomerDeliveryMethodAccommodationsVO;
import org.ets.ereg.profile.accommodation.vo.CustomerProgramAccommodationsVO;
import org.ets.ereg.profile.accommodation.vo.CustomerTestAccommodationsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("customerAccommodationService")
public class CustomerAccommodationServiceImpl implements CustomerAccommodationService{

    private static Logger logger = LoggerFactory.getLogger(CustomerAccommodationServiceImpl.class);

    @Resource(name="customerAccommodationTestDao")
    private CustomerAccommodationTestDao customerAccommodationTestDao;

    @Resource(name="accommodationTypeValueDao")
    private AccommodationTypeValueDao accommodationTypeValueDao;

    @Resource(name="testDao")
    private TestDao testDao;

    @Resource(name="programAccommodationDeliveryModeDao")
    private ProgramAccommodationDeliveryModeDao programAccommodationDeliveryModeDao;

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public List<CustomerAccommodationTest> getActiveAccommodations(Long customerId,Date expirationDate) {
        return customerAccommodationTestDao.getActiveAccommodations(customerId,expirationDate);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public List<CustomerAccommodationTest> getAllAccommodations(Long customerId) {
        return customerAccommodationTestDao.getAllAccommodations(customerId);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public List<CustomerAccommodationTest> getActiveAccommodationsForTest(
            Long customerId, Long testId, String programCode,Date expirationDate) {
        return customerAccommodationTestDao.getActiveAccommodationsForTest(customerId, testId,
                                                                programCode,expirationDate);
    }


    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public Map<String,List<CustomerAccommodationTest>> getActiveAccommodationsForTestByDeliveryMethod(
            Long customerId, Long testId, String programCode,Date expirationDate) {
        List<CustomerAccommodationTest> customerAccommodations = customerAccommodationTestDao.getActiveAccommodationsForTest(customerId, testId,
                                                                programCode,expirationDate);

        return groupAccommodationByDeliveryMethod(customerAccommodations);
    }



    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
    public void saveCustomerAccommodation(
            CustomerAccommodationTest customerAccommodation) {
        customerAccommodationTestDao.save(customerAccommodation);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
    public void deleteCustomerAccommodation(
            CustomerAccommodationTest customerAccommodation) {
        customerAccommodationTestDao.delete(customerAccommodation);
    }

    public CustomerAccommodationTestDao getCustomerAccommodationTestDao() {
        return customerAccommodationTestDao;
    }

    public void setCustomerAccommodationTestDao(
            CustomerAccommodationTestDao customerAccommodationTestDao) {
        this.customerAccommodationTestDao = customerAccommodationTestDao;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public boolean hasApprovedActiveAccommodations(Long customerId,
            Date expirationDate) {
        return (getActiveAccommodations(customerId,expirationDate).size()>0);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
    public void deleteCustomerAccommodation(Long customerId, String programCode,
            Long testId, String accommodationTypeCode,String deliveryModeCode) {
        CustomerAccommodationTest customerAccomodatinTest = customerAccommodationTestDao.create();
        CustomerAccommodationTestId customerAccommodationTestId = customerAccomodatinTest.getCustomerAccommodationTestId();
        customerAccommodationTestId.setCustomerId(customerId);
        customerAccommodationTestId.setProgramCode(programCode);
        customerAccommodationTestId.setTestId(testId);
        customerAccommodationTestId.setDeliveryModeCode(deliveryModeCode);
        customerAccommodationTestId.setAccommodationTypeCode(accommodationTypeCode);
        CustomerAccommodationTest custAccommodationTest = customerAccommodationTestDao.findByPrimaryKey(CustomerAccommodationTest.class,customerAccommodationTestId);
        customerAccommodationTestDao.delete(custAccommodationTest);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public List<Test> getTestsWithoutAccommodations(Long customerId,
            String programCode, String deliveryModeCode) {

        return customerAccommodationTestDao.getTestsWithoutAccommodations(customerId,programCode,deliveryModeCode);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public List<AccommodationType> getAllNotApprovedAccommodations(
            Long customerId, String programCode, String deliveryModeCode) {

        return customerAccommodationTestDao.getAllNotApprovedAccommodations(customerId,programCode,deliveryModeCode);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
    public void addAccommodations(List<CustomerAccommodationVO> customerAccommodations) {

        if(!customerAccommodations.isEmpty()){

            CustomerAccommodationTest customerAccommodationTest = null;
            CustomerAccommodationTestId customerAccommodationTestId = null;
            List<CustomerAccommodationTest> customerAccommodationTests = new ArrayList<CustomerAccommodationTest>();

            AccommodationTypeValue accommodationTypeValue=null;
            for(CustomerAccommodationVO customerAccommodation: customerAccommodations){

                customerAccommodationTest = customerAccommodationTestDao.create();
                customerAccommodationTestId = customerAccommodationTest.getCustomerAccommodationTestId();

                customerAccommodationTestId.setCustomerId(customerAccommodation.getCustomerId());
                customerAccommodationTestId.setDeliveryModeCode(customerAccommodation.getDeliveryMethodCode());
                customerAccommodationTestId.setProgramCode(customerAccommodation.getProgramCode());
                customerAccommodationTestId.setTestId(customerAccommodation.getTestId());
                customerAccommodationTestId.setAccommodationTypeCode(customerAccommodation
                                                        .getAccommodation().getAccommodationTypeCode());

                Test test = testDao.getTest(customerAccommodation.getTestId());
                customerAccommodationTest.setTest(test);

                ProgramAccommodationDeliveryMode programAccommodationDeliveryMode = programAccommodationDeliveryModeDao.getProgramAccommodationDeliveryMode(customerAccommodation.getProgramCode(),
                        customerAccommodation.getDeliveryMethodCode(), customerAccommodation.getAccommodation().getAccommodationTypeCode());
                customerAccommodationTest.setProgramAccommodationDeliveryMode(programAccommodationDeliveryMode);

                if(customerAccommodation.getAccommodation().getValueId() != null){
                    accommodationTypeValue = accommodationTypeValueDao.findByPrimaryKey(customerAccommodation.getAccommodation().getValueId());
                    customerAccommodationTest.setAccommodationTypeValue(accommodationTypeValue);
                }

                if(StringUtils.isNotEmpty(customerAccommodation.getAccommodation().getOtherValueText())){
                    customerAccommodationTest.setOtherAccommodationTypeText(
                                    customerAccommodation.getAccommodation().getOtherValueText());
                }


                customerAccommodationTest.setApprovedDate(customerAccommodation.getApprovalDate());
                customerAccommodationTest.setExpiryDate(customerAccommodation.getExpirationDate());
                customerAccommodationTests.add(customerAccommodationTest);


            }

            logger.debug("No of accommodations to save: {}",customerAccommodationTests.size());
            //save all accomodations..
            for(CustomerAccommodationTest custAccommodationTest:customerAccommodationTests){
                customerAccommodationTestDao.save(custAccommodationTest);
            }



        }else{

            logger.debug("No accommodations to save");
        }

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
    public void updateAccommodations(List<CustomerAccommodationVO> customerAccommodations){
        addAccommodations(customerAccommodations);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public CustomerAccommodationTest getAccommodation(Long customerId, String programCode,
            Long testId, String accommodationTypeCode,
            String deliveryModeCode) {

        CustomerAccommodationTest customerAccommodationTest = customerAccommodationTestDao.getCustomerProgramAccommodationTest(customerId,
                        programCode,testId,accommodationTypeCode,deliveryModeCode);

        return customerAccommodationTest;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public CustomerProgramAccommodationsVO getAllAccommodations(Long customerId,
            String programCode, Long testId, String deliveryModeCode,
            AccommodationStatus accommodationStatus) {

            List<CustomerAccommodationTestImpl> customerAccommodations = customerAccommodationTestDao.getAllAccommodations(customerId, programCode,
                    testId, deliveryModeCode, accommodationStatus,null);

            Map<Long,Map<String,List<CustomerAccommodationTest>>> customerAccommodationsByTest = groupAccommodationByTestAndDeliveryMethod(customerAccommodations);

            return toCustomerProgramAccommodationsVO(customerId, programCode, customerAccommodationsByTest);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public CustomerProgramAccommodationsVO getAllAccommodations(
            Long customerId, String programCode, Long testId,
            String deliveryModeCode, AccommodationStatus accommodationStatus,Date testDate) {


        List<CustomerAccommodationTestImpl> customerAccommodations = customerAccommodationTestDao.getAllAccommodations(customerId, programCode,
                                                    testId, deliveryModeCode, accommodationStatus,testDate);

        Map<Long,Map<String,List<CustomerAccommodationTest>>> customerAccommodationsByTest = groupAccommodationByTestAndDeliveryMethod(customerAccommodations);

        return toCustomerProgramAccommodationsVO(customerId, programCode, customerAccommodationsByTest);
    }


    private CustomerProgramAccommodationsVO toCustomerProgramAccommodationsVO(Long customerId,
                String programCode,Map<Long,Map<String,List<CustomerAccommodationTest>>> customerAccommodationsByTest){


        CustomerTestAccommodationsVO customerTestAccommodationsVO=null;
        CustomerDeliveryMethodAccommodationsVO customerDeliveryMethodAccommodationsVO=null;
        CustomerAccommodationVO customerAccommodationVO=null;

        List<CustomerDeliveryMethodAccommodationsVO> customerDeliveryMethodAccommodations=null;
        List<CustomerAccommodationVO> customerAccommodationsList=null;
        List<CustomerTestAccommodationsVO> customerTestAccommodations = new ArrayList<CustomerTestAccommodationsVO>();

        Set<Entry<Long, Map<String, List<CustomerAccommodationTest>>>> accommodationByTestEntrySet = customerAccommodationsByTest.entrySet();

        CustomerProgramAccommodationsVO customerProgramAccommodationsVO = new CustomerProgramAccommodationsVO();
        customerProgramAccommodationsVO.setCustomerId(customerId);
        customerProgramAccommodationsVO.setProgramCode(programCode);


        Map<String,List<CustomerAccommodationTest>> custAccByDeliveryMethod=null;

        Set<Entry<String, List<CustomerAccommodationTest>>> deliveryMethodsEntrySet =null;
        List<CustomerAccommodationTest> customerAccommodationsTest=null;

        for(Entry<Long, Map<String, List<CustomerAccommodationTest>>> entry: accommodationByTestEntrySet){

            custAccByDeliveryMethod = entry.getValue();
            customerTestAccommodationsVO = new CustomerTestAccommodationsVO();
            customerTestAccommodations.add(customerTestAccommodationsVO);

            deliveryMethodsEntrySet = custAccByDeliveryMethod.entrySet();
            customerDeliveryMethodAccommodations = new ArrayList<CustomerDeliveryMethodAccommodationsVO>();

            for(Entry<String,List<CustomerAccommodationTest>> deliveryMethodEntry:deliveryMethodsEntrySet){

                customerAccommodationsTest = deliveryMethodEntry.getValue();

                customerTestAccommodationsVO.setTestTitle(customerAccommodationsTest.get(0).getTest().getTestName());

                customerDeliveryMethodAccommodationsVO = new CustomerDeliveryMethodAccommodationsVO();
                customerDeliveryMethodAccommodationsVO.setDeliveryMethod(
                        customerAccommodationsTest.get(0)
                            .getProgramAccommodationDeliveryMode().getDeliveryModeType().getDescription());

                customerAccommodationsList = new ArrayList<CustomerAccommodationVO>();

                for(CustomerAccommodationTest customerAccommodation:customerAccommodationsTest){

                    customerAccommodationVO = toCustomerAccommodationVO(customerAccommodation, customerId, programCode);
                    customerAccommodationsList.add(customerAccommodationVO);
                    customerAccommodationVO.setAccommodation(toAccommodationVO(customerAccommodation));
                }



                customerDeliveryMethodAccommodationsVO.setCustomerAccommodations(customerAccommodationsList);
                customerDeliveryMethodAccommodations.add(customerDeliveryMethodAccommodationsVO);
            }
            customerTestAccommodationsVO.setCustomerDeliveryMethodAccommodations(customerDeliveryMethodAccommodations);

        }



        customerProgramAccommodationsVO.setCustomerTestAccommodations(customerTestAccommodations);

        return customerProgramAccommodationsVO;
    }

    private CustomerAccommodationVO toCustomerAccommodationVO(
                CustomerAccommodationTest customerAccommodation,Long customerId,String programCode){

        CustomerAccommodationVO customerAccommodationVO = new CustomerAccommodationVO();
        customerAccommodationVO.setApprovalDate(customerAccommodation.getApprovedDate());
        customerAccommodationVO.setCustomerId(customerId);
        customerAccommodationVO.setDeliveryMethodCode(customerAccommodation.
                        getProgramAccommodationDeliveryMode().getDeliveryModeType().getCode());

        customerAccommodationVO.setExpirationDate(customerAccommodation.getExpiryDate());
        customerAccommodationVO.setProgramCode(programCode);
        customerAccommodationVO.setTestId(customerAccommodation.getCustomerAccommodationTestId().getTestId());

        return customerAccommodationVO;

    }

    private AccommodationVO toAccommodationVO(CustomerAccommodationTest customerAccommodation){
        AccommodationVO accommodationVO = new AccommodationVO();
        accommodationVO.setAccommodationTypeCode(customerAccommodation.
                            getCustomerAccommodationTestId().getAccommodationTypeCode());


        accommodationVO.setDescription(customerAccommodation.
                        getProgramAccommodationDeliveryMode().getAccommodationType().getDescription());

        accommodationVO.setOtherValueText(customerAccommodation.getOtherAccommodationTypeText());
        if(customerAccommodation.getAccommodationTypeValue()!=null){
            accommodationVO.setValueId(customerAccommodation.getAccommodationTypeValue().getAccommodationTypeValueIdentifier());
            accommodationVO.setValueText(customerAccommodation.getAccommodationTypeValue().getLabel());
        }

        return accommodationVO;
    }

    private Map<Long,Map<String,List<CustomerAccommodationTest>>> groupAccommodationByTestAndDeliveryMethod(List<CustomerAccommodationTestImpl> customerAccommodations){

        Map<Long,Map<String,List<CustomerAccommodationTest>>> customerAccommodationsByTest = new HashMap<Long,Map<String,List<CustomerAccommodationTest>>>();

        Long testId=null;
        String deliveryModeCode =null;
        Map<String,List<CustomerAccommodationTest>> accommodationsByDeliveryMethod = null;

        Map<String,List<CustomerAccommodationTest>> tmpAccommodationsByDeliveryMethod =null;
        List<CustomerAccommodationTest> tmpCustomerAccommodations = null;
        List<CustomerAccommodationTest> customerAccommodationsList = null;

        for(CustomerAccommodationTest customerAccommodation:customerAccommodations){

            tmpAccommodationsByDeliveryMethod =null;
            tmpCustomerAccommodations = null;
            accommodationsByDeliveryMethod = null;
            customerAccommodationsList = null;

            testId = customerAccommodation.getTest().getTestId();
            deliveryModeCode = customerAccommodation.
                    getProgramAccommodationDeliveryMode().getDeliveryModeType().getCode();


            if(customerAccommodationsByTest.containsKey(testId)){
                tmpAccommodationsByDeliveryMethod = customerAccommodationsByTest.get(testId);

                if(tmpAccommodationsByDeliveryMethod.containsKey(deliveryModeCode)){
                    tmpCustomerAccommodations = tmpAccommodationsByDeliveryMethod.get(deliveryModeCode);
                    tmpCustomerAccommodations.add(customerAccommodation);
                    tmpAccommodationsByDeliveryMethod.put(deliveryModeCode, tmpCustomerAccommodations);
                    customerAccommodationsByTest.put(testId, tmpAccommodationsByDeliveryMethod);
                }else{
                    customerAccommodationsList = new ArrayList<CustomerAccommodationTest>();
                    customerAccommodationsList.add(customerAccommodation);

                    tmpAccommodationsByDeliveryMethod.put(deliveryModeCode, customerAccommodationsList);
                    customerAccommodationsByTest.put(testId, tmpAccommodationsByDeliveryMethod);
                }

            }else{
                customerAccommodationsList = new ArrayList<CustomerAccommodationTest>();
                customerAccommodationsList.add(customerAccommodation);

                accommodationsByDeliveryMethod = new HashMap<String, List<CustomerAccommodationTest>>();
                accommodationsByDeliveryMethod.put(deliveryModeCode,customerAccommodationsList);
                customerAccommodationsByTest.put(testId, accommodationsByDeliveryMethod);
            }

        }
        return customerAccommodationsByTest;
    }

    private Map<String, List<CustomerAccommodationTest>> groupAccommodationByDeliveryMethod(
            List<CustomerAccommodationTest> customerAccommodations) {

        String deliveryMethod = null;
        Map<String, List<CustomerAccommodationTest>> customerAccommodationsByDeliveryMethod = new HashMap<String, List<CustomerAccommodationTest>>();

        List<CustomerAccommodationTest> tmpCustomerAccommodations = null;

        for (CustomerAccommodationTest customerAccommodation : customerAccommodations) {

            deliveryMethod = customerAccommodation
                    .getProgramAccommodationDeliveryMode()
                    .getDeliveryModeType().getDescription();

            if (customerAccommodationsByDeliveryMethod
                    .containsKey(deliveryMethod)) {

                customerAccommodationsByDeliveryMethod.get(deliveryMethod).add(
                        customerAccommodation);

            } else {

                tmpCustomerAccommodations = new ArrayList<CustomerAccommodationTest>();
                tmpCustomerAccommodations.add(customerAccommodation);
                customerAccommodationsByDeliveryMethod.put(deliveryMethod,
                        tmpCustomerAccommodations);

            }

        }
        return customerAccommodationsByDeliveryMethod;
    }

    public AccommodationTypeValueDao getAccommodationTypeValueDao() {
        return accommodationTypeValueDao;
    }

    public void setAccommodationTypeValueDao(
            AccommodationTypeValueDao accommodationTypeValueDao) {
        this.accommodationTypeValueDao = accommodationTypeValueDao;
    }

@Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public List<CustomerAccommodationTest> getAllActiveAccommodationsByDeliveryMode(
            Long customerId, String programCode, Long testId,
            String deliveryModeCode, Date expirationDate) {
        List<CustomerAccommodationTest> customerAccommodations = customerAccommodationTestDao.getAllActiveAccommodationsByDeliveryMode(customerId, programCode,
                                                    testId, deliveryModeCode, expirationDate);
        return customerAccommodations;
    }

public TestDao getTestDao() {
    return testDao;
}

public void setTestDao(TestDao testDao) {
    this.testDao = testDao;
}

public ProgramAccommodationDeliveryModeDao getProgramAccommodationDeliveryModeDao() {
    return programAccommodationDeliveryModeDao;
}

public void setProgramAccommodationDeliveryModeDao(
        ProgramAccommodationDeliveryModeDao programAccommodationDeliveryModeDao) {
    this.programAccommodationDeliveryModeDao = programAccommodationDeliveryModeDao;
}

@Override
@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
public CustomerProgramAccommodationsVO getCustomerAccommodation(
        Long customerId, String programCode, Long testId,
        String accommodationTypeCode, String deliveryModeCode) {

    CustomerAccommodationTest customerAccommodationTest = getAccommodation(customerId, programCode,
                        testId, accommodationTypeCode, deliveryModeCode);

    CustomerProgramAccommodationsVO customerProgramAccommodationsVO = new CustomerProgramAccommodationsVO();
    customerProgramAccommodationsVO.setCustomerId(customerId);
    customerProgramAccommodationsVO.setProgramCode(programCode);

    List<CustomerTestAccommodationsVO> customerTestAccommodations =new ArrayList<CustomerTestAccommodationsVO>();

    CustomerTestAccommodationsVO customerTestAccommodation = new CustomerTestAccommodationsVO();
    customerTestAccommodation.setTestTitle(customerAccommodationTest.getTest().getTestName());

    customerTestAccommodations.add(customerTestAccommodation);
    customerProgramAccommodationsVO.setCustomerTestAccommodations(customerTestAccommodations);


    List<CustomerDeliveryMethodAccommodationsVO> customerDeliveryMethodAccommodations = new ArrayList<CustomerDeliveryMethodAccommodationsVO>();

    CustomerDeliveryMethodAccommodationsVO customerDeliveryMethodAccommodation = new CustomerDeliveryMethodAccommodationsVO();
    customerDeliveryMethodAccommodation.setDeliveryMethod(customerAccommodationTest.
                                getProgramAccommodationDeliveryMode().getDeliveryModeType().getDescription());

    customerDeliveryMethodAccommodations.add(customerDeliveryMethodAccommodation);


    customerTestAccommodation.setCustomerDeliveryMethodAccommodations(customerDeliveryMethodAccommodations);

    List<CustomerAccommodationVO> customerAccommodations = new ArrayList<CustomerAccommodationVO>();

    CustomerAccommodationVO customerAccommodation=  toCustomerAccommodationVO(customerAccommodationTest, customerId, programCode);
    AccommodationVO accommodation = toAccommodationVO(customerAccommodationTest);
    customerAccommodation.setAccommodation(accommodation);

    customerAccommodations.add(customerAccommodation);

    customerDeliveryMethodAccommodation.setCustomerAccommodations(customerAccommodations);

    return customerProgramAccommodationsVO;


}


}
