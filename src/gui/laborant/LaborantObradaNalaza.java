package gui.laborant;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

import entity.AnalizaZaObradu;
import entity.Nalaz;
import entity.korisnici.Laborant;
import gui.models.AnalizeZaObraduTableModel;
import res.ResourceLoader;
import services.NalaziServis;

public class LaborantObradaNalaza extends JDialog {

	private static final long serialVersionUID = 7503696906650414690L;
	private Laborant laborant;
	private JDialog roditelj;
	private Nalaz nalaz;
	private NalaziServis nalaziServis = new NalaziServis();
	
	
	protected JButton btnOdustani = new JButton("Izlaz");
	protected JButton btnObradi = new JButton("Obradi stavku nalaza");
	protected JToolBar mainToolbar = new JToolBar();
	protected JTable tabelaAnalizeZaObradu;
	
	
	
	public LaborantObradaNalaza(JDialog roditelj, boolean modal, Laborant laborant, Nalaz nalaz) {
		super(roditelj, modal);
		this.roditelj = roditelj;
		this.laborant = laborant;
		this.nalaz = nalaz;
		
		setTitle("Obrada nalaza");
		setSize(1200,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		initGui();
		initAkcije();
	}
	
	private void initGui() {
		podesiToolBar();
		podesiTabelu();
		podesiJug();
	}
	
	
	private void podesiToolBar() {
		ImageIcon obradiIcon = ResourceLoader.getImageIcon("science.png");
		ImageIcon obradiIconResized = new ImageIcon(obradiIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		btnObradi.setIcon(obradiIconResized);
		btnObradi.setToolTipText("Obradi stavku nalaza");
		mainToolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		mainToolbar.add(btnObradi);
		mainToolbar.setFloatable(false);
		add(mainToolbar, BorderLayout.NORTH);
		
	}
	
	private void podesiTabelu() {
		tabelaAnalizeZaObradu = new JTable(new AnalizeZaObraduTableModel(this.nalaz.getAnalizeZaObradu()));
		JPanel panelCentar = new JPanel(new GridLayout(1,1));
		tabelaAnalizeZaObradu.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaAnalizeZaObradu.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaAnalizeZaObradu.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcPan = new JScrollPane(tabelaAnalizeZaObradu);
		
		panelCentar.add(srcPan);
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
	}
	
	private void podesiJug() {
		JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelJug.add(btnOdustani);
		add(panelJug, BorderLayout.SOUTH);
	}
	
	private void initAkcije() {
		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da prekinete sa obradom nalaza?",
						  "Pitanje", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					LaborantObradaNalaza.this.dispose();
					LaborantObradaNalaza.this.setVisible(false);
				}
				
			}
		});
		
		btnObradi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaAnalizeZaObradu.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati stavku nalaza iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int id = Integer.parseInt(tabelaAnalizeZaObradu.getValueAt(selectedRow, 0).toString());
					AnalizaZaObradu a = nalaziServis.getAnalizuZaObraduById(id);
					if (a.isJesteObradjena()) {
						JOptionPane.showMessageDialog(null, "Ova stavka je već obradjena", "Greska", JOptionPane.ERROR_MESSAGE);
					}
					else if (nalaziServis.laborantNeMozeDaRadiStavkuNalaza(laborant, a)) {
						JOptionPane.showMessageDialog(null, "Ne možete da radite ovu stavku nalaza\njer niste za nju specijalizovani","Greska",JOptionPane.ERROR_MESSAGE);
					}
					else {
						double obradjenaVrednost = nalaziServis.obradiIvratiVrednostStavkeNalaza(laborant, a);
						String poruka = generisPorukuOIzmerenojVrednosti(obradjenaVrednost, a);
						JOptionPane.showMessageDialog(null, poruka ,"Informacija", JOptionPane.INFORMATION_MESSAGE);
						osveziTabelu();
						nalaziServis.obeleziDatumNalazaAkoJeZavrsenIStaviZahtevUObradjen(nalaz);
						((LaborantPretragaNalaza) roditelj).osveziTabelu();
					}
					
				}
			}

			private String generisPorukuOIzmerenojVrednosti(double obradjenaVrednost, AnalizaZaObradu a) {
				return String.format("Vrednost je uspešno obradjena.\n\n-------Rezultati obrade-------\n"
						+ "izmerena vrednost:   %s\ndonja ref.vrednost:   %s\ngornja ref.vrednost:"
						+ "   %s", obradjenaVrednost, a.getAnaliza().getDonjaRefVrednost(), a.getAnaliza().getGornjaRefVrednost());
			}		
		});
		
		
	}
	
	public void osveziTabelu() {
		AnalizeZaObraduTableModel am = (AnalizeZaObraduTableModel)this.tabelaAnalizeZaObradu.getModel();
		am.fireTableDataChanged();
	}
	
	
}
