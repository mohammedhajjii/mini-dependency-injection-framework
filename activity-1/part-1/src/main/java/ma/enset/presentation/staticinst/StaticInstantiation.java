package ma.enset.presentation.staticinst;

import ma.enset.dao.IDao;
import ma.enset.dao.impl.DaoImpl;
import ma.enset.service.IService;
import ma.enset.service.impl.IServiceImpl;

public class StaticInstantiation {

    public static void main(String[] args) {
        /**
         * IDAO implementation version 1:
         */
        IDao dao = new DaoImpl();
        /**
         * injecting dao using static injection by constructor:
         * that is why we create a constructor in IServiceImpl class:
         */
        IService iService = new IServiceImpl(dao);

        System.out.println("calculate data: " + iService.calculate());

    }
}
