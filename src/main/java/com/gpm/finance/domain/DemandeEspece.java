package com.gpm.finance.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandeEspece.
 */
@Entity
@Table(name = "demande_espece")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeEspece implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Float montant;

    @NotNull
    @Column(name = "motif", nullable = false)
    private String motif;

    /**
     * Cross-service FK → WorkOrder (operationsService)
     */
    @Column(name = "work_order_id")
    private Long workOrderId;

    /**
     * Cross-service FK → Utilisateur (operationsService)
     */
    @Column(name = "beneficiaire_id")
    private String beneficiaireId;

    @Column(name = "beneficiaire_user_login")
    private String beneficiaireUserLogin;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_by_user_login")
    private String createdByUserLogin;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_by_user_login")
    private String updatedByUserLogin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeEspece id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMontant() {
        return this.montant;
    }

    public DemandeEspece montant(Float montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public String getMotif() {
        return this.motif;
    }

    public DemandeEspece motif(String motif) {
        this.setMotif(motif);
        return this;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Long getWorkOrderId() {
        return this.workOrderId;
    }

    public DemandeEspece workOrderId(Long workOrderId) {
        this.setWorkOrderId(workOrderId);
        return this;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getBeneficiaireId() {
        return this.beneficiaireId;
    }

    public DemandeEspece beneficiaireId(String beneficiaireId) {
        this.setBeneficiaireId(beneficiaireId);
        return this;
    }

    public void setBeneficiaireId(String beneficiaireId) {
        this.beneficiaireId = beneficiaireId;
    }

    public String getBeneficiaireUserLogin() {
        return this.beneficiaireUserLogin;
    }

    public DemandeEspece beneficiaireUserLogin(String beneficiaireUserLogin) {
        this.setBeneficiaireUserLogin(beneficiaireUserLogin);
        return this;
    }

    public void setBeneficiaireUserLogin(String beneficiaireUserLogin) {
        this.beneficiaireUserLogin = beneficiaireUserLogin;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public DemandeEspece createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public DemandeEspece updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public DemandeEspece createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByUserLogin() {
        return this.createdByUserLogin;
    }

    public DemandeEspece createdByUserLogin(String createdByUserLogin) {
        this.setCreatedByUserLogin(createdByUserLogin);
        return this;
    }

    public void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public DemandeEspece updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedByUserLogin() {
        return this.updatedByUserLogin;
    }

    public DemandeEspece updatedByUserLogin(String updatedByUserLogin) {
        this.setUpdatedByUserLogin(updatedByUserLogin);
        return this;
    }

    public void setUpdatedByUserLogin(String updatedByUserLogin) {
        this.updatedByUserLogin = updatedByUserLogin;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeEspece)) {
            return false;
        }
        return id != null && id.equals(((DemandeEspece) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeEspece{" +
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
