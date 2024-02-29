package ma.enset.resolvers;

public interface BeanResolver {
    Object resolve();
    boolean isSatisfied();
}
