package ma.enset.injectors;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
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
    @SneakyThrows(IllegalAccessException.class)
    public void inject()  {
        field.setAccessible(true);
        field.set(instance.resolve(), injectedValue.resolve());

    }

    @Override
    public boolean canBeInjected() {
        return instance.canBeResolved() && injectedValue.canBeResolved();
    }
}
