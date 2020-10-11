package gui.admin;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import entity.Nalaz;
import gui.PrikazStavkiNalaza;
import gui.models.NalazZaObraduTableModel;
import services.NalaziServis;

public class PregledNezavrsenihNalaza extends JDialog{

	private static final long serialVersionUID = 1878333428751296352L;
	
	protected JTable tabelaNalazi = new JTable();
	private List<Nalaz>nalazi;
	private NalaziServis nalaziServis = new NalaziServis();
	
	public PregledNezavrsenihNalaza(JFrame roditelj, boolean modal, List<Nalaz>nalazi) {
		super(roditelj, modal);
		this.nalazi = nalazi;
		setTitle("Nezavršeni nalazi");
		setSize(1000,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
		initGUI();
		actions();
	}
	
	private void initGUI() {
		podesiTabelu();
	}
	
	private void podesiTabelu() {
		tabelaNalazi.setModel(new NalazZaObraduTableModel(nalazi));
		JPanel panelCentar = new JPanel(new GridLayout(1,1));
		tabelaNalazi.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaNalazi.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaNalazi.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcPan = new JScrollPane(tabelaNalazi);
		panelCentar.add(srcPan);
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
	}
	private void actions() {
		tabelaNalazi.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = tabelaNalazi.getSelectedRow();
				if (selectedRow != -1) {
					int id = Integer.parseInt(tabelaNalazi.getValueAt(selectedRow, 0).toString());
					Nalaz nalaz = nalaziServis.getNalazById(id);
					PrikazStavkiNalaza p = new PrikazStavkiNalaza(PregledNezavrsenihNalaza.this, true, nalaz);
					p.setVisible(true);
				}
			}
		});
	}
	
}
