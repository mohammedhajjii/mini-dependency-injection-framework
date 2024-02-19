package ma.enset.dao.impl;

import ma.enset.dao.IDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


@Repository("daoV1")
public class IDaoImpl implements IDao {

    @Override
    public double getData() {
        System.out.println(IDaoImpl.class.getName() + " in use...");
        return 11243555.88;
    }
}
