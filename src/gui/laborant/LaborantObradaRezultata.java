package gui.laborant;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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

import entity.PosebnaAnaliza;
import gui.grafici.PrikazGrafikaPacijenti;
import gui.models.AnalizaTableModel;
import net.miginfocom.swing.MigLayout;
import repositories.RepositoryFactory;
import res.ResourceLoader;
import services.AnalizeServis;
import services.DatumiServis;
import utils.DTOselektovaniDatumiIOpsegGodina;
import utils.DatumLabelaFormater;

public class LaborantObradaRezultata extends JDialog {

	private static final long serialVersionUID = -2752831450426015927L;

	private AnalizeServis analizeServis = new AnalizeServis();
	private DatumiServis datumiServis = new DatumiServis();

	protected JDatePickerImpl datePickerDatumPocetak;
	protected JDatePickerImpl datePickerDatumKraj;

	private JTable tabelaAnalize = new JTable(
			new AnalizaTableModel(RepositoryFactory.getInstance().getPosebnaAnalizaRepo().getEntitetiList()));
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	protected JList listGrupe = new JList(analizeServis.getValidneGrupeAnalizaNazivi().toArray());
	protected JList listGodinePocetak;
	protected JList listGodineKraj;
	protected JTextField tfSearch = new JTextField(30);
	protected JButton btnPogledajRezultat = new JButton("Pogledaj rezultate"); 

