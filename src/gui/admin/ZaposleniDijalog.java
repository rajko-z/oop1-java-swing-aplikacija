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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import entity.korisnici.MedicinskiTehničar;
import gui.admin.addEdit.AddEditLaborant;
import gui.admin.addEdit.AddEditMedicinar;
import gui.models.KorisniciTableModel;
import res.ResourceLoader;
import services.KorisniciServis;
import services.LaborantServis;
import services.MedicinskiTehničarServis;

public class ZaposleniDijalog extends JDialog {

	private static final long serialVersionUID = -8174551416755291667L;
	private LaborantServis laborantServis = new LaborantServis();
	private MedicinskiTehničarServis mTehničarServis = new MedicinskiTehničarServis();
	private KorisniciServis korisniciServis = new KorisniciServis();
	
	protected JTable tabelaZaposleni = new JTable();
	protected KorisniciTableModel korisniciTableModel;
	protected boolean laborantiDijalog;
	
	protected JToolBar mainToolBar = new JToolBar();
	protected JButton btnDodajZaposlenog = new JButton("Dodaj zaposlenog");
	protected JButton btnObrisiZaposlenog = new JButton("Obriši zaposlenog");
	protected JButton btnPromeniPodatke = new JButton("Promena podataka");
	protected JButton btnPlata = new JButton("Plata");
	
	public ZaposleniDijalog(JFrame roditelj, boolean modal, boolean laborantiDijalog) {
		super(roditelj, modal);
		this.laborantiDijalog = laborantiDijalog;
		if (laborantiDijalog) {
			this.setTitle("Laboranti");
		}
		else {
			this.setTitle("Medicinski tehničari");
		}
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
		ImageIcon dodajZaposlenogIcon = ResourceLoader.getImageIcon("dodaj.png");
		ImageIcon dodajZaposlenogIconResized = new ImageIcon(dodajZaposlenogIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnDodajZaposlenog.setIcon(dodajZaposlenogIconResized);
		btnDodajZaposlenog.setToolTipText("Dodaj novog zaposlenog");
		mainToolBar.add(btnDodajZaposlenog);
		
		ImageIcon obrisiIcon = ResourceLoader.getImageIcon("quit.png");
		ImageIcon obrisiIconResized = new ImageIcon(obrisiIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		btnObrisiZaposlenog.setIcon(obrisiIconResized);
		btnObrisiZaposlenog.setToolTipText("Obriši zaposlenog");
		mainToolBar.add(btnObrisiZaposlenog);
		
		ImageIcon editZaposleniIcon = ResourceLoader.getImageIcon("editUser.png");
		ImageIcon editZaposleniIconResized = new ImageIcon(editZaposleniIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnPromeniPodatke.setIcon(editZaposleniIconResized);
		btnPromeniPodatke.setToolTipText("Promeni podatke");
		mainToolBar.add(btnPromeniPodatke);
		
		ImageIcon plataIcon = ResourceLoader.getImageIcon("coins.png");
		ImageIcon plataIconResized = new ImageIcon(plataIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnPlata.setIcon(plataIconResized);
		btnPlata.setToolTipText("Plata zaposlenog");
		mainToolBar.add(btnPlata);
		
		mainToolBar.setFloatable(false);
		add(mainToolBar, BorderLayout.NORTH);
		
	}
	
	private void podesiTabelu() {
		if (laborantiDijalog) {
			korisniciTableModel = new KorisniciTableModel(laborantServis.getZaposleniLaboranti());
		}
		else {
			korisniciTableModel = new KorisniciTableModel(mTehničarServis.getZaposleniMedicinari());
		}
		tabelaZaposleni.setModel(korisniciTableModel);
		JPanel panel = new JPanel(new GridLayout(1,1));
		tabelaZaposleni.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaZaposleni.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaZaposleni.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcPan = new JScrollPane(tabelaZaposleni);
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	private void initAkcije() {
		
		btnDodajZaposlenog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (laborantiDijalog) {
					AddEditLaborant addEditLaborant = new AddEditLaborant(ZaposleniDijalog.this, true, null);
					addEditLaborant.setVisible(true);
				}else {
					AddEditMedicinar addEditMedicinar = new AddEditMedicinar(ZaposleniDijalog.this, true, null);
					addEditMedicinar.setVisible(true);
				}
			}
		});
		
		btnPromeniPodatke.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaZaposleni.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati zaposlenog iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else{
					int id = Integer.parseInt(tabelaZaposleni.getValueAt(selectedRow, 0).toString());
					if (laborantiDijalog) {
						Laborant l = laborantServis.getById(id);
						AddEditLaborant addEditLaborant = new AddEditLaborant(ZaposleniDijalog.this, true, l);
						addEditLaborant.setVisible(true);
					}
					else {
						MedicinskiTehničar m = mTehničarServis.getById(id);
						AddEditMedicinar addEditMedicinar = new AddEditMedicinar(ZaposleniDijalog.this, true, m);
						addEditMedicinar.setVisible(true);
					}
				}
			}
		});
		
		btnPlata.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaZaposleni.getSelectedRow();
				if (red == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati zaposlenog iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(tabelaZaposleni.getValueAt(red, 0).toString());
					Korisnik korisnik = korisniciServis.getById(id);
					PlateDijalog p = new PlateDijalog(ZaposleniDijalog.this, true, korisnik);
					p.setVisible(true);
				}
			}
		});
		
		
		
		btnObrisiZaposlenog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaZaposleni.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati zaposlenog iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else{
					int answer = JOptionPane.showConfirmDialog(null, "Da li ste 100% posto sigurni da želite da obrišete ovog zaposlenog :(", "Pitanje",JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						int id = Integer.parseInt(tabelaZaposleni.getValueAt(selectedRow, 0).toString());
						if (laborantiDijalog) {
							laborantServis.dajOtkazLaborantu(id);
							JOptionPane.showMessageDialog(null, "Laborant je uspešno obrisan");
						}else {
							mTehničarServis.dajOtkazMTehničaru(id);
							JOptionPane.showMessageDialog(null, "Medicinski tehničar je uspešno obrisan");
						}
						osveziTabelu();
					}
				}
			}
		});
		
	}
	
	public void osveziTabelu() {
		if (laborantiDijalog) {
			korisniciTableModel = new KorisniciTableModel(laborantServis.getZaposleniLaboranti());
		}
		else {
			korisniciTableModel = new KorisniciTableModel(mTehničarServis.getZaposleniMedicinari());
		}
		tabelaZaposleni.setModel(korisniciTableModel);
	}
	
	
}
