package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entity.CenePodesavanja;
import entity.Dostava;
import entity.PosebnaAnaliza;
import entity.Zahtev;
import entity.korisnici.Korisnik;
import entity.korisnici.MedicinskiTehničar;
import entity.korisnici.Pacijent;
import gui.models.AnalizaTableModel;
import gui.models.AnalizeSaPopustomTableModel;
import net.miginfocom.swing.MigLayout;
import res.ResourceLoader;
import services.AnalizeServis;
import services.DatumiServis;
import services.DatumiVazenjaPopustaServis;
import services.DostavaServis;
import services.PacijentServis;
import services.ZahteviServis;
import utils.DatumLabelaFormater;

public class ZatraziZahtevTableFrame extends JDialog {

	private static final long serialVersionUID = 5398398430409078733L;
	private AnalizeServis analizeServis = new AnalizeServis();
	private PacijentServis pacijentServis = new PacijentServis();
	private ZahteviServis zahteviServis = new ZahteviServis();
	private DatumiServis datumiServis = new DatumiServis();
	private DostavaServis dostavaServis = new DostavaServis();
	private DatumiVazenjaPopustaServis datumiVazenjaPopustaServis = new DatumiVazenjaPopustaServis();
	private Korisnik korisnik;

	private JTable tabelaAnalize = new JTable();
	private JTable tabelaPovisenihAnaliza = new JTable();
	
	
	
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected JList listGrupe = new JList(analizeServis.getValidneGrupeAnalizaNazivi().toArray());
	@SuppressWarnings("rawtypes")
	protected DefaultListModel izabraneAnalizeListModel = new DefaultListModel();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected JList listIzabraneAnalize = new JList(izabraneAnalizeListModel);

	protected JTextField tfLBO = new JTextField(30);
	protected JTextField tfSearch = new JTextField(30);
	protected JTextField tfUkupnaCena = new JTextField(10);
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnDodajAnalizu = new JButton("Dodaj analizu u zahtev");
	protected JButton btnPotrvdi = new JButton("Potvrdite zahtev");
	protected JButton btnOdustani = new JButton("Odustani");
	protected JButton btnPrikazPopustaGrupa = new JButton();

	// dodatno za pacijenta
	private double cenaDostaveDatum = CenePodesavanja.getInstance().getDostavaDatum();
	private double cenaDostaveVreme = CenePodesavanja.getInstance().getDostavaVreme();
	protected JRadioButton radioButtonDa = new JRadioButton("DA", false);
	protected JRadioButton radioButtonNe = new JRadioButton("NE", false);
	protected ButtonGroup groupKuncaDostava = new ButtonGroup();

	protected JRadioButton radioBtnSaVremenomDostava = new JRadioButton("DA", false);
	protected JRadioButton radioBtnBezVremenaDostava = new JRadioButton("NE", false);
	protected ButtonGroup groupKuncaDostavaSaVremenom = new ButtonGroup();

	protected JLabel labelaIzaberiteDatum = new JLabel();
	protected JLabel labelaIzaberiteVreme = new JLabel(
			"<html>Izaberite tačno vreme kada" + "<br>želite da dođemo,   (OPCIONO POLJE)"
					+ "<br>naplaćuje se dodatno sa " + String.valueOf(cenaDostaveVreme) + ".");
	protected UtilDateModel model;
	protected JDatePanelImpl datePanel;
	protected JDatePickerImpl datePicker;
	protected JComboBox<LocalTime> combobox = new JComboBox<LocalTime>();

