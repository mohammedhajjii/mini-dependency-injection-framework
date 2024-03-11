package ma.enset.repo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Context {

    INSTANCE;
    private final Map<String, Object> context = new ConcurrentHashMap<>();

    public void saveBean(String beanName, Object beanValue){
        this.context.put(beanName, beanValue);
    }

    public Object getBean(String beanName){
        return context.get(beanName);
    }

    public Object getBean(Class<?> type){
        return this.context.values()
                .stream()
                .filter(obj -> type.isAssignableFrom(obj.getClass()))
                .findFirst()
                .orElse(null);
    }

    public int size(){
        return this.context.size();
    }

    public boolean beanExisted(String beanName){
        return this.context.containsKey(beanName);
    }

    public boolean beanExiste(Class<?> type){
        return  this.context.values()
                .stream()
                .anyMatch(obj -> type.isAssignableFrom(obj.getClass()));
    }
}
