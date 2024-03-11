package ma.enset.converters;

import lombok.SneakyThrows;
import ma.enset.annotation.Bean;
import ma.enset.annotation.Component;
import ma.enset.annotation.Inject;
import ma.enset.initializers.*;
import ma.enset.injectors.Injector;
import ma.enset.injectors.Injectors;
import ma.enset.resolvers.BeanNameResolver;
import ma.enset.resolvers.BeanResolver;
import ma.enset.scanners.DetectedBean;
import java.beans.Introspector;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Converters {


    @SneakyThrows(NoSuchMethodException.class)
    public static DetectedBean beanFactoryClassToDetectedBean(Class<?> beanFactoryClass){

        return DetectedBean.builder()
                .specifiedName(Introspector.decapitalize(beanFactoryClass.getSimpleName()))
                .initializer(new SimpleConstructorInitializer(beanFactoryClass.getConstructor()))
                .injectorSet(Set.of())
                .build();

    }

    public static DetectedBean componentClassToDetectedBean(Class<?> componentClass) {

        String annotationValue = componentClass.getAnnotation(Component.class).value();
        String specifiedName = annotationValue.isBlank() ?
                Introspector.decapitalize(componentClass.getSimpleName()) : annotationValue;

        Constructor<?> annotatedConstructor = Arrays.stream(componentClass.getConstructors())
                .filter(cst -> cst.isAnnotationPresent(Inject.class))
                .findFirst()
                .orElseGet(() -> {
                    try {
                        return componentClass.getConstructor();
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });

        DetectedBean detectedBean = DetectedBean.builder()
                .specifiedName(specifiedName)
                .initializer(Initializers.resolveInitializerFromClass(annotatedConstructor))
                .injectorSet(Set.of())
                .build();

        if (annotatedConstructor.getParameterCount() > 0)
            return detectedBean;

        BeanResolver instanceResolver = new BeanNameResolver(specifiedName);
        Stream<Injector> injectorStream = Stream.concat(
                Arrays.stream(componentClass.getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(Inject.class))
                        .map(field -> Injectors.resolveInjectorsFromField(field, instanceResolver))
                ,
                Arrays.stream(componentClass.getDeclaredMethods())
                        .filter(setter -> setter.isAnnotationPresent(Inject.class))
                        .map(setter -> Injectors.resolveInjectorsFromSetter(setter, instanceResolver))
        );


        detectedBean.setInjectorSet(injectorStream.collect(Collectors.toSet()));

        return detectedBean;
    }

    public static DetectedBean beanMethodToDetectedBean(Method beanMethod, BeanResolver instanceResolver){

        String annotationValue = beanMethod.getAnnotation(Bean.class).value();
        String specifiedName = annotationValue.isBlank() ? beanMethod.getName() : annotationValue;

       return  DetectedBean.builder()
               .specifiedName(specifiedName)
               .initializer(Initializers.resolveInitializerFromMethod(beanMethod, instanceResolver))
               .injectorSet(Set.of())
               .build();
    }

}



