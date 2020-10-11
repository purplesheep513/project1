package com.koreait.semipro;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.koreait.day14.DBConn;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
	
	@FXML private MainController mainController;
	@FXML private Button btnLogin, btnJoin, btnFindID;
	@FXML private TextField txtID;
	@FXML private PasswordField txtPass;
	
	boolean namechkBool;
	boolean addresschkBool;
	boolean emailchkBool = false;
	boolean IDchkBool = false;
	boolean passchkBool = false;
	boolean recommenderchkBool =false;
	boolean findID = false;
	
	ArrayList<MemberDupChk> list = new ArrayList<MemberDupChk>();
	MemberDupChk chk;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLogin.setOnAction(event->handleLoginAction());
		btnJoin.setOnAction(event->handleJoinAction());
		btnFindID.setOnAction(event->handleFindIdPassAction());
		
		
	}
	
	public void handleLoginAction() {//로그인하기
		PreparedStatement pstmt;
		String id =txtID.getText();
		String pass = txtPass.getText();
		String sql="SELECT * FROM pizza_join WHERE join_id = ? AND join_password=password(?)";
		String sql2="INSERT INTO login VALUES (?)";
	        try {
				Connection conn = DBConn.getConnection(); 
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, pass);
				
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					if(!id.equals("admin")) {//로그인이 확인되면 main이 띄워짐.
						
						PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				        pstmt2.setString(1, id);
				        int result = pstmt2.executeUpdate();//로그인한 id 저장
				        
						Stage primartyStage = (Stage) btnLogin.getScene().getWindow();
				        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("main.fxml")));
				        primartyStage.setTitle("맛있는 피자한판?");
				        primartyStage.setScene(scene);
			        
					}else if(id.equals("admin")) {
						Stage primartyStage = (Stage) btnLogin.getScene().getWindow();
				        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("admin.fxml")));
				        primartyStage.setTitle("관리자페이지");
				        primartyStage.setScene(scene);

				        
					}
				}else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("아이디와 비밀번호를 확인해주세요");

					alert.showAndWait();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public void handleFindIdPassAction() {//ID찾기, 비밀번호 변경
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnJoin.getScene().getWindow());
			dialog.setTitle("ID 찾기");
		
			Parent parent = FXMLLoader.load(getClass().getResource("findidpass.fxml"));
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);	
			dialog.show();
			
			TextField findByEmail = (TextField) parent.lookup("#findByEmail");
			Label yourID = (Label) parent.lookup("#yourID");
			Hyperlink changePass = (Hyperlink) parent.lookup("#changePass");
			Hyperlink closeFind = (Hyperlink) parent.lookup("#closeFind");
			Button letsFind = (Button) parent.lookup("#letsFind");
			
			letsFind.setOnAction(event->{//ID 찾기
				String sql = "SELECT join_id FROM pizza_join WHERE join_email=?";
				try {
					Connection conn = DBConn.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, findByEmail.getText());
					ResultSet rs = pstmt.executeQuery();
					if(rs.next()) {
						yourID.setText("회원님의 id는 " + rs.getString("join_id") + " 입니다.");
						findID = true;
					}else {yourID.setText("등록된 이메일이 없습니다.");}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			});
			
			changePass.setOnAction(e->{//비밀번호 변경
				if(findID) {
					try {
						Stage dialog2 = new Stage(StageStyle.UTILITY);
						dialog2.initModality(Modality.WINDOW_MODAL);
						dialog2.initOwner(btnJoin.getScene().getWindow());
						dialog2.setTitle("PASSWORD 변경");
					
						Parent parent2 = FXMLLoader.load(getClass().getResource("changepassword.fxml"));
						
						Scene scene2 = new Scene(parent2);
						dialog2.setScene(scene2);
						dialog2.setResizable(false);	
						dialog2.show();
						
						PasswordField newPass1 = (PasswordField) parent2.lookup("#newPass1");
						PasswordField newPass2 = (PasswordField) parent2.lookup("#newPass2");
						Button btnNewPass = (Button) parent2.lookup("#btnNewPass");
//						Label labelNewPass = (Label) parent.lookup("#labelNewPass");
						
						btnNewPass.setOnAction(ev->{
							String sql = "UPDATE pizza_join SET join_password = PASSWORD(?) WHERE join_email = ?";
							try {
								if(newPass1.getText().equals(newPass2.getText())) {		
									Connection conn = DBConn.getConnection();
									PreparedStatement pstmt = conn.prepareStatement(sql);
									pstmt.setString(1, newPass1.getText());
									pstmt.setString(2, findByEmail.getText());
									int result = pstmt.executeUpdate();
									if(result>=1) {
										yourID.setText("비밀번호 변경 성공.");
										dialog2.close();
									}else {
										yourID.setText("오류발생.");
										dialog2.close();
									}
								}else if(!newPass1.getText().equals(newPass2.getText())) {
									dialog2.close();
									yourID.setText("동일한 비밀번호를 입력하세요.");
								}
							}catch(Exception exc) {
								yourID.setText("동일한 비밀번호를 입력하세요.");
							}
							
						});
						
					}catch(Exception ex) {
						ex.printStackTrace();
					}
				}else {
					yourID.setText("ID찿기를 먼저 진행해주세요.");
				}
				
			});
			
			closeFind.setOnAction(eve->dialog.close());
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleJoinAction() {

		try {//다이얼로그 열어서 join.fxml 파일과 연결
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnJoin.getScene().getWindow());
			dialog.setTitle("회원가입");
		
			Parent parent = FXMLLoader.load(getClass().getResource("join.fxml"));
			
			String sql0 = "SELECT join_id, join_email FROM pizza_join";
			
			Connection conn0 = DBConn.getConnection();
			Statement stmt0 = conn0.createStatement();
			ResultSet rs0 = stmt0.executeQuery(sql0);
			while(rs0.next()) {
				chk = new MemberDupChk(rs0.getString("join_id"),rs0.getString("join_email"));
				list.add(chk);
			}
			
			//아이디 중복체크
			Button chkID = (Button) parent.lookup("#chkID");
			TextField txtFieldId = (TextField) parent.lookup("#txtFieldId");
			Label chkIdLabel = (Label) parent.lookup("#chkIdLabel");
			chkID.setOnAction(e->{ 
				String sql = "SELECT join_id, join_email FROM pizza_join";
				try {
					Connection conn = DBConn.getConnection();
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					if(rs.next()) {
						if(!txtFieldId.getText().isEmpty()){//아이디에 뭔가 입력했을때 이벤트가 일어남			
							for(int i=0; i<list.size(); i++) {//id 중복검사
								if(list.get(i).getID().equals(txtFieldId.getText())) {
									chkIdLabel.setText("중복되는 아이디입니다.");
									break;
								}else {
									chkIdLabel.setText("사용가능한 아이디입니다.");
									IDchkBool = true;
								}
							}
							
						}else {
							chkIdLabel.setText("아이디를 입력하세요.");
						}
					}else {
						chkIdLabel.setText("사용가능한 아이디입니다.");
						IDchkBool = true;
					}
					
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			});
			
			
			//비밀번호 중복체크
			Button chkPassword = (Button) parent.lookup("#chkPassword");
			PasswordField password1 = (PasswordField) parent.lookup("#password1");
			PasswordField password2 = (PasswordField) parent.lookup("#password2");
			Label chkPasswordLabel = (Label) parent.lookup("#chkPasswordLabel");
			chkPassword.setOnAction(e->{
				if(!password1.getText().isEmpty() && !password2.getText().isEmpty()) {
				if(password1.getText().equals(password2.getText())) {//pass1과 pass2의 값이 일치할때
					chkPasswordLabel.setText("일치합니다.");
					passchkBool = true;
				} else {chkPasswordLabel.setText("일치하지 않습니다.");}
				}else {chkPasswordLabel.setText("비밀번호를 입력하세요.");}
			});
			
			//추천인 아이디 DB 존재여부체크
			Button chkRecommender =(Button) parent.lookup("#chkRecommender");
			Label chkRecommenderLabel = (Label) parent.lookup("#chkRecommenderLabel");
			TextField textFieldRecommender = (TextField) parent.lookup("#textFieldRecommender");
			chkRecommender.setOnAction(e->{
				
				try {
					String id = textFieldRecommender.getText();
					Connection conn = DBConn.getConnection();
					Statement stmt = conn.createStatement();
					String sql ="SELECT * FROM pizza_join WHERE join_id = "+"'"+ id +"'";
					ResultSet rs = stmt.executeQuery(sql);
					if(rs.next() ) {
						chkRecommenderLabel.setText("쿠폰이 지급됐습니다.");
						recommenderchkBool = true;
					}else {chkRecommenderLabel.setText("아이디를 찾을 수 없습니다.");}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			});
			
			//이메일 중복체크
			Button chkEmail = (Button) parent.lookup("#chkEmail");
			TextField textFieldEmail = (TextField) parent.lookup("#textFieldEmail");
			Label chkEmailLabel = (Label) parent.lookup("#chkEmailLabel");
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
						for(int i=0; i<list.size(); i++) {
							if(list.get(i).getEmail().equals(textFieldEmail.getText())) {
								chkEmailLabel.setText("이메일이 중복됩니다.");	
								break;
							}else {
								emailchkBool = true;
								chkEmailLabel.setText("확인됐습니다.");	
							}
						}
					}
				}else {chkEmailLabel.setText("이메일을 입력하세요.");}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			});
			
			
			//회원가입 완료
			Button btnJoin_joinpage = (Button) parent.lookup("#btnJoin_joinpage");
			TextField textFieldName = (TextField) parent.lookup("#textFieldName");
			TextField textFieldAddress = (TextField) parent.lookup("#textFieldAddress");
			Label chkJoinLabel = (Label) parent.lookup("#chkJoinLabel");
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
										dialog.close();
										
									}else {System.out.println("오류발생");}
									
								}catch(Exception exc) {exc.printStackTrace();}
							}
							
						}catch(Exception ex) {
							ex.printStackTrace();
						}
						chkJoinLabel.setText("가입되었습니다.");
						dialog.close();
					}else {chkJoinLabel.setText("빠짐없이 입력하세요.");}
					}else {chkJoinLabel.setText("이메일을 확인하세요.");}
				}else {chkJoinLabel.setText("ID 또는 비밀번호를 확인하세요.");}
			});
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);	
			dialog.show();

		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
}
