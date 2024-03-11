package ma.enset.injectors;

import lombok.SneakyThrows;

public interface Injector {
    void inject();
    boolean canBeInjected();
}
