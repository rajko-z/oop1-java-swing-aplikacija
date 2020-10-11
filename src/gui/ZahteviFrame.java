package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

import entity.korisnici.Korisnik;
import entity.korisnici.MedicinskiTehničar;
import gui.models.ZahtevTableModel;
import repositories.RepositoryFactory;
import res.ResourceLoader;
import services.ZahteviServis;


public class ZahteviFrame extends JDialog{

	private static final long serialVersionUID = 5008391436084259752L;
	protected Korisnik korisnik;
	private JTable tabelaZahteva = new JTable();
	private ZahteviServis zahteviServis = new ZahteviServis();
	
	public ZahteviFrame(JFrame roditelj, boolean modal, Korisnik korisnik) {
		super(roditelj, modal);
		
		this.korisnik = korisnik;
		this.setIconImage(ResourceLoader.getImageIcon("main.png").getImage());
		setTitle("Pregled zahteva");
		setSize(1800,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
		initGUI();
	}


	private void initGUI() {
		
		setTableData();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		tabelaZahteva.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaZahteva.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaZahteva.getTableHeader().setReorderingAllowed(false);
		JTableHeader th = tabelaZahteva.getTableHeader();
		th.setPreferredSize(new Dimension(50, 45));
		
		JScrollPane srcPan = new JScrollPane(tabelaZahteva);
		
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	private void setTableData() {
		if (korisnik instanceof MedicinskiTehničar) {
			tabelaZahteva.setModel(new ZahtevTableModel(RepositoryFactory.getInstance().getZahtevRepo().getEntitetiList()));
		}else {
			tabelaZahteva.setModel(new ZahtevTableModel(zahteviServis.getSviZahteviNaCekanju()));
		}
	}
}
