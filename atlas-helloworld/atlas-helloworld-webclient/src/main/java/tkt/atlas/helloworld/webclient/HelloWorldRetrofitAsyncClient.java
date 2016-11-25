package tkt.atlas.helloworld.webclient;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;
import tkt.atlas.helloworld.api.domain.Person;

import java.util.List;

/**
 *
 */
public interface HelloWorldRetrofitAsyncClient {

    @POST("helloworld")
    Observable<String> createPerson(@Body Person person);

    @GET("helloworld/{id}")
    Observable<Person> readPerson(@Path("id") String id);

    @PUT("helloworld/{id}")
    Observable<Person> updatePerson(@Path("id") String id, @Body Person person);

    @DELETE("helloworld/{id}")
    Observable<Void> deletePerson(@Path("id") String id);

    @GET("helloworld")
    Observable<List<Person>> listPersons();

}
