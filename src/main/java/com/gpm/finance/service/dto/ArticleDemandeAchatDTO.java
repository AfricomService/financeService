package com.gpm.finance.service.dto;

import com.gpm.finance.domain.enumeration.TypeArticleDemandeAchat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.gpm.finance.domain.ArticleDemandeAchat} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleDemandeAchatDTO implements Serializable {

    private Long id;

    @NotNull
    private Float qteDemandee;

    @NotNull
    private TypeArticleDemandeAchat type;

    /**
     * Cross-service FK → Article (projectService)
     */
    @Schema(description = "Cross-service FK → Article (projectService)")
    private String articleCode;

    private DemandeAchatDTO demandeAchat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getQteDemandee() {
        return qteDemandee;
    }

    public void setQteDemandee(Float qteDemandee) {
        this.qteDemandee = qteDemandee;
    }

    public TypeArticleDemandeAchat getType() {
        return type;
    }

    public void setType(TypeArticleDemandeAchat type) {
        this.type = type;
    }

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public DemandeAchatDTO getDemandeAchat() {
        return demandeAchat;
    }

    public void setDemandeAchat(DemandeAchatDTO demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleDemandeAchatDTO)) {
            return false;
        }

        ArticleDemandeAchatDTO articleDemandeAchatDTO = (ArticleDemandeAchatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, articleDemandeAchatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleDemandeAchatDTO{" +
            "id=" + getId() +
            ", qteDemandee=" + getQteDemandee() +
            ", type='" + getType() + "'" +
            ", articleCode='" + getArticleCode() + "'" +
            ", demandeAchat=" + getDemandeAchat() +
            "}";
    }
}
