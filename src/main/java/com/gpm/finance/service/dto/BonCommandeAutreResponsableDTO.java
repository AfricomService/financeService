package com.gpm.finance.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gpm.finance.domain.BonCommandeAutreResponsable} entity.
 */
public class BonCommandeAutreResponsableDTO implements Serializable {

    private Long id;

    private Long bonCommandeId;

    private Long contactSocieteId;

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

    public Long getContactSocieteId() {
        return contactSocieteId;
    }

    public void setContactSocieteId(Long contactSocieteId) {
        this.contactSocieteId = contactSocieteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommandeAutreResponsableDTO)) {
            return false;
        }

        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO = (BonCommandeAutreResponsableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bonCommandeAutreResponsableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommandeAutreResponsableDTO{" +
            "id=" + getId() +
            ", bonCommandeId=" + getBonCommandeId() +
            ", contactSocieteId=" + getContactSocieteId() +
            "}";
    }
}
