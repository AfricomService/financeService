package com.gpm.finance.domain;

import com.gpm.finance.domain.enumeration.StatutFraisDeMission;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FraisDeMission.
 */
@Entity
@Table(name = "frais_de_mission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FraisDeMission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @NotNull
    @Column(name = "montant_total", nullable = false)
    private Float montantTotal;

    @Column(name = "avance_recue")
    private Float avanceRecue;

    @NotNull
    @Column(name = "net_a_payer", nullable = false)
    private Float netAPayer;

    /**
     * CHECK max 6
     */
    @Column(name = "note_rendement")
    private Float noteRendement;

    /**
     * CHECK max 6
     */
    @Column(name = "note_qualite")
    private Float noteQualite;

    /**
     * CHECK max 2
     */
    @Column(name = "note_conduite")
    private Float noteConduite;

    /**
     * CHECK max 14
     */
    @Column(name = "note_totale")
    private Float noteTotale;

    @Column(name = "bonus_extra")
    private Float bonusExtra;

    @Column(name = "justificatif_bonus")
    private String justificatifBonus;

    @Column(name = "justificatif_modification")
    private String justificatifModification;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutFraisDeMission statut;

    /**
     * Cross-service FK → WorkOrder (operationsService)
     */
    @Column(name = "work_order_id")
    private Long workOrderId;

    /**
     * Cross-service FK → Utilisateur (operationsService)
     */
    @Column(name = "beneficiaire_id")
    private String beneficiaireId;

    @Column(name = "beneficiaire_user_login")
    private String beneficiaireUserLogin;

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

    public FraisDeMission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public FraisDeMission dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public FraisDeMission dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Float getMontantTotal() {
        return this.montantTotal;
    }

    public FraisDeMission montantTotal(Float montantTotal) {
        this.setMontantTotal(montantTotal);
        return this;
    }

    public void setMontantTotal(Float montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Float getAvanceRecue() {
        return this.avanceRecue;
    }

    public FraisDeMission avanceRecue(Float avanceRecue) {
        this.setAvanceRecue(avanceRecue);
        return this;
    }

    public void setAvanceRecue(Float avanceRecue) {
        this.avanceRecue = avanceRecue;
    }

    public Float getNetAPayer() {
        return this.netAPayer;
    }

    public FraisDeMission netAPayer(Float netAPayer) {
        this.setNetAPayer(netAPayer);
        return this;
    }

    public void setNetAPayer(Float netAPayer) {
        this.netAPayer = netAPayer;
    }

    public Float getNoteRendement() {
        return this.noteRendement;
    }

    public FraisDeMission noteRendement(Float noteRendement) {
        this.setNoteRendement(noteRendement);
        return this;
    }

    public void setNoteRendement(Float noteRendement) {
        this.noteRendement = noteRendement;
    }

    public Float getNoteQualite() {
        return this.noteQualite;
    }

    public FraisDeMission noteQualite(Float noteQualite) {
        this.setNoteQualite(noteQualite);
        return this;
    }

    public void setNoteQualite(Float noteQualite) {
        this.noteQualite = noteQualite;
    }

    public Float getNoteConduite() {
        return this.noteConduite;
    }

    public FraisDeMission noteConduite(Float noteConduite) {
        this.setNoteConduite(noteConduite);
        return this;
    }

    public void setNoteConduite(Float noteConduite) {
        this.noteConduite = noteConduite;
    }

    public Float getNoteTotale() {
        return this.noteTotale;
    }

    public FraisDeMission noteTotale(Float noteTotale) {
        this.setNoteTotale(noteTotale);
        return this;
    }

    public void setNoteTotale(Float noteTotale) {
        this.noteTotale = noteTotale;
    }

    public Float getBonusExtra() {
        return this.bonusExtra;
    }

    public FraisDeMission bonusExtra(Float bonusExtra) {
        this.setBonusExtra(bonusExtra);
        return this;
    }

    public void setBonusExtra(Float bonusExtra) {
        this.bonusExtra = bonusExtra;
    }

    public String getJustificatifBonus() {
        return this.justificatifBonus;
    }

    public FraisDeMission justificatifBonus(String justificatifBonus) {
        this.setJustificatifBonus(justificatifBonus);
        return this;
    }

    public void setJustificatifBonus(String justificatifBonus) {
        this.justificatifBonus = justificatifBonus;
    }

    public String getJustificatifModification() {
        return this.justificatifModification;
    }

    public FraisDeMission justificatifModification(String justificatifModification) {
        this.setJustificatifModification(justificatifModification);
        return this;
    }

    public void setJustificatifModification(String justificatifModification) {
        this.justificatifModification = justificatifModification;
    }

    public StatutFraisDeMission getStatut() {
        return this.statut;
    }

    public FraisDeMission statut(StatutFraisDeMission statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutFraisDeMission statut) {
        this.statut = statut;
    }

    public Long getWorkOrderId() {
        return this.workOrderId;
    }

    public FraisDeMission workOrderId(Long workOrderId) {
        this.setWorkOrderId(workOrderId);
        return this;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getBeneficiaireId() {
        return this.beneficiaireId;
    }

    public FraisDeMission beneficiaireId(String beneficiaireId) {
        this.setBeneficiaireId(beneficiaireId);
        return this;
    }

    public void setBeneficiaireId(String beneficiaireId) {
        this.beneficiaireId = beneficiaireId;
    }

    public String getBeneficiaireUserLogin() {
        return this.beneficiaireUserLogin;
    }

    public FraisDeMission beneficiaireUserLogin(String beneficiaireUserLogin) {
        this.setBeneficiaireUserLogin(beneficiaireUserLogin);
        return this;
    }

    public void setBeneficiaireUserLogin(String beneficiaireUserLogin) {
        this.beneficiaireUserLogin = beneficiaireUserLogin;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public FraisDeMission createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public FraisDeMission updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public FraisDeMission createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByUserLogin() {
        return this.createdByUserLogin;
    }

    public FraisDeMission createdByUserLogin(String createdByUserLogin) {
        this.setCreatedByUserLogin(createdByUserLogin);
        return this;
    }

    public void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FraisDeMission updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedByUserLogin() {
        return this.updatedByUserLogin;
    }

    public FraisDeMission updatedByUserLogin(String updatedByUserLogin) {
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
        if (!(o instanceof FraisDeMission)) {
            return false;
        }
        return id != null && id.equals(((FraisDeMission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FraisDeMission{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", montantTotal=" + getMontantTotal() +
            ", avanceRecue=" + getAvanceRecue() +
            ", netAPayer=" + getNetAPayer() +
            ", noteRendement=" + getNoteRendement() +
            ", noteQualite=" + getNoteQualite() +
            ", noteConduite=" + getNoteConduite() +
            ", noteTotale=" + getNoteTotale() +
            ", bonusExtra=" + getBonusExtra() +
            ", justificatifBonus='" + getJustificatifBonus() + "'" +
            ", justificatifModification='" + getJustificatifModification() + "'" +
            ", statut='" + getStatut() + "'" +
            ", workOrderId=" + getWorkOrderId() +
            ", beneficiaireId='" + getBeneficiaireId() + "'" +
            ", beneficiaireUserLogin='" + getBeneficiaireUserLogin() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdByUserLogin='" + getCreatedByUserLogin() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedByUserLogin='" + getUpdatedByUserLogin() + "'" +
            "}";
    }
}
