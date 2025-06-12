package com.sidoCop.sysPharma.domaine.model;

public class MedicamentGenerique extends Medicament {
    private Medicament medicamentOriginal;

    public MedicamentGenerique(int id, String designation, double prix, String description, String image,
            Categorie categorie,
            Medicament medicamentOriginal) {
        super(id, designation, prix, description, image, categorie);
        this.medicamentOriginal = medicamentOriginal;
    }

    // getter
}
