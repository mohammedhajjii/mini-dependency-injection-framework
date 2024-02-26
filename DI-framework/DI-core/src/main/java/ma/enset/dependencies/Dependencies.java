package ma.enset.dependencies;

import ma.enset.annotation.Prefer;
import ma.enset.resolvers.BeanNameResolver;
import ma.enset.resolvers.BeanResolver;
import ma.enset.resolvers.BeanTypeResolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dependencies {

    public static Set<Dependency> resolveDependenciesFromFields(Set<Field> annotatedFields){
        return annotatedFields.stream()
                .map(field -> {
                    if (field.isAnnotationPresent(Prefer.class))
                        return new BeanNameDependency(field.getAnnotation(Prefer.class).value());
                    return new BeanTypeDependency(field.getType());
                }).collect(Collectors.toSet());
    }

    public static Set<Dependency> resolveDependenciesFromSetters(Set<Method> annotatedSetters){
        return annotatedSetters.stream()
                .map(setter -> {
                        Parameter parameter = setter.getParameters()[0];
                        if (parameter.isAnnotationPresent(Prefer.class))
                            return new BeanNameDependency(parameter.getAnnotation(Prefer.class).value());
                        return new BeanTypeDependency(parameter.getType());
                }).collect(Collectors.toSet());
    }

    public static Set<Dependency> resolveDependenciesFromConstructor(Constructor<?> annotatedConstructor){
        return Arrays.stream(annotatedConstructor.getParameters())
                .map(parameter -> {
                    if (parameter.isAnnotationPresent(Prefer.class))
                        return new BeanNameDependency(parameter.getAnnotation(Prefer.class).value());
                    return new BeanTypeDependency(parameter.getType());
                }).collect(Collectors.toSet());
    }

    public static Set<Dependency> resolveDependenciesFromMethod(Method annotatedMethod){
        return Arrays.stream(annotatedMethod.getParameters())
                .map(parameter -> {
                    if (parameter.isAnnotationPresent(Prefer.class))
                        return new BeanNameDependency(parameter.getAnnotation(Prefer.class).value());
                    return new BeanTypeDependency(parameter.getType());
                }).collect(Collectors.toSet());
    }

    public static Dependency resolveDependencyFrom(BeanResolver beanResolver){
        if (beanResolver instanceof BeanNameResolver resolver)
            return new BeanNameDependency(resolver.getName());
        return new BeanTypeDependency(((BeanTypeResolver) beanResolver).getType());
    }
}
