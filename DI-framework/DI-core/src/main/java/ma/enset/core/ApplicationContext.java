package ma.enset.core;

import ma.enset.repository.Context;
import ma.enset.strategies.InjectionStrategy;

public class ApplicationContext {

    public ApplicationContext(InjectionStrategy  injectionStrategy) {
        injectionStrategy.apply();
    }

    public Object getBean(Class<?> type){
        return Context.INSTANCE.getBean(type);
    }
    public Object getBean(String beanName){
        return Context.INSTANCE.getBean(beanName);
    }
}
