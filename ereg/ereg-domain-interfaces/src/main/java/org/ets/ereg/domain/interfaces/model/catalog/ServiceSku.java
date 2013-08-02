package org.ets.ereg.domain.interfaces.model.catalog;

import org.ets.ereg.domain.interfaces.model.common.ProgramType;

public interface ServiceSku extends ETSSku {

    public ProgramType getProgramCode();

    public void setProgramCode(ProgramType programType);

}
