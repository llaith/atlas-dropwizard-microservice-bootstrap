package tkt.atlas.helloworld.webclient.helper;

import tkt.atlas.helloworld.api.domain.Person;
import tkt.util.UuidUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class HelloWorldTestHelper {

    public List<Person> getTestPersons() {
        return Arrays.asList(
                getPerson1(),
                getPerson2());
    }

    public Person getPerson1() {
        return new Person() {{
            setId(UuidUtil.uuid());
            setName("Mr Noddy");
            setUpdatedBy("Tester");
            setUpdatedAt(new Date());
        }};
    }

    public Person getPerson2() {
        return new Person() {{
            setId(UuidUtil.uuid());
            setName("Mr Plod");
            setUpdatedBy("Tester");
            setUpdatedAt(new Date());
        }};
    }

}
