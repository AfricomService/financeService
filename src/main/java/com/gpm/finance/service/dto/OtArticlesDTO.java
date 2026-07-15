package com.gpm.finance.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.gpm.finance.domain.OtArticles} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OtArticlesDTO implements Serializable {

    private Long id;

    private Long otId;

    private Long articleId;

    private BigDecimal prixPropose;

    private ZonedDateTime dateAffectation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOtId() {
        return otId;
    }

    public void setOtId(Long otId) {
        this.otId = otId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public BigDecimal getPrixPropose() {
        return prixPropose;
    }

    public void setPrixPropose(BigDecimal prixPropose) {
        this.prixPropose = prixPropose;
    }

    public ZonedDateTime getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(ZonedDateTime dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OtArticlesDTO)) {
            return false;
        }

        OtArticlesDTO otArticlesDTO = (OtArticlesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, otArticlesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtArticlesDTO{" +
            "id=" + getId() +
            ", otId=" + getOtId() +
            ", articleId=" + getArticleId() +
            ", prixPropose=" + getPrixPropose() +
            ", dateAffectation='" + getDateAffectation() + "'" +
            "}";
    }
}
