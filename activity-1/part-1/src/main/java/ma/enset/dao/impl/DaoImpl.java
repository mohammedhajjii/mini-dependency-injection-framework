package ma.enset.dao.impl;

import ma.enset.dao.IDao;

public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("get data version 1");
        return 666181715.65;
    }
}
