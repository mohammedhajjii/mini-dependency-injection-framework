package ma.enset.converters;

import ma.enset.annotation.Bean;
import ma.enset.annotation.Component;
import ma.enset.annotation.Inject;
import ma.enset.initializers.*;
import ma.enset.injectors.Injector;
import ma.enset.injectors.Injectors;
import ma.enset.resolvers.BeanNameResolver;
import ma.enset.resolvers.BeanResolver;
import ma.enset.scanner.DetectedBean;
import java.beans.Introspector;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Converters {


    public static DetectedBean beanFactoryClassToDetectedBean(Class<?> beanFactoryClass){

        try {
            return DetectedBean.builder()
                    .specifiedName(Introspector.decapitalize(beanFactoryClass.getSimpleName()))
                    .initializer(new SimpleConstructorInitializer(beanFactoryClass.getConstructor()))
                    .injectorSet(Set.of())
                    .build();
        }catch (NoSuchMethodException exception){
            throw new RuntimeException(exception);
        }


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


        Set<Field> annotatedFields = Arrays.stream(componentClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .collect(Collectors.toSet());

        Set<Method> annotatedSetters = Arrays.stream(componentClass.getDeclaredMethods())
                .filter(setter -> setter.isAnnotationPresent(Inject.class))
                .collect(Collectors.toSet());

        BeanResolver instanceResolver = new BeanNameResolver(specifiedName);
        Set<Injector> injectorSet = Injectors.resolveInjectorsFromFields(annotatedFields, instanceResolver);
        injectorSet.addAll(Injectors.resolveInjectorsFromSetters(annotatedSetters, instanceResolver));

        detectedBean.setInjectorSet(injectorSet);

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



