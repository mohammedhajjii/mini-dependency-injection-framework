package ma.enset.dao;

import ma.enset.annotation.Component;

@Component("dao2")
public class DaoImplV2 implements IDao{
    @Override
    public double getData() {
        System.out.println("dao version 2");
        return 200;
    }
}
