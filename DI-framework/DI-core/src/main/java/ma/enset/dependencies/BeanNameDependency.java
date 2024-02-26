package ma.enset.dependencies;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ma.enset.repo.Context;

@Getter @Setter
@AllArgsConstructor
@Builder
public class BeanNameDependency implements Dependency{

    private String beanName;
    @Override
    public boolean isSatisfied() {
        return Context.INSTANCE
                .getContext()
                .keySet()
                .stream()
                .anyMatch(existedBeanName -> existedBeanName.equals(beanName));
    }
}
