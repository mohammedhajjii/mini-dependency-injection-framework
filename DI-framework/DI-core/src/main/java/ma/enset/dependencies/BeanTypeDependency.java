package ma.enset.dependencies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ma.enset.repo.Context;

@Data
@AllArgsConstructor
@Builder
public class BeanTypeDependency implements Dependency{

    private Class<?> type;
    @Override
    public boolean isSatisfied() {
        return Context.INSTANCE
                .getContext()
                .values()
                .stream()
                .anyMatch(existedType -> type.isAssignableFrom(existedType.getClass()));
    }
}
