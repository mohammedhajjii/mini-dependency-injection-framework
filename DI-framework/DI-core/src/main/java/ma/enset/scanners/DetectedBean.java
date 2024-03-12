package ma.enset.scanners;

import lombok.*;
import ma.enset.initializers.Initializer;
import ma.enset.injectors.Injector;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetectedBean {
    private String specifiedName;
    private Initializer initializer;
    private Set<Injector> injectorSet;
}
