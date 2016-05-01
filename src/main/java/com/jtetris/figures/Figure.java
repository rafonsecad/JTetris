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
    void RotateClockWise();
    void MoveRight ();
    void MoveLeft ();
    void MoveDown (int units);
    List<Rectangle> getCoordinatesDown(int units);
    List<Rectangle> getCoordinatesToRotationCW();
    List<Rectangle> getCoordinatesToRight();
    List<Rectangle> getCoordinatesToLeft();
    List<Rectangle> getComponents();
    Group getGroup();
}
