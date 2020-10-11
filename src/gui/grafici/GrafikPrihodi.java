package gui.grafici;

import java.util.List;
import java.util.Map;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries.PieSeriesRenderStyle;
import org.knowm.xchart.style.PieStyler.AnnotationType;

import entity.GrupaAnaliza;
import entity.Nalaz;
import services.grafici.GrafikPrihodaServis;

public class GrafikPrihodi implements GenericChart<PieChart> {
	
	private GrafikPrihodaServis grafikPrihodaServis;
	
	
	public GrafikPrihodi(List<Nalaz>nalazi) {
		this.grafikPrihodaServis = new GrafikPrihodaServis(nalazi);
	}

	@Override
	public PieChart getChart() {

		PieChart chart = new PieChartBuilder().width(700).height(700).title("Prihodi grupa analiza").build();

		chart.getStyler().setLegendVisible(true);
		chart.getStyler().setAnnotationType(AnnotationType.Label);
		chart.getStyler().setAnnotationDistance(.82);
		chart.getStyler().setPlotContentSize(.9);
		chart.getStyler().setDefaultSeriesRenderStyle(PieSeriesRenderStyle.Donut);

		for (Map.Entry<GrupaAnaliza, Double> par: grafikPrihodaServis.getProcenatPrihodaOdSvakeGrupe().entrySet()) {
			chart.addSeries(par.getKey().getNaziv() + String.valueOf(par.getValue()) + "%", par.getValue());
		}
		return chart;
	}

}