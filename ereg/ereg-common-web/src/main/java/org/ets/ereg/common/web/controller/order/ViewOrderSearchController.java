package org.ets.ereg.common.web.controller.order;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.ets.ereg.commerce.order.vo.ShoppingCartVO;
import org.ets.ereg.commerce.order.vo.ViewOrderPaginationResultVO;
import org.ets.ereg.commerce.order.vo.ViewOrderSearchAppointmentVO;
import org.ets.ereg.commerce.order.vo.ViewOrderSearchCriteriaVO;
import org.ets.ereg.commerce.order.vo.ViewOrderSearchResultVO;
import org.ets.ereg.common.business.service.PaginationService;
import org.ets.ereg.common.exception.ERegCheckedException;
import org.ets.ereg.common.web.context.ViewOrderSearchContext;
import org.ets.ereg.common.web.controller.order.form.ViewOrderSearchForm;
import org.ets.ereg.common.web.util.CommonJSPMappingConstants;
import org.ets.ereg.common.web.util.CommonWebMappingConstants;
import org.ets.ereg.common.web.util.Constant;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.security.user.LoggedInUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(CommonWebMappingConstants.BASE_ORDER_VIEW)
public class ViewOrderSearchController {

	private final static Logger logger = LoggerFactory
			.getLogger(ViewOrderSearchController.class);

