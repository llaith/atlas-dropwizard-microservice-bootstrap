package tkt.atlas.helloworld.service;

import rx.Observable;
import tkt.atlas.helloworld.api.HelloWorldAsyncService;
import tkt.atlas.helloworld.api.HelloWorldService;
import tkt.atlas.helloworld.api.domain.Person;

import java.util.List;


public class HelloWorldServiceAsyncImpl implements HelloWorldAsyncService {

    private final HelloWorldService service;

    public HelloWorldServiceAsyncImpl(final HelloWorldService service) {

        this.service = service;

    }

    @Override
    public Observable<String> createPerson(final Person person) {

        return Observable.fromCallable(() -> this.service.createPerson(person));

    }

    @Override
    public Observable<Person> readPerson(final String id) {

        return Observable.fromCallable(() -> this.service.readPerson(id));

    }

    @Override
    public Observable<Person> updatePerson(final Person person) {

        return Observable.fromCallable(() -> this.service.updatePerson(person));

    }

    @Override
    public Observable<Void> deletePerson(final String id) {

        return Observable.fromCallable(() -> {
            this.service.deletePerson(id);
            return null;
        });

    }

    @Override
    public Observable<List<Person>> listPersons() {

        return Observable.fromCallable(() -> this.service.listPersons());

    }

}
