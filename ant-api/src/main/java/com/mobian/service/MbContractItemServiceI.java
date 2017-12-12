package com.mobian.service;

import com.mobian.model.TmbContractItem;
import com.mobian.pageModel.*;

import java.util.List;

/**
 * @author John
 */
public interface MbContractItemServiceI {

    /**
     * 获取MbContractItem数据表格
     *
     * @param mbContractItem 参数
     * @param ph             分页帮助类
     * @return
     */
    DataGrid dataGrid(MbContractItem mbContractItem, PageHelper ph);

    /**
     * 添加MbContractItem
     *
     * @param mbContractItem
     */
    void add(MbContractItem mbContractItem);

    /**
     * 获得MbContractItem对象
     *
     * @param id
     * @return
     */
    MbContractItem get(Integer id);

    /**
     * 修改MbContractItem
     *
     * @param mbContractItem
     */
    void edit(MbContractItem mbContractItem);

    /**
     * 修改并保存历史
     *
     * @param mbContractItem
     */
    void editAndHistory(MbContractItem mbContractItem);

    /**
     * 批量添加修改
     *
     * @param mbContractItemList
     */
    void addBatchAndOverride(List<MbContractItem> mbContractItemList);

    /**
     * 删除MbContractItem
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 获得List<TmbContractItem>对象
     */
    List<TmbContractItem> queryMbContractItemByShopId(Integer contractId);

    /**
     * 获取合同明细信息
     *
     * @param mbContractItem
     * @param ph
     * @return
     */
    DataGrid queryContractItem(MbContractItem mbContractItem, PageHelper ph);

    /**
     * 批量修改合同价格
     *
     * @param mbContractItemList
     * @param newPrice
     */
    void updateBatchContractPrice(String mbContractItemList, Integer newPrice);

    /**
     * 查询合同商品信息
     * @param mbContractItem
     * @return
     */
    List<MbContractItem> query(MbContractItem mbContractItem);


    /**
     * 获取商品列表与数量和合同价格
     * @param DeliverOrderShopIds
     * @param shopId
     * @return
     */
    List<MbItemView> getItemListWidthPriceAndQuantity(String DeliverOrderShopIds,Integer shopId);
}
