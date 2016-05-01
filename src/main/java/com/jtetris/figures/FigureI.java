/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtetris.figures;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author rafael
 */
public class FigureI extends FigureBase implements Figure{
    
    
    public FigureI(){
        List<Rectangle> components = new ArrayList<>();
        
        components.add(new Rectangle(140, 0, 19.75, 19.75));
        components.add(new Rectangle(140, 20, 19.75, 19.75));
        components.add(new Rectangle(140, 40, 19.75, 19.75));
        components.add(new Rectangle(140, 60, 19.75, 19.75));

        this.setComponents(components);
        
        Group group = new Group();
        
        for (Rectangle rec : components){
            rec.setStyle("-fx-fill:cyan");
            group.getChildren().add(rec);
        }
        
        this.setGroup(group);
    }
    
}
