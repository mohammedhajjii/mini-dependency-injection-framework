package ma.enset.initializers;


import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.enset.resolvers.BeanResolver;

import java.lang.reflect.Method;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
public class ParametricMethodInitializer extends ParametricInitializer{

    private Method init;
    private BeanResolver instance;

    @Override
    @SneakyThrows(ReflectiveOperationException.class)
    public Object initialize() {
        return init.invoke(
                instance.resolve(),
                this.getParameters()
                        .stream()
                        .map(BeanResolver::resolve)
                        .toArray()
        );
    }

    @Override
    public boolean canBeInitialized() {
        return this.getParameters().stream()
                .allMatch(BeanResolver::canBeResolved)
                && instance.canBeResolved();
    }
}
