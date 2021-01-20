package com.vts.data.processing.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.vts.data.processing.domain.EligibilityProcessError} entity. This class is used
 * in {@link com.vts.data.processing.web.rest.EligibilityProcessErrorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /eligibility-process-errors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EligibilityProcessErrorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private StringFilter sourceId;

    private StringFilter refId;

    private IntegerFilter itemCount;

    private StringFilter validationError;

    public EligibilityProcessErrorCriteria() {
    }

    public EligibilityProcessErrorCriteria(EligibilityProcessErrorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.sourceId = other.sourceId == null ? null : other.sourceId.copy();
        this.refId = other.refId == null ? null : other.refId.copy();
        this.itemCount = other.itemCount == null ? null : other.itemCount.copy();
        this.validationError = other.validationError == null ? null : other.validationError.copy();
    }

    @Override
    public EligibilityProcessErrorCriteria copy() {
        return new EligibilityProcessErrorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StringFilter getSourceId() {
        return sourceId;
    }

    public void setSourceId(StringFilter sourceId) {
        this.sourceId = sourceId;
    }

    public StringFilter getRefId() {
        return refId;
    }

    public void setRefId(StringFilter refId) {
        this.refId = refId;
    }

    public IntegerFilter getItemCount() {
        return itemCount;
    }

    public void setItemCount(IntegerFilter itemCount) {
        this.itemCount = itemCount;
    }

    public StringFilter getValidationError() {
        return validationError;
    }

    public void setValidationError(StringFilter validationError) {
        this.validationError = validationError;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EligibilityProcessErrorCriteria that = (EligibilityProcessErrorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(sourceId, that.sourceId) &&
            Objects.equals(refId, that.refId) &&
            Objects.equals(itemCount, that.itemCount) &&
            Objects.equals(validationError, that.validationError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createdBy,
        createdDate,
        lastModifiedBy,
        lastModifiedDate,
        sourceId,
        refId,
        itemCount,
        validationError
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EligibilityProcessErrorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (sourceId != null ? "sourceId=" + sourceId + ", " : "") +
                (refId != null ? "refId=" + refId + ", " : "") +
                (itemCount != null ? "itemCount=" + itemCount + ", " : "") +
                (validationError != null ? "validationError=" + validationError + ", " : "") +
            "}";
    }

}
