/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtetris.figures;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author rafael
 */
public interface Figure {
    public void RotateClockWise();
    public void MoveRight ();
    public void MoveLeft ();
    public void MoveDown (int units);
    public List<Rectangle> getCoordinatesDown(int units);
    public List<Rectangle> getCoordinatesToRight();
    public List<Rectangle> getCoordinatesToLeft();
    public List<Rectangle> getComponents();
    public Group getGroup();
}
