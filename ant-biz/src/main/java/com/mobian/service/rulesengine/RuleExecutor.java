package com.mobian.service.rulesengine;


import com.mobian.service.rulesengine.model.Context;
import com.mobian.service.rulesengine.model.Rule;

/**
 * Created by wanxp on 17-9-2.
 */
public interface RuleExecutor <T extends Rule> {
    /**
     * 返回执行器类型
     * @return
     */
    String getType();

    /**
     * 执行规则 并将结果写入上下文
     * @param context
     * @return 返回条件是否成立
     */
    boolean execute(Context context, T rule);
}
