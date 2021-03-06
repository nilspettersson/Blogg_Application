


import java.awt.Desktop;
import java.awt.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import backend_request.HttpRequest;
import backend_request.Json;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

public class Blogg {

	private ScrollPane scrollPane;
	private ScrollPane postScrollPane;
	
	private HBox refreshField;
	private VBox addField;
	
	private VBox content;
	
	//private GridPane content1;
	
	private HBox buttons;
	private Button post;
	private Button add;
	
	private Label labelTitle;
	
	private Button hashtag;
	
	private HBox hashtagField;

	private TextArea src;
	private TextArea linkText;
	private TextArea text;
	
	private HBox titleBox;
	
	private Image refreshImg;
	
	private VBox scrollPaneBox= new VBox(20);
	
	private String bloggId="null";//this is bloggId.
	public Blogg() {
		addPost();
		scrollPaneSetup();
		
		//window.setMinWidth(600);
		//window.setMinHeight(300);
		
	}
	
	public void scrollPaneSetup() {
		scrollPane= new ScrollPane();
		scrollPane.setContent(getScrollPaneBox());
		scrollPane.setPannable(true);
		scrollPane.setPrefSize(200, 1000);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		scrollPane.fitToWidthProperty().set(true);
		
		scrollPane.setOnDragDetected(e -> {
			scrollPane.setCursor(Cursor.DEFAULT);
		});
		scrollPane.setStyle("-fx-background-color:transparent;");
	
		postScrollPane= new ScrollPane();
		postScrollPane.setContent(getAddField());
		postScrollPane.setPannable(true);
		postScrollPane.setPrefSize(/*bloggar.getWidth()*/200, 800);
		//scrollPane.setFitToWidth(true);

		postScrollPane.fitToWidthProperty().set(true);
		
		postScrollPane.setOnDragDetected(e -> {
			postScrollPane.setCursor(Cursor.DEFAULT);
			System.out.println("blah");
		});
		postScrollPane.setStyle("-fx-background-color:transparent;");
		
	
		FileInputStream input;
		try {
			input = new FileInputStream("refresh.png");
			refreshImg = new Image(input);
			ImageView imageView = new ImageView(refreshImg);
			imageView.setFitHeight(18);
			imageView.setFitWidth(15);
			Button refresh = new Button("",imageView);
			
			refresh.setOnAction(e -> {
				refresh();
			});
			
			
			refreshField = new HBox();
			refreshField.getChildren().addAll(refresh);
			refreshField.getStyleClass().add("refreshField");
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
	     
		
		
		
		
		
	}

	public ScrollPane getPostScrollPane() {
		return postScrollPane;
	}

	public void setPostScrollPane(ScrollPane postScrollPane) {
		this.postScrollPane = postScrollPane;
	}

	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;
	
	
	File selectedFile;
	public void addPost() {
		content = new VBox();
		
		addField = new VBox(20);
		addField.getStyleClass().add("addField");
		add = new Button("What is on your mind?");
		add.setOnAction(e -> {
			TextField mainTitle = new TextField();
			mainTitle.setPromptText("Title for your post");
			mainTitle.setMaxWidth(600);
			mainTitle.getStyleClass().add("addContent");
			
			
			addTextLimiter(mainTitle, 80);
			
			content.getStyleClass().add("addContent");
			content.getChildren().add(mainTitle);
			
			Button addText = new Button("Add Text");
			addText.setOnAction(ee -> {
				TextArea text = new TextArea();
				text.setMaxWidth(800);
				text.setWrapText(true);
				
				text.setUserData("1");
				
				text.setOnKeyReleased(new EventHandler<Event>() {

					@Override
					public void handle(Event event) {
						System.out.println("click");
						resizeTextArea(text);
					}
					
				});
				
				HBox area = new HBox();
				area.setUserData("0");
				ComboBox<String> plusList = new ComboBox<String>();
				plusList.setValue("add");
				plusList.getItems().add("Text");
				plusList.getItems().add("Picture");
				plusList.getItems().add("Link");
				plusList.setOnAction(eee -> {
					System.out.println(plusList.getValue());
				
					if(plusList.getValue().equals("Text")) {
						TextArea secondText = new TextArea();
						secondText.setMaxWidth(800);
						secondText.setWrapText(true);
						secondText.getStyleClass().add("addField");
						secondText.setPromptText("Text");
						secondText.setUserData("1");
						area.getChildren().add(secondText);
						area.setUserData("1");
					}
					else if(plusList.getValue().equals("Picture")) {
						TextArea secondPic = new TextArea();
						secondPic.setMaxWidth(800);
						secondPic.setWrapText(true);
						secondPic.getStyleClass().add("addField");
						secondPic.setPromptText("Image Source");
						secondPic.setUserData("2");
						area.getChildren().add(secondPic);
						area.setUserData("1");
					}
					else if(plusList.getValue().equals("Link")) {
						TextArea secondLink = new TextArea();
						secondLink.setMaxWidth(800);
						secondLink.setWrapText(true);
						secondLink.getStyleClass().add("addField");
						secondLink.setPromptText("Insert a link here");
						secondLink.setUserData("3");
						area.getChildren().add(secondLink);
						area.setUserData("1");
					}
					area.getChildren().remove(plusList);
				});
				
				text.getStyleClass().add("addField");
				text.setPromptText("Text");
				
				Button plus = new Button();
				plus.setOnAction(eee -> {
					TextArea secondText = new TextArea();
					secondText.setMaxWidth(800);
					secondText.setWrapText(true);
					secondText.getStyleClass().add("addField");
					
					
					area.getChildren().add(secondText);
					area.getChildren().remove(plus);
				});
				
				area.getChildren().addAll(text,plusList);
				
				content.getChildren().add(area);
			});
			
			Button addPic = new Button("Add Pic");
			addPic.setOnAction(ee -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				
				
			        
				src = new TextArea();
				
				//src.setMaxWidth(800);
				src.setWrapText(true);
				src.getStyleClass().add("addField");
				src.setPromptText("Image Source");
				
				
				src.setUserData("2");
				
				src.setOnDragDetected(new EventHandler<MouseEvent>() {
				    public void handle(MouseEvent event) {
				       
				        Dragboard db = src.startDragAndDrop(TransferMode.ANY);
				        
				        ClipboardContent content = new ClipboardContent();
				        content.putString(src.getText());
				        db.setContent(content);
				        
				        event.consume();
				    }
				});
				
				
				   selectedFile = fileChooser.showOpenDialog(null);
		            src.setText(selectedFile.getPath());

				//content.getChildren().add(src);
				
			});
			
			Button addLink = new Button("Add Link");
			addLink.setOnAction(ee -> {
				
				linkText = new TextArea();
				
				//src.setMaxWidth(800);
				linkText.setWrapText(true);
				linkText.getStyleClass().add("addField");
				linkText.setPromptText("type a link here");
				
				
				linkText.setUserData("3");
				
				//content.getChildren().add(linkText);
				
			});
			
			Button addTitle = new Button("Add Title");
			addTitle.setOnAction(ee -> {
				TextArea title = new TextArea();
				title.setMaxWidth(800);
				
				
				title.getStyleClass().add("addField");
				title.setPromptText("Title");
				
				title.setUserData("0");
				
				//addTextLimiter(text, 100);
				
				//content.getChildren().add(title);
			});
			
			buttons = new HBox(20);
			buttons.getChildren().addAll(addText,addPic,addLink,addTitle);
			
			
			
			hashtagField = new HBox();
			hashtag = new Button("Add #Hashtag");
			
			hashtag.setOnAction(ee -> {
				TextArea hashtagWord = new TextArea();
				hashtagWord.setMaxWidth(150);
				addTextLimiterArea(hashtagWord, 20);
				hashtagWord.getStyleClass().add("hashtag");
				hashtagWord.setPromptText("Any word");
				
				hashtagField.getChildren().add(hashtagWord);
			});
			
			hashtagField.getChildren().add(hashtag);
			
			
			
			post = new Button("Post");
			post.setOnAction(ee -> {
				
				//final File file = new File(src.getText());
				
				
				String str;
				try {
					
					String inlagg="";
					String title="";
					for(int i=0;i<content.getChildren().size();i++) {
						
						if(i==0) {
							title=((TextField) content.getChildren().get(i)).getText();
							
						}
							
						else if(content.getChildren().get(i).getUserData()=="0") {
							HBox currentArea=(HBox) content.getChildren().get(i);
							
							String line=((TextArea) currentArea.getChildren().get(0)).getText();
							//String line=((TextArea) content.getChildren().get(i)).getText();
							if(((TextArea)currentArea.getChildren().get(0)).getUserData()=="0") {
								inlagg+="<!title"+line+">";
							}
							else if(((TextArea)currentArea.getChildren().get(0)).getUserData()=="1") {
								inlagg+="<!text"+line+">";
							}
							else if(((TextArea)currentArea.getChildren().get(0)).getUserData()=="2") {
								String imgSrc=MultipartUtility(line);
								inlagg+="<!img"+imgSrc+">";
							}
							else if(((TextArea)currentArea.getChildren().get(0)).getUserData()=="3") {
								inlagg+="<!link"+line+">";
							}
						
						}
						else if(content.getChildren().get(i).getUserData()=="1") {
							HBox currentArea=(HBox) content.getChildren().get(i);
							
							for(int ii=0;ii<currentArea.getChildren().size();ii++) {
								String line=((TextArea) currentArea.getChildren().get(ii)).getText();
								if(ii==0) {//if it is on right side.
									if(((TextArea)currentArea.getChildren().get(ii)).getUserData()=="0") {
										inlagg+="<!title"+line+">";
									}
									else if(((TextArea)currentArea.getChildren().get(ii)).getUserData()=="1") {
										inlagg+="<!text"+line+">";
									}
									else if(((TextArea)currentArea.getChildren().get(ii)).getUserData()=="2") {
										String imgSrc=MultipartUtility(line);
										inlagg+="<!img"+imgSrc+">";
									}
									else if(((TextArea)currentArea.getChildren().get(ii)).getUserData()=="3") {
										inlagg+="<!link"+line+">";
									}
								}
								else {
									if(((TextArea)currentArea.getChildren().get(ii)).getUserData()=="0") {
										inlagg+="<!title1"+line+">";
									}
									else if(((TextArea)currentArea.getChildren().get(ii)).getUserData()=="1") {
										inlagg+="<!text1"+line+">";
									}
									else if(((TextArea)currentArea.getChildren().get(ii)).getUserData()=="2") {
										String imgSrc=MultipartUtility(line);
										inlagg+="<!img1"+imgSrc+">";
									}
									else if(((TextArea)currentArea.getChildren().get(ii)).getUserData()=="3") {
										inlagg+="<!link1"+line+">";
									}
								}
								
							}
						
						}
						
						
						
						else {
							System.out.println("error");
						}
							
						
						
						
					}
					
					//hashtag!!!!!
					for(int i=1;i<hashtagField.getChildren().size();i++) {
						String line=((TextArea) hashtagField.getChildren().get(i)).getText();
						inlagg+="<!tag"+line+">";
					}
					
					
					
					str = HttpRequest.send("Blogg/funktioner/skapa.php","funktion=skapaInlagg&bloggId="+Main.currentBlogg+"&Title="+title+"&innehall="+inlagg);
					
					
				} catch (Exception eee) {
					// TODO Auto-generated catch block
					eee.printStackTrace();
				}
				
				content.getChildren().clear();
				addField.getChildren().removeAll(content, buttons,hashtagField, post);
				
				addField.getChildren().add(add);
				
				refresh();
			});
			
			
			
			addField.getChildren().addAll(content, buttons,hashtagField, post);
			addField.getChildren().remove(add);
				
		});
		
	}
	
