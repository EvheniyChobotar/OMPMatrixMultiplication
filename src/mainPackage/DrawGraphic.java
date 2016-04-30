package mainPackage;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class DrawGraphic extends ApplicationFrame 
{
    public DrawGraphic(String title) 
    {
        super(title);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(  "Графік" ,
                                                                  "Розмір вхідних даних" ,
                                                                  "Час" ,
                                                                  createDataset() ,
                                                                  PlotOrientation.VERTICAL ,
                                                                  true , true , false);
        
        ChartPanel chartPanel   = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize( new java.awt.Dimension(1200, 700));
        final XYPlot plot = xylineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint( 0 , Color.GREEN);
        renderer.setSeriesPaint( 1 , Color.RED);
        plot.setRenderer(renderer); 
        setContentPane(chartPanel); 
        
    }
    
    private XYDataset createDataset()
    {
        XYSeries parallelGraphic    = new XYSeries("Паралельно");  
        XYSeries sequentialGraphic  = new XYSeries("Послідовно");
        XYSeries boost              = new XYSeries("Прискорення");
        
        for(int i=0;i<Main.dimensionList.size();i++)
        {
            System.out.println("Пар"+Main.parellelTimeList.get(i)+"\nПосл"+Main.sequentialTimeList.get(i)+"\n");
            parallelGraphic.add((int)Main.dimensionList.get(i),Main.parellelTimeList.get(i));
            sequentialGraphic.add((int)Main.dimensionList.get(i),Main.sequentialTimeList.get(i));
            boost.add((int)Main.dimensionList.get(i),(Main.sequentialTimeList.get(i)/Main.parellelTimeList.get(i)));
        }
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        //dataset.addSeries(parallelGraphic);  
        //dataset.addSeries(sequentialGraphic);
        dataset.addSeries(boost);
        return dataset;
    }
}
