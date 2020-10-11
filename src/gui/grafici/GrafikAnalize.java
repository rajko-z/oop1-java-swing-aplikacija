package gui.grafici;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ListCellRenderer;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.markers.SeriesMarkers;

import entity.PosebnaAnaliza;
import entity.korisnici.Pacijent;
import services.grafici.GrafikAnalizeServis;

public class GrafikAnalize implements GenericChart<XYChart> {

	GrafikAnalizeServis grafikAnalizeServis;
	PosebnaAnaliza analiza;
	
	public GrafikAnalize(Pacijent pacijent, PosebnaAnaliza analiza) {
		this.grafikAnalizeServis = new GrafikAnalizeServis(pacijent, analiza);
		this.analiza = analiza;
	}
	
	@Override
	public XYChart getChart() {

		XYChart chart = new XYChartBuilder().width(800).height(600).title(analiza.getNaziv())
				.xAxisTitle("Datumi").yAxisTitle("Izmerene vrednosti").build();

	    chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.LIGHT_GREY));
		chart.getStyler().setPlotGridLinesColor(Color.blue);
	    chart.getStyler().setChartBackgroundColor(Color.white);
	    chart.getStyler().setAxisTickMarkLength(15);
	    chart.getStyler().setPlotMargin(20);
	    chart.getStyler().setLegendSeriesLineLength(12);
	    chart.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
		chart.getStyler().setDatePattern("yyyy-MM-dd");
	    chart.getStyler().setDecimalPattern("#0.000");

		List<Date> xData = new ArrayList<Date>();
		List<Double> yData = new ArrayList<Double>();
		
		TreeMap<Date, Double>data = grafikAnalizeServis.getPodaciZaGrafik();
		
		for (Map.Entry<Date, Double>par : data.entrySet()) {
			
			xData.add(par.getKey());
			yData.add(par.getValue());
		}
		
		XYSeries series = chart.addSeries("Izmerene\nvrednosti", xData, yData);
		series.setMarkerColor(Color.blue);
		series.setMarker(SeriesMarkers.DIAMOND);
	
		List<Date> xdonjaRef = new ArrayList<Date>();
		List<Double> ydonjaRef = new ArrayList<Double>();
		List<Date> xgornjaRef = new ArrayList<Date>();
		List<Double> ygornjaRef = new ArrayList<Double>();
		Date pocetak;
		Date kraj;
		
		if (xData.size() == 1) {
			Calendar c = Calendar.getInstance(); 
			c.setTime(xData.get(0));
			c.add(Calendar.DATE, -1);
			pocetak = c.getTime();
			c.setTime(xData.get(0));
			c.add(Calendar.DATE, 1);
			kraj = c.getTime();
		}else {
			pocetak = xData.get(0);
			kraj = xData.get(xData.size() - 1);
			
		}
		
		xdonjaRef.add(pocetak);
		xdonjaRef.add(kraj);
		ydonjaRef.add(this.analiza.getDonjaRefVrednost());
		ydonjaRef.add(this.analiza.getDonjaRefVrednost());
		
		xgornjaRef.add(pocetak);
		xgornjaRef.add(kraj);
		ygornjaRef.add(this.analiza.getGornjaRefVrednost());
		ygornjaRef.add(this.analiza.getGornjaRefVrednost());
		
		
		XYSeries donjaRef = chart.addSeries("Donja ref.vrednosti", xdonjaRef, ydonjaRef);
		donjaRef.setMarker(SeriesMarkers.DIAMOND);
		donjaRef.setMarkerColor(Color.green);
		donjaRef.setLineColor(Color.green);
		
		XYSeries gornjaRef = chart.addSeries("Gornja ref.vrednosti", xgornjaRef, ygornjaRef);
		gornjaRef.setMarker(SeriesMarkers.DIAMOND);
		gornjaRef.setMarkerColor(Color.red);
		gornjaRef.setLineColor(Color.red);
		
		
		 //series.setLineColor(XChartSeriesColors.BLUE);
	    //series.setMarkerColor(Color.ORANGE);
	    //series.setMarker(SeriesMarkers.CIRCLE);
	    //series.setLineStyle(SeriesLines.SOLID);

		return chart;
	}
}