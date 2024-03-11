package ma.enset.conf;

import ma.enset.annotation.Bean;
import ma.enset.annotation.BeansFactory;

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
