package org.ets.ereg.csr.web.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

public class AppointmentUtil {

    public static boolean getPrivilegeForApptUpdate(HttpServletRequest request,
            List<TestCenter> testCenters, Booking booking) {
        SecurityContextHolderAwareRequestWrapper wrapper = new SecurityContextHolderAwareRequestWrapper(request, "");
        boolean privilege = true;
        if (!wrapper.isUserInRole("ROLE_CUSTOMER_SERVICE_REP") &&
        wrapper.isUserInRole("ROLE_TEST_CENTER_ADMIN") &&
        !testCenters.contains(booking.getTestCenter())) {
            privilege = false;
        }

        return privilege;

    }

}
