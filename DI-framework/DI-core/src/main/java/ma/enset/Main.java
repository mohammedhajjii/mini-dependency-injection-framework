package ma.enset;

import ma.enset.repo.Context;
import ma.enset.resolvers.BeanNameResolver;
import ma.enset.resolvers.BeanResolver;
import ma.enset.resolvers.BeanTypeResolver;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Context.INSTANCE.getContext().put("hello", "hello value");
        Context.INSTANCE.getContext().put("world", 67);
        Context.INSTANCE.getContext().put("h", 68.7);

        Context.INSTANCE.getContext()
                .values()
                .forEach(System.out::println);

        System.out.println("----------------------");
        BeanResolver beanResolver = new BeanNameResolver("hello");
        System.out.println("1- " + beanResolver.resolve());
        System.out.println("----------------------");
        BeanResolver typeResolver = new BeanTypeResolver(Integer.class);
        System.out.println("2- " + typeResolver.resolve());


        System.out.println("------------------------------------------");
        Stream<Integer> integerStream = Stream.of(1, 3, 5, 7, 9, 11);
        integerStream.forEach(System.out::println);
        System.out.println("---------------------");
        integerStream.forEach(System.out::println);
    }

}