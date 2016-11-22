package tkt.atlas.helloworld.webclient;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import tkt.atlas.helloworld.api.HelloWorldService;
import tkt.atlas.helloworld.api.domain.Person;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class HelloWorldRemoteClient implements HelloWorldService {

    private final HelloWorldRetrofitClient retrofit;

    public HelloWorldRemoteClient(final String baseUrl) {

        if (!baseUrl.endsWith("/")) throw new RuntimeException("The baseUrl parameter must end with a '/'");

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()) // order matters, this should be last
                .build();

        this.retrofit = retrofit.create(HelloWorldRetrofitClient.class);

    }

    @Override
    public String createPerson(final Person person) {

        try {

            return this.retrofit
                    .createPerson(person)
                    .execute()
                    .body();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Person readPerson(final String id) {

        try {

            return this.retrofit
                    .readPerson(id)
                    .execute()
                    .body();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Person updatePerson(final Person person) {

        try {

            return this.retrofit
                    .updatePerson(person.getId(), person)
                    .execute()
                    .body();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deletePerson(final String id) {

        try {

            this.retrofit
                    .deletePerson(id)
                    .execute()
                    .body();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Person> listPersons() {

        try {

            return this.retrofit
                    .listPersons()
                    .execute()
                    .body();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
