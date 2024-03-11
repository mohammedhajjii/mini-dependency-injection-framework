package ma.enset.initializers;

import lombok.*;
import ma.enset.resolvers.BeanResolver;

import java.lang.reflect.Method;

@Getter @Setter
@AllArgsConstructor
@Builder
public class SimpleMethodInitializer extends SimpleInitializer{

    private Method init;
    private BeanResolver instance;

    @Override
    @SneakyThrows(ReflectiveOperationException.class)
    public Object initialize() {
        return init.invoke(instance.resolve());
    }

    @Override
    public boolean canBeInitialized() {
        return instance.canBeResolved();
    }
}
