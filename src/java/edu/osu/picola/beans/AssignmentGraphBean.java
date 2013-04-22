/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.picola.beans;
import edu.osu.picola.beans.AssignmentMenuBean.MenuTab;
import edu.osu.picola.businesslogic.MCStatisticHandler;
import java.io.Serializable;
import java.util.List;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author Billium813
 */
public class AssignmentGraphBean implements Serializable {
    private MenuTab selectedMenuTab;
    private CartesianChartModel categoryModel = new CartesianChartModel(); 

    public CartesianChartModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CartesianChartModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public MenuTab getSelectedMenuTab() {
        return selectedMenuTab;
    }

    public void setSelectedMenuTab(MenuTab selectedMenuTab) {
        this.selectedMenuTab = selectedMenuTab;
    }
    

    
    
    public void updateGraph(){
        System.out.println("Update chart model!");
        categoryModel = new CartesianChartModel();
        System.out.println("SelectedMenuTab: " + selectedMenuTab);
        System.out.println("SelectedMenuTab: Assignment: " + selectedMenuTab.getAssignment());
        System.out.println("SelectedMenuTab: Assignment: ID: " + selectedMenuTab.getAssignment().getAssignment_id());
        List<Integer> values = MCStatisticHandler.getMCStatsForAssignment(selectedMenuTab.getAssignment().getAssignment_id());
        ChartSeries value = new ChartSeries();
        for(int count = 0; count < values.size(); count++){
            System.out.println("MC Option: " + new Character((char)(count+65)) + " with " + values.get(count) + "chosen");
            value.set(new Character((char)(count+65)), values.get(count));
        }
        categoryModel.addSeries(value);
    }
    
    
}
