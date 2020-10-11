package com.koreait.semipro;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class adminController implements Initializable {
	@FXML Button btnMemModi, btnMemDel, btnPizzaAdd, btnTopAdd, btnDrinkAdd,
	btnPizzaAddTrue, btnTopAddTrue, btnDrinkAddTrue;
	@FXML TextField txtIdx, txtMem30, txtMem10,
	PizzaPrice, txtPizzaIdx, PizzaInfo, PizzaName, txtTopPrice, txtTopName, txtTopIdx,
	txtDrinkIdx, txtDrinkName, txt500Price, txt15Price, txtPizzaIdxAdd, txtPizzaNameadd,
	txtPizzaInfoAdd, txtPizzaPriceAdd, txtDrink15add, txtDrink500add, txtDrinkNameAdd, txtDrinkIdxAdd,
	txtTopIdxAdd, txtTopNameAdd, txtTopPriceAdd ;
	@FXML TableView<Member> tv1;
	@FXML TableView<PizzaAdmin> tv2;
	@FXML TableView<ToppingAdmin> tv3;
	@FXML TableView<DrinkAdmin> tv4;
	
	private ObservableList<Member> list;
	private ObservableList<PizzaAdmin> list1;
	private ObservableList<ToppingAdmin> list2;
	private ObservableList<DrinkAdmin> list3;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = FXCollections.observableArrayList(); //회원정보를 가져옴
		try {
			String sql = "SELECT * FROM pizza_join";
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new Member(
						rs.getInt("idx"),
						rs.getString("join_name"),
						rs.getString("join_id"),
						rs.getString("join_email"),
						rs.getInt("coupon_thirty"),
						rs.getInt("coupon_ten")
						));
			}
		}catch(Exception e){}
		
		list1 = FXCollections.observableArrayList(); 
		try {//DB에서 피자메뉴 데이터를 불러옴
			String sql = "SELECT * FROM pizza_product";
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list1.add(new PizzaAdmin(
						rs.getInt("pizza_idx"),
						rs.getString("pizza_name"),
						rs.getString("pizza_info"),
						rs.getString("pizza_price")
						));
			}
		}catch(Exception e){}
		
		list2 = FXCollections.observableArrayList();
		try {//DB에서 토핑메뉴 데이터를 불러옴
			String sql = "SELECT * FROM pizza_topping";
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list2.add(new ToppingAdmin(
						rs.getInt("topping_idx"),
						rs.getString("topping_name"),
						rs.getString("topping_price")
						));
			}
		}catch(Exception e){}
				
		list3 = FXCollections.observableArrayList();
		try {//DB에서 음료메뉴 데이터를 불러옴
			String sql = "SELECT drink_idx, drink_name, one_price, half_price FROM pizza_drink";
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list3.add(new DrinkAdmin(
						rs.getInt("drink_idx"),
						rs.getString("drink_name"),
						rs.getString("one_price"),
						rs.getString("half_price")
						));
			}
		}catch(Exception e){}
		
		
		TableColumn tc1 = tv1.getColumns().get(0);//회원정보 수정탭에 회원정보를 출력
		tc1.setCellValueFactory(new PropertyValueFactory("idx"));	
		tc1 = tv1.getColumns().get(1);
		tc1.setCellValueFactory(new PropertyValueFactory("memName"));		
		tc1 = tv1.getColumns().get(2);
		tc1.setCellValueFactory(new PropertyValueFactory("memId"));		
		tc1 = tv1.getColumns().get(3);
		tc1.setCellValueFactory(new PropertyValueFactory("memEmail"));		
		tc1 = tv1.getColumns().get(4);
		tc1.setCellValueFactory(new PropertyValueFactory("coupon30"));		
		tc1 = tv1.getColumns().get(5);
		tc1.setCellValueFactory(new PropertyValueFactory("coupon10"));
		
		TableColumn tc2 = tv2.getColumns().get(0);//피자메뉴 수정탭에 피자를 출력
		tc2.setCellValueFactory(new PropertyValueFactory("pizza_idx"));		
		tc2 = tv2.getColumns().get(1);
		tc2.setCellValueFactory(new PropertyValueFactory("pizza_name"));		
		tc2 = tv2.getColumns().get(2);
		tc2.setCellValueFactory(new PropertyValueFactory("pizza_info"));		
		tc2 = tv2.getColumns().get(3);
		tc2.setCellValueFactory(new PropertyValueFactory("pizza_price"));
		
		TableColumn tc3 = tv3.getColumns().get(0);//토핑메뉴 수정탭에 토핑 출력
		tc3.setCellValueFactory(new PropertyValueFactory("topping_idx"));		
		tc3 = tv3.getColumns().get(1);
		tc3.setCellValueFactory(new PropertyValueFactory("topping_name"));		
		tc3 = tv3.getColumns().get(2);
		tc3.setCellValueFactory(new PropertyValueFactory("topping_price"));		

		TableColumn tc4 = tv4.getColumns().get(0);//음료메뉴 수정탭에 음료 출력
		tc4.setCellValueFactory(new PropertyValueFactory("drink_idx"));		
		tc4 = tv4.getColumns().get(1);
		tc4.setCellValueFactory(new PropertyValueFactory("drink_name"));			
		tc4 = tv4.getColumns().get(2);
		tc4.setCellValueFactory(new PropertyValueFactory("one_price"));		
		tc4 = tv4.getColumns().get(3);
		tc4.setCellValueFactory(new PropertyValueFactory("half_price"));	
		
		tv1.setItems(list);
		tv2.setItems(list1);
		tv3.setItems(list2);
		tv4.setItems(list3);
		
		btnMemModi.setOnAction(event->handleAdminMemAction(event));
		btnMemDel.setOnAction(event->handleDelMemAction(event));
		
		tv2.setOnMouseClicked(event->handleTableViewMouseClicked2(event));
		btnPizzaAdd.setOnAction(event->handleAdminPizzaAction(event));
		
		tv3.setOnMouseClicked(event->handleTableViewMouseClicked3(event));
		btnTopAdd.setOnAction(event->handleAdminTopAction(event));
		
		tv4.setOnMouseClicked(event->handleTableViewMouseClicked4(event));
		btnDrinkAdd.setOnAction(event->handelAdminDrinkAction(event));
		
		btnPizzaAddTrue.setOnAction(event->handleAddPizzaAction(event));
		btnTopAddTrue.setOnAction(event->handleAddTopAction(event));
		btnDrinkAddTrue.setOnAction(event->handleAddDrinkAction(event));
	}
	
	public void handleAddPizzaAction(ActionEvent event) {//피자메뉴 추가
		try {
			String sql = "INSERT INTO pizza_product VALUES(?,?,?,?)";
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(txtPizzaIdxAdd.getText()));
			pstmt.setString(2, txtPizzaNameadd.getText());
			pstmt.setString(3, txtPizzaInfoAdd.getText());
			pstmt.setString(4, txtPizzaPriceAdd.getText());
			
			int result = pstmt.executeUpdate();
			if(result>=1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Add Pizza");
				alert.setContentText("피자가 추가되었습니다.");
				alert.showAndWait();
				int i = list1.size()-1;
				list1.get(i).setPizza_idx(Integer.parseInt(txtPizzaIdxAdd.getText()));
				list1.get(i).setPizza_name(txtPizzaNameadd.getText());
				list1.get(i).setPizza_info(txtPizzaInfoAdd.getText());
				list1.get(i).setPizza_price(txtPizzaPriceAdd.getText());
				tv2.refresh();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void handleAddTopAction(ActionEvent event) {//토핑메뉴 추가
		try {
			String sql = "INSERT INTO pizza_topping VALUES(?,?,?)";
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(txtTopIdxAdd.getText()));
			pstmt.setString(2, txtTopNameAdd.getText());
			pstmt.setString(3, txtTopPriceAdd.getText());
			int result = pstmt.executeUpdate();
			if(result>=1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Add Topping");
				alert.setContentText("토핑이 추가되었습니다.");
				alert.showAndWait();
				int i= list2.size()-1;
				list2.get(i).setTopping_idx(Integer.parseInt(txtTopIdxAdd.getText()));
				list2.get(i).setTopping_name(txtTopNameAdd.getText());
				list2.get(i).setTopping_price(txtTopPriceAdd.getText());
				tv3.refresh();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleAddDrinkAction(ActionEvent event) {//음료추가
		try {
			String sql = "INSERT INTO pizza_drink VALUES(?,?,?,?)";
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(txtDrinkIdxAdd.getText()));
			pstmt.setString(2, txtDrinkNameAdd.getText());
			pstmt.setString(3, txtDrink15add.getText());
			pstmt.setString(4, txtDrink500add.getText());
			int result = pstmt.executeUpdate();
			if(result>=1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Add Drink");
				alert.setContentText("음료가 추가되었습니다.");
				alert.showAndWait();
				int i =list3.size()-1;
				list3.get(i).setDrink_idx(Integer.parseInt(txtDrinkIdxAdd.getText()));
				list3.get(i).setDrink_name(txtDrinkNameAdd.getText());
				list3.get(i).setOne_price(txtDrink15add.getText());
				list3.get(i).setHalf_price(txtDrink500add.getText());
				tv4.refresh();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void handleDelMemAction(ActionEvent event) {//회원 삭제
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("회원삭제");
		alert.setContentText(tv1.getSelectionModel().getSelectedItem().getMemName() + "님을 삭제하겠습니까?");
		System.out.println();
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				String sql = "DELETE FROM pizza_join WHERE idx=?";
				Connection conn = DBConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, tv1.getSelectionModel().getSelectedItem().getIdx());
				
				int rs = pstmt.executeUpdate();
				
				if(rs>=1) {
					list.remove(tv1.getSelectionModel().getSelectedIndex());
				}
			}catch(Exception e) {}
		} else {
		    
		}
	}
	
	public void handleAdminMemAction(ActionEvent event) { // 멤버 쿠폰 수 수정
		String sql1 = "UPDATE pizza_join SET coupon_thirty = ?, coupon_ten = ?  WHERE idx = ?";
		try {
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, Integer.parseInt(txtMem30.getText()));
			pstmt.setInt(2, Integer.parseInt(txtMem10.getText()));
			pstmt.setInt(3, tv1.getSelectionModel().getSelectedItem().getIdx());
			
			int result = pstmt.executeUpdate();
			if(result>=1) {
				list.get(tv1.getSelectionModel().getSelectedIndex()).setCoupon30(Integer.parseInt(txtMem30.getText()));
				list.get(tv1.getSelectionModel().getSelectedIndex()).setCoupon10(Integer.parseInt(txtMem10.getText()));
				tv1.refresh();
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("실패.");
				alert.showAndWait();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleTableViewMouseClicked2(MouseEvent event) {//피자삭제
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("피자삭제");
		alert.setContentText(tv2.getSelectionModel().getSelectedItem().getPizza_name() + " 삭제하겠습니까?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				String sql = "DELETE FROM pizza_product WHERE pizza_idx=?";
				Connection conn = DBConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, tv2.getSelectionModel().getSelectedItem().getPizza_idx());
				
				int rs = pstmt.executeUpdate();
				
				if(rs>=1) {
					list1.remove(tv2.getSelectionModel().getSelectedIndex());
				}
			}catch(Exception e) {}
		} 
	}
	
	public void handleAdminPizzaAction(ActionEvent event) {//피자수정
		
		String sql1 = "UPDATE pizza_product SET pizza_name = ?, pizza_info = ?, pizza_price= ? WHERE pizza_idx = ?";
		try {
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, PizzaName.getText());
			pstmt.setString(2, PizzaInfo.getText());
			pstmt.setString(3, PizzaPrice.getText());
			pstmt.setInt(4, Integer.parseInt(txtPizzaIdx.getText()));
			
			int result = pstmt.executeUpdate();
			if(result>=1) {
				for(int i=0; i<list1.size(); i++) {
					if(list1.get(i).getPizza_idx() == Integer.parseInt(txtPizzaIdx.getText())) {
						list1.get(i).setPizza_name(PizzaName.getText());
						list1.get(i).setPizza_info(PizzaInfo.getText());
						list1.get(i).setPizza_price(PizzaPrice.getText());
					}
				}
				tv2.refresh();
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("실패.");
				alert.showAndWait();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void handleTableViewMouseClicked3(MouseEvent event) {//토핑삭제
		System.out.println(tv3.getSelectionModel().getSelectedIndex());
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("토핑삭제");
		alert.setContentText(tv3.getSelectionModel().getSelectedItem().getTopping_name() + " 삭제하겠습니까?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){		
				try {
					String sql = "DELETE FROM pizza_topping WHERE topping_idx=?";
					Connection conn = DBConn.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, tv3.getSelectionModel().getSelectedItem().getTopping_idx());
					
					int rs = pstmt.executeUpdate();
					
					if(rs>=1) {
						list2.remove(tv3.getSelectionModel().getSelectedIndex());
					}
				}catch(Exception e) {}
			
			
		}
	}
	
	public void handleAdminTopAction(ActionEvent event) {//토핑수정

		String sql1 = "UPDATE pizza_topping SET topping_name = ?, topping_price = ? WHERE topping_idx = ?";
		try {
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, txtTopName.getText());
			pstmt.setString(2, txtTopPrice.getText());
			pstmt.setString(3, txtTopIdx.getText());
			
			int result = pstmt.executeUpdate();
			if(result>=1) {
				for(int i=0; i<list2.size(); i++) {
					if(list2.get(i).getTopping_idx() == Integer.parseInt(txtTopIdx.getText())) {
						list2.get(i).setTopping_name(txtTopName.getText());
						list2.get(i).setTopping_price(txtTopPrice.getText());
					}
				}
				tv3.refresh();
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("실패.");
				alert.showAndWait();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleTableViewMouseClicked4(MouseEvent event) {//음료삭제
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("음료삭제");
		alert.setContentText(tv4.getSelectionModel().getSelectedItem().getDrink_name() + " 삭제하겠습니까?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				String sql = "DELETE FROM pizza_drink WHERE drink_idx=?";
				Connection conn = DBConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, tv4.getSelectionModel().getSelectedItem().getDrink_idx());
				
				int rs = pstmt.executeUpdate();
				
				if(rs>=1) {
					list3.remove(tv4.getSelectionModel().getSelectedIndex());
				}
			}catch(Exception e) {}
		} else {
		    
		}
	}
	
	public void handelAdminDrinkAction(ActionEvent event) {//음료수정
		String sql1 = "UPDATE pizza_drink SET drink_name = ?, one_price = ?, half_price = ? WHERE drink_idx = ?";
		try {
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, txtDrinkName.getText());
			pstmt.setString(2, txt500Price.getText());
			pstmt.setString(3, txt15Price.getText());
			pstmt.setString(4, txtDrinkIdx.getText());
			
			int result = pstmt.executeUpdate();
			if(result>=1) {
				for(int i=0; i<list2.size(); i++) {
					if(list3.get(i).getDrink_idx() == Integer.parseInt(txtDrinkIdx.getText())) {
						list3.get(i).setDrink_name(txtDrinkName.getText());
						list3.get(i).setOne_price(txt500Price.getText());
						list3.get(i).setHalf_price(txt15Price.getText());
					}
				}
				tv4.refresh();
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("실패.");
				alert.showAndWait();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
