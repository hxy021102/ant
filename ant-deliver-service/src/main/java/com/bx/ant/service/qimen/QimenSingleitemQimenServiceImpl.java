package com.bx.ant.service.qimen;

import com.bx.ant.pageModel.SupplierItemRelation;
import com.bx.ant.service.SupplierItemRelationServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.MbItem;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbItemServiceI;
import com.mobian.util.ConvertNameUtil;
import com.qimen.api.QimenRequest;
import com.qimen.api.QimenResponse;
import com.qimen.api.request.SingleitemSynchronizeRequest;
import com.qimen.api.response.SingleitemSynchronizeResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by john on 17/11/28.
 */
@Service("taobao.qimen.singleitem.synchronize")
public class QimenSingleitemQimenServiceImpl extends AbstrcatQimenService {

    @Resource
    private MbItemServiceI mbItemService;
    @Resource
    private SupplierItemRelationServiceI supplierItemRelationService;

    @Override
    public QimenResponse execute(QimenRequest request) {
        SingleitemSynchronizeRequest req = (SingleitemSynchronizeRequest) request;
        SingleitemSynchronizeRequest.Item item = req.getItem();
        MbItem mbItem = new MbItem();
        mbItem.setCode(item.getItemCode());
        mbItem.setName(item.getItemName());
        mbItem.setIspack(false);           //是否包装瓶
        mbItem.setCarton(item.getPcs()); //箱规
        mbItem.setBarCode(item.getBarCode());//条形码
        MbItem itemReq = new MbItem();
        itemReq.setCode(item.getItemCode());
        //item.getIsValid()
        List<MbItem> mbItemList = mbItemService.query(itemReq);
        if (CollectionUtils.isEmpty(mbItemList)) {
            mbItem.setIsShelves(false);
            mbItemService.add(mbItem);
        } else {
            mbItem.setId(mbItemList.get(0).getId());
            mbItemService.edit(mbItem);
        }
        Integer supplierId = Integer.parseInt(ConvertNameUtil.getString(QimenRequestService.QIM_06));
        SupplierItemRelation supplierItemRelation = new SupplierItemRelation();
        supplierItemRelation.setSupplierId(supplierId);
        supplierItemRelation.setSupplierItemCode(item.getItemCode());
        supplierItemRelation.setOnline(true);
        PageHelper pageHelper = new PageHelper();
        pageHelper.setHiddenTotal(true);
        List<SupplierItemRelation> supplierItemRelations = supplierItemRelationService.dataGrid(supplierItemRelation, pageHelper).getRows();
        if (CollectionUtils.isEmpty(supplierItemRelations)) {
            Integer itemId = -1;
            if (CollectionUtils.isNotEmpty(mbItemList)) {
                itemId = mbItemList.get(0).getId();
            }
            supplierItemRelation.setItemId(itemId);
            if(F.empty(item.getCostPrice())){
                item.setCostPrice("0");
            }
            Double costPrice = new Double(item.getCostPrice()) * 100;
            supplierItemRelation.setInPrice(costPrice.intValue());//成本价
            supplierItemRelation.setFreight(Integer.parseInt(ConvertNameUtil.getString(QimenRequestService.QIM_07)));//运费
            supplierItemRelation.setPrice(supplierItemRelation.getInPrice() + supplierItemRelation.getFreight());//总价
            supplierItemRelationService.add(supplierItemRelation);
        }
        SingleitemSynchronizeResponse response = new SingleitemSynchronizeResponse();
        response.setFlag("success");
        response.setItemId(item.getItemCode());
        return response;
    }

    @Override
    public Class getParserRequestClass() {
        return SingleitemSynchronizeRequest.class;
    }

}
