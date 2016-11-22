package tkt.atlas.helloworld.webservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class HelloWorldConfiguration extends Configuration {

    @JsonProperty
    @NotNull
    private String serviceInstance;

    public HelloWorldConfiguration() {
        super();
    }

    public String getServiceInstance() {
        return serviceInstance;
    }

    public void setServiceInstance(final String serviceInstance) {
        this.serviceInstance = serviceInstance;
    }
    
}
