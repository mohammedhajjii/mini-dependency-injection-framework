package ma.enset.initializers;


import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.enset.resolvers.BeanResolver;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ParametricInitializer implements Initializer{
    private Set<BeanResolver> parameters;
}
