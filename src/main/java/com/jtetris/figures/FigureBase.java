/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtetris.figures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author rafael
 */
public class FigureBase {
    
    final private int UNIT = 20;
    final private int WIDTH = 15;
    final private int LENGTH = 30;
    
    private List<Rectangle> components;
    private Group group;

    public List<Rectangle> getComponents() {
        return components;
    }

    public void setComponents(List<Rectangle> components) {
        this.components = components;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    
    protected List<Rectangle> newElements(){
        
        List<Rectangle> recs = new ArrayList<>();
        
        for (int i=0; i < getComponents().size(); i++){
            recs.add(new Rectangle( getComponents().get(i).getX(), 
                                    getComponents().get(i).getY(), 
                                    getComponents().get(i).getWidth(),
                                    getComponents().get(i).getHeight()));
        }
        return recs;
    }
    
    public FigureBase(){
    }
    
    public void RotateClockWise(){
        List<Integer> levelsX = getLevelsX (getComponents());
        List<Integer> levelsY = getLevelsY (getComponents());
        
        int maxy = Collections.max(levelsY);
       
        int PositionX = getUpperLeftCoordinates(components)[0];
        int PositionY = getUpperLeftCoordinates(components)[1];
        
        for (int i=0; i<getComponents().size(); i++){
            
            components.get(i).setY(levelsX.get(i)*UNIT);
            components.get(i).setX((maxy-levelsY.get(i))*UNIT);
            
        }

        setUpperLeftCoordinates (components, PositionX, PositionY);
    }
    
    public void MoveRight () {
        for (int i=0; i<getComponents().size(); i++){
            double position = getComponents().get(i).getX();
            getComponents().get(i).setX(position + 20);
        }
    }

    public void MoveLeft (){
        for (int i=0; i<getComponents().size(); i++){
            double position = getComponents().get(i).getX();
            getComponents().get(i).setX(position - 20);
        }
    }
    
    public void MoveDown(int units){
        for (int i=0; i<getComponents().size(); i++){
            double position = getComponents().get(i).getY();
            getComponents().get(i).setY(position+10*units);
        }
    }
    
    public List<Rectangle> getCoordinatesDown(int units){
        List<Rectangle> recs = newElements();

        for (int i=0; i<recs.size(); i++){
            double position = recs.get(i).getY();
            recs.get(i).setY((position+10*units));
        }
        
        return recs;
    }
    
    public List<Rectangle> getCoordinatesToRight(){
        List<Rectangle> recs = newElements();

        for (int i=0; i<recs.size(); i++){
            double position = recs.get(i).getX();
            recs.get(i).setX(position+20);
        }
        
        return recs;
    }
    
    public List<Rectangle> getCoordinatesToLeft(){
        List<Rectangle> recs = newElements();

        for (int i=0; i<recs.size(); i++){
            double position = recs.get(i).getX();
            recs.get(i).setX(position-20);
        }
        
        return recs;
    }
    
    public List<Rectangle> getCoordinatesToRotationCW(){
        List<Rectangle> recs = newElements();
        
        List<Integer> levelsX = getLevelsX (recs);
        List<Integer> levelsY = getLevelsY (recs);
        
        int maxy = Collections.max(levelsY);
        
        int PositionX = getUpperLeftCoordinates(recs)[0];
        int PositionY = getUpperLeftCoordinates(recs)[1];
        
        for (int i=0; i<recs.size(); i++){
            recs.get(i).setY(levelsX.get(i)*UNIT);
            recs.get(i).setX((maxy-levelsY.get(i))*UNIT);
        }
        
        setUpperLeftCoordinates (recs,PositionX, PositionY);
        
        return recs;
    }
    
    protected List<Integer> getLevelsX (List<Rectangle> comp){
        List<Integer> posx = new ArrayList<>();
        List<Integer> levels = new ArrayList<>();
        
        for (Rectangle rec : comp){
            posx.add((int)rec.getX());
        }

        int min = Collections.min(posx);
        
        for (Integer x: posx){
            levels.add((x-min)/UNIT);
        }
        
        return levels;
    }
    
    protected List<Integer> getLevelsY (List<Rectangle> comp){
        List<Integer> posy = new ArrayList<>();
        List<Integer> levels = new ArrayList<>();
        
        for (Rectangle rec : comp){
            posy.add((int)rec.getY());
        }

        int min = Collections.min(posy);
        
        for (Integer y: posy){
            levels.add((y-min)/UNIT);
        }
        
        return levels;
    }
    
    protected int [] getUpperLeftCoordinates (List<Rectangle> recs){
        int x, y;
        x = (int)recs.get(0).getX();
        y = (int)recs.get(0).getY();
        
        for (int i=1; i < recs.size(); i++){
            if (x > (int)recs.get(i).getX()){
                x = (int)recs.get(i).getX();
            }
            if (y > (int)recs.get(i).getY()){
                y = (int)recs.get(i).getY();
            }
        }
        return new int [] {x, y};
    }
    
    protected void setUpperLeftCoordinates (List<Rectangle> recs, int x, int y){
        
        int PositionX = getUpperLeftCoordinates(recs)[0];
        int PositionY = getUpperLeftCoordinates(recs)[1];
        
        for (int i=0; i < recs.size(); i++){
            
            int posX = (int)recs.get(i).getX();
            int posY = (int)recs.get(i).getY();
            
            recs.get(i).setX((posX-PositionX) + x);
            recs.get(i).setY((posY-PositionY) + y);
            
        }
        
    }
}