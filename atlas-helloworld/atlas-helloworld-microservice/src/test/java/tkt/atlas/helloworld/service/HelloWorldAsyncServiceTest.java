package tkt.atlas.helloworld.service;

import org.junit.Before;
import org.junit.Test;
import tkt.atlas.helloworld.api.HelloWorldAsyncService;
import tkt.atlas.helloworld.api.domain.Person;

import java.util.Date;

import static org.junit.Assert.*;
import static tkt.util.ObservableUtil.firstFrom;
import static tkt.util.ObservableUtil.testSubscriptionOn;

/**
 *
 */
public class HelloWorldAsyncServiceTest {

    private HelloWorldAsyncService service;

    @Before
    public void setup() {

        this.service = new HelloWorldAsyncServiceImpl("Tester");

    }

    @Test
    public void crudTest() throws Exception {

        // create a new person, check they have no id
        final Person person = new Person("Mr Noddy");
        assertNull(person.getId());

        // save it and check the id is set
        final String id = firstFrom(testSubscriptionOn(service.createPerson(person)));
        assertNotNull(id);

        // read the person back and check the details
        final Person read = firstFrom(testSubscriptionOn(this.service.readPerson(id)));
        assertNotNull(read);
        assertEquals(person.getName(), read.getName());
        assertEquals(read.getUpdatedBy(), "Tester");

        // capture the date, we'll check again after update
        final Date createDate = read.getUpdatedAt();
        assertNotNull(createDate);

        // update and check its worked 
        read.setName("Mr Plod");
        final Person updated = firstFrom(testSubscriptionOn(this.service.updatePerson(read)));
        assertNotNull(updated);
        assertEquals(read.getId(), updated.getId());
        assertEquals(read.getName(), updated.getName());
        assertNotEquals(createDate, updated.getUpdatedAt());

        // delete and check it's gone
        testSubscriptionOn(this.service.deletePerson(updated.getId()));
        final Person deleted = firstFrom(testSubscriptionOn(this.service.readPerson(updated.getId())));
        assertNull(deleted);

    }

}