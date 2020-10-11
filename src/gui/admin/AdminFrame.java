package gui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entity.Nalaz;
import entity.korisnici.Admin;
import gui.PregledIstampanjeGotovihNalazaDialog;
import gui.ZahteviFrame;
import gui.admin.addEdit.EditCenePodesavanjaDijalog;
import gui.admin.izvestaji.PacijentiIzvestaj;
import gui.admin.izvestaji.PrihodiRashodiDijalog;
import gui.admin.izvestaji.ZahteviIzvestaj;
import gui.login.MainFrame;
import gui.medicinar.MedicinskiTehničarFrame;
import net.miginfocom.swing.MigLayout;
import res.ResourceLoader;
import services.NalaziServis;
import services.ZahteviServis;

public class AdminFrame extends JFrame {
	private static final long serialVersionUID = 7124917562823111509L;
	
	private NalaziServis nalaziServis = new NalaziServis();
	private ZahteviServis zahteviServis = new ZahteviServis();
	
	protected Admin admin;
	protected JButton btnOdjava = new JButton("Odjava");
	protected JButton btnLaboranti = new JButton("Laboranti");
	protected JButton btnMedicinari = new JButton("Medicinari");
	protected JButton btnZahteviIzvestaj = new JButton("Zahtevi izvestaj");
	protected JButton btnPacijentiIzvestaj = new JButton("Pacijenti izvestaj");
	protected JButton btnPregledNalaza = new JButton("Gotovi Nalazi");
	protected JButton btnCenePodesavanja = new JButton("Promena koeficijenata/cena poseta");
	protected JButton btnPrikaziPrihodeRashode = new JButton("Prihodi/Rashodi");
	protected JButton btnPregledNezavrsenihNalaza = new JButton("Nalazi u obradi");
	protected JButton btnPosebneAnalize = new JButton("Posebne analize");
	protected JButton btnGrupeAnaliza = new JButton("Grupe analiza");
	protected JButton btnZahteviNaCekanju = new JButton("Zahtevi na čekanju");
	
