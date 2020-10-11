package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import gui.models.GrupeSaPopustomTableModel;
import services.AnalizeServis;

public class PrikazGrupaPopusta extends JDialog {

	private static final long serialVersionUID = 116538795046763404L;
	private AnalizeServis analizeServis = new AnalizeServis();
	private JTable tabelaPrikaza = new JTable(new GrupeSaPopustomTableModel(analizeServis.getValidneGrupeAnaliza()));
	
	public PrikazGrupaPopusta(JDialog roditelj, boolean modal) {
		super(roditelj, modal);
		this.setTitle("Prikaz grupnih popusta");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		this.pack();
		setLocationRelativeTo(null);
	}

	private void initGUI() {
		JPanel panelCentar = new JPanel(new GridLayout(1,1));
		tabelaPrikaza.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaPrikaza.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaPrikaza.getTableHeader().setReorderingAllowed(false);
		tabelaPrikaza.setAutoCreateRowSorter(true);
		JScrollPane srcPan = new JScrollPane(tabelaPrikaza);
		panelCentar.add(srcPan);
		add(panelCentar, BorderLayout.CENTER);
	}
	
}
