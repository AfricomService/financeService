package com.gpm.finance.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gpm.finance.domain.ModelPhaseOT} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelPhaseOTDTO implements Serializable {

    private Long id;

    private String nom;

    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModelPhaseOTDTO)) {
            return false;
        }

        ModelPhaseOTDTO modelPhaseOTDTO = (ModelPhaseOTDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, modelPhaseOTDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModelPhaseOTDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
