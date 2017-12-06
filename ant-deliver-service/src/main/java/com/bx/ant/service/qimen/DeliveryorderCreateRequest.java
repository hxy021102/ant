package com.bx.ant.service.qimen;

/**
 * Created by john on 17/12/5.
 */
import com.qimen.api.QimenRequest;
import com.qimen.api.response.DeliveryorderCreateResponse;
import com.taobao.api.ApiRuleException;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;
import com.taobao.api.internal.mapping.ApiListType;
import java.util.List;
import java.util.Map;

public class DeliveryorderCreateRequest extends QimenRequest<DeliveryorderCreateResponse> {
    private DeliveryorderCreateRequest.DeliveryOrder deliveryOrder;
    private Map extendProps;
    private List<DeliveryorderCreateRequest.Item> items;
    @ApiListField("orderLines")
    @ApiField("orderLine")
    private List<DeliveryorderCreateRequest.OrderLine> orderLines;

    public DeliveryorderCreateRequest() {
    }

    public void setDeliveryOrder(DeliveryorderCreateRequest.DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public DeliveryorderCreateRequest.DeliveryOrder getDeliveryOrder() {
        return this.deliveryOrder;
    }

    public void setExtendProps(Map extendProps) {
        this.extendProps = extendProps;
    }

    public Map getExtendProps() {
        return this.extendProps;
    }

    public void setItems(List<DeliveryorderCreateRequest.Item> items) {
        this.items = items;
    }

    public List<DeliveryorderCreateRequest.Item> getItems() {
        return this.items;
    }

    public void setOrderLines(List<DeliveryorderCreateRequest.OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public List<DeliveryorderCreateRequest.OrderLine> getOrderLines() {
        return this.orderLines;
    }

    public String getApiMethodName() {
        return "taobao.qimen.deliveryorder.create";
    }

    public Class<DeliveryorderCreateResponse> getResponseClass() {
        return DeliveryorderCreateResponse.class;
    }

    public void check() throws ApiRuleException {
    }

    public static class DeliveryOrder {
        @ApiField("actualAmount")
        private String actualAmount;
        @ApiField("arAmount")
        private String arAmount;
        @ApiField("batchCode")
        private String batchCode;
        @ApiField("businessMemo")
        private String businessMemo;
        @ApiField("buyerMessage")
        private String buyerMessage;
        @ApiField("buyerName")
        private String buyerName;
        @ApiField("buyerNick")
        private String buyerNick;
        @ApiField("buyerPhone")
        private String buyerPhone;
        @ApiField("collectedAmount")
        private String collectedAmount;
        @ApiField("confirmType")
        private String confirmType;
        @ApiField("createTime")
        private String createTime;
        @ApiField("declaredAmount")
        private String declaredAmount;
        @ApiField("deliveryNote")
        private String deliveryNote;
        @ApiField("deliveryOrderCode")
        private String deliveryOrderCode;
        @ApiField("deliveryRequirements")
        private DeliveryorderCreateRequest.DeliveryRequirements deliveryRequirements;
        @ApiField("discountAmount")
        private String discountAmount;
        @ApiField("exceptionCode")
        private String exceptionCode;
        @ApiField("expressCode")
        private String expressCode;
        @ApiField("fetchItemLocation")
        private String fetchItemLocation;
        @ApiField("freight")
        private String freight;
        @ApiField("gotAmount")
        private String gotAmount;
        @ApiField("identifyCode")
        private String identifyCode;
        @ApiField("insurance")
        private DeliveryorderCreateRequest.Insurance insurance;
        @ApiField("insuranceFlag")
        private String insuranceFlag;
        @ApiField("invoiceFlag")
        private String invoiceFlag;
        @ApiListField("invoices")
        @ApiField("invoice")
        private List<DeliveryorderCreateRequest.Invoice> invoices;
        @ApiField("isCod")
        private String isCod;
        @ApiField("isPaymentCollected")
        private String isPaymentCollected;
        @ApiField("isUrgency")
        private String isUrgency;
        @ApiField("isValueDeclared")
        private String isValueDeclared;
        @ApiField("itemAmount")
        private String itemAmount;
        @ApiField("itemCode")
        private String itemCode;
        @ApiField("itemName")
        private String itemName;
        @ApiListField("items")
        @ApiField("item")
        private List<DeliveryorderCreateRequest.Item> items;
        @ApiField("lineNumber")
        private String lineNumber;
        @ApiField("logisticsAreaCode")
        private String logisticsAreaCode;
        @ApiField("logisticsCode")
        private String logisticsCode;
        @ApiField("logisticsName")
        private String logisticsName;
        @ApiField("maxArrivalTime")
        private String maxArrivalTime;
        @ApiField("mergeOrderCodes")
        private String mergeOrderCodes;
        @ApiField("mergeOrderFlag")
        private String mergeOrderFlag;
        @ApiField("minArrivalTime")
        private String minArrivalTime;
        @ApiField("modifiedTime")
        private String modifiedTime;
        @ApiField("noStackTag")
        private String noStackTag;
        @ApiField("operateTime")
        private String operateTime;
        @ApiField("operatorCode")
        private String operatorCode;
        @ApiField("operatorName")
        private String operatorName;
        @ApiField("orderConfirmTime")
        private String orderConfirmTime;
        @ApiField("orderFlag")
        private String orderFlag;
        @ApiListField("orderLines")
        @ApiField("orderLine")
        private List<DeliveryorderCreateRequest.OrderLine> orderLines;
        @ApiField("orderNote")
        private String orderNote;
        @ApiField("orderSourceCode")
        private String orderSourceCode;
        @ApiField("orderStatus")
        private String orderStatus;
        @ApiField("orderType")
        private String orderType;
        @ApiField("outBizCode")
        private String outBizCode;
        @ApiField("ownerCode")
        private String ownerCode;
        @ApiField("packCode")
        private String packCode;
        @ApiListField("packages")
        @ApiField("package")
        private List<DeliveryorderCreateRequest.Package> packages;
        @ApiField("payMethod")
        private String payMethod;
        @ApiField("payNo")
        private String payNo;
        @ApiField("payTime")
        private String payTime;
        @ApiField("personalOrderNote")
        private String personalOrderNote;
        @ApiField("personalPackageNote")
        private String personalPackageNote;
        @ApiField("pickerInfo")
        private DeliveryorderCreateRequest.PickerInfo pickerInfo;
        @ApiField("placeOrderTime")
        private String placeOrderTime;
        @ApiField("planArrivalTime")
        private String planArrivalTime;
        @ApiField("planDeliveryDate")
        private String planDeliveryDate;
        @ApiField("preDeliveryOrderCode")
        private String preDeliveryOrderCode;
        @ApiField("preDeliveryOrderId")
        private String preDeliveryOrderId;
        @ApiField("presaleOrderType")
        private String presaleOrderType;
        @ApiField("price")
        private String price;
        @ApiField("priorityCode")
        private String priorityCode;
        @ApiField("produceDate")
        private String produceDate;
        @ApiField("quantity")
        private String quantity;
        @ApiField("receiveOrderTime")
        private String receiveOrderTime;
        @ApiField("receiverInfo")
        private DeliveryorderCreateRequest.ReceiverInfo receiverInfo;
        @ApiListField("relatedOrders")
        @ApiField("relatedOrder")
        private List<DeliveryorderCreateRequest.RelatedOrder> relatedOrders;
        @ApiField("remark")
        private String remark;
        @ApiField("salesModel")
        private String salesModel;
        @ApiField("scheduleDate")
        private String scheduleDate;
        @ApiField("sellerId")
        private String sellerId;
        @ApiField("sellerMessage")
        private String sellerMessage;
        @ApiField("sellerNick")
        private String sellerNick;
        @ApiField("senderInfo")
        private DeliveryorderCreateRequest.SenderInfo senderInfo;
        @ApiField("serviceCode")
        private String serviceCode;
        @ApiField("serviceFee")
        private String serviceFee;
        @ApiField("shelfLife")
        private String shelfLife;
        @ApiField("shopCode")
        private String shopCode;
        @ApiField("shopName")
        private String shopName;
        @ApiField("shopNick")
        private String shopNick;
        @ApiField("sourceOrderCode")
        private String sourceOrderCode;
        @ApiField("sourcePlatformCode")
        private String sourcePlatformCode;
        @ApiField("sourcePlatformName")
        private String sourcePlatformName;
        @ApiField("status")
        private String status;
        @ApiField("storageFee")
        private String storageFee;
        @ApiField("supplierCode")
        private String supplierCode;
        @ApiField("supplierName")
        private String supplierName;
        @ApiField("totalAmount")
        private String totalAmount;
        @ApiField("totalOrderLines")
        private String totalOrderLines;
        @ApiField("transportMode")
        private String transportMode;
        @ApiField("transpostSum")
        private String transpostSum;
        @ApiField("uomCode")
        private String uomCode;
        @ApiField("warehouseAddressCode")
        private String warehouseAddressCode;
        @ApiField("warehouseCode")
        private String warehouseCode;

        public DeliveryOrder() {
        }

        public String getActualAmount() {
            return this.actualAmount;
        }

        public void setActualAmount(String actualAmount) {
            this.actualAmount = actualAmount;
        }

        public String getArAmount() {
            return this.arAmount;
        }

        public void setArAmount(String arAmount) {
            this.arAmount = arAmount;
        }

        public String getBatchCode() {
            return this.batchCode;
        }

        public void setBatchCode(String batchCode) {
            this.batchCode = batchCode;
        }

        public String getBusinessMemo() {
            return this.businessMemo;
        }

        public void setBusinessMemo(String businessMemo) {
            this.businessMemo = businessMemo;
        }

        public String getBuyerMessage() {
            return this.buyerMessage;
        }

        public void setBuyerMessage(String buyerMessage) {
            this.buyerMessage = buyerMessage;
        }

        public String getBuyerName() {
            return this.buyerName;
        }

        public void setBuyerName(String buyerName) {
            this.buyerName = buyerName;
        }

        public String getBuyerNick() {
            return this.buyerNick;
        }

        public void setBuyerNick(String buyerNick) {
            this.buyerNick = buyerNick;
        }

        public String getBuyerPhone() {
            return this.buyerPhone;
        }

        public void setBuyerPhone(String buyerPhone) {
            this.buyerPhone = buyerPhone;
        }

        public String getCollectedAmount() {
            return this.collectedAmount;
        }

        public void setCollectedAmount(String collectedAmount) {
            this.collectedAmount = collectedAmount;
        }

        public String getConfirmType() {
            return this.confirmType;
        }

        public void setConfirmType(String confirmType) {
            this.confirmType = confirmType;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeclaredAmount() {
            return this.declaredAmount;
        }

        public void setDeclaredAmount(String declaredAmount) {
            this.declaredAmount = declaredAmount;
        }

        public String getDeliveryNote() {
            return this.deliveryNote;
        }

        public void setDeliveryNote(String deliveryNote) {
            this.deliveryNote = deliveryNote;
        }

        public String getDeliveryOrderCode() {
            return this.deliveryOrderCode;
        }

        public void setDeliveryOrderCode(String deliveryOrderCode) {
            this.deliveryOrderCode = deliveryOrderCode;
        }

        public DeliveryorderCreateRequest.DeliveryRequirements getDeliveryRequirements() {
            return this.deliveryRequirements;
        }

        public void setDeliveryRequirements(DeliveryorderCreateRequest.DeliveryRequirements deliveryRequirements) {
            this.deliveryRequirements = deliveryRequirements;
        }

        public String getDiscountAmount() {
            return this.discountAmount;
        }

        public void setDiscountAmount(String discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getExceptionCode() {
            return this.exceptionCode;
        }

        public void setExceptionCode(String exceptionCode) {
            this.exceptionCode = exceptionCode;
        }

        public String getExpressCode() {
            return this.expressCode;
        }

        public void setExpressCode(String expressCode) {
            this.expressCode = expressCode;
        }

        public String getFetchItemLocation() {
            return this.fetchItemLocation;
        }

        public void setFetchItemLocation(String fetchItemLocation) {
            this.fetchItemLocation = fetchItemLocation;
        }

        public String getFreight() {
            return this.freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getGotAmount() {
            return this.gotAmount;
        }

        public void setGotAmount(String gotAmount) {
            this.gotAmount = gotAmount;
        }

        public String getIdentifyCode() {
            return this.identifyCode;
        }

        public void setIdentifyCode(String identifyCode) {
            this.identifyCode = identifyCode;
        }

        public DeliveryorderCreateRequest.Insurance getInsurance() {
            return this.insurance;
        }

        public void setInsurance(DeliveryorderCreateRequest.Insurance insurance) {
            this.insurance = insurance;
        }

        public String getInsuranceFlag() {
            return this.insuranceFlag;
        }

        public void setInsuranceFlag(String insuranceFlag) {
            this.insuranceFlag = insuranceFlag;
        }

        public String getInvoiceFlag() {
            return this.invoiceFlag;
        }

        public void setInvoiceFlag(String invoiceFlag) {
            this.invoiceFlag = invoiceFlag;
        }

        public List<DeliveryorderCreateRequest.Invoice> getInvoices() {
            return this.invoices;
        }

        public void setInvoices(List<DeliveryorderCreateRequest.Invoice> invoices) {
            this.invoices = invoices;
        }

        public String getIsCod() {
            return this.isCod;
        }

        public void setIsCod(String isCod) {
            this.isCod = isCod;
        }

        public String getIsPaymentCollected() {
            return this.isPaymentCollected;
        }

        public void setIsPaymentCollected(String isPaymentCollected) {
            this.isPaymentCollected = isPaymentCollected;
        }

        public String getIsUrgency() {
            return this.isUrgency;
        }

        public void setIsUrgency(String isUrgency) {
            this.isUrgency = isUrgency;
        }

        public String getIsValueDeclared() {
            return this.isValueDeclared;
        }

        public void setIsValueDeclared(String isValueDeclared) {
            this.isValueDeclared = isValueDeclared;
        }

        public String getItemAmount() {
            return this.itemAmount;
        }

        public void setItemAmount(String itemAmount) {
            this.itemAmount = itemAmount;
        }

        public String getItemCode() {
            return this.itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemName() {
            return this.itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public List<DeliveryorderCreateRequest.Item> getItems() {
            return this.items;
        }

        public void setItems(List<DeliveryorderCreateRequest.Item> items) {
            this.items = items;
        }

        public String getLineNumber() {
            return this.lineNumber;
        }

        public void setLineNumber(String lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getLogisticsAreaCode() {
            return this.logisticsAreaCode;
        }

        public void setLogisticsAreaCode(String logisticsAreaCode) {
            this.logisticsAreaCode = logisticsAreaCode;
        }

        public String getLogisticsCode() {
            return this.logisticsCode;
        }

        public void setLogisticsCode(String logisticsCode) {
            this.logisticsCode = logisticsCode;
        }

        public String getLogisticsName() {
            return this.logisticsName;
        }

        public void setLogisticsName(String logisticsName) {
            this.logisticsName = logisticsName;
        }

        public String getMaxArrivalTime() {
            return this.maxArrivalTime;
        }

        public void setMaxArrivalTime(String maxArrivalTime) {
            this.maxArrivalTime = maxArrivalTime;
        }

        public String getMergeOrderCodes() {
            return this.mergeOrderCodes;
        }

        public void setMergeOrderCodes(String mergeOrderCodes) {
            this.mergeOrderCodes = mergeOrderCodes;
        }

        public String getMergeOrderFlag() {
            return this.mergeOrderFlag;
        }

        public void setMergeOrderFlag(String mergeOrderFlag) {
            this.mergeOrderFlag = mergeOrderFlag;
        }

        public String getMinArrivalTime() {
            return this.minArrivalTime;
        }

        public void setMinArrivalTime(String minArrivalTime) {
            this.minArrivalTime = minArrivalTime;
        }

        public String getModifiedTime() {
            return this.modifiedTime;
        }

        public void setModifiedTime(String modifiedTime) {
            this.modifiedTime = modifiedTime;
        }

        public String getNoStackTag() {
            return this.noStackTag;
        }

        public void setNoStackTag(String noStackTag) {
            this.noStackTag = noStackTag;
        }

        public String getOperateTime() {
            return this.operateTime;
        }

        public void setOperateTime(String operateTime) {
            this.operateTime = operateTime;
        }

        public String getOperatorCode() {
            return this.operatorCode;
        }

        public void setOperatorCode(String operatorCode) {
            this.operatorCode = operatorCode;
        }

        public String getOperatorName() {
            return this.operatorName;
        }

        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }

        public String getOrderConfirmTime() {
            return this.orderConfirmTime;
        }

        public void setOrderConfirmTime(String orderConfirmTime) {
            this.orderConfirmTime = orderConfirmTime;
        }

        public String getOrderFlag() {
            return this.orderFlag;
        }

        public void setOrderFlag(String orderFlag) {
            this.orderFlag = orderFlag;
        }

        public List<DeliveryorderCreateRequest.OrderLine> getOrderLines() {
            return this.orderLines;
        }

        public void setOrderLines(List<DeliveryorderCreateRequest.OrderLine> orderLines) {
            this.orderLines = orderLines;
        }

        public String getOrderNote() {
            return this.orderNote;
        }

        public void setOrderNote(String orderNote) {
            this.orderNote = orderNote;
        }

        public String getOrderSourceCode() {
            return this.orderSourceCode;
        }

        public void setOrderSourceCode(String orderSourceCode) {
            this.orderSourceCode = orderSourceCode;
        }

        public String getOrderStatus() {
            return this.orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderType() {
            return this.orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOutBizCode() {
            return this.outBizCode;
        }

        public void setOutBizCode(String outBizCode) {
            this.outBizCode = outBizCode;
        }

        public String getOwnerCode() {
            return this.ownerCode;
        }

        public void setOwnerCode(String ownerCode) {
            this.ownerCode = ownerCode;
        }

        public String getPackCode() {
            return this.packCode;
        }

        public void setPackCode(String packCode) {
            this.packCode = packCode;
        }

        public List<DeliveryorderCreateRequest.Package> getPackages() {
            return this.packages;
        }

        public void setPackages(List<DeliveryorderCreateRequest.Package> packages) {
            this.packages = packages;
        }

        public String getPayMethod() {
            return this.payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public String getPayNo() {
            return this.payNo;
        }

        public void setPayNo(String payNo) {
            this.payNo = payNo;
        }

        public String getPayTime() {
            return this.payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getPersonalOrderNote() {
            return this.personalOrderNote;
        }

        public void setPersonalOrderNote(String personalOrderNote) {
            this.personalOrderNote = personalOrderNote;
        }

        public String getPersonalPackageNote() {
            return this.personalPackageNote;
        }

        public void setPersonalPackageNote(String personalPackageNote) {
            this.personalPackageNote = personalPackageNote;
        }

        public DeliveryorderCreateRequest.PickerInfo getPickerInfo() {
            return this.pickerInfo;
        }

        public void setPickerInfo(DeliveryorderCreateRequest.PickerInfo pickerInfo) {
            this.pickerInfo = pickerInfo;
        }

        public String getPlaceOrderTime() {
            return this.placeOrderTime;
        }

        public void setPlaceOrderTime(String placeOrderTime) {
            this.placeOrderTime = placeOrderTime;
        }

        public String getPlanArrivalTime() {
            return this.planArrivalTime;
        }

        public void setPlanArrivalTime(String planArrivalTime) {
            this.planArrivalTime = planArrivalTime;
        }

        public String getPlanDeliveryDate() {
            return this.planDeliveryDate;
        }

        public void setPlanDeliveryDate(String planDeliveryDate) {
            this.planDeliveryDate = planDeliveryDate;
        }

        public String getPreDeliveryOrderCode() {
            return this.preDeliveryOrderCode;
        }

        public void setPreDeliveryOrderCode(String preDeliveryOrderCode) {
            this.preDeliveryOrderCode = preDeliveryOrderCode;
        }

        public String getPreDeliveryOrderId() {
            return this.preDeliveryOrderId;
        }

        public void setPreDeliveryOrderId(String preDeliveryOrderId) {
            this.preDeliveryOrderId = preDeliveryOrderId;
        }

        public String getPresaleOrderType() {
            return this.presaleOrderType;
        }

        public void setPresaleOrderType(String presaleOrderType) {
            this.presaleOrderType = presaleOrderType;
        }

        public String getPrice() {
            return this.price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPriorityCode() {
            return this.priorityCode;
        }

        public void setPriorityCode(String priorityCode) {
            this.priorityCode = priorityCode;
        }

        public String getProduceDate() {
            return this.produceDate;
        }

        public void setProduceDate(String produceDate) {
            this.produceDate = produceDate;
        }

        public String getQuantity() {
            return this.quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getReceiveOrderTime() {
            return this.receiveOrderTime;
        }

        public void setReceiveOrderTime(String receiveOrderTime) {
            this.receiveOrderTime = receiveOrderTime;
        }

        public DeliveryorderCreateRequest.ReceiverInfo getReceiverInfo() {
            return this.receiverInfo;
        }

        public void setReceiverInfo(DeliveryorderCreateRequest.ReceiverInfo receiverInfo) {
            this.receiverInfo = receiverInfo;
        }

        public List<DeliveryorderCreateRequest.RelatedOrder> getRelatedOrders() {
            return this.relatedOrders;
        }

        public void setRelatedOrders(List<DeliveryorderCreateRequest.RelatedOrder> relatedOrders) {
            this.relatedOrders = relatedOrders;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSalesModel() {
            return this.salesModel;
        }

        public void setSalesModel(String salesModel) {
            this.salesModel = salesModel;
        }

        public String getScheduleDate() {
            return this.scheduleDate;
        }

        public void setScheduleDate(String scheduleDate) {
            this.scheduleDate = scheduleDate;
        }

        public String getSellerId() {
            return this.sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public String getSellerMessage() {
            return this.sellerMessage;
        }

        public void setSellerMessage(String sellerMessage) {
            this.sellerMessage = sellerMessage;
        }

        public String getSellerNick() {
            return this.sellerNick;
        }

        public void setSellerNick(String sellerNick) {
            this.sellerNick = sellerNick;
        }

        public DeliveryorderCreateRequest.SenderInfo getSenderInfo() {
            return this.senderInfo;
        }

        public void setSenderInfo(DeliveryorderCreateRequest.SenderInfo senderInfo) {
            this.senderInfo = senderInfo;
        }

        public String getServiceCode() {
            return this.serviceCode;
        }

        public void setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public String getServiceFee() {
            return this.serviceFee;
        }

        public void setServiceFee(String serviceFee) {
            this.serviceFee = serviceFee;
        }

        public String getShelfLife() {
            return this.shelfLife;
        }

        public void setShelfLife(String shelfLife) {
            this.shelfLife = shelfLife;
        }

        public String getShopCode() {
            return this.shopCode;
        }

        public void setShopCode(String shopCode) {
            this.shopCode = shopCode;
        }

        public String getShopName() {
            return this.shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopNick() {
            return this.shopNick;
        }

        public void setShopNick(String shopNick) {
            this.shopNick = shopNick;
        }

        public String getSourceOrderCode() {
            return this.sourceOrderCode;
        }

        public void setSourceOrderCode(String sourceOrderCode) {
            this.sourceOrderCode = sourceOrderCode;
        }

        public String getSourcePlatformCode() {
            return this.sourcePlatformCode;
        }

        public void setSourcePlatformCode(String sourcePlatformCode) {
            this.sourcePlatformCode = sourcePlatformCode;
        }

        public String getSourcePlatformName() {
            return this.sourcePlatformName;
        }

        public void setSourcePlatformName(String sourcePlatformName) {
            this.sourcePlatformName = sourcePlatformName;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStorageFee() {
            return this.storageFee;
        }

        public void setStorageFee(String storageFee) {
            this.storageFee = storageFee;
        }

        public String getSupplierCode() {
            return this.supplierCode;
        }

        public void setSupplierCode(String supplierCode) {
            this.supplierCode = supplierCode;
        }

        public String getSupplierName() {
            return this.supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getTotalAmount() {
            return this.totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getTotalOrderLines() {
            return this.totalOrderLines;
        }

        public void setTotalOrderLines(String totalOrderLines) {
            this.totalOrderLines = totalOrderLines;
        }

        public String getTransportMode() {
            return this.transportMode;
        }

        public void setTransportMode(String transportMode) {
            this.transportMode = transportMode;
        }

        public String getTranspostSum() {
            return this.transpostSum;
        }

        public void setTranspostSum(String transpostSum) {
            this.transpostSum = transpostSum;
        }

        public String getUomCode() {
            return this.uomCode;
        }

        public void setUomCode(String uomCode) {
            this.uomCode = uomCode;
        }

        public String getWarehouseAddressCode() {
            return this.warehouseAddressCode;
        }

        public void setWarehouseAddressCode(String warehouseAddressCode) {
            this.warehouseAddressCode = warehouseAddressCode;
        }

        public String getWarehouseCode() {
            return this.warehouseCode;
        }

        public void setWarehouseCode(String warehouseCode) {
            this.warehouseCode = warehouseCode;
        }
    }

    @ApiListType("relatedOrder")
    public static class RelatedOrder {
        @ApiField("orderCode")
        private String orderCode;
        @ApiField("orderType")
        private String orderType;
        @ApiField("remark")
        private String remark;

        public RelatedOrder() {
        }

        public String getOrderCode() {
            return this.orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getOrderType() {
            return this.orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    @ApiListType("package")
    public static class Package {
        @ApiField("expressCode")
        private String expressCode;
        @ApiField("height")
        private String height;
        @ApiField("invoiceNo")
        private String invoiceNo;
        @ApiListField("items")
        @ApiField("item")
        private List<DeliveryorderCreateRequest.Item> items;
        @ApiField("length")
        private String length;
        @ApiField("logisticsCode")
        private String logisticsCode;
        @ApiField("logisticsName")
        private String logisticsName;
        @ApiField("packageCode")
        private String packageCode;
        @ApiListField("packageMaterialList")
        @ApiField("packageMaterial")
        private List<DeliveryorderCreateRequest.PackageMaterial> packageMaterialList;
        @ApiField("remark")
        private String remark;
        @ApiField("theoreticalWeight")
        private String theoreticalWeight;
        @ApiField("volume")
        private String volume;
        @ApiField("weight")
        private String weight;
        @ApiField("width")
        private String width;

        public Package() {
        }

        public String getExpressCode() {
            return this.expressCode;
        }

        public void setExpressCode(String expressCode) {
            this.expressCode = expressCode;
        }

        public String getHeight() {
            return this.height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getInvoiceNo() {
            return this.invoiceNo;
        }

        public void setInvoiceNo(String invoiceNo) {
            this.invoiceNo = invoiceNo;
        }

        public List<DeliveryorderCreateRequest.Item> getItems() {
            return this.items;
        }

        public void setItems(List<DeliveryorderCreateRequest.Item> items) {
            this.items = items;
        }

        public String getLength() {
            return this.length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getLogisticsCode() {
            return this.logisticsCode;
        }

        public void setLogisticsCode(String logisticsCode) {
            this.logisticsCode = logisticsCode;
        }

        public String getLogisticsName() {
            return this.logisticsName;
        }

        public void setLogisticsName(String logisticsName) {
            this.logisticsName = logisticsName;
        }

        public String getPackageCode() {
            return this.packageCode;
        }

        public void setPackageCode(String packageCode) {
            this.packageCode = packageCode;
        }

        public List<DeliveryorderCreateRequest.PackageMaterial> getPackageMaterialList() {
            return this.packageMaterialList;
        }

        public void setPackageMaterialList(List<DeliveryorderCreateRequest.PackageMaterial> packageMaterialList) {
            this.packageMaterialList = packageMaterialList;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTheoreticalWeight() {
            return this.theoreticalWeight;
        }

        public void setTheoreticalWeight(String theoreticalWeight) {
            this.theoreticalWeight = theoreticalWeight;
        }

        public String getVolume() {
            return this.volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getWeight() {
            return this.weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getWidth() {
            return this.width;
        }

        public void setWidth(String width) {
            this.width = width;
        }
    }

    @ApiListType("packageMaterial")
    public static class PackageMaterial {
        @ApiField("quantity")
        private String quantity;
        @ApiField("remark")
        private String remark;
        @ApiField("type")
        private String type;

        public PackageMaterial() {
        }

        public String getQuantity() {
            return this.quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    @ApiListType("orderLine")
    public static class OrderLine {
        @ApiField("actualPrice")
        private String actualPrice;
        @ApiField("actualQty")
        private String actualQty;
        @ApiField("amount")
        private String amount;
        @ApiField("batchCode")
        private String batchCode;
        @ApiListField("batchs")
        @ApiField("batch")
        private List<DeliveryorderCreateRequest.Batch> batchs;
        @ApiField("color")
        private String color;
        @ApiField("deliveryOrderId")
        private String deliveryOrderId;
        @ApiField("discount")
        private String discount;
        @ApiField("discountAmount")
        private String discountAmount;
        @ApiField("discountPrice")
        private String discountPrice;
        @ApiField("exceptionQty")
        private String exceptionQty;
        @ApiField("expireDate")
        private String expireDate;
        @ApiField("extCode")
        private String extCode;
        @ApiField("inventoryType")
        private String inventoryType;
        @ApiField("itemCode")
        private String itemCode;
        @ApiField("itemId")
        private String itemId;
        @ApiField("itemName")
        private String itemName;
        @ApiField("locationCode")
        private String locationCode;
        @ApiField("moveInLocation")
        private String moveInLocation;
        @ApiField("moveOutLocation")
        private String moveOutLocation;
        @ApiField("orderLineNo")
        private String orderLineNo;
        @ApiField("orderSourceCode")
        private String orderSourceCode;
        @ApiField("outBizCode")
        private String outBizCode;
        @ApiField("ownerCode")
        private String ownerCode;
        @ApiField("payNo")
        private String payNo;
        @ApiField("planQty")
        private String planQty;
        @ApiField("produceCode")
        private String produceCode;
        @ApiField("productCode")
        private String productCode;
        @ApiField("productDate")
        private String productDate;
        @ApiField("purchasePrice")
        private String purchasePrice;
        @ApiField("qrCode")
        private String qrCode;
        @ApiField("quantity")
        private String quantity;
        @ApiField("referencePrice")
        private String referencePrice;
        @ApiField("remark")
        private String remark;
        @ApiField("retailPrice")
        private String retailPrice;
        @ApiField("settlementAmount")
        private String settlementAmount;
        @ApiField("size")
        private String size;
        @ApiField("skuProperty")
        private String skuProperty;
        @ApiField("sourceOrderCode")
        private String sourceOrderCode;
        @ApiField("standardAmount")
        private String standardAmount;
        @ApiField("standardPrice")
        private String standardPrice;
        @ApiField("status")
        private String status;
        @ApiField("stockInQty")
        private String stockInQty;
        @ApiField("stockOutQty")
        private String stockOutQty;
        @ApiField("subDeliveryOrderId")
        private String subDeliveryOrderId;
        @ApiField("subSourceCode")
        private String subSourceCode;
        @ApiField("subSourceOrderCode")
        private String subSourceOrderCode;
        @ApiField("taobaoItemCode")
        private String taobaoItemCode;
        @ApiField("warehouseCode")
        private String warehouseCode;

        public OrderLine() {
        }

        public String getActualPrice() {
            return this.actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getActualQty() {
            return this.actualQty;
        }

        public void setActualQty(String actualQty) {
            this.actualQty = actualQty;
        }

        public String getAmount() {
            return this.amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getBatchCode() {
            return this.batchCode;
        }

        public void setBatchCode(String batchCode) {
            this.batchCode = batchCode;
        }

        public List<DeliveryorderCreateRequest.Batch> getBatchs() {
            return this.batchs;
        }

        public void setBatchs(List<DeliveryorderCreateRequest.Batch> batchs) {
            this.batchs = batchs;
        }

        public String getColor() {
            return this.color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getDeliveryOrderId() {
            return this.deliveryOrderId;
        }

        public void setDeliveryOrderId(String deliveryOrderId) {
            this.deliveryOrderId = deliveryOrderId;
        }

        public String getDiscount() {
            return this.discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscountAmount() {
            return this.discountAmount;
        }

        public void setDiscountAmount(String discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getDiscountPrice() {
            return this.discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getExceptionQty() {
            return this.exceptionQty;
        }

        public void setExceptionQty(String exceptionQty) {
            this.exceptionQty = exceptionQty;
        }

        public String getExpireDate() {
            return this.expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }

        public String getExtCode() {
            return this.extCode;
        }

        public void setExtCode(String extCode) {
            this.extCode = extCode;
        }

        public String getInventoryType() {
            return this.inventoryType;
        }

        public void setInventoryType(String inventoryType) {
            this.inventoryType = inventoryType;
        }

        public String getItemCode() {
            return this.itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemId() {
            return this.itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return this.itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getLocationCode() {
            return this.locationCode;
        }

        public void setLocationCode(String locationCode) {
            this.locationCode = locationCode;
        }

        public String getMoveInLocation() {
            return this.moveInLocation;
        }

        public void setMoveInLocation(String moveInLocation) {
            this.moveInLocation = moveInLocation;
        }

        public String getMoveOutLocation() {
            return this.moveOutLocation;
        }

        public void setMoveOutLocation(String moveOutLocation) {
            this.moveOutLocation = moveOutLocation;
        }

        public String getOrderLineNo() {
            return this.orderLineNo;
        }

        public void setOrderLineNo(String orderLineNo) {
            this.orderLineNo = orderLineNo;
        }

        public String getOrderSourceCode() {
            return this.orderSourceCode;
        }

        public void setOrderSourceCode(String orderSourceCode) {
            this.orderSourceCode = orderSourceCode;
        }

        public String getOutBizCode() {
            return this.outBizCode;
        }

        public void setOutBizCode(String outBizCode) {
            this.outBizCode = outBizCode;
        }

        public String getOwnerCode() {
            return this.ownerCode;
        }

        public void setOwnerCode(String ownerCode) {
            this.ownerCode = ownerCode;
        }

        public String getPayNo() {
            return this.payNo;
        }

        public void setPayNo(String payNo) {
            this.payNo = payNo;
        }

        public String getPlanQty() {
            return this.planQty;
        }

        public void setPlanQty(String planQty) {
            this.planQty = planQty;
        }

        public String getProduceCode() {
            return this.produceCode;
        }

        public void setProduceCode(String produceCode) {
            this.produceCode = produceCode;
        }

        public String getProductCode() {
            return this.productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductDate() {
            return this.productDate;
        }

        public void setProductDate(String productDate) {
            this.productDate = productDate;
        }

        public String getPurchasePrice() {
            return this.purchasePrice;
        }

        public void setPurchasePrice(String purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public String getQrCode() {
            return this.qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getQuantity() {
            return this.quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getReferencePrice() {
            return this.referencePrice;
        }

        public void setReferencePrice(String referencePrice) {
            this.referencePrice = referencePrice;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRetailPrice() {
            return this.retailPrice;
        }

        public void setRetailPrice(String retailPrice) {
            this.retailPrice = retailPrice;
        }

        public String getSettlementAmount() {
            return this.settlementAmount;
        }

        public void setSettlementAmount(String settlementAmount) {
            this.settlementAmount = settlementAmount;
        }

        public String getSize() {
            return this.size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getSkuProperty() {
            return this.skuProperty;
        }

        public void setSkuProperty(String skuProperty) {
            this.skuProperty = skuProperty;
        }

        public String getSourceOrderCode() {
            return this.sourceOrderCode;
        }

        public void setSourceOrderCode(String sourceOrderCode) {
            this.sourceOrderCode = sourceOrderCode;
        }

        public String getStandardAmount() {
            return this.standardAmount;
        }

        public void setStandardAmount(String standardAmount) {
            this.standardAmount = standardAmount;
        }

        public String getStandardPrice() {
            return this.standardPrice;
        }

        public void setStandardPrice(String standardPrice) {
            this.standardPrice = standardPrice;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStockInQty() {
            return this.stockInQty;
        }

        public void setStockInQty(String stockInQty) {
            this.stockInQty = stockInQty;
        }

        public String getStockOutQty() {
            return this.stockOutQty;
        }

        public void setStockOutQty(String stockOutQty) {
            this.stockOutQty = stockOutQty;
        }

        public String getSubDeliveryOrderId() {
            return this.subDeliveryOrderId;
        }

        public void setSubDeliveryOrderId(String subDeliveryOrderId) {
            this.subDeliveryOrderId = subDeliveryOrderId;
        }

        public String getSubSourceCode() {
            return this.subSourceCode;
        }

        public void setSubSourceCode(String subSourceCode) {
            this.subSourceCode = subSourceCode;
        }

        public String getSubSourceOrderCode() {
            return this.subSourceOrderCode;
        }

        public void setSubSourceOrderCode(String subSourceOrderCode) {
            this.subSourceOrderCode = subSourceOrderCode;
        }

        public String getTaobaoItemCode() {
            return this.taobaoItemCode;
        }

        public void setTaobaoItemCode(String taobaoItemCode) {
            this.taobaoItemCode = taobaoItemCode;
        }

        public String getWarehouseCode() {
            return this.warehouseCode;
        }

        public void setWarehouseCode(String warehouseCode) {
            this.warehouseCode = warehouseCode;
        }
    }

    public static class PickerInfo {
        @ApiField("area")
        private String area;
        @ApiField("birthDate")
        private String birthDate;
        @ApiField("carNo")
        private String carNo;
        @ApiField("career")
        private String career;
        @ApiField("city")
        private String city;
        @ApiField("company")
        private String company;
        @ApiField("countryCode")
        private String countryCode;
        @ApiField("countryCodeCiq")
        private String countryCodeCiq;
        @ApiField("countryCodeCus")
        private String countryCodeCus;
        @ApiField("detailAddress")
        private String detailAddress;
        @ApiField("email")
        private String email;
        @ApiField("fax")
        private String fax;
        @ApiField("gender")
        private String gender;
        @ApiField("id")
        private String id;
        @ApiField("idNumber")
        private String idNumber;
        @ApiField("idType")
        private String idType;
        @ApiField("nick")
        private String nick;
        @ApiField("province")
        private String province;
        @ApiField("receiverMobile")
        private String receiverMobile;
        @ApiField("receiverName")
        private String receiverName;
        @ApiField("receiverTel")
        private String receiverTel;
        @ApiField("receiverZipCode")
        private String receiverZipCode;
        @ApiField("remark")
        private String remark;
        @ApiField("town")
        private String town;

        public PickerInfo() {
        }

        public String getArea() {
            return this.area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getBirthDate() {
            return this.birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getCarNo() {
            return this.carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getCareer() {
            return this.career;
        }

        public void setCareer(String career) {
            this.career = career;
        }

        public String getCity() {
            return this.city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCompany() {
            return this.company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCountryCode() {
            return this.countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryCodeCiq() {
            return this.countryCodeCiq;
        }

        public void setCountryCodeCiq(String countryCodeCiq) {
            this.countryCodeCiq = countryCodeCiq;
        }

        public String getCountryCodeCus() {
            return this.countryCodeCus;
        }

        public void setCountryCodeCus(String countryCodeCus) {
            this.countryCodeCus = countryCodeCus;
        }

        public String getDetailAddress() {
            return this.detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFax() {
            return this.fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getGender() {
            return this.gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdNumber() {
            return this.idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getIdType() {
            return this.idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getNick() {
            return this.nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getProvince() {
            return this.province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getReceiverMobile() {
            return this.receiverMobile;
        }

        public void setReceiverMobile(String receiverMobile) {
            this.receiverMobile = receiverMobile;
        }

        public String getReceiverName() {
            return this.receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiverTel() {
            return this.receiverTel;
        }

        public void setReceiverTel(String receiverTel) {
            this.receiverTel = receiverTel;
        }

        public String getReceiverZipCode() {
            return this.receiverZipCode;
        }

        public void setReceiverZipCode(String receiverZipCode) {
            this.receiverZipCode = receiverZipCode;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTown() {
            return this.town;
        }

        public void setTown(String town) {
            this.town = town;
        }
    }

    public static class Insurance {
        @ApiField("amount")
        private String amount;
        @ApiField("type")
        private String type;

        public Insurance() {
        }

        public String getAmount() {
            return this.amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Invoice {
        @ApiField("amount")
        private String amount;
        @ApiField("code")
        private String code;
        @ApiField("content")
        private String content;
        @ApiField("detail")
        private DeliveryorderCreateRequest.Detail detail;
        @ApiField("header")
        private String header;
        @ApiField("invoiceAmount")
        private String invoiceAmount;
        @ApiField("invoiceContent")
        private String invoiceContent;
        @ApiField("invoiceHead")
        private String invoiceHead;
        @ApiField("number")
        private String number;
        @ApiField("remark")
        private String remark;
        @ApiField("taxNumber")
        private String taxNumber;
        @ApiField("type")
        private String type;

        public Invoice() {
        }

        public String getAmount() {
            return this.amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public DeliveryorderCreateRequest.Detail getDetail() {
            return this.detail;
        }

        public void setDetail(DeliveryorderCreateRequest.Detail detail) {
            this.detail = detail;
        }

        public String getHeader() {
            return this.header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getInvoiceAmount() {
            return this.invoiceAmount;
        }

        public void setInvoiceAmount(String invoiceAmount) {
            this.invoiceAmount = invoiceAmount;
        }

        public String getInvoiceContent() {
            return this.invoiceContent;
        }

        public void setInvoiceContent(String invoiceContent) {
            this.invoiceContent = invoiceContent;
        }

        public String getInvoiceHead() {
            return this.invoiceHead;
        }

        public void setInvoiceHead(String invoiceHead) {
            this.invoiceHead = invoiceHead;
        }

        public String getNumber() {
            return this.number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTaxNumber() {
            return this.taxNumber;
        }

        public void setTaxNumber(String taxNumber) {
            this.taxNumber = taxNumber;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Detail {
        @ApiListField("items")
        @ApiField("item")
        private List<DeliveryorderCreateRequest.Item> items;

        public Detail() {
        }

        public List<DeliveryorderCreateRequest.Item> getItems() {
            return this.items;
        }

        public void setItems(List<DeliveryorderCreateRequest.Item> items) {
            this.items = items;
        }
    }

    public static class Item {
        @ApiField("actualAmount")
        private String actualAmount;
        @ApiField("actualQty")
        private String actualQty;
        @ApiField("adventLifecycle")
        private String adventLifecycle;
        @ApiField("amount")
        private String amount;
        @ApiField("approvalNumber")
        private String approvalNumber;
        @ApiField("barCode")
        private String barCode;
        @ApiField("batchCode")
        private String batchCode;
        @ApiField("batchRemark")
        private String batchRemark;
        @ApiListField("batchs")
        @ApiField("batch")
        private List<DeliveryorderCreateRequest.Batch> batchs;
        @ApiField("brandCode")
        private String brandCode;
        @ApiField("brandName")
        private String brandName;
        @ApiField("categoryId")
        private String categoryId;
        @ApiField("categoryName")
        private String categoryName;
        @ApiField("changeTime")
        private String changeTime;
        @ApiField("channelCode")
        private String channelCode;
        @ApiField("color")
        private String color;
        @ApiField("costPrice")
        private String costPrice;
        @ApiField("defectiveQty")
        private String defectiveQty;
        @ApiField("diffQuantity")
        private String diffQuantity;
        @ApiField("discount")
        private String discount;
        @ApiField("discountPrice")
        private String discountPrice;
        @ApiField("englishName")
        private String englishName;
        @ApiField("exCode")
        private String exCode;
        @ApiField("expireDate")
        private String expireDate;
        @ApiField("extCode")
        private String extCode;
        @ApiField("goodsCode")
        private String goodsCode;
        @ApiField("grossWeight")
        private String grossWeight;
        @ApiField("height")
        private String height;
        @ApiField("inventoryType")
        private String inventoryType;
        @ApiField("isAreaSale")
        private String isAreaSale;
        @ApiField("isBatchMgmt")
        private String isBatchMgmt;
        @ApiField("isFragile")
        private String isFragile;
        @ApiField("isHazardous")
        private String isHazardous;
        @ApiField("isSNMgmt")
        private String isSNMgmt;
        @ApiField("isShelfLifeMgmt")
        private String isShelfLifeMgmt;
        @ApiField("isSku")
        private String isSku;
        @ApiField("itemCode")
        private String itemCode;
        @ApiField("itemId")
        private String itemId;
        @ApiField("itemName")
        private String itemName;
        @ApiField("itemType")
        private String itemType;
        @ApiField("lackQty")
        private String lackQty;
        @ApiField("latestUpdateTime")
        private String latestUpdateTime;
        @ApiField("length")
        private String length;
        @ApiField("lockQuantity")
        private String lockQuantity;
        @ApiField("lockupLifecycle")
        private String lockupLifecycle;
        @ApiField("netWeight")
        private String netWeight;
        @ApiField("normalQty")
        private String normalQty;
        @ApiField("orderCode")
        private String orderCode;
        @ApiField("orderLineNo")
        private String orderLineNo;
        @ApiField("orderType")
        private String orderType;
        @ApiField("originAddress")
        private String originAddress;
        @ApiField("originCode")
        private String originCode;
        @ApiField("outBizCode")
        private String outBizCode;
        @ApiField("ownerCode")
        private String ownerCode;
        @ApiField("packCode")
        private String packCode;
        @ApiField("packageMaterial")
        private String packageMaterial;
        @ApiField("paperQty")
        private String paperQty;
        @ApiField("pcs")
        private String pcs;
        @ApiField("planQty")
        private String planQty;
        @ApiField("price")
        private String price;
        @ApiField("priceAdjustment")
        private DeliveryorderCreateRequest.PriceAdjustment priceAdjustment;
        @ApiField("pricingCategory")
        private String pricingCategory;
        @ApiField("produceCode")
        private String produceCode;
        @ApiField("productCode")
        private String productCode;
        @ApiField("productDate")
        private String productDate;
        @ApiField("purchasePrice")
        private String purchasePrice;
        @ApiField("quantity")
        private Long quantity;
        @ApiField("reason")
        private String reason;
        @ApiField("receiveQty")
        private String receiveQty;
        @ApiField("referencePrice")
        private String referencePrice;
        @ApiField("rejectLifecycle")
        private String rejectLifecycle;
        @ApiField("remark")
        private String remark;
        @ApiField("retailPrice")
        private String retailPrice;
        @ApiField("safetyStock")
        private String safetyStock;
        @ApiField("seasonCode")
        private String seasonCode;
        @ApiField("seasonName")
        private String seasonName;
        @ApiField("shelfLife")
        private String shelfLife;
        @ApiField("shortName")
        private String shortName;
        @ApiField("size")
        private String size;
        @ApiField("skuProperty")
        private String skuProperty;
        @ApiField("sn")
        private String sn;
        @ApiField("snCode")
        private String snCode;
        @ApiField("sourceOrderCode")
        private String sourceOrderCode;
        @ApiField("standardPrice")
        private String standardPrice;
        @ApiField("stockStatus")
        private String stockStatus;
        @ApiField("stockUnit")
        private String stockUnit;
        @ApiField("subSourceOrderCode")
        private String subSourceOrderCode;
        @ApiField("supplierCode")
        private String supplierCode;
        @ApiField("supplierName")
        private String supplierName;
        @ApiField("tagPrice")
        private String tagPrice;
        @ApiField("tareWeight")
        private String tareWeight;
        @ApiField("tempRequirement")
        private String tempRequirement;
        @ApiField("title")
        private String title;
        @ApiField("unit")
        private String unit;
        @ApiField("volume")
        private String volume;
        @ApiField("warehouseCode")
        private String warehouseCode;
        @ApiField("width")
        private String width;

        public Item() {
        }

        public String getActualAmount() {
            return this.actualAmount;
        }

        public void setActualAmount(String actualAmount) {
            this.actualAmount = actualAmount;
        }

        public String getActualQty() {
            return this.actualQty;
        }

        public void setActualQty(String actualQty) {
            this.actualQty = actualQty;
        }

        public String getAdventLifecycle() {
            return this.adventLifecycle;
        }

        public void setAdventLifecycle(String adventLifecycle) {
            this.adventLifecycle = adventLifecycle;
        }

        public String getAmount() {
            return this.amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getApprovalNumber() {
            return this.approvalNumber;
        }

        public void setApprovalNumber(String approvalNumber) {
            this.approvalNumber = approvalNumber;
        }

        public String getBarCode() {
            return this.barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public String getBatchCode() {
            return this.batchCode;
        }

        public void setBatchCode(String batchCode) {
            this.batchCode = batchCode;
        }

        public String getBatchRemark() {
            return this.batchRemark;
        }

        public void setBatchRemark(String batchRemark) {
            this.batchRemark = batchRemark;
        }

        public List<DeliveryorderCreateRequest.Batch> getBatchs() {
            return this.batchs;
        }

        public void setBatchs(List<DeliveryorderCreateRequest.Batch> batchs) {
            this.batchs = batchs;
        }

        public String getBrandCode() {
            return this.brandCode;
        }

        public void setBrandCode(String brandCode) {
            this.brandCode = brandCode;
        }

        public String getBrandName() {
            return this.brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getCategoryId() {
            return this.categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return this.categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getChangeTime() {
            return this.changeTime;
        }

        public void setChangeTime(String changeTime) {
            this.changeTime = changeTime;
        }

        public String getChannelCode() {
            return this.channelCode;
        }

        public void setChannelCode(String channelCode) {
            this.channelCode = channelCode;
        }

        public String getColor() {
            return this.color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getCostPrice() {
            return this.costPrice;
        }

        public void setCostPrice(String costPrice) {
            this.costPrice = costPrice;
        }

        public String getDefectiveQty() {
            return this.defectiveQty;
        }

        public void setDefectiveQty(String defectiveQty) {
            this.defectiveQty = defectiveQty;
        }

        public String getDiffQuantity() {
            return this.diffQuantity;
        }

        public void setDiffQuantity(String diffQuantity) {
            this.diffQuantity = diffQuantity;
        }

        public String getDiscount() {
            return this.discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscountPrice() {
            return this.discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getEnglishName() {
            return this.englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getExCode() {
            return this.exCode;
        }

        public void setExCode(String exCode) {
            this.exCode = exCode;
        }

        public String getExpireDate() {
            return this.expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }

        public String getExtCode() {
            return this.extCode;
        }

        public void setExtCode(String extCode) {
            this.extCode = extCode;
        }

        public String getGoodsCode() {
            return this.goodsCode;
        }

        public void setGoodsCode(String goodsCode) {
            this.goodsCode = goodsCode;
        }

        public String getGrossWeight() {
            return this.grossWeight;
        }

        public void setGrossWeight(String grossWeight) {
            this.grossWeight = grossWeight;
        }

        public String getHeight() {
            return this.height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getInventoryType() {
            return this.inventoryType;
        }

        public void setInventoryType(String inventoryType) {
            this.inventoryType = inventoryType;
        }

        public String getIsAreaSale() {
            return this.isAreaSale;
        }

        public void setIsAreaSale(String isAreaSale) {
            this.isAreaSale = isAreaSale;
        }

        public String getIsBatchMgmt() {
            return this.isBatchMgmt;
        }

        public void setIsBatchMgmt(String isBatchMgmt) {
            this.isBatchMgmt = isBatchMgmt;
        }

        public String getIsFragile() {
            return this.isFragile;
        }

        public void setIsFragile(String isFragile) {
            this.isFragile = isFragile;
        }

        public String getIsHazardous() {
            return this.isHazardous;
        }

        public void setIsHazardous(String isHazardous) {
            this.isHazardous = isHazardous;
        }

        public String getIsSNMgmt() {
            return this.isSNMgmt;
        }

        public void setIsSNMgmt(String isSNMgmt) {
            this.isSNMgmt = isSNMgmt;
        }

        public String getIsShelfLifeMgmt() {
            return this.isShelfLifeMgmt;
        }

        public void setIsShelfLifeMgmt(String isShelfLifeMgmt) {
            this.isShelfLifeMgmt = isShelfLifeMgmt;
        }

        public String getIsSku() {
            return this.isSku;
        }

        public void setIsSku(String isSku) {
            this.isSku = isSku;
        }

        public String getItemCode() {
            return this.itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemId() {
            return this.itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return this.itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemType() {
            return this.itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getLackQty() {
            return this.lackQty;
        }

        public void setLackQty(String lackQty) {
            this.lackQty = lackQty;
        }

        public String getLatestUpdateTime() {
            return this.latestUpdateTime;
        }

        public void setLatestUpdateTime(String latestUpdateTime) {
            this.latestUpdateTime = latestUpdateTime;
        }

        public String getLength() {
            return this.length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getLockQuantity() {
            return this.lockQuantity;
        }

        public void setLockQuantity(String lockQuantity) {
            this.lockQuantity = lockQuantity;
        }

        public String getLockupLifecycle() {
            return this.lockupLifecycle;
        }

        public void setLockupLifecycle(String lockupLifecycle) {
            this.lockupLifecycle = lockupLifecycle;
        }

        public String getNetWeight() {
            return this.netWeight;
        }

        public void setNetWeight(String netWeight) {
            this.netWeight = netWeight;
        }

        public String getNormalQty() {
            return this.normalQty;
        }

        public void setNormalQty(String normalQty) {
            this.normalQty = normalQty;
        }

        public String getOrderCode() {
            return this.orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getOrderLineNo() {
            return this.orderLineNo;
        }

        public void setOrderLineNo(String orderLineNo) {
            this.orderLineNo = orderLineNo;
        }

        public String getOrderType() {
            return this.orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOriginAddress() {
            return this.originAddress;
        }

        public void setOriginAddress(String originAddress) {
            this.originAddress = originAddress;
        }

        public String getOriginCode() {
            return this.originCode;
        }

        public void setOriginCode(String originCode) {
            this.originCode = originCode;
        }

        public String getOutBizCode() {
            return this.outBizCode;
        }

        public void setOutBizCode(String outBizCode) {
            this.outBizCode = outBizCode;
        }

        public String getOwnerCode() {
            return this.ownerCode;
        }

        public void setOwnerCode(String ownerCode) {
            this.ownerCode = ownerCode;
        }

        public String getPackCode() {
            return this.packCode;
        }

        public void setPackCode(String packCode) {
            this.packCode = packCode;
        }

        public String getPackageMaterial() {
            return this.packageMaterial;
        }

        public void setPackageMaterial(String packageMaterial) {
            this.packageMaterial = packageMaterial;
        }

        public String getPaperQty() {
            return this.paperQty;
        }

        public void setPaperQty(String paperQty) {
            this.paperQty = paperQty;
        }

        public String getPcs() {
            return this.pcs;
        }

        public void setPcs(String pcs) {
            this.pcs = pcs;
        }

        public String getPlanQty() {
            return this.planQty;
        }

        public void setPlanQty(String planQty) {
            this.planQty = planQty;
        }

        public String getPrice() {
            return this.price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public DeliveryorderCreateRequest.PriceAdjustment getPriceAdjustment() {
            return this.priceAdjustment;
        }

        public void setPriceAdjustment(DeliveryorderCreateRequest.PriceAdjustment priceAdjustment) {
            this.priceAdjustment = priceAdjustment;
        }

        public String getPricingCategory() {
            return this.pricingCategory;
        }

        public void setPricingCategory(String pricingCategory) {
            this.pricingCategory = pricingCategory;
        }

        public String getProduceCode() {
            return this.produceCode;
        }

        public void setProduceCode(String produceCode) {
            this.produceCode = produceCode;
        }

        public String getProductCode() {
            return this.productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductDate() {
            return this.productDate;
        }

        public void setProductDate(String productDate) {
            this.productDate = productDate;
        }

        public String getPurchasePrice() {
            return this.purchasePrice;
        }

        public void setPurchasePrice(String purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public Long getQuantity() {
            return this.quantity;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }

        public String getReason() {
            return this.reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getReceiveQty() {
            return this.receiveQty;
        }

        public void setReceiveQty(String receiveQty) {
            this.receiveQty = receiveQty;
        }

        public String getReferencePrice() {
            return this.referencePrice;
        }

        public void setReferencePrice(String referencePrice) {
            this.referencePrice = referencePrice;
        }

        public String getRejectLifecycle() {
            return this.rejectLifecycle;
        }

        public void setRejectLifecycle(String rejectLifecycle) {
            this.rejectLifecycle = rejectLifecycle;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRetailPrice() {
            return this.retailPrice;
        }

        public void setRetailPrice(String retailPrice) {
            this.retailPrice = retailPrice;
        }

        public String getSafetyStock() {
            return this.safetyStock;
        }

        public void setSafetyStock(String safetyStock) {
            this.safetyStock = safetyStock;
        }

        public String getSeasonCode() {
            return this.seasonCode;
        }

        public void setSeasonCode(String seasonCode) {
            this.seasonCode = seasonCode;
        }

        public String getSeasonName() {
            return this.seasonName;
        }

        public void setSeasonName(String seasonName) {
            this.seasonName = seasonName;
        }

        public String getShelfLife() {
            return this.shelfLife;
        }

        public void setShelfLife(String shelfLife) {
            this.shelfLife = shelfLife;
        }

        public String getShortName() {
            return this.shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getSize() {
            return this.size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getSkuProperty() {
            return this.skuProperty;
        }

        public void setSkuProperty(String skuProperty) {
            this.skuProperty = skuProperty;
        }

        public String getSn() {
            return this.sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getSnCode() {
            return this.snCode;
        }

        public void setSnCode(String snCode) {
            this.snCode = snCode;
        }

        public String getSourceOrderCode() {
            return this.sourceOrderCode;
        }

        public void setSourceOrderCode(String sourceOrderCode) {
            this.sourceOrderCode = sourceOrderCode;
        }

        public String getStandardPrice() {
            return this.standardPrice;
        }

        public void setStandardPrice(String standardPrice) {
            this.standardPrice = standardPrice;
        }

        public String getStockStatus() {
            return this.stockStatus;
        }

        public void setStockStatus(String stockStatus) {
            this.stockStatus = stockStatus;
        }

        public String getStockUnit() {
            return this.stockUnit;
        }

        public void setStockUnit(String stockUnit) {
            this.stockUnit = stockUnit;
        }

        public String getSubSourceOrderCode() {
            return this.subSourceOrderCode;
        }

        public void setSubSourceOrderCode(String subSourceOrderCode) {
            this.subSourceOrderCode = subSourceOrderCode;
        }

        public String getSupplierCode() {
            return this.supplierCode;
        }

        public void setSupplierCode(String supplierCode) {
            this.supplierCode = supplierCode;
        }

        public String getSupplierName() {
            return this.supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getTagPrice() {
            return this.tagPrice;
        }

        public void setTagPrice(String tagPrice) {
            this.tagPrice = tagPrice;
        }

        public String getTareWeight() {
            return this.tareWeight;
        }

        public void setTareWeight(String tareWeight) {
            this.tareWeight = tareWeight;
        }

        public String getTempRequirement() {
            return this.tempRequirement;
        }

        public void setTempRequirement(String tempRequirement) {
            this.tempRequirement = tempRequirement;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUnit() {
            return this.unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getVolume() {
            return this.volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getWarehouseCode() {
            return this.warehouseCode;
        }

        public void setWarehouseCode(String warehouseCode) {
            this.warehouseCode = warehouseCode;
        }

        public String getWidth() {
            return this.width;
        }

        public void setWidth(String width) {
            this.width = width;
        }
    }

    public static class PriceAdjustment {
        @ApiField("discount")
        private String discount;
        @ApiField("endDate")
        private String endDate;
        @ApiField("remark")
        private String remark;
        @ApiField("standardPrice")
        private String standardPrice;
        @ApiField("startDate")
        private String startDate;
        @ApiField("type")
        private String type;

        public PriceAdjustment() {
        }

        public String getDiscount() {
            return this.discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getEndDate() {
            return this.endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStandardPrice() {
            return this.standardPrice;
        }

        public void setStandardPrice(String standardPrice) {
            this.standardPrice = standardPrice;
        }

        public String getStartDate() {
            return this.startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    @ApiListType("batch")
    public static class Batch {
        @ApiField("actualQty")
        private String actualQty;
        @ApiField("batchCode")
        private String batchCode;
        @ApiField("expireDate")
        private String expireDate;
        @ApiField("inventoryType")
        private String inventoryType;
        @ApiField("produceCode")
        private String produceCode;
        @ApiField("productDate")
        private String productDate;
        @ApiField("quantity")
        private String quantity;
        @ApiField("remark")
        private String remark;

        public Batch() {
        }

        public String getActualQty() {
            return this.actualQty;
        }

        public void setActualQty(String actualQty) {
            this.actualQty = actualQty;
        }

        public String getBatchCode() {
            return this.batchCode;
        }

        public void setBatchCode(String batchCode) {
            this.batchCode = batchCode;
        }

        public String getExpireDate() {
            return this.expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }

        public String getInventoryType() {
            return this.inventoryType;
        }

        public void setInventoryType(String inventoryType) {
            this.inventoryType = inventoryType;
        }

        public String getProduceCode() {
            return this.produceCode;
        }

        public void setProduceCode(String produceCode) {
            this.produceCode = produceCode;
        }

        public String getProductDate() {
            return this.productDate;
        }

        public void setProductDate(String productDate) {
            this.productDate = productDate;
        }

        public String getQuantity() {
            return this.quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class ReceiverInfo {
        @ApiField("area")
        private String area;
        @ApiField("birthDate")
        private String birthDate;
        @ApiField("carNo")
        private String carNo;
        @ApiField("career")
        private String career;
        @ApiField("city")
        private String city;
        @ApiField("company")
        private String company;
        @ApiField("countryCode")
        private String countryCode;
        @ApiField("countryCodeCiq")
        private String countryCodeCiq;
        @ApiField("countryCodeCus")
        private String countryCodeCus;
        @ApiField("detailAddress")
        private String detailAddress;
        @ApiField("email")
        private String email;
        @ApiField("fax")
        private String fax;
        @ApiField("gender")
        private String gender;
        @ApiField("id")
        private String id;
        @ApiField("idNumber")
        private String idNumber;
        @ApiField("idType")
        private String idType;
        @ApiField("mobile")
        private String mobile;
        @ApiField("name")
        private String name;
        @ApiField("nick")
        private String nick;
        @ApiField("province")
        private String province;
        @ApiField("receiverMobile")
        private String receiverMobile;
        @ApiField("receiverName")
        private String receiverName;
        @ApiField("receiverTel")
        private String receiverTel;
        @ApiField("receiverZipCode")
        private String receiverZipCode;
        @ApiField("remark")
        private String remark;
        @ApiField("tel")
        private String tel;
        @ApiField("town")
        private String town;
        @ApiField("zipCode")
        private String zipCode;

        public ReceiverInfo() {
        }

        public String getArea() {
            return this.area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getBirthDate() {
            return this.birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getCarNo() {
            return this.carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getCareer() {
            return this.career;
        }

        public void setCareer(String career) {
            this.career = career;
        }

        public String getCity() {
            return this.city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCompany() {
            return this.company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCountryCode() {
            return this.countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryCodeCiq() {
            return this.countryCodeCiq;
        }

        public void setCountryCodeCiq(String countryCodeCiq) {
            this.countryCodeCiq = countryCodeCiq;
        }

        public String getCountryCodeCus() {
            return this.countryCodeCus;
        }

        public void setCountryCodeCus(String countryCodeCus) {
            this.countryCodeCus = countryCodeCus;
        }

        public String getDetailAddress() {
            return this.detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFax() {
            return this.fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getGender() {
            return this.gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdNumber() {
            return this.idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getIdType() {
            return this.idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getMobile() {
            return this.mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNick() {
            return this.nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getProvince() {
            return this.province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getReceiverMobile() {
            return this.receiverMobile;
        }

        public void setReceiverMobile(String receiverMobile) {
            this.receiverMobile = receiverMobile;
        }

        public String getReceiverName() {
            return this.receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiverTel() {
            return this.receiverTel;
        }

        public void setReceiverTel(String receiverTel) {
            this.receiverTel = receiverTel;
        }

        public String getReceiverZipCode() {
            return this.receiverZipCode;
        }

        public void setReceiverZipCode(String receiverZipCode) {
            this.receiverZipCode = receiverZipCode;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTel() {
            return this.tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTown() {
            return this.town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getZipCode() {
            return this.zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }

    public static class SenderInfo {
        @ApiField("area")
        private String area;
        @ApiField("birthDate")
        private String birthDate;
        @ApiField("carNo")
        private String carNo;
        @ApiField("career")
        private String career;
        @ApiField("city")
        private String city;
        @ApiField("company")
        private String company;
        @ApiField("countryCode")
        private String countryCode;
        @ApiField("countryCodeCiq")
        private String countryCodeCiq;
        @ApiField("countryCodeCus")
        private String countryCodeCus;
        @ApiField("detailAddress")
        private String detailAddress;
        @ApiField("email")
        private String email;
        @ApiField("fax")
        private String fax;
        @ApiField("gender")
        private String gender;
        @ApiField("id")
        private String id;
        @ApiField("idNumber")
        private String idNumber;
        @ApiField("idType")
        private String idType;
        @ApiField("mobile")
        private String mobile;
        @ApiField("name")
        private String name;
        @ApiField("nick")
        private String nick;
        @ApiField("province")
        private String province;
        @ApiField("receiverMobile")
        private String receiverMobile;
        @ApiField("receiverName")
        private String receiverName;
        @ApiField("receiverTel")
        private String receiverTel;
        @ApiField("receiverZipCode")
        private String receiverZipCode;
        @ApiField("remark")
        private String remark;
        @ApiField("tel")
        private String tel;
        @ApiField("town")
        private String town;
        @ApiField("zipCode")
        private String zipCode;

        public SenderInfo() {
        }

        public String getArea() {
            return this.area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getBirthDate() {
            return this.birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getCarNo() {
            return this.carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getCareer() {
            return this.career;
        }

        public void setCareer(String career) {
            this.career = career;
        }

        public String getCity() {
            return this.city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCompany() {
            return this.company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCountryCode() {
            return this.countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryCodeCiq() {
            return this.countryCodeCiq;
        }

        public void setCountryCodeCiq(String countryCodeCiq) {
            this.countryCodeCiq = countryCodeCiq;
        }

        public String getCountryCodeCus() {
            return this.countryCodeCus;
        }

        public void setCountryCodeCus(String countryCodeCus) {
            this.countryCodeCus = countryCodeCus;
        }

        public String getDetailAddress() {
            return this.detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFax() {
            return this.fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getGender() {
            return this.gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdNumber() {
            return this.idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getIdType() {
            return this.idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getMobile() {
            return this.mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNick() {
            return this.nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getProvince() {
            return this.province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getReceiverMobile() {
            return this.receiverMobile;
        }

        public void setReceiverMobile(String receiverMobile) {
            this.receiverMobile = receiverMobile;
        }

        public String getReceiverName() {
            return this.receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiverTel() {
            return this.receiverTel;
        }

        public void setReceiverTel(String receiverTel) {
            this.receiverTel = receiverTel;
        }

        public String getReceiverZipCode() {
            return this.receiverZipCode;
        }

        public void setReceiverZipCode(String receiverZipCode) {
            this.receiverZipCode = receiverZipCode;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTel() {
            return this.tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTown() {
            return this.town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getZipCode() {
            return this.zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }

    public static class DeliveryRequirements {
        @ApiField("deliveryType")
        private String deliveryType;
        @ApiField("remark")
        private String remark;
        @ApiField("scheduleDay")
        private String scheduleDay;
        @ApiField("scheduleEndTime")
        private String scheduleEndTime;
        @ApiField("scheduleStartTime")
        private String scheduleStartTime;
        @ApiField("scheduleType")
        private Long scheduleType;

        public DeliveryRequirements() {
        }

        public String getDeliveryType() {
            return this.deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getScheduleDay() {
            return this.scheduleDay;
        }

        public void setScheduleDay(String scheduleDay) {
            this.scheduleDay = scheduleDay;
        }

        public String getScheduleEndTime() {
            return this.scheduleEndTime;
        }

        public void setScheduleEndTime(String scheduleEndTime) {
            this.scheduleEndTime = scheduleEndTime;
        }

        public String getScheduleStartTime() {
            return this.scheduleStartTime;
        }

        public void setScheduleStartTime(String scheduleStartTime) {
            this.scheduleStartTime = scheduleStartTime;
        }

        public Long getScheduleType() {
            return this.scheduleType;
        }

        public void setScheduleType(Long scheduleType) {
            this.scheduleType = scheduleType;
        }
    }
}
