package com.gpm.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OtArticles.
 */
@Entity
@Table(name = "ot_articles")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OtArticles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ot_id")
    private Long otId;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "prix_propose", precision = 21, scale = 2)
    private BigDecimal prixPropose;

    @Column(name = "date_affectation")
    private ZonedDateTime dateAffectation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OtArticles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOtId() {
        return this.otId;
    }

    public OtArticles otId(Long otId) {
        this.setOtId(otId);
        return this;
    }

    public void setOtId(Long otId) {
        this.otId = otId;
    }

    public Long getArticleId() {
        return this.articleId;
    }

    public OtArticles articleId(Long articleId) {
        this.setArticleId(articleId);
        return this;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public BigDecimal getPrixPropose() {
        return this.prixPropose;
    }

    public OtArticles prixPropose(BigDecimal prixPropose) {
        this.setPrixPropose(prixPropose);
        return this;
    }

    public void setPrixPropose(BigDecimal prixPropose) {
        this.prixPropose = prixPropose;
    }

    public ZonedDateTime getDateAffectation() {
        return this.dateAffectation;
    }

    public OtArticles dateAffectation(ZonedDateTime dateAffectation) {
        this.setDateAffectation(dateAffectation);
        return this;
    }

    public void setDateAffectation(ZonedDateTime dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OtArticles)) {
            return false;
        }
        return id != null && id.equals(((OtArticles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtArticles{" +
            "id=" + getId() +
            ", otId=" + getOtId() +
            ", articleId=" + getArticleId() +
            ", prixPropose=" + getPrixPropose() +
            ", dateAffectation='" + getDateAffectation() + "'" +
            "}";
    }
}
