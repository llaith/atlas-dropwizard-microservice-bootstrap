package tkt.atlas.helloworld.webclient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tkt.atlas.helloworld.api.domain.Person;

import java.util.List;

/**
 *
 */
public interface HelloWorldRetrofitClient {

    @POST("helloworld")
    Call<String> createPerson(@Body Person person);

    @GET("helloworld/{id}")
    Call<Person> readPerson(@Path("id") String id);

    @PUT("helloworld/{id}")
    Call<Person> updatePerson(@Path("id") String id, @Body Person person);

    @DELETE("helloworld/{id}")
    Call<Void> deletePerson(@Path("id") String id);

    @GET("helloworld")
    Call<List<Person>> listPersons();

}
