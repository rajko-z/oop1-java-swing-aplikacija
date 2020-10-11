package gui.pacijent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entity.Nalaz;
import entity.korisnici.Pacijent;
import gui.ZatraziZahtevTableFrame;
import gui.PregledIstampanjeGotovihNalazaDialog;
import gui.login.MainFrame;
import net.miginfocom.swing.MigLayout;
import res.ResourceLoader;
import services.DatumiServis;
import services.NalaziServis;
import services.PacijentServis;
import services.ValidatorServis;
import services.ZahteviServis;
import utils.DatumLabelaFormater;

public class PacijentFrame extends JFrame{

	private static final long serialVersionUID = -7498339235692203338L;
	
	protected Pacijent pacijent;
	private NalaziServis nalaziServis = new NalaziServis();
	private ZahteviServis zahteviServis = new ZahteviServis();
	private PacijentServis pacijentServis = new PacijentServis();
	private DatumiServis datumServis = new DatumiServis();
	private ValidatorServis validatorServis = new ValidatorServis();
	
	// podesavanje zapada
	protected JButton btnZatraziZahtev = new JButton("Zatraži zahtev");
	protected JButton btnTrenutniZahtevi = new JButton("Moj trenutni zahtev");
	protected JButton btnIstorijaNalaza = new JButton("Istorija nalaza");
	protected JButton btnOdjava = new JButton("Odjava");
	
	// podesavanje centra
	private JTextField tfId = new JTextField(35);
	private JTextField tfIme = new JTextField(35);
	private JTextField tfPrezime = new JTextField(35);
	private JTextField tfLBO = new JTextField(35);
	private JTextField tfBrojTel = new JTextField(35);
	private JTextField tfAdresa = new JTextField(35);
	private JRadioButton rbMuski = new JRadioButton("M");
	private JRadioButton rbZenski = new JRadioButton("Ž");
	private ButtonGroup group = new ButtonGroup();
	private JDatePickerImpl datePickerRodjene;
	private UtilDateModel model = new UtilDateModel();
	private JButton btnSacuvaj = new JButton("Sačuvaj");
	private JButton btnOdustani = new JButton("Odustani");
	
	
	
	
	public PacijentFrame(Pacijent pacijent) {
		this.pacijent = pacijent;
		this.setIconImage(ResourceLoader.getImageIcon("main.png").getImage());
		setTitle("Pacijent: " + pacijent.getIme() + " " + pacijent.getPrezime());
		setPreferredSize(new Dimension(1050,650));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		initGUI();
		pack();
		setLocationRelativeTo(null);
		initAkcije();
	
	}
	
	private void initGUI() {
		
		setButtons();
		initZapad();
		initCentar();
		
	}
	
	private void initZapad() {
		JPanel panelZapad = new JPanel(new MigLayout());
		panelZapad.setBackground(new Color(193, 229, 255));
		
		JLabel zatraziZahtevImage = kreirajLabeluSaSlikom("plus.png",45,45);
		JLabel mojiTrenutniZahteviImage = kreirajLabeluSaSlikom("question.png", 45, 45);
		JLabel istorijaNalazaImage = kreirajLabeluSaSlikom("clock.png", 50, 50);
		JLabel odjavaImage = kreirajLabeluSaSlikom("logout.png", 45, 45);
		
		panelZapad.add(zatraziZahtevImage, "split 2, sg a");
		panelZapad.add(btnZatraziZahtev, "wrap");
		panelZapad.add(mojiTrenutniZahteviImage, "split 2, sg a");
		panelZapad.add(btnTrenutniZahtevi, "wrap");
		panelZapad.add(istorijaNalazaImage, "split 2, sg a");
		panelZapad.add(btnIstorijaNalaza, "wrap");
		panelZapad.add(odjavaImage, "split 2, sg a");
		panelZapad.add(btnOdjava, "wrap");
		
		this.getContentPane().add(panelZapad, BorderLayout.WEST);
		
	}
	
	private void setButtons() {
		Dimension d = new Dimension(250,30);
		btnZatraziZahtev.setPreferredSize(d);
		btnTrenutniZahtevi.setPreferredSize(d);
		btnIstorijaNalaza.setPreferredSize(d);
		btnOdjava.setPreferredSize(d);
	}
	
