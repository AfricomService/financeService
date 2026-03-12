package com.gpm.finance.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.gpm.finance.domain.DemandeEspece} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeEspeceDTO implements Serializable {

    private Long id;

    @NotNull
    private Float montant;

    @NotNull
    private String motif;

    /**
     * Cross-service FK → WorkOrder (operationsService)
     */
    @Schema(description = "Cross-service FK → WorkOrder (operationsService)")
    private Long workOrderId;

    /**
     * Cross-service FK → Utilisateur (operationsService)
     */
    @Schema(description = "Cross-service FK → Utilisateur (operationsService)")
    private String beneficiaireId;

    private String beneficiaireUserLogin;

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

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getBeneficiaireId() {
        return beneficiaireId;
    }

    public void setBeneficiaireId(String beneficiaireId) {
        this.beneficiaireId = beneficiaireId;
    }

    public String getBeneficiaireUserLogin() {
        return beneficiaireUserLogin;
    }

    public void setBeneficiaireUserLogin(String beneficiaireUserLogin) {
        this.beneficiaireUserLogin = beneficiaireUserLogin;
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
        if (!(o instanceof DemandeEspeceDTO)) {
            return false;
        }

        DemandeEspeceDTO demandeEspeceDTO = (DemandeEspeceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandeEspeceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeEspeceDTO{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", motif='" + getMotif() + "'" +
            ", workOrderId=" + getWorkOrderId() +
            ", beneficiaireId='" + getBeneficiaireId() + "'" +
            ", beneficiaireUserLogin='" + getBeneficiaireUserLogin() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdByUserLogin='" + getCreatedByUserLogin() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedByUserLogin='" + getUpdatedByUserLogin() + "'" +
            "}";
    }
}
