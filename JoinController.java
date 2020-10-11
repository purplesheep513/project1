package com.koreait.semipro;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.koreait.day14.DBConn;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class JoinController implements Initializable {
	@FXML private Button chkEmail, btnJoin_joinpage, chkID, chkPassword, chkRecommender;
	@FXML private TextField textFieldName, txtFieldId, textFieldEmail, textFieldAddress, textFieldRecommender;
	@FXML private Label chkIdLabel, chkPasswordLabel, chkEmailLabel, chkRecommenderLabel, chkJoinLabel;
	@FXML private PasswordField password1, password2;
	
	boolean namechkBool;
	boolean addresschkBool;
	boolean emailchkBool = false;
	boolean IDchkBool = false;
	boolean passchkBool = false;
	boolean recommenderchkBool =false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		chkID.setOnAction(e->{ //���̵� �ߺ�üũ 
			String sql = "SELECT join_id FROM pizza_join";
			try {
				Connection conn = DBConn.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				if(!txtFieldId.getText().isEmpty()){//���̵� ���� �Է������� �̺�Ʈ�� �Ͼ
					boolean check = rs.next();
					
				if(rs.next()) {//DB�ȿ� ���� ������

					if(txtFieldId.getText().equals(rs.getString("join_id"))) {
						chkIdLabel.setText("�ߺ��� ���̵��Դϴ�.");
					}else {
						chkIdLabel.setText("��밡���� ���̵��Դϴ�.");
						IDchkBool = true;
						System.out.println(check);
						System.out.println(txtFieldId);
						}
				}else {//DB�ȿ� �ƹ��͵� ������
					chkIdLabel.setText("��밡���� ���̵��Դϴ�.");
					IDchkBool = true;
				}
			}else {chkIdLabel.setText("���̵� �Է��ϼ���.");}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});


		//��й�ȣ �ߺ�üũ
		chkPassword.setOnAction(e->{
			if(!password1.getText().isEmpty() && !password2.getText().isEmpty()) {
			if(password1.getText().equals(password2.getText())) {//pass1�� pass2�� ���� ��ġ�Ҷ�
				chkPasswordLabel.setText("��ġ�մϴ�.");
				passchkBool = true;
			} else {chkPasswordLabel.setText("��ġ���� �ʽ��ϴ�.");}
			}else {chkPasswordLabel.setText("��й�ȣ�� �Է��ϼ���.");}
		});
		
		//��õ�� ���̵� DB ���翩��üũ
		chkRecommender.setOnAction(e->{
			
			try {
				String id = textFieldRecommender.getText();
				Connection conn = DBConn.getConnection();
				Statement stmt = conn.createStatement();
				String sql ="SELECT * FROM pizza_join WHERE join_id = "+"'"+ id +"'";
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next() ) {//������ �ȿ� ���� ������
					chkRecommenderLabel.setText("������ ���޵ƽ��ϴ�.");
					recommenderchkBool = true;
				}else {chkRecommenderLabel.setText("���̵� ã�� �� �����ϴ�.");}//������
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		//�̸��� �ߺ�üũ
		chkEmail.setOnAction(e->{ 
			String sql = "SELECT join_email FROM pizza_join";
			try {
				Connection conn = DBConn.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if(!textFieldEmail.getText().isEmpty()){//�̸��Ͽ� ���� �Է������� �̺�Ʈ�� �Ͼ
				if(rs.next() == false) {//DB�ȿ� �ƹ��͵� ������
					emailchkBool = true;
					chkEmailLabel.setText("Ȯ�εƽ��ϴ�.");	
				}else {//DB�ȿ� ���� ������
					if(textFieldEmail.getText().equals(rs.getString("join_email"))) {
						chkEmailLabel.setText("�̹� ���Ե� �̸����Դϴ�.");
					}else {
						emailchkBool = true;
						chkEmailLabel.setText("Ȯ�εƽ��ϴ�.");
						}
				}
			}else {chkEmailLabel.setText("�̸����� �Է��ϼ���.");}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		
		//ȸ������ �Ϸ�

		btnJoin_joinpage.setOnAction(e->{
			namechkBool = textFieldName.getText().isEmpty();;
			addresschkBool = textFieldAddress.getText().isEmpty();
			String name = textFieldName.getText();
			String id = txtFieldId.getText();
			String password = password1.getText();
			String email = textFieldEmail.getText();
			String address = textFieldAddress.getText();
			String sql2 = "INSERT INTO pizza_join(join_name, join_id, join_password, join_email, join_address,coupon_thirty, coupon_ten) "
					+ "VALUES (?, ?, PASSWORD(?), ?, ?, ?, ?)";
			
			if(IDchkBool && passchkBool) {//id�� ��й�ȣ üũ�� ��ģ���
				if(emailchkBool) {
				if(!addresschkBool && !namechkBool) {// �̸� �ּҸ� �Է��ѻ��
					try {
						if(recommenderchkBool) {//��õ�� �Է�������
							try {
								Connection conn = DBConn.getConnection();
								PreparedStatement pstmt = conn.prepareStatement(sql2);
								pstmt.setString(1, name);
								pstmt.setString(2, id);
								pstmt.setString(3, password);
								pstmt.setString(4, email);
								pstmt.setString(5, address);
								pstmt.setInt(6, 1);
								pstmt.setInt(7, 1);
								
								int result = pstmt.executeUpdate();
								if(result >= 1) {
									System.out.println("���ԿϷ�");
								}else {System.out.println("�����߻�");}
								
							}catch(Exception exc) {exc.printStackTrace();}
						}else if(!recommenderchkBool) {//��õ�� �Է����� �ʾ�����
							try {
								Connection conn = DBConn.getConnection();
								PreparedStatement pstmt = conn.prepareStatement(sql2);
								pstmt.setString(1, name);
								pstmt.setString(2, id);
								pstmt.setString(3, password);
								pstmt.setString(4, email);
								pstmt.setString(5, address);
								pstmt.setInt(6, 0);
								pstmt.setInt(7, 0);
								
								int result = pstmt.executeUpdate();
								if(result >= 1) {
									System.out.println("���ԿϷ�");
									
								}else {System.out.println("�����߻�");}
								
							}catch(Exception exc) {exc.printStackTrace();}
						}
						
					}catch(Exception ex) {
						ex.printStackTrace();
					}
					chkJoinLabel.setText("���ԵǾ����ϴ�.");
					Platform.exit();
				}else {chkJoinLabel.setText("�������� �Է��ϼ���.");}
				}else {chkJoinLabel.setText("�̸����� Ȯ���ϼ���.");}
			}else {chkJoinLabel.setText("ID �Ǵ� ��й�ȣ�� Ȯ���ϼ���.");}
		});
		
	}

	public void handlejoinbtnAction() {
		
	}
	
	
}
