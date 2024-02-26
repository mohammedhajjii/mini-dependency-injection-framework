package ma.enset.strategy;

import ma.enset.dependencies.Dependency;
import ma.enset.repo.Context;
import ma.enset.scanner.AnnotationScanner;
import ma.enset.scanner.DetectedBean;
import ma.enset.scanner.Scanner;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnnotationStrategy implements InjectionStrategy{

    private final Scanner scanner;

    public AnnotationStrategy(String ...forPackages) {
        this.scanner = new AnnotationScanner(forPackages);
    }

    @Override
    public void apply() throws Exception {
        Set<DetectedBean> detectedBeanSet = scanner.scan();

        int oldSize = 0;
        while (Context.INSTANCE.getContext().size() != detectedBeanSet.size()){
            detectedBeanSet.stream()
                    .filter(detectedBean -> detectedBean.getDependencySet()
                            .stream()
                            .allMatch(Dependency::isSatisfied)
                    ).forEach(detectedBean ->  {
                        try {
                            Object bean = detectedBean.getInitializer().initialize();
                            Context.INSTANCE.getContext().put(detectedBean.getSpecifiedName(), bean);
                        }catch (ReflectiveOperationException exception){
                            throw new RuntimeException(exception);
                        }
                    });
            if (oldSize == Context.INSTANCE.getContext().size())
                throw new Exception("detected cycle");
        }
        detectedBeanSet.stream()
                .filter(detectedBean -> !detectedBean.getInjectorSet().isEmpty())
                .forEach(detectedBean -> {
                    detectedBean.getInjectorSet()
                            .forEach(injector -> {
                                try {
                                    injector.inject();
                                } catch (ReflectiveOperationException exception) {
                                   throw new RuntimeException(exception);
                                }
                            });
                });



    }
}
