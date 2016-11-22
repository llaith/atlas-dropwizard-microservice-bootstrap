package tkt.atlas.helloworld.api;


import tkt.atlas.helloworld.api.domain.Person;

import java.util.List;

public interface HelloWorldService {

    String createPerson(Person person);

    Person readPerson(String id);

    Person updatePerson(Person person);

    void deletePerson(String id);

    List<Person> listPersons();

}
