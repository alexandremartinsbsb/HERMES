package maxempresarial.controlador;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

@Named
@RequestScoped
public class GraficoVendasBean {

	private LineChartModel multiAxisModel;

	@PostConstruct
	public void init() {
		createMultiAxisModel();
	}

	public LineChartModel getMultiAxisModel() {
		return multiAxisModel;
	}

	private void createMultiAxisModel() {
		multiAxisModel = new LineChartModel();

		BarChartSeries boys = new BarChartSeries();
		boys.setLabel("Boys");

		boys.set("2004", 120);
		boys.set("2005", 100);
		boys.set("2006", 44);
		boys.set("2007", 150);
		boys.set("2008", 25);

		LineChartSeries girls = new LineChartSeries();
		girls.setLabel("Girls");
		girls.setXaxis(AxisType.X2);
		girls.setYaxis(AxisType.Y2);

		girls.set("A", 52);
		girls.set("B", 60);
		girls.set("C", 110);
		girls.set("D", 135);
		girls.set("E", 120);

		multiAxisModel.addSeries(boys);
		multiAxisModel.addSeries(girls);

		multiAxisModel.setTitle("Multi Axis Chart");
		multiAxisModel.setMouseoverHighlight(false);

		multiAxisModel.getAxes().put(AxisType.X, new CategoryAxis("Years"));
		multiAxisModel.getAxes().put(AxisType.X2, new CategoryAxis("Period"));

		Axis yAxis = multiAxisModel.getAxis(AxisType.Y);
		yAxis.setLabel("Birth");
		yAxis.setMin(0);
		yAxis.setMax(200);

		Axis y2Axis = new LinearAxis("Number");
		y2Axis.setMin(0);
		y2Axis.setMax(200);

		multiAxisModel.getAxes().put(AxisType.Y2, y2Axis);
	}

}
