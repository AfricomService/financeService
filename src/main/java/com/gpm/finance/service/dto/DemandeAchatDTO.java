package com.gpm.finance.service.dto;

import com.gpm.finance.domain.enumeration.StatutDemandeAchat;
import com.gpm.finance.domain.enumeration.TypeDemandeAchat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.gpm.finance.domain.DemandeAchat} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeAchatDTO implements Serializable {

    private Long id;

    /**
     * DA-YYYY-XXXX auto-generated
     */
    @NotNull
    @Schema(description = "DA-YYYY-XXXX auto-generated", required = true)
    private String code;

    @NotNull
    private StatutDemandeAchat statut;

    @NotNull
    private TypeDemandeAchat type;

    @NotNull
    private ZonedDateTime dateCreation;

    @NotNull
    private LocalDate dateMiseADisposition;

    /**
     * Cross-service FK → WorkOrder (operationsService)
     */
    @Schema(description = "Cross-service FK → WorkOrder (operationsService)")
    private Long workOrderId;

    /**
     * Cross-service FK → Affaire (projectService)
     */
    @Schema(description = "Cross-service FK → Affaire (projectService)")
    private Long affaireId;

    /**
     * Cross-service FK → Utilisateur (operationsService) — Demandeur
     */
    @Schema(description = "Cross-service FK → Utilisateur (operationsService) — Demandeur")
    private String demandeurId;

    private String demandeurUserLogin;

    /**
     * Cross-service FK → Utilisateur (operationsService) — Validateur
     */
    @Schema(description = "Cross-service FK → Utilisateur (operationsService) — Validateur")
    private String validateurId;

    private String validateurUserLogin;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StatutDemandeAchat getStatut() {
        return statut;
    }

    public void setStatut(StatutDemandeAchat statut) {
        this.statut = statut;
    }

    public TypeDemandeAchat getType() {
        return type;
    }

    public void setType(TypeDemandeAchat type) {
        this.type = type;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateMiseADisposition() {
        return dateMiseADisposition;
    }

    public void setDateMiseADisposition(LocalDate dateMiseADisposition) {
        this.dateMiseADisposition = dateMiseADisposition;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Long getAffaireId() {
        return affaireId;
    }

    public void setAffaireId(Long affaireId) {
        this.affaireId = affaireId;
    }

    public String getDemandeurId() {
        return demandeurId;
    }

    public void setDemandeurId(String demandeurId) {
        this.demandeurId = demandeurId;
    }

    public String getDemandeurUserLogin() {
        return demandeurUserLogin;
    }

    public void setDemandeurUserLogin(String demandeurUserLogin) {
        this.demandeurUserLogin = demandeurUserLogin;
    }

    public String getValidateurId() {
        return validateurId;
    }

    public void setValidateurId(String validateurId) {
        this.validateurId = validateurId;
    }

    public String getValidateurUserLogin() {
        return validateurUserLogin;
    }

    public void setValidateurUserLogin(String validateurUserLogin) {
        this.validateurUserLogin = validateurUserLogin;
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
        if (!(o instanceof DemandeAchatDTO)) {
            return false;
        }

        DemandeAchatDTO demandeAchatDTO = (DemandeAchatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandeAchatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeAchatDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", statut='" + getStatut() + "'" +
            ", type='" + getType() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateMiseADisposition='" + getDateMiseADisposition() + "'" +
            ", workOrderId=" + getWorkOrderId() +
            ", affaireId=" + getAffaireId() +
            ", demandeurId='" + getDemandeurId() + "'" +
            ", demandeurUserLogin='" + getDemandeurUserLogin() + "'" +
            ", validateurId='" + getValidateurId() + "'" +
            ", validateurUserLogin='" + getValidateurUserLogin() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdByUserLogin='" + getCreatedByUserLogin() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedByUserLogin='" + getUpdatedByUserLogin() + "'" +
            "}";
    }
}
