package ma.enset.injectors;

import lombok.SneakyThrows;
import ma.enset.resolvers.UnresolvedBean;

public interface Injector {
    void inject();
    boolean canBeInjected();

    UnresolvedBean findFirstUnresolvedBean();
}
