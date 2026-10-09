package com.gpm.finance.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String CAN_SEE_PRICE = "ROLE_CAN_SEE_PRICE";
    public static final String CAN_ACTIVATE_BON_COMMANDE = "ROLE_CAN_ACTIVATE_BON_COMMANDE";
    public static final String CAN_ACTIVATE_OT_EXTERNE = "ROLE_CAN_ACTIVATE_OT_EXTERNE";

    private AuthoritiesConstants() {}
}
