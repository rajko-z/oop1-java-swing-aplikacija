package gui.laborant;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import entity.GrupaAnaliza;
import entity.Nalaz;
import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import gui.models.AnalizeZaObraduTableModel;
import gui.models.NalazZaObraduTableModel;
import net.miginfocom.swing.MigLayout;
import res.ResourceLoader;
import services.NalaziServis;

public class LaborantPretragaNalaza extends JDialog {

	private static final long serialVersionUID = 7503696906650414690L;
	protected Laborant laborant;
	private NalaziServis nalazServis = new NalaziServis();
	
	protected JButton btnOdustani = new JButton("Izlaz");
	protected JButton btnPredjiNaObradu = new JButton("Predji na obradu nalaza");
	protected JButton btnPretrazi = new JButton("Pretraži");
	protected JToolBar mainToolbar = new JToolBar();
	protected JTable tabelaNalazi = new JTable();
	@SuppressWarnings("rawtypes")
	protected JList jlistGrupe; 
	
	public LaborantPretragaNalaza(JFrame roditelj, boolean modal, Korisnik korisnik) {
		super(roditelj, modal);
		this.laborant = (Laborant) korisnik;
		setTitle("Odabir nalaza za obradu");
		setSize(1200,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
		initGUI();
		actions();
	}

	private void initGUI() {
		podesiToolBar();
		podesiTabelu();
		podesiZapad();
		podesiJug();
	}
	
	private void podesiJug() {
		JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelJug.add(btnOdustani);
		add(panelJug, BorderLayout.SOUTH);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void podesiZapad() {
		jlistGrupe = new JList(this.laborant.getGrupeAnaliza().toArray());
		JScrollPane src = new JScrollPane(jlistGrupe);
		JPanel panelZapad = new JPanel(new MigLayout());
		panelZapad.add(new JLabel("<html>Izaberite neku od grupe analiza<br>za koju ste specijalizovani<br>"
				+ "i kliknite pretraži.<br> Sistem će vam vratiti sve nalaze koji sadrže<br>"
				+ "bar jednu analizu iz označene grupe."), "span, wrap");
		panelZapad.add(new JLabel(" "), "span, wrap");
		panelZapad.add(src, "wrap");
		panelZapad.add(btnPretrazi, "wrap");
		
		add(panelZapad, BorderLayout.WEST);
		
	}

	private void podesiToolBar() {
		ImageIcon predjiNaObraduIcon = ResourceLoader.getImageIcon("epruveta.png");
		ImageIcon predjiNaObraduResized = new ImageIcon(predjiNaObraduIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		btnPredjiNaObradu.setIcon(predjiNaObraduResized);
		btnPredjiNaObradu.setToolTipText("Predji na obradu nalaza");
		mainToolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		mainToolbar.add(btnPredjiNaObradu);
		mainToolbar.setFloatable(false);
		add(mainToolbar, BorderLayout.NORTH);
		
		btnPredjiNaObradu.setVisible(false);
	}
	
	private void podesiTabelu() {
		JPanel panelCentar = new JPanel(new GridLayout(1,1));
		tabelaNalazi.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaNalazi.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaNalazi.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcPan = new JScrollPane(tabelaNalazi);
		
		panelCentar.add(srcPan);
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
	}
	
	private void setTableData(List<Nalaz>nalazi) {
		this.tabelaNalazi.setModel(new NalazZaObraduTableModel(nalazi));
	}
	
	
	private void actions() {
		addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				LaborantPretragaNalaza.this.dispose();
				LaborantPretragaNalaza.this.setVisible(false);
				super.windowClosing(e);
			}
		});
		
		
		btnOdustani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da prekinete sa traženjem nalaza?",
						  "Pitanje", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					LaborantPretragaNalaza.this.dispose();
					LaborantPretragaNalaza.this.setVisible(false);
				}
				
			}
		});
		
		btnPretrazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jlistGrupe.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Selektujete grupu iz liste.", "Grupa nije selektovana", JOptionPane.ERROR_MESSAGE);
				}
				else {
					GrupaAnaliza grupa = (GrupaAnaliza)jlistGrupe.getSelectedValue();
					ArrayList<Nalaz>nalaziZaObradu = nalazServis.getNeobradjeniNalaziZaLaboranta(grupa);
					if (nalaziZaObradu.size() == 0) {
						JOptionPane.showMessageDialog(null, "Trenutno iz ove grupe nema slobodnih nalaza koje možete da obradite","Informacija", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						setTableData(nalaziZaObradu);
						btnPredjiNaObradu.setVisible(true);
					}
				}
			}
		});
		
		btnPredjiNaObradu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaNalazi.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati nalaz iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int id = Integer.parseInt(tabelaNalazi.getValueAt(selectedRow, 0).toString());
					Nalaz nalaz = nalazServis.getNalazById(id);
					LaborantObradaNalaza l = new LaborantObradaNalaza(LaborantPretragaNalaza.this, true, LaborantPretragaNalaza.this.laborant, nalaz);
					l.setVisible(true);
				}
			}
				
		});
		
	}
	
	
	public void osveziTabelu() {
		NalazZaObraduTableModel n = (NalazZaObraduTableModel) tabelaNalazi.getModel();
		n.fireTableDataChanged();
	}
	
}
