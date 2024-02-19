package ma.enset.service.impl;

import ma.enset.dao.IDao;
import ma.enset.service.IService;

public class IServiceImpl implements IService {

    /**
     * create a dependency using IDao interface
     * so this impl can accecpt any kind of class
     * that implementing IDao interface
     * ==> week coupling
     */
    private IDao dao;

    /**
     * constructor of IServiceImpl, for injection:
     * @param dao: IDAO implementation that will be used by
     *           this IServiceImpl version:
     */
    public IServiceImpl(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calculate() {
        double data = dao.getData();
        return data * 34;
    }
}
