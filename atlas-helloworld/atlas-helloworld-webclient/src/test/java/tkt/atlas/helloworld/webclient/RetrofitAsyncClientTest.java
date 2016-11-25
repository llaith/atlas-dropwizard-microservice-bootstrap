package tkt.atlas.helloworld.webclient;

import io.dropwizard.testing.junit.DropwizardClientRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import tkt.atlas.helloworld.api.HelloWorldService;
import tkt.atlas.helloworld.api.domain.Person;
import tkt.atlas.helloworld.webclient.helper.HelloWorldTestHelper;
import tkt.atlas.helloworld.webservice.jaxrs.HelloWorldResource;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static tkt.util.ObservableUtil.firstFrom;
import static tkt.util.ObservableUtil.testSubscriptionOn;

public class RetrofitAsyncClientTest {

    private static HelloWorldService service = mock(HelloWorldService.class);

    private HelloWorldRetrofitAsyncClient client;

    @ClassRule
    public static final DropwizardClientRule dropwizard = new DropwizardClientRule(
            new HelloWorldResource(service));

    @Before
    public void setup() {

        final String url = dropwizard.baseUri().toString() + "/";

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()) // order matters, this should be after the scalar one
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.client = retrofit.create(HelloWorldRetrofitAsyncClient.class);

    }

    @Test
    public void testCreatePerson() throws IOException {

        final String expectedId = "12345";

        when(service.createPerson(any(Person.class))).thenReturn(expectedId);

        final String actualId = firstFrom(testSubscriptionOn(client.createPerson(new Person("Mr Noddy"))));

        assertEquals(expectedId, actualId);

    }

    @Test
    public void testReadPerson() throws IOException {

        final String expectedId = "12345";

        when(service.readPerson(any(String.class))).thenReturn(new Person("Mr Noddy") {{
            setId(expectedId);
            setUpdatedAt(new Date());
            setUpdatedBy("Tester");
        }});

        final Person actualPerson = firstFrom(testSubscriptionOn(client.readPerson(expectedId)));

        Assert.assertNotNull(actualPerson);
        Assert.assertEquals(expectedId, actualPerson.getId());
        Assert.assertEquals("Mr Noddy", actualPerson.getName());

    }

    @Test
    public void testUpdatePerson() throws IOException {

        final String id = "12345";

        final Person roundtrip = new Person("Mr Noddy") {{
            setId(id);
            setUpdatedAt(new Date());
            setUpdatedBy("Tester");
        }};

        when(service.updatePerson(any(Person.class))).thenReturn(roundtrip);

        final Person actualPerson = firstFrom(testSubscriptionOn(client.updatePerson(id, roundtrip)));

        Assert.assertNotNull(actualPerson);
        Assert.assertEquals(id, actualPerson.getId());
        Assert.assertEquals("Mr Noddy", actualPerson.getName());

    }

    @Test
    public void testDeletePerson() throws IOException {

        final String id = "12345";

        doNothing().when(service).deletePerson(anyString());

        firstFrom(testSubscriptionOn(client.deletePerson(id)));

    }

    @Test
    public void testListPersons() throws IOException {

        final List<Person> expected = new HelloWorldTestHelper().getTestPersons();

        when(service.listPersons()).thenReturn(expected);

        final List<Person> actual = firstFrom(testSubscriptionOn(client.listPersons()));

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());

    }

}