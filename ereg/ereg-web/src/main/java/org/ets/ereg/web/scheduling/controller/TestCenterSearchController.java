package org.ets.ereg.web.scheduling.controller;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.service.CountryService;
import org.codehaus.jackson.map.ObjectMapper;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.scheduling.service.TestCenterService;
import org.ets.ereg.scheduling.vo.SearchResult;
import org.ets.ereg.scheduling.vo.TestCenterSearchCriteria;
import org.ets.ereg.scheduling.vo.TestCenterSearchResultEntry;
import org.ets.ereg.session.facade.program.service.ProgramBusinessService;
import org.ets.ereg.session.facade.shared.service.ApplicationConfigurationBusinessService;
import org.ets.ereg.session.facade.shared.service.ReferenceBusinessService;
import org.ets.ereg.web.scheduling.form.TestCenterSearchForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/public/testcenter")
public class TestCenterSearchController {
	public static final String FORM_ATTRIBUTE = "testCenterSearchForm";
	
	@Resource(name = "blCountryService")
	private CountryService countryService;
	
	@Resource(name = "referenceEntityBusinessService")
	private ReferenceBusinessService referenceEntityBusinessService;
	
	@Resource(name = "applicationConfigurationBusinessService")
	private ApplicationConfigurationBusinessService applicationConfigurationBusinessService;
	
	@Resource(name = "programBusinessService")
	private ProgramBusinessService programBusinessService;
	
	@Resource(name = "testCenterService")
	private TestCenterService testCenterService;
	
	private String getTestCenterSearchView() {
		return "/public/scheduling/testCenterSearch";
	}
	
	private String getTestCenterDetailView() {
		return "/public/scheduling/testCenterDetail";
	}
	
	private SearchResult<TestCenterSearchResultEntry> search(
			String detailViewLink,
			TestCenterSearchForm testCenterSearchForm)
	{
		TestCenterSearchCriteria searchCriteria = new TestCenterSearchCriteria();	
		searchCriteria.setProgramType(programBusinessService.getProgramByPrimaryKey(testCenterSearchForm.getProgramCode().toUpperCase()));
		searchCriteria.setLatitudeDegree(testCenterSearchForm.getLatitudeDegree());
		searchCriteria.setLongitudeDegree(testCenterSearchForm.getLongitudeDegree());
		searchCriteria.setTestTypes(testCenterSearchForm.getTestTypes());
		if(null != testCenterSearchForm.getDistance()){
			searchCriteria.setDistanceMile(testCenterSearchForm.getDistance().doubleValue());
		}
		searchCriteria.setTestCenterName(testCenterSearchForm.getTestCenterName());
		SearchResult<TestCenterSearchResultEntry> searchResult;
		if(null != searchCriteria.getLatitudeDegree() && null != searchCriteria.getLongitudeDegree()){
			searchResult = testCenterService.findTestCenters(searchCriteria);
		}
		else{
			searchResult = new SearchResult<TestCenterSearchResultEntry>();
		}
		searchResult.setLink(detailViewLink);
		return searchResult;
	}
	
	@RequestMapping(value="/search/{programCode}", method = RequestMethod.GET)
	public String onGetTestCenterSearch(
			@PathVariable(TestCenterSearchForm.PROGRAM_CODE) String programCode,
			Model model,
			@ModelAttribute(TestCenterSearchController.FORM_ATTRIBUTE) TestCenterSearchForm testCenterSearchForm,
	        BindingResult errors,
	        HttpServletRequest request,
			HttpServletResponse response)
	{
		String jsonSearchResult = null;
		testCenterSearchForm.setCountry(countryService.findCountryByAbbreviation("US"));
		List<Integer> distances = getDistances();
		testCenterSearchForm.setDistance(distances.get(2));
		testCenterSearchForm.setTestTypes(getTestTypes());
		if(null == testCenterSearchForm.getLatitudeDegree() || null == testCenterSearchForm.getLongitudeDegree()){
			jsonSearchResult = "null";
		}
		else{
			ObjectMapper objectMapper = new ObjectMapper();
			testCenterSearchForm.setProgramCode(programCode);
			SearchResult<TestCenterSearchResultEntry> searchResult = search(request.getContextPath()+"/public/testcenter/", testCenterSearchForm);
			try{
				jsonSearchResult = objectMapper.writeValueAsString(searchResult);
			}
			catch(Exception e){
				//TODO log
			}
		}
		model.addAttribute("searchResult", jsonSearchResult);
		return getTestCenterSearchView();
	}
	
	@RequestMapping(value="/search/{programCode}", method = RequestMethod.POST)
	@ResponseBody public SearchResult<TestCenterSearchResultEntry> onPostTestCenterSearch(
			@PathVariable(TestCenterSearchForm.PROGRAM_CODE) String programCode,
			HttpServletRequest request,
			@ModelAttribute(TestCenterSearchController.FORM_ATTRIBUTE) TestCenterSearchForm testCenterSearchForm,
	        BindingResult errors) 
	{
		testCenterSearchForm.setProgramCode(programCode);
		return search(request.getContextPath()+"/public/testcenter/", testCenterSearchForm);
	}
	
	@RequestMapping(value = "/{testCenterId}", method = RequestMethod.GET)
	public String onGetTestCenterDetail(
			@PathVariable("testCenterId") Long testCenterId,
			@RequestParam(value=TestCenterSearchForm.DISTANCE, required=false) Double distance,
			@RequestParam(value=TestCenterSearchForm.LATITUDE_DEGREE, required=true) Double latitudeDegree,
			@RequestParam(value=TestCenterSearchForm.LONGITUDE_DEGREE, required=true) Double longitudeDegree,
			Model model)
	{
		TestCenter testCenter = testCenterService.readTestCenterById(testCenterId);
		model.addAttribute("testCenter", testCenter);
		if(null!=distance) {
			model.addAttribute(TestCenterSearchForm.DISTANCE, distance);
		}
		model.addAttribute(TestCenterSearchForm.LATITUDE_DEGREE, latitudeDegree);
		model.addAttribute(TestCenterSearchForm.LONGITUDE_DEGREE, longitudeDegree);
		return getTestCenterDetailView();
	}
			
	
    @ModelAttribute("countries")
    public List<Country> getCountries()
    {
    	return countryService.findCountries();
    }
    
    @ModelAttribute("distances")
    public List<Integer> getDistances()
    {
		List<Integer> distances = new ArrayList<Integer>();
		String val = applicationConfigurationBusinessService.findApplicationConfigurationValue(ApplicationConfigurationService.SEARCH_DISTANCES);
		if(null != val && val.trim().length() > 0){
			for(String distance:val.split(",")){
				distances.add(Integer.parseInt(distance));
			}
		}
    	return distances;
    }
    
    @ModelAttribute("testTypes")
    public List<DeliveryModeType> getTestTypes()
    {
    	List<DeliveryModeType> testTypes = new ArrayList<DeliveryModeType>();
    	testTypes.add(referenceEntityBusinessService.getEntityByPrimaryKey(DeliveryModeType.class, DeliveryModeType.PBT));
    	testTypes.add(referenceEntityBusinessService.getEntityByPrimaryKey(DeliveryModeType.class, DeliveryModeType.CBT));
    	return testTypes;
    }
    
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(DeliveryModeType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
            	DeliveryModeType value = referenceEntityBusinessService.getEntityByPrimaryKey(DeliveryModeType.class, text);
                setValue(value);
            }
        });
        
        binder.registerCustomEditor(Country.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
            	Country country = countryService.findCountryByAbbreviation(text);
                setValue(country);
            }
        });
    }
}
