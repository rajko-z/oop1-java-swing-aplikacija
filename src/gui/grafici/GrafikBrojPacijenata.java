package gui.grafici;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

import services.grafici.GrafikBrojPacijenataServis;
import utils.DTOselektovaniDatumiIOpsegGodina;

public class GrafikBrojPacijenata implements GenericChart<XYChart> {

	private GrafikBrojPacijenataServis grafServis;
	
	public GrafikBrojPacijenata() {
	}
	
	public GrafikBrojPacijenata(DTOselektovaniDatumiIOpsegGodina dto) {
		this.grafServis = new GrafikBrojPacijenataServis(dto);
	}
	
	@Override
	public XYChart getChart() {

		XYChart chart = new XYChartBuilder().width(800).height(600).title("Grafik odnosa broja pacijenata prema analizi na svake dve nedelje").xAxisTitle("X")
				.yAxisTitle("Y").build();

		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setAxisTitlesVisible(false);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);
	    chart.getStyler().setAxisTickMarkLength(10);
	    chart.getStyler().setPlotMargin(20);
	    chart.getStyler().setDatePattern("dd-MMM");
	  //  Color[] colors = new Color[] { Color.blue, Color.pink, Color.orange};
	//	chart.getStyler().setSeriesColors(colors);
		
		TreeMap<Date, Integer[]> podaci = this.grafServis.getPodaciZaGrafik();
		
		List<Date>xdatumi = new ArrayList<Date>();
		List<Integer>muski = new ArrayList<Integer>();
		List<Integer>zenski = new ArrayList<Integer>();
		List<Integer>oba = new ArrayList<Integer>();
		
		
		for (Map.Entry<Date, Integer[]>par: podaci.entrySet()) {
			Integer brM = par.getValue()[0];
			Integer brF = par.getValue()[1];
			
			xdatumi.add(par.getKey());
			
			muski.add(brM);
			zenski.add(brF);
			oba.add(brM + brF);
		}
		
		chart.addSeries("muško", xdatumi, muski);
		chart.addSeries("žensko", xdatumi, zenski);
		chart.addSeries("oba", xdatumi, oba);
		
		return chart;
	}

}