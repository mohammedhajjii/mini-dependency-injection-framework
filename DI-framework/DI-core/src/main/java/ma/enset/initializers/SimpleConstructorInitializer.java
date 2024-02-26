package ma.enset.initializers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.lang.reflect.Constructor;

@Getter @Setter
@AllArgsConstructor
@Builder
public class SimpleConstructorInitializer extends SimpleInitializer{

    private Constructor<?> constructor;
    @Override
    public Object initialize() throws ReflectiveOperationException {
        return constructor.newInstance();
    }
}
