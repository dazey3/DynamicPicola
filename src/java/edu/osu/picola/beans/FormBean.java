package edu.osu.picola.beans;
import java.io.Serializable;  
import java.util.ArrayList;
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
  
public class FormBean implements Serializable {  
  
    private List<String> selectedMovies;  
  
    private List<String> movies;  
  
    public FormBean() {  
        movies = new ArrayList<String>();  
        movies.add("Scarface");  
        movies.add("Goodfellas");  
        movies.add("Godfather");  
        movies.add("Carlito's Way"); 
        
    }  
  
    public List<String> getSelectedMovies() {  
        return selectedMovies;  
    }  
    public void setSelectedMovies(List<String> selectedMovies) {  
        this.selectedMovies = selectedMovies;  
    }  
  
    public List<String> getMovies() {  
        return movies;  
    }  
}  
                          