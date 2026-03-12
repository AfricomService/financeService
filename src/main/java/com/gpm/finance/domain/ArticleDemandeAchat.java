package com.gpm.finance.domain;

import com.gpm.finance.domain.enumeration.TypeArticleDemandeAchat;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ArticleDemandeAchat.
 */
@Entity
@Table(name = "article_demande_achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleDemandeAchat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "qte_demandee", nullable = false)
    private Float qteDemandee;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeArticleDemandeAchat type;

    /**
     * Cross-service FK → Article (projectService)
     */
    @Column(name = "article_code")
    private String articleCode;

    @ManyToOne(optional = false)
    @NotNull
    private DemandeAchat demandeAchat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArticleDemandeAchat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getQteDemandee() {
        return this.qteDemandee;
    }

    public ArticleDemandeAchat qteDemandee(Float qteDemandee) {
        this.setQteDemandee(qteDemandee);
        return this;
    }

    public void setQteDemandee(Float qteDemandee) {
        this.qteDemandee = qteDemandee;
    }

    public TypeArticleDemandeAchat getType() {
        return this.type;
    }

    public ArticleDemandeAchat type(TypeArticleDemandeAchat type) {
        this.setType(type);
        return this;
    }

    public void setType(TypeArticleDemandeAchat type) {
        this.type = type;
    }

    public String getArticleCode() {
        return this.articleCode;
    }

    public ArticleDemandeAchat articleCode(String articleCode) {
        this.setArticleCode(articleCode);
        return this;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public DemandeAchat getDemandeAchat() {
        return this.demandeAchat;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public ArticleDemandeAchat demandeAchat(DemandeAchat demandeAchat) {
        this.setDemandeAchat(demandeAchat);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleDemandeAchat)) {
            return false;
        }
        return id != null && id.equals(((ArticleDemandeAchat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleDemandeAchat{" +
            "id=" + getId() +
            ", qteDemandee=" + getQteDemandee() +
            ", type='" + getType() + "'" +
            ", articleCode='" + getArticleCode() + "'" +
            "}";
    }
}
