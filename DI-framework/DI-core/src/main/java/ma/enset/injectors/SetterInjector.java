package ma.enset.injectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ma.enset.resolvers.BeanResolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Getter
@Setter
@Builder
public class SetterInjector implements Injector{
    private Method setter;
    private BeanResolver instance;
    private BeanResolver parameter;


    @Override
    public void inject() {
        try {
            setter.invoke(instance.resolve(), parameter.resolve());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
