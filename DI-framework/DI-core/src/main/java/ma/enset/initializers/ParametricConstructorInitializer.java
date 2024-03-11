package ma.enset.initializers;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.enset.resolvers.BeanResolver;

import java.lang.reflect.Constructor;


@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParametricConstructorInitializer extends ParametricInitializer{
    private Constructor<?> constructor;
    @Override
    @SneakyThrows(ReflectiveOperationException.class)
    public Object initialize() {
        return constructor.newInstance(
                this.getParameters()
                        .stream()
                        .map(BeanResolver::resolve)
                        .toArray()
        );
    }

    @Override
    public boolean canBeInitialized() {
        return this.getParameters()
                .stream()
                .allMatch(BeanResolver::canBeResolved);
    }
}
