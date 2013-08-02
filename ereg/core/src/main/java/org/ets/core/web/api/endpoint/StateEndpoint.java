package org.ets.core.web.api.endpoint;

import org.broadleafcommerce.core.web.api.wrapper.StateWrapper;
import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.core.service.StateService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component("etsRestStateEndpoint")
@Scope("singleton")
@Path("/state")
@Produces(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StateEndpoint implements ApplicationContextAware {

    @Resource(name="blStateService")
    protected StateService stateService;

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @GET
    public List<StateWrapper> findStates(@Context HttpServletRequest request, @QueryParam("abbreviation") String abbreviation) {
        List<State> states = null;
        if (abbreviation != null) {
            states = stateService.findStates(abbreviation);
        } else {
            states = stateService.findStates();
        }

        if (states != null && !states.isEmpty()) {
            List<StateWrapper> wrappers = new ArrayList<StateWrapper>();
            for (State state : states) {
                StateWrapper wrapper = (StateWrapper) context.getBean(StateWrapper.class.getName());
                wrapper.wrap(state, request);
                wrappers.add(wrapper);
            }
            return wrappers;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @GET
    @Path("/{abbreviation}")
    public StateWrapper findStatebyAbbreviation(@Context HttpServletRequest request, @PathParam("abbreviation") String abbreviation) {
        State state = stateService.findStateByAbbreviation(abbreviation);
        if (state != null) {
            StateWrapper wrapper = (StateWrapper) context.getBean(StateWrapper.class.getName());
            wrapper.wrap(state, request);
            return wrapper;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }


}
