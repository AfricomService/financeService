package com.gpm.finance.service.dto;

import com.gpm.finance.domain.enumeration.StatutFraisDeMission;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.gpm.finance.domain.FraisDeMission} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FraisDeMissionDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateDebut;

    @NotNull
    private LocalDate dateFin;

    @NotNull
    private Float montantTotal;

    private Float avanceRecue;

    @NotNull
    private Float netAPayer;

    /**
     * CHECK max 6
     */
    @Schema(description = "CHECK max 6")
    private Float noteRendement;

    /**
     * CHECK max 6
     */
    @Schema(description = "CHECK max 6")
    private Float noteQualite;

    /**
     * CHECK max 2
     */
    @Schema(description = "CHECK max 2")
    private Float noteConduite;

    /**
     * CHECK max 14
     */
    @Schema(description = "CHECK max 14")
    private Float noteTotale;

    private Float bonusExtra;

    private String justificatifBonus;

    private String justificatifModification;

    @NotNull
    private StatutFraisDeMission statut;

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

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Float getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Float montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Float getAvanceRecue() {
        return avanceRecue;
    }

    public void setAvanceRecue(Float avanceRecue) {
        this.avanceRecue = avanceRecue;
    }

    public Float getNetAPayer() {
        return netAPayer;
    }

    public void setNetAPayer(Float netAPayer) {
        this.netAPayer = netAPayer;
    }

    public Float getNoteRendement() {
        return noteRendement;
    }

    public void setNoteRendement(Float noteRendement) {
        this.noteRendement = noteRendement;
    }

    public Float getNoteQualite() {
        return noteQualite;
    }

    public void setNoteQualite(Float noteQualite) {
        this.noteQualite = noteQualite;
    }

    public Float getNoteConduite() {
        return noteConduite;
    }

    public void setNoteConduite(Float noteConduite) {
        this.noteConduite = noteConduite;
    }

    public Float getNoteTotale() {
        return noteTotale;
    }

    public void setNoteTotale(Float noteTotale) {
        this.noteTotale = noteTotale;
    }

    public Float getBonusExtra() {
        return bonusExtra;
    }

    public void setBonusExtra(Float bonusExtra) {
        this.bonusExtra = bonusExtra;
    }

    public String getJustificatifBonus() {
        return justificatifBonus;
    }

    public void setJustificatifBonus(String justificatifBonus) {
        this.justificatifBonus = justificatifBonus;
    }

    public String getJustificatifModification() {
        return justificatifModification;
    }

    public void setJustificatifModification(String justificatifModification) {
        this.justificatifModification = justificatifModification;
    }

    public StatutFraisDeMission getStatut() {
        return statut;
    }

    public void setStatut(StatutFraisDeMission statut) {
        this.statut = statut;
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
        if (!(o instanceof FraisDeMissionDTO)) {
            return false;
        }

        FraisDeMissionDTO fraisDeMissionDTO = (FraisDeMissionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fraisDeMissionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FraisDeMissionDTO{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", montantTotal=" + getMontantTotal() +
            ", avanceRecue=" + getAvanceRecue() +
            ", netAPayer=" + getNetAPayer() +
            ", noteRendement=" + getNoteRendement() +
            ", noteQualite=" + getNoteQualite() +
            ", noteConduite=" + getNoteConduite() +
            ", noteTotale=" + getNoteTotale() +
            ", bonusExtra=" + getBonusExtra() +
            ", justificatifBonus='" + getJustificatifBonus() + "'" +
            ", justificatifModification='" + getJustificatifModification() + "'" +
            ", statut='" + getStatut() + "'" +
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
