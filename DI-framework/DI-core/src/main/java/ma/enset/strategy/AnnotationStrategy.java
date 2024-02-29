package ma.enset.strategy;

import ma.enset.injectors.Injector;
import ma.enset.repo.Context;
import ma.enset.scanner.AnnotationScanner;
import ma.enset.scanner.DetectedBean;
import ma.enset.scanner.Scanner;

import java.util.Set;

public class AnnotationStrategy implements InjectionStrategy{

    private final Scanner annotationScanner;

    public AnnotationStrategy(String ...forPackages) {
        this.annotationScanner = new AnnotationScanner(forPackages);
    }

    @Override
    public void apply() throws Exception {
        Set<DetectedBean> detectedBeanSet = annotationScanner.scan();

        int oldSize;
        while (Context.INSTANCE.getContext().size() != detectedBeanSet.size()){
            oldSize = Context.INSTANCE.getContext().size();

            detectedBeanSet.stream()
                    .filter(detectedBean -> detectedBean.getInitializer().areAllDependenciesSatisfied())
                    .forEach(detectedBean ->  {
                        Object bean = detectedBean.getInitializer().initialize();
                        Context.INSTANCE.getContext().put(detectedBean.getSpecifiedName(), bean);
                    });

            if (oldSize == Context.INSTANCE.getContext().size())
                throw new RuntimeException("detected cycle");
        }
        detectedBeanSet.stream()
                .filter(detectedBean -> !detectedBean.getInjectorSet().isEmpty())
                .forEach(detectedBean -> {
                    detectedBean.getInjectorSet()
                            .forEach(Injector::inject);
                });



    }
}
