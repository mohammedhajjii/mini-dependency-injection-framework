package ma.enset.metier;

import ma.enset.annotations.Component;
import ma.enset.annotations.Inject;
import ma.enset.annotations.Prefer;
import ma.enset.dao.IDao;

@Component
public class MetierImpl implements IMetier{
    @Inject
    @Prefer("dao2")
    private IDao dao;
    @Inject
    @Prefer("ti")
    private Double temp;

    public void setDao( IDao dao){
        this.dao = dao;
    }

    @Override
    public double calculate() {
        return dao.getData() * temp;
    }
}
