package ma.enset.resolvers;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ma.enset.repository.Context;

@Getter @Setter
@AllArgsConstructor
public class BeanTypeResolver implements BeanResolver{

    private Class<?> type;

    @Override
    public Object resolve() {
        return Context.INSTANCE.getBean(type);
    }

    @Override
    public boolean canBeResolved() {
        return Context.INSTANCE.beanExiste(type);
    }
}
