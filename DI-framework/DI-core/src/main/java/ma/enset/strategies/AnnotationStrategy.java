package ma.enset.strategies;

import ma.enset.injectors.Injector;
import ma.enset.repo.Context;
import ma.enset.scanners.AnnotationScanner;
import ma.enset.scanners.DetectedBean;
import ma.enset.scanners.Scanner;
import java.util.Set;


public class AnnotationStrategy implements InjectionStrategy{

    private final Scanner annotationScanner;

    public AnnotationStrategy(String ...forPackages) {
        this.annotationScanner = new AnnotationScanner(forPackages);
    }


    @Override
    public void apply(){
        Set<DetectedBean> detectedBeanSet = annotationScanner.scan();

        int oldSize = 0;
        while (Context.INSTANCE.size() != detectedBeanSet.size()){
            detectedBeanSet.stream()
                    .filter(detectedBean -> detectedBean.getInitializer().canBeInitialized())
                    .forEach(detectedBean ->  {
                        Object bean = detectedBean.getInitializer().initialize();
                        Context.INSTANCE.saveBean(detectedBean.getSpecifiedName(), bean);
                    });

            if (oldSize == Context.INSTANCE.size()){
                throw new RuntimeException("some bean cannot be resolved");
            }
            oldSize = Context.INSTANCE.size();
        }

        boolean someDependenciesMissed = detectedBeanSet.stream()
                .flatMap(detectedBean -> detectedBean.getInjectorSet().stream())
                .anyMatch(injector -> !injector.canBeInjected());

        if (someDependenciesMissed){
            throw new RuntimeException("some bean cannot be resolved");
        }

        detectedBeanSet.stream()
                .filter(detectedBean -> !detectedBean.getInjectorSet().isEmpty())
                .forEach(detectedBean -> {
                    detectedBean.getInjectorSet()
                            .forEach(Injector::inject);
                });



    }
}
