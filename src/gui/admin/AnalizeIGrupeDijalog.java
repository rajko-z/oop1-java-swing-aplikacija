package gui.admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import entity.GrupaAnaliza;
import entity.PosebnaAnaliza;
import gui.admin.addEdit.AddEditGrupaAnalize;
import gui.admin.addEdit.AddEditPosebnaAnaliza;
import gui.admin.addEdit.VratiObrisaneGrupeIAnalize;
import gui.models.AnalizaTableModel;
import gui.models.GrupeAnalizaTableModel;
import res.ResourceLoader;
import services.AnalizeServis;

public class AnalizeIGrupeDijalog extends JDialog {

	private static final long serialVersionUID = -3247988393308680679L;
	private AnalizeServis analizeServis = new AnalizeServis();
	protected boolean jestePrikazAnaliza = false;

	protected JButton btnUkloni = new JButton();
	protected JButton btnDodaj = new JButton();
	protected JButton btnPromeni = new JButton();
	protected JButton btnVratiNazad = new JButton();
	protected JButton btnPopusti = new JButton();
	
	protected JToolBar mainToolBar = new JToolBar();
	protected JTable tabelaStavke = new JTable();
	protected JTextField tfSearch = new JTextField(20);

	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected JList listGrupe = new JList(analizeServis.getValidneGrupeAnalizaNazivi().toArray());

