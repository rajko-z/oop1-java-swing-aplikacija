package gui.admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entity.DatumiPopustaAnaliza;
import entity.DatumiPopustaGrupa;
import entity.GrupaAnaliza;
import entity.PosebnaAnaliza;
import gui.admin.addEdit.EditPopustDijalog;
import gui.models.AnalizaTableModel;
import gui.models.AnalizeSaPopustomTableModel;
import gui.models.GrupeAnalizaTableModel;
import gui.models.GrupeSaPopustomTableModel;
import res.ResourceLoader;
import services.AnalizeServis;
import services.DatumiServis;
import services.DatumiVazenjaPopustaServis;
import utils.DatumLabelaFormater;

public class PopustiDijalog extends JDialog {

	private static final long serialVersionUID = -552049101895678148L;
	private boolean isAnalizeDijalog;
	private DatumiServis datumiServis = new DatumiServis();
	private AnalizeServis analizeServis = new AnalizeServis();
	private DatumiVazenjaPopustaServis datumiVazenjaPopustaServis = new DatumiVazenjaPopustaServis();
	private DatumiPopustaAnaliza datumiPopustaAnaliza = DatumiPopustaAnaliza.getInstance();
	private DatumiPopustaGrupa datumiPopustaGrupa = DatumiPopustaGrupa.getInstance();
	
	
	protected JDatePickerImpl datePickerDatumPocetak;
	private UtilDateModel modelPocetak = new UtilDateModel();
	protected JDatePickerImpl datePickerDatumKraj;
	private UtilDateModel modelKraj = new UtilDateModel();

	protected JTextField tfSearch = new JTextField(20);

	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected JList listGrupe = new JList(analizeServis.getValidneGrupeAnalizaNazivi().toArray());
	
	protected JRadioButton rbPon = new JRadioButton("PON");
	protected JRadioButton rbUto = new JRadioButton("UTO");
	protected JRadioButton rbSre = new JRadioButton("SRE");
	protected JRadioButton rbCet = new JRadioButton("ČET");
	protected JRadioButton rbPet = new JRadioButton("PET");
	protected JRadioButton rbSub = new JRadioButton("SUB");
	protected JRadioButton rbNed = new JRadioButton("NED");

	protected JTable tabelaPopusta = new JTable();
	protected JToolBar mainToolBar = new JToolBar();
	protected JScrollPane srcTabela = new JScrollPane(tabelaPopusta);
	protected JButton btnPopusti = new JButton("Odredi popust za stavku");
	protected JButton btnSacuvaj = new JButton("Sačuvaj promene");
	protected JScrollPane srcGrupeAnaliza = new JScrollPane(listGrupe);
	protected JLabel lblPretraga = new JLabel("Pretraga:");
	protected JLabel lblGrupeAnaliza = new JLabel("Grupe analiza:");
	

