package tkt.atlas.helloworld.api;


import rx.Observable;
import tkt.atlas.helloworld.api.domain.Person;

import java.util.List;

public interface HelloWorldAsyncService {

    Observable<String> createPerson(Person person);

    Observable<Person> readPerson(String id);

    Observable<Person> updatePerson(Person person);

    Observable<Void> deletePerson(String id);

    Observable<List<Person>> listPersons();

}