	public VBox getContent() {
		return content;
	}

	public void setContent(VBox content) {
		this.content = content;
	}

	public static void addTextLimiter(final TextField text, final int maxLength) {
	    text.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	            if (text.getText().length() > maxLength) {
	                String s = text.getText().substring(0, maxLength);
	                text.setText(s);
	            }
	        }
	    });
	}
	
	public static void addTextLimiterArea(final TextArea text, final int maxLength) {
	    text.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	            if (text.getText().length() > maxLength) {
	                String s = text.getText().substring(0, maxLength);
	                text.setText(s);
	            }
	        }
	    });
	}
	
	
	private void resizeTextArea(TextArea txtArea) {
        String text = txtArea.getText();
        System.out.println(text);
        
        /*if(text.length()>20*amount) {
        	txtArea.setText(text+"\n");
        	txtArea.selectEnd();
        	txtArea.endOfNextWord();
        	amount++;
        }*/
        
        for(int i=text.length();i>=0;i--) {
        	
        }
        
        double amount=0;
        for(int i=0;i<text.length();i++) {
        	if(text.charAt(i)=='\n') {
        		amount++;
        	}
        }
        
        double height = 40+(amount*22);
        txtArea.setMinHeight(height);
        txtArea.setMaxHeight(height);
    }
	
	
	
	Button likes;
	public void post(String postId, String title, String text, int likesAmount) {
		
		
		Label postTitle = new Label(title);
		if(Main.settings.getColorTheme()=="Light") {
			postTitle.getStyleClass().clear();
			postTitle.getStyleClass().add("postTitle");
		}
		if(Main.settings.getColorTheme()=="Dark") {
			postTitle.getStyleClass().clear();
			postTitle.getStyleClass().add("postTitleDark");
		}
		
		String tags="";
		String tag="";
		String action="";
		ArrayList<Node>nodes=new ArrayList<Node>();
		
		
		
		ArrayList<HBox>tagBox=new ArrayList<HBox>();//new hbox every time the column has 5 tags.
		tagBox.add(new HBox(10));
		
		//HBox tagBox=new HBox(10);
		
		int startIndex=0;
		for(int i=0;i<text.length();i++) {
			boolean done=false;
			
			if(text.charAt(i)=='<' && !done) {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='t') {
						if(text.charAt(i+3)=='e') {
							if(text.charAt(i+4)=='x') {
								if(text.charAt(i+5)=='t') {
									if(text.charAt(i+6)=='1') {
										action="text1";
										startIndex=i+7;
										done=true;
									}
								}
							}
						}
					}
				}	
			}
			
			if(text.charAt(i)=='<' && !done) {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='i') {
						if(text.charAt(i+3)=='m') {
							if(text.charAt(i+4)=='g') {
								if(text.charAt(i+5)=='1') {
									action="img1";
									startIndex=i+6;
									done=true;
								}
							}
						}
					}
				}	
			}
			
			if(text.charAt(i)=='<' && !done) {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='l') {
						if(text.charAt(i+3)=='i') {
							if(text.charAt(i+4)=='n') {
								if(text.charAt(i+5)=='k') {
									if(text.charAt(i+6)=='1') {
										action="link1";
										startIndex=i+7;
										done=true;
									}
								}
							}
						}
					}
				}	
			}
			
			
			
			
			if(text.charAt(i)=='<' && !done) {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='t') {
						if(text.charAt(i+3)=='i') {
							if(text.charAt(i+4)=='t') {
								if(text.charAt(i+5)=='l') {
									if(text.charAt(i+6)=='e') {
										action="title";
										startIndex=i+7;
										done=true;
									}
								}
							}
						}
					}
				}	
			}
			
			if(text.charAt(i)=='<' && !done) {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='t') {
						if(text.charAt(i+3)=='e') {
							if(text.charAt(i+4)=='x') {
								if(text.charAt(i+5)=='t') {
									action="text";
									startIndex=i+6;
									done=true;
								}
							}
						}
					}
				}	
			}
			
			if(text.charAt(i)=='<' && !done) {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='i') {
						if(text.charAt(i+3)=='m') {
							if(text.charAt(i+4)=='g') {
								action="img";
								startIndex=i+5;
								done=true;
							}
						}
					}
				}	
			}
			
			if(text.charAt(i)=='<' && !done) {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='l') {
						if(text.charAt(i+3)=='i') {
							if(text.charAt(i+4)=='n') {
								if(text.charAt(i+5)=='k') {
									action="link";
									startIndex=i+6;
									done=true;
								}
							}
						}
					}
				}	
			}
			
			if(text.charAt(i)=='<' && !done) {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='t') {
						if(text.charAt(i+3)=='a') {
							if(text.charAt(i+4)=='g') {
								action="tag";
								startIndex=i+5;
								done=true;
							}
						}
					}
				}	
			}
			
			
			
			
			if(text.charAt(i)=='>') {
				String subText=text.substring(startIndex,i);
				
				
				if(action.equals("text1")) {
					nodes.add(new Label(subText));
					((Label) nodes.get(nodes.size()-1)).setPadding(new Insets(0, 0, 20, 0));
					((Label) nodes.get(nodes.size()-1)).setWrapText(true);
					nodes.get(nodes.size()-1).setUserData("1");
					((Label) nodes.get(nodes.size()-1)).setPrefWidth(Main.mainLayout.getWidth());
				}
				if(action.equals("img1")) {
					String path =subText;
					try {
						BorderPane pane = new BorderPane();
						pane.getStyleClass().add("shadow");
						pane.setPrefWidth(Main.mainLayout.getWidth());
						
						Image image = new Image(path);
						ImageView imageView = new ImageView(image);
						
						
						
						pane.setCenter(imageView);
						
						double ratio=image.getHeight()/image.getWidth();
						double scale=350;
						imageView.setFitWidth(scale);
						imageView.setFitHeight(scale*ratio);
					
						nodes.add(pane);
						nodes.get(nodes.size()-1).setUserData("1");
					}
					catch (IllegalArgumentException e) {
						// TODO: handle exception
					}
				}
				if(action.equals("link1")) {
					String path =subText;
					
					
					try {
						
						Desktop desktop = Desktop.getDesktop();
						Hyperlink hyperlink = new Hyperlink();
						hyperlink.setText(path);
						hyperlink.setOnAction(e -> {
							
							try {
								desktop.browse(new URI(path));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (URISyntaxException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						});
						
						nodes.add(hyperlink);
						nodes.get(nodes.size()-1).setUserData("1");
						((Hyperlink) nodes.get(nodes.size()-1)).setPrefWidth(Main.mainLayout.getWidth());
					}
					catch (IllegalArgumentException e) {
						// TODO: handle exception
					}
					
				}
				
				if(action.equals("text")) {
					nodes.add(new Label(subText));
					((Label) nodes.get(nodes.size()-1)).setPadding(new Insets(0, 0, 20, 0));
					((Label) nodes.get(nodes.size()-1)).setWrapText(true);
					nodes.get(nodes.size()-1).setUserData("0");
					((Label) nodes.get(nodes.size()-1)).setPrefWidth(Main.mainLayout.getWidth());
					
					if(Main.settings.getColorTheme()=="Light") {
						nodes.get(nodes.size()-1).getStyleClass().clear();
						nodes.get(nodes.size()-1).getStyleClass().add("postText");
					}
					if(Main.settings.getColorTheme()=="Dark") {
						nodes.get(nodes.size()-1).getStyleClass().clear();
						nodes.get(nodes.size()-1).getStyleClass().add("postTextDark");
					}
				}
				if(action.equals("title")) {
					nodes.add(new Label(subText));
					if(Main.settings.getColorTheme()=="Light") {
						nodes.get(nodes.size()-1).getStyleClass().clear();
						nodes.get(nodes.size()-1).getStyleClass().add("postInnerTitle");
					}
					if(Main.settings.getColorTheme()=="Dark") {
						nodes.get(nodes.size()-1).getStyleClass().clear();
						nodes.get(nodes.size()-1).getStyleClass().add("postInnerTitleDark");
					}
					nodes.get(nodes.size()-1).setUserData("0");
					
				}
				if(action.equals("img")) {
					String path =subText;
					try {
						
						Image image = new Image(path);
						ImageView imageView = new ImageView(image);
						
						double ratio=image.getHeight()/image.getWidth();
						double scale=350;
						imageView.setFitWidth(scale);
						imageView.setFitHeight(scale*ratio);
					
						nodes.add(imageView);
						nodes.get(nodes.size()-1).setUserData("0");
						
					}
					catch (IllegalArgumentException e) {
						// TODO: handle exception
					}
				}
				if(action.equals("link")) {
					String path =subText;
					
					
					try {
						
						Desktop desktop = Desktop.getDesktop();
						Hyperlink hyperlink = new Hyperlink();
						hyperlink.setText(path);
						hyperlink.setOnAction(e -> {
							
							try {
								desktop.browse(new URI(path));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (URISyntaxException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						});
						
						nodes.add(hyperlink);
						nodes.get(nodes.size()-1).setUserData("0");
					}
					catch (IllegalArgumentException e) {
						// TODO: handle exception
					}
					
				}
				
				if(action.equals("tag")) {
					if(tagBox.get(tagBox.size()-1).getChildren().size()>=5) {
						tagBox.add(new HBox(10));
					}
					
					
					tags+="#"+subText+" ";
					tag="#"+subText+" ";
					
					Button tagButton = new Button(tag);
					tagButton.getStyleClass().add("hashtag");
					
					
					tagBox.get(tagBox.size()-1).getChildren().add(tagButton);
					
					for(int ii=0;ii<tagBox.size();ii++) {
						nodes.add(tagBox.get(ii));
					}
					
					nodes.get(nodes.size()-1).setUserData("0");
					
					tagButton.setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent event) {
							String tag=((Button) event.getSource()).getText();
							
							getScrollPaneBox().getChildren().clear();
							
							try {
								String blogg = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg);
							
								JSONObject json=new JSONObject(blogg);
								
								String bloggTitle=json.getString("titel");
								bloggId=json.getString("bloggId");

								JSONArray inlagg=json.getJSONArray("bloggInlagg");
								for(int i=inlagg.length()-1;i>=0;i--) {
									String inlaggStr = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg="+inlagg.getJSONObject(i).getString("id"));
									

									JSONObject inlaggJson=Json.toJSONObject(inlaggStr);
									
									String title=inlaggJson.getString("titel");
									
									String postId=inlaggJson.getString("id");
									
									String text=inlaggJson.getString("innehall");
									
									JSONArray array=inlaggJson.getJSONArray("gillningar");
									
									int likesAmount=array.length();
									
									String newTag=tag.substring(1,tag.length()-1);
									if(text.contains("<!tag"+newTag+">")) {
										post(postId,title, text, likesAmount);
									}
									
								}
								if(Main.login.getBloggId().equals(Main.currentBlogg+"")) {
									getAddField().getChildren().removeAll(getButtons(),getPost(),getAdd());
									getAddField().getChildren().add(getAdd());
								}
							} catch (Exception ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
					});
				}
			}
		}
		
		Label labelText= new Label(text);
		labelText.setWrapText(true);
	
		likes= new Button("Like: "+likesAmount);
		likes.setUserData(postId);
		likes.setOnAction(e -> {
			if(Main.login.isLoggedIn()) {
				System.out.println(Main.currentBlogg);
				try {
					String str = HttpRequest.send("Blogg/funktioner/skapa.php","funktion=gillaInlagg&anvandarId="+Main.login.getUserId()+"&inlaggsId="+likes.getUserData());
					refresh();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		});
		
		HBox likeBox= new HBox();
		likeBox.getStyleClass().add("likes");
		likeBox.getChildren().add(likes);
		
		VBox post= new VBox(10);
		post.setPadding(new Insets(20));
		//post.setUserData(tags);//this might be needed!!!!!
		post.setUserData(postId);
		post.getChildren().addAll(postTitle);
		
		for(int i=0;i<nodes.size();i++) {
			HBox postContent=new HBox();
			if(nodes.get(i).getUserData()=="0") {
				postContent.getChildren().add(nodes.get(i));
				if(i+1!=nodes.size()) {
					
					if(nodes.get(i+1).getUserData()=="1") {
						postContent.getChildren().add(nodes.get(i+1));
						i++;
					}
				}
			}
			post.getChildren().add(postContent);
		}
		post.getChildren().addAll(likeBox);
		
		HBox commentBox = new HBox(10);
		
		commentBox.getStyleClass().add("commentBox");
		TextField commentText = new TextField();
		commentText.setPrefSize(800, 10);
		commentBox.setMargin(commentText, new Insets(20,0,0,0));
		addTextLimiter(commentText, 110);
		Button comment= new Button("Comment");
		commentBox.setMargin(comment, new Insets(20,0,0,0));
		commentBox.getChildren().addAll(commentText,comment);
		
		comment.setOnAction(e -> {{
			if(Main.login.isLoggedIn()) {
				try {
					String str = HttpRequest.send("Blogg/funktioner/skapa.php","funktion=skapaKommentar&anvandarId="+Main.login.getUserId()+"&inlaggsId="+likes.getUserData()+"&hierarchyID=0"+"&text="+commentText.getText());
					refresh();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}});
		
		post.getChildren().add(commentBox);
		
		post.setPrefWidth(Main.mainLayout.getWidth());
		
		if(Main.settings.getColorTheme()=="Light") {
			post.getStyleClass().clear();
			post.getStyleClass().add("post");
		}
		if(Main.settings.getColorTheme()=="Dark") {
			post.getStyleClass().clear();
			post.getStyleClass().add("postDark");
		}
		
		post.setOnMouseClicked( ( e ) ->
        {
       	 
        });
		
		HBox showComment= new HBox(20);
		showComment.getStyleClass().add("showComment");
		
		Label theComment= new Label("hejjj");
		
		theComment.setPadding(new Insets(5,20,5,20));
		
		HBox theCommentBox = new HBox();
		theCommentBox.setPrefWidth(1000);
		theCommentBox.getChildren().add(theComment);
		
		Button reply= new Button("Reply");
		reply.setOnAction(e -> {
			comments();
			
		});
		
		HBox replyBox = new HBox();
		
		replyBox.getChildren().add(reply);
		
		showComment.getChildren().addAll(theCommentBox, replyBox);
		
		getScrollPaneBox().getChildren().addAll(post, showComment);
		scrollPaneBox.setPadding(new Insets(0,20,0,20));
		
	}
	
	public void refresh() {
		
		content.getChildren().clear();
		addField.getChildren().removeAll(content, buttons,hashtagField, post);
		
		getScrollPaneBox().getChildren().clear();
		Main.center.getChildren().remove(labelTitle);
		try {
			String blogg = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg);
		
			JSONObject json=new JSONObject(blogg);
			
			String bloggTitle=json.getString("titel");
			bloggId=json.getString("bloggId");
			
			JSONArray inlagg=json.getJSONArray("bloggInlagg");
			for(int i=inlagg.length()-1;i>=0;i--) {
				String inlaggStr = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg="+inlagg.getJSONObject(i).getString("id"));
				
				
				JSONObject inlaggJson=Json.toJSONObject(inlaggStr);
				
				String title=inlaggJson.getString("titel");
				String postId=inlaggJson.getString("id");
				String text=inlaggJson.getString("innehall");
				
				JSONArray array=inlaggJson.getJSONArray("gillningar");
				
				int likesAmount=array.length();
					
				//comments();
				
				post(postId,title, text, likesAmount);
				
			}
			
			//title for blogg.
			labelTitle=new Label(bloggTitle);
			labelTitle.setFont(new Font(40));
			labelTitle.setAlignment(Pos.CENTER);
			labelTitle.setPrefWidth(4000);
			labelTitle.getStyleClass().add("bloggTitle");
			
			titleBox = new HBox();
			
			titleBox.getChildren().add(labelTitle);
			
			//removes and adds title.
			
			Main.center.getChildren().add(1,labelTitle);
			
			if(Main.login.getBloggId().equals(Main.currentBlogg+"")) {
				getAddField().getChildren().removeAll(getButtons(),getPost(),getAdd());
				getAddField().getChildren().add(getAdd());
				
				
			}
			else {
				getAddField().getChildren().removeAll(getButtons(),getPost(),getAdd());
			}
			
			
			Main.archive.setValue("All posts");
			
		
		} catch (Exception ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}
		
	}
	
	public void commentsIn(JSONArray array) {
		try {
			String str = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg=3");
		
			System.out.println(str);
			JSONObject json=Json.toJSONObject(str);
		
			array=json.getJSONArray("kommentarer");
			for(int i=0;i<array.length();i++) {
				JSONArray array2=array;
				commentsIn(array2);
			}
			
			System.out.println(array.length());
				
		
		} catch (Exception ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}
		
		
	}
	
	public void comments() {
		try {
			String str = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg="+likes.getUserData());
		
			System.out.println(Main.currentBlogg);
			
			System.out.println(str);
			JSONObject json=Json.toJSONObject(str);
			JSONArray array;
			array=json.getJSONArray("kommentarer");
			
			System.out.println(array);
			
			System.out.println(array.length());
				
		
		} catch (Exception ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}
		
		
	}
	
	
	public static String MultipartUtility(String file) {
        String charset = "UTF-8";
        File uploadFile1 = new File(file);
        String oldPath=uploadFile1.getPath();
        
        String newPath=oldPath.substring(0, oldPath.length()-4);
        newPath+=(long)(Math.random()*1000000000)+".png";
        File newFile=new File(newPath);
        uploadFile1.renameTo(newFile);
        
        System.out.println("name: "+newFile.getName());
        
        
        
        //String requestURL = "http://10.130.216.101/TP/Blogg/funktioner/bilder";
 
        String requestURL = "http://10.130.216.101/TP/Blogg/funktioner/skapaBilder.php";

        String location="";
        
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
             
            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");
             
            multipart.addFormField("description", "Cool Pictures");
            multipart.addFormField("keywords", "Java,upload,Spring");
            
            multipart.addFilePart("file", newFile);
            
            
            newFile.renameTo(new File(oldPath));
            System.out.println("renamed name: "+newFile.getName()+"  oldPath: "+oldPath+"  newPath: "+newPath);
            
            
            List<String> response = multipart.finish();
             
            System.out.println("SERVER REPLIED:");
             
            for (String line : response) {
                System.out.println(line);
                location += line;
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println(location);
        
        JSONObject json=Json.toJSONObject(location);
        String send="";
        try {
			send=json.getString("resp");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return send;
    }
	

	public ScrollPane getScrollPane() {
		return scrollPane;
	}


	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}


	public HBox getRefreshField() {
		return refreshField;
	}


	public void setRefreshField(HBox refreshField) {
		this.refreshField = refreshField;
	}

	public VBox getAddField() {
		return addField;
	}

	public void setAddField(VBox addField) {
		this.addField = addField;
	}


	public HBox getButtons() {
		return buttons;
	}

	public void setButtons(HBox buttons) {
		this.buttons = buttons;
	}

	public Button getPost() {
		return post;
	}

	public void setPost(Button post) {
		this.post = post;
	}

	


	public Button getAdd() {
		return add;
	}

	public void setAdd(Button add) {
		this.add = add;
	}

	public VBox getScrollPaneBox() {
		return scrollPaneBox;
	}

	public void setScrollPaneBox(VBox scrollPaneBox) {
		this.scrollPaneBox = scrollPaneBox;
	}

	public Label getLabelTitle() {
		return labelTitle;
	}

	public void setLabelTitle(Label labelTitle) {
		this.labelTitle = labelTitle;
	}

	public Button getHashtag() {
		return hashtag;
	}

	public void setHashtag(Button hashtag) {
		this.hashtag = hashtag;
	}

	public HBox getHashtagField() {
		return hashtagField;
	}

	public void setHashtagField(HBox hashtagField) {
		this.hashtagField = hashtagField;
	}

	public HBox getTitleBox() {
		return titleBox;
	}

	public void setTitleBox(HBox titleBox) {
		this.titleBox = titleBox;
	}

}
