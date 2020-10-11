package gui.login;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import entity.korisnici.Admin;
import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import entity.korisnici.MedicinskiTehničar;
import entity.korisnici.Pacijent;
import exceptions.KorisnikNijeNadjen;
import gui.admin.AdminFrame;
import gui.laborant.LaborantFrame;
import gui.medicinar.MedicinskiTehničarFrame;
import gui.pacijent.PacijentFrame;
import net.miginfocom.swing.MigLayout;
import repositories.RepositoryFactory;
import res.ResourceLoader;
import services.KorisniciServis;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -8026416994513756565L;

	KorisniciServis korisniciServis = new KorisniciServis();

	JButton btnPrijava = new JButton("Prijava");
	JButton btnIzlaz = new JButton("Izlaz");

	JTextField tfKorisnickoIme = new JTextField(20);
	JPasswordField pfLozinka = new JPasswordField(20);

	public MainFrame() {
		this.setIconImage(ResourceLoader.getImageIcon("main.png").getImage());
		this.setTitle("Prijava");
		this.setLayout(new MigLayout());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.getContentPane().setBackground(new Color(193, 229, 255));
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
		initAkcije();
	}

	private void initGUI() {
		JLabel usernameImage = new JLabel();
		ImageIcon userIcon = ResourceLoader.getImageIcon("username.png");
		usernameImage.setIcon(userIcon);

		JLabel passwordImage = new JLabel();
		ImageIcon passwordIcon = ResourceLoader.getImageIcon("password.png");
		passwordImage.setIcon(passwordIcon);

		this.getRootPane().setDefaultButton(btnPrijava);

		this.add(new JLabel("Dobrodošli. Prijavite se na sistem."), "span, center, wrap");
		this.add(usernameImage, "split 3, sg b");
		this.add(new JLabel("Korisničko ime: "), "sg a");
		this.add(tfKorisnickoIme, "pushx, growx, wrap");
		this.add(passwordImage, "split 3, sg b");
		this.add(new Label("Lozinka: "), "sg a");
		this.add(pfLozinka, "pushx, growx, wrap");
		this.add(new JLabel(""));
		this.add(new JLabel(""));
		this.add(btnPrijava);
		this.add(btnIzlaz);
	}
	
	
	private void initAkcije() {

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				proveraIzlaksaUpit();
			}
		});
		
		btnIzlaz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				proveraIzlaksaUpit();
			}
		});

		btnPrijava.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = tfKorisnickoIme.getText();
				String password = new String(pfLozinka.getPassword());
				if (!username.equals("") & !password.equals("")) {
					checkUser(username, password);
				} else {
					JOptionPane.showMessageDialog(null, "Unesite korisničko ime i lozinku.", "Greška",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		pfLozinka.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					tfKorisnickoIme.requestFocusInWindow();
				}
			}
		});
		
		tfKorisnickoIme.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					pfLozinka.requestFocusInWindow();
				}
			}
		});

	}

	private void checkUser(String username, String password) {
		try {
			Korisnik korisnik = korisniciServis.getRegistrovanKorisnik(username, password);
			otvoriProzorZaKorisnika(korisnik);
		} catch (KorisnikNijeNadjen e) {
			JOptionPane.showMessageDialog(null, "Korisnik nije pronadjen.\nProverite podatke i probajte ponovo.",
					"Informacija", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void otvoriProzorZaKorisnika(Korisnik korisnik) {
		this.setVisible(false);
		this.dispose();
		if (korisnik instanceof Admin) {
			AdminFrame adminFrame = new AdminFrame((Admin) korisnik);
			adminFrame.setVisible(true);
		} else if (korisnik instanceof Laborant) {
			LaborantFrame laborantFrame = new LaborantFrame((Laborant) korisnik);
			laborantFrame.setVisible(true);
		} else if (korisnik instanceof MedicinskiTehničar) {
			MedicinskiTehničarFrame medicinskiTehničarFrame = new MedicinskiTehničarFrame(
					(MedicinskiTehničar) korisnik);
			medicinskiTehničarFrame.setVisible(true);
		} else if (korisnik instanceof Pacijent) {
			PacijentFrame pacijentFrame = new PacijentFrame((Pacijent) korisnik);
			pacijentFrame.setVisible(true);
		}
	}

	private void proveraIzlaksaUpit() {
		int option = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da napustite aplikaciju?",
				"Kraj rada", JOptionPane.YES_NO_CANCEL_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			// ne cuvam promene zbog eksprimentisanja sa jar fajlom
			//RepositoryFactory.getInstance().saveData();
			System.exit(0);
		}
	}

}
