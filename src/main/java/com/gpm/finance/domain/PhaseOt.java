package com.gpm.finance.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PhaseOt.
 */
@Entity
@Table(name = "phase_ot")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhaseOt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "duree")
    private Integer duree;

    @Column(name = "bloquante")
    private Boolean bloquante;

    @Column(name = "statut")
    private String statut;

    @Column(name = "date_debut")
    private ZonedDateTime dateDebut;

    @Column(name = "dl")
    private ZonedDateTime dl;

    @Column(name = "dlc")
    private ZonedDateTime dlc;

    @Column(name = "phase_parent_id")
    private Long phaseParentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PhaseOt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public PhaseOt nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public PhaseOt description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuree() {
        return this.duree;
    }

    public PhaseOt duree(Integer duree) {
        this.setDuree(duree);
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Boolean getBloquante() {
        return this.bloquante;
    }

    public PhaseOt bloquante(Boolean bloquante) {
        this.setBloquante(bloquante);
        return this;
    }

    public void setBloquante(Boolean bloquante) {
        this.bloquante = bloquante;
    }

    public String getStatut() {
        return this.statut;
    }

    public PhaseOt statut(String statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public ZonedDateTime getDateDebut() {
        return this.dateDebut;
    }

    public PhaseOt dateDebut(ZonedDateTime dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDl() {
        return this.dl;
    }

    public PhaseOt dl(ZonedDateTime dl) {
        this.setDl(dl);
        return this;
    }

    public void setDl(ZonedDateTime dl) {
        this.dl = dl;
    }

    public ZonedDateTime getDlc() {
        return this.dlc;
    }

    public PhaseOt dlc(ZonedDateTime dlc) {
        this.setDlc(dlc);
        return this;
    }

    public void setDlc(ZonedDateTime dlc) {
        this.dlc = dlc;
    }

    public Long getPhaseParentId() {
        return this.phaseParentId;
    }

    public PhaseOt phaseParentId(Long phaseParentId) {
        this.setPhaseParentId(phaseParentId);
        return this;
    }

    public void setPhaseParentId(Long phaseParentId) {
        this.phaseParentId = phaseParentId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhaseOt)) {
            return false;
        }
        return id != null && id.equals(((PhaseOt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhaseOt{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", duree=" + getDuree() +
            ", bloquante='" + getBloquante() + "'" +
            ", statut='" + getStatut() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dl='" + getDl() + "'" +
            ", dlc='" + getDlc() + "'" +
            ", phaseParentId=" + getPhaseParentId() +
            "}";
    }
}
