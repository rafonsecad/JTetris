/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtetris.container;

import com.jtetris.figures.Figure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author rafael
 */
public class TContainer {
    
    final private int UNIT = 20;
    final private int WIDTH = 15;
    final private int LENGTH = 30;
    
    private List<Rectangle> elements;
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Rectangle> getElements() {
        return elements;
    }

    public void setElements(List<Rectangle> elements) {
        this.elements = elements;
    }
    
    public static Rectangle newRectangle(Rectangle r){
        return new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }
    
    public TContainer(){
        elements = new ArrayList<>();
        group = new Group();
        //floor
        for(int i=0; i < WIDTH; i++){
            elements.add(new Rectangle(i*UNIT, UNIT*LENGTH, 20, 20));
            group.getChildren().add(elements.get(i));
        }
        
        //Test Right
        
//        Rectangle r = new Rectangle(WIDTH*UNIT-20, 20, 20, 20);
//        elements.add(r);
//        r.setStyle("-fx-fill:yellow");
//        group.getChildren().add(elements.get(WIDTH));
    }
    
    
    public boolean isContainerFull(){

        int minYCoordinate = UNIT*LENGTH;
        for (Rectangle r : elements){
            if (r.getY() < minYCoordinate){
                minYCoordinate = (int)r.getY();
            }
        }
        
        if (minYCoordinate <= UNIT){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void SettleFigure (Figure f){
        AddFigure(f);
    }
    
    public void FastSettle (Figure f){
        
        int Y = 0;
        while (!IsSettled(f, Y)){
            Y++;
        }
        f.MoveDown(Y-2);
    }
    
    public Group AddFigure (Figure f){
        List<Rectangle> elements = getElements();
        Group g = new Group();
        for (Rectangle rec : f.getComponents()){
            
            Rectangle r = newRectangle(rec);
            r.setStyle(rec.getStyle());
            elements.add(r);
            g.getChildren().add(r);
        }
        return g;
    }
    
    public List<Rectangle> RemoveLines(List<Integer> lines){
        List<Rectangle> RectanglesToRemoved = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        
        if (!lines.isEmpty()){
            
            for (int i = 0; i<getElements().size(); i++){
                for (int j=0; j < lines.size(); j++){
                    if (lines.get(j)*UNIT == (int)getElements().get(i).getY()){
                        
                        RectanglesToRemoved.add(newRectangle(getElements().get(i)));
                        indexes.add(i);
                    }
                }
            }
            
            for(int index=indexes.size()-1; index >= 0; index--){
                this.elements.remove(indexes.get(index).intValue());
            }
        }
        return RectanglesToRemoved;
    }
    
    public boolean IsSettled (Figure f, int y){
        List<Rectangle> comp = f.getCoordinatesDown(y);
        List<Rectangle> elements = getElements();
        
        for (Rectangle RecContainer : elements){
            for (Rectangle RecFigure: comp){
                if ((int)RecContainer.getX() == (int)RecFigure.getX() &&
                    (int)RecContainer.getY() == (int)RecFigure.getY()){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean CanMoveToRight(Figure f){
        List<Rectangle> comp = f.getCoordinatesToRight();
        List<Rectangle> elements = getElements();
        
        for (Rectangle RecContainer : elements){
            for (Rectangle RecFigure: comp){
                
                if ((int)RecFigure.getX() == UNIT*WIDTH){
                    return false;
                }
                
                if ((int)RecContainer.getX() == (int)RecFigure.getX() &&
                    (int)RecContainer.getY() == (int)RecFigure.getY()){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public boolean CanMoveToLeft(Figure f){
        List<Rectangle> comp = f.getCoordinatesToLeft();
        List<Rectangle> elements = getElements();
        
        for (Rectangle RecContainer : elements){
            for (Rectangle RecFigure: comp){
                
                if ((int)RecFigure.getX() == -20){
                    return false;
                }
                
                if ((int)RecContainer.getX() == (int)RecFigure.getX() &&
                    (int)RecContainer.getY() == (int)RecFigure.getY()){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public List<Integer> getLines (){
        List<Integer> LinesCompleted = new ArrayList<>();
        int Lines [] = new int [LENGTH];
        
        for (int i = 0; i < Lines.length; i++){
            Lines[i] = 0;
        }
        
        for (int i= WIDTH; i < getElements().size(); i++){
            int line = ((int)getElements().get(i).getY())/UNIT;
            Lines[line]++;
        }
        
        for (int i=0; i < Lines.length; i++){
            if (Lines[i] == WIDTH){
                LinesCompleted.add(i);
            }
        }
        return LinesCompleted;
    }
    
    public void MoveLinesDown(List<Integer> LinesRemoved){
        for(Rectangle r : elements){
           int pos = (int)r.getY()/UNIT;
           int positionsToMove = 0;
           for (int i=0; i<LinesRemoved.size(); i++){
               if (pos < LinesRemoved.get(i)){
                   positionsToMove++;
               }else{
                   break;
               }
           }
           r.setY(r.getY() + positionsToMove*UNIT);
        }
    }
}