	public AdminFrame(Admin admin) {
		this.admin = admin;
		this.setIconImage(ResourceLoader.getImageIcon("main.png").getImage());
		setTitle("Admin: " + admin.getIme() + " " + admin.getPrezime());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(900,650));
		initGUI();
		pack();
		setLocationRelativeTo(null);
		initAkcije();
	}	

	private void initGUI() {
		setButtons();
		podesiLeviDeo();
		podesiCentar();
	}
	
	private void setButtons() {
		Dimension d = new Dimension(260,30);
		btnLaboranti.setPreferredSize(d);
		btnMedicinari.setPreferredSize(d);
		btnPosebneAnalize.setPreferredSize(d);
		btnGrupeAnaliza.setPreferredSize(d);
		btnCenePodesavanja.setPreferredSize(d);
		btnOdjava.setPreferredSize(d);
		
		btnZahteviIzvestaj.setPreferredSize(d);
		btnPacijentiIzvestaj.setPreferredSize(d);
		btnPrikaziPrihodeRashode.setPreferredSize(d);
		btnPregledNalaza.setPreferredSize(d);
		btnPregledNezavrsenihNalaza.setPreferredSize(d);
		btnZahteviNaCekanju.setPreferredSize(d);
	}
	private void podesiCentar() {
		JPanel panelCentar = new JPanel(new MigLayout());
		panelCentar.setBackground(Color.white);
		
		JLabel userIconLabel = kreirajLabeluSaSlikom("profile.png", 130, 130);
		
		panelCentar.add(userIconLabel, "span, center, wrap");
		panelCentar.add(new JLabel(" "), "span, wrap");
		panelCentar.add(new JLabel("                 Ime:"), "split 2, sg a");
		panelCentar.add(new JLabel(this.admin.getIme()), "wrap");
		panelCentar.add(new JLabel("                 Prezime:"), "split 2, sg a");
		panelCentar.add(new JLabel(this.admin.getPrezime()), "wrap");
		panelCentar.add(new JLabel("                 Korisničko ime:"), "split 2, sg a");
		panelCentar.add(new JLabel(this.admin.getUsername()), "wrap");
		panelCentar.add(new JLabel("                                                                      "
				+ "                                                    "), "span, wrap");
		
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
		
	}

	private JLabel kreirajLabeluSaSlikom(String imagePath, int width, int height) {
		JLabel labelImage = new JLabel();	
		ImageIcon icon = ResourceLoader.getImageIcon(imagePath);
		ImageIcon iconResized = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		labelImage.setIcon(iconResized);
		return labelImage;
	}
	
	private void podesiLeviDeo() {
		JPanel panelZapad = new JPanel(new MigLayout());
		panelZapad.setBackground(new Color(193, 229, 255));
		
		JLabel laborantiImage = kreirajLabeluSaSlikom("laborant.png",45,45);
		JLabel medicinariImage = kreirajLabeluSaSlikom("medicinar.png", 45, 45);
		JLabel analizeImage = kreirajLabeluSaSlikom("analiza.png", 45, 45);
		JLabel grupeAnalizaImage = kreirajLabeluSaSlikom("grupa.png", 45, 45);
		JLabel podesavanjeKoeficijenataImage = kreirajLabeluSaSlikom("podesavanja.png", 45, 45);
		
		JLabel zahteviIzvestajImage = kreirajLabeluSaSlikom("information.png",45,45);
		JLabel pacijentiIzvestajImage = kreirajLabeluSaSlikom("pacijenti.png", 45, 45);
		JLabel prihodiRashodiImage = kreirajLabeluSaSlikom("coins.png", 45, 45);
		JLabel zavrseniNalaziImage = kreirajLabeluSaSlikom("obradaRezultata.png", 45, 45);
		JLabel nalaziUObradiImage = kreirajLabeluSaSlikom("uObradi.png", 45, 45);
		JLabel zahteviNaCekanjuImage = kreirajLabeluSaSlikom("question.png", 45, 45);
		
		JLabel odjavaImage = kreirajLabeluSaSlikom("logout.png", 45, 45);
		
		
		panelZapad.add(laborantiImage, "split 2, sg a");
		panelZapad.add(btnLaboranti, "wrap");
		panelZapad.add(medicinariImage, "split 2, sg a");
		panelZapad.add(btnMedicinari, "wrap");
		panelZapad.add(analizeImage, "split 2, sg a");
		panelZapad.add(btnPosebneAnalize, "wrap");
		panelZapad.add(grupeAnalizaImage, "split 2, sg a");
		panelZapad.add(btnGrupeAnaliza, "wrap");
		panelZapad.add(podesavanjeKoeficijenataImage, "split 2, sg a");
		panelZapad.add(btnCenePodesavanja, "wrap");
		
		panelZapad.add(zahteviIzvestajImage, "split 2, sg a");
		panelZapad.add(btnZahteviIzvestaj, "wrap");
		panelZapad.add(pacijentiIzvestajImage, "split 2, sg a");
		panelZapad.add(btnPacijentiIzvestaj, "wrap");
		panelZapad.add(prihodiRashodiImage, "split 2, sg a");
		panelZapad.add(btnPrikaziPrihodeRashode, "wrap");
		panelZapad.add(zavrseniNalaziImage, "split 2, sg a");
		panelZapad.add(btnPregledNalaza, "wrap");
		panelZapad.add(nalaziUObradiImage, "split 2, sg a");
		panelZapad.add(btnPregledNezavrsenihNalaza, "wrap");
		panelZapad.add(zahteviNaCekanjuImage, "split 2, sg a");
		panelZapad.add(btnZahteviNaCekanju,"wrap" );
		panelZapad.add(odjavaImage, "sg a, split 2");
		panelZapad.add(btnOdjava, "wrap");
		
		this.getContentPane().add(panelZapad, BorderLayout.WEST);
		
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
		
		btnLaboranti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ZaposleniDijalog z = new ZaposleniDijalog(AdminFrame.this, true, true);
				z.setVisible(true);
			}
		});
		
		btnMedicinari.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ZaposleniDijalog z = new ZaposleniDijalog(AdminFrame.this, true, false);
				z.setVisible(true);
			}
		});
		
		btnZahteviIzvestaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZahteviIzvestaj z = new ZahteviIzvestaj(AdminFrame.this, true);
				z.setVisible(true);
			}
		});
		
		btnPacijentiIzvestaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacijentiIzvestaj p = new PacijentiIzvestaj(AdminFrame.this, true);
				p.setVisible(true);
			}
		});
		
		btnPregledNalaza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PregledIstampanjeGotovihNalazaDialog pregled = new PregledIstampanjeGotovihNalazaDialog(AdminFrame.this, true, admin);
				pregled.setVisible(true);
			}
		});
		
		btnCenePodesavanja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditCenePodesavanjaDijalog d = new EditCenePodesavanjaDijalog(AdminFrame.this, true);
				d.setVisible(true);
			}
		});
		
		btnPrikaziPrihodeRashode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrihodiRashodiDijalog p = new PrihodiRashodiDijalog(AdminFrame.this, true);
				p.setVisible(true);
			}
		});
		
		btnPregledNezavrsenihNalaza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Nalaz>nalazi = nalaziServis.getSviNeobradjeniNalazi();
				if (nalazi.size() == 0) {
					JOptionPane.showMessageDialog(null, "Trenutno su svi nalazi obradjeni");
				}else {
					PregledNezavrsenihNalaza p = new PregledNezavrsenihNalaza(AdminFrame.this, true, nalazi);
					p.setVisible(true);
				}
			}
		});
		
		btnPosebneAnalize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AnalizeIGrupeDijalog a = new AnalizeIGrupeDijalog(AdminFrame.this, true, true);
				a.setVisible(true);
			}
		});
		
		btnGrupeAnaliza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AnalizeIGrupeDijalog a = new AnalizeIGrupeDijalog(AdminFrame.this, true, false);
				a.setVisible(true);			
			}
		});
		
		
		btnZahteviNaCekanju.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (zahteviServis.getSviZahteviNaCekanju().size() == 0) {
					JOptionPane.showMessageDialog(null, "Trenutno nema zahteva na čekanju", "Informacija", JOptionPane.INFORMATION_MESSAGE);
				}else {
					ZahteviFrame mtf = new ZahteviFrame(AdminFrame.this, true, admin);
					mtf.setVisible(true);
				}
			}
		});
	}

}
