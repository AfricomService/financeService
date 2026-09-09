package com.gpm.finance.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gpm.finance.domain.OtExterneAutreResponsable} entity.
 */
public class OtExterneAutreResponsableDTO implements Serializable {

    private Long id;

    private Long otExterneId;

    private Long contactSocieteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOtExterneId() {
        return otExterneId;
    }

    public void setOtExterneId(Long otExterneId) {
        this.otExterneId = otExterneId;
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
        if (!(o instanceof OtExterneAutreResponsableDTO)) {
            return false;
        }

        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO = (OtExterneAutreResponsableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, otExterneAutreResponsableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtExterneAutreResponsableDTO{" +
            "id=" + getId() +
            ", otExterneId=" + getOtExterneId() +
            ", contactSocieteId=" + getContactSocieteId() +
            "}";
    }
}
