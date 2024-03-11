package ma.enset.resolvers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ma.enset.repo.Context;

@Getter @Setter
@AllArgsConstructor
public class BeanNameResolver implements BeanResolver {

    private String name;

    @Override
    public Object resolve()  {
        return Context.INSTANCE.getBean(name);
    }

    @Override
    public boolean canBeResolved() {
        return Context.INSTANCE.beanExisted(name);
    }
}
