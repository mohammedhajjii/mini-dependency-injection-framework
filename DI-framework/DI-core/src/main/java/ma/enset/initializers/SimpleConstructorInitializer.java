package ma.enset.initializers;

import lombok.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Getter @Setter
@AllArgsConstructor
@Builder
public class SimpleConstructorInitializer extends SimpleInitializer{

    private Constructor<?> constructor;
    @Override
    @SneakyThrows(ReflectiveOperationException.class)
    public Object initialize() {
        return constructor.newInstance();
    }

    @Override
    public boolean canBeInitialized() {
        return true;
    }
}
