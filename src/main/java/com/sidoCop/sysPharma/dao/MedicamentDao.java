package com.sidoCop.sysPharma.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sidoCop.sysPharma.domaine.model.Categorie;
import com.sidoCop.sysPharma.domaine.model.Medicament;

public class MedicamentDao implements IMedicamentDao {

	private DataSource dataSourceSk;

	public DataSource getDataSourceSk() {
		return dataSourceSk;
	}

	public void setDataSourceSk(DataSource dataSourceSk) {
		this.dataSourceSk = dataSourceSk;
	}

	@Override
	public Medicament recuperationMedicament(int id) {
		System.out.println("DAO: récupération du médicament id=" + id);

		// Information d'acc�s � la base de donn�es
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : r�cup�ration de la connexion
			cn = dataSourceSk.getConnection();

			// Etape 3 : Cr�ation d'un statement
			st = cn.createStatement();

			String sql = "SELECT * FROM Medicament WHERE id=" + id;

			// Etape 4 : ex�cution requ�te
			rs = st.executeQuery(sql);

			// Si r�cup donn�es alors �tapes 5 (parcours Resultset)

			while (rs.next()) {
				return new Medicament(rs.getInt("id"),
						rs.getString("designation"),
						rs.getInt("prix"),
						rs.getString("description"),
						rs.getString("image"),
						new Categorie(rs.getString("designation")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// Etape 6 : lib�rer ressources de la m�moire.
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void creerMedicament(Medicament medicament) {
		System.out.println("DAO: création du médicament " + medicament.toString());

		// Information d'acc�s � la base de donn�es
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : r�cup�ration de la connexion
			cn = dataSourceSk.getConnection();

			// Etape 3 : Cr�ation d'un statement
			st = cn.createStatement();

			String sql = "INSERT INTO Medicament (designation, prix, description, image, designationCategorie) VALUES ('"
					+ medicament.getDesignation() + "'," + medicament.getPrix() + ",'" + medicament.getDescription() + "','"
					+ medicament.getImage() + "','" + medicament.getCategorie().getDesignation() + "')";

			// Etape 4 : ex�cution requ�te
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// Etape 6 : lib�rer ressources de la m�moire.
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Medicament modifierMedicament(Medicament medicament) {
		System.out.println("DAO: modification du médicament " + medicament.toString());

		// Information d'acc�s � la base de donn�es
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : r�cup�ration de la connexion
			cn = dataSourceSk.getConnection();

			// Etape 3 : Cr�ation d'un statement
			st = cn.createStatement();

			String sql = "UPDATE Medicament SET designation='" + medicament.getDesignation() + "', prix="
					+ medicament.getPrix()
					+ ", description='" + medicament.getDescription() + "', image='" + medicament.getImage()
					+ "', designationCategorie='"
					+ medicament.getCategorie().getDesignation() + "' WHERE id=" + medicament.getId();

			// Etape 4 : ex�cution requ�te
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// Etape 6 : lib�rer ressources de la m�moire.
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return medicament;
	}

	@Override
	public void supprimerMedicament(Medicament medicament) {
		System.out.println("DAO: suppression du médicament " + medicament.toString());

		// Information d'acc�s � la base de donn�es
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : r�cup�ration de la connexion
			cn = dataSourceSk.getConnection();

			// Etape 3 : Cr�ation d'un statement
			st = cn.createStatement();

			String sql = "DELETE FROM Medicament WHERE id=" + medicament.getId();

			// Etape 4 : ex�cution requ�te
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// Etape 6 : lib�rer ressources de la m�moire.
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Medicament> recuperationListeMedicament() {
		System.out.println("DAO: récupération de tous les médicaments");

		// Information d'acc�s � la base de donn�es
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : r�cup�ration de la connexion
			cn = dataSourceSk.getConnection();

			// Etape 3 : Cr�ation d'un statement
			st = cn.createStatement();

			String sql = "SELECT * FROM Medicament";

			// Etape 4 : ex�cution requ�te
			rs = st.executeQuery(sql);

			// Si r�cup donn�es alors �tapes 5 (parcours Resultset)

			List<Medicament> listeMedicament = new ArrayList<Medicament>();

			while (rs.next()) {
				listeMedicament.add(new Medicament(rs.getInt("id"),
						rs.getString("designation"),
						rs.getInt("prix"),
						rs.getString("description"),
						rs.getString("image"),
						new Categorie(rs.getString("designation"))));
			}
			return listeMedicament;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// Etape 6 : lib�rer ressources de la m�moire.
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void initialisation() {
		System.out.println("Dao: création du spring");
	}

	public void destruction() {
		System.out.println("Dao: destruction du spring");
	}

}