package Vista;

import Modelo.Candidato;
import java.awt.Color;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class GraficaDePastel extends ApplicationFrame {

    private static ArrayList<Candidato> candidato;
    private static GraficaDePastel instancia;

    private GraficaDePastel(String title) {
        super(title);
        RefineryUtilities.centerFrameOnScreen(this);
    }

    public static GraficaDePastel getInstance(String title) {
        if (instancia == null) {
            instancia = new GraficaDePastel(title);
        }
        return instancia;
    }

    public void setDataSet(ArrayList<Candidato> candidatos) {
        this.candidato = candidatos;
        this.dibujarGrafica();
    }

    private void dibujarGrafica() {
        final JFreeChart grafica = createChart(createDataset());
        final ChartPanel chartPanel = new ChartPanel(grafica);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }
    
    private static JFreeChart createChart(PieDataset graficaPastel) {

        JFreeChart grafica = ChartFactory.createPieChart(
                "Votos - Grafica de Pastel",
                graficaPastel,
                true,
                true,
                false);
        grafica.setBorderPaint(new Color(222, 222, 255));
        PiePlot plot = (PiePlot) grafica.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setNoDataMessage("No data available");
        plot.setCircular(true);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0} = {2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));
        plot.setLabelGap(0.02);

        for (Candidato candidatos : candidato) {
            plot.setSectionPaint(candidatos.getNombre(), candidatos.getColor());
        }
        return grafica;

    }

    private static PieDataset createDataset() {
        DefaultPieDataset graficaPastel = new DefaultPieDataset();

        for (Candidato candidatos : candidato) {
            graficaPastel.setValue(candidatos.getNombre(), candidatos.getNumVotos());
        }

        return graficaPastel;
    }
}
