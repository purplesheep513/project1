package com.koreait.semipro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("로그인이 필요합니다");
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setWidth(750);
		primaryStage.setHeight(500);
		primaryStage.setResizable(false);
		primaryStage.show();


	}

	public static void main(String[] args) {
		launch(args);
	}

}
