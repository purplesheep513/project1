package com.koreait.semipro;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.koreait.day14.DBConn;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class menutopping1_01Controller implements Initializable {
	@FXML AnchorPane anchorPane;
	@FXML ScrollPane scroll;
	ArrayList<String> list = new ArrayList<String>();

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {//DB���� �����̸� �ҷ�����
			String sql = "SELECT topping_name FROM pizza_topping";
			Connection conn = DBConn.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {//����Ʈ�� �����̸��� �߰����ش�.
				list.add(rs.getString("topping_name"));
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		Button[] button = new Button[list.size()];//��ٱ��Ͽ� ������ ���� ��ư����.
		
		for(int i=0; i<list.size(); i++) {
			Image image = new Image(getClass().getResource("topping"+(i+1)+".png").toString());
			ImageView imageView = new ImageView(image);//���� ������ �ҷ��� �������
			imageView.setFitWidth(140);
			imageView.setFitHeight(96);		
			
			scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
			scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			
			button[i] = new Button("��ٱ��Ϸ�");
			button[i].setId(list.get(i).toString());
			button[i].setStyle("-fx-background-color: #E6E6E6;"); 
			button[i].setOnAction(event -> handleGetToppingAction((Button) event.getSource()));
			
			anchorPane.getChildren().addAll(imageView);//���λ��� ���
			AnchorPane.setTopAnchor(imageView, (double) (120*i+10));
			AnchorPane.setLeftAnchor(imageView, 70.0);
		
			anchorPane.getChildren().addAll(button[i]);//��ٱ��� ��ư ���
			AnchorPane.setTopAnchor(button[i], (double) (120*i+50));
			AnchorPane.setLeftAnchor(button[i], 270.0);

			}

	}//////

	public void handleGetToppingAction(Button button) {
		for (int i = 0; i <list.size(); i++) {
            if(button.getId().equals(list.get(i).toString())) {
            	List<Integer> num = new ArrayList<>();//�ֹ� ������ 5�������� ����.
            	num.add(1);
            	num.add(2);
            	num.add(3);
            	num.add(4);
            	num.add(5);
            	
            	ChoiceDialog<Integer> n = new ChoiceDialog<>(1,num);//1�� �⺻��
            	n.setHeaderText(list.get(i).toString()+" ��/�� �ֹ��ϰڽ��ϱ�");//"���� �̸�" �� �ֹ��Ͻðڽ��ϱ�
            	n.setContentText("����");

            	Optional<Integer> result = n.showAndWait();//�ֹ��� ������ ������
            	
            	String topping = list.get(i).toString();
            	int toppingNum = n.getSelectedItem();
            	
            	if(result.isPresent()) {//�ֹ��� ������ �����ϸ� �Ʒ����� �����.
            	try {
            		String sql ="SELECT * FROM pizza_topping WHERE topping_name=?";
            		Connection conn = DBConn.getConnection();
            		PreparedStatement pstmt = conn.prepareStatement(sql);
            		pstmt.setString(1, topping);
            		
            		ResultSet rs = pstmt.executeQuery();
            		
            		if(rs.next()) {//toppping ���� ��Ͽ� �߰���.
            			String price = rs.getString("topping_price");
            			try {
            				String sql1="INSERT INTO topping_order VALUES(?,?,?)";
            				Connection conn1 = DBConn.getConnection();
            				PreparedStatement pstmt1 = conn1.prepareStatement(sql1);
            				pstmt1.setString(1, topping);
            				pstmt1.setInt(2, toppingNum);
            				pstmt1.setString(3, price);
            				int resultTopping = pstmt1.executeUpdate();
//            				if(resultTopping>=1){
//            					System.out.println("����");
//            				}
            			}catch(Exception ex) {
            				ex.printStackTrace();
            			}
            		}
            	}catch(Exception e) {
            		e.printStackTrace();
            	}
            	}else {}
            }
		}
	}//////
}
