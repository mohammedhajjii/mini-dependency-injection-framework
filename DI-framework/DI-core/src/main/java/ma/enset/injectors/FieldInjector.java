package ma.enset.injectors;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ma.enset.resolvers.BeanResolver;
import java.lang.reflect.Field;

@Getter
@Setter
@Builder
public class FieldInjector implements Injector{

    private Field field;
    private BeanResolver instance;
    private BeanResolver injectedValue;


    @Override
    public void inject() throws ReflectiveOperationException {
        field.setAccessible(true);
        field.set(instance.resolve(), injectedValue.resolve());
    }
}
