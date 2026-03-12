package com.gpm.finance.domain;

import com.gpm.finance.domain.enumeration.StatutDemandeAchat;
import com.gpm.finance.domain.enumeration.TypeDemandeAchat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandeAchat.
 */
@Entity
@Table(name = "demande_achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeAchat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * DA-YYYY-XXXX auto-generated
     */
    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutDemandeAchat statut;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeDemandeAchat type;

    @NotNull
    @Column(name = "date_creation", nullable = false)
    private ZonedDateTime dateCreation;

    @NotNull
    @Column(name = "date_mise_a_disposition", nullable = false)
    private LocalDate dateMiseADisposition;

    /**
     * Cross-service FK → WorkOrder (operationsService)
     */
    @Column(name = "work_order_id")
    private Long workOrderId;

    /**
     * Cross-service FK → Affaire (projectService)
     */
    @Column(name = "affaire_id")
    private Long affaireId;

    /**
     * Cross-service FK → Utilisateur (operationsService) — Demandeur
     */
    @Column(name = "demandeur_id")
    private String demandeurId;

    @Column(name = "demandeur_user_login")
    private String demandeurUserLogin;

    /**
     * Cross-service FK → Utilisateur (operationsService) — Validateur
     */
    @Column(name = "validateur_id")
    private String validateurId;

    @Column(name = "validateur_user_login")
    private String validateurUserLogin;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_by_user_login")
    private String createdByUserLogin;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_by_user_login")
    private String updatedByUserLogin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeAchat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public DemandeAchat code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StatutDemandeAchat getStatut() {
        return this.statut;
    }

    public DemandeAchat statut(StatutDemandeAchat statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutDemandeAchat statut) {
        this.statut = statut;
    }

    public TypeDemandeAchat getType() {
        return this.type;
    }

    public DemandeAchat type(TypeDemandeAchat type) {
        this.setType(type);
        return this;
    }

    public void setType(TypeDemandeAchat type) {
        this.type = type;
    }

    public ZonedDateTime getDateCreation() {
        return this.dateCreation;
    }

    public DemandeAchat dateCreation(ZonedDateTime dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateMiseADisposition() {
        return this.dateMiseADisposition;
    }

    public DemandeAchat dateMiseADisposition(LocalDate dateMiseADisposition) {
        this.setDateMiseADisposition(dateMiseADisposition);
        return this;
    }

    public void setDateMiseADisposition(LocalDate dateMiseADisposition) {
        this.dateMiseADisposition = dateMiseADisposition;
    }

    public Long getWorkOrderId() {
        return this.workOrderId;
    }

    public DemandeAchat workOrderId(Long workOrderId) {
        this.setWorkOrderId(workOrderId);
        return this;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Long getAffaireId() {
        return this.affaireId;
    }

    public DemandeAchat affaireId(Long affaireId) {
        this.setAffaireId(affaireId);
        return this;
    }

    public void setAffaireId(Long affaireId) {
        this.affaireId = affaireId;
    }

    public String getDemandeurId() {
        return this.demandeurId;
    }

    public DemandeAchat demandeurId(String demandeurId) {
        this.setDemandeurId(demandeurId);
        return this;
    }

    public void setDemandeurId(String demandeurId) {
        this.demandeurId = demandeurId;
    }

    public String getDemandeurUserLogin() {
        return this.demandeurUserLogin;
    }

    public DemandeAchat demandeurUserLogin(String demandeurUserLogin) {
        this.setDemandeurUserLogin(demandeurUserLogin);
        return this;
    }

    public void setDemandeurUserLogin(String demandeurUserLogin) {
        this.demandeurUserLogin = demandeurUserLogin;
    }

    public String getValidateurId() {
        return this.validateurId;
    }

    public DemandeAchat validateurId(String validateurId) {
        this.setValidateurId(validateurId);
        return this;
    }

    public void setValidateurId(String validateurId) {
        this.validateurId = validateurId;
    }

    public String getValidateurUserLogin() {
        return this.validateurUserLogin;
    }

    public DemandeAchat validateurUserLogin(String validateurUserLogin) {
        this.setValidateurUserLogin(validateurUserLogin);
        return this;
    }

    public void setValidateurUserLogin(String validateurUserLogin) {
        this.validateurUserLogin = validateurUserLogin;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public DemandeAchat createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public DemandeAchat updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public DemandeAchat createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByUserLogin() {
        return this.createdByUserLogin;
    }

    public DemandeAchat createdByUserLogin(String createdByUserLogin) {
        this.setCreatedByUserLogin(createdByUserLogin);
        return this;
    }

    public void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public DemandeAchat updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedByUserLogin() {
        return this.updatedByUserLogin;
    }

    public DemandeAchat updatedByUserLogin(String updatedByUserLogin) {
        this.setUpdatedByUserLogin(updatedByUserLogin);
        return this;
    }

    public void setUpdatedByUserLogin(String updatedByUserLogin) {
        this.updatedByUserLogin = updatedByUserLogin;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeAchat)) {
            return false;
        }
        return id != null && id.equals(((DemandeAchat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeAchat{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", statut='" + getStatut() + "'" +
            ", type='" + getType() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateMiseADisposition='" + getDateMiseADisposition() + "'" +
            ", workOrderId=" + getWorkOrderId() +
            ", affaireId=" + getAffaireId() +
            ", demandeurId='" + getDemandeurId() + "'" +
            ", demandeurUserLogin='" + getDemandeurUserLogin() + "'" +
            ", validateurId='" + getValidateurId() + "'" +
            ", validateurUserLogin='" + getValidateurUserLogin() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdByUserLogin='" + getCreatedByUserLogin() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedByUserLogin='" + getUpdatedByUserLogin() + "'" +
            "}";
    }
}
