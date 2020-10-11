package com.koreait.semipro;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class menudrink1_01Controller implements Initializable {
	@FXML AnchorPane anchorPane;
	@FXML ScrollPane scroll;
	
	ArrayList<String> list = new ArrayList<String>();

	boolean drinkSort;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			String sql="SELECT drink_name FROM pizza_drink";
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getString("drink_name"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		Button[] button = new Button[list.size()];
		for(int i=0; i<list.size(); i++) {
			Image image = new Image(getClass().getResource("음료"+(i+1)+".png").toString());
			ImageView imageView = new ImageView(image);//음료사진을 불러와 출력해줌
			imageView.setFitWidth(140);
			imageView.setFitHeight(96);
			
			scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
			scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			
			anchorPane.getChildren().addAll(imageView);//음료사진 출력
			AnchorPane.setTopAnchor(imageView, (double) (120*i+30));
			AnchorPane.setLeftAnchor(imageView, 70.0);
			
			Label label = new Label(list.get(i).toString());//음료이름을 출력
			label.setStyle("-fx-font-size: 15pt;");
			anchorPane.getChildren().addAll(label);
			AnchorPane.setTopAnchor(label, (double) (120*i+50));
			AnchorPane.setLeftAnchor(label, 250.0);
			
			button[i] = new Button("장바구니로");
			button[i].setId(list.get(i).toString());
			button[i].setStyle("-fx-background-color: #E6E6E6;"); 
			button[i].setOnAction(event-> handleGetDrinkAction((Button) event.getSource()));
			anchorPane.getChildren().addAll(button[i]);//장바구니에 담는 버튼을 출력
			AnchorPane.setTopAnchor(button[i], (double) (120*i+80));
			AnchorPane.setLeftAnchor(button[i], 250.0);
		}
		
	}

	public void handleGetDrinkAction(Button button) {//장바구니로 이동을 눌렀을때 이벤트
		for(int i=0; i<list.size(); i++) {
			if(button.getId().equals(list.get(i).toString())) {
				try {
					Stage dialog = new Stage(StageStyle.UTILITY);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initOwner(button.getScene().getWindow());
				
					Parent parent = FXMLLoader.load(getClass().getResource("hadlegetdrinkaction.fxml"));
					
					Scene scene = new Scene(parent);
					dialog.setScene(scene);
					dialog.setResizable(false);	
					dialog.show();
					
					ComboBox combo1 = (ComboBox) parent.lookup("#combo1");//fxml파일의 combobox 불러와서 값넣어줌
					ComboBox combo2 = (ComboBox) parent.lookup("#combo2");
					Label lblDrinkCart = (Label) parent.lookup("#lblDrinkCart");
					Button decision = (Button) parent.lookup("#decision");
					
					combo1.getItems().addAll("500ml","1.5L");
					combo1.getSelectionModel().selectFirst();
					combo2.getItems().addAll("1","2","3","4","5");
					combo2.getSelectionModel().selectFirst();
					
					
					String btnId = list.get(i).toString();//스트링에 사고싶은 음료의 이름값 저장해줌
					

					decision.setOnAction(e->{//장바구니에 넣으면 DB에 저장됨.
						try {
							String com1 = combo1.getSelectionModel().getSelectedItem().toString(); 
							//500ml 인지 1.5L인지 선택한 값
							String com2 =combo2.getSelectionModel().getSelectedItem().toString(); 
							//음료개수 선택한 값
							
							String sql1 ="SELECT one_price FROM pizza_drink where drink_name=?";//1.5L를 선택했을때의 가격.
							Connection conn1 = DBConn.getConnection();
							PreparedStatement pstmt1 = conn1.prepareStatement(sql1);
							pstmt1.setString(1, btnId);
							ResultSet rs1 = pstmt1.executeQuery();
							if(rs1.next()) {
							String com15 = rs1.getString("one_price"); //스트링에 1.5리터 특정음료의 가격 저장해줌
							
							if(com1 == "1.5L") {//com1값이 1.5L가 선택되었을경우
								String sql3="INSERT INTO drink_order VALUES(?,?,?,?)";
								Connection conn3 = DBConn.getConnection();
								PreparedStatement pstmt3 = conn3.prepareStatement(sql3);
								pstmt3.setString(1, btnId);
								pstmt3.setString(2, com1);
								pstmt3.setString(3, com2);
								pstmt3.setString(4, com15);
								
								int result = pstmt3.executeUpdate();
//								if(result >=1) {
//								}
							}	
							}
							String sql2 ="SELECT half_price FROM pizza_drink where drink_name=?";
							Connection conn2 = DBConn.getConnection();
							PreparedStatement pstmt2 = conn2.prepareStatement(sql2);
							pstmt2.setString(1, btnId);
							ResultSet rs2 = pstmt2.executeQuery();
							if(rs2.next()) {
							String com500 = rs2.getString("half_price");
							
							if(com1 == "500ml") {
								String sql3="INSERT INTO drink_order VALUES(?,?,?,?)";
								Connection conn3 = DBConn.getConnection();
								PreparedStatement pstmt3 = conn3.prepareStatement(sql3);
								pstmt3.setString(1, btnId);
								pstmt3.setString(2, com1);
								pstmt3.setString(3, com2);
								pstmt3.setString(4, com500);
								int result = pstmt3.executeUpdate();
//								if(result>=1) {
//								}
							}
							}

						}catch(Exception ex) {
							ex.printStackTrace();
						}
						
						
						dialog.close();
					});
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}	
	}
}
