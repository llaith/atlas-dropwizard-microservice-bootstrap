package tkt.atlas.helloworld.webservice.jaxrs;

import io.dropwizard.jersey.caching.CacheControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tkt.atlas.helloworld.api.HelloWorldService;
import tkt.atlas.helloworld.api.domain.Person;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("helloworld")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldResource.class.getName());

    private HelloWorldService service;

    public HelloWorldResource(final HelloWorldService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @CacheControl(isPrivate = true, noCache = true, noStore = true, mustRevalidate = true, maxAge = 0)
    public Response createPerson(
            @Context final UriInfo uriInfo,
            @Context final HttpServletRequest request,
            @Valid final Person person) {

        logger.debug("Creating person: " + person);

        final String id = this.service.createPerson(person);

        final URI selfUri = uriInfo
                .getBaseUriBuilder()
                .path(HelloWorldResource.class)
                .path(HelloWorldResource.class, "readPerson")
                .build(id);

        return Response.created(selfUri)
                       .entity(id)
                       .link(selfUri, "self")
                       .header(HttpHeaders.CONTENT_LOCATION, selfUri)
                       .build();

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @CacheControl(isPrivate = true, mustRevalidate = true, maxAge = 1)
    public Response readPerson(
            @Context final UriInfo uriInfo,
            @Context final Request request,
            @PathParam("id") final String id) {

        logger.debug("Reading person with id: " + id);

        final Person person = this.service.readPerson(id);

        if (person == null)
            throw new NotFoundException(String.format("Person with id: %s was not found.", id));

        final URI selfUri = uriInfo
                .getBaseUriBuilder()
                .path(HelloWorldResource.class)
                .path(HelloWorldResource.class, "readPerson")
                .build(person.getId());

        return Response.ok(person)
                       .link(selfUri, "self")
                       .build();

    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @CacheControl(isPrivate = true, noCache = true, noStore = true, mustRevalidate = true, maxAge = 0)
    public Response updatePerson(
            @Context final UriInfo uriInfo,
            @Context final HttpServletRequest request,
            @Valid final Person person) {

        logger.debug("Updating person: " + person);

        final Person result = this.service.updatePerson(person);

        final URI selfUri = uriInfo
                .getBaseUriBuilder()
                .path(HelloWorldResource.class)
                .path(HelloWorldResource.class, "readPerson")
                .build(result.getId());

        return Response.ok(result)
                       .link(selfUri, "self")
                       .header(HttpHeaders.CONTENT_LOCATION, selfUri)
                       .build();

    }

    @DELETE
    @Path("{id}")
    @CacheControl(isPrivate = true, noCache = true, noStore = true, mustRevalidate = true, maxAge = 0)
    public Response deletePerson(
            @Context final HttpServletRequest request,
            @PathParam("id") final String id) {

        logger.debug("Deleting person with id: " + id);

        this.service.deletePerson(id);

        return Response.noContent().build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @CacheControl(isPrivate = true, mustRevalidate = true, maxAge = 1)
    public Response listPersons(@Context final UriInfo uriInfo) {

        logger.debug("Listing all persons.");

        // look for persons for today
        final List<Person> persons = this.service.listPersons();

        // build self-uri
        final URI selfUri = uriInfo
                .getBaseUriBuilder()
                .path(HelloWorldResource.class)
                .build();

        // return ok with list of persons
        return Response.ok(new GenericEntity<List<Person>>(persons) {})
                       .link(selfUri, "self")
                       .build();

    }

}
