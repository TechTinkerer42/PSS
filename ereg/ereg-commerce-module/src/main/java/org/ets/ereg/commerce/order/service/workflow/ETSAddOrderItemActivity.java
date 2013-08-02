package org.ets.ereg.commerce.order.service.workflow;

import javax.annotation.Resource;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductBundle;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.call.DiscreteOrderItemRequest;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.call.ProductBundleOrderItemRequest;
import org.broadleafcommerce.core.order.service.workflow.CartOperationContext;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.commerce.order.dto.BatteryOrderItemRequestDTO;
import org.ets.ereg.commerce.order.dto.MembershipOrderItemRequestDTO;
import org.ets.ereg.commerce.order.service.ETSOrderItemService;
import org.ets.ereg.commerce.order.service.call.BatteryOrderItemRequest;
import org.ets.ereg.commerce.order.service.call.ETSDiscreteOrderItemRequest;
import org.ets.ereg.commerce.order.service.call.MembershipOrderItemRequest;
import org.ets.ereg.commerce.order.service.call.RescheduleOrderItemRequest;
import org.ets.ereg.domain.interfaces.commerce.order.ETSRescheduleTestItemRequest;
import org.ets.ereg.domain.interfaces.commerce.order.ETSTestItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class has the same functionality as the Broadleaf AddOrderItemActivity
 * but modified to support adding Dependent Items from a Cart Rule
 */
public class ETSAddOrderItemActivity extends BaseActivity {

    private static Logger LOG = LoggerFactory.getLogger(ETSAddOrderItemActivity.class);

    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @Resource(name = "blOrderItemService")
    protected ETSOrderItemService orderItemService;

    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;

