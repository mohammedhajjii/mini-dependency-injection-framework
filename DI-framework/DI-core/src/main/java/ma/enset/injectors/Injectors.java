package ma.enset.injectors;

import ma.enset.annotation.Prefer;
import ma.enset.resolvers.BeanNameResolver;
import ma.enset.resolvers.BeanResolver;
import ma.enset.resolvers.BeanTypeResolver;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;
import java.util.stream.Collectors;

public class Injectors {

    public static Set<Injector> resolveInjectorsFromFields(Set<Field> annotatedFields, BeanResolver instanceResolver){
        return annotatedFields.stream()
                .map(field -> {
                        FieldInjector injector = FieldInjector.builder()
                                .field(field)
                                .instance(instanceResolver)
                                .build();
                        if (field.isAnnotationPresent(Prefer.class))
                            injector.setInjectedValue(new BeanNameResolver(field.getAnnotation(Prefer.class).value()));
                        else
                            injector.setInjectedValue(new BeanTypeResolver(field.getType()));
                        return injector;
                }).collect(Collectors.toSet());
    }

    public static Set<Injector> resolveInjectorsFromSetters(Set<Method> annotatedSetters, BeanResolver instanceResolver){
        return annotatedSetters.stream()
                .map(setter -> {
                        SetterInjector injector = SetterInjector.builder()
                                .setter(setter)
                                .instance(instanceResolver)
                                .build();
                        Parameter parameter = setter.getParameters()[0];
                        if (parameter.isAnnotationPresent(Prefer.class))
                            injector.setParameter(new BeanNameResolver(parameter.getAnnotation(Prefer.class).value()));
                        else
                            injector.setParameter(new BeanTypeResolver(parameter.getType()));
                        return injector;
                }).collect(Collectors.toSet());
    }



}
