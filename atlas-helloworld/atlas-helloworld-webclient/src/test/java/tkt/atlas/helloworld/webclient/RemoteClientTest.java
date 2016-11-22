package tkt.atlas.helloworld.webclient;

import io.dropwizard.testing.junit.DropwizardClientRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
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

public class RemoteClientTest {

    private static HelloWorldService service = mock(HelloWorldService.class);

    private HelloWorldRemoteClient client;

    @ClassRule
    public static final DropwizardClientRule dropwizard = new DropwizardClientRule(
            new HelloWorldResource(service));

    @Before
    public void setup() {

        final String url = dropwizard.baseUri().toString() + "/";

        this.client = new HelloWorldRemoteClient(url);

    }

    @Test
    public void testCreatePerson() throws IOException {

        final String expectedId = "12345";

        when(service.createPerson(any(Person.class))).thenReturn(expectedId);

        final String actualId = client.createPerson(new Person("Mr Noddy"));

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

        final Person actualPerson = client.readPerson(expectedId);

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

        final Person actualPerson = client.updatePerson(roundtrip);

        Assert.assertNotNull(actualPerson);
        Assert.assertEquals(id, actualPerson.getId());
        Assert.assertEquals("Mr Noddy", actualPerson.getName());

    }

    @Test
    public void testDeletePerson() throws IOException {

        final String id = "12345";

        doNothing().when(service).deletePerson(anyString());

        client.deletePerson(id);

    }

    @Test
    public void testListPersons() throws IOException {

        final List<Person> expected = new HelloWorldTestHelper().getTestPersons();

        when(service.listPersons()).thenReturn(expected);

        final List<Person> actual = client.listPersons();

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());

    }


}