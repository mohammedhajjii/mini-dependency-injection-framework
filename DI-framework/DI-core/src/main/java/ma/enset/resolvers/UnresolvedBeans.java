package ma.enset.resolvers;

public class UnresolvedBeans {
    private static String UNRESOLVED_BEAN_NAME = "unresolved bean with name: ";
    private static String UNRESOLVED_BEAN_TYPE = "unresolved bean with type: ";

    public static UnresolvedBean from(BeanResolver beanResolver){
        if (beanResolver instanceof BeanNameResolver beanNameResolver)
            return new UnresolvedBean(UNRESOLVED_BEAN_NAME + beanNameResolver.getName());
        return new UnresolvedBean(UNRESOLVED_BEAN_TYPE + ((BeanTypeResolver) beanResolver).getType().getName());
    }
}