    @Override
    public ProcessContext execute(ProcessContext context) throws Exception {
        CartOperationRequest request = ((CartOperationContext) context).getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();

        // Order and sku have been verified in a previous activity -- the values
        // in the request can be trusted
        Order order = request.getOrder();
        Sku sku = catalogService.findSkuById(orderItemRequestDTO.getSkuId());

        Product product = null;
        if (orderItemRequestDTO.getProductId() != null) {
            product = catalogService.findProductById(orderItemRequestDTO.getProductId());
        }

        Category category = null;
        if (orderItemRequestDTO.getCategoryId() != null) {
            category = catalogService.findCategoryById(orderItemRequestDTO.getCategoryId());
        }

        if (category == null && product != null) {
            category = product.getDefaultCategory();
        }

        OrderItem item;
        if (orderItemRequestDTO instanceof ETSTestItemRequest){        	
        	ETSDiscreteOrderItemRequest itemRequest = new ETSDiscreteOrderItemRequest();
            itemRequest.setCategory(category);
            itemRequest.setProduct(product);
            itemRequest.setSku(sku);
            itemRequest.setQuantity(orderItemRequestDTO.getQuantity());
            itemRequest.setItemAttributes(orderItemRequestDTO.getItemAttributes());
            itemRequest.setOrder(order);
            itemRequest.setSalePriceOverride(orderItemRequestDTO.getOverrideSalePrice());
            itemRequest.setRetailPriceOverride(orderItemRequestDTO.getOverrideRetailPrice());
             
            item = orderItemService.createTestDiscreteOrderItem(itemRequest); 
             
        } else if (orderItemRequestDTO instanceof ETSRescheduleTestItemRequest) {
            RescheduleOrderItemRequest itemRequest = new RescheduleOrderItemRequest();
            itemRequest.setCategory(category);
            itemRequest.setProduct(product);
            itemRequest.setSku(sku);
            itemRequest.setQuantity(orderItemRequestDTO.getQuantity());
            itemRequest.setItemAttributes(orderItemRequestDTO.getItemAttributes());
            itemRequest.setOrder(order);
            itemRequest.setSalePriceOverride(orderItemRequestDTO.getOverrideSalePrice());
            itemRequest.setRetailPriceOverride(orderItemRequestDTO.getOverrideRetailPrice());

            ETSRescheduleTestItemRequest rdto = (ETSRescheduleTestItemRequest) orderItemRequestDTO;
            itemRequest.setOriginalTestItem(rdto.getOriginalTestItem());

            item = orderItemService.createRescheduleTestDiscreteOrderItem(itemRequest);
            
        } else if (orderItemRequestDTO instanceof MembershipOrderItemRequestDTO) {
            MembershipOrderItemRequest itemRequest = new MembershipOrderItemRequest();
            itemRequest.setCategory(category);
            itemRequest.setProduct(product);
            itemRequest.setSku(sku);
            itemRequest.setQuantity(orderItemRequestDTO.getQuantity());
            itemRequest.setItemAttributes(orderItemRequestDTO.getItemAttributes());
            itemRequest.setOrder(order);
            itemRequest.setSalePriceOverride(orderItemRequestDTO.getOverrideSalePrice());
            itemRequest.setRetailPriceOverride(orderItemRequestDTO.getOverrideRetailPrice());

            MembershipOrderItemRequestDTO mdto = (MembershipOrderItemRequestDTO) orderItemRequestDTO;
            itemRequest.setRefOrderItemId(mdto.getRefOrderItemId());
            itemRequest.setAgency(mdto.getAgency());
            itemRequest.setStartDate(mdto.getStartDate());
            itemRequest.setEndDate(mdto.getEndDate());

            item = orderItemService.createMembershipDiscreteOrderItem(itemRequest);
            
        } else if (orderItemRequestDTO instanceof BatteryOrderItemRequestDTO) {
            BatteryOrderItemRequest itemRequest = new BatteryOrderItemRequest();
            itemRequest.setCategory(category);
            itemRequest.setProduct(product);
            itemRequest.setSku(sku);
            itemRequest.setQuantity(orderItemRequestDTO.getQuantity());
            itemRequest.setItemAttributes(orderItemRequestDTO.getItemAttributes());
            itemRequest.setOrder(order);
            itemRequest.setSalePriceOverride(orderItemRequestDTO.getOverrideSalePrice());
            itemRequest.setRetailPriceOverride(orderItemRequestDTO.getOverrideRetailPrice());

            BatteryOrderItemRequestDTO bdto = (BatteryOrderItemRequestDTO) orderItemRequestDTO;
            itemRequest.setAgency(bdto.getAgency());

            item = orderItemService.createBatteryDiscreteOrderItem(itemRequest);
            
        } else if(product == null || !(product instanceof ProductBundle)) {
            DiscreteOrderItemRequest itemRequest = new DiscreteOrderItemRequest();
            itemRequest.setCategory(category);
            itemRequest.setProduct(product);
            itemRequest.setSku(sku);
            itemRequest.setQuantity(orderItemRequestDTO.getQuantity());
            itemRequest.setItemAttributes(orderItemRequestDTO.getItemAttributes());
            itemRequest.setOrder(order);
            itemRequest.setSalePriceOverride(orderItemRequestDTO.getOverrideSalePrice());
            itemRequest.setRetailPriceOverride(orderItemRequestDTO.getOverrideRetailPrice());
            item = orderItemService.createDiscreteOrderItem(itemRequest);
            
        } else {
            ProductBundleOrderItemRequest bundleItemRequest = new ProductBundleOrderItemRequest();
            bundleItemRequest.setCategory(category);
            bundleItemRequest.setProductBundle((ProductBundle) product);
            bundleItemRequest.setSku(sku);
            bundleItemRequest.setQuantity(orderItemRequestDTO.getQuantity());
            bundleItemRequest.setItemAttributes(orderItemRequestDTO.getItemAttributes());
            bundleItemRequest.setName(product.getName());
            bundleItemRequest.setOrder(order);
            bundleItemRequest.setSalePriceOverride(orderItemRequestDTO.getOverrideSalePrice());
            bundleItemRequest.setRetailPriceOverride(orderItemRequestDTO.getOverrideRetailPrice());
            item = orderItemService.createBundleOrderItem(bundleItemRequest);
        }

        item = orderItemService.saveOrderItem(item);
        order.getOrderItems().add(item);
        order = orderService.save(order, false);

        request.setOrder(order);
        request.setAddedOrderItem(item);
        return context;
    }

}

