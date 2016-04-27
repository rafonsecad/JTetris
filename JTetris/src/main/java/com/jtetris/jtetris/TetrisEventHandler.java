/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtetris.jtetris;

import com.jtetris.container.TContainer;
import com.jtetris.figures.Figure;
import com.jtetris.figures.FigureI;
import com.jtetris.figures.FigureJ;
import com.jtetris.figures.FigureL;
import com.jtetris.figures.FigureO;
import com.jtetris.figures.FigureS;
import com.jtetris.figures.FigureT;
import com.jtetris.figures.FigureZ;
import java.util.List;
import java.util.TimerTask;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 *
 * @author rafael
 */
public class TetrisEventHandler extends TimerTask {
    
    private Figure figure;
    private TContainer container;
    private AnchorPane root;
    private Score score;
    private static int recCounter = 0;
    
    public AnchorPane getRoot() {
        return root;
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public TContainer getContainer() {
        return container;
    }

    public void setContainer(TContainer container) {
        this.container = container;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
    
    public TetrisEventHandler(){
        this.figure = getRandomFigure();
    }
    
    public static TetrisEventHandler getTetrisEventHandler(TetrisEventHandler teh){
        TetrisEventHandler t = new TetrisEventHandler();
        t.setContainer(teh.getContainer());
        t.setFigure(teh.getFigure());
        t.setRoot(teh.getRoot());
        t.setScore(teh.getScore());
        return t;
    }
    
    protected void MoveFigureDown (){
        if (!container.IsSettled(figure, 2)){
            figure.MoveDown(2);
        }
        else{

            SettleFigure();
        }
    }

    protected void RemoveLines(List<Rectangle> recs, List<Integer> lines){
        
        if (!recs.isEmpty()){
            FadeNodes(recs, lines);
        }
    }
    
    protected void FadeNodes(List<Rectangle> recs, List<Integer> lines){
        
        final int LastRecNbr = recs.size();
        final List<Rectangle> frecs = recs;
        final List<Integer> linesRemoved = lines;
        
        ProcessNodes(recs, new NodeHandler (){
      
            @Override
            public void Process(Group g, int index){
                FadeTransition ft = new FadeTransition(Duration.millis(250), g.getChildren().get(index));
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                ft.setCycleCount(4);
                ft.setAutoReverse(true);
                ft.play();
                
                ft.setOnFinished(new EventHandler<ActionEvent> (){
                    @Override
                    public void handle(ActionEvent Event){
                        recCounter++;
                        if (recCounter == LastRecNbr){
                            RemoveNodes(frecs);
                            container.MoveLinesDown(linesRemoved);
                            recCounter= 0;
                        }
                    }
                });
            }
        });
    }
    
    protected void RemoveNodes(List<Rectangle> recs){
        ProcessNodes(recs, new NodeHandler (){
            @Override
            public void Process(Group g, int index){
                g.getChildren().remove(index);
            }
        });
    }
    
    protected void ProcessNodes(List<Rectangle> recs, NodeHandler nh){
        
        for (int i=0; i < recs.size(); i++){
            for (int k=0; k < root.getChildren().size(); k++){
                if (root.getChildren().get(k) instanceof Group){
                    Group g = (Group) root.getChildren().get(k);
                    for (int j=0; j< g.getChildren().size(); j++){
                        if (g.getChildren().get(j) instanceof Rectangle){
                            Rectangle r = (Rectangle) g.getChildren().get(j);

                            if (doRectanglesMatch(r, recs.get(i))){
                                nh.Process(g, j);
                                break;
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public interface NodeHandler{
        public void Process(Group g, int index);
    }
 
    @Override
    public void run(){
         Platform.runLater(new Runnable(){
             @Override
             public void run(){
                MoveFigureDown();
             }
         });
    }
   
    protected void SettleFigure(){
        
        root.getChildren().remove(figure.getGroup());
        root.getChildren().add(container.AddFigure(figure));
        
        List<Integer> lines = container.getLines();
        score.setScoreByLines(lines);
        List<Rectangle> RecsToRemove = container.RemoveLines(lines);
        RemoveLines(RecsToRemove, lines);
        
        if (!isGameOver()){
            figure = getRandomFigure();
            root.getChildren().add(figure.getGroup());
        }
        else{
            showGameOver();
        }
    }
    
    private void showGameOver(){
        
        this.cancel();
        Label GameOverLabel = new Label("Game Over");
        Font fontTitle = Font.font("Monospace", FontWeight.BOLD, FontPosture.ITALIC, 26);
        GameOverLabel.setFont(fontTitle);
        root.getChildren().add(GameOverLabel);
    }
    
    public boolean isGameOver(){
        return container.isContainerFull();
    }
    
    private boolean doRectanglesMatch(Rectangle r1, Rectangle r2){
        
        return r1.getX() == r2.getX()          &&
               r1.getY() == r2.getY()          &&
               r1.getWidth() == r2.getWidth()  &&
               r1.getHeight() == r2.getHeight();
    }
    
    protected Figure getRandomFigure(){
        Figure f;
        int RandomFigure = (int )(Math.random() * 7 + 1);
        
        switch (RandomFigure){
            case 1:
                f = new FigureO();
                break;
            case 2:
                f = new FigureT();
                break;
            case 3:
                f = new FigureZ();
                break;
            case 4:
                f = new FigureI();
                break;
            case 5:
                f = new FigureJ();
                break;
            case 6:
                f = new FigureL();
                break;
            case 7:
                f = new FigureS();
                break;
            default:
                f = new FigureO();
                break;
        }
        return f;
    }
    
}
