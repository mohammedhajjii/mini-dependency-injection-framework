package ma.enset.resolvers;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ma.enset.repo.Context;

@Getter @Setter
@AllArgsConstructor
public class BeanTypeResolver implements BeanResolver{

    private Class<?> type;

    @Override
    public Object resolve() {
        return Context.INSTANCE.getContext()
                .values()
                .stream()
                .filter(obj -> type.isAssignableFrom(obj.getClass()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isSatisfied() {
        return Context.INSTANCE.getContext()
                .values()
                .stream()
                .anyMatch(obj -> type.isAssignableFrom(obj.getClass()));
    }
}
