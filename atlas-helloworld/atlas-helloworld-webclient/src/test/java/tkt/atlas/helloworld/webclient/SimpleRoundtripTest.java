package tkt.atlas.helloworld.webclient;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import tkt.atlas.helloworld.api.domain.Person;
import tkt.atlas.helloworld.webservice.HelloWorldApi;
import tkt.atlas.helloworld.webservice.HelloWorldConfiguration;

import java.io.IOException;
import java.util.List;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SimpleRoundtripTest {

    private HelloWorldRetrofitClient client;

    @ClassRule
    public static final DropwizardAppRule<HelloWorldConfiguration> dropwizard = new DropwizardAppRule<>(
            HelloWorldApi.class,
            resourceFilePath("test-config.yml"));

    @Before
    public void setup() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(createBaseUri())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()) // order matters, this should be last
                .build();

        this.client = retrofit.create(HelloWorldRetrofitClient.class);

    }

    @Test
    public void testBasicIntegration() throws IOException {

        final Person person = new Person("Mr Noddy");

        final String id = client.createPerson(person).execute().body();

        assertNotNull(id);

        final List<Person> persons = client.listPersons().execute().body();

        assertNotNull(persons);
        assertEquals(1, persons.size());
        assertEquals(person.getName(), persons.get(0).getName());
        assertEquals(id, persons.get(0).getId());

    }

    private static String createBaseUri() {
        return "http://localhost:" + dropwizard.getLocalPort() + "/";
    }

}