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
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class Main extends JFrame implements Runnable{

    private WebEngine webEngine;
    private JFXPanel panel;
    private Button backButton, forwardButton;

    public void run() {
        setTitle("Wenote Browser");
        setVisible(true);
        setBounds(0,0,1950, 1080);

        
        // back
    	backButton = new Button("->");
    	backButton.setMaxSize(32, 32);
    	backButton.setOnMouseClicked(event -> goBack());
        // forward
    	forwardButton = new Button("->");
    	forwardButton.setMaxSize(32, 32);
    	forwardButton.setOnMouseClicked(event -> goForward());
        
        
        panel = new JFXPanel();
        add(panel);
       
        
        Platform.runLater(() -> {
            WebView view = new WebView();
            view.getEngine().load("https://www.google.com/");
            
            panel.setScene(new Scene(view));
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
    
    


	public void goBack() {

		final WebHistory history = webEngine.getHistory();
		ObservableList<WebHistory.Entry> entryList = history.getEntries();
		int currentIndex = history.getCurrentIndex();

		Platform.runLater(() -> {
			history.go(entryList.size() > 1 && currentIndex > 0 ? -1 : 0);
		});
	}

	public void goForward() {
		final WebHistory history = webEngine.getHistory();
		ObservableList<WebHistory.Entry> entryList = history.getEntries();
		int currentIndex = history.getCurrentIndex();

		Platform.runLater(() -> {
			history.go(entryList.size() > 1 && currentIndex < entryList.size() - 1 ? 1 : 0);
		});
	}

}
