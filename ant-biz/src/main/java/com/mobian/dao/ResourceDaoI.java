package com.mobian.dao;

import com.mobian.model.Tresource;

/**
 * 资源数据库操作类
 *
 * @author John
 */
public interface ResourceDaoI extends BaseDaoI<Tresource> {

    Tresource getByUrl(String url);

}
