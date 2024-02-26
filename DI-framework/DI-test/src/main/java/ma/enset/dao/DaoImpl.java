package ma.enset.dao;

import ma.enset.annotation.Component;

@Component("dao1")
public class DaoImpl implements IDao{

    @Override
    public double getData() {
        System.out.println("dao version 1");
        return 100;
    }

}
