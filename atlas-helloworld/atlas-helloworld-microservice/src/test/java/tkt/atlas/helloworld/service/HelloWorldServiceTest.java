package tkt.atlas.helloworld.service;

import org.junit.Before;
import org.junit.Test;
import tkt.atlas.helloworld.api.HelloWorldService;
import tkt.atlas.helloworld.api.domain.Person;

import java.util.Date;

import static org.junit.Assert.*;

/**
 *
 */
public class HelloWorldServiceTest {

    private HelloWorldService service;

    @Before
    public void setup() {

        this.service = new HelloWorldServiceImpl("Tester");

    }

    @Test
    public void crudTest() throws Exception {

        // create a new person, check they have no id
        final Person person = new Person("Mr Noddy");
        assertNull(person.getId());

        // save it and check the id is set
        final String id = this.service.createPerson(person);
        assertNotNull(id);

        // read the person back and check the details
        final Person read = this.service.readPerson(id);
        assertEquals(person.getName(), read.getName());
        assertEquals(read.getUpdatedBy(), "Tester");

        // capture the date, we'll check again after update
        final Date createDate = read.getUpdatedAt();
        assertNotNull(createDate);

        // update and check its worked 
        read.setName("Mr Plod");
        final Person updated = this.service.updatePerson(read);
        assertEquals(read.getId(), updated.getId());
        assertEquals(read.getName(), updated.getName());
        assertNotEquals(createDate, updated.getUpdatedAt());

        // delete and check it's gone
        this.service.deletePerson(updated.getId());
        final Person deleted = this.service.readPerson(updated.getId());
        assertNull(deleted);

    }

}