

import backend_request.Post;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menus {

	private HBox topMenu;
	
	private VBox sideMenu;
	
	private HBox leftTop;
	
	public HBox getLeftTop() {
		return leftTop;
	}

	public void setLeftTop(HBox leftTop) {
		this.leftTop = leftTop;
	}


	private Button exploreBlogg;
	private Button yourBlogg;
	
	public Menus() {
		scene();
	}
	
	public void scene() {
		
		
		sideMenu();
		topMenu();
	}
	

	public void topMenu() {
		
		exploreBlogg = new Button("Explore Bloggs");
		
		yourBlogg = new Button("Your Blogg");
		
		
		
		
		leftTop = new HBox(20);
		leftTop.getChildren().addAll(exploreBlogg, yourBlogg);
		
		
		leftTop.setMargin(leftTop, new Insets(20,0,0,75));
		leftTop.setMinSize(200, 50);
		leftTop.setPrefSize(2000, 50);
		leftTop.getStyleClass().add("leftTop");
		
		
		Button search = new Button("Search");
		
		TextField searchPost = new TextField();
		
		HBox rightTop = new HBox();
		rightTop.getChildren().addAll(search, searchPost);
		rightTop.setMargin(rightTop, new Insets(20));
		rightTop.setMinSize(250, 50);
		rightTop.getStyleClass().add("rightTop");
		
		topMenu = new HBox(20);
		topMenu.getChildren().addAll(leftTop, rightTop);
		
	}
	

	private Button setings;
	private Button login;
	private Label username;
	public void sideMenu() {
		username=new Label("Niles ahmad");
		Label likes=new Label("Likes: 217");
		setings=new Button("Setings");
		
		sideMenu=new VBox(100);
		sideMenu.setPrefHeight(1000);
		
		sideMenu.setMinWidth(sideMenu.USE_PREF_SIZE);
		
		
		VBox upper=new VBox(20);
		upper.getChildren().addAll(username,likes);
		upper.setPadding(new Insets(120, 20, 20, 20));
		
		
		VBox middle=new VBox(20);
		middle.setPadding(new Insets(0, 20, 0, 20));
		middle.setPrefHeight(sideMenu.getPrefHeight());
		middle.getChildren().addAll(setings);
		
		
		VBox lower=new VBox(20);
		
		
		login=new Button("Log in");
		login.setMinWidth(128);
		login.setMinHeight(40);
		
		
		lower.getChildren().add(login);
		
		
		sideMenu.getChildren().addAll(upper,middle,lower);
		
		
		sideMenu.getStyleClass().add("sideMenu");
		
		
		upper.getStyleClass().add("upper");
		username.getStyleClass().add("upper");
		likes.getStyleClass().add("upper");
		
		
		
		middle.getStyleClass().add("middle");
		setings.getStyleClass().add("sideButton");
		
		
		
		login.getStyleClass().add("sideButton");
		lower.getStyleClass().add("loginButton");
	}


	public Button getLogin() {
		return login;
	}
	

	public void setLogin(Button login) {
		this.login = login;
	}

	public HBox getTopMenu() {
		return topMenu;
	}

	public void setTopMenu(HBox topMenu) {
		this.topMenu = topMenu;
	}

	public VBox getSideMenu() {
		return sideMenu;
	}
	

	public void setSideMenu(VBox sideMenu) {
		this.sideMenu = sideMenu;
	}

	public Button getSetings() {
		return setings;
	}

	public void setSetings(Button setings) {
		this.setings = setings;
	}

	public Button getExploreBlogg() {
		return exploreBlogg;
	}

	public void setExploreBlogg(Button exploreBlogg) {
		this.exploreBlogg = exploreBlogg;
	}

	public Button getYourBlogg() {
		return yourBlogg;
	}

	public void setYourBlogg(Button yourBlogg) {
		this.yourBlogg = yourBlogg;
	}

	public Label getUsername() {
		return username;
	}

	public void setUsername(Label username) {
		this.username = username;
	}
	
	

}
