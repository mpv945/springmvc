package org.haijun.study.cxf;

import org.haijun.study.vo.Person;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces({ MediaType.APPLICATION_JSON })
public interface PersonService {

    @POST
    @Path("/person/{name}")
    public Person getPerson(@PathParam("name") String name);

    @POST
    @Path("/persons")
    public List<Person> getPersons();
}
