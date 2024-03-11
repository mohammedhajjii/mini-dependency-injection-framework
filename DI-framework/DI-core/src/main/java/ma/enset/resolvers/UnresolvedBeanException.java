package ma.enset.resolvers;

public class UnresolvedBeanException extends RuntimeException{

    public UnresolvedBeanException(String beanName) {
        super("unresolved bean with name: " + beanName);
    }

    public UnresolvedBeanException(Class<?> type) {
        super("unresolved bean with type: " + type.getName());
    }
}