	public LaborantObradaRezultata(JFrame roditelj, boolean modal) {
		super(roditelj, modal);
		this.setTitle("Obrada odnosa broja pacijenata prema analizama");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(1200,600));
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
		initAkcija();
	}

	private void initGUI() {
		podesiTabeluAnaliza();
		podesiZapad();
	}

	private void podesiTabeluAnaliza() {
		JPanel panelCentar = new JPanel(new GridLayout(1, 1));
		panelCentar.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Selektujte analizu",TitledBorder.CENTER,TitledBorder.TOP));
		tabelaAnalize.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaAnalize.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSorter.setModel((AbstractTableModel) tabelaAnalize.getModel());
		tabelaAnalize.getTableHeader().setReorderingAllowed(false);
		tabelaAnalize.setRowSorter(tableSorter);
		JScrollPane srcPan = new JScrollPane(tabelaAnalize);
		panelCentar.add(srcPan);
		add(panelCentar, BorderLayout.CENTER);
	}

	private void podesiZapad() {
		podesiKalendar();
		podesiDugmeRezultati();
		JPanel panelWest = new JPanel(new MigLayout());
		JScrollPane srcGrupe = new JScrollPane(listGrupe);
		add(panelWest, BorderLayout.WEST);
		panelWest.add(new JLabel("Pretraga:"), "split 2, sg a");
		panelWest.add(tfSearch, "pushx, growx, wrap");
		panelWest.add(new JLabel("Grupe analiza: "), "split 2, sg a");
		panelWest.add(srcGrupe, "wrap");
		panelWest.add(getPanelVreme(), "wrap");
		panelWest.add(getPanelGodine(), "wrap");
		panelWest.add(btnPogledajRezultat, "wrap");
		this.getContentPane().add(panelWest, BorderLayout.WEST);
	}
	
	private void podesiDugmeRezultati() {
		ImageIcon grafikIcon = ResourceLoader.getImageIcon("grafikObican.png");
		ImageIcon resizedGrafikIcon = new ImageIcon(grafikIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		btnPogledajRezultat.setIcon(resizedGrafikIcon);
		btnPogledajRezultat.setToolTipText("Pogledaj grafik rezultata");
	}
	
	private JPanel getPanelGodine() {
		popuniListeZaGodine();
		JScrollPane srcPocetak = new JScrollPane(listGodinePocetak);
		srcPocetak.setPreferredSize(new Dimension(40,80));
		JScrollPane srcKraj = new JScrollPane(listGodineKraj);
		srcKraj.setPreferredSize(new Dimension(40,80));
		
		JPanel panelGodine = new JPanel(new MigLayout());
		panelGodine.add(new JLabel("Izaberite početni opseg godina: "), "split 4, sg a");
		panelGodine.add(srcPocetak);
		panelGodine.add(new JLabel("Izaberite krajnji opseg godina: "));
		panelGodine.add(srcKraj, "wrap");
		return panelGodine;
	}
	
	private void popuniListeZaGodine() {
		Integer[] godine = new Integer[102];
		for (int i = 1; i <= 100; i ++) {
			godine[i] = i;
		}
		listGodinePocetak = new JList(godine);
		listGodineKraj = new JList(godine);
	}
	
	private JPanel getPanelVreme() {
		JPanel datumiPanel = new JPanel(new MigLayout());
		JLabel datumiNapomena = new JLabel("<html>Napomena: brojanje pacijenata se vrši na svake dve nedelje, tako da ukoliko<br>"
				+ "želite da rezultati grafika budu kompletniji, izaberite veće opsege datuma.");
		datumiNapomena.setForeground(Color.blue);
		datumiPanel.add(datumiNapomena, "span, wrap");
		datumiPanel.add(new JLabel("Izaberite početni datum: "), "split 2, sg a");
		datumiPanel.add(datePickerDatumPocetak,"wrap");
		datumiPanel.add(new JLabel("Izaberite krajnji datum: "), "split 2, sg a");
		datumiPanel.add(datePickerDatumKraj,"wrap");
		return datumiPanel;
	}
	

	private void podesiKalendar() {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePickerDatumPocetak = new JDatePickerImpl(datePanel, new DatumLabelaFormater());

		Properties p2 = new Properties();
		p2.put("text.today", "Today");
		p2.put("text.month", "Month");
		p2.put("text.year", "Year");
		UtilDateModel model2 = new UtilDateModel();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
		datePickerDatumKraj = new JDatePickerImpl(datePanel2, new DatumLabelaFormater());
	}

	private void initAkcija() {
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
		
		
		btnPogledajRezultat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (proveriUnos()) {
					int selectedRow = tabelaAnalize.getSelectedRow();
					if (selectedRow == -1) {
						JOptionPane.showMessageDialog(null, "Morate selektovati analizu iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
					}
					else {
						int id = Integer.parseInt(tabelaAnalize.getValueAt(selectedRow, 0).toString());
						PosebnaAnaliza analiza = analizeServis.getPosebnaAnalizaById(id);
						LocalDate pocetak = getSelectedDate(datePickerDatumPocetak);
						LocalDate kraj = getSelectedDate(datePickerDatumKraj);
						DTOselektovaniDatumiIOpsegGodina dto = new DTOselektovaniDatumiIOpsegGodina(pocetak, kraj, getGodinePocetak(), getGodineKraj(), analiza);
						PrikazGrafikaPacijenti prikaz = new PrikazGrafikaPacijenti(LaborantObradaRezultata.this, false, dto);
						prikaz.setVisible(true);
					}	
				}
			}

			private boolean proveriUnos() {
				if (getSelectedDate(datePickerDatumPocetak) == null | getSelectedDate(datePickerDatumKraj) == null) {
					JOptionPane.showMessageDialog(null, "Niste selektovali datume.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				LocalDate pocetak = getSelectedDate(datePickerDatumPocetak);
				LocalDate kraj = getSelectedDate(datePickerDatumKraj);
				
				if (!datumiServis.datumJeProslost(pocetak) | !datumiServis.datumJeProslost(kraj)) {
					JOptionPane.showMessageDialog(null, "Za početni i kranji datum morate izabrati neki od prošlih datuma.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if(pocetak.compareTo(kraj) >= 0) {
					JOptionPane.showMessageDialog(null, "Početni datum mora biti starije od krajnjeg.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if (listGodinePocetak.getSelectedIndex() == -1 | listGodineKraj.getSelectedIndex() == -1 ) {
					JOptionPane.showMessageDialog(null, "Morate selektovani početni i kranji opseg godina za pretragu.", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if (getGodinePocetak() > getGodineKraj()) {
					JOptionPane.showMessageDialog(null, "Početni opseg godina mora biti manji od krajnjeg", "Greška", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				return true;
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
	private int getGodinePocetak() {
		return (int)listGodinePocetak.getSelectedValue();
	}
	private int getGodineKraj() {
		return (int)listGodineKraj.getSelectedValue();
	}
	
}
