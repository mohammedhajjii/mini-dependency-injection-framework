package ma.enset.presentation.dynamicinst;

import ma.enset.dao.IDao;
import ma.enset.service.IService;
import java.io.File;
import java.util.Scanner;

public class DynamicInstantiation {

    public static void main(String[] args) throws Exception {

        /**
         * make a scanner for file config.txt :
         * path: src/main/resources/config.txt
         */
        Scanner scanner = new Scanner(new File(
                DynamicInstantiation.class
                        .getClassLoader()
                        .getResource("config.txt")
                        .toURI()
        ));

        /**
         * instantiate IDao implementation using java reflection:
         */
        IDao iDao = (IDao) Class.forName(scanner.nextLine())
                .getDeclaredConstructor()
                .newInstance();

        /**
         * instantiate IService implementation using java reflection:
         * during creating this instance of IService we use dependency injection
         * by constructor
         */
        IService iService = (IService) Class.forName(scanner.nextLine())
                .getDeclaredConstructor(IDao.class)
                .newInstance(iDao);


        System.out.println("calculate data: " + iService.calculate());




    }
}
