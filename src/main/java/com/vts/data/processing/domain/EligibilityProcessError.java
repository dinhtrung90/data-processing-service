package com.vts.data.processing.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A EligibilityProcessError.
 */
@Entity
@Table(name = "eligibility_process_error")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public EligibilityProcessError createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public EligibilityProcessError createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public EligibilityProcessError lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public EligibilityProcessError lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getSourceId() {
        return sourceId;
    }

    public EligibilityProcessError sourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getRefId() {
        return refId;
    }

    public EligibilityProcessError refId(String refId) {
        this.refId = refId;
        return this;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public EligibilityProcessError itemCount(Integer itemCount) {
        this.itemCount = itemCount;
        return this;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getValidationError() {
        return validationError;
    }

    public EligibilityProcessError validationError(String validationError) {
        this.validationError = validationError;
        return this;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EligibilityProcessError)) {
            return false;
        }
        return id != null && id.equals(((EligibilityProcessError) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EligibilityProcessError{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", sourceId='" + getSourceId() + "'" +
            ", refId='" + getRefId() + "'" +
            ", itemCount=" + getItemCount() +
            ", validationError='" + getValidationError() + "'" +
            "}";
    }
}
