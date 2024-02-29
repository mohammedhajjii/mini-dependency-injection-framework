package ma.enset.metier;

import ma.enset.annotation.Component;
import ma.enset.annotation.Inject;
import ma.enset.annotation.Prefer;
import ma.enset.dao.IDao;

@Component
public class MetierImpl implements IMetier{
    @Inject
    @Prefer("dao1")
    private IDao dao;
    @Inject
    @Prefer("tf")
    private Double temp;

    public void setDao( IDao dao){
        this.dao = dao;
    }

    @Override
    public double calculate() {
        return dao.getData() * temp;
    }
}
