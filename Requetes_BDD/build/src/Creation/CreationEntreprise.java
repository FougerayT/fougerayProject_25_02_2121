package Creation;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CreationEntreprise {

	// numero siret
	private JFormattedTextField jtfSiret = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JLabel labelSiret = new JLabel("Numero SIRET");

	// nom
	private JTextField jtfNom = new JTextField("");
	private JLabel labelNom = new JLabel("Nom de l'enreprise");

	// ajout du boutton valider
	private JButton boutonValider = new JButton("Valider");


	// ajout du menu
	private JFrame frame;
	private JMenuBar menuBarRetourne;

	private JPanel container = new JPanel();

	public CreationEntreprise(JFrame frame) {
		this.frame = frame;





		// Numero siret et recherche de l'existance d'une entreprise
		Box top = Box.createHorizontalBox();
		jtfSiret.setToolTipText("numero de 15 chiffre"); // lorsqu'on laisse la souris dessus : donne des indications
		top.add(labelSiret);
		top.add(jtfSiret);
		top.setMaximumSize(new Dimension(300, 20));

		// caracteristique d'une entreprise
		Box l1 = Box.createHorizontalBox();
		l1.add(Box.createRigidArea(new Dimension(42, 0)));
		l1.add(labelNom);
		l1.add(jtfNom);

		Box valider = Box.createHorizontalBox();
		valider.add(boutonValider);
		

		Font police = new Font("Arial", Font.BOLD, 14);
		jtfSiret.setFont(police);
		jtfSiret.setForeground(Color.BLUE);

		// On positionne maintenant ces trois lignes en colonne
		Box b4 = Box.createVerticalBox();
		b4.add(Box.createVerticalStrut(10));
		b4.add(top);
		b4.add(Box.createVerticalStrut(10));
		b4.add(l1);
		b4.add(Box.createVerticalStrut(10));
		b4.add(valider);

		// listener du bouton valider
		boutonValider.addActionListener(new BoutonListener());

		frame.add(b4);

	}

	// Classe interne implémentant l'interface ItemListener
	class ItemState implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			System.out.println("événement déclenché sur : " + e.getItem());
		}
	}

	class StateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("source : " + ((JCheckBox) e.getSource()).getText() + " - état : "
					+ ((JCheckBox) e.getSource()).isSelected());
		}
	}

	class BoutonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// ajouter un message de confiration
			
			int value = (int) (jtfSiret.getValue());
			

			//System.out.println("Siret : " + jtfSiret.getValue());
			//System.out.println("Nom : " + jtfNom.getText());
			
			newEntreprise(6598123,"un",value,"quatre","cinq",1,2,3,4,5);

			
			
			
		}
	}

	class BoutonListenerRechercher implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			long value = (long) (jtfSiret.getValue());

			//si le nombre est dans la BDD
			if (value==123456789012345L)
			{
				jtfNom.setText("Fougeray");
			}
			// test siret de 15 chiffre pour creation 
			else if (value >= 100000000000000L && value < 1000000000000000L) {
				System.out.println(value);
			}
			//siret incorect 
			else 
			{
				jtfSiret.setText("");
			}

		}
	}

	public void start() {
		setLocation();
	}

	public void stop() {
		frame.setVisible(false);
	}

	void setLocation() {
		frame.setJMenuBar(menuBarRetourne);
		frame.setTitle("Creation d'une entreprise"); // Définit un titre pour notre fenêtre
		frame.setSize(600, 300); // Définit sa taille : 400 pixels de large et 100 pixels de haut
		frame.setLocationRelativeTo(null); // Nous demandons maintenant à notre objet de se positionner au centre
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termine le processus lorsqu'on clique sur la croix
																// rouge
		frame.setResizable(false); // Empeche le redimensionnement
		frame.setAlwaysOnTop(true); // garde touours la fenetre au premier plan

		frame.setVisible(true);

	}
	
	public static void newEntreprise(int siret, String nom_entreprise, int IDCC, String date_debut,String date_fin, int OPCO, int fHebergement, int fRestauration, int fpremieresEquipements, int fMobilités){
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
	        PreparedStatement ps = connexion.prepareStatement("INSERT INTO entreprises VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

	        ps.setLong(1,siret);
	        ps.setString(2,nom_entreprise);
	        ps.setInt(3, IDCC);
	        ps.setString(4,date_debut);
	        ps.setString(5,date_fin);
	        ps.setInt(6,OPCO);
	        ps.setInt(7,fHebergement);
	        ps.setInt(8,fRestauration);
	        ps.setInt(9,fpremieresEquipements);
	        ps.setInt(10,fMobilités);
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
}
