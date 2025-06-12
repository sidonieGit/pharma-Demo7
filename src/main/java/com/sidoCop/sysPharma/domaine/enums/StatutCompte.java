package com.sidoCop.sysPharma.domaine.enums;

public enum StatutCompte {
    ACTIF,
    INACTIF,
    DESACTIVE;

    public boolean isActif() {
        return this == ACTIF;
    }
}
