package com.vts.data.processing.service.mapper;

import com.vts.data.processing.domain.*;
import com.vts.data.processing.service.dto.EligibilityProcessErrorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EligibilityProcessError} and its DTO {@link EligibilityProcessErrorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EligibilityProcessErrorMapper extends EntityMapper<EligibilityProcessErrorDTO, EligibilityProcessError> {



    default EligibilityProcessError fromId(Long id) {
        if (id == null) {
            return null;
        }
        EligibilityProcessError eligibilityProcessError = new EligibilityProcessError();
        eligibilityProcessError.setId(id);
        return eligibilityProcessError;
    }
}
