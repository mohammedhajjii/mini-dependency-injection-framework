package ma.enset.scanner;

import lombok.Data;
import ma.enset.annotation.Bean;
import ma.enset.annotation.BeansFactory;
import ma.enset.annotation.Component;
import ma.enset.converters.Converters;
import ma.enset.resolvers.BeanResolver;
import ma.enset.resolvers.BeanTypeResolver;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class AnnotationScanner implements Scanner{

    private Reflections reflection;
    private String[] forPackages;

    public AnnotationScanner(String ...forPackages) {
        this.forPackages = forPackages;
        this.reflection = new Reflections(
            new ConfigurationBuilder()
                    .forPackages(forPackages)
                    .setScanners(
                            Scanners.TypesAnnotated,
                            Scanners.MethodsAnnotated
                    )
        );
    }

    @Override
    public Set<DetectedBean> scan() {
       Set<Class<?>> beansFactorySet =   reflection.getTypesAnnotatedWith(BeansFactory.class);
       Set<DetectedBean> detectedBeanMethodSet = beansFactorySet.stream()
               .flatMap(
                       factory -> {
                           BeanResolver factoryBeanResolver = new BeanTypeResolver(factory);
                           return Arrays.stream(factory.getDeclaredMethods())
                                   .filter(method -> method.isAnnotationPresent(Bean.class))
                                   .map(method -> Converters.beanMethodToDetectedBean(method, factoryBeanResolver));
                       }
               ).collect(Collectors.toSet());


       Set<DetectedBean>  detectedBeanSet = beansFactorySet.stream()
               .map(Converters::beanFactoryClassToDetectedBean)
               .collect(Collectors.toSet());

        Set<DetectedBean> detectedCompoenentSet = reflection.getTypesAnnotatedWith(Component.class)
                .stream()
                .map(Converters::componentClassToDetectedBean).collect(Collectors.toSet());

       detectedBeanSet.addAll(detectedBeanMethodSet);
       detectedBeanSet.addAll(detectedCompoenentSet);
       return detectedBeanSet;
    }
}
