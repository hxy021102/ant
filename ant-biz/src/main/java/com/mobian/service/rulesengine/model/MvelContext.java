package com.mobian.service.rulesengine.model;

/**
 * 规则引擎上下文具体扩展
 */
public class MvelContext extends Context {
    public MvelContext(){
        super();
    }
    public MvelContext(Context context){
        super.putAll(context);
    }
}
