package com.gpm.finance.service.dto;

import com.gpm.finance.domain.enumeration.StatutOtExterne;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.gpm.finance.domain.OtExterne} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OtExterneDTO implements Serializable {

    private Long id;

    @NotNull
    private String reference;

    @NotNull
    private StatutOtExterne statut;

    /**
     * Cross-service FK → Affaire (projectService)
     */
    @Schema(description = "Cross-service FK → Affaire (projectService)")
    private Long affaireId;

    /**
     * Cross-service FK → Client (projectService)
     */
    @Schema(description = "Cross-service FK → Client (projectService)")
    private Long clientId;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private String createdBy;

    private String createdByUserLogin;

    private String updatedBy;

    private String updatedByUserLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public StatutOtExterne getStatut() {
        return statut;
    }

    public void setStatut(StatutOtExterne statut) {
        this.statut = statut;
    }

    public Long getAffaireId() {
        return affaireId;
    }

    public void setAffaireId(Long affaireId) {
        this.affaireId = affaireId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByUserLogin() {
        return createdByUserLogin;
    }

    public void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedByUserLogin() {
        return updatedByUserLogin;
    }

    public void setUpdatedByUserLogin(String updatedByUserLogin) {
        this.updatedByUserLogin = updatedByUserLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OtExterneDTO)) {
            return false;
        }

        OtExterneDTO otExterneDTO = (OtExterneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, otExterneDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtExterneDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", statut='" + getStatut() + "'" +
            ", affaireId=" + getAffaireId() +
            ", clientId=" + getClientId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdByUserLogin='" + getCreatedByUserLogin() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedByUserLogin='" + getUpdatedByUserLogin() + "'" +
            "}";
    }
}
