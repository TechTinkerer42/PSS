package org.ets.core.web.api.endpoint;

import org.broadleafcommerce.core.web.api.wrapper.CountryWrapper;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.service.CountryService;
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

@Component("etsRestCountryEndpoint")
@Scope("singleton")
@Path("/country")
@Produces(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CountryEndpoint implements ApplicationContextAware {

    @Resource(name="blCountryService")
    protected CountryService countryService;

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @GET
    public List<CountryWrapper> findCountries(@Context HttpServletRequest request) {
        List<Country> countries = countryService.findCountries();
        if (countries != null && !countries.isEmpty()) {
            List<CountryWrapper> wrappers = new ArrayList<CountryWrapper>();
            for (Country country : countries) {
                CountryWrapper wrapper = (CountryWrapper) context.getBean(CountryWrapper.class.getName());
                wrapper.wrap(country, request);
                wrappers.add(wrapper);
            }
            return wrappers;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @GET
    @Path("/{abbreviation}")
    public CountryWrapper findCountrybyAbbreviation(@Context HttpServletRequest request, @PathParam("abbreviation") String abbreviation) {
        Country country = countryService.findCountryByAbbreviation(abbreviation);
        if (country != null) {
            CountryWrapper wrapper = (CountryWrapper) context.getBean(CountryWrapper.class.getName());
            wrapper.wrap(country, request);
            return wrapper;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

}
