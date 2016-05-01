/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtetris.jtetris;

import com.jtetris.container.TContainer;
import java.util.Timer;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author rafael
 */
public class TetrisPane extends BorderPane implements EventHandler<KeyEvent> {
    
    private AnchorPane GamePane;
    private VBox GameInfo;
    private TContainer TetrisBox;
    private TetrisEventHandler TetrisHandler;
    private Score score;
    private Label lblPoints;
    
    private boolean isPaused = false;

    public TetrisEventHandler getTetrisHandler() {
        return TetrisHandler;
    }

    public void setTetrisHandler(TetrisEventHandler TetrisHandler) {
        this.TetrisHandler = TetrisHandler;
    }
    
    TetrisPane(){
        
        GameInfo = InitializeGameInfo();
        GamePane = InitializeGameContainer();
        
        this.setCenter(GamePane);
        this.setLeft(GameInfo);
    }
    
    public VBox InitializeGameInfo (){
        
        VBox vbox = new VBox();
        vbox.setPrefSize(150, 600);
        Label lblTitle = new Label("JTetris");
        Label label = new Label("Score");
        
        Font fontTitle = Font.font("Monospace", FontWeight.BOLD, FontPosture.ITALIC, 22);
        Font fontLabel = Font.font("Monospace", FontWeight.LIGHT, FontPosture.REGULAR, 18);
        label.setFont(fontLabel);
        lblTitle.setFont(fontTitle);
        lblPoints = new Label();
        vbox.getChildren().add(lblTitle);
        vbox.getChildren().add(label);
        vbox.getChildren().add(lblPoints);
        return vbox;
    }
    
    public AnchorPane InitializeGameContainer(){
        
        AnchorPane anchor = new AnchorPane();
        TetrisBox = new TContainer();
        TetrisHandler = new TetrisEventHandler();
        score = new Score(0, lblPoints);
        anchor.getChildren().add(TetrisHandler.getFigure().getGroup());
        anchor.getChildren().add(TetrisBox.getGroup());
        anchor.getStyleClass().add("GamePane");
        
        TetrisHandler.setContainer(TetrisBox);
        TetrisHandler.setRoot(anchor);
        TetrisHandler.setScore(score);
   
        return anchor;
    }
    
    public void resume(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(TetrisHandler, 0, 200);
    }
    
    @Override
    public void handle(KeyEvent ke){
        if (CanRotate(ke)){
            TetrisHandler.getFigure().RotateClockWise();
        }
        if (CanGoRight(ke)){
            TetrisHandler.getFigure().MoveRight();
        }
        if (CanGoLeft(ke)){
            TetrisHandler.getFigure().MoveLeft();
        }
        if (CanHardDrop(ke)){
            
            TContainer tc = TetrisHandler.getContainer();
            tc.FastSettle(TetrisHandler.getFigure());
            score.setScoreByHardDrop(TetrisHandler.getFigure());
            TetrisHandler.SettleFigure();
        }
        if (ke.getCode() == KeyCode.SPACE){
            if (!isPaused){
                TetrisHandler.cancel();
                isPaused = true;
                TetrisEventHandler teh = TetrisEventHandler.getTetrisEventHandler(TetrisHandler);
                setTetrisHandler(teh);
            }
            else{
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(TetrisHandler, 0, 200);
                isPaused = false;
            }
        }
    }
    
    public boolean CanRotate(KeyEvent ke){
        return !isPaused && 
               ke.getCode() == KeyCode.D &&
               !TetrisHandler.isGameOver();
    }
    
    public boolean CanGoRight(KeyEvent ke){
        TContainer tc = TetrisHandler.getContainer();
        return !isPaused &&
               ke.getCode() == KeyCode.RIGHT &&
               !TetrisHandler.isGameOver() &&
               tc.CanMoveToRight(TetrisHandler.getFigure());
    }
    
    public boolean CanGoLeft(KeyEvent ke){
        TContainer tc = TetrisHandler.getContainer();
        return !isPaused &&
               ke.getCode() == KeyCode.LEFT && 
               !TetrisHandler.isGameOver() &&
               tc.CanMoveToLeft(TetrisHandler.getFigure());
    }
    
    public boolean CanHardDrop(KeyEvent ke){
        return !isPaused &&
               ke.getCode() == KeyCode.DOWN &&
               !TetrisHandler.isGameOver();
    }
}