	@Resource(name = "etsViewOrderSearchService")
	private PaginationService<ViewOrderSearchCriteriaVO, ViewOrderPaginationResultVO> paginationService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	protected String viewOrderByCustomerId(
			@ModelAttribute("viewOrderSearchForm") ViewOrderSearchForm viewOrderSearchForm,
			Model model, @RequestParam("customerId") Long customerId) {
	
		Customer customer = CustomerState.getCustomer();
		if (customerId == null && customer != null) {
				customerId = customer.getId();
			}
		
	    setAttributes(model,customerId);
		
		setOrderHistory(customerId, model);

		return CommonJSPMappingConstants.VIEW_ORDER_SEARCH_VIEW;
	}
	
	
	private void setAttributes(Model model, Long customerId) {
		model.addAttribute(Constant.MODEL_ATTR_CUSTOMERID, customerId);
		
		Customer customer = CustomerState.getCustomer();
			if (customer != null && customer.getId().equals(customerId)) {
				model.addAttribute("displayHomeButton", "true");
			}
			else{
				model.addAttribute("displayHomeButton", "false");
			}
		
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	protected String searchViewOrderByCriteria(
			@ModelAttribute("viewOrderSearchForm") ViewOrderSearchForm viewOrderSearchForm,
			BindingResult errors, HttpServletRequest request, Model model,
			HttpServletResponse response, @LoggedInUser ERegUser loggedInUser)
			throws ERegCheckedException {

		return displayViewOrders(viewOrderSearchForm, errors, request, model,
				response);
	}

	@RequestMapping(value = "/search/{dummy}", method = RequestMethod.POST)
	protected String searchViewOrderByCriteria(
			@PathVariable("dummy") String dummy,
			@ModelAttribute("viewOrderSearchForm") ViewOrderSearchForm viewOrderSearchForm,
			BindingResult errors, HttpServletRequest request, Model model,
			HttpServletResponse response, @LoggedInUser ERegUser loggedInUser)
			throws ERegCheckedException {
		return displayViewOrders(viewOrderSearchForm, errors, request, model,
				response);
	}

	private void copyProperties(ViewOrderSearchForm viewOrderSearchForm,
			ViewOrderSearchCriteriaVO viewOrderSearchCriteriaVo)
			throws ERegCheckedException {
		try {
			BeanUtils.copyProperties(viewOrderSearchCriteriaVo,
					viewOrderSearchForm);

		} catch (IllegalAccessException e) {
			throw new ERegCheckedException("Error while assembling", e);
		} catch (InvocationTargetException e) {
			throw new ERegCheckedException("Error while assembling", e);
		}

	}

	private String displayViewOrders(ViewOrderSearchForm viewOrderSearchForm,
			BindingResult errors, HttpServletRequest request, Model model,
			HttpServletResponse response) throws ERegCheckedException {

		HttpSession session = request.getSession();
		ViewOrderSearchCriteriaVO viewOrderSearchCriteriaVo = null;

		if (Constant.CRITERIA_UPDATED.equals(viewOrderSearchForm.getAction())) {

			if (viewOrderSearchForm.getCustomerId() == null) {
				Customer customer = CustomerState.getCustomer();
				if (customer != null) {
					viewOrderSearchForm.setCustomerId(customer.getId());
				}
			}

			viewOrderSearchCriteriaVo = new ViewOrderSearchCriteriaVO();
			copyProperties(viewOrderSearchForm, viewOrderSearchCriteriaVo);
			ViewOrderSearchContext searchContext = new ViewOrderSearchContext();
			searchContext.setSearchCriteriaVO(viewOrderSearchCriteriaVo);

			session.setAttribute(
					ViewOrderSearchContext.VIEW_ORDER_SEARCH_CRITERA,
					searchContext);

		} else if (viewOrderSearchCriteriaVo == null) {
			ViewOrderSearchContext searchContext = (ViewOrderSearchContext) session
					.getAttribute(ViewOrderSearchContext.VIEW_ORDER_SEARCH_CRITERA);
			if (searchContext != null) {
				viewOrderSearchCriteriaVo = searchContext.getSearchCriteriaVO();
			}

		}

		if (viewOrderSearchCriteriaVo != null) {

			ViewOrderPaginationResultVO viewOrderPaginationResultVO = invokePagination(
					viewOrderSearchCriteriaVo, viewOrderSearchForm);
			setOrderHistory(viewOrderSearchForm.getCustomerId(), model);

			setAttributes(request, model, viewOrderSearchForm,
					viewOrderPaginationResultVO);
		}

		return CommonJSPMappingConstants.VIEW_ORDER_SEARCH_RESULTS_VIEW;

	}

	private ViewOrderPaginationResultVO invokePagination(
			ViewOrderSearchCriteriaVO viewOrderSearchCriteriaVo,
			ViewOrderSearchForm viewOrderSearchForm) {
		viewOrderSearchCriteriaVo.setPageNo(viewOrderSearchForm.getPageNo());
		viewOrderSearchCriteriaVo.setRowsperPage(viewOrderSearchForm
				.getRowperPage());

		ViewOrderPaginationResultVO viewOrderPaginationResultVO = paginationService
				.getDataList(viewOrderSearchCriteriaVo);

		List<ViewOrderSearchResultVO> listViewOrderSearchResultVO = viewOrderPaginationResultVO
				.getResults();

		Map<String, ViewOrderSearchResultVO> listHashMap = new HashMap<String, ViewOrderSearchResultVO>();
		groupOrderSearchResult(listViewOrderSearchResultVO, listHashMap);
		viewOrderPaginationResultVO
				.setResults(new ArrayList<ViewOrderSearchResultVO>(listHashMap
						.values()));

		return viewOrderPaginationResultVO;
	}

	private void setAttributes(HttpServletRequest request, Model model,
			ViewOrderSearchForm viewOrderSearchForm,
			ViewOrderPaginationResultVO viewOrderPaginationResultVO) {
		model.addAttribute(Constant.MODEL_ATTR_CUSTOMERID,
				viewOrderSearchForm.getCustomerId());

		request.setAttribute(Constant.SELECT_VIEW_ORDER,
				viewOrderSearchForm.getViewOrder());

		request.setAttribute(Constant.VIEW_ORDER_RESULT,
				viewOrderPaginationResultVO);
	}

	private void groupOrderSearchResult(
			List<ViewOrderSearchResultVO> listViewOrderSearchResultVO,
			Map<String, ViewOrderSearchResultVO> listHashMap) {
		for (ViewOrderSearchResultVO viewOrderSearchResultVO : listViewOrderSearchResultVO) {

			if (listHashMap.containsKey(viewOrderSearchResultVO
					.getOrderNumber())) {
				ViewOrderSearchResultVO oldViewOrderSearchResultVO = listHashMap
						.get(viewOrderSearchResultVO.getOrderNumber());
				viewOrderSearchResultVO.getListAppointment().addAll(
						oldViewOrderSearchResultVO.getListAppointment());
			}
			ViewOrderSearchAppointmentVO viewOrderSearchAppointmentVO = new ViewOrderSearchAppointmentVO(
					viewOrderSearchResultVO.getTestName(),
					viewOrderSearchResultVO.getFormattedAppointmentDate());
			viewOrderSearchResultVO.getListAppointment().add(
					viewOrderSearchAppointmentVO);

			listHashMap.put(viewOrderSearchResultVO.getOrderNumber(),
					viewOrderSearchResultVO);
		}
	}

	@ModelAttribute("viewOrders")
	public Map<String, String> getViewOrders() {

		Map<String, String> viewOrdersMap = new HashMap<String, String>();
		viewOrdersMap.put(Constant.RECENT_ORDER, Constant.LAST_SIX_MONTH_ORDER);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, 0);
		viewOrdersMap.put(String.valueOf(now.get(Calendar.YEAR)),
				String.valueOf(now.get(Calendar.YEAR)));
		now.add(Calendar.YEAR, -1);
		viewOrdersMap.put(String.valueOf(now.get(Calendar.YEAR)),
				String.valueOf(now.get(Calendar.YEAR)));

		viewOrdersMap.put(Constant.ALL, Constant.ALL);

		return viewOrdersMap;

	}

	private void setOrderHistory(Long customerId, Model model) {

		ViewOrderSearchCriteriaVO viewOrderSearchCriteriaVo = new ViewOrderSearchCriteriaVO();

		viewOrderSearchCriteriaVo.setCustomerId(customerId);
		viewOrderSearchCriteriaVo.setViewOrder(Constant.ALL);
		viewOrderSearchCriteriaVo.setPageNo(1);
		viewOrderSearchCriteriaVo.setRowsperPage(5);
		ViewOrderPaginationResultVO viewOrderPaginationResultVO = paginationService
				.getDataList(viewOrderSearchCriteriaVo);
		if (!CollectionUtils.isEmpty(viewOrderPaginationResultVO.getResults())) {
			model.addAttribute(Constant.MODEL_ATTR_ORDERHISTORY, Constant.YES);
		} else {
			model.addAttribute(Constant.MODEL_ATTR_ORDERHISTORY, Constant.NO);

		}

	}

}
