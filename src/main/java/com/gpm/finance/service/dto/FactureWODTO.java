package com.gpm.finance.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.gpm.finance.domain.FactureWO} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactureWODTO implements Serializable {

    private Long id;

    /**
     * ex: 75.0 pour 75%
     */
    @Schema(description = "ex: 75.0 pour 75%")
    private Float pourcentageFacture;

    private Float montantFacture;

    private String remarque;

    /**
     * Cross-service FK → WorkOrder (operationsService)
     */
    @Schema(description = "Cross-service FK → WorkOrder (operationsService)")
    private Long workOrderId;

    private FactureDTO facture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPourcentageFacture() {
        return pourcentageFacture;
    }

    public void setPourcentageFacture(Float pourcentageFacture) {
        this.pourcentageFacture = pourcentageFacture;
    }

    public Float getMontantFacture() {
        return montantFacture;
    }

    public void setMontantFacture(Float montantFacture) {
        this.montantFacture = montantFacture;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public FactureDTO getFacture() {
        return facture;
    }

    public void setFacture(FactureDTO facture) {
        this.facture = facture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureWODTO)) {
            return false;
        }

        FactureWODTO factureWODTO = (FactureWODTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, factureWODTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureWODTO{" +
            "id=" + getId() +
            ", pourcentageFacture=" + getPourcentageFacture() +
            ", montantFacture=" + getMontantFacture() +
            ", remarque='" + getRemarque() + "'" +
            ", workOrderId=" + getWorkOrderId() +
            ", facture=" + getFacture() +
            "}";
    }
}
