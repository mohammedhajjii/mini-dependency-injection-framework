package ma.enset.metier;

import ma.enset.annotation.Component;
import ma.enset.annotation.Inject;
import ma.enset.annotation.Prefer;
import ma.enset.dao.IDao;

@Component
public class MetierImpl implements IMetier{

    private IDao dao;
    @Inject
    @Prefer("tf")
    private Double temp;

    @Inject
    public void setDao(@Prefer("dao2") IDao dao){
        this.dao = dao;
    }

    @Override
    public double calculate() {
        return dao.getData() * temp;
    }
}
