package gui.medicinar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.ImageGraphicAttribute;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

import entity.Nalaz;
import entity.korisnici.Korisnik;
import entity.korisnici.MedicinskiTehničar;
import gui.ZatraziZahtevTableFrame;
import gui.PacijentTableFrame;
import gui.PregledIstampanjeGotovihNalazaDialog;
import gui.ZahteviFrame;
import gui.login.MainFrame;
import gui.pacijent.PacijentFrame;
import net.miginfocom.swing.MigLayout;
import repositories.korisnici.KorisnikRepository;
import res.ResourceLoader;
import services.NalaziServis;
import services.ZahteviServis;

public class MedicinskiTehničarFrame extends JFrame{

	private static final long serialVersionUID = -2359848928875673735L;
	
	private MedicinskiTehničar mTehničar;
	private NalaziServis nalaziServis = new NalaziServis();
	
	private JButton btnPacijenti = new JButton("Pacijenti");
	private JButton btnKreirajZahtev = new JButton("Napravi zahtev");
	private JButton btnPrikazSvihZahteva = new JButton("Prikaz svih zahteva");
	private JButton btnUzmiKucnuPosetu = new JButton("Zahtevi sa kućnom posetom");
	private JButton btnMojeKucnePosete = new JButton("Moje trenutne preuzete posete");
	private JButton btnStaviUObradu = new JButton("Označi zahtev za obradu");
	private JButton btnPregledNalaza = new JButton("Pregled obradjenih nalaza");
	private JButton btnOdjava = new JButton("Odjavi se");
	
	private ZahteviServis zahteviServis = new ZahteviServis();
	
	public MedicinskiTehničarFrame(MedicinskiTehničar m) {
		this.mTehničar = m;
		this.setIconImage(ResourceLoader.getImageIcon("main.png").getImage());
		setTitle("Medicinski tehničar: " + m.getIme() + " " + m.getPrezime());
		setPreferredSize(new Dimension(900,600));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		initGui();
		pack();
		setLocationRelativeTo(null);
		initAkcije();
	}
	
	
	private void initGui() {
		setButtons();
		podesiZapad();
		podesiCentar();
		
	}
	
	private void setButtons() {
		Dimension d = new Dimension(250,30);
		btnPacijenti.setPreferredSize(d);
		btnKreirajZahtev.setPreferredSize(d);
		btnMojeKucnePosete.setPreferredSize(d);
		btnOdjava.setPreferredSize(d);
		btnPregledNalaza.setPreferredSize(d);
		btnPrikazSvihZahteva.setPreferredSize(d);
		btnStaviUObradu.setPreferredSize(d);
		btnUzmiKucnuPosetu.setPreferredSize(d);
	}
	
	private void podesiZapad() {
		JPanel panelZapad = new JPanel(new MigLayout());
		panelZapad.setBackground(new Color(193, 229, 255));
		
		JLabel pacijentiImage = kreirajLabeluSaSlikom("pacijenti.png",45,45);
		JLabel napraviZahtevImage = kreirajLabeluSaSlikom("plus.png", 45, 45);
		JLabel zahteviSaKucnomPosetomImage = kreirajLabeluSaSlikom("kuca.png", 45, 45);
		JLabel oznaciZahtevZaObraduImage = kreirajLabeluSaSlikom("tick.png", 45, 45);
		JLabel prikazObradjenihNalazaImage = kreirajLabeluSaSlikom("pregledNalaza.png", 45, 45);
		JLabel prikazSvihZahtevaImage = kreirajLabeluSaSlikom("information.png", 45, 45);
		JLabel odjavaImage = kreirajLabeluSaSlikom("logout.png", 45, 45);
		JLabel mojePreuztePoseteImage = kreirajLabeluSaSlikom("archive.png", 45, 45);
		
		panelZapad.add(pacijentiImage, "split 2, sg a");
		panelZapad.add(btnPacijenti, "wrap");
		panelZapad.add(napraviZahtevImage, "split 2, sg a");
		panelZapad.add(btnKreirajZahtev, "wrap");
		panelZapad.add(prikazSvihZahtevaImage, "split 2, sg a");
		panelZapad.add(btnPrikazSvihZahteva, "wrap");
		panelZapad.add(zahteviSaKucnomPosetomImage, "split 2, sg a");
		panelZapad.add(btnUzmiKucnuPosetu, "wrap");
		panelZapad.add(mojePreuztePoseteImage, "split 2, sg a");
		panelZapad.add(btnMojeKucnePosete, "wrap");
		panelZapad.add(oznaciZahtevZaObraduImage, "split 2, sg a");
		panelZapad.add(btnStaviUObradu, "wrap");
		panelZapad.add(oznaciZahtevZaObraduImage, "split 2, sg a");
		panelZapad.add(btnStaviUObradu, "wrap");
		panelZapad.add(prikazObradjenihNalazaImage, "split 2, sg a");
		panelZapad.add(btnPregledNalaza, "wrap");
		panelZapad.add(odjavaImage, "split 2, sg a");
		panelZapad.add(btnOdjava, "wrap");
		
		this.getContentPane().add(panelZapad, BorderLayout.WEST);
		
	}
	
