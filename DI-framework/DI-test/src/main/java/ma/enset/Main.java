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



        IMetier iMetier = (IMetier) new BeanTypeResolver(IMetier.class).resolve();

        System.out.println("data: " + iMetier.calculate());


        System.out.println(double.class.isAssignableFrom(double.class));


    }
}





