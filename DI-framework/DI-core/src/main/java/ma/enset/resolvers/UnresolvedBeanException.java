package ma.enset.resolvers;

public class UnresolvedBeanException extends RuntimeException{

    public UnresolvedBeanException(UnresolvedBean unresolvedBean) {
        super(unresolvedBean.raison());
    }
}
