package com.gpm.finance.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BonCommandeArticles.
 */
@Entity
@Table(name = "bon_commande_articles")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonCommandeArticles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bon_commande_id")
    private Long bonCommandeId;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "date_realisation")
    private ZonedDateTime dateRealisation;

    @Column(name = "qte_commande")
    private Integer qteCommande;

    @Column(name = "qte_effectuee")
    private Integer qteEffectuee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BonCommandeArticles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBonCommandeId() {
        return this.bonCommandeId;
    }

    public BonCommandeArticles bonCommandeId(Long bonCommandeId) {
        this.setBonCommandeId(bonCommandeId);
        return this;
    }

    public void setBonCommandeId(Long bonCommandeId) {
        this.bonCommandeId = bonCommandeId;
    }

    public Long getArticleId() {
        return this.articleId;
    }

    public BonCommandeArticles articleId(Long articleId) {
        this.setArticleId(articleId);
        return this;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public ZonedDateTime getDateRealisation() {
        return this.dateRealisation;
    }

    public BonCommandeArticles dateRealisation(ZonedDateTime dateRealisation) {
        this.setDateRealisation(dateRealisation);
        return this;
    }

    public void setDateRealisation(ZonedDateTime dateRealisation) {
        this.dateRealisation = dateRealisation;
    }

    public Integer getQteCommande() {
        return this.qteCommande;
    }

    public BonCommandeArticles qteCommande(Integer qteCommande) {
        this.setQteCommande(qteCommande);
        return this;
    }

    public void setQteCommande(Integer qteCommande) {
        this.qteCommande = qteCommande;
    }

    public Integer getQteEffectuee() {
        return this.qteEffectuee;
    }

    public BonCommandeArticles qteEffectuee(Integer qteEffectuee) {
        this.setQteEffectuee(qteEffectuee);
        return this;
    }

    public void setQteEffectuee(Integer qteEffectuee) {
        this.qteEffectuee = qteEffectuee;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommandeArticles)) {
            return false;
        }
        return id != null && id.equals(((BonCommandeArticles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommandeArticles{" +
            "id=" + getId() +
            ", bonCommandeId=" + getBonCommandeId() +
            ", articleId=" + getArticleId() +
            ", dateRealisation='" + getDateRealisation() + "'" +
            ", qteCommande=" + getQteCommande() +
            ", qteEffectuee=" + getQteEffectuee() +
            "}";
    }
}
