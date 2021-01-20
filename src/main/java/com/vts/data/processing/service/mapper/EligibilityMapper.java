package com.vts.data.processing.service.mapper;


import com.vts.data.processing.domain.*;
import com.vts.data.processing.service.dto.EligibilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Eligibility} and its DTO {@link EligibilityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EligibilityMapper extends EntityMapper<EligibilityDTO, Eligibility> {



    default Eligibility fromId(Long id) {
        if (id == null) {
            return null;
        }
        Eligibility eligibility = new Eligibility();
        eligibility.setId(id);
        return eligibility;
    }
}
