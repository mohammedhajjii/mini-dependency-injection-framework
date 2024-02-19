package ma.enset.dao.impl;

import ma.enset.dao.IDao;

public class DaoImplVersion2 implements IDao {
    @Override
    public double getData() {
        System.out.println("get data version 2");
        return 10000000;
    }
}
