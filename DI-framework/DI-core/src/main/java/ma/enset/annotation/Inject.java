package ma.enset.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

@Target({FIELD, CONSTRUCTOR, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
}
