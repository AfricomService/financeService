package com.gpm.finance.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.gpm.finance.domain.PhaseOt} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhaseOtDTO implements Serializable {

    private Long id;

    private String nom;

    private String description;

    private Integer duree;

    private Boolean bloquante;

    private String statut;

    private ZonedDateTime dateDebut;

    private ZonedDateTime dl;

    private ZonedDateTime dlc;

    private Long phaseParentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Boolean getBloquante() {
        return bloquante;
    }

    public void setBloquante(Boolean bloquante) {
        this.bloquante = bloquante;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDl() {
        return dl;
    }

    public void setDl(ZonedDateTime dl) {
        this.dl = dl;
    }

    public ZonedDateTime getDlc() {
        return dlc;
    }

    public void setDlc(ZonedDateTime dlc) {
        this.dlc = dlc;
    }

    public Long getPhaseParentId() {
        return phaseParentId;
    }

    public void setPhaseParentId(Long phaseParentId) {
        this.phaseParentId = phaseParentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhaseOtDTO)) {
            return false;
        }

        PhaseOtDTO phaseOtDTO = (PhaseOtDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, phaseOtDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhaseOtDTO{" +
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
