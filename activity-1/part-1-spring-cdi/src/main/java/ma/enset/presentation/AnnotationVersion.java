package ma.enset.presentation;

import ma.enset.service.IService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationVersion {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                "ma.enset", "ma.enset"
        );

        System.out.println("calculate data: " + applicationContext.getBean(IService.class).calculate());
    }
}
