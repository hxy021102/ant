package com.mobian.service;

import com.bx.ant.service.ShopItemServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 黄晓渝 on 2017/12/9.
 */
@Service
public class ShopItemTaskService {
    @Resource
    private ShopItemServiceI shopItemService;

    public void addShopItemAllocation() {
        shopItemService.addShopItemAllocation();
    }

}
