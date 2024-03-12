package ma.enset.initializers;


import ma.enset.resolvers.UnresolvedBean;

public interface Initializer {
    Object initialize();
    boolean canBeInitialized();

     default UnresolvedBean findFirstUnresolvedBean(){
         return null;
     }
}
