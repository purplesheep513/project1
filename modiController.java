package com.koreait.semipro;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class modiController implements Initializable {
	@FXML AnchorPane anchor;
	@FXML TextField txtID;
	@FXML PasswordField oldPass;
	@FXML Button btnLogin;
	@FXML PasswordField pass1, pass2;
	@FXML TextField address, email, oldEmail;
	@FXML Label checkLbl;
	@FXML Button modifyFin;
	boolean loginChk = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLogin.setOnAction(e->loginAgain(e));
		modifyFin.setOnAction(e->handleModify());
	}
	
	public void loginAgain(ActionEvent event) {
		try {
			String sql = "SELECT * FROM pizza_join WHERE join_id = ? AND join_password=password(?)";
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, txtID.getText());
			pstmt.setString(2, oldPass.getText());
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				loginChk = true;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("알림창");
				alert.setContentText("확인되었습니다.");

				alert.showAndWait();
			}else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("알림창");
				alert.setContentText("잘못입력하셨습니다.");

				alert.showAndWait();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleModify() {
		if(loginChk) {
			if(pass1.getText().equals(pass2.getText())) {
			try {
				String sql1 = "UPDATE pizza_join SET join_password = PASSWORD(?), join_address = ?, join_email=? WHERE join_id =?";
				Connection conn1 = DBConn.getConnection();
				PreparedStatement pstmt1 = conn1.prepareStatement(sql1);
				pstmt1.setString(1, pass1.getText());
				pstmt1.setString(2, address.getText());
				pstmt1.setString(3, email.getText());
				pstmt1.setString(4, txtID.getText());
				
				int res = pstmt1.executeUpdate();
				if(res >=1) {
					loginChk = true;
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("알림창");
					alert.setContentText("변경되었습니다.");

					alert.showAndWait();
					
					checkLbl.setText("창을 닫으셔도 됩니다.");
				
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {checkLbl.setText("비밀번호가 일치하지 않습니다.");}
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("알림창");
			alert.setContentText("먼저 아이디와 비밀번호를 확인해주세요.");

			alert.showAndWait();
			checkLbl.setText("오류입니다.");
		}
	}
	
}
