package com.chinacreator.c2.sys.sdk.service.test;

import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.chinacreator.c2.sys.sdk.bean.Orgnization;

public class ParameterRule implements MethodRule {

    @Override
    public Statement apply(final Statement paramStatement,final FrameworkMethod paramFrameworkMethod,final Object paramObject) {
        return new Statement() {
            public void evaluate() throws Throwable {
                Orgnization orgnization=new Orgnization();
                orgnization.setOrgId("1");
                paramFrameworkMethod.invokeExplosively(paramObject,orgnization);
            }
        };
    }
    
    
}
