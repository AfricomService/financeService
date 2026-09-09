package com.gpm.finance.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OtExterneAutreResponsable.
 */
@Entity
@Table(name = "ot_externe_autre_responsable")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OtExterneAutreResponsable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ot_externe_id")
    private Long otExterneId;

    @Column(name = "contact_societe_id")
    private Long contactSocieteId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OtExterneAutreResponsable id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOtExterneId() {
        return this.otExterneId;
    }

    public OtExterneAutreResponsable otExterneId(Long otExterneId) {
        this.setOtExterneId(otExterneId);
        return this;
    }

    public void setOtExterneId(Long otExterneId) {
        this.otExterneId = otExterneId;
    }

    public Long getContactSocieteId() {
        return this.contactSocieteId;
    }

    public OtExterneAutreResponsable contactSocieteId(Long contactSocieteId) {
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
        if (!(o instanceof OtExterneAutreResponsable)) {
            return false;
        }
        return id != null && id.equals(((OtExterneAutreResponsable) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtExterneAutreResponsable{" +
            "id=" + getId() +
            ", otExterneId=" + getOtExterneId() +
            ", contactSocieteId=" + getContactSocieteId() +
            "}";
    }
}
