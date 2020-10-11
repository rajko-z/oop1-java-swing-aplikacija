package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import entity.Nalaz;
import gui.models.AnalizeZaObraduTableModel;

public class PrikazStavkiNalaza extends JDialog {

	private static final long serialVersionUID = 8523807713149643563L;
	protected JTable tabelaAnalizeZaObradu;
	private Nalaz nalaz;
	
	public PrikazStavkiNalaza(JDialog roditelj, boolean modal, Nalaz nalaz) {
		super(roditelj, modal);
		this.nalaz = nalaz;
		setTitle("Vrednosti nalaza");
		setSize(1000,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
		initGUI();
	}
	
	private void initGUI() {
		initTable();
	}
	
	private void initTable() {
		tabelaAnalizeZaObradu = new JTable(new AnalizeZaObraduTableModel(this.nalaz.getAnalizeZaObradu()));
		JPanel panelCentar = new JPanel(new GridLayout(1,1));
		tabelaAnalizeZaObradu.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaAnalizeZaObradu.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaAnalizeZaObradu.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcPan = new JScrollPane(tabelaAnalizeZaObradu);
		panelCentar.add(srcPan);
		this.getContentPane().add(panelCentar, BorderLayout.CENTER);
	}
}
