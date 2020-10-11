package gui.grafici;

import java.awt.Color;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;

public class GrafikRashodi implements GenericChart<PieChart> {
	
	private double procenatLaborant;
	private double procenatMedicinar;
	
	public GrafikRashodi(double procentaMedicinar, double procenatLaborant) {
		this.procenatMedicinar = procentaMedicinar;
		this.procenatLaborant = procenatLaborant;
	}
	
	@Override
	public PieChart getChart() {
		PieChart chart = new PieChartBuilder().width(400).height(300).title("Podela rashoda").build();

		Color[] sliceColors = new Color[] { Color.orange, new Color(132, 231, 253) };
		chart.getStyler().setSeriesColors(sliceColors);

		chart.addSeries("Tehničari", procenatMedicinar);
		chart.addSeries("Laboranti", procenatLaborant);

		return chart;
	}

}
