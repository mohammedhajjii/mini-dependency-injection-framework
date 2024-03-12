package ma.enset.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.*;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface Prefer {
    String value();
}
