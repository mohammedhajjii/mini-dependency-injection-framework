package ma.enset.processors;


import com.google.auto.service.AutoService;
import ma.enset.annotation.Bean;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("ma.enset.annotation.Bean")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class BeanProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        //processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "this note by processor");
        roundEnv.getElementsAnnotatedWith(Bean.class)
                .stream()
                .filter(element -> ((ExecutableElement) element).getModifiers().contains(Modifier.PRIVATE))
                .forEach(element -> processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "bean method must be public", element));


        return false;
    }
}
