package ma.enset.initializers;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.enset.resolvers.BeanResolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParametricConstructorInitializer extends ParametricInitializer{
    private Constructor<?> constructor;
    @Override
    public Object initialize() {

        try {
            return constructor.newInstance(
                    this.getParameters()
                            .stream()
                            .map(BeanResolver::resolve)
                            .toArray()
            );
        } catch (ReflectiveOperationException exception){
            throw new RuntimeException(exception);
        }

    }

    @Override
    public boolean areAllDependenciesSatisfied() {
        return this.getParameters()
                .stream()
                .allMatch(BeanResolver::isSatisfied);
    }
}
