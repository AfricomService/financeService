package com.gpm.finance.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.gpm.finance.domain.BonCommande} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonCommandeDTO implements Serializable {

    private Long id;

    private Long clientId;

    private Long affaireId;

    private String lieu;

    private String responsableId;

    private String referenceClient;

    private ZonedDateTime dateBonCommande;

    private BigDecimal montantTotal;

    private BigDecimal montantCommande;

    private BigDecimal montantConsomme;

    private BigDecimal montantMissionEffectue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getAffaireId() {
        return affaireId;
    }

    public void setAffaireId(Long affaireId) {
        this.affaireId = affaireId;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(String responsableId) {
        this.responsableId = responsableId;
    }

    public String getReferenceClient() {
        return referenceClient;
    }

    public void setReferenceClient(String referenceClient) {
        this.referenceClient = referenceClient;
    }

    public ZonedDateTime getDateBonCommande() {
        return dateBonCommande;
    }

    public void setDateBonCommande(ZonedDateTime dateBonCommande) {
        this.dateBonCommande = dateBonCommande;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public BigDecimal getMontantCommande() {
        return montantCommande;
    }

    public void setMontantCommande(BigDecimal montantCommande) {
        this.montantCommande = montantCommande;
    }

    public BigDecimal getMontantConsomme() {
        return montantConsomme;
    }

    public void setMontantConsomme(BigDecimal montantConsomme) {
        this.montantConsomme = montantConsomme;
    }

    public BigDecimal getMontantMissionEffectue() {
        return montantMissionEffectue;
    }

    public void setMontantMissionEffectue(BigDecimal montantMissionEffectue) {
        this.montantMissionEffectue = montantMissionEffectue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommandeDTO)) {
            return false;
        }

        BonCommandeDTO bonCommandeDTO = (BonCommandeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bonCommandeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommandeDTO{" +
            "id=" + getId() +
            ", clientId=" + getClientId() +
            ", affaireId=" + getAffaireId() +
            ", lieu='" + getLieu() + "'" +
            ", responsableId='" + getResponsableId() + "'" +
            ", referenceClient='" + getReferenceClient() + "'" +
            ", dateBonCommande='" + getDateBonCommande() + "'" +
            ", montantTotal=" + getMontantTotal() +
            ", montantCommande=" + getMontantCommande() +
            ", montantConsomme=" + getMontantConsomme() +
            ", montantMissionEffectue=" + getMontantMissionEffectue() +
            "}";
    }
}
