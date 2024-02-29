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
    public Object initialize() {
        try {
            return init.invoke(
                    instance.resolve(),
                    this.getParameters()
                            .stream()
                            .map(BeanResolver::resolve)
                            .toArray()
            );
        }catch (ReflectiveOperationException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean areAllDependenciesSatisfied() {
        return this.getParameters().stream()
                .allMatch(BeanResolver::isSatisfied)
                && instance.isSatisfied();
    }
}
