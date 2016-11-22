package tkt.atlas.helloworld.webservice;


import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import tkt.atlas.helloworld.service.HelloWorldServiceImpl;
import tkt.atlas.helloworld.webservice.jaxrs.HelloWorldResource;


public class HelloWorldApi extends Application<HelloWorldConfiguration> {

    @Override
    public void initialize(final Bootstrap<HelloWorldConfiguration> bootstrap) {

        // blank

    }

    @Override
    public void run(final HelloWorldConfiguration configuration, final Environment environment) throws Exception {

        // add a health check
        environment.healthChecks().register("HealthCheck1", new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return Result.healthy();
            }
        });

        // add resources
        environment.jersey().register(new HelloWorldResource(new HelloWorldServiceImpl(configuration.getServiceInstance())));

    }

    public static void main(String[] args) throws Exception {
        new HelloWorldApi().run(args);
    }

}
