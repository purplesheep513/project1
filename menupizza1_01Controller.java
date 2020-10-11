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
		try {//DB�� ���ڸ޴��� ���� �̸��� ������
			String sql = "SELECT pizza_name FROM pizza_product";
			Connection conn = DBConn.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {//������ �����̸��� ����Ʈ�� �߰�����
				list.add(rs.getString("pizza_name"));
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		Button button1[] = new Button[list.size()];//��ư �迭�� ����� ����Ʈ ������ŭ�� ��ư���� �����Ѵ�
		Button button2[] = new Button[list.size()];
		for(int i=0; i<list.size(); i++) {
			Image image = new Image(getClass().getResource("pizza"+ (i+1) +".png").toString());//���ڻ��� �ҷ�����
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(160);
			imageView.setFitHeight(160);
			Label label1 = new Label(list.get(i).toString());//���� ����� �����̸��� ȭ�鿡 ����Ѵ�
			label1.setStyle("-fx-font-size: 20pt;");
			button1[i] = new Button("��ٱ��Ϸ�");//button1 : ���ڸ� ��ٱ��Ͽ� ��� ��ư
			button1[i].setId(list.get(i).toString());
			button2[i] = new Button("������");//button2 : �̰��� Ŭ���ϸ� �˾����� ������ �������� Ȯ���� �� �ִ�.
			button2[i].setId("btn2_"+i);
			button1[i].setStyle("-fx-background-color: #E6E6E6;"); 
			button2[i].setStyle("-fx-background-color: #E6E6E6;"); 
			button1[i].setOnAction(event -> handleCartAction((Button) event.getSource()));
			button2[i].setOnAction(event -> handleInfoAction((Button) event.getSource()));
			
			scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
			scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			
			anchorPane.getChildren().addAll(imageView);//���� ������ ���
			AnchorPane.setTopAnchor(imageView, (double) (120*i+40));
			AnchorPane.setLeftAnchor(imageView, 50.0);
			
			anchorPane.getChildren().addAll(label1);//���� �̸� ���
			AnchorPane.setTopAnchor(label1, (double) (120*i+50));
			AnchorPane.setLeftAnchor(label1, 235.0);
			
			anchorPane.getChildren().addAll(button1[i]);//��ٱ��Ͽ� ��� ��ư�� ���
			AnchorPane.setTopAnchor(button1[i], (double) (120*i+100));
			AnchorPane.setLeftAnchor(button1[i], 250.0);
		
			anchorPane.getChildren().addAll(button2[i]);//������ �������� Ȯ���ϴ� ��ư�� ���
			AnchorPane.setTopAnchor(button2[i], (double) (120*i+100));
			AnchorPane.setLeftAnchor(button2[i], 335.0);
			}
		
	}
	
	public void handleCartAction (Button button) {//���ڸ� ��ٱ��Ͽ� ������� ���̾�α�
		 for (int i = 0; i <list.size(); i++) {
	            if(button.getId().equals(list.get(i).toString())) {//��ư�̸��� Ư�� �����̸��ϰ�� �Ʒ� ���� �����
	            	
	            	List<Integer> num = new ArrayList<>();//�ֹ� ������ 5�������� ����.
	            	num.add(1);
	            	num.add(2);
	            	num.add(3);
	            	num.add(4);
	            	num.add(5);
	            	
	            	ChoiceDialog<Integer> n = new ChoiceDialog<>(1,num);//
	            	n.setHeaderText(list.get(i).toString()+" ��/�� �ֹ��ϰڽ��ϱ�");//"���� �̸�" �� �ֹ��Ͻðڽ��ϱ�
	            	n.setContentText("����");

	            	Optional<Integer> result = n.showAndWait();//�ֹ��� ������ ������
	            	String pizzaName = list.get(i).toString();
	            	
//	            	System.out.println(n.getSelectedItem());
	            	
	            	int pizzaNum = n.getSelectedItem();
	            	
	            	if(result.isPresent()) {//�ֹ��� ������ �����ϸ� �Ʒ����� �����.
	            		try {
	            			String sql = "SELECT * FROM pizza_product WHERE pizza_name= ?";
	            			Connection conn = DBConn.getConnection();
	            			PreparedStatement pstmt = conn.prepareStatement(sql);
	            			pstmt.setString(1, pizzaName);
	            			
	            			ResultSet rs = pstmt.executeQuery();
	            			if(rs.next()) {
	            				String price = rs.getString("pizza_price");
		            			
		            			try {//������ ���� DB�� ������ ��.
		            				String sql1 = "INSERT INTO pizza_order VALUES(?,?,?)";
		            				Connection conn1 = DBConn.getConnection();
		            				PreparedStatement pstmt1 = conn1.prepareStatement(sql1);
		            				pstmt1.setString(1, pizzaName);
		            				pstmt1.setInt(2, pizzaNum);
		            				pstmt1.setString(3, price);
		            				
		            				int resultPizza = pstmt1.executeUpdate();
//		            				if(resultPizza>=1) {
//		            					System.out.println("����");
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
	
	public void handleInfoAction(Button button) {//�˾�â�� ��� ������ �������� �����Ѵ�.
		for (int i = 0; i <list.size(); i++) {
            if(button.getId().equals("btn2_" + i)) {//�����ϴ� ��ȣ�� Ư�� id���� ���� ��쿡
            	try {
            		String sql = "SELECT pizza_info FROM pizza_product WHERE pizza_name=?";//������ �������� DB���� ������
        			Connection conn = DBConn.getConnection(); 
        			PreparedStatement pstmt = conn.prepareStatement(sql);
        			pstmt.setString(1, list.get(i).toString());
        			ResultSet rs = pstmt.executeQuery();
        			if(rs.next()) {
        				
        				Stage stage = (Stage)button.getScene().getWindow();
						Popup pop = new Popup(); 
						Parent root = FXMLLoader.load(getClass().getResource("popup.fxml"));
						
						final double x = stage.getX() //Ŭ���� ��ġ���� �˾�â�� �� �� �ְ� x,y���� ���Ѵ�.
								+ button.localToScene(0, 0).getX() 
								+ button.getScene().getX() ; 
						final double y = stage.getY() 
								+ button.localToScene(0, 0).getY() 
								+ button.getScene().getY() 
								+ button.getHeight();

						pop.getContent().add(root);
						pop.setAutoHide(true);
						pop.show(stage, x, y);
						Label labInfo = (Label)root.lookup("#labInfo");//�̸� ������ ���� ã�� �������� �����
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
