package ma.enset.initializers;

import ma.enset.annotations.Prefer;
import ma.enset.resolvers.BeanNameResolver;
import ma.enset.resolvers.BeanResolver;
import ma.enset.resolvers.BeanTypeResolver;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Initializers {

    public static Initializer resolveInitializerFromClass(Constructor<?> annotatedConstructor){
        if (annotatedConstructor.getParameterCount()  > 0){
            return ParametricConstructorInitializer.builder()
                    .constructor(annotatedConstructor)
                    .parameters(
                            Arrays.stream(annotatedConstructor.getParameters())
                                    .map(parameter -> {
                                        if (parameter.isAnnotationPresent(Prefer.class))
                                            return new BeanNameResolver(parameter.getAnnotation(Prefer.class).value());
                                        return new BeanTypeResolver(parameter.getType());
                                    }).collect(Collectors.toSet())
                    ).build();
        }
        return SimpleConstructorInitializer.builder()
                .constructor(annotatedConstructor)
                .build();
    }

    public static Initializer resolveInitializerFromMethod(Method annotatedMethod, BeanResolver instanceResolver){
        if (annotatedMethod.getParameterCount() > 0){
            return ParametricMethodInitializer.builder()
                    .init(annotatedMethod)
                    .instance(instanceResolver)
                    .parameters(
                            Arrays.stream(annotatedMethod.getParameters())
                                    .map(parameter -> {
                                        if (parameter.isAnnotationPresent(Prefer.class))
                                            return new BeanNameResolver(parameter.getAnnotation(Prefer.class).value());
                                        return new BeanTypeResolver(parameter.getType());
                                    }).collect(Collectors.toSet())
                    ).build();
        }
        return SimpleMethodInitializer.builder()
                .init(annotatedMethod)
                .instance(instanceResolver)
                .build();
    }
}
