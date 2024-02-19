package ma.enset.dao.impl;

import ma.enset.dao.IDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


@Repository("daoV2")
public class IDaoImplV2 implements IDao {
    @Override
    public double getData() {
        System.out.println(IDaoImplV2.class.getName() + " in use...");
        return 6888654.9;
    }
}
