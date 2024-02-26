package ma.enset;


import lombok.Data;
import ma.enset.metier.IMetier;
import ma.enset.repo.Context;
import ma.enset.resolvers.BeanTypeResolver;
import ma.enset.strategy.AnnotationStrategy;

public class Main {
    public static void main(String[] args) throws Exception {

        AnnotationStrategy annotationStrategy = new AnnotationStrategy("ma.enset");
        annotationStrategy.apply();

        System.out.println(Context.INSTANCE.getContext());
        System.out.println("------------------------------");

        IMetier iMetier = (IMetier) new BeanTypeResolver(IMetier.class).resolve();

        System.out.println("data: " + iMetier.calculate());



    }
}


@Data
class User{
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}



