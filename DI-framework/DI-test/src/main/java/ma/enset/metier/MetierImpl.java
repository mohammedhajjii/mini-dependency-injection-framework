package ma.enset.metier;

import ma.enset.annotation.Component;
import ma.enset.annotation.Inject;
import ma.enset.annotation.Prefer;
import ma.enset.dao.IDao;

@Component
public class MetierImpl implements IMetier{

    private IDao dao;

    @Inject
    public void setDao(@Prefer("dao1") IDao dao){
        this.dao = dao;
    }

    @Override
    public double calculate() {
        return dao.getData() * 23;
    }
}
