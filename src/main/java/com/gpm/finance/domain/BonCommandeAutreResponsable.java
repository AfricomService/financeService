package com.gpm.finance.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BonCommandeAutreResponsable.
 */
@Entity
@Table(name = "bon_commande_autre_responsable")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BonCommandeAutreResponsable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bon_commande_id")
    private Long bonCommandeId;

    @Column(name = "contact_societe_id")
    private Long contactSocieteId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BonCommandeAutreResponsable id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBonCommandeId() {
        return this.bonCommandeId;
    }

    public BonCommandeAutreResponsable bonCommandeId(Long bonCommandeId) {
        this.setBonCommandeId(bonCommandeId);
        return this;
    }

    public void setBonCommandeId(Long bonCommandeId) {
        this.bonCommandeId = bonCommandeId;
    }

    public Long getContactSocieteId() {
        return this.contactSocieteId;
    }

    public BonCommandeAutreResponsable contactSocieteId(Long contactSocieteId) {
        this.setContactSocieteId(contactSocieteId);
        return this;
    }

    public void setContactSocieteId(Long contactSocieteId) {
        this.contactSocieteId = contactSocieteId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommandeAutreResponsable)) {
            return false;
        }
        return id != null && id.equals(((BonCommandeAutreResponsable) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommandeAutreResponsable{" +
            "id=" + getId() +
            ", bonCommandeId=" + getBonCommandeId() +
            ", contactSocieteId=" + getContactSocieteId() +
            "}";
    }
}
