package com.bx.ant.service.config;

import com.bx.ant.listener.Application;
import org.springframework.stereotype.Service;

/**
 * Created by john on 17/9/26.
 */
@Service
public class ConfigServiceImpl implements ConfigServiceI {
    @Override
    public void refreshAppVariable() {
        Application.refresh();
    }
}
