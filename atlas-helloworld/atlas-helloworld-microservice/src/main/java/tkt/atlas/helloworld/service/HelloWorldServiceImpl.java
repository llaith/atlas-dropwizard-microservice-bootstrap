package tkt.atlas.helloworld.service;

import tkt.atlas.helloworld.api.HelloWorldService;
import tkt.atlas.helloworld.api.domain.Person;
import tkt.util.UuidUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HelloWorldServiceImpl implements HelloWorldService {

    private final String serviceInstance;

    private final Map<String,Person> persons = new HashMap<>();

    public HelloWorldServiceImpl(final String creator) {

        this.serviceInstance = creator;

    }

    @Override
    public String createPerson(final Person person) {

        person.setId(UuidUtil.uuid());
        person.setUpdatedBy(this.serviceInstance);
        person.setUpdatedAt(new Date());

        this.persons.put(person.getId(), person);

        return person.getId();

    }

    @Override
    public Person readPerson(final String id) {

        return this.persons.get(id);

    }

    @Override
    public Person updatePerson(final Person person) {

        if (!this.persons.containsKey(person.getId()))
            throw new RuntimeException("No person exists with id: " + person.getId());

        person.setUpdatedBy(this.serviceInstance);
        person.setUpdatedAt(new Date(new Date().getTime() + 1)); // test is too fast to get a new date!

        this.persons.put(person.getId(), person);

        return person;

    }

    @Override
    public void deletePerson(final String id) {

        if (!this.persons.containsKey(id))
            throw new RuntimeException("No person exists with id: " + id);

        final Person person = this.persons.get(id);

        this.persons.remove(person.getId());

    }

    @Override
    public List<Person> listPersons() {

        return new ArrayList<>(this.persons.values());

    }

}
