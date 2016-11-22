package tkt.atlas.helloworld.webservice;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.apache.http.auth.AuthenticationException;
import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import tkt.atlas.helloworld.api.HelloWorldService;
import tkt.atlas.helloworld.api.domain.Person;
import tkt.atlas.helloworld.webservice.jaxrs.HelloWorldResource;
import tkt.util.UuidUtil;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class HelloWorldResourceTest {

    private static HelloWorldService service = mock(HelloWorldService.class);

    @ClassRule
    public static final ResourceTestRule resources =
            ResourceTestRule.builder()
                            .setTestContainerFactory(new InMemoryTestContainerFactory())
                            .addResource(new HelloWorldResource(service))
                            .build();


    @Test
    public void testCreatePerson() throws AuthenticationException {

        final String expectedId = "12345";

        when(service.createPerson(any(Person.class))).thenReturn(expectedId);

        final Response response = resources.getJerseyTest()
                                           .target("/helloworld")
                                           .request()
                                           .post(Entity.json(new Person("Noddy")));

        final String actualId = response.readEntity(String.class);

        Assert.assertEquals(201, response.getStatus());
        Assert.assertNotNull(actualId);
        Assert.assertEquals(expectedId, actualId);

    }

    @Test
    public void testReadPerson() throws AuthenticationException {

        final String expectedId = "12345";

        when(service.readPerson(any(String.class))).thenReturn(new Person("Mr Noddy") {{
            setId(expectedId);
            setUpdatedAt(new Date());
            setUpdatedBy("Tester");
        }});

        final Response response = resources.getJerseyTest()
                                           .target("/helloworld/" + expectedId)
                                           .request()
                                           .get();

        final Person actualPerson = response.readEntity(Person.class);

        Assert.assertEquals(200, response.getStatus());
        Assert.assertNotNull(actualPerson);
        Assert.assertEquals(expectedId, actualPerson.getId());
        Assert.assertEquals("Mr Noddy", actualPerson.getName());

    }

    @Test
    public void testUpdatePerson() throws AuthenticationException {

        final String id = "12345";

        final Person roundtrip = new Person("Mr Noddy") {{
            setId(id);
            setUpdatedAt(new Date());
            setUpdatedBy("Tester");
        }};

        when(service.updatePerson(any(Person.class))).thenReturn(roundtrip);

        final Response response = resources.getJerseyTest()
                                           .target("/helloworld/" + id)
                                           .request()
                                           .put(Entity.json(roundtrip));

        final Person actualPerson = response.readEntity(Person.class);

        Assert.assertEquals(200, response.getStatus());
        Assert.assertNotNull(actualPerson);
        Assert.assertEquals(id, actualPerson.getId());
        Assert.assertEquals("Mr Noddy", actualPerson.getName());

    }

    @Test
    public void testDeletePerson() throws AuthenticationException {

        final String id = "12345";

        doNothing().when(service).deletePerson(anyString());

        final Response response = resources.getJerseyTest()
                                           .target("/helloworld/" + id)
                                           .request()
                                           .delete();

        Assert.assertEquals(204, response.getStatus());

    }

    @Test
    public void testListPersons() throws AuthenticationException {

        final List<Person> expectedPersons = this.getTestPersons();

        when(service.listPersons()).thenReturn(new ArrayList<>(expectedPersons));

        final Response response = resources.getJerseyTest()
                                           .target("/helloworld")
                                           .request()
                                           .get();

        final List<Person> actualPersons = response.readEntity(new GenericType<List<Person>>() {});

        Assert.assertEquals(200, response.getStatus());
        Assert.assertNotNull(actualPersons);
        Assert.assertEquals(expectedPersons.size(), actualPersons.size());

    }

    private List<Person> getTestPersons() {
        return Arrays.asList(
                new Person() {{
                    setId(UuidUtil.uuid());
                    setName("Mr Noddy");
                    setUpdatedBy("Tester");
                    setUpdatedAt(new Date());
                }},
                new Person() {{
                    setId(UuidUtil.uuid());
                    setName("Mr Plod");
                    setUpdatedBy("Tester");
                    setUpdatedAt(new Date());
                }}
        );

    }

}