package gui.admin.izvestaji;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;

import entity.Nalaz;
import gui.grafici.GenericChart;
import gui.grafici.GrafikPrihodi;
import gui.models.GotovNalazTableModel;

public class PrihodiNalaza extends JDialog{
	private static final long serialVersionUID = 5703757367787238291L;
	
	protected JTable tabelaPrihoda;
	protected List<Nalaz>dataForTbl;
	
	protected JTextField tfUkupniPrihodi = new JTextField(20); 
	
	public PrihodiNalaza(JDialog roditelj, boolean modal, List<Nalaz>nalazi) {
		super(roditelj, modal);
		this.dataForTbl = nalazi;
		this.setTitle("Prihodi");
		setSize(1800,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
		initGUI();
	}
	
	private void initGUI() {
		initTabela();
		podesiJug();
		postaviGrafik();
	}
	
	private void initTabela() {
		this.tabelaPrihoda = new JTable(new GotovNalazTableModel(this.dataForTbl));
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		for (int i = 0; i < 6; i++) {
			tabelaPrihoda.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		}
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		tabelaPrihoda.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaPrihoda.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaPrihoda.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcPan = new JScrollPane(tabelaPrihoda);
		panel.add(srcPan);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	
	private void podesiJug() {
		tfUkupniPrihodi.setText(String.valueOf(getUkupniPrihodi()));
		tfUkupniPrihodi.setEditable(false);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.setBackground(Color.white);
		panel.add(new JLabel("Ukupni prihodi:"));
		panel.add(tfUkupniPrihodi);
		this.getContentPane().add(panel, BorderLayout.SOUTH);

	}
	
	private double getUkupniPrihodi() {
		int retVal = 0;
		for (int row = 0; row < tabelaPrihoda.getRowCount(); row++) {
			GotovNalazTableModel model = (GotovNalazTableModel) tabelaPrihoda.getModel();
			double cena = (double) model.getValueAt(row, 4);
			retVal += cena;
		}
		return retVal;
	}
	
	private void postaviGrafik() {
		GenericChart<PieChart> exampleChart = new GrafikPrihodi(dataForTbl);
		PieChart chart = exampleChart.getChart();
		JPanel chartPanel = new XChartPanel(chart);
		this.getContentPane().add(chartPanel, BorderLayout.WEST);
	}

}
