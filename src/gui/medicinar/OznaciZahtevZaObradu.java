package gui.medicinar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

import entity.Zahtev;
import entity.korisnici.Korisnik;
import entity.korisnici.MedicinskiTehničar;
import entity.korisnici.Pacijent;
import gui.models.ZahtevTableModel;
import res.ResourceLoader;
import services.PacijentServis;
import services.ZahteviServis;

public class OznaciZahtevZaObradu extends JDialog {

	private static final long serialVersionUID = 1326470575042732627L;
	protected MedicinskiTehničar mTehničar;
	protected ZahteviServis zahteviServis = new ZahteviServis();
	protected PacijentServis pacijentServis = new PacijentServis();
	
	protected JButton btnOdustani = new JButton("Odustani");
	protected JButton btnStaviUObradu = new JButton("Spremno za obradu");
	protected JButton btnPretrazi = new JButton("Pretraži");
	protected JToolBar mainToolbar = new JToolBar();
	protected JTable tabelaZahtev = new JTable();
	protected JTextField tfLBO = new JTextField(10);

	public OznaciZahtevZaObradu(Frame parent, boolean modal, Korisnik korisnik) {
		super(parent, modal);
		this.mTehničar = (MedicinskiTehničar) korisnik;
		this.setTitle("Označite zahtev kao speman za obradu");
		
		this.setIconImage(ResourceLoader.getImageIcon("main.png").getImage());
		setSize(1100,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
		initGUI();
		akcije();
		
	}

	private void initGUI() {
		podesiToolBar();
		podesiTabelu();
		podesiJug();
	}
	
	
	private void podesiTabelu() {
		
		JPanel panelCentar = new JPanel(new GridLayout(1,1));
		tabelaZahtev.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaZahtev.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaZahtev.getTableHeader().setReorderingAllowed(false);
		JTableHeader th = tabelaZahtev.getTableHeader();
		th.setPreferredSize(new Dimension(50, 45));
		JScrollPane srcPan = new JScrollPane(tabelaZahtev);
		
		panelCentar.add(srcPan);
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
	}
	
	
	
	private void podesiToolBar() {
		mainToolbar.add(new JLabel("Unesite LBO pacijenta: "));
		mainToolbar.add(tfLBO);
		
		ImageIcon pretraziIcon = ResourceLoader.getImageIcon("document.png");
		ImageIcon pretraziIconResized = new ImageIcon(pretraziIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnPretrazi.setIcon(pretraziIconResized);
		mainToolbar.add(btnPretrazi);
		
		ImageIcon staviUObraduIcon = ResourceLoader.getImageIcon("tickGreen.png");
		ImageIcon staviUObraduIconResized = new ImageIcon(staviUObraduIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnStaviUObradu.setIcon(staviUObraduIconResized);
		
		mainToolbar.add(btnStaviUObradu);
		mainToolbar.setFloatable(false);
		add(mainToolbar, BorderLayout.NORTH);
		
		btnStaviUObradu.setVisible(false);
	}
	
	
	private void podesiJug() {
		JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelJug.add(btnOdustani);
		add(panelJug, BorderLayout.SOUTH);
	}
	
	private void setTableData(List<Zahtev>data) {
		tabelaZahtev.setModel(new ZahtevTableModel(data));
	}
	
	
	private void akcije() {
		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OznaciZahtevZaObradu.this.dispose();
				OznaciZahtevZaObradu.this.setVisible(false);
			}
		});
		
		
		btnStaviUObradu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaZahtev.getSelectedRow();
				if (selectedRow == -1) {
					
					JOptionPane.showMessageDialog(null, "Morate selektovati zahtev iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int id = Integer.parseInt(tabelaZahtev.getValueAt(selectedRow, 0).toString());
					Zahtev zahtev = zahteviServis.getZahtevById(id);
					int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da označite ovaj zahtev kao preuzet\n"
							+ "tako da laboranti mogu da krenu na obradu.", "Pitanje", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						zahteviServis.oznaciZahtevNaStanjeObrade(zahtev, mTehničar);
						JOptionPane.showMessageDialog(null, "Zahtev je uspešno stavljen na obradu.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
						OznaciZahtevZaObradu.this.dispose();
						OznaciZahtevZaObradu.this.setVisible(false);
					}
				}
			}
		});
		
		btnPretrazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (proveriUnos()) {
					Pacijent pacijent = pacijentServis.getPacijentSaZadatimLBObrojem(tfLBO.getText().trim());
					Zahtev zahtev = zahteviServis.getZahtevPacijentaKojiNijeStavljenUObradu(pacijent);
					List<Zahtev> dataForTable = new ArrayList<Zahtev>();
					dataForTable.add(zahtev);
					setTableData(dataForTable);
					btnStaviUObradu.setVisible(true);
				}
			}
			
			
			private boolean proveriUnos() {
				String LBO = tfLBO.getText().trim();
				Pacijent pacijent = pacijentServis.getPacijentSaZadatimLBObrojem(LBO);
				if (LBO.equals("")) {
					JOptionPane.showMessageDialog(null, "Unesite LBO broj.", "Greška", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				if (pacijent == null) {
					JOptionPane.showMessageDialog(null, "Nije moguće pronaći pacijenta sa zadatim LBO brojem.", "Greška", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				Zahtev zahtev = zahteviServis.getZahtevPacijentaKojiNijeStavljenUObradu(pacijent);
				if (zahtev == null) {
					JOptionPane.showMessageDialog(null, "Za ovog pacijenta ne postoji zahtev koji čeka na obradu.", "Greska", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				if (zahtev.getStanjeZahteva().getId() == 2) {
					if (!zahteviServis.zahtevPripadaMedicinaru(mTehničar, zahtev)) {
						JOptionPane.showMessageDialog(null, "Zahtev za ovog pacijenta je u nadležnosti\ndrugog mediciskog tehničara.", "Greska", JOptionPane.WARNING_MESSAGE);
						return false;
					}
				}
				if (zahteviServis.uzorakNijePreuzet(zahtev)) {
					JOptionPane.showMessageDialog(null, "Uzorak za zahtev ovog pacijenta nije preuzet.", "Greska", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				return true;
			}
		});
		
	}
}
