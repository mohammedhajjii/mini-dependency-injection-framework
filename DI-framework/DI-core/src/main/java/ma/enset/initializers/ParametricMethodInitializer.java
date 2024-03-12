package ma.enset.initializers;


import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.enset.resolvers.BeanResolver;
import ma.enset.resolvers.UnresolvedBean;
import ma.enset.resolvers.UnresolvedBeans;

import java.lang.reflect.Method;
import java.util.stream.Stream;

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

    @Override
    public UnresolvedBean findFirstUnresolvedBean() {
        return  Stream.concat(
                    this.getParameters().stream(),
                    Stream.of(instance)
                )
                .filter(beanResolver -> !beanResolver.canBeResolved())
                .map(UnresolvedBeans::from)
                .findAny()
                .orElse(null);
    }
}
