package com.gpm.finance.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FactureWO.
 */
@Entity
@Table(name = "facture_wo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactureWO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * ex: 75.0 pour 75%
     */
    @Column(name = "pourcentage_facture")
    private Float pourcentageFacture;

    @Column(name = "montant_facture")
    private Float montantFacture;

    @Column(name = "remarque")
    private String remarque;

    /**
     * Cross-service FK → WorkOrder (operationsService)
     */
    @Column(name = "work_order_id")
    private Long workOrderId;

    @ManyToOne(optional = false)
    @NotNull
    private Facture facture;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FactureWO id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPourcentageFacture() {
        return this.pourcentageFacture;
    }

    public FactureWO pourcentageFacture(Float pourcentageFacture) {
        this.setPourcentageFacture(pourcentageFacture);
        return this;
    }

    public void setPourcentageFacture(Float pourcentageFacture) {
        this.pourcentageFacture = pourcentageFacture;
    }

    public Float getMontantFacture() {
        return this.montantFacture;
    }

    public FactureWO montantFacture(Float montantFacture) {
        this.setMontantFacture(montantFacture);
        return this;
    }

    public void setMontantFacture(Float montantFacture) {
        this.montantFacture = montantFacture;
    }

    public String getRemarque() {
        return this.remarque;
    }

    public FactureWO remarque(String remarque) {
        this.setRemarque(remarque);
        return this;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public Long getWorkOrderId() {
        return this.workOrderId;
    }

    public FactureWO workOrderId(Long workOrderId) {
        this.setWorkOrderId(workOrderId);
        return this;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Facture getFacture() {
        return this.facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public FactureWO facture(Facture facture) {
        this.setFacture(facture);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureWO)) {
            return false;
        }
        return id != null && id.equals(((FactureWO) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureWO{" +
            "id=" + getId() +
            ", pourcentageFacture=" + getPourcentageFacture() +
            ", montantFacture=" + getMontantFacture() +
            ", remarque='" + getRemarque() + "'" +
            ", workOrderId=" + getWorkOrderId() +
            "}";
    }
}
