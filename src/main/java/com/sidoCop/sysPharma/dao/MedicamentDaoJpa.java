package com.sidoCop.sysPharma.dao;

import java.util.List;

import com.sidoCop.sysPharma.domaine.model.Categorie;
import com.sidoCop.sysPharma.domaine.model.Medicament;

public class MedicamentDaoJpa implements IMedicamentDao {

	@Override
	public Medicament recuperationMedicament(int id) {
		System.out.println("MedicamentDaoJpa: récupération du médicament id=" + id);

		if (id == 2) {
			Medicament medicament = new Medicament(2, "Aspirine", 10.0, "Description", "image",
					new Categorie("ANTIINFLAMMATOIRES"));// création d'un objet-mock- MOCK SGBDR
			return medicament;

		}
		return null;
	}

	@Override
	public void creerMedicament(Medicament medicament) {
		System.out.println("MedicamentDaoJpa: création du médicament " + medicament.toString());

	}

	@Override
	public Medicament modifierMedicament(Medicament medicament) {
		System.out.println("MedicamentDaoJpa: modification du médicament " + medicament.toString());
		return medicament;
	}

	@Override
	public void supprimerMedicament(Medicament medicament) {
		System.out.println("MedicamentDaoJpa: suppression du médicament " + medicament.toString());
	}

	@Override
	public List<Medicament> recuperationListeMedicament() {
		System.out.println("MedicamentDaoJpa: récupération de tous les médicaments");
		return null;
	}

	public void initialisation() {
		System.out.println("Dao: création du spring");
	}

	public void destruction() {
		System.out.println("Dao: destruction du spring");
	}

}
