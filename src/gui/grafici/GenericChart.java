package gui.grafici;

import org.knowm.xchart.internal.chartpart.Chart;

public interface GenericChart<C extends Chart<?, ?>> {
	C getChart();
}
