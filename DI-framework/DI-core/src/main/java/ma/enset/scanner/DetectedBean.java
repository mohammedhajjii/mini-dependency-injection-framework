package ma.enset.scanner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.initializers.Initializer;
import ma.enset.injectors.Injector;
import ma.enset.dependencies.Dependency;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetectedBean {
    private String specifiedName;
    private Initializer initializer;
    private Set<Dependency> dependencySet;
    private Set<Injector> injectorSet;
}
