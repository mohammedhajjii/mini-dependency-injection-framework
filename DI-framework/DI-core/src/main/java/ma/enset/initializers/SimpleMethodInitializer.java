package ma.enset.initializers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ma.enset.resolvers.BeanResolver;

import java.lang.reflect.Method;

@Getter @Setter
@AllArgsConstructor
@Builder
public class SimpleMethodInitializer extends SimpleInitializer{

    private Method init;
    private BeanResolver instance;

    @Override
    public Object initialize() throws ReflectiveOperationException {
        return init.invoke(instance.resolve());
    }
}
