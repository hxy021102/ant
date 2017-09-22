package com.mobian.service.rulesengine.impl;


import com.mobian.service.rulesengine.*;
import com.mobian.service.rulesengine.model.Context;
import com.mobian.service.rulesengine.model.MvelContext;
import com.mobian.service.rulesengine.model.Rule;
import org.mvel2.MVEL;
import org.springframework.stereotype.Service;

@Service("ruleExecutor")
public class RuleExecutorImpl implements RuleExecutor<Rule> {
    @Override
    public String getType() {
        return "mvel";
    }

    @Override
    public boolean execute(Context context, Rule rule) {
        try {
            if (rule.isVaild()) {
                if (executeCondition(rule.getCondition(), context)) {
                    executeAction(rule.getAction(), context);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Mvel规则引擎执行器发生异常:", e);
        }
    }

    /**
     * 判读条件是否匹配
     *
     * @param condition
     * @return
     * @para context
     */
    protected boolean executeCondition(String condition, Context context) {
        try {
            MvelContext mvelContext = null;
            if (context instanceof MvelContext) {
                mvelContext = (MvelContext) context;
            } else {
                mvelContext = new MvelContext(context);
            }
            return (Boolean) MVEL.eval("if (" + condition + ") return true; else return false;", mvelContext);
        } catch (Exception e) {
            throw new RuntimeException(String.format("条件[%s]匹配发生异常:", condition), e);
        }
    }

    /**
     * 执行条件匹配后的操作
     *
     * @param action
     * @param context
     */
    protected void executeAction(String action, Context context) {
        try {
            MvelContext mvelContext = null;
            if (context instanceof MvelContext) {
                mvelContext = (MvelContext) context;
            } else {
                mvelContext = new MvelContext(context);
            }
            MVEL.eval(action, mvelContext);
        } catch (Exception e) {
            throw new RuntimeException(String.format("后续操作[%s]执行发生异常:", action), e);
        }
    }
}

