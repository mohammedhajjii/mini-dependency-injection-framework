package ma.enset.injectors;

import java.lang.reflect.InvocationTargetException;

public interface Injector {
    void inject() throws ReflectiveOperationException;
}
