package ma.enset.initializers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Getter @Setter
@AllArgsConstructor
@Builder
public class SimpleConstructorInitializer extends SimpleInitializer{

    private Constructor<?> constructor;
    @Override
    public Object initialize() {
        try {
            return constructor.newInstance();
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean areAllDependenciesSatisfied() {
        return true;
    }
}
