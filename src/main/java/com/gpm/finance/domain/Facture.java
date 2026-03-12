package com.gpm.finance.domain;

import com.gpm.finance.domain.enumeration.StatutFacture;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Facture.
 */
@Entity
@Table(name = "facture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "num_facture", nullable = false, unique = true)
    private String numFacture;

    @Column(name = "bon_de_commande")
    private String bonDeCommande;

    @NotNull
    @Column(name = "montant_facture", nullable = false)
    private Float montantFacture;

    @NotNull
    @Column(name = "date_facture", nullable = false)
    private LocalDate dateFacture;

    @Column(name = "date_echeance")
    private LocalDate dateEcheance;

    @Column(name = "date_decharge")
    private LocalDate dateDecharge;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutFacture statut;

    /**
     * Cross-service FK → Client (projectService)
     */
    @Column(name = "client_id")
    private Long clientId;

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

    public Facture id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumFacture() {
        return this.numFacture;
    }

    public Facture numFacture(String numFacture) {
        this.setNumFacture(numFacture);
        return this;
    }

    public void setNumFacture(String numFacture) {
        this.numFacture = numFacture;
    }

    public String getBonDeCommande() {
        return this.bonDeCommande;
    }

    public Facture bonDeCommande(String bonDeCommande) {
        this.setBonDeCommande(bonDeCommande);
        return this;
    }

    public void setBonDeCommande(String bonDeCommande) {
        this.bonDeCommande = bonDeCommande;
    }

    public Float getMontantFacture() {
        return this.montantFacture;
    }

    public Facture montantFacture(Float montantFacture) {
        this.setMontantFacture(montantFacture);
        return this;
    }

    public void setMontantFacture(Float montantFacture) {
        this.montantFacture = montantFacture;
    }

    public LocalDate getDateFacture() {
        return this.dateFacture;
    }

    public Facture dateFacture(LocalDate dateFacture) {
        this.setDateFacture(dateFacture);
        return this;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public LocalDate getDateEcheance() {
        return this.dateEcheance;
    }

    public Facture dateEcheance(LocalDate dateEcheance) {
        this.setDateEcheance(dateEcheance);
        return this;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public LocalDate getDateDecharge() {
        return this.dateDecharge;
    }

    public Facture dateDecharge(LocalDate dateDecharge) {
        this.setDateDecharge(dateDecharge);
        return this;
    }

    public void setDateDecharge(LocalDate dateDecharge) {
        this.dateDecharge = dateDecharge;
    }

    public StatutFacture getStatut() {
        return this.statut;
    }

    public Facture statut(StatutFacture statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutFacture statut) {
        this.statut = statut;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public Facture clientId(Long clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Facture createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public Facture updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Facture createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByUserLogin() {
        return this.createdByUserLogin;
    }

    public Facture createdByUserLogin(String createdByUserLogin) {
        this.setCreatedByUserLogin(createdByUserLogin);
        return this;
    }

    public void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Facture updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedByUserLogin() {
        return this.updatedByUserLogin;
    }

    public Facture updatedByUserLogin(String updatedByUserLogin) {
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
        if (!(o instanceof Facture)) {
            return false;
        }
        return id != null && id.equals(((Facture) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facture{" +
            "id=" + getId() +
            ", numFacture='" + getNumFacture() + "'" +
            ", bonDeCommande='" + getBonDeCommande() + "'" +
            ", montantFacture=" + getMontantFacture() +
            ", dateFacture='" + getDateFacture() + "'" +
            ", dateEcheance='" + getDateEcheance() + "'" +
            ", dateDecharge='" + getDateDecharge() + "'" +
            ", statut='" + getStatut() + "'" +
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
