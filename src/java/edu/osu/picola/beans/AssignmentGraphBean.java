/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.picola.beans;
import edu.osu.picola.beans.AssignmentMenuBean.MenuTab;
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
public class AssignmentGraphBean  {
    private MenuTab selectedAssignment;
    private CartesianChartModel categoryModel; 

    public CartesianChartModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CartesianChartModel categoryModel) {
        this.categoryModel = categoryModel;
    }
    
    public MenuTab getSelectedAssignment() {
        return selectedAssignment;
    }

    public void setSelectedAssignment(MenuTab selectedAssignment) {
        this.selectedAssignment = selectedAssignment;
    }
    
    
    public void updateGraph(){
        categoryModel = new CartesianChartModel();
        List<Integer> values = MCStatisticHandler.getMCStatsForAssignment(selectedAssignment.getAssignment().getAssignment_id());
        ChartSeries value = new ChartSeries();
        for(int count = 0; count < values.size(); count++){
            value.set(new Character((char)(count+65)), values.get(count));
        }
        categoryModel.addSeries(value);
    }
    
    
}
