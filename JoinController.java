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
		
		chkID.setOnAction(e->{ //아이디 중복체크 
			String sql = "SELECT join_id FROM pizza_join";
			try {
				Connection conn = DBConn.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				if(!txtFieldId.getText().isEmpty()){//아이디에 뭔가 입력했을때 이벤트가 일어남
					boolean check = rs.next();
					
				if(rs.next()) {//DB안에 뭔가 있을때

					if(txtFieldId.getText().equals(rs.getString("join_id"))) {
						chkIdLabel.setText("중복된 아이디입니다.");
					}else {
						chkIdLabel.setText("사용가능한 아이디입니다.");
						IDchkBool = true;
						System.out.println(check);
						System.out.println(txtFieldId);
						}
				}else {//DB안에 아무것도 없을때
					chkIdLabel.setText("사용가능한 아이디입니다.");
					IDchkBool = true;
				}
			}else {chkIdLabel.setText("아이디를 입력하세요.");}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});


		//비밀번호 중복체크
		chkPassword.setOnAction(e->{
			if(!password1.getText().isEmpty() && !password2.getText().isEmpty()) {
			if(password1.getText().equals(password2.getText())) {//pass1과 pass2의 값이 일치할때
				chkPasswordLabel.setText("일치합니다.");
				passchkBool = true;
			} else {chkPasswordLabel.setText("일치하지 않습니다.");}
			}else {chkPasswordLabel.setText("비밀번호를 입력하세요.");}
		});
		
		//추천인 아이디 DB 존재여부체크
		chkRecommender.setOnAction(e->{
			
			try {
				String id = textFieldRecommender.getText();
				Connection conn = DBConn.getConnection();
				Statement stmt = conn.createStatement();
				String sql ="SELECT * FROM pizza_join WHERE join_id = "+"'"+ id +"'";
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next() ) {//쿼리문 안에 뭔가 있을때
					chkRecommenderLabel.setText("쿠폰이 지급됐습니다.");
					recommenderchkBool = true;
				}else {chkRecommenderLabel.setText("아이디를 찾을 수 없습니다.");}//없을때
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		//이메일 중복체크
		chkEmail.setOnAction(e->{ 
			String sql = "SELECT join_email FROM pizza_join";
			try {
				Connection conn = DBConn.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if(!textFieldEmail.getText().isEmpty()){//이메일에 뭔가 입력했을때 이벤트가 일어남
				if(rs.next() == false) {//DB안에 아무것도 없을때
					emailchkBool = true;
					chkEmailLabel.setText("확인됐습니다.");	
				}else {//DB안에 뭔가 있을때
					if(textFieldEmail.getText().equals(rs.getString("join_email"))) {
						chkEmailLabel.setText("이미 가입된 이메일입니다.");
					}else {
						emailchkBool = true;
						chkEmailLabel.setText("확인됐습니다.");
						}
				}
			}else {chkEmailLabel.setText("이메일을 입력하세요.");}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		
		//회원가입 완료

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
			
			if(IDchkBool && passchkBool) {//id와 비밀번호 체크를 마친사람
				if(emailchkBool) {
				if(!addresschkBool && !namechkBool) {// 이름 주소를 입력한사람
					try {
						if(recommenderchkBool) {//추천인 입력했을때
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
									System.out.println("가입완료");
								}else {System.out.println("오류발생");}
								
							}catch(Exception exc) {exc.printStackTrace();}
						}else if(!recommenderchkBool) {//추천인 입력하지 않았을때
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
									System.out.println("가입완료");
									
								}else {System.out.println("오류발생");}
								
							}catch(Exception exc) {exc.printStackTrace();}
						}
						
					}catch(Exception ex) {
						ex.printStackTrace();
					}
					chkJoinLabel.setText("가입되었습니다.");
					Platform.exit();
				}else {chkJoinLabel.setText("빠짐없이 입력하세요.");}
				}else {chkJoinLabel.setText("이메일을 확인하세요.");}
			}else {chkJoinLabel.setText("ID 또는 비밀번호를 확인하세요.");}
		});
		
	}

	public void handlejoinbtnAction() {
		
	}
	
	
}
