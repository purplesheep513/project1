package com.koreait.semipro;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {
	@FXML MenuItem menu_pizza, menu_topping, menu_drink, cart, modifyInfo;
	@FXML Button btntest, btn1,btn2,btn3,btn4,btn5, btnLogout, btnClose;


	private ObservableList<Order> list;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menu_pizza.setOnAction(e->handleMenuPizzaAction(e));
		menu_topping.setOnAction(e->handleMenuToppingAction(e));
		menu_drink.setOnAction(e->handleMenuDrinkAction(e));
		modifyInfo.setOnAction(e->handleModifyAction(e));
		cart.setOnAction(e->handleCartAction(e));
		btn1.setOnAction(e->handleMenuPizzaAction(e));
		btn2.setOnAction(e->handleMenuToppingAction(e));
		btn3.setOnAction(e->handleMenuDrinkAction(e));
		btn4.setOnAction(e->handleModifyAction(e));
		btn5.setOnAction(e->handleCartAction(e));
		btnLogout.setOnAction(e->logout(e));
		btnClose.setOnAction(e->close(e));	
	}
	
	public void logout(ActionEvent event) {
		try {
			Stage primartyStage = (Stage) btnLogout.getScene().getWindow();
	         Scene scene = new Scene(FXMLLoader.load(getClass().getResource("login.fxml")));
	         primartyStage.setScene(scene);
	         primartyStage.setTitle("로그인이필요합니다.");
	         primartyStage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(ActionEvent event) {
		Platform.exit();
	}

	public void handleMenuPizzaAction(ActionEvent e) {//메뉴에서 피자를 클릭했을때 뜨는 창
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("메뉴보기");
			Parent root = FXMLLoader.load(getClass().getResource("list.fxml"));
			Scene scene = new Scene(root);
			dialog.setResizable(false);	
			dialog.setScene(scene);
			dialog.show();	
			
			Button btnPizza = (Button) root.lookup("#btnPizza");
			Button btnTopping = (Button) root.lookup("#btnTopping");
			Button btnDrink = (Button) root.lookup("#btnDrink");
			AnchorPane anchorPane =(AnchorPane) root.lookup("#anchorPane");
			Button btnExit = (Button) root.lookup("#btnExit");
			btnExit.setOnAction(ev->{
				dialog.close();
			});
			
			//기본으로 실행되는 것
				btnPizza.setStyle("-fx-background-color: #A4A4A4; ");
				btnTopping.setStyle("-fx-background-color: transparent; ");
				btnDrink.setStyle("-fx-background-color: transparent; ");	
				try {
			         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menupizza1_01.fxml")));
				}catch(Exception exc) {
					exc.printStackTrace();
				}
				
				btnPizza.setOnAction(event->{//피자를 눌렀을때 실행
					btnPizza.setStyle("-fx-background-color: #A4A4A4; ");
					btnTopping.setStyle("-fx-background-color: transparent; ");
					btnDrink.setStyle("-fx-background-color: transparent; ");	
					try {
				         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menupizza1_01.fxml")));
					}catch(Exception exc) {
						exc.printStackTrace();
					}
			});
			
			btnTopping.setOnAction(event->{//토핑을 눌렀을 때 실행
				btnPizza.setStyle("-fx-background-color: transparent; ");
				btnTopping.setStyle("-fx-background-color: #A4A4A4; ");
				btnDrink.setStyle("-fx-background-color: transparent; ");	
				try {
			         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menutopping1_01.fxml")));
				}catch(Exception exc) {
					exc.printStackTrace();
				}
			});
			
			btnDrink.setOnAction(event->{//음료를 눌렀을 때 실행
				btnPizza.setStyle("-fx-background-color: transparent; ");
				btnTopping.setStyle("-fx-background-color: transparent; ");
				btnDrink.setStyle("-fx-background-color: #A4A4A4; ");	
				try {
			         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menudrink1_01.fxml")));
				}catch(Exception exc) {
					exc.printStackTrace();
				}
			});
			
		}catch(Exception ex) {ex.printStackTrace();}
	}
	
	public void handleMenuToppingAction(ActionEvent e) {
		try {//메뉴에서 토핑을 눌렀을때
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("메뉴보기");
			Parent root = FXMLLoader.load(getClass().getResource("list.fxml"));
			Scene scene = new Scene(root);
			dialog.setResizable(false);	
			dialog.setScene(scene);
			dialog.show();	
			
			Button btnPizza = (Button) root.lookup("#btnPizza");
			Button btnTopping = (Button) root.lookup("#btnTopping");
			Button btnDrink = (Button) root.lookup("#btnDrink");
			AnchorPane anchorPane =(AnchorPane) root.lookup("#anchorPane");
			
			Button btnExit = (Button) root.lookup("#btnExit");
			btnExit.setOnAction(ev->{
				dialog.close();
			});
			//기본적으로 실행되는것
			btnPizza.setStyle("-fx-background-color: transparent; ");
			btnTopping.setStyle("-fx-background-color: #A4A4A4; ");
			btnDrink.setStyle("-fx-background-color: transparent; ");	
			try {
		         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menutopping1_01.fxml")));
			}catch(Exception exc) {
				exc.printStackTrace();
			}
			btnPizza.setOnAction(event->{//피자를 눌렀을때 실행
				btnPizza.setStyle("-fx-background-color: #A4A4A4; ");
				btnTopping.setStyle("-fx-background-color: transparent; ");
				btnDrink.setStyle("-fx-background-color: transparent; ");	
				try {
			         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menupizza1_01.fxml")));
				}catch(Exception exc) {
					exc.printStackTrace();
				}
			});


				btnTopping.setOnAction(event->{//토핑을 눌렀을 때 실행
					btnPizza.setStyle("-fx-background-color: transparent; ");
					btnTopping.setStyle("-fx-background-color: #A4A4A4; ");
					btnDrink.setStyle("-fx-background-color: transparent; ");	
					try {
				         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menutopping1_01.fxml")));
					}catch(Exception exc) {
						exc.printStackTrace();
					}
				});
				
			
			btnDrink.setOnAction(event->{//음료를 눌렀을 때 실행
				btnPizza.setStyle("-fx-background-color: transparent; ");
				btnTopping.setStyle("-fx-background-color: transparent; ");
				btnDrink.setStyle("-fx-background-color: #A4A4A4; ");	
				try {
			         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menudrink1_01.fxml")));
				}catch(Exception exc) {
					exc.printStackTrace();
				}
			});
			
		}catch(Exception ex) {ex.printStackTrace();}
	}
	public void handleMenuDrinkAction(ActionEvent e) {
		try {//메뉴에서 음료누르면
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("메뉴보기");
			Parent root = FXMLLoader.load(getClass().getResource("list.fxml"));
			Scene scene = new Scene(root);
			dialog.setResizable(false);	
			dialog.setScene(scene);
			dialog.show();	
			
			Button btnPizza = (Button) root.lookup("#btnPizza");
			Button btnTopping = (Button) root.lookup("#btnTopping");
			Button btnDrink = (Button) root.lookup("#btnDrink");
			AnchorPane anchorPane =(AnchorPane) root.lookup("#anchorPane");
			Button btnExit = (Button) root.lookup("#btnExit");
			btnExit.setOnAction(ev->{
				dialog.close();
			});
			//기본적으로 실행되는것
			btnPizza.setStyle("-fx-background-color: transparent; ");
			btnTopping.setStyle("-fx-background-color: transparent; ");
			btnDrink.setStyle("-fx-background-color: #A4A4A4; ");	
			try {
		         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menudrink1_01.fxml")));
			}catch(Exception exc) {
				exc.printStackTrace();
			}
			
			btnPizza.setOnAction(event->{//피자를 눌렀을때 실행
				btnPizza.setStyle("-fx-background-color: #A4A4A4; ");
				btnTopping.setStyle("-fx-background-color: transparent; ");
				btnDrink.setStyle("-fx-background-color: transparent; ");	
				try {
			         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menupizza1_01.fxml")));
				}catch(Exception exc) {
					exc.printStackTrace();
				}
			});
		
			btnTopping.setOnAction(event->{//토핑을 눌렀을 때 실행
				btnPizza.setStyle("-fx-background-color: transparent; ");
				btnTopping.setStyle("-fx-background-color: #A4A4A4; ");
				btnDrink.setStyle("-fx-background-color: transparent; ");	
				try {
			         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menutopping1_01.fxml")));
				}catch(Exception exc) {
					exc.printStackTrace();
				}
			});
			btnDrink.setOnAction(event->{//음료를 눌렀을 때 실행
				btnPizza.setStyle("-fx-background-color: transparent; ");
				btnTopping.setStyle("-fx-background-color: transparent; ");
				btnDrink.setStyle("-fx-background-color: #A4A4A4; ");	
				try {
			         anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("menudrink1_01.fxml")));
				}catch(Exception exc) {
					exc.printStackTrace();
				}
			});
			
		}catch(Exception ex) {ex.printStackTrace();}
	}
	
	
	public void handleModifyAction(ActionEvent e) {//내정보 수정
		try {Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource("modifymyinfo0.fxml"));
		Scene scene = new Scene(root);
		dialog.setScene(scene);
		dialog.show();	
		}catch(Exception ex) {ex.printStackTrace();}
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	
	
	public void handleCartAction(ActionEvent event) {//장바구니 확인후 주문페이지로.	
		
		list = FXCollections.observableArrayList();
		try {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource("cart.fxml"));
		Scene scene = new Scene(root);
		dialog.setScene(scene);
		dialog.show();	
		Connection conn = DBConn.getConnection();
		
		TableView<Order> tableview = (TableView<Order>) root.lookup("#tableview");
		
		GetId getid;
		ArrayList<GetId> getidlist = new ArrayList<GetId>();
		String sql="SELECT * FROM login";// 사용자가 로그인한 아이디를 불러옴
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {//arraylist에 로그인한 id리스트를 쭉 넣어주고 마지막값을 가져옴.(최근에 로그인한아이디를 불러오기위해)
			getid = new GetId(rs.getString("id"));
			getidlist.add(getid);
		}
		int gl = getidlist.size()-1;
		
		Label lbl10chk = (Label) root.lookup("#lbl10chk");
		Label lbl30chk = (Label) root.lookup("#lbl30chk");
		
		String sql0 = "SELECT coupon_thirty, coupon_ten FROM pizza_join WHERE join_id=?";
		PreparedStatement pstmt0 = conn.prepareStatement(sql0);
		pstmt0.setString(1, getidlist.get(gl).getId());
		ResultSet rs0 = pstmt0.executeQuery();	
		if(rs0.next()) {//쿠폰개수를 세팅해줌
			
			lbl30chk.setText(rs0.getString("coupon_thirty"));
			lbl10chk.setText(rs0.getString("coupon_ten"));
		}
		
		RadioButton rb30 = (RadioButton) root.lookup(("#rb30"));
		RadioButton rb10 = (RadioButton) root.lookup(("#rb10"));
		RadioButton rbLater = (RadioButton) root.lookup(("#rbLater"));
		
		String sql1="SELECT * FROM pizza_order";		//피자주문한것 출력
		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
		ResultSet rs1 = pstmt1.executeQuery();
		while(rs1.next()) {
			list.add(new Order(//list에 추가
					rs1.getString("order_pizza"),
					Integer.parseInt(rs1.getString("order_num")),
					Integer.parseInt(rs1.getString("order_num"))*Integer.parseInt(rs1.getString("order_price"))
					));
		}
		
		String sql2="SELECT * FROM topping_order"; // 토핑주문한것 출력
		PreparedStatement pstmt2 = conn.prepareStatement(sql2);
		ResultSet rs2 = pstmt2.executeQuery();
		while(rs2.next()) {
			list.add(new Order(//list에추가
					rs2.getString("order_topping"),
					Integer.parseInt(rs2.getString("order_num")),
					Integer.parseInt(rs2.getString("order_num"))*Integer.parseInt(rs2.getString("order_price"))
					));
		}
		
		String sql3="SELECT * FROM drink_order"; //음료 주문한것 출력
		PreparedStatement pstmt3 = conn.prepareStatement(sql3);
		ResultSet rs3 = pstmt3.executeQuery();
		while(rs3.next()) {
			list.add(new Order(//list에 추가
					rs3.getString("order_drink") + "/" + rs3.getString("order_sort") ,
					Integer.parseInt(rs3.getString("order_num")),
					Integer.parseInt(rs3.getString("order_price")) * Integer.parseInt(rs3.getString("order_num"))
					));
		}
		
		TableColumn tc = tableview.getColumns().get(0);//테이블칼럼에 값을 넣어준다.
		tc.setCellValueFactory(new PropertyValueFactory("productName"));	
		tc = tableview.getColumns().get(1);
		tc.setCellValueFactory(new PropertyValueFactory("productNum"));		
		tc = tableview.getColumns().get(2);
		tc.setCellValueFactory(new PropertyValueFactory("price"));	
		
		tableview.setItems(list);
		
		Label totalPrice = (Label) root.lookup("#totalPrice");//총액을 나타내는 라벨
		
		int q1=0;
		for(int i=0; i<list.size(); i++) {//총액
			int a = list.get(i).getPrice();
			q1 = q1 + a;
			totalPrice.setText(Integer.toString(q1));
		}	
		
		rb30.setOnAction(e->{
			int q=0;
			if(Integer.parseInt(lbl30chk.getText())>0) {//30퍼센트 쿠폰이 있을때의 총액
				for(int j=0; j<list.size();j++) {
					int f = list.get(j).getPrice();
					q = q + f;
					totalPrice.setText(Integer.toString(q*7/10));
				}
			}else {
				for(int i=0; i<list.size(); i++) {//없을때
					int a = list.get(i).getPrice();
					q = q + a;
					totalPrice.setText(Integer.toString(q));
				}	
			}
		});
		
		rb10.setOnAction(e->{
			int q=0;
			if(Integer.parseInt(lbl10chk.getText())>0) {//10퍼센트 쿠폰이 있을때의 총액
				for(int j=0; j<list.size();j++) {
					int f = list.get(j).getPrice();
					q = q + f;
					totalPrice.setText(Integer.toString(q*9/10));
				}
			}else {
				for(int i=0; i<list.size(); i++) {//없을때
					int a = list.get(i).getPrice();
					q = q + a;
					totalPrice.setText(Integer.toString(q));
				}	
			}
		});
		
		rbLater.setOnAction(e->{//쿠폰을 나중에 사용할때
			int q=0;
			for(int i=0; i<list.size(); i++) {
				int a = list.get(i).getPrice();
				q = q + a;
				totalPrice.setText(Integer.toString(q));
			}	
		});

		Button btnDelCart =(Button) root.lookup("#btnDelCart");
		btnDelCart.setOnAction(e->{//장바구니 안의 상품삭제하기
			int idx = tableview.getSelectionModel().getSelectedIndex();
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("상품삭제");
			alert.setContentText("상품을 삭제하겠습니까?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				list.remove(idx);
				tableview.refresh();
				int total=0;
				for(int i=0; i<list.size(); i++) {//다시 총액을 계산해줘야함
					int a = list.get(i).getPrice();
					total = total + a;
					totalPrice.setText(Integer.toString(total));
			
				}
			} 
		});
		Button btnAllDel = (Button) root.lookup("#btnAllDel");//전체삭제
		btnAllDel.setOnAction(eve->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("상품삭제");
			alert.setContentText("전체삭제합니다.");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				list.remove(0, list.size());
				totalPrice.setText("0");
			try {
				String sql1_1="DELETE FROM pizza_order";
				Connection conn1_1 = DBConn.getConnection();
				PreparedStatement pstmt1_1 = conn1_1.prepareStatement(sql1_1);
					int r1 = pstmt1_1.executeUpdate();

				String sql1_2="DELETE FROM topping_order";
				Connection conn1_2 = DBConn.getConnection();
				PreparedStatement pstmt1_2 = conn1_2.prepareStatement(sql1_2);
					int r2 = pstmt1_2.executeUpdate();

				String sql1_3="DELETE FROM drink_order";
				Connection conn1_3 = DBConn.getConnection();
				PreparedStatement pstmt1_3 = conn1_3.prepareStatement(sql1_3);
					int r3 = pstmt1_3.executeUpdate();

					tableview.refresh();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});


		
		Button letsOrder = (Button) root.lookup("#letsOrder");//주문하기 버튼 눌렀을때.
		letsOrder.setOnAction(ev->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("상품삭제");
			alert.setContentText("총액"+ totalPrice.getText() +"을 주문하겠습니까?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					Stage stage = new Stage(StageStyle.UTILITY);
					stage.initModality(Modality.WINDOW_MODAL);
					Parent root1 = FXMLLoader.load(getClass().getResource("finish.fxml"));
					Scene scene1 = new Scene(root1);
					stage.setResizable(false);	
					stage.setScene(scene1);
					stage.show();
					
					dialog.close();
					
					Button btnFinal = (Button) root1.lookup("#btnFinal");
					btnFinal.setOnAction(eve->{
						stage.close();
						try {
						String sql1_1="DELETE FROM pizza_order";
						Connection conn1 = DBConn.getConnection();
						PreparedStatement pstmt1_1 = conn.prepareStatement(sql1_1);
							int r1 = pstmt1_1.executeUpdate();

						String sql1_2="DELETE FROM topping_order";
						PreparedStatement pstmt1_2 = conn1.prepareStatement(sql1_2);
							int r2 = pstmt1_2.executeUpdate();

						String sql1_3="DELETE FROM drink_order";
						PreparedStatement pstmt1_3 = conn1.prepareStatement(sql1_3);
							int r3 = pstmt1_3.executeUpdate();

						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					});
					
					
				}catch(Exception exc) {}
			}
		});
		
		}catch(Exception ex) {ex.printStackTrace();}
	}
}
