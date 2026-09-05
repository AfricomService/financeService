package com.gpm.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BonCommande.
 */
@Entity
@Table(name = "bon_commande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BonCommande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "affaire_id")
    private Long affaireId;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "responsable_id")
    private String responsableId;

    @Column(name = "reference_client")
    private String referenceClient;

    @Column(name = "date_bon_commande")
    private ZonedDateTime dateBonCommande;

    @Column(name = "montant_total", precision = 21, scale = 2)
    private BigDecimal montantTotal;

    @Column(name = "montant_commande", precision = 21, scale = 2)
    private BigDecimal montantCommande;

    @Column(name = "montant_consomme", precision = 21, scale = 2)
    private BigDecimal montantConsomme;

    @Column(name = "montant_mission_effectue", precision = 21, scale = 2)
    private BigDecimal montantMissionEffectue;

    @Column(name = "identifiant_unique")
    private String identifiantUnique;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BonCommande id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public BonCommande clientId(Long clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getAffaireId() {
        return this.affaireId;
    }

    public BonCommande affaireId(Long affaireId) {
        this.setAffaireId(affaireId);
        return this;
    }

    public void setAffaireId(Long affaireId) {
        this.affaireId = affaireId;
    }

    public String getLieu() {
        return this.lieu;
    }

    public BonCommande lieu(String lieu) {
        this.setLieu(lieu);
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getResponsableId() {
        return this.responsableId;
    }

    public BonCommande responsableId(String responsableId) {
        this.setResponsableId(responsableId);
        return this;
    }

    public void setResponsableId(String responsableId) {
        this.responsableId = responsableId;
    }

    public String getReferenceClient() {
        return this.referenceClient;
    }

    public BonCommande referenceClient(String referenceClient) {
        this.setReferenceClient(referenceClient);
        return this;
    }

    public void setReferenceClient(String referenceClient) {
        this.referenceClient = referenceClient;
    }

    public ZonedDateTime getDateBonCommande() {
        return this.dateBonCommande;
    }

    public BonCommande dateBonCommande(ZonedDateTime dateBonCommande) {
        this.setDateBonCommande(dateBonCommande);
        return this;
    }

    public void setDateBonCommande(ZonedDateTime dateBonCommande) {
        this.dateBonCommande = dateBonCommande;
    }

    public BigDecimal getMontantTotal() {
        return this.montantTotal;
    }

    public BonCommande montantTotal(BigDecimal montantTotal) {
        this.setMontantTotal(montantTotal);
        return this;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public BigDecimal getMontantCommande() {
        return this.montantCommande;
    }

    public BonCommande montantCommande(BigDecimal montantCommande) {
        this.setMontantCommande(montantCommande);
        return this;
    }

    public void setMontantCommande(BigDecimal montantCommande) {
        this.montantCommande = montantCommande;
    }

    public BigDecimal getMontantConsomme() {
        return this.montantConsomme;
    }

    public BonCommande montantConsomme(BigDecimal montantConsomme) {
        this.setMontantConsomme(montantConsomme);
        return this;
    }

    public void setMontantConsomme(BigDecimal montantConsomme) {
        this.montantConsomme = montantConsomme;
    }

    public BigDecimal getMontantMissionEffectue() {
        return this.montantMissionEffectue;
    }

    public BonCommande montantMissionEffectue(BigDecimal montantMissionEffectue) {
        this.setMontantMissionEffectue(montantMissionEffectue);
        return this;
    }

    public void setMontantMissionEffectue(BigDecimal montantMissionEffectue) {
        this.montantMissionEffectue = montantMissionEffectue;
    }

    public String getIdentifiantUnique() {
        return this.identifiantUnique;
    }

    public BonCommande identifiantUnique(String identifiantUnique) {
        this.setIdentifiantUnique(identifiantUnique);
        return this;
    }

    public void setIdentifiantUnique(String identifiantUnique) {
        this.identifiantUnique = identifiantUnique;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommande)) {
            return false;
        }
        return id != null && id.equals(((BonCommande) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommande{" +
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
            ", identifiantUnique='" + getIdentifiantUnique() + "'" +
            "}";
    }
}
