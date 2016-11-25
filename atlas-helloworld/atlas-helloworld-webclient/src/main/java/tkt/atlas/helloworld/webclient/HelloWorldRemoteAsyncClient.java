package tkt.atlas.helloworld.webclient;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import tkt.atlas.helloworld.api.HelloWorldAsyncService;
import tkt.atlas.helloworld.api.domain.Person;

import java.util.List;

/**
 *
 */
public class HelloWorldRemoteAsyncClient implements HelloWorldAsyncService {

    private final HelloWorldRetrofitAsyncClient retrofit;

    public HelloWorldRemoteAsyncClient(final String baseUrl) {

        if (!baseUrl.endsWith("/")) throw new RuntimeException("The baseUrl parameter must end with a '/'");

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()) // order matters, this should be last
                .build();

        this.retrofit = retrofit.create(HelloWorldRetrofitAsyncClient.class);

    }

    @Override
    public Observable<String> createPerson(final Person person) {

        return this.retrofit.createPerson(person);

    }

    @Override
    public Observable<Person> readPerson(final String id) {

        return this.retrofit.readPerson(id);

    }

    @Override
    public Observable<Person> updatePerson(final Person person) {

        return this.retrofit.updatePerson(person.getId(), person);

    }

    @Override
    public Observable<Void> deletePerson(final String id) {

        this.retrofit.deletePerson(id);

        return null;

    }

    @Override
    public Observable<List<Person>> listPersons() {

        return this.retrofit.listPersons();

    }

}
