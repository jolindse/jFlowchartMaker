package gui;

import interfaces.iControll;
import interfaces.iObjects;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import symbols.*;

import java.util.ArrayList;
import java.util.List;

public class AppWindow {

    private BorderPane root;
    private ContentArea content;
    private ControllPane controllpane;
    private MainMenu menubar;

    private iControll controll;

    private List<Symbols> selectedElements;
    private List<Connectors> selectedConnectors;
    private ObservableList<Node> elements;
    private boolean isSymbolSelected;

    private String symbolType;
    private Group currSymbol;

    public AppWindow(Stage stage, iControll controll) {
        this.controll = controll;
        root = new BorderPane();
        Scene scene = new Scene(root, 600, 800);

        selectedElements = new ArrayList<>();
        selectedConnectors = new ArrayList<>();

        elements = FXCollections.observableArrayList();

        stage.setScene(scene);
        stage.setMinHeight(800);
        stage.setMinWidth(600);
        stage.show();

        content = new ContentArea(controll);
        controllpane = new ControllPane(controll);
        menubar = new MainMenu(controll);

        root.setTop(menubar);
        root.setCenter(content);
        root.setBottom(controllpane);

        content.addEventHandler(MouseEvent.ANY, (e) -> {
            if(isSymbolSelected) {
                if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
                    addElement(e);

                }
                if (e.getEventType().equals(MouseEvent.MOUSE_MOVED)) {
                    currSymbol.toFront();
                    double x = currSymbol.getLayoutX() + e.getX();
                    double y = currSymbol.getLayoutY() + e.getY();
                    currSymbol.setTranslateX(x);
                    currSymbol.setTranslateY(y);
                }

                if (e.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
                    elements.add(currSymbol);
                }
                if (e.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
                    elements.remove(currSymbol);
                }
            }
        });

        elements.addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
                content.getChildren().clear();
                content.getChildren().addAll(elements);
            }
        });


        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.DELETE) {
                for (Symbols currElement : selectedElements) {
                    for (Connectors currConn : currElement.getConnections()) {
                        currConn.remove(currElement);
                        elements.remove(currConn.getPathReference());
                        elements.remove(currConn);
                    }
                    elements.remove(currElement);
                }
                controll.clearAll();
            }
        });

    }

    // SETTERS FOR CONTROLLER TO UPDATE VIEW

    public void setSelectedElements(List<Symbols> selected) {
        selectedElements = selected;
    }

    public void setSelectedConnectors(List<Connectors> selected) {
        selectedConnectors = selected;
    }

    public void setSymbolType(String type) {
        symbolType = type;
    }

    public void setCurrentSymbol(Group symbol) {
        currSymbol = symbol;
    }

    public void getCurrentSymbol(){
        currSymbol = controllpane.getSymbolForMouse(symbolType);
    }

    public void setSymbolSelected(boolean symbool) {
        isSymbolSelected = symbool;
    }

    // Element controll methods

    public void addElement(MouseEvent e) {
        if (isSymbolSelected) {
            Symbols currElement = null;
            double x = content.getTranslateX() + e.getX();
            double y = content.getTranslateY() + e.getY();
            switch (symbolType) {
                case "process":
                    currElement = new ProcessSymbol(controll);
                    break;
                case "decision":
                    currElement = new DecisionSymbol(controll);
                    break;
                case "terminator":
                    currElement = new TerminatorSymbol(controll);
                    break;
                case "text":
                    currElement = new TextSymbol(controll);
                    break;
            }
            if (currElement != null) {
                currElement.setTranslateX(x);
                currElement.setTranslateY(y);
                elements.add(currElement);
            }
            controllpane.clearSelection();
            controll.clearAll();

        } else {
            controll.clearAll();
        }
        deselectSymbols();
    }

    public void removeElement(iObjects currElement) {
        if (currElement instanceof Symbols) {
            List<Connectors> currConnections = ((Symbols) currElement).getConnections();
            for (Connectors currConn : currConnections) {
                currConn.remove((Symbols) currElement);
            }
        } else if (currElement instanceof Connectors) {
            // remove only connector
        }
        elements.remove(currElement);

    }

    public void moveElement(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            if (e.getSource() instanceof Symbols) {
                Symbols currElement = (Symbols) e.getSource();
                double x = currElement.getTranslateX() + e.getX();
                double y = currElement.getTranslateY() + e.getY();

                currElement.setTranslateX(x);
                currElement.setTranslateY(y);
                if (currElement.isConnected()) {
                    List<Connectors> currConnections = currElement.getConnections();
                    for (Connectors currConn : currConnections) {
                        currConn.update();
                    }
                }
                currElement.toFront();
            }
        }
    }

    public void setElementColor(String type) {
        Color col = controllpane.getColor();
        if (selectedElements.size() > 0) {
            for (Symbols currElement : selectedElements) {
                switch (type) {
                    case "fill":
                        currElement.setFillColor(col);
                        break;
                    case "stroke":
                        currElement.setStrokeColor(col);
                        break;
                }
            }
        }
    }


    public void addConnector() {
		/*
		 * Itterate through list and add for each start and end.
		 */

        int numberOfElements = selectedElements.size();
        int index = 0;

        if (numberOfElements > 1) {

            while (index < numberOfElements - 1) {
                System.out.println("In connector loop number of elements: "+numberOfElements); // TEST
                Symbols start = selectedElements.get(index);
                Symbols end = selectedElements.get(index + 1);

                System.out.println("Start: "+start+" End: "+end); // TEST

                if (start.isConnectable() && end.isConnectable()) {
                    ArrowSymbol connector = new ArrowSymbol(start, end);
                    start.setConnected(connector);
                    end.setConnected(connector);
                    Path arrow = connector.getArrow();
                    connector.setPathReference(arrow);
                    System.out.println("Arrow: "+arrow); // TEST
                    elements.add(arrow);
                }
                index++;
            }
        } else {
            // NOT ENOUGH OBJECTS ERROR
        }
        deselectSymbols();
    }

    private void deselectSymbols() {
        for (Symbols currElement: selectedElements){
            currElement.setDeselected();
        }
    }

    public void removeAll() {
        elements.clear();
    }

}
