package gui.grafici;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import utils.DTOselektovaniDatumiIOpsegGodina;

public class PrikazGrafikaPacijenti extends JDialog{
	
	private static final long serialVersionUID = 2840673941718396526L;
	private DTOselektovaniDatumiIOpsegGodina dto;
	
	public PrikazGrafikaPacijenti(JDialog roditelj, boolean modal, DTOselektovaniDatumiIOpsegGodina dto) {
		super(roditelj, modal);
		this.dto = dto;
		this.setTitle("Odnos broja pacijenata prema " + "---> Analiza: " + dto.getAnaliza().getNaziv());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initGUI();
		this.pack();
		this.setLocationRelativeTo(null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initGUI() {
		JPanel centar = new JPanel();
		GenericChart<XYChart> exampleChart = new GrafikBrojPacijenata(this.dto);
		XYChart chart = exampleChart.getChart();
		JPanel chartPanel = new XChartPanel(chart);
		centar.add(chartPanel);
		this.getContentPane().add(centar);
	}

}

