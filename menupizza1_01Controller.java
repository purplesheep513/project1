package com.koreait.semipro;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class menupizza1_01Controller implements Initializable {
	@FXML AnchorPane anchorPane;
	@FXML ScrollPane scroll;
	
	ArrayList<String> list = new ArrayList<String>();
	

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {//DB의 피자메뉴중 피자 이름을 가져옴
			String sql = "SELECT pizza_name FROM pizza_product";
			Connection conn = DBConn.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {//가져온 피자이름을 리스트에 추가해줌
				list.add(rs.getString("pizza_name"));
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		Button button1[] = new Button[list.size()];//버튼 배열을 만들어 리스트 갯수만큼의 버튼들을 생성한다
		Button button2[] = new Button[list.size()];
		for(int i=0; i<list.size(); i++) {
			Image image = new Image(getClass().getResource("pizza"+ (i+1) +".png").toString());//피자사진 불러오기
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(160);
			imageView.setFitHeight(160);
			Label label1 = new Label(list.get(i).toString());//라벨을 만들어 피자이름을 화면에 출력한다
			label1.setStyle("-fx-font-size: 20pt;");
			button1[i] = new Button("장바구니로");//button1 : 피자를 장바구니에 담는 버튼
			button1[i].setId(list.get(i).toString());
			button2[i] = new Button("상세정보");//button2 : 이것을 클릭하면 팝업으로 피자의 상세정보를 확인할 수 있다.
			button2[i].setId("btn2_"+i);
			button1[i].setStyle("-fx-background-color: #E6E6E6;"); 
			button2[i].setStyle("-fx-background-color: #E6E6E6;"); 
			button1[i].setOnAction(event -> handleCartAction((Button) event.getSource()));
			button2[i].setOnAction(event -> handleInfoAction((Button) event.getSource()));
			
			scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
			scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			
			anchorPane.getChildren().addAll(imageView);//피자 사진을 출력
			AnchorPane.setTopAnchor(imageView, (double) (120*i+40));
			AnchorPane.setLeftAnchor(imageView, 50.0);
			
			anchorPane.getChildren().addAll(label1);//피자 이름 출력
			AnchorPane.setTopAnchor(label1, (double) (120*i+50));
			AnchorPane.setLeftAnchor(label1, 235.0);
			
			anchorPane.getChildren().addAll(button1[i]);//장바구니에 담는 버튼을 출력
			AnchorPane.setTopAnchor(button1[i], (double) (120*i+100));
			AnchorPane.setLeftAnchor(button1[i], 250.0);
		
			anchorPane.getChildren().addAll(button2[i]);//피자의 상세정보를 확인하는 버튼을 출력
			AnchorPane.setTopAnchor(button2[i], (double) (120*i+100));
			AnchorPane.setLeftAnchor(button2[i], 335.0);
			}
		
	}
	
	public void handleCartAction (Button button) {//피자를 장바구니에 담기위한 다이얼로그
		 for (int i = 0; i <list.size(); i++) {
	            if(button.getId().equals(list.get(i).toString())) {//버튼이름이 특정 피자이름일경우 아래 식이 실행됨
	            	
	            	List<Integer> num = new ArrayList<>();//주문 개수는 5개까지로 제한.
	            	num.add(1);
	            	num.add(2);
	            	num.add(3);
	            	num.add(4);
	            	num.add(5);
	            	
	            	ChoiceDialog<Integer> n = new ChoiceDialog<>(1,num);//
	            	n.setHeaderText(list.get(i).toString()+" 을/를 주문하겠습니까");//"피자 이름" 을 주문하시겠습니까
	            	n.setContentText("개수");

	            	Optional<Integer> result = n.showAndWait();//주문한 개수를 리턴함
	            	String pizzaName = list.get(i).toString();
	            	
//	            	System.out.println(n.getSelectedItem());
	            	
	            	int pizzaNum = n.getSelectedItem();
	            	
	            	if(result.isPresent()) {//주문한 개수가 존재하면 아래식이 실행됨.
	            		try {
	            			String sql = "SELECT * FROM pizza_product WHERE pizza_name= ?";
	            			Connection conn = DBConn.getConnection();
	            			PreparedStatement pstmt = conn.prepareStatement(sql);
	            			pstmt.setString(1, pizzaName);
	            			
	            			ResultSet rs = pstmt.executeQuery();
	            			if(rs.next()) {
	            				String price = rs.getString("pizza_price");
		            			
		            			try {//오더한 피자 DB에 저장이 됨.
		            				String sql1 = "INSERT INTO pizza_order VALUES(?,?,?)";
		            				Connection conn1 = DBConn.getConnection();
		            				PreparedStatement pstmt1 = conn1.prepareStatement(sql1);
		            				pstmt1.setString(1, pizzaName);
		            				pstmt1.setInt(2, pizzaNum);
		            				pstmt1.setString(3, price);
		            				
		            				int resultPizza = pstmt1.executeUpdate();
//		            				if(resultPizza>=1) {
//		            					System.out.println("성공");
//		            				}
		            			}catch(Exception e) {
		            				e.printStackTrace();
		            			}
	            			}
	            		}catch(Exception ex) {
	            			ex.printStackTrace();
	            		}
	            	}else {}
	            }
	        }
		}
	
	public void handleInfoAction(Button button) {//팝업창을 띄워 피자의 상세정보를 제공한다.
		for (int i = 0; i <list.size(); i++) {
            if(button.getId().equals("btn2_" + i)) {//선택하는 번호가 특정 id값을 가질 경우에
            	try {
            		String sql = "SELECT pizza_info FROM pizza_product WHERE pizza_name=?";//피자의 상세정보를 DB에서 가져옴
        			Connection conn = DBConn.getConnection(); 
        			PreparedStatement pstmt = conn.prepareStatement(sql);
        			pstmt.setString(1, list.get(i).toString());
        			ResultSet rs = pstmt.executeQuery();
        			if(rs.next()) {
        				
        				Stage stage = (Stage)button.getScene().getWindow();
						Popup pop = new Popup(); 
						Parent root = FXMLLoader.load(getClass().getResource("popup.fxml"));
						
						final double x = stage.getX() //클릭한 위치에서 팝업창이 뜰 수 있게 x,y값을 정한다.
								+ button.localToScene(0, 0).getX() 
								+ button.getScene().getX() ; 
						final double y = stage.getY() 
								+ button.localToScene(0, 0).getY() 
								+ button.getScene().getY() 
								+ button.getHeight();

						pop.getContent().add(root);
						pop.setAutoHide(true);
						pop.show(stage, x, y);
						Label labInfo = (Label)root.lookup("#labInfo");//미리 만들어둔 라벨을 찾아 상세정보를 출력함
						labInfo.setWrapText(true);
						labInfo.setText(rs.getString("pizza_info"));
						
        			}else System.out.println(list.get(i).toString());
            	}catch(Exception e) {
            		e.printStackTrace();
            	}
            }
        }
	}
}
