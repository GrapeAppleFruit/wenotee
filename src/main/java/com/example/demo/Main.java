package com.example.demo;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Painter;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.DimensionUIResource;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class Main extends JFrame implements Runnable{

    private WebView view;
    private JFXPanel panel;
    private Button backButton,homeButton, forwardButton;
    
    private final String homeAddress = "https://www.google.com/";

    public void run() {
        setTitle("Wenote Browser");
        setVisible(true);
        setBounds(0,0,1950, 1080);

        panel = new JFXPanel();
        add(panel);
       
        
        Platform.runLater(() -> {
            view = new WebView();
            
            // loading home
            view.getEngine().load(homeAddress);
            
            int buttonMinWidth = 32, buttonMinHeight = 24;
            
            // back button
        	backButton = new Button("<-");
        	backButton.setOnMouseClicked(event -> goBack());
        	backButton.setPrefSize(buttonMinWidth, buttonMinHeight);
        	
        	// home button
        	homeButton = new Button("Home");
        	homeButton.setOnMouseClicked(event -> goHome());
        	homeButton.setPrefSize(buttonMinWidth * 2, buttonMinHeight);
        	
            // forward button
        	forwardButton = new Button("->");
        	forwardButton.setOnMouseClicked(event -> goForward());
        	forwardButton.setPrefSize(buttonMinWidth, buttonMinHeight);
            
        	// button container
        	HBox buttonContainer = new HBox(backButton, homeButton, forwardButton);
        	int spacing = 2;
        	//buttonContainer.setBackground(Background.fill(Paint.valueOf("red")));
        	buttonContainer.setMaxSize(
        			backButton.getPrefWidth() + forwardButton.getPrefWidth() + homeButton.getPrefWidth(), 
        			buttonMinHeight + spacing);
        	
        	// stack pane containing browser & buttons
            StackPane stackPane = new StackPane(view, buttonContainer);
            StackPane.setAlignment(buttonContainer, Pos.TOP_LEFT);
            
            
            // note pad pane
            StackPane notePadPane = createNotePadPane();
            
            // split pane which fills the scene
        	SplitPane splitPane = new SplitPane(notePadPane, stackPane);
        	splitPane.setDividerPositions(0.5f);
        	
        	// scene
            panel.setScene(new Scene(splitPane));
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
    
    
    
    // -- browser: back, home, forward buttons --
    
    public void goHome() {
    	view.getEngine().load(homeAddress);
    }

	public void goBack() {

		final WebHistory history = view.getEngine().getHistory();
		ObservableList<WebHistory.Entry> entryList = history.getEntries();
		int currentIndex = history.getCurrentIndex();

		Platform.runLater(() -> {
			history.go(entryList.size() > 1 && currentIndex > 0 ? -1 : 0);
		});
	}

	public void goForward() {
		final WebHistory history = view.getEngine().getHistory();
		ObservableList<WebHistory.Entry> entryList = history.getEntries();
		int currentIndex = history.getCurrentIndex();

		Platform.runLater(() -> {
			history.go(entryList.size() > 1 && currentIndex < entryList.size() - 1 ? 1 : 0);
		});
	}
	
    // -- note pad pane --
	
    public StackPane createNotePadPane() {
    	// placeholder pane
    	return new StackPane();
    }

}
