package ma.enset.converters;

import ma.enset.annotation.Bean;
import ma.enset.annotation.Component;
import ma.enset.annotation.Inject;
import ma.enset.annotation.Prefer;
import ma.enset.dependencies.Dependencies;
import ma.enset.initializers.*;
import ma.enset.injectors.FieldInjector;
import ma.enset.injectors.Injector;
import ma.enset.injectors.Injectors;
import ma.enset.injectors.SetterInjector;
import ma.enset.resolvers.BeanNameResolver;
import ma.enset.resolvers.BeanResolver;
import ma.enset.resolvers.BeanTypeResolver;
import ma.enset.dependencies.BeanNameDependency;
import ma.enset.dependencies.BeanTypeDependency;
import ma.enset.dependencies.Dependency;
import ma.enset.scanner.DetectedBean;
import java.beans.Introspector;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Converters {


    public static DetectedBean beanFactoryClassToDetectedBean(Class<?> beanFactoryClass) throws NoSuchMethodException {

        return DetectedBean.builder()
                .specifiedName(Introspector.decapitalize(beanFactoryClass.getSimpleName()))
                .initializer(new SimpleConstructorInitializer(beanFactoryClass.getConstructor()))
                .dependencySet(Set.of())
                .injectorSet(Set.of())
                .build();


    }

    public static DetectedBean componentClassToDetectedBean(Class<?> componentClass) throws NoSuchMethodException {

        String annotationValue = componentClass.getAnnotation(Component.class).value();
        String specifiedName = switch (annotationValue){
            case "" -> Introspector.decapitalize(componentClass.getSimpleName());
            default -> annotationValue;
        };

        System.out.println("1 --- ");
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
        System.out.println("2 --- ");

        if (annotatedConstructor.getParameterCount() > 0){
            return DetectedBean.builder()
                    .specifiedName(specifiedName)
                    .initializer(Initializers.resolveInitializerFromClass(annotatedConstructor))
                    .dependencySet(Dependencies.resolveDependenciesFromConstructor(annotatedConstructor))
                    .injectorSet(Set.of())
                    .build();
        }

        Set<Field> annotatedFields = Arrays.stream(componentClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .collect(Collectors.toSet());

        Set<Method> annotatedSetters = Arrays.stream(componentClass.getDeclaredMethods())
                .filter(setter -> setter.isAnnotationPresent(Inject.class))
                .collect(Collectors.toSet());


        Set<Dependency> dependencySet = Dependencies.resolveDependenciesFromFields(annotatedFields);
        dependencySet.addAll(Dependencies.resolveDependenciesFromSetters(annotatedSetters));

        BeanResolver instanceResolver = new BeanNameResolver(specifiedName);
        Set<Injector> injectorSet = Injectors.resolveInjectorsFromFields(annotatedFields, instanceResolver);
        injectorSet.addAll(Injectors.resolveInjectorsFromSetters(annotatedSetters, instanceResolver));

        return DetectedBean.builder()
                .specifiedName(specifiedName)
                .initializer(Initializers.resolveInitializerFromClass(annotatedConstructor))
                .dependencySet(dependencySet)
                .injectorSet(injectorSet)
                .build();
    }

    public static DetectedBean beanMethodToDetectedBean(Method beanMethod, BeanResolver instanceResolver){

        String annotationValue = beanMethod.getAnnotation(Bean.class).value();
        String specifiedName = switch (annotationValue){
            case "" -> beanMethod.getName();
            default -> annotationValue;
        };

        Set<Dependency> dependencySet = new HashSet<>();
        dependencySet.add(Dependencies.resolveDependencyFrom(instanceResolver));

        if (beanMethod.getParameterCount() > 0){
            dependencySet.addAll(Dependencies.resolveDependenciesFromMethod(beanMethod));
            return DetectedBean.builder()
                    .specifiedName(specifiedName)
                    .initializer(Initializers.resolveInitializerFromMethod(beanMethod, instanceResolver))
                    .dependencySet(dependencySet)
                    .injectorSet(Set.of())
                    .build();
        }


        return DetectedBean.builder()
                .specifiedName(specifiedName)
                .initializer(Initializers.resolveInitializerFromMethod(beanMethod, instanceResolver))
                .dependencySet(dependencySet)
                .injectorSet(Set.of())
                .build();

    }

}


