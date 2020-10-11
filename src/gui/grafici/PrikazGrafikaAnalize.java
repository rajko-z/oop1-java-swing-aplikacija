package gui.grafici;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import entity.PosebnaAnaliza;
import entity.korisnici.Pacijent;

public class PrikazGrafikaAnalize extends JDialog{

	private static final long serialVersionUID = -5546186817800396559L;
	
	protected Pacijent pacijent;
	protected PosebnaAnaliza analiza;
	
	public PrikazGrafikaAnalize(JDialog roditelj, boolean modal, Pacijent pacijent, PosebnaAnaliza analiza) {
		super(roditelj, modal);
		this.pacijent = pacijent;
		this.analiza = analiza;
		this.setTitle("Analiza: " + analiza.getNaziv() + " ---GRAFIK---");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initGUI() {
		JPanel centar = new JPanel();
		GenericChart<XYChart> exampleChart = new GrafikAnalize(pacijent, analiza);
		XYChart chart = exampleChart.getChart();
		JPanel chartPanel = new XChartPanel(chart);
		centar.add(chartPanel);
		this.getContentPane().add(centar);
	}
}
