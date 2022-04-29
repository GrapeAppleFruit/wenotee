package com.example.demo;

import javax.swing.JButton;
import javax.swing.JFrame;
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
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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
            view.getEngine().load(homeAddress);
            
            int width = 32, height = width;
            // back
        	backButton = new Button("<-");
        	backButton.setMinSize(width, height);
        	backButton.setOnMouseClicked(event -> goBack());
        	
        	// home
        	homeButton = new Button("Home");
        	homeButton.setMinSize(width, height);
        	homeButton.setOnMouseClicked(event -> goHome());
        	
            // forward
        	forwardButton = new Button("->");
        	forwardButton.setMinSize(width, height);
        	forwardButton.setOnMouseClicked(event -> goForward());
            
            StackPane stackPane = new StackPane(view, backButton,homeButton, forwardButton);
            
            StackPane.setAlignment(backButton, Pos.TOP_LEFT);
            StackPane.setAlignment(homeButton, Pos.TOP_CENTER);
            StackPane.setAlignment(forwardButton, Pos.TOP_RIGHT);
        	//forwardButton.setLayoutX(width);
            
            panel.setScene(new Scene(stackPane));
//          panel.setScene(new Scene(view));
            
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
    
    
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

}
