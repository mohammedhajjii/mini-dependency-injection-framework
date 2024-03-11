package ma.enset.initializers;


public interface Initializer {
    Object initialize();
    boolean canBeInitialized();
}
