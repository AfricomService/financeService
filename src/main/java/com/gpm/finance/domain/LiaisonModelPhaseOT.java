package com.gpm.finance.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LiaisonModelPhaseOT.
 */
@Entity
@Table(name = "liaison_model_phaseot")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LiaisonModelPhaseOT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "model_phase_ot_id")
    private Long modelPhaseOtId;

    @Column(name = "phase_id")
    private Long phaseId;

    @Column(name = "classement_phase")
    private Integer classementPhase;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LiaisonModelPhaseOT id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModelPhaseOtId() {
        return this.modelPhaseOtId;
    }

    public LiaisonModelPhaseOT modelPhaseOtId(Long modelPhaseOtId) {
        this.setModelPhaseOtId(modelPhaseOtId);
        return this;
    }

    public void setModelPhaseOtId(Long modelPhaseOtId) {
        this.modelPhaseOtId = modelPhaseOtId;
    }

    public Long getPhaseId() {
        return this.phaseId;
    }

    public LiaisonModelPhaseOT phaseId(Long phaseId) {
        this.setPhaseId(phaseId);
        return this;
    }

    public void setPhaseId(Long phaseId) {
        this.phaseId = phaseId;
    }

    public Integer getClassementPhase() {
        return this.classementPhase;
    }

    public LiaisonModelPhaseOT classementPhase(Integer classementPhase) {
        this.setClassementPhase(classementPhase);
        return this;
    }

    public void setClassementPhase(Integer classementPhase) {
        this.classementPhase = classementPhase;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LiaisonModelPhaseOT)) {
            return false;
        }
        return id != null && id.equals(((LiaisonModelPhaseOT) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LiaisonModelPhaseOT{" +
            "id=" + getId() +
            ", modelPhaseOtId=" + getModelPhaseOtId() +
            ", phaseId=" + getPhaseId() +
            ", classementPhase=" + getClassementPhase() +
            "}";
    }
}
