import org.json.JSONArray;
import org.json.JSONObject;

import backend_request.HttpRequest;
import backend_request.Json;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private HBox rightTop;
	
	private HBox refreshField;
	
	public HBox getLeftTop() {
		return leftTop;
	}

	public void setLeftTop(HBox leftTop) {
		this.leftTop = leftTop;
	}

	private Button exploreBlogg;
	private Button yourBlogg;
	
	private Label noResult;
	
	private TextField searchPost;
	public Menus() {
		scene();
	}
	
	public void scene() {
		
		
		sideMenu();
		topMenu();
	}
	

	public void topMenu() {
		
		exploreBlogg = new Button("Explore Bloggs");
		exploreBlogg.getStyleClass().add("topButton");
		
		yourBlogg = new Button("My Blogg");
		yourBlogg.getStyleClass().add("topButton");
		leftTop = new HBox(20);
		leftTop.getChildren().addAll(exploreBlogg, yourBlogg);
		
		leftTop.setMargin(leftTop, new Insets(5,0,0,30));
		leftTop.setMinSize(180, 28);
		leftTop.setPrefSize(2000, 10);
		
		
		topMenu = new HBox(20);
		topMenu.getStyleClass().add("topMenu");
		topMenu.getChildren().addAll(leftTop);
		search();
		
	}
	
	public void search() {
		searchPost = new TextField();
		
		searchPost.getStyleClass().add("search");
		//Main.menus.getSearchPost().getStyleClass().add("searchDark");
		
		searchPost.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	searching();
	            }
	        }
	    });
		
		Button search = new Button("Search");
		search.getStyleClass().add("topButton");
		search.setOnAction(e -> {
			searching();
				
			
		});
		
		rightTop = new HBox();
		rightTop.getChildren().addAll(search, searchPost);
		rightTop.setMargin(rightTop, new Insets(5,0,0,30));
		rightTop.setMinSize(350, 28);
		
		topMenu.getChildren().addAll( rightTop);
	}
	
	public void searching() {

		String searchResult= searchPost.getText();
		char[] searchArray= searchResult.toCharArray();
		System.out.println(searchResult);
		
		try {
				//String blogg = HttpRequest.send("nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=JSON&bloggId="+Main.currentBlogg);
			
				Main.blogg.getScrollPaneBox().getChildren().clear();
			
				Main.blogg.getScrollPaneBox().getChildren().remove(noResult);
						
						String inlaggStr1;
						
						inlaggStr1 = HttpRequest.send("Blogg/funktioner/skapa.php","funktion=sokfalt&sok="+ searchResult+"&bloggId="+Main.currentBlogg);
						System.out.println(inlaggStr1);
						
						JSONObject json=new JSONObject(inlaggStr1);
					
						JSONArray inlagg=json.getJSONArray("inlagg");
						for(int i=inlagg.length()-1;i>=0;i--) {
							String inlaggStr = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg="+inlagg.getJSONObject(i).getString("id"));
							
							JSONObject inlaggJson=Json.toJSONObject(inlaggStr);
							
							String title=inlaggJson.getString("titel");
							String postId=inlaggJson.getString("id");
							char[] tempTitle=title.toCharArray();
							String text=inlaggJson.getString("innehall");
							
							JSONArray array=inlaggJson.getJSONArray("gillningar");
							
							int likesAmount=array.length();
							String newTitle="";
							String temp= title.toLowerCase();
							String found= "|"+searchResult+"|";
							
							if ( temp.indexOf(searchResult.toLowerCase()) != -1 ) {
								
								Label word= new Label(found);
								word.getStyleClass().add("found");
								
								title=title.replaceAll("(?i)"+searchResult, word.getText());
								
								newTitle= title;
								Main.blogg.post(postId,newTitle, text, likesAmount);
								
							}
						}
			} 
       		catch (Exception ee) {
				// TODO Auto-generated catch block
				//ee.printStackTrace();
       			Main.blogg.getScrollPaneBox().getChildren().remove(noResult);
       			noResult = new Label("No results...");
       			noResult.getStyleClass().add("noResult");
       			noResult.setPrefWidth(2000);
       			HBox resultBox = new HBox();
       			resultBox.getChildren().add(noResult);
       			resultBox.setMargin(noResult, new Insets(50,50,300,50));
       			
				Main.blogg.getScrollPaneBox().getChildren().add(resultBox);
			}
	}
	
	private Button setings;
	private Button login;
	private Label username;
	private Label likes;
	public void sideMenu() {
		username=new Label("Guest");
		
		likes=new Label();
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
	
	public void refreshField() {
		
		Button refresh = new Button("Refresh");
		
		refresh.setOnAction(e -> {
			if(Main.page==1) {
				Main.blogg.refresh();
			}
			if(Main.page==2) {
				Main.explore.refresh();
			}
		});
		
		refreshField = new HBox();
		refreshField.getChildren().addAll(refresh);
		refreshField.getStyleClass().add("refreshField");
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
	

	public Label getNoResult() {
		return noResult;
	}

	public void setNoResult(Label noResult) {
		this.noResult = noResult;
	}

	public TextField getSearchPost() {
		return searchPost;
	}

	public void setSearchPost(TextField searchPost) {
		this.searchPost = searchPost;
	}

	public HBox getRightTop() {
		return rightTop;
	}

	public void setRightTop(HBox rightTop) {
		this.rightTop = rightTop;
	}

	public Label getLikes() {
		return likes;
	}

	public void setLikes(Label likes) {
		this.likes = likes;
	}
	
	public HBox getRefreshField() {
		return refreshField;
	}


	public void setRefreshField(HBox refreshField) {
		this.refreshField = refreshField;
	}


}
