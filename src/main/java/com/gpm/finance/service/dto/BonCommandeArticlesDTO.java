package com.gpm.finance.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.gpm.finance.domain.BonCommandeArticles} entity.
 */
public class BonCommandeArticlesDTO implements Serializable {

    private Long id;

    private Long bonCommandeId;

    private Long articleId;

    private ZonedDateTime dateRealisation;

    private Integer qteCommande;

    private Integer qteEffectuee;

    private BigDecimal prixArticle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBonCommandeId() {
        return bonCommandeId;
    }

    public void setBonCommandeId(Long bonCommandeId) {
        this.bonCommandeId = bonCommandeId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public ZonedDateTime getDateRealisation() {
        return dateRealisation;
    }

    public void setDateRealisation(ZonedDateTime dateRealisation) {
        this.dateRealisation = dateRealisation;
    }

    public Integer getQteCommande() {
        return qteCommande;
    }

    public void setQteCommande(Integer qteCommande) {
        this.qteCommande = qteCommande;
    }

    public Integer getQteEffectuee() {
        return qteEffectuee;
    }

    public void setQteEffectuee(Integer qteEffectuee) {
        this.qteEffectuee = qteEffectuee;
    }

    public BigDecimal getPrixArticle() {
        return prixArticle;
    }

    public void setPrixArticle(BigDecimal prixArticle) {
        this.prixArticle = prixArticle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommandeArticlesDTO)) {
            return false;
        }

        BonCommandeArticlesDTO bonCommandeArticlesDTO = (BonCommandeArticlesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bonCommandeArticlesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommandeArticlesDTO{" +
            "id=" + getId() +
            ", bonCommandeId=" + getBonCommandeId() +
            ", articleId=" + getArticleId() +
            ", dateRealisation='" + getDateRealisation() + "'" +
            ", qteCommande=" + getQteCommande() +
            ", qteEffectuee=" + getQteEffectuee() +
            ", prixArticle=" + getPrixArticle() +
            "}";
    }
}
