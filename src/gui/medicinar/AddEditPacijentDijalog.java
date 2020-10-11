package gui.medicinar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.w3c.dom.css.ElementCSSInlineStyle;

import entity.korisnici.Pacijent;
import gui.PacijentTableFrame;
import net.miginfocom.swing.MigLayout;
import services.DatumiServis;
import services.PacijentServis;
import services.ValidatorServis;
import utils.DatumLabelaFormater;

public class AddEditPacijentDijalog extends JDialog{

	private static final long serialVersionUID = -1683983620386553716L;
	
	private Pacijent pacijent;
	private PacijentServis pacijentServis = new PacijentServis();
	private ValidatorServis validatorServis = new ValidatorServis();
	private DatumiServis datumServis = new DatumiServis();
	
	
	
	private JDialog roditelj;
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
	
	public AddEditPacijentDijalog(PacijentTableFrame pacijentTableFrame, boolean modal, Pacijent p) {
		super(pacijentTableFrame, modal);
		this.roditelj = pacijentTableFrame;
		this.pacijent = p;
		
		if(pacijent == null) {
			setTitle("Dodavanje pacijenta bez registracije");
		}
		else {setTitle("Promena podataka");}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		initGUI();
		this.pack();
		setLocationRelativeTo(null);
		akcije();
	}

	private void initGUI() {
		
		podesiDatePickerRodjena();
		MigLayout mgLayout = new MigLayout();
		this.setLayout(mgLayout);
		
		group.add(rbMuski);
		group.add(rbZenski);
		
		tfAdresa.setPreferredSize(new Dimension(30,20));
		JScrollPane scrollPane = new JScrollPane(tfAdresa);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		add(new JLabel("Pacijent id: "), "split, sg a");
		add(tfId, "pushx, growx, wrap");
		add(new JLabel("Ime: "), "split 2, sg a");
		add(tfIme, "pushx, growx, wrap");
		add(new JLabel("Prezime: "), "split 2, sg a");
		add(tfPrezime, "pushx, growx, wrap");
		add(new JLabel("Pol: "), "split 3, sg a");
		add(rbMuski);
		add(rbZenski, "wrap");
		add(new JLabel("LBO: "), "split 2, sg a");
		add(tfLBO, "pushx, growx, wrap");
		add(new JLabel("Telefon: "), "split 2, sg a");
		add(tfBrojTel, "pushx, growx, wrap");
		add(new JLabel("<html>Datum rođenja:"), "split 2, sg a");
		add(datePickerRodjene, "pushx, growx, wrap");
		add(new JLabel(" "), "span, wrap");
		add(new JLabel("Adresa: "), "split 2, sg a");
		add(scrollPane, "pushx, growx, wrap");
		add(new JLabel(" "), "span, wrap");
		add(new JLabel(" "), "span, wrap");
		add(new JLabel(" "), "split 3, sg a");
		add(btnSacuvaj);
		add(btnOdustani);
		
		if (this.pacijent != null) {
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
			podesiInicijalniDatumRodjenja();
			tfAdresa.setText(pacijent.getAdresa());
			tfId.setText(String.valueOf(pacijent.getId()));
			tfId.setEditable(false);
		}
		else {
			tfId.setVisible(false);
		}
		
	}
	
	private void podesiInicijalniDatumRodjenja() {
		int godina = pacijent.getDatumRodjena().getYear();
		int mesec = pacijent.getDatumRodjena().getMonthValue() - 1;
		int dan = pacijent.getDatumRodjena().getDayOfMonth();
		model.setDate(godina, mesec, dan);
		model.setSelected(true);
	}
	
	private void podesiDatePickerRodjena() {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePickerRodjene = new JDatePickerImpl(datePanel, new DatumLabelaFormater());
	}
	
	
	private void akcije() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				AddEditPacijentDijalog.this.dispose();
				AddEditPacijentDijalog.this.setVisible(false);
				roditelj.setVisible(true);
				super.windowClosing(e);
			}
		});
		

		btnSacuvaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ime = tfIme.getText().trim();
				String prezime = tfPrezime.getText().trim();
				String pol = odrediPol();
				String LBO = tfLBO.getText().trim();
				String brojTel = tfBrojTel.getText().trim();
				String adresa = tfAdresa.getText().trim();
				LocalDate datum = getSelectedDate(datePickerRodjene);
				
				if (unosValidan(ime, prezime, pol, LBO, brojTel, adresa, datum)) {
					int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate date podatke?", "Pitanje", JOptionPane.YES_NO_CANCEL_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						if (AddEditPacijentDijalog.this.pacijent != null) {
							pacijentServis.edit(pacijent, ime, prezime, pol, LBO, brojTel, adresa, datum);
						}
						else {
							pacijentServis.add(ime, prezime, pol, LBO, brojTel, adresa, datum);
						}
						((PacijentTableFrame) roditelj).osveziTabelu();
						AddEditPacijentDijalog.this.dispose();
					}
					
				}
				
			}

			private boolean unosValidan(String ime, String prezime, String pol, String LBO, String brojTel,
					String adresa, LocalDate datum) {
				
				if (ime.equals("") | prezime.equals("") | pol == null | LBO.equals("") | brojTel.equals("") | adresa.equals("")) {
					JOptionPane.showMessageDialog(null, "Molim vas da unesite sve potrebne podatke."
							                     ,"Greska", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				else if (ime.length() > 15 | prezime.length() > 20) {
					JOptionPane.showMessageDialog(null, "Ime ne može imati vise od 15 slova, a prezime više od 20 slova.");
					return false;
				}
				else if ( ! pacijentServis.LBOjeJedinstven(LBO, AddEditPacijentDijalog.this.pacijent)) {
					JOptionPane.showMessageDialog(null, "Već postoji korisnik sa unetim LBO brojem.\n"
							+ "Molimo vas proverite ponovo unos.","Greska LBO", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				else if( ! validatorServis.LBOjeIspravan(LBO)) {
					JOptionPane.showMessageDialog(null, "LBO mora da sadrži 11 cifara.", "Greska", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				else if( ! validatorServis.brojTelefonaJeIspravan(brojTel)) {
					JOptionPane.showMessageDialog(null, "Broj telefona mora da sadrži 8 do 13 cifara.", "Greska", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				else if ( datum == null) {
					JOptionPane.showMessageDialog(null, "Izaberite datum rodjenja pacijenta");
					return false;
				}
				else if (!datumServis.datumJeProslost(datum)) {
					JOptionPane.showMessageDialog(null, "Ne možete izabrati budući datum za datum rodjenja.", "Greška", JOptionPane.WARNING_MESSAGE);
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

		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddEditPacijentDijalog.this.dispose();
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
