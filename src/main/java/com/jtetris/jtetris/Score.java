/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtetris.jtetris;

import com.jtetris.figures.Figure;
import java.util.List;
import javafx.scene.control.Label;

/**
 *
 * @author rafael
 */
public class Score {
    
    final private int LINE1 = 40;
    final private int LINE2 = 40;
    final private int LINE3 = 40;
    final private int LINE4 = 40;
    
    private int points;
    private int level;
    private Label lblPoints;

    public Label getLblPoints() {
        return lblPoints;
    }

    public void setLblPoints(Label lblPoints) {
        this.lblPoints = lblPoints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    Score(int level, Label lbl){
        this.points = 0;
        this.level = level;
        this.lblPoints = lbl;
    }
    
    public void setScoreByHardDrop (Figure f){
        
        int pointsFigure = f.getComponents().size();
        points += pointsFigure;
        this.lblPoints.setText(Integer.toString(points));
    }
    
    public void setScoreByLines(List<Integer> lines){
        
        switch(lines.size()){
            case 1:
                points += LINE1*(level+1);
                break;
            case 2:
                points += LINE2*(level+1);
                break;
            case 3:
                points += LINE3*(level+1);
                break;
            case 4:
                points += LINE4*(level+1);
                break;
            default:
                break;
        }
        
        this.lblPoints.setText(Integer.toString(points));
    }
}