	public ZatraziZahtevTableFrame(JFrame roditelj, boolean modal, Korisnik korisnik) {
		super(roditelj, modal);
		this.korisnik = korisnik;
		setTitle("Pravljenje zahteva");
		if (korisnik instanceof MedicinskiTehničar) {
			setPreferredSize(new Dimension(1750, 650));
		}else {
			setPreferredSize(new Dimension(1750, 800));
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		this.pack();
		setLocationRelativeTo(null);
		actions();
	}

	private void initGUI() {
		podesavnjeToolBaraIIcona();
		podesavnjeTabeleCentar();
		podesavanjeZapada();
		podesavanjeJuga();
	}

	private void podesavnjeToolBaraIIcona() {
		ImageIcon dodajAnalizu = ResourceLoader.getImageIcon("plus.png");
		ImageIcon resizedDodajAnalizu = new ImageIcon(
				dodajAnalizu.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		btnDodajAnalizu.setIcon(resizedDodajAnalizu);
		btnDodajAnalizu.setToolTipText("Dodaj analizu u zahtev");

		ImageIcon pogledajPopust = ResourceLoader.getImageIcon("price.png");
		ImageIcon resizedPogledajPopust = new ImageIcon(
				pogledajPopust.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		btnPrikazPopustaGrupa.setIcon(resizedPogledajPopust);
		btnPrikazPopustaGrupa.setToolTipText("Pogledaj grupne popuste");

		mainToolbar.add(btnDodajAnalizu);
		mainToolbar.add(new JLabel("  Pretraži analizu:"));
		mainToolbar.add(tfSearch);

		mainToolbar.setFloatable(false);
		add(mainToolbar, BorderLayout.NORTH);
	}

	private void podesavnjeTabeleCentar() {
		setTableDataUZavisnostiOdPopusta();
		JPanel panelCentar = new JPanel(new MigLayout(" "));
		panelCentar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Selektujte analizu i kliknite na dodaj analizu u gornjem levom uglu", TitledBorder.CENTER,
				TitledBorder.TOP));
		tabelaAnalize.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaAnalize.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSorter.setModel((AbstractTableModel) tabelaAnalize.getModel());
		tabelaAnalize.getTableHeader().setReorderingAllowed(false);
		tabelaAnalize.setRowSorter(tableSorter);
		tabelaAnalize.getTableHeader().setOpaque(false);
		tabelaAnalize.getTableHeader().setBackground(new Color(193, 229, 255));
		JScrollPane srcPan = new JScrollPane(tabelaAnalize);
		srcPan.setPreferredSize(new Dimension(1000, 500));
		panelCentar.add(srcPan, "wrap");

		if (korisnik instanceof Pacijent) {
			podesavanjeTabelePovisenihVrednosti(panelCentar);
		}
		add(panelCentar, BorderLayout.CENTER);
	}

	private void podesavanjeTabelePovisenihVrednosti(JPanel panelCentar) {
		List<PosebnaAnaliza> poviseneAnalize = pacijentServis
				.getAnalizeSaPovisenomVrednostuUposlednjaDvaMerenja((Pacijent) korisnik);
		if (poviseneAnalize.size() != 0) {
			if (datumiVazenjaPopustaServis.danasSuAnalizeNaPopustu()) {
				tabelaPovisenihAnaliza.setModel(new AnalizeSaPopustomTableModel(poviseneAnalize));
			} else {
				tabelaPovisenihAnaliza.setModel(new AnalizaTableModel(poviseneAnalize));
			}
			JPanel panelPovisene = new JPanel();
			panelPovisene.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
					"Na ovim analizama ste imali prošla dva puta vrednosti van opsega, preporučuje se da ih odradite ponovo. Selektujte i kliklnite dodaj u gornjem levom uglu",
					TitledBorder.CENTER, TitledBorder.TOP));
			tabelaPovisenihAnaliza.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tabelaPovisenihAnaliza.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabelaPovisenihAnaliza.setModel((AbstractTableModel) tabelaPovisenihAnaliza.getModel());
			tabelaPovisenihAnaliza.getTableHeader().setReorderingAllowed(false);
			tabelaPovisenihAnaliza.getTableHeader().setOpaque(false);
			tabelaPovisenihAnaliza.getTableHeader().setBackground(new Color(193, 229, 255));
			JScrollPane srcPan = new JScrollPane(tabelaPovisenihAnaliza);
			srcPan.setPreferredSize(new Dimension(1000, 100));

			panelPovisene.add(srcPan);
			panelCentar.add(panelPovisene, "wrap");
		}
	}

	private void setTableDataUZavisnostiOdPopusta() {
		if (datumiVazenjaPopustaServis.danasSuAnalizeNaPopustu()) {
			tabelaAnalize.setModel(new AnalizeSaPopustomTableModel(analizeServis.getValidneAnalize()));
		} else {
			tabelaAnalize.setModel(new AnalizaTableModel(analizeServis.getValidneAnalize()));
		}
	}

	private void podesavanjeJuga() {
		JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelJug.add(btnPotrvdi);
		panelJug.add(btnOdustani);
		add(panelJug, BorderLayout.SOUTH);
	}

	private void podesavanjeZapada() {
		JPanel panelWest = new JPanel(new MigLayout());
		zajednickoPodesavanjaMigLayoutaZaMedicinaraIPacijenta(panelWest);
		if (korisnik instanceof Pacijent) {
			dodatnoPodesavanjeMigLayoutaZaPacijenta(panelWest);
		}
		add(panelWest, BorderLayout.WEST);
	}

	private void zajednickoPodesavanjaMigLayoutaZaMedicinaraIPacijenta(JPanel panelWest) {
		JScrollPane srcGrupe = new JScrollPane(listGrupe);
		JScrollPane srcIzabraneAnalize = new JScrollPane(listIzabraneAnalize);
		tfUkupnaCena.setEditable(false);

		proveriDaliSuGrupeNaPopustuIOndaDadajOpciju(panelWest, srcGrupe);

		panelWest.add(new JLabel("Unesite LBO:  (OBAVEZNO POLJE)"), "split 2, sg a");
		panelWest.add(tfLBO, "pushx, growx, wrap");
		panelWest.add(new JLabel("<html>Trenutne analize u zahtevu." + "<br>Ukoliko želite da poništite neku analizu,"
				+ "<br>ovde možete da kliknete na nju."), "split 2, sg a");
		panelWest.add(srcIzabraneAnalize, "wrap");
		panelWest.add(new JLabel("<html>Ukupna cena analiza sa svim<br>popustima u trenutnom zahtevu:"),
				"split 2, sg a");
		panelWest.add(tfUkupnaCena, "pushx, growx, wrap");
	}

	private void proveriDaliSuGrupeNaPopustuIOndaDadajOpciju(JPanel panelWest, JScrollPane srcGrupe) {
		if (datumiVazenjaPopustaServis.danasSuGrupeNaPopustu()) {
			panelWest.add(new JLabel("Grupe analiza: "), "split 4, sg a");
			panelWest.add(srcGrupe);
			JLabel tekst = new JLabel(
					"<html>Danas važe grupni<br>popusti za neke od<br>" + "grupa. Ukoliko odaberete 3 ili<br>"
							+ "više analiza iz<br>neke grupe, onda<br>ostvarujete dodatan popust<br>"
							+ "na izabrane analize. Ovaj<br>popust se gleda<br>nezavisno od pojedinačnih<br>"
							+ "popusta analiza");
			tekst.setForeground(Color.blue);
			panelWest.add(tekst);
			panelWest.add(btnPrikazPopustaGrupa, "wrap");
		} else {
			panelWest.add(new JLabel("Grupe analiza: "), "split 2, sg a");
			panelWest.add(srcGrupe, "wrap");
		}
	}

	private void dodatnoPodesavanjeMigLayoutaZaPacijenta(JPanel panelWest) {

		podesiKalendar();
		podesiVremenaComboBox();
		groupKuncaDostava.add(radioButtonDa);
		groupKuncaDostava.add(radioButtonNe);
		groupKuncaDostavaSaVremenom.add(radioBtnSaVremenomDostava);
		groupKuncaDostavaSaVremenom.add(radioBtnBezVremenaDostava);

		panelWest.add(new JLabel("<html>Da li želite kućnu dostavu?<br>" + "ova usluga se naplaćuje sa "
				+ String.valueOf(cenaDostaveDatum)), "sg a, split 3");
		panelWest.add(radioButtonDa);
		panelWest.add(radioButtonNe, "wrap");
		panelWest.add(labelaIzaberiteDatum, "sg a, split 2");
		panelWest.add(datePicker, "wrap");
		panelWest.add(labelaIzaberiteVreme, "sg a, split 4");
		panelWest.add(radioBtnBezVremenaDostava);
		panelWest.add(radioBtnSaVremenomDostava);
		panelWest.add(combobox, "wrap");

		labelaIzaberiteDatum.setVisible(false);
		datePicker.setVisible(false);

		labelaIzaberiteVreme.setVisible(false);
		radioBtnBezVremenaDostava.setVisible(false);
		radioBtnSaVremenomDostava.setVisible(false);
		combobox.setVisible(false);
	}

	private void podesiVremenaComboBox() {
		for (int i = 0; i < datumiServis.getVremena().toArray().length; i++)
			combobox.addItem((LocalTime) datumiServis.getVremena().toArray()[i]);
	}

	@SuppressWarnings("serial")
	private void podesiKalendar() {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DatumLabelaFormater());
	}

	private void actions() {
		addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ZatraziZahtevTableFrame.this.dispose();
				ZatraziZahtevTableFrame.this.setVisible(false);
				super.windowClosing(e);
			}
		});

		tabelaAnalize.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					JTable target = (JTable) me.getSource();
					target.clearSelection();
				}
			}
		});
		
		tabelaPovisenihAnaliza.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					JTable target = (JTable) me.getSource();
					target.clearSelection();
				}
			}
		});
		

		this.tfSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (tfSearch.getText().trim().length() == 0) {
					tableSorter.setRowFilter(null);
				} else {
					tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + tfSearch.getText().trim()));
				}
			}
		});

		this.listGrupe.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				tfSearch.setText(listGrupe.getSelectedValue().toString());
			}
		});

		radioButtonDa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				labelaIzaberiteDatum.setText("<html>Izaberite datum kada želite<br>" + "da dođemo po uzorak");
				labelaIzaberiteDatum.setVisible(true);
				datePicker.setVisible(true);

				labelaIzaberiteVreme.setVisible(true);
				radioBtnBezVremenaDostava.setVisible(true);
				radioBtnSaVremenomDostava.setVisible(true);
			}
		});

		radioButtonNe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				labelaIzaberiteDatum.setText("<html>Izaberite datum kada želite <br>" + "da donesete uzorak");
				labelaIzaberiteDatum.setVisible(true);
				datePicker.setVisible(true);

				labelaIzaberiteVreme.setVisible(false);
				radioBtnBezVremenaDostava.setVisible(false);
				radioBtnSaVremenomDostava.setVisible(false);
				combobox.setVisible(false);

			}
		});

		radioBtnBezVremenaDostava.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				combobox.setVisible(false);
			}
		});

		radioBtnSaVremenomDostava.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				combobox.setVisible(true);
			}
		});

		btnDodajAnalizu.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedRowAnalize = tabelaAnalize.getSelectedRow();
				int selectedRowPovisene = tabelaPovisenihAnaliza.getSelectedRow();
				if (selectedRowAnalize == -1 & selectedRowPovisene == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati analizu iz tabele.", "Greška",
							JOptionPane.WARNING_MESSAGE);
				} else if (selectedRowAnalize != -1 & selectedRowPovisene != -1) {
					JOptionPane.showMessageDialog(null,
							"Ne možete selektovati alalizu iz obe tabele u isto vreme.\nSelektujte analizu iz jedne od tabela.\n"
							+ "Analizu možete deselektovati duplim klikom na nju.",
							"Greška", JOptionPane.WARNING_MESSAGE);
				} else {
					int id;
					if (selectedRowAnalize != -1) {
						id = Integer.parseInt(tabelaAnalize.getValueAt(selectedRowAnalize, 0).toString());
					} else {
						id = Integer.parseInt(tabelaPovisenihAnaliza.getValueAt(selectedRowPovisene, 0).toString());
					}
					PosebnaAnaliza pa = analizeServis.getPosebnaAnalizaById(id);
					if (izabraneAnalizeListModel.contains(pa)) {
						JOptionPane.showMessageDialog(null, "Ovu analizu ste već dodali u vaš zahtev.", "Informacija",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						izabraneAnalizeListModel.addElement(pa);
						tfUkupnaCena.setText(String.valueOf(izracunajCenu()));
					}
					tabelaAnalize.clearSelection();
					tabelaPovisenihAnaliza.clearSelection();
				}
			}
		});

		this.listIzabraneAnalize.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (listIzabraneAnalize.getSelectedIndex() >= 0) {
					int answer = JOptionPane.showConfirmDialog(null,
							"Da li ste sigurni da želite da uklonite ovu analizu iz zahteva?", "Pitanje",
							JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						izabraneAnalizeListModel.removeElement(listIzabraneAnalize.getSelectedValue());
						tfUkupnaCena.setText(String.valueOf(izracunajCenu()));
					}
				}
			}

		});

		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(null,
						"Da li ste sigurni da želite da prekinete sa pravljenjem zahteva?", "Pitanje",
						JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					ZatraziZahtevTableFrame.this.dispose();
					ZatraziZahtevTableFrame.this.setVisible(false);
				}

			}
		});

		btnPrikazPopustaGrupa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrikazGrupaPopusta prikaz = new PrikazGrupaPopusta(ZatraziZahtevTableFrame.this, false);
				prikaz.setVisible(true);
			}
		});

		btnPotrvdi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (proveriValidnostPodataka(korisnik)) {
					int answer = JOptionPane.showConfirmDialog(null, generisiPorukuOZahtevu(korisnik), "Pitanje",
							JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						ArrayList<PosebnaAnaliza> analize = getIzabraneAnalize();

						if (korisnik instanceof MedicinskiTehničar) {
							kreirajZahtevUSlucajuDaGaPraviMedicinskiTehnicar(analize);
						} else if (korisnik instanceof Pacijent) {
							kreirajZahtevUSlucajuDaGaPraviPacijent(analize);
						}

						JOptionPane.showMessageDialog(null, "Zahtev je uspešno kreiran", "Informacija",
								JOptionPane.INFORMATION_MESSAGE);
						ZatraziZahtevTableFrame.this.dispose();
						ZatraziZahtevTableFrame.this.setVisible(false);
					}
				}
			}

			private void kreirajZahtevUSlucajuDaGaPraviMedicinskiTehnicar(ArrayList<PosebnaAnaliza> analize) {
				MedicinskiTehničar m = (MedicinskiTehničar) korisnik;
				Pacijent p = pacijentServis.getPacijentSaZadatimLBObrojem(tfLBO.getText().trim());
				@SuppressWarnings("unused")
				Zahtev zahtev = zahteviServis.kreirajZahtevOdMedicinskogTeh(m, p, analize, izracunajCenu());
			}

			private void kreirajZahtevUSlucajuDaGaPraviPacijent(ArrayList<PosebnaAnaliza> analize) {
				Pacijent pacijent = (Pacijent) korisnik;
				Dostava dostava = null;
				if (radioButtonNe.isSelected()) {
					dostava = dostavaServis.kreirajDostavuBezKucnePosete(getSelectedDate());
				} else if (radioBtnBezVremenaDostava.isSelected()) {
					dostava = dostavaServis.kreirajKucnuDostavuBezVremena(getSelectedDate());
				} else {
					dostava = dostavaServis.kreirajKucnuDostavuSaVremenom(getSelectedDate(),
							(LocalTime) combobox.getSelectedItem());
				}
				zahteviServis.kreirajZahtevOdStranePacijenta(analize, pacijent, dostava , getUkupnuCenuZaPacijenta());
			}

			private String generisiPorukuOZahtevu(Korisnik k) {
				String retVal = "Da li ste sigurni da želite da potvrdite sledeći zahtev:\n\n";
				double cena = 0;
				for (int i = 0; i < izabraneAnalizeListModel.getSize(); i++) {
					retVal += (PosebnaAnaliza) izabraneAnalizeListModel.getElementAt(i) + "\n";
				}
				if (korisnik instanceof Pacijent) {
					cena = getUkupnuCenuZaPacijenta();
					retVal += "Sa ukupnom cenom analiza i dostave: " + String.valueOf(cena);
					return retVal;
				}
				cena = izracunajCenu();
				retVal += "Sa ukupnom cenom od: " + String.valueOf(izracunajCenu());
				return retVal;
			}

			private double getUkupnuCenuZaPacijenta() {
				double cena = izracunajCenu();
				if (radioButtonNe.isSelected()) {
					return cena;
				} else {
					if (radioBtnBezVremenaDostava.isSelected()) {
						return cena + cenaDostaveDatum;
					}
					return cena + cenaDostaveDatum + cenaDostaveVreme;
				}
			}

			private boolean proveriValidnostPodataka(Korisnik k) {
				String LBO = tfLBO.getText().trim();
				if (LBO.equals("")) {
					JOptionPane.showMessageDialog(null, "Molim vas unesite LBO broj", "LBO unos",
							JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if (getIzabraneAnalize().size() == 0) {
					JOptionPane.showMessageDialog(null, "Niste dodali nijednu analizu", "Greska",
							JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if (k instanceof MedicinskiTehničar) {
					return validnostPodatakaUSlucajuDaZahtevPraviMedicinar();
				}
				return validnostPodatakaUSlucajuDaPacijentPraviZahtev();
			}

			private boolean validnostPodatakaUSlucajuDaZahtevPraviMedicinar() {
				String LBO = tfLBO.getText().trim();
				Pacijent pacijent = pacijentServis.getPacijentSaZadatimLBObrojem(LBO);
				if (pacijent == null) {
					JOptionPane.showMessageDialog(null, "Nije moguće pronaći pacijenta sa zadatim LBO brojem",
							"LBO greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if (zahteviServis.getZahtevPacijentaKojiNijeGotov(pacijent) != null) {
					JOptionPane.showMessageDialog(null,
							"Već je napravljen zahtev za ovog pacijenta koji još nije obradjen", "LBO greška",
							JOptionPane.WARNING_MESSAGE);
					return false;
				}
				return true;
			}

			private boolean validnostPodatakaUSlucajuDaPacijentPraviZahtev() {
				String LBO = tfLBO.getText().trim();
				Pacijent pacijent = (Pacijent) korisnik;
				if (!pacijent.getLBO().equals(LBO)) {
					JOptionPane.showMessageDialog(null, "Neispravan LBO broj, proverite ponovo unos.",
							"Nepoklapanje LBO broja", JOptionPane.WARNING_MESSAGE);
					return false;
				} else if (!radioButtonDa.isSelected() & !radioButtonNe.isSelected()) {
					JOptionPane.showMessageDialog(null, "Molim vas izaberite da li " + "želite kućnu posetu.",
							"Kućna poseta nije određena", JOptionPane.WARNING_MESSAGE);
					return false;
				} else if (getSelectedDate() == null) {
					JOptionPane.showMessageDialog(null, "Niste selektovali datum.", "Greška",
							JOptionPane.WARNING_MESSAGE);
					return false;
				} else if (datumiServis.datumJeProslost(getSelectedDate())) {
					JOptionPane.showMessageDialog(null, "Izaberite neki od budućih datuma.", "Greška",
							JOptionPane.WARNING_MESSAGE);
					return false;
				} else if (radioButtonDa.isSelected() & !radioBtnBezVremenaDostava.isSelected()
						& !radioBtnSaVremenomDostava.isSelected()) {
					JOptionPane.showMessageDialog(null, "Izaberite da li želite tačno vreme dostave.", "Greška",
							JOptionPane.WARNING_MESSAGE);
					return false;
				}
				return true;
			}

			private LocalDate getSelectedDate() {
				Date datum = (Date) datePicker.getModel().getValue();
				if (datum != null) {
					return datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				}
				return null;
			}
		});

	}

	private double izracunajCenu() {
		double cena = 0;
		List<PosebnaAnaliza> listaAnaliza = new ArrayList<PosebnaAnaliza>();
		for (int i = 0; i < izabraneAnalizeListModel.getSize(); i++) {
			PosebnaAnaliza pa = (PosebnaAnaliza) izabraneAnalizeListModel.getElementAt(i);
			listaAnaliza.add(pa);
			if (datumiVazenjaPopustaServis.danasSuAnalizeNaPopustu()) {
				cena += analizeServis.getCenaSaPopustom(pa);
			}else {
				cena += pa.getCena(); 
			}
		}
		return cena - popustOstvarenNagrupama(listaAnaliza);
	}

	private double popustOstvarenNagrupama(List<PosebnaAnaliza> analize) {
		if (datumiVazenjaPopustaServis.danasSuGrupeNaPopustu()) {
			return analizeServis.analizeGetIznosPopustaOstvarenNadGrupama(analize);
		}
		return 0;
	}

	private ArrayList<PosebnaAnaliza> getIzabraneAnalize() {
		ArrayList<PosebnaAnaliza> analize = new ArrayList<PosebnaAnaliza>();
		for (int i = 0; i < izabraneAnalizeListModel.getSize(); i++) {
			PosebnaAnaliza pa = (PosebnaAnaliza) izabraneAnalizeListModel.getElementAt(i);
			analize.add(pa);
		}
		return analize;
	}

}
