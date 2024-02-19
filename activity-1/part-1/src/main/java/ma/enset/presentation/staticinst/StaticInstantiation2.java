package ma.enset.presentation.staticinst;

import ma.enset.dao.IDao;
import ma.enset.dao.impl.DaoImpl;
import ma.enset.dao.impl.DaoImplVersion2;
import ma.enset.service.IService;
import ma.enset.service.impl.IServiceImpl;

public class StaticInstantiation2 {
    public static void main(String[] args) {
        /**
         * IDAO implementation version 2:
         */
        IDao dao = new DaoImplVersion2();
        /**
         * injecting dao using static injection by constructor:
         * that is why we create a constructor in IServiceImpl class:
         */
        IService iService = new IServiceImpl(dao);

        System.out.println("calculate data: " + iService.calculate());

    }
}
