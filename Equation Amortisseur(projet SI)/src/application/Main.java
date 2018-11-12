/*******************************************************************************
 * Copyright (C) 2017 ROMAINPC_LECHAT
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Main extends Application {
	
	//données:
	private NumberAxis xAxis = new NumberAxis();
	private NumberAxis yAxis = new NumberAxis();
	private LineChart<Number, Number> lC = new LineChart<Number, Number>(xAxis, yAxis);
	private XYChart.Series<Number, Number> serie = new XYChart.Series<Number, Number>();
	
	//sliders:
	private Slider sldX0;
	private Slider sldCF;
	private Slider sldRR;
	private Slider sldM;
	private Slider precision;
	
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1400,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setMaximized(true);
			
			scene.setFill(Color.BLACK);
			root.setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0,0), null, null)));
			
			
			
			
			
			//le graph:
			lC.prefHeightProperty().bind(scene.heightProperty().divide(1.7));
			lC.setLegendVisible(false);
			
			//Sliders:
			
			//x0
			Label lblX0 = new Label("X0 (m)");
			lblX0.setTextFill(Color.RED); lblX0.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
			sldX0 = new Slider();
			sldX0.setPrefSize(960, 270);
			sldX0.setShowTickMarks(true); sldX0.setShowTickLabels(true);
			sldX0.setMin(0); sldX0.setMax(0.10);
			sldX0.setMajorTickUnit(0.1); sldX0.setMinorTickCount(9);
			sldX0.setBlockIncrement(0.005);
			
			Label valX0 = new Label("null");
			sldX0.valueProperty().addListener(event -> valX0.setText(Double.toString(sldX0.getValue()) + " m"));
			valX0.setTextFill(Color.CYAN); valX0.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
			
			//Coefficient de frottement
			Label lblCF = new Label("Coefficient de frottement (N/m/s)");
			lblCF.setTextFill(Color.RED); lblCF.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
			sldCF =  new Slider();
			sldCF.setPrefSize(960, 270);
			sldCF.setShowTickMarks(true); sldCF.setShowTickLabels(true);
			sldCF.setMin(0); sldCF.setMax(1000);
			sldCF.setMajorTickUnit(100); sldCF.setMinorTickCount(9);
			sldCF.setBlockIncrement(10);
			
			Label valCF = new Label("null");
			sldCF.valueProperty().addListener(event -> valCF.setText(Double.toString(sldCF.getValue()) + " N/m/s"));
			valCF.setTextFill(Color.CYAN); valCF.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
			
			//Raideur du ressort
			Label lblRR = new Label("Raideur du ressort (N/m)");
			lblRR.setTextFill(Color.RED); lblRR.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
			sldRR =  new Slider();
			sldRR.setPrefSize(960, 270);
			sldRR.setShowTickMarks(true); sldRR.setShowTickLabels(true);
			sldRR.setMin(0); sldRR.setMax(100000);
			sldRR.setMajorTickUnit(1000); sldRR.setMinorTickCount(0);
			sldRR.setBlockIncrement(1000);
			
			Label valRR = new Label("null");
			sldRR.valueProperty().addListener(event -> valRR.setText(Double.toString(sldRR.getValue()) + " N/m"));
			valRR.setTextFill(Color.CYAN); valRR.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
			
			//Masse appliquée
			Label lblM = new Label("Masse appliquée (kg)");
			lblM.setTextFill(Color.RED); lblM.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
			sldM = new Slider();
			sldM.setPrefSize(960, 270);
			sldM.setShowTickMarks(true); sldM.setShowTickLabels(true);
			sldM.setMin(0); sldM.setMax(100);
			sldM.setMajorTickUnit(1); sldM.setMinorTickCount(0);
			sldM.setBlockIncrement(1);
			
			Label valM = new Label("null");
			sldM.valueProperty().addListener(event -> valM.setText(Double.toString(sldM.getValue()) + " kg"));
			valM.setTextFill(Color.CYAN); valM.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
			
			sldX0.setValue(0.042); sldCF.setValue(200); sldRR.setValue(23000); sldM.setValue(15);
			
			//précision et actualisation:
			precision = new Slider();
			precision.setPrefSize(960, 270);
			precision.setShowTickMarks(true); precision.setShowTickLabels(true);
			precision.setMin(0); precision.setMax(3);
			precision.setMajorTickUnit(1); precision.setMinorTickCount(0);
			precision.setBlockIncrement(1);
			Label lblP = new Label("Intervalle de précision (s) --> 10^-précision");
			lblP.setTextFill(Color.RED); lblP.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
			Label valP = new Label("null");
			precision.valueProperty().addListener(event -> valP.setText(Double.toString(Math.pow(10, -precision.getValue())) + " s"));
			valP.setTextFill(Color.CYAN); valP.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
			precision.setValue(2);
			
			Button actu = new Button("Rafraîchir");
			actu.setPrefSize(960, 50);
			actu.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
			actu.setTextFill(Color.RED);
			actu.setOnAction((ActionEvent e) -> courbe());
			
			//Assignations des zones:
			GridPane sliders = new GridPane();
			sliders.setHgap(20); sliders.setPadding(new Insets(0, 20, 20, 0));
			
			sliders.add(lblP, 1, 1);
			sliders.add(valP, 1, 2);
			sliders.add(precision, 1, 3);
			
			sliders.add(actu, 2, 1, 1, 3);
			
			sliders.add(sldX0,1, 6);
			sliders.add(lblX0, 1, 4);
			sliders.add(valX0, 1, 5);
			
			sliders.add(sldCF,2, 6);
			sliders.add(lblCF, 2, 4);
			sliders.add(valCF, 2, 5);
			
			sliders.add(sldRR,1, 9);
			sliders.add(lblRR, 1, 7);
			sliders.add(valRR, 1, 8);
			
			sliders.add(sldM,2, 9);
			sliders.add(lblM, 2, 7);
			sliders.add(valM, 2, 8);
			
			root.setTop(lC);
			root.setCenter(sliders);
			
			lC.getData().add(serie);
			courbe();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//tracé de la courbe
	private void courbe(){
		//calcul des données:
		ObservableList<XYChart.Data<Number, Number>> donnee = FXCollections.observableArrayList();
		double formule;
		double lambda;
		double omega;
		double pseudoP;
		for(double t = 0 ; t <= 5 ; t += Math.pow(10, -precision.getValue())){
			lambda = sldCF.getValue() / sldM.getValue() / 2;
			omega = Math.sqrt(sldRR.getValue() / sldM.getValue());
			pseudoP = Math.sqrt(omega * omega - lambda * lambda);
			formule = sldX0.getValue() * Math.exp( - lambda * t) * (Math.cos(pseudoP * t) + (lambda / pseudoP) * Math.sin(pseudoP * t));
			donnee.add(new XYChart.Data<Number, Number>(t, formule));
		}
		
		//entrée des données
		serie.getData().clear();
		for(int i = 0 ; i < donnee.size() ; i++)
			serie.getData().add(donnee.get(i));
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
