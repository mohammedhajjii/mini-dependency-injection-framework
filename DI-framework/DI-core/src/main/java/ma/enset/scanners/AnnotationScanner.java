package ma.enset.scanners;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ma.enset.annotation.Bean;
import ma.enset.annotation.BeansFactory;
import ma.enset.annotation.Component;
import ma.enset.converters.Converters;
import ma.enset.resolvers.BeanResolver;
import ma.enset.resolvers.BeanTypeResolver;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@AllArgsConstructor
public class AnnotationScanner implements Scanner{

    private String[] scannedPackages;

    @Override
    public Set<DetectedBean> scan() {
        Reflections reflection = new Reflections(new ConfigurationBuilder().forPackages(scannedPackages));
        Set<Class<?>> beansFactorySet =   reflection.getTypesAnnotatedWith(BeansFactory.class);
       
        return Stream.concat(
               beansFactorySet.stream()
                       .map(Converters::beanFactoryClassToDetectedBean),
               Stream.concat(
                       beansFactorySet.stream()
                               .flatMap(
                                       factory -> {
                                           BeanResolver factoryBeanResolver = new BeanTypeResolver(factory);
                                           return Arrays.stream(factory.getDeclaredMethods())
                                                   .filter(method -> method.isAnnotationPresent(Bean.class))
                                                   .map(method -> Converters.beanMethodToDetectedBean(method, factoryBeanResolver));
                                       }
                               ),
                       reflection.getTypesAnnotatedWith(Component.class)
                               .stream()
                               .map(Converters::componentClassToDetectedBean)
               )
        ).collect(Collectors.toSet());
    }
}