	private void initCentar() {
		JPanel panelCentar = new JPanel(new MigLayout());
		panelCentar.setBackground(Color.white);
		group.add(rbMuski);
		group.add(rbZenski);
		
		tfAdresa.setPreferredSize(new Dimension(30,20));
		JScrollPane scrollPane = new JScrollPane(tfAdresa);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JLabel userIconLabel = new JLabel();
		
		ImageIcon userIcon = ResourceLoader.getImageIcon("profile.png");
		ImageIcon resizedUserIcon = new ImageIcon(userIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		userIconLabel.setIcon(resizedUserIcon);
		
		podesiDatePickerRodjena();
		
		panelCentar.add(userIconLabel, "span, center, wrap");
		panelCentar.add(new JLabel("Pacijent id: "), "split, sg a");
		panelCentar.add(tfId, "pushx, growx, wrap");
		panelCentar.add(new JLabel("Ime: "), "split 2, sg a");
		panelCentar.add(tfIme, "pushx, growx, wrap");
		panelCentar.add(new JLabel("Prezime: "), "split 2, sg a");
		panelCentar.add(tfPrezime, "pushx, growx, wrap");
		panelCentar.add(new JLabel("Pol: "), "split 3, sg a");
		panelCentar.add(rbMuski);
		panelCentar.add(rbZenski, "wrap");
		panelCentar.add(new JLabel("LBO: "), "split 2, sg a");
		panelCentar.add(tfLBO, "pushx, growx, wrap");
		panelCentar.add(new JLabel("Telefon: "), "split 2, sg a");
		panelCentar.add(tfBrojTel, "pushx, growx, wrap");
		panelCentar.add(new JLabel("<html>Datum rođenja:"), "split 2, sg a");
		panelCentar.add(datePickerRodjene, "pushx, growx, wrap");
		panelCentar.add(new JLabel(" "), "span, wrap");
		panelCentar.add(new JLabel("Adresa: "), "split 2, sg a");
		panelCentar.add(scrollPane, "pushx, growx, wrap");
		panelCentar.add(new JLabel(" "), "span, wrap");
		panelCentar.add(new JLabel(" "), "span, wrap");
		panelCentar.add(new JLabel(" "), "split 3, sg a");
		panelCentar.add(btnSacuvaj);
		panelCentar.add(btnOdustani);
		
		popuniPoljaInicijalnimPodacima();
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
	}
	
	private void popuniPoljaInicijalnimPodacima() {
		podesiInicijalniDatumRodjenja();
		tfIme.setText(pacijent.getIme());
		tfPrezime.setText(pacijent.getPrezime());
		if (pacijent.getPol().equals("muški")) {
			rbMuski.setSelected(true);
		}
		else {
			rbZenski.setSelected(true);
		}
		tfLBO.setText(pacijent.getLBO());
		tfBrojTel.setText(pacijent.getTelefon());
		tfAdresa.setText(pacijent.getAdresa());
		tfId.setText(String.valueOf(pacijent.getId()));
		tfId.setEditable(false);
		tfLBO.setEditable(false);
	}
	
	private void podesiDatePickerRodjena() {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePickerRodjene = new JDatePickerImpl(datePanel, new DatumLabelaFormater());
	}
	
	
	private void podesiInicijalniDatumRodjenja() {
		int godina = pacijent.getDatumRodjena().getYear();
		int mesec = pacijent.getDatumRodjena().getMonthValue() - 1;
		int dan = pacijent.getDatumRodjena().getDayOfMonth();
		model.setDate(godina, mesec, dan);
		model.setSelected(true);
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

		
		
		btnZatraziZahtev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (zahteviServis.getZahtevPacijentaKojiNijeGotov(pacijent) != null) {
					JOptionPane.showMessageDialog(null, "Vaš zahtev nije obradjen.\n"
							+ "Kada bude gotov i naplaćen dobićete mogućnost da podnosite novi.", "Napomena", JOptionPane.WARNING_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Pre nego što podnesete zahtev želeli bismo da\n"
							+ " vas upoznamo sa pravilima poslovanja naše firme.\n"
							+ "Kada jednom podnesete zahtev, dalje podnošenje zahteva će vam\n"
							+ "biti onemogućeno sve dok zahtev ne bude odradjen i naplaćen.\n"
							+ "Stanje zahteva možete da pratite tako da ćete znati kad je gotov.\n\n"
							+ "Hvala na razumevanju.", "Information", JOptionPane.INFORMATION_MESSAGE);
					ZatraziZahtevTableFrame analizaTableFrame = new ZatraziZahtevTableFrame(PacijentFrame.this, true, pacijent);
					analizaTableFrame.setVisible(true);
				}
			}
		});
		
		btnTrenutniZahtevi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (zahteviServis.getZahtevPacijentaKojiNijeGotov(pacijent) == null) {
					JOptionPane.showMessageDialog(null, "Trenutno nema neobradjenih zahteva.\n"
							+ "Sve obradjene zahteve mozete pogledati u istoriji nalaza.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					PacijentStanjeZahtevaDijalog p = new PacijentStanjeZahtevaDijalog(PacijentFrame.this, true, pacijent);
					p.setVisible(true);
				}
			}
		});
		
		btnIstorijaNalaza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Nalaz>nalazi = nalaziServis.getNalaziZaPacijenta(pacijent);
				if (nalazi.size() == 0) {
					JOptionPane.showMessageDialog(null, "Nemate za sada odradjenih nalaza.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					PregledIstampanjeGotovihNalazaDialog p = new PregledIstampanjeGotovihNalazaDialog(PacijentFrame.this, true, pacijent);
					p.setVisible(true);
				}
			}
		});
		
		
		
		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popuniPoljaInicijalnimPodacima();
			}
		});
		
		btnSacuvaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ime = tfIme.getText().trim();
				String prezime = tfPrezime.getText().trim();
				String pol = odrediPol();
				String brojTel = tfBrojTel.getText().trim();
				String adresa = tfAdresa.getText().trim();
				LocalDate datum = getSelectedDate(datePickerRodjene);
				
				if (unosValidan(ime, prezime, pol, brojTel, adresa, datum)) {
					int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate date podatke?", "Pitanje", JOptionPane.YES_NO_CANCEL_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						pacijentServis.edit(pacijent, ime, prezime, pol, pacijent.getLBO(), brojTel, adresa, datum);
						JOptionPane.showMessageDialog(null, "Informacije uspešno ažurirane.", "Inforamcija", JOptionPane.INFORMATION_MESSAGE);
						popuniPoljaInicijalnimPodacima();
					}
					
				}
				
			}

			private boolean unosValidan(String ime, String prezime, String pol, String brojTel,
					String adresa, LocalDate datum) {
				if (ime.equals("") | prezime.equals("") | pol == null  | brojTel.equals("") | adresa.equals("")) {
					JOptionPane.showMessageDialog(null, "Molim vas da unesite sve potrebne podatke."
							                     ,"Greska", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				else if (ime.length() > 15 | prezime.length() > 20) {
					JOptionPane.showMessageDialog(null, "Ime ne može imati vise od 15 slova, a prezime više od 20 slova.");
					return false;
				}
				else if ( datum == null) {
					JOptionPane.showMessageDialog(null, "Izaberite datum rodjenja");
					return false;
				}
				else if (!datumServis.datumJeProslost(datum)) {
					JOptionPane.showMessageDialog(null, "Ne možete izabrati budući datum za datum rodjenja.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				else if(!validatorServis.brojTelefonaJeIspravan(brojTel)) {
					JOptionPane.showMessageDialog(null, "Broj telefona mora da sadrži 8 do 13 cifara.", "Greska", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				return true;
			}
			private String odrediPol() {
				String retVal;
				if (rbMuski.isSelected()) retVal = "muški"; 
				else retVal = "ženski";
				return retVal;
			}
		});
		
	}
	
	
	
	private LocalDate getSelectedDate(JDatePickerImpl datePicker) {
		 Date datum = (Date) datePicker.getModel().getValue();
		 if (datum != null) {
			 return datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 }
		 return null;
	}
}
