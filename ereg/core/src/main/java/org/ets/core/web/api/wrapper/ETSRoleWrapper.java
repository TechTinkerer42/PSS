package org.ets.core.web.api.wrapper;

import org.broadleafcommerce.core.web.api.wrapper.APIUnwrapper;
import org.broadleafcommerce.core.web.api.wrapper.APIWrapper;
import org.broadleafcommerce.core.web.api.wrapper.BaseWrapper;
import org.broadleafcommerce.profile.core.domain.Role;
import org.broadleafcommerce.profile.core.domain.RoleImpl;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "etsRole")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ETSRoleWrapper extends BaseWrapper implements APIWrapper<Role>, APIUnwrapper<Role> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String roleName;

    @Override
    public void wrap(Role model, HttpServletRequest request) {
        this.id = model.getId();
        this.roleName = model.getRoleName();
    }

    @Override
    public Role unwrap(HttpServletRequest request, ApplicationContext appContext) {
        Role role = new RoleImpl();
        role.setId(this.id);
        role.setRoleName(this.roleName);

        return role;
    }

}
