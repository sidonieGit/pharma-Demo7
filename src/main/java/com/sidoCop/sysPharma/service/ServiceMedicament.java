package com.sidoCop.sysPharma.service;

import java.util.List;

import com.sidoCop.sysPharma.dao.IMedicamentDao;
import com.sidoCop.sysPharma.domaine.model.Medicament;

/**
 * Service de gestion des médicaments (CRUD + recherche).
 */
public class ServiceMedicament {

    private IMedicamentDao imedicamentDao;

    public ServiceMedicament() {
        super();
    }

    // injection de l'objet dao-encore en paramètre -appelé injection de dépendance
    public ServiceMedicament(IMedicamentDao imedicamentDao) {
        super();// pour que l'objet soit instanciable

        this.imedicamentDao = imedicamentDao;
    }

    public Medicament recherMedicament(int id) {
        System.out.println("SERVICE: récupération du médicament id=" + id);
        Medicament medicamentRetour = imedicamentDao.recuperationMedicament(id);
        return medicamentRetour;
    }

    public void creerMedicament(Medicament medicament) {
        System.out.println("SERVICE: création du médicament " + medicament.toString());
        imedicamentDao.creerMedicament(medicament);
    }

    public Medicament modifierMedicament(Medicament medicament) {
        System.out.println("SERVICE: modification du médicament " + medicament.toString());
        imedicamentDao.modifierMedicament(medicament);
        return medicament;
    }

    public void supprimerMedicament(Medicament medicament) {
        System.out.println("SERVICE: suppression du médicament " + medicament.toString());
        imedicamentDao.supprimerMedicament(medicament);
    }

    public List<Medicament> recuperationListeMedicament() {
        System.out.println("SERVICE: récupération de tous les médicaments");
        List<Medicament> medicamentListeRetour = imedicamentDao.recuperationListeMedicament();
        return medicamentListeRetour;
    }

    public IMedicamentDao getImedicamentDao() {
        return imedicamentDao;
    }

    public void setImedicamentDao(IMedicamentDao imedicamentDao) {
        this.imedicamentDao = imedicamentDao;
    }

    public void initialisation() {
        System.out.println("service: creation spring");
    }

    public void destruction() {
        System.out.println("service: destruction spring");
    }

}
