package ma.enset;


import ma.enset.core.ApplicationContext;
import ma.enset.metier.IMetier;
import ma.enset.resolvers.BeanTypeResolver;
import ma.enset.strategies.AnnotationStrategy;

public class Main {
    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ApplicationContext(new AnnotationStrategy("ma.enset"));

        IMetier iMetier = (IMetier) context.getBean(IMetier.class);

        System.out.println("data: " + iMetier.calculate());


    }
}





