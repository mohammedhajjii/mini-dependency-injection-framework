package ma.enset.injectors;

import ma.enset.annotations.Prefer;
import ma.enset.resolvers.BeanNameResolver;
import ma.enset.resolvers.BeanResolver;
import ma.enset.resolvers.BeanTypeResolver;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Injectors {

    public static Injector resolveInjectorsFromField(Field annotatedField, BeanResolver instanceResolver){
        FieldInjector injector = FieldInjector.builder()
                .field(annotatedField)
                .instance(instanceResolver)
                .build();
        if (annotatedField.isAnnotationPresent(Prefer.class))
            injector.setInjectedValue(new BeanNameResolver(annotatedField.getAnnotation(Prefer.class).value()));
        else
            injector.setInjectedValue(new BeanTypeResolver(annotatedField.getType()));
        return injector;
    }

    public static Injector resolveInjectorsFromSetter(Method annotatedSetter, BeanResolver instanceResolver){
        SetterInjector injector = SetterInjector.builder()
                .setter(annotatedSetter)
                .instance(instanceResolver)
                .build();
        Parameter parameter = annotatedSetter.getParameters()[0];
        if (parameter.isAnnotationPresent(Prefer.class))
            injector.setParameter(new BeanNameResolver(parameter.getAnnotation(Prefer.class).value()));
        else
            injector.setParameter(new BeanTypeResolver(parameter.getType()));
        return injector;
    }



}
