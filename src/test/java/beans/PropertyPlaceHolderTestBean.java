package beans;


import org.springframework.beans.factory.annotation.Value;

public class PropertyPlaceHolderTestBean{
    @Value("${instance.name:'UnDefined'}")
    String instanceName;
    public String toString(){
        return instanceName;
    }
}
