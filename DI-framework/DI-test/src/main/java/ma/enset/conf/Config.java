package ma.enset.conf;

import ma.enset.annotations.Bean;
import ma.enset.annotations.BeansFactory;

@BeansFactory
public class Config {

    @Bean("ti")
    public double initialTemp(){
        return 1000.;
    }

    @Bean("tf")
    public double finalTemp(){
        return 50000.;
    }
}
