package sqlRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class request {


public static void newEntreprise(String siret, String nom_entreprise){
	String url = "jdbc:mysql://localhost:3306/mabase";
	String utilisateur = "root";
	String motDePasse = "";
	Connection connexion = null;

	
	try {
		// etape 1 : chargement du serveur 
	    Class.forName( "com.mysql.jdbc.Driver" );
	    
	    // etape 2 : recuperation de la connection 
	    connexion = DriverManager.getConnection( url, utilisateur, motDePasse);
        connexion.setAutoCommit(true);

	    
	    // etape 3 : creation d'un statement             
        PreparedStatement ps = connexion.prepareStatement("INSERT INTO entreprises VALUES (?, ?)");

        ps.setString(1,siret);
        ps.setString(2,nom_entreprise);
        ps.addBatch();

        ps.clearParameters();
        int[] results = ps.executeBatch();

	    
	    System.out.println("Entreprise créée");
	    
	} catch ( SQLException e ) {
		e.printStackTrace();
	}catch ( ClassNotFoundException e ) {
		e.printStackTrace();
	} finally {
	    if ( connexion != null )
	        try {
	            /* Fermeture de la connexion */
	            connexion.close();
	        } catch ( SQLException e ) {
	        	e.printStackTrace();
	        }
	}
	

}
public static void readAllStudent(){
	String url = "jdbc:mysql://localhost:3306/mabase";
	String utilisateur = "root";
	String motDePasse = "";
	Connection connexion = null;
	Statement st = null;
	ResultSet rs = null;

	
	try {
		// etape 1 : chargement du serveur 
	    Class.forName( "com.mysql.jdbc.Driver" );
	    
	    // etape 2 : recuperation de la connection 
	    connexion = DriverManager.getConnection( url, utilisateur, motDePasse);
        connexion.setAutoCommit(true);
	    
	    // etape 3 : creation d'un statement    
        st = connexion.createStatement();
        String sql = "SELECT * FROM javadb";
        
        //etape 4 execution de la requete 
        rs = st.executeQuery(sql);
        
        //etape 5 parcours des resultats
        /* Récupération des données du résultat de la requête de lecture */
        while ( rs.next() ) {
            String nom = rs.getString(1);
            String prenom = rs.getString(2);

            /* Traiter ici les valeurs récupérées. */
            System.out.println("nom :" + nom + "  prenom :" + prenom );
        }

	    
	    System.out.println("Voici la liste des étudiants");
	    
	} catch ( SQLException e ) {
		e.printStackTrace();
	}catch ( ClassNotFoundException e ) {
		e.printStackTrace();
	} finally {
	    if ( connexion != null )
	        try {
	            /* Fermeture de la connexion */
	            connexion.close();
	            st.close();
	        } catch ( SQLException e ) {
	        	e.printStackTrace();
	        }
	}
	}
	
	public static void findStudent(String col, String row ){
		String url = "jdbc:mysql://localhost:3306/mabase";
		String utilisateur = "root";
		String motDePasse = "";
		Connection connexion = null;
		Statement st = null;
		ResultSet rs = null;
	
		
		try {
			// etape 1 : chargement du serveur 
		    Class.forName( "com.mysql.jdbc.Driver" );
		    
		    // etape 2 : recuperation de la connection 
		    connexion = DriverManager.getConnection( url, utilisateur, motDePasse);
	        connexion.setAutoCommit(true);

		    // etape 3 : creation d'un statement    
	        st = connexion.createStatement();
            String sql = "SELECT * FROM javadb WHERE " + col + " = " + "\"" + row + "\"";
            
            System.out.println(sql);
            //etape 4 execution de la requete 
            rs = st.executeQuery(sql);
            
            while ( rs.next() ) {
                String nom = rs.getString(1);
                String prenom = rs.getString(2);

                /* Traiter ici les valeurs récupérées. */
                System.out.println("nom :" + nom + "  prenom :" + prenom );
            }
		    
		    System.out.println("Voici l'étudiant que tu cherchais");
		    
		} catch ( SQLException e ) {
			e.printStackTrace();
		}catch ( ClassNotFoundException e ) {
			e.printStackTrace();
		} finally {
		    if ( connexion != null )
		        try {
		            /* Fermeture de la connexion */
		            connexion.close();
		            st.close();
		        } catch ( SQLException e ) {
		        	e.printStackTrace();
		        }
		}
	

}
}