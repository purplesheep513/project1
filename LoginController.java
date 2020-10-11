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
	
	public void handleLoginAction() {//�α����ϱ�
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
					if(!id.equals("admin")) {//�α����� Ȯ�εǸ� main�� �����.
						
						PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				        pstmt2.setString(1, id);
				        int result = pstmt2.executeUpdate();//�α����� id ����
				        
						Stage primartyStage = (Stage) btnLogin.getScene().getWindow();
				        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("main.fxml")));
				        primartyStage.setTitle("���ִ� ��������?");
				        primartyStage.setScene(scene);
			        
					}else if(id.equals("admin")) {
						Stage primartyStage = (Stage) btnLogin.getScene().getWindow();
				        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("admin.fxml")));
				        primartyStage.setTitle("������������");
				        primartyStage.setScene(scene);

				        
					}
				}else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("���̵�� ��й�ȣ�� Ȯ�����ּ���");

					alert.showAndWait();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public void handleFindIdPassAction() {//IDã��, ��й�ȣ ����
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnJoin.getScene().getWindow());
			dialog.setTitle("ID ã��");
		
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
			
			letsFind.setOnAction(event->{//ID ã��
				String sql = "SELECT join_id FROM pizza_join WHERE join_email=?";
				try {
					Connection conn = DBConn.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, findByEmail.getText());
					ResultSet rs = pstmt.executeQuery();
					if(rs.next()) {
						yourID.setText("ȸ������ id�� " + rs.getString("join_id") + " �Դϴ�.");
						findID = true;
					}else {yourID.setText("��ϵ� �̸����� �����ϴ�.");}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			});
			
			changePass.setOnAction(e->{//��й�ȣ ����
				if(findID) {
					try {
						Stage dialog2 = new Stage(StageStyle.UTILITY);
						dialog2.initModality(Modality.WINDOW_MODAL);
						dialog2.initOwner(btnJoin.getScene().getWindow());
						dialog2.setTitle("PASSWORD ����");
					
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
										yourID.setText("��й�ȣ ���� ����.");
										dialog2.close();
									}else {
										yourID.setText("�����߻�.");
										dialog2.close();
									}
								}else if(!newPass1.getText().equals(newPass2.getText())) {
									dialog2.close();
									yourID.setText("������ ��й�ȣ�� �Է��ϼ���.");
								}
							}catch(Exception exc) {
								yourID.setText("������ ��й�ȣ�� �Է��ϼ���.");
							}
							
						});
						
					}catch(Exception ex) {
						ex.printStackTrace();
					}
				}else {
					yourID.setText("ID�O�⸦ ���� �������ּ���.");
				}
				
			});
			
			closeFind.setOnAction(eve->dialog.close());
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleJoinAction() {

		try {//���̾�α� ��� join.fxml ���ϰ� ����
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnJoin.getScene().getWindow());
			dialog.setTitle("ȸ������");
		
			Parent parent = FXMLLoader.load(getClass().getResource("join.fxml"));
			
			String sql0 = "SELECT join_id, join_email FROM pizza_join";
			
			Connection conn0 = DBConn.getConnection();
			Statement stmt0 = conn0.createStatement();
			ResultSet rs0 = stmt0.executeQuery(sql0);
			while(rs0.next()) {
				chk = new MemberDupChk(rs0.getString("join_id"),rs0.getString("join_email"));
				list.add(chk);
			}
			
			//���̵� �ߺ�üũ
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
						if(!txtFieldId.getText().isEmpty()){//���̵� ���� �Է������� �̺�Ʈ�� �Ͼ			
							for(int i=0; i<list.size(); i++) {//id �ߺ��˻�
								if(list.get(i).getID().equals(txtFieldId.getText())) {
									chkIdLabel.setText("�ߺ��Ǵ� ���̵��Դϴ�.");
									break;
								}else {
									chkIdLabel.setText("��밡���� ���̵��Դϴ�.");
									IDchkBool = true;
								}
							}
							
						}else {
							chkIdLabel.setText("���̵� �Է��ϼ���.");
						}
					}else {
						chkIdLabel.setText("��밡���� ���̵��Դϴ�.");
						IDchkBool = true;
					}
					
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			});
			
			
			//��й�ȣ �ߺ�üũ
			Button chkPassword = (Button) parent.lookup("#chkPassword");
			PasswordField password1 = (PasswordField) parent.lookup("#password1");
			PasswordField password2 = (PasswordField) parent.lookup("#password2");
			Label chkPasswordLabel = (Label) parent.lookup("#chkPasswordLabel");
			chkPassword.setOnAction(e->{
				if(!password1.getText().isEmpty() && !password2.getText().isEmpty()) {
				if(password1.getText().equals(password2.getText())) {//pass1�� pass2�� ���� ��ġ�Ҷ�
					chkPasswordLabel.setText("��ġ�մϴ�.");
					passchkBool = true;
				} else {chkPasswordLabel.setText("��ġ���� �ʽ��ϴ�.");}
				}else {chkPasswordLabel.setText("��й�ȣ�� �Է��ϼ���.");}
			});
			
			//��õ�� ���̵� DB ���翩��üũ
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
						chkRecommenderLabel.setText("������ ���޵ƽ��ϴ�.");
						recommenderchkBool = true;
					}else {chkRecommenderLabel.setText("���̵� ã�� �� �����ϴ�.");}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			});
			
			//�̸��� �ߺ�üũ
			Button chkEmail = (Button) parent.lookup("#chkEmail");
			TextField textFieldEmail = (TextField) parent.lookup("#textFieldEmail");
			Label chkEmailLabel = (Label) parent.lookup("#chkEmailLabel");
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
						for(int i=0; i<list.size(); i++) {
							if(list.get(i).getEmail().equals(textFieldEmail.getText())) {
								chkEmailLabel.setText("�̸����� �ߺ��˴ϴ�.");	
								break;
							}else {
								emailchkBool = true;
								chkEmailLabel.setText("Ȯ�εƽ��ϴ�.");	
							}
						}
					}
				}else {chkEmailLabel.setText("�̸����� �Է��ϼ���.");}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			});
			
			
			//ȸ������ �Ϸ�
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
										dialog.close();
										
									}else {System.out.println("�����߻�");}
									
								}catch(Exception exc) {exc.printStackTrace();}
							}
							
						}catch(Exception ex) {
							ex.printStackTrace();
						}
						chkJoinLabel.setText("���ԵǾ����ϴ�.");
						dialog.close();
					}else {chkJoinLabel.setText("�������� �Է��ϼ���.");}
					}else {chkJoinLabel.setText("�̸����� Ȯ���ϼ���.");}
				}else {chkJoinLabel.setText("ID �Ǵ� ��й�ȣ�� Ȯ���ϼ���.");}
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
