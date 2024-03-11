package ma.enset.injectors;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import ma.enset.resolvers.BeanResolver;
import ma.enset.resolvers.UnresolvedBean;
import ma.enset.resolvers.UnresolvedBeans;

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

    @Override
    public UnresolvedBean findFirstUnresolvedBean() {
        if (!instance.canBeResolved())
            return UnresolvedBeans.from(instance);

        if (!injectedValue.canBeResolved())
            return UnresolvedBeans.from(injectedValue);

        return null;
    }
}
