package com.gpm.finance.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gpm.finance.domain.LiaisonModelPhaseOT} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LiaisonModelPhaseOTDTO implements Serializable {

    private Long id;

    private Long modelPhaseOtId;

    private Long phaseId;

    private Integer classementPhase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModelPhaseOtId() {
        return modelPhaseOtId;
    }

    public void setModelPhaseOtId(Long modelPhaseOtId) {
        this.modelPhaseOtId = modelPhaseOtId;
    }

    public Long getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Long phaseId) {
        this.phaseId = phaseId;
    }

    public Integer getClassementPhase() {
        return classementPhase;
    }

    public void setClassementPhase(Integer classementPhase) {
        this.classementPhase = classementPhase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LiaisonModelPhaseOTDTO)) {
            return false;
        }

        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO = (LiaisonModelPhaseOTDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, liaisonModelPhaseOTDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LiaisonModelPhaseOTDTO{" +
            "id=" + getId() +
            ", modelPhaseOtId=" + getModelPhaseOtId() +
            ", phaseId=" + getPhaseId() +
            ", classementPhase=" + getClassementPhase() +
            "}";
    }
}