	private void podesiCentar() {
		JPanel panelCentar = new JPanel(new MigLayout());
		panelCentar.setBackground(Color.white);
		
		JLabel userIconLabel = kreirajLabeluSaSlikom("profile.png", 130, 130);
		
		panelCentar.add(userIconLabel, "span, center, wrap");
		panelCentar.add(new JLabel(" "), "span, wrap");
		panelCentar.add(new JLabel("       Ime:"), "split 2, sg a");
		panelCentar.add(new JLabel(this.mTehničar.getIme()), "wrap");
		panelCentar.add(new JLabel("       Prezime:"), "split 2, sg a");
		panelCentar.add(new JLabel(this.mTehničar.getPrezime()), "wrap");
		panelCentar.add(new JLabel("       Korisničko ime:"), "split 2, sg a");
		panelCentar.add(new JLabel(this.mTehničar.getUsername()), "wrap");
		panelCentar.add(new JLabel("       Stručna sprema:"), "split 2,sg a");
		panelCentar.add(new JLabel(this.mTehničar.getSprema().getOpis()), "wrap");
		
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
	}
	
	private JLabel kreirajLabeluSaSlikom(String imagePath, int width, int height) {
		JLabel labelImage = new JLabel();	
		ImageIcon icon = ResourceLoader.getImageIcon(imagePath);
		ImageIcon iconResized = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		labelImage.setIcon(iconResized);
		return labelImage;
	}
	
	private void initAkcije() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
				MainFrame login = new MainFrame();
				login.setVisible(true);
			}
		});
		
		
		btnOdjava.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				MainFrame login = new MainFrame();
				login.setVisible(true);
			}
		});
		
		btnPacijenti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacijentTableFrame ptf = new PacijentTableFrame(MedicinskiTehničarFrame.this, true);
				ptf.setVisible(true);
			}
		});
		
		btnKreirajZahtev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ZatraziZahtevTableFrame atf = new ZatraziZahtevTableFrame(MedicinskiTehničarFrame.this, true, mTehničar);
				atf.setVisible(true);
			}
		});
		
		btnPrikazSvihZahteva.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZahteviFrame mtf = new ZahteviFrame(MedicinskiTehničarFrame.this, true, mTehničar);
				mtf.setVisible(true);
			}
		});
		
		btnUzmiKucnuPosetu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (zahteviServis.getZahteviSaKucnomDostavom().size() == 0) {
					JOptionPane.showMessageDialog(null, "Trenutno nema zahteva sa kućnom posetom koji nisu"
							+ " preuzeti", "Inforamtion", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					ObradaKucnihPosetaDialog m = new ObradaKucnihPosetaDialog(MedicinskiTehničarFrame.this, true, mTehničar, true);
					m.setVisible(true);
				}
			}
		});
		
		btnMojeKucnePosete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (zahteviServis.getZahteviTrenutnihPosetaMediciskogTehnicara(mTehničar).size() == 0) {
					JOptionPane.showMessageDialog(null, "Trenutno nema zahteva sa kućnom posetom"
							   + " koje ste preuzeli", "Inforamtion", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					ObradaKucnihPosetaDialog m = new ObradaKucnihPosetaDialog(MedicinskiTehničarFrame.this, true, mTehničar, false);
					m.setVisible(true);
				}
			}
		});
	
		
		btnStaviUObradu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OznaciZahtevZaObradu m = new OznaciZahtevZaObradu(MedicinskiTehničarFrame.this, true, mTehničar);
				m.setVisible(true);
			}
		});
		
		btnPregledNalaza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Nalaz>nalazi = nalaziServis.getSviZavrseniNalazi();
				if (nalazi.size() == 0) {
					JOptionPane.showMessageDialog(null, "Za sada nema odradjenih nalaza.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					PregledIstampanjeGotovihNalazaDialog p = new PregledIstampanjeGotovihNalazaDialog(MedicinskiTehničarFrame.this, true, mTehničar);
					p.setVisible(true);
				}
			}
		});
		
	}
		

	
}