	public PopustiDijalog(JDialog roditelj, boolean modal, boolean isAnalizeDijalog) {
		super(roditelj, modal);
		this.setPreferredSize(new Dimension(1350, 600));
		this.isAnalizeDijalog = isAnalizeDijalog;
		if (isAnalizeDijalog) {
			setTitle("Popusti analiza");
		} else {
			setTitle("Popusti grupa");
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		this.pack();
		setLocationRelativeTo(null);
		initAkcije();
	}

	private void initGUI() {
		initToolBar();
		initTabela();
	}

	private void initTabela() {
		setTableData();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		tabelaPopusta.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaPopusta.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaPopusta.getTableHeader().setReorderingAllowed(false);
		tableSorter.setModel((AbstractTableModel) tabelaPopusta.getModel());
		tabelaPopusta.setRowSorter(tableSorter);
		panel.add(srcTabela);
		srcTabela.setVisible(false);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}

	private void setTableData() {
		if (isAnalizeDijalog) {
			tabelaPopusta.setModel(new AnalizeSaPopustomTableModel(analizeServis.getValidneAnalize()));
		}else {
			tabelaPopusta.setModel(new GrupeSaPopustomTableModel(analizeServis.getValidneGrupeAnaliza()));
		}
	}

	private void initToolBar() {
		podesiKalendar();
		podesiInicijalneDatumeVazenja();
		podesiInicijalneDaneVazenja();
		setIcons();
		mainToolBar.setFloatable(false);
		mainToolBar.setPreferredSize(new Dimension(1350, 120));
		
		mainToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		mainToolBar.add(new JLabel("<html>Početni datum<br>važenja"));
		mainToolBar.add(datePickerDatumPocetak);
		mainToolBar.add(new JLabel("<html>Kranji datum<br>važenja"));
		mainToolBar.add(datePickerDatumKraj);
		mainToolBar.add(new JLabel("Dani važenja:"));
		
		mainToolBar.add(rbPon);
		mainToolBar.add(rbUto);
		mainToolBar.add(rbSre);
		mainToolBar.add(rbCet);
		mainToolBar.add(rbPet);
		mainToolBar.add(rbSub);
		mainToolBar.add(rbNed);
		mainToolBar.add(btnSacuvaj);
		
		srcGrupeAnaliza.setPreferredSize(new Dimension(200, 60));
		mainToolBar.add(lblPretraga);
		mainToolBar.add(tfSearch);
		mainToolBar.add(lblGrupeAnaliza);
		mainToolBar.add(srcGrupeAnaliza);
		
		mainToolBar.add(new JLabel("   "));
		mainToolBar.add(btnPopusti);
		
		lblPretraga.setVisible(false);
		lblGrupeAnaliza.setVisible(false);
		tfSearch.setVisible(false);
		srcGrupeAnaliza.setVisible(false);
		btnPopusti.setVisible(false);
		
		mainToolBar.setFloatable(false);
		this.getContentPane().add(mainToolBar, BorderLayout.NORTH);
	}
	
	private void setIcons() {
		ImageIcon popustiIcon = ResourceLoader.getImageIcon("price.png");
		ImageIcon popustiIconResized = new ImageIcon(
				popustiIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnPopusti.setIcon(popustiIconResized);
		btnPopusti.setToolTipText("Odredi popuste");
		mainToolBar.add(btnPopusti);
		
		ImageIcon sacuvajIcon = ResourceLoader.getImageIcon("save.png");
		ImageIcon sacuvajIconResized = new ImageIcon(
				sacuvajIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnSacuvaj.setIcon(sacuvajIconResized);
		btnSacuvaj.setToolTipText("Sačuvaj promene");
		mainToolBar.add(btnSacuvaj);
	}
	
	private void initAkcije() {
		this.addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentResized(ComponentEvent e) {
		        setSize(new Dimension(1350, getHeight()));
		        super.componentResized(e);
		    }
		});
		
		this.tfSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {changedUpdate(e);}
			@Override
			public void insertUpdate(DocumentEvent e) {changedUpdate(e);}
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
		
		btnSacuvaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (proveriUnos()) {
					LocalDate pocetak = getSelectedDate(datePickerDatumPocetak);
					LocalDate kraj = getSelectedDate(datePickerDatumKraj);
					if (isAnalizeDijalog) {
						datumiVazenjaPopustaServis.editDatumiAnalize(pocetak, kraj, getSelektovaneDane());
					}else {
						datumiVazenjaPopustaServis.editDatumiGrupe(pocetak, kraj, getSelektovaneDane());
					}
					JOptionPane.showMessageDialog(null, "Podaci su uspešno sačuvani");
					lblPretraga.setVisible(true);
					lblGrupeAnaliza.setVisible(true);
					tfSearch.setVisible(true);
					srcGrupeAnaliza.setVisible(true);
					btnPopusti.setVisible(true);
					srcTabela.setVisible(true);
					
					btnSacuvaj.setEnabled(false);
					datePickerDatumKraj.setEnabled(false);
					datePickerDatumPocetak.setEnabled(false);
					
				}
			}
		});
		
		
		btnPopusti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = tabelaPopusta.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int id = Integer.parseInt(tabelaPopusta.getValueAt(selectedRow, 0).toString());
					if (isAnalizeDijalog) {
						PosebnaAnaliza analiza = analizeServis.getPosebnaAnalizaById(id);
						EditPopustDijalog e = new EditPopustDijalog(PopustiDijalog.this, true, analiza, null);
						e.setVisible(true);
					}else {
						GrupaAnaliza grupaAnaliza = analizeServis.getGrupaAnalizeById(id);
						EditPopustDijalog e = new EditPopustDijalog(PopustiDijalog.this, true, null, grupaAnaliza);
						e.setVisible(true);
					}
					
				}
			}
		});
		
		
		
		
	}
	
	private boolean proveriUnos() {
		if (getSelectedDate(datePickerDatumPocetak) == null | getSelectedDate(datePickerDatumKraj) == null) {
			JOptionPane.showMessageDialog(null, "Niste selektovali datume.", "Greška", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		LocalDate pocetak = getSelectedDate(datePickerDatumPocetak);
		LocalDate kraj = getSelectedDate(datePickerDatumKraj);
		
		if (datumiServis.datumJeProslost(kraj)) {
			JOptionPane.showMessageDialog(null, "Za krajni datum morate izabrati neki od budućih datuma.", "Greška", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(pocetak.compareTo(kraj) >= 0) {
			JOptionPane.showMessageDialog(null, "Početni datum mora biti starije od krajnjeg.", "Greška", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if ( ! (rbPon.isSelected() | rbUto.isSelected() | rbSre.isSelected() | rbCet.isSelected() | rbPet.isSelected() | rbSub.isSelected() | rbNed.isSelected())){
			JOptionPane.showMessageDialog(null, "Izaberite dane.", "Greška", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void podesiKalendar() {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		modelPocetak = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(modelPocetak, p);
		datePickerDatumPocetak = new JDatePickerImpl(datePanel, new DatumLabelaFormater());

		Properties p2 = new Properties();
		p2.put("text.today", "Today");
		p2.put("text.month", "Month");
		p2.put("text.year", "Year");
		modelKraj = new UtilDateModel();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(modelKraj, p);
		datePickerDatumKraj = new JDatePickerImpl(datePanel2, new DatumLabelaFormater());
	}

	private void podesiInicijalneDatumeVazenja() {
		LocalDate pocetak = null;
		LocalDate kraj = null;
		if (isAnalizeDijalog) {
			pocetak = datumiPopustaAnaliza.getPocetak();
			kraj = datumiPopustaAnaliza.getKraj();
		}else {
			pocetak = datumiPopustaGrupa.getPocetak();
			kraj = datumiPopustaGrupa.getKraj();
		}
		int godinaPocatak = pocetak.getYear();
		int mesecPocetak = pocetak.getMonthValue() - 1;
		int danPocetak = pocetak.getDayOfMonth();
		int godinaKraj = kraj.getYear();
		int mesecKraj = kraj.getMonthValue() - 1;
		int danKraj = kraj.getDayOfMonth();
		
		modelPocetak.setDate(godinaPocatak, mesecPocetak, danPocetak);
		modelPocetak.setSelected(true);
		modelKraj.setDate(godinaKraj, mesecKraj, danKraj);
		modelKraj.setSelected(true);
	}
	
	private void podesiInicijalneDaneVazenja() {
		List<DayOfWeek>dani = null;
		if (isAnalizeDijalog) {
			dani = datumiPopustaAnaliza.getDani();
		}else {
			dani = datumiPopustaGrupa.getDani();
		}
		for (DayOfWeek dan: dani) {
			switch (dan) {
			case MONDAY:
				rbPon.setSelected(true);
				break;
			case TUESDAY:
				rbUto.setSelected(true);
				break;
			case WEDNESDAY:
				rbSre.setSelected(true);
				break;
			case THURSDAY:
				rbCet.setSelected(true);
				break;
			case FRIDAY:
				rbPet.setSelected(true);
				break;
			case SATURDAY:
				rbSub.setSelected(true);
				break;
			case SUNDAY:
				rbNed.setSelected(true);
				break;
			default:
				break;
			}
		}
	}

	private LocalDate getSelectedDate(JDatePickerImpl datePicker) {
		Date datum = (Date) datePicker.getModel().getValue();
		if (datum != null) {
			return datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return null;
	}

	private List<DayOfWeek> getSelektovaneDane() {
		ArrayList<DayOfWeek> retList = new ArrayList<DayOfWeek>();
		if (rbPon.isSelected()) {
			retList.add(DayOfWeek.MONDAY);
		}
		if (rbUto.isSelected()) {
			retList.add(DayOfWeek.TUESDAY);
		}
		if (rbSre.isSelected()) {
			retList.add(DayOfWeek.WEDNESDAY);
		}
		if (rbCet.isSelected()) {
			retList.add(DayOfWeek.THURSDAY);
		}
		if (rbPet.isSelected()) {
			retList.add(DayOfWeek.FRIDAY);
		}
		if (rbSub.isSelected()) {
			retList.add(DayOfWeek.SATURDAY);
		}
		if (rbNed.isSelected()) {
			retList.add(DayOfWeek.SUNDAY);
		}
		return retList;
	}
	
	public void osveziTabelu() {
		if (isAnalizeDijalog) {
			tabelaPopusta.setModel(new AnalizeSaPopustomTableModel(analizeServis.getValidneAnalize()));
			tableSorter.setModel((AbstractTableModel) tabelaPopusta.getModel());
		}else {
			tabelaPopusta.setModel(new GrupeSaPopustomTableModel(analizeServis.getValidneGrupeAnaliza()));
			tableSorter.setModel((AbstractTableModel) tabelaPopusta.getModel());
		}
	}

}
