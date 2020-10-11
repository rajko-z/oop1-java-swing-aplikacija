package gui.admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import entity.Plata;
import entity.korisnici.Korisnik;
import gui.admin.addEdit.AddEditPlataDijalog;
import gui.models.PlateTableModel;
import res.ResourceLoader;
import services.PlataServis;

public class PlateDijalog extends JDialog {

	private static final long serialVersionUID = 794633195174724887L;
	private Korisnik korisnik;
	private PlataServis plataServis = new PlataServis();
	
	protected JButton btnUkloni = new JButton("Ukloni stavku");
	protected JButton btnDodaj = new JButton("Dodaj platu");
	protected JButton btnPromeni = new JButton("Promeni iznos");
	private JToolBar mainToolBar = new JToolBar();
	private JTable tabelaPlate = new JTable();
	
	public PlateDijalog(JDialog roditelj, boolean modal, Korisnik korisnik) {
		super(roditelj, modal);
		this.korisnik = korisnik;
		setTitle("Prikaz plata");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(1000,500));
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
		initAkcije();
	}
	
	private void initGUI() {
		
		podesiToolBar();
		podesiTabelu();
		
	}
	
	private void podesiToolBar() {
		ImageIcon dodajPlatuIcon = ResourceLoader.getImageIcon("dodaj.png");
		ImageIcon dodajPlatuIconResized = new ImageIcon(dodajPlatuIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnDodaj.setIcon(dodajPlatuIconResized);
		btnDodaj.setToolTipText("Dodaj platu");
		mainToolBar.add(btnDodaj);
		
		ImageIcon obrisiIcon = ResourceLoader.getImageIcon("quit.png");
		ImageIcon obrisiIconResized = new ImageIcon(obrisiIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		btnUkloni.setIcon(obrisiIconResized);
		btnUkloni.setToolTipText("Ukloni stavku");
		mainToolBar.add(btnUkloni);
		
		ImageIcon promeniIznosIcon = ResourceLoader.getImageIcon("editUser.png");
		ImageIcon promeniIznosIconResized = new ImageIcon(promeniIznosIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnPromeni.setIcon(promeniIznosIconResized);
		btnPromeni.setToolTipText("Promeni iznos");
		mainToolBar.add(btnPromeni);
		
		mainToolBar.setFloatable(false);
		add(mainToolBar, BorderLayout.NORTH);
		
	}
	
	private void podesiTabelu() {
		setTableData();
		JPanel panel = new JPanel(new GridLayout(1,1));
		tabelaPlate.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaPlate.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaPlate.getTableHeader().setReorderingAllowed(false);
		tabelaPlate.setAutoCreateRowSorter(true);
		JScrollPane srcPan = new JScrollPane(tabelaPlate);
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	private void setTableData() {
		if (plataServis.getPlateZaKorisnika(korisnik).size() == 0) {
			tabelaPlate = new JTable();
		}else {
			tabelaPlate = new JTable(new PlateTableModel(plataServis.getPlateZaKorisnika(korisnik)));
		}
	}
	
	
	private void initAkcije() {
		btnUkloni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaPlate.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else{
					int id = Integer.parseInt(tabelaPlate.getValueAt(selectedRow, 0).toString());
					Plata plata = plataServis.getPlataById(id);
					int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete ovu stavku iz tabele,\n"
							+ "ona više neće biti uračunata u izveštaje o prihodima i rashodima ?", "Pitanje", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						plataServis.delete(plata);
						JOptionPane.showMessageDialog(null, "Stavka je uspesno obrisana");
						osveziTabelu();
					}
				}
			}
		});
		
		btnPromeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaPlate.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else{
					int id = Integer.parseInt(tabelaPlate.getValueAt(selectedRow, 0).toString());
					Plata plata = plataServis.getPlataById(id);
					AddEditPlataDijalog p = new AddEditPlataDijalog(PlateDijalog.this, true, plata, korisnik);
					p.setVisible(true);
				}
			}
		});
		
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddEditPlataDijalog p = new AddEditPlataDijalog(PlateDijalog.this, true, null, korisnik);
				p.setVisible(true);
			}
		});
		
		
		
	}
	
	public void osveziTabelu() {
		PlateTableModel model = new PlateTableModel(plataServis.getPlateZaKorisnika(korisnik));
		tabelaPlate.setModel(model);
	}
	
	
}
