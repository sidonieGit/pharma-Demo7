package com.sidoCop.sysPharma.launcher;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sidoCop.sysPharma.domaine.model.Categorie;
import com.sidoCop.sysPharma.domaine.model.Medicament;
import com.sidoCop.sysPharma.service.ServiceMedicament;

public class Laucher {
	public static void main(String[] args) {

		// 1. Chargement du conteneur en 4.0.0 Spring
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("demo-beans.xml");

		// 2. Recuperation d'un bean du conteneur
		ServiceMedicament serviceMedicament = (ServiceMedicament) applicationContext.getBean("serviceMedicament");

		// 3. Manipulation du bean avec injection de dependances

		Medicament medicament1 = new Medicament(1, "Aspirine", 10.0, "Description", "image",
				new Categorie("ANTIINFLAMMATOIRES"));
		serviceMedicament.creerMedicament(medicament1);

		Medicament medicament2 = serviceMedicament.recherMedicament(2);
		if (medicament2 != null) {
			System.out.println("LAUNCHER: Récuperation du médicament " + medicament2.toString());
		} else {
			System.out.println("LAUNCHER: Récuperation du médicament échouée");
		}
		// applicationContext.close();
	}

}
