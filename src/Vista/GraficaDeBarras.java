package Vista;

import Modelo.Candidato;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class GraficaDeBarras extends JFrame {

    private ArrayList<Candidato> candidato;
    private static GraficaDeBarras instancia;

    private GraficaDeBarras(String title) {
        super(title);
        RefineryUtilities.centerFrameOnScreen(this);
    }

    public static GraficaDeBarras getInstance(String title) {
        if (instancia == null) {
            instancia = new GraficaDeBarras(title);
        }
        return instancia;
    }

    public void setDataSet(ArrayList<Candidato> candidatos) {
        this.candidato = candidatos;
        this.dibujarGrafica();
    }

    private void dibujarGrafica() {
        final CategoryDataset datos = createDataset();
        final JFreeChart grafica = createChart(datos);
        final ChartPanel chartPanel = new ChartPanel(grafica);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private CategoryDataset createDataset() {
        // create data set...
        final DefaultCategoryDataset data = new DefaultCategoryDataset();
        // column keys...
        final String categoria1 = "Votaciones";
        // row keys...
        for (Candidato cand : this.candidato) {
            data.addValue(cand.getNumVotos(), cand.getNombre(), categoria1);
        }
        return data;
    }

    private JFreeChart createChart(final CategoryDataset datos) {
        // create the chart...
        final JFreeChart graficaBarra = ChartFactory.createBarChart(
                "Votos - Grafica de Barras",
                null,
                "Votos",
                datos,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // set the background color for the chart...
        graficaBarra.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = graficaBarra.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true);

        int i = 0;

        for (Candidato cand : this.candidato) {
            renderer.setSeriesPaint(i, cand.getColor());
            i++;
        }
        return graficaBarra;
    }

}