	public AnalizeIGrupeDijalog(JFrame roditelj, boolean modal, boolean jestePrikazAnaliza) {
		super(roditelj, modal);
		this.jestePrikazAnaliza = jestePrikazAnaliza;
		if (jestePrikazAnaliza) {
			setTitle("Pojedinačne analize");
		} else {
			setTitle("Grupe analiza");
		}
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(1000, 500));
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
		initAkcije();
	}

	private void initGUI() {
		podesiToolBar();
		podesiTabelu();
		if (jestePrikazAnaliza) {
			podesiJug();
		}
	}

	private void podesiJug() {
		JScrollPane src = new JScrollPane(listGrupe);
		src.setPreferredSize(new Dimension(200, 60));
		JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelJug.add(new JLabel("Pretraga: "));
		panelJug.add(tfSearch);
		panelJug.add(new JLabel("Grupe analiza:"));
		panelJug.add(src);

		this.getContentPane().add(panelJug, BorderLayout.SOUTH);
	}

	private void podesiToolBar() {
		ImageIcon dodajStavkuIcon = ResourceLoader.getImageIcon("dodaj.png");
		ImageIcon dodajStavkuIconResized = new ImageIcon(
				dodajStavkuIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnDodaj.setIcon(dodajStavkuIconResized);
		btnDodaj.setToolTipText("Dodaj novu stavku");
		mainToolBar.add(btnDodaj);

		ImageIcon vratiNazadIcon = ResourceLoader.getImageIcon("revert.png");
		ImageIcon vratiNazadIconResized = new ImageIcon(
				vratiNazadIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnVratiNazad.setIcon(vratiNazadIconResized);
		btnVratiNazad.setToolTipText("Vrati nazad obrisanu stavku");
		mainToolBar.add(btnVratiNazad);
		
		ImageIcon obrisiIcon = ResourceLoader.getImageIcon("quit.png");
		ImageIcon obrisiIconResized = new ImageIcon(
				obrisiIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		btnUkloni.setIcon(obrisiIconResized);
		btnUkloni.setToolTipText("Ukloni stavku");
		mainToolBar.add(btnUkloni);

		ImageIcon promeniIznosIcon = ResourceLoader.getImageIcon("editUser.png");
		ImageIcon promeniIznosIconResized = new ImageIcon(
				promeniIznosIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnPromeni.setIcon(promeniIznosIconResized);
		btnPromeni.setToolTipText("Promeni stavku");
		mainToolBar.add(btnPromeni);
		
		ImageIcon popustiIcon = ResourceLoader.getImageIcon("price.png");
		ImageIcon popustiIconResized = new ImageIcon(
				popustiIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnPopusti.setIcon(popustiIconResized);
		btnPopusti.setToolTipText("Odredi popuste");
		mainToolBar.add(btnPopusti);

		mainToolBar.setFloatable(false);
		add(mainToolBar, BorderLayout.NORTH);
	}

	private void podesiTabelu() {
		setTableData();
		JPanel panel = new JPanel(new GridLayout(1, 1));
		tabelaStavke.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaStavke.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaStavke.getTableHeader().setReorderingAllowed(false);
		tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
		tabelaStavke.setRowSorter(tableSorter);
		JScrollPane srcPan = new JScrollPane(tabelaStavke);
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}

	private void setTableData() {
		if (jestePrikazAnaliza) {
			tabelaStavke.setModel(new AnalizaTableModel(analizeServis.getValidneAnalize()));
		} else {
			tabelaStavke.setModel(new GrupeAnalizaTableModel(analizeServis.getValidneGrupeAnaliza()));
		}
	}

	private void initAkcije() {
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

		btnUkloni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaStavke.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int answer = JOptionPane.showConfirmDialog(null, generisiPoruku(), "Pitanje",
							JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						int id = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());
						if (jestePrikazAnaliza) {
							PosebnaAnaliza analiza = analizeServis.getPosebnaAnalizaById(id);
							analizeServis.obrisiAnalizu(analiza);
						} else {
							GrupaAnaliza grupaAnaliza = analizeServis.getGrupaAnalizeById(id);
							analizeServis.obrisiGrupuAnalize(grupaAnaliza);
						}
						JOptionPane.showMessageDialog(null, "Stavka je uspešno obrisana");
						osveziTabelu();
					}
				}
			}

			private String generisiPoruku() {
				if (jestePrikazAnaliza) {
					return new String("Da li ste sigurni da želite da obrišete ovu analizu?\n" +
							  "Ukoliko su postojali zahtevi sa ovom analizom, biće obradjeni,\nali u svim budućim zahtevima" +
							  " ova analiza neće biti dostupna.");
				}else {
					return new String("Da li ste sigurni da želite da obrišete ovu grupu analiza?\n" +
							  "Brisanjem ove grupe brišu se i sve njene analize." +
							  "Ova grupa ostaje dostupna u sistemu pri pravljenu izveštaja i prihoda\n"
							  + "ali će biti nedostupna pacijentima prilikom pravljenja nalaza.");
				}
			}
		});

		btnPromeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaStavke.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int id = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());
					
					if (jestePrikazAnaliza) {
						PosebnaAnaliza analiza = analizeServis.getPosebnaAnalizaById(id);
						AddEditPosebnaAnaliza a = new AddEditPosebnaAnaliza(AnalizeIGrupeDijalog.this, true, analiza);
						a.setVisible(true);
					}else {
						GrupaAnaliza grupa = analizeServis.getGrupaAnalizeById(id);
						AddEditGrupaAnalize a  = new AddEditGrupaAnalize(AnalizeIGrupeDijalog.this, true, grupa);
						a.setVisible(true);
					}
				}
			}
		});

		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jestePrikazAnaliza) {
					AddEditPosebnaAnaliza a = new AddEditPosebnaAnaliza(AnalizeIGrupeDijalog.this, true,null);
					a.setVisible(true);
				}else {
					AddEditGrupaAnalize a  = new AddEditGrupaAnalize(AnalizeIGrupeDijalog.this, true, null);
					a.setVisible(true);
				}
			}
		});
		
		
		btnVratiNazad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jestePrikazAnaliza) {
					if (analizeServis.getObrisaneAnalize().size() == 0) {
						JOptionPane.showMessageDialog(null, "Trenutno nema obrisanih analiza");
					}else {
						VratiObrisaneGrupeIAnalize v = new VratiObrisaneGrupeIAnalize(AnalizeIGrupeDijalog.this, true, true);
						v.setVisible(true);
					}
				}else {
					if (analizeServis.getObrisaneGrupeAnaliza().size() == 0) {
						JOptionPane.showMessageDialog(null, "Trenutno nema obrisanih grupa analiza");
					}else {
						VratiObrisaneGrupeIAnalize v = new VratiObrisaneGrupeIAnalize(AnalizeIGrupeDijalog.this, true, false);
						v.setVisible(true);
					}
				}
			}
		});
		
		btnPopusti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (jestePrikazAnaliza) {
					PopustiDijalog popusti = new PopustiDijalog(AnalizeIGrupeDijalog.this, true, true);
					popusti.setVisible(true);
				}else {
					PopustiDijalog popusti = new PopustiDijalog(AnalizeIGrupeDijalog.this, true, false);
					popusti.setVisible(true);
				}
				
			}
		});
		
		

	}

	public void osveziTabelu() {
		if (jestePrikazAnaliza) {
			AnalizaTableModel model = new AnalizaTableModel(analizeServis.getValidneAnalize());
			tabelaStavke.setModel(model);
			tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
		}else {
			GrupeAnalizaTableModel model = new GrupeAnalizaTableModel(analizeServis.getValidneGrupeAnaliza());
			tabelaStavke.setModel(model);
			tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
		}
	}
}
