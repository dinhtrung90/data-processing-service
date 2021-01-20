package com.vts.data.processing.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A EligibilityProcessError.
 */
@Entity
@Table(name = "vts_eligibility_process_error")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityProcessError implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "source_id")
    private String sourceId;

    @Column(name = "ref_id")
    private String refId;

    @Column(name = "item_count")
    private Integer itemCount;

    @Column(name = "validation_error")
    private String validationError;

}
