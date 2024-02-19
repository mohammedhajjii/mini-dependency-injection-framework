package ma.enset.service.impl;

import ma.enset.dao.IDao;
import ma.enset.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("service")
public class IServiceImpl implements IService {

    private  IDao iDao;

    public IServiceImpl(@Qualifier("daoV1") IDao iDao) {
        this.iDao = iDao;
    }

    @Override
    public double calculate() {
        return iDao.getData() * 345 - 655;
    }
}
