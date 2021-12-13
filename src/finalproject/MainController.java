// Nicholas_Baird_FinalProject - MainController.java
// Author: Nicholas Baird
// Date: 12/13/2021

package finalproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.util.Duration;

public class MainController {
	
	/*============================================================================
	---------------------- LIST OF ALL FXML NODES BEING USED ---------------------
	==============================================================================*/
	// Start Page
    @FXML
    private Button startbtn;
    @FXML
    private Pane startpane;
    
    // Close Buttons
    @FXML
    private Button closeBtn;
    @FXML
    private Button closeBtn1;
    @FXML
    private Button closeBtn2;
    @FXML
    private Button closeBtn3;
    
    // OpenCreate Page
    @FXML
    private Button openbtn;
    @FXML
    private Button createbtn;
    @FXML
    private Pane opencreatepane;
    
    // Create Page
    @FXML
    private Pane createpane;
    @FXML
    private Button createBlockchainBtn;
    @FXML
    private ComboBox<Currency> cryptoCombo;
    @FXML
    private Button createCryptoBtn;
    @FXML
    private TextField blockchainName;
    
    // Open Page
    @FXML
    private Pane openpane;
    @FXML
    private ListView<Blockchain> blockchainsList;
    @FXML
    private Button selectBtn;
    
    // CreateCrypto Page
    @FXML
    private Pane createcurrencypane;
    @FXML
    private Button createCurrencyBtn;
    @FXML
    private Button moreInfoBtn;
    @FXML
    private TextField identifierTx;
    @FXML
    private TextField totalTx;
    @FXML
    private TextField currentTx;
    @FXML
    private TextField priceTx;
    
    // Blockchain Page
    @FXML
    private Pane blockchainpane;
    @FXML
    private Pagination blockchainDisplay;
    @FXML
    private ListView<Holder> holdersList;
    @FXML
    private ListView<Miner> minersList;
    @FXML
    private Button holderSendBtn;
    @FXML
    private Button minerSendBtn;
    @FXML
    private Button mineBtn;
    @FXML
    private Button confirmBtn;
    @FXML
    private TextField amtTx;
    @FXML
    private Label senderTxt;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label getterTxt;
    @FXML
    private Button createHolder;
    @FXML
    private Button createMiner;
    /*============================================================================
	------------------ END OF LIST OF ALL FXML NODES BEING USED ------------------
	==============================================================================*/
    
    // ---- Popup elements for new information or to tell the user what they are doing wrong
    // 		Used when adding a new Holder/Miner (asks for private key)
    private Popup popup = new Popup();
    // 		Used to display an error, usually when there is insufficient information entered
    private Alert alert = new Alert(AlertType.ERROR);
    
    // ---- ArrayList's to hold all of the created cryptocurrencies and blockchains
    ArrayList<Currency> cryptocurrencies = new ArrayList<>();
    
    ArrayList<Blockchain> blockchains = new ArrayList<>();
    
    // ---- ArrayList's to hold the currentBlockchain's created holders and miners
    ArrayList<Holder> holders = new ArrayList<>();
    
    ArrayList<Miner> miners = new ArrayList<>();

    private Blockchain currentBlockchain;
    
    // Stores all of the buttons
    Button[] btns = { };
    
    // Used for selecting a sender and getter for a transaction
    boolean selecting = false;
    Holder sender = null;
    Holder getter = null;
    Miner senderM = null;
    Miner getterM = null; 
    
    
    // All Buttons have a onMousePressed Event Handler and onMouseReleased Event Handler that changes their style to show it being "pressed"
    public void initialize() {
    	
    	
    	// Loads in all of the previously created blockchains and cryptocurrencies
    	loadBlockchains();
    	loadCryptos();
    	// Loads in all of the created buttons and initialize them with a basic onPressed and onReleased styling to simulate pressing of a button
    	btns = new Button[] { startbtn, closeBtn, closeBtn1, closeBtn2, closeBtn3, openbtn, createbtn, createBlockchainBtn, createCryptoBtn, selectBtn, createCurrencyBtn, moreInfoBtn, holderSendBtn, minerSendBtn, mineBtn, confirmBtn, cancelBtn };
    	initializeButtons();
    	// Start off disabling the transaction buttons
    	confirmBtn.setDisable(true);
    	cancelBtn.setDisable(true);
    	
    	
    	/*============================================================================
    	------------------------------- BLOCKCHAIN PANE ------------------------------
    	==============================================================================*/
    	holdersList.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if(selecting && holdersList.getSelectionModel().getSelectedItems().size() == 1) {
					getter = holdersList.getSelectionModel().getSelectedItem();
					getterTxt.setText("Getter: " + getter.getHash());
					confirmBtn.setDisable(false);
					cancelBtn.setDisable(false);
				}
			
			}
		});
    	
    	minersList.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if(selecting && minersList.getSelectionModel().getSelectedItems().size() == 1) {
					getterM = minersList.getSelectionModel().getSelectedItem();
					getterTxt.setText("Getter: " + getter.getHash());
					confirmBtn.setDisable(false);
					cancelBtn.setDisable(false);
				}	
			}
		});
    	
    	minersList.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if(selecting && minersList.getSelectionModel().getSelectedItems().size() == 1) {
					getterM = minersList.getSelectionModel().getSelectedItem();
					getterTxt.setText("Getter: " + getterM.getHash());
					confirmBtn.setDisable(false);
					cancelBtn.setDisable(false);
				}
			}
		});
    	
    	confirmBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				confirmBtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				sendTransaction();
				holderSendBtn.setDisable(false);
				minerSendBtn.setDisable(false);
			}
		});	
    	
    	cancelBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				cancelBtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				sender = null;
				getter = null;
				getterM = null;
				senderTxt.setText("Sender: ");
				getterTxt.setText("Getter: ");
				selecting = false;
				confirmBtn.setDisable(true);
				cancelBtn.setDisable(true);
				holderSendBtn.setDisable(false);
				minerSendBtn.setDisable(false);
					
			}
		});
    	
    	
    	holderSendBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				holderSendBtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				if(holdersList.getSelectionModel().getSelectedItems().size() == 1) {
					sender = holdersList.getSelectionModel().getSelectedItem();
					senderTxt.setText("Sender: " + sender.getHash());
					holderSendBtn.setDisable(true);
					minerSendBtn.setDisable(true);
					selecting = true;
				}
					
			}
		});
    	
    	minerSendBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				minerSendBtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				if(minersList.getSelectionModel().getSelectedItems().size() == 1) {
					senderM = minersList.getSelectionModel().getSelectedItem();
					senderTxt.setText("Sender: " + senderM.getHash());
					holderSendBtn.setDisable(true);
					minerSendBtn.setDisable(true);
					selecting = true;
				}
					
			}
		});
    	
    	mineBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				mineBtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				if(minersList.getSelectionModel().getSelectedItems().size() == 1) {
					minersList.getSelectionModel().getSelectedItem().startMining(5);
					updateDisplay();
					updateLists();
				}
					
			}
		});
  	
    	GridPane holderPane = new GridPane();
    	GridPane minerPane = new GridPane();
    	holderPane.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 8); -fx-border-color: black; -fx-border-width: 3px;");
    	minerPane.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 8); -fx-border-color: black; -fx-border-width: 3px;");
    	holderPane.setPadding(new Insets(20));
    	minerPane.setPadding(new Insets(20));
    	
    	Button addHolder = new Button("Create Holder");
    	Button addMiner = new Button("Create Miner");
    	addHolder.setStyle("-fx-background-color: #a4d5cf; -fx-effect: dropshadow(three-pass-box, #74a3b0, 0, 0, 0, 4);");
    	addMiner.setStyle("-fx-background-color: #a4d5cf; -fx-effect: dropshadow(three-pass-box, #74a3b0, 0, 0, 0, 4);");
    	
    	Label holderLabel = new Label("Enter Holder's Private Key: ");
    	Label minerLabel = new Label("Enter Miner's Private Key: ");
    	
    	TextField holderTx = new TextField();
    	TextField minerTx = new TextField();
    	
    	holderPane.add(holderLabel, 0, 0);
    	holderPane.add(holderTx, 1, 0);
    	holderPane.add(addHolder, 0, 1, 2, 1);
    	
    	minerPane.add(minerLabel, 0, 0);
    	minerPane.add(minerTx, 1, 0);
    	minerPane.add(addMiner, 0, 1, 2, 1);
    	
    	addHolder.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//addHolder.setStyle("-fx-background-color: #a4d5cf; -fx-effect: dropshadow(three-pass-box, #74a3b0, 0, 0, 0, 4);");
				if(holderTx.getText() != "") {
					Holder holder = new Holder(currentBlockchain, holderTx.getText());
					holders.add(holder);
					popup.hide();
					updateLists();
				}
			}
		});
    	 	
    	addMiner.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//addMiner.setStyle("-fx-background-color: #a4d5cf; -fx-effect: dropshadow(three-pass-box, #74a3b0, 0, 0, 0, 4);");
				if(minerTx.getText() != "") {
					Miner miner = new Miner(currentBlockchain, holderTx.getText());
					miners.add(miner);
					popup.hide();
					updateLists();
				}
			}
		});
    	
    	createHolder.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				createHolder.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				popup = new Popup();
				popup.getContent().add(holderPane);
				popup.setAutoHide(true);
				popup.show(Main.primaryStage);
			}
		});
    		
    	createMiner.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				createMiner.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				popup = new Popup();
				popup.getContent().add(minerPane);
				popup.setAutoHide(true);
				popup.show(Main.primaryStage);
			}
		});
    	/*============================================================================
    	--------------------------- END OF BLOCKCHAIN PANE ---------------------------
    	==============================================================================*/
    	
    	
    	
    	/*============================================================================
    	------------------- CREATE BLOCKCHAIN/CRYPTOCURRENCY PANE --------------------
    	==============================================================================*/
    	createBlockchainBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				createBlockchainBtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				if(cryptoCombo.getValue() != null) {
					
					if(blockchainName.getText() != null && blockchainName.getText().length() > 0) {
					
						System.out.println("Blockchain created");
						currentBlockchain = new Blockchain(blockchainName.getText(), cryptoCombo.getValue());
						blockchains.add(currentBlockchain);
						
						updateDisplay();
						updateLists();
						saveData();
						
						createFadeTransition(createpane, 1.0, 0.0, 1000, true);
						
						createFadeTransition(blockchainpane, 0.0, 1.0, 1300, false);
						
					} else {
						
						alert.setHeaderText("You must enter a name for the Blockchain");
						alert.setContentText("Try Again...");
						alert.show();
						
					}
				}
			}
		});	
    	
    	if(cryptoCombo.getItems().size() == 0) {
    		cryptoCombo.setDisable(true);
    		//createBlockchainBtn.setDisable(true);
    	}
    	
    	createCryptoBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				createCryptoBtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				createFadeTransition(createpane, 1.0, 0.0, 1000, true);
				
				createFadeTransition(createcurrencypane, 0.0, 1.0, 1300, false);
			}
		});
    	
    	createCurrencyBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				createCurrencyBtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				if(identifierTx.getText().length() == 0 || totalTx.getText().length() == 0 || currentTx.getText().length() == 0 || priceTx.getText().length() == 0) {
					alert.setHeaderText("You must fill out all of the fields");
					alert.setContentText("Try Again...");
					alert.show();
					//createCurrencyBtn.setStyle("-fx-background-color: #a4d5cf; -fx-effect: dropshadow(three-pass-box, #74a3b0, 0, 0, 0, 4);");
				} else {
					
					String identifier = "";
					long totalSupply = 0;
					double currentSupply = 0;
					double currentUSDPrice = 0;
					boolean valid = true;
					try {
						identifier = identifierTx.getText();
						totalSupply = Long.valueOf(totalTx.getText());
						currentSupply = Double.valueOf(currentTx.getText());
						currentUSDPrice = Double.valueOf(priceTx.getText());
						
						if(totalSupply == 0) {
							valid = false;
							alert.setHeaderText("Total Supply Cannot be 0");
							alert.setContentText("Try Again...");
							alert.show();
						}
						
						if(currentSupply > totalSupply) {
							valid = false;
							alert.setHeaderText("Current Supply Cannot be greater than Total Supply");
							alert.setContentText("Try Again...");
							alert.show();
						}
						
					} catch(Exception except) { 
						valid = false; 
						alert.setHeaderText("One or more of the fields are not valid inputs");
						alert.setContentText("Follow the examples and Try Again...");
						alert.show();
					}
					
					if(valid) {
						
						Currency newCurrency = new Currency(identifier, totalSupply, currentSupply, currentUSDPrice);
						cryptocurrencies.add(newCurrency);
						cryptoCombo.setItems(FXCollections.observableArrayList(cryptocurrencies));
						cryptoCombo.setDisable(false);
						createBlockchainBtn.setDisable(false);
						
						createFadeTransition(createcurrencypane, 1.0, 0.0, 1000, true);
						
						createFadeTransition(createpane, 0.0, 1.0, 1300, false);
					}
				}
			}
		});
    	
    	moreInfoBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				moreInfoBtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				popup = new Popup();
				Pane moreInformation = new Pane();
				Label info = new Label("Identifier/Name of Currency: The name of the currency that the blockchain will run off of."
						+ "\nTotal Supply: The amount of currency that the blockchain runs off of. Aka maximum amount of mined currency."
						+ "\nCurrenty Supply: The amount of currency that the blockchain starts off with. Aka minimum amount of currency."
						+ "\nUSD Price per Coin: You can set the amount each coin is worth in USD.");
				info.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4); -fx-font-size: 16pt; -fx-padding: 30px;");
				moreInformation.getChildren().add(info);
				popup.getContent().add(moreInformation);
				popup.setAutoHide(true);
				popup.show(Main.primaryStage);
			}
		}); 	
    	/*============================================================================
    	--------------- END OF CREATE BLOCKCHAIN/CRYPTOCURRENCY PANE -----------------
    	==============================================================================*/
    	
    	
    	
    	/*============================================================================
    	--------------------------- OPEN BLOCKCHAIN PANE -----------------------------
    	==============================================================================*/
    	selectBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				openbtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				if(!(blockchainsList.getSelectionModel().isEmpty())) {
					createFadeTransition(openpane, 1.0, 0.0, 1000, true);
					
					createFadeTransition(blockchainpane, 0.0, 1.0, 1300, false);
					
					currentBlockchain = blockchainsList.getSelectionModel().getSelectedItem();
					
					loadData(currentBlockchain.getHash());
					updateDisplay();
					updateLists();
				} else {
					alert.setHeaderText("You need to select which blockchain you want to open");
					alert.setContentText("Try Again...");
					alert.show();
				}
			}
		});
    	/*============================================================================
    	----------------------- END OF OPEN BLOCKCHAIN PANE --------------------------
    	==============================================================================*/
    	
    	
    	
    	
    	/*============================================================================
    	----------------------------- OPEN/CREATE PANE -------------------------------
    	==============================================================================*/
    	openbtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				openbtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				createFadeTransition(opencreatepane, 1.0, 0.0, 1000, true);
				
				createFadeTransition(openpane, 0.0, 1.0, 1300, false);
			}
		});
    	
    	createbtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				createbtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				createFadeTransition(opencreatepane, 1.0, 0.0, 1000, true);
				
				createFadeTransition(createpane, 0.0, 1.0, 1300, false);
			}
		});
    	/*============================================================================
    	------------------------- END OF OPEN/CREATE PANE ----------------------------
    	==============================================================================*/
    
    	
    	
    	/*============================================================================
    	------------------------------- CLOSE BUTTONS --------------------------------
    	==============================================================================*/
    	// From Create Pane to Open/Create Pane
    	closeBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				closeBtn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				createFadeTransition(createpane, 1.0, 0.0, 1000, true);
				createFadeTransition(opencreatepane, 0.0, 1.0, 1000, false);	
			}
		});
    	
    	// From CreateCrypto Pane to Create Pane
    	closeBtn1.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				closeBtn1.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				createFadeTransition(createcurrencypane, 1.0, 0.0, 1000, true);
				createFadeTransition(createpane, 0.0, 1.0, 1300, false);
			}
		});
    	// From Blockchain Pane to Open/Create Pane
    	closeBtn2.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				closeBtn2.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				sender = null;
				senderM = null;
				getter = null;
				getterM = null;
				senderTxt.setText("Sender: ");
				getterTxt.setText("Getter: ");
				selecting = false;
				confirmBtn.setDisable(true);
				cancelBtn.setDisable(true);
				confirmBtn.setStyle("-fx-background-color: #a4d5cf; -fx-effect: dropshadow(three-pass-box, #74a3b0, 0, 0, 0, 4);");
				cancelBtn.setStyle("-fx-background-color: #a4d5cf; -fx-effect: dropshadow(three-pass-box, #74a3b0, 0, 0, 0, 4);");
				
				saveData();
				loadBlockchains();
				holders.clear();
				miners.clear();
				
				createFadeTransition(blockchainpane, 1.0, 0.0, 1000, true);
				createFadeTransition(opencreatepane, 0.0, 1.0, 1300, false);
			}
		});
    	// From Open Pane to Open/Create Pane
    	closeBtn3.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				closeBtn2.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
				
				createFadeTransition(openpane, 1.0, 0.0, 1000, true);
				createFadeTransition(opencreatepane, 0.0, 1.0, 1300, false);
			}
		});
    	/*============================================================================
    	--------------------------- END OF CLOSE BUTTONS -----------------------------
    	==============================================================================*/
    	
    	// START MENU
	    startbtn.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				startbtn.setStyle("-fx-background-color: #a4d5cf; -fx-effect: dropshadow(three-pass-box, #74a3b0, 0, 0, 0, 4);");

				TranslateTransition translateTransition = new TranslateTransition();
				translateTransition.setDuration(Duration.millis(2000));
				translateTransition.setNode(startpane);
				translateTransition.setByY(720);
				translateTransition.setAutoReverse(false); 
				translateTransition.play(); 
				
				TranslateTransition translateTransition2 = new TranslateTransition();
				translateTransition2.setDuration(Duration.millis(2000));
				translateTransition2.setNode(opencreatepane);
				translateTransition2.setByY(720);
				translateTransition2.setAutoReverse(false); 
				translateTransition2.play(); 
				
			}
	    });
	    // END OF START MENU
    }
    
    
    //--------------------------------------------------------------------------------------------------------------
    
    /*============================================================================
	------------------------------- METHODS CREATED ------------------------------
	==============================================================================*/
    
    // Takes in a pane node, starting opacity, final opactity, length of transaction, and whether the pane should be diabled.
    // 		It then creates a FadeTransition with the given information.
    //		Used to fade in between two panes.
    public void createFadeTransition(Node obj, double from, double to, int length, boolean disable) {
    	FadeTransition ft = new FadeTransition();
    	ft.setDuration(Duration.millis(length));
    	ft.setNode(obj);
    	ft.setFromValue(from);
    	ft.setToValue(to);
    	ft.setAutoReverse(false);
    	ft.play();
    	obj.setDisable(disable);
    }
    
    
    // Updates the Blockchain Display once a new block is added to the blockchain
    public void updateDisplay() {
    	int current = blockchainDisplay.getCurrentPageIndex();
    	blockchainDisplay.setPageCount(currentBlockchain.toChain().size());
    	blockchainDisplay.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
    	blockchainDisplay.setCurrentPageIndex(current);
    	
    	// Saves the data after this occurs
    	saveData();
    }
    
    // Updates the Holder/Miner side panels when a new Holder/Miner is added or a balance change happens
    public void updateLists() {
    	holdersList.getItems().clear();
    	for(Holder holder : holders)
    		holdersList.getItems().add(holder);
    	
    	minersList.getItems().clear();
    	for(Miner miner : miners)
    		minersList.getItems().add(miner);
    	
    	// Saves the data after this occurs
    	saveData();
    }
     
    // Used to update the Blockchain Display --- RIGHT ABOVE ---
    //		Big Label to Display which block it is, then smaller text to display the blocks information
    public ScrollPane createPage(int pageIndex) {
    	ScrollPane page = new ScrollPane();
    	Label blockHeader = new Label("Block " + pageIndex);
    	blockHeader.setFont(new Font(24));
    	Label blockInfo = new Label(currentBlockchain.toChain().get(pageIndex).toString());
    	blockInfo.setPadding(new Insets(25, 0, 0, 0));
    	page.setContent(new Pane(blockHeader, blockInfo));
    
    	return page;
    }
    
    
    // Saves all of the data - Cryptocurrencies, Blockchain, Holders for a blockchain, Miners for a blockchain - to a file labeled by the blockchains hash
    //		Each created blockchain will have there own folder for their own information
    public void saveData() {
    	
    	File file = new File("data/" + this.currentBlockchain.getHash());
		if(!file.exists())
			file.mkdir();
		
		file = new File("cryptos/");
		if(!file.exists())
			file.mkdir();
    	
    	try {
    		file = new File("data/" + this.currentBlockchain.getHash() + "/blockchain.txt");
            FileOutputStream fileOut =
            new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(currentBlockchain);
            out.close();
            fileOut.close();
         }catch(IOException i) {
            i.printStackTrace();
         }
    	
    	try {
    		file = new File("data/" + this.currentBlockchain.getHash() + "/holders.txt");
            FileOutputStream fileOut =
            new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(holders);
            out.close();
            fileOut.close();
         }catch(IOException i) {
            i.printStackTrace();
         }
    	
    	try {
    		file = new File("data/" + this.currentBlockchain.getHash() + "/miners.txt");
            FileOutputStream fileOut =
            new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(miners);
            out.close();
            fileOut.close();
         }catch(IOException i) {
            i.printStackTrace();
         }
    	
    	try {
    		file = new File("cryptos/cryptocurrencies.txt");
            FileOutputStream fileOut =
            new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(cryptocurrencies);
            out.close();
            fileOut.close();
         }catch(IOException i) {
            i.printStackTrace();
         }
    	
    }
    
    
    // Loads in all of the saved data back into the program on startup, or when brought back to the open/create page.
    //		Searches through the finds a folder with the specified hash
    public void loadData(String hash) {
    	
    	try {
            FileInputStream fileIn = new FileInputStream("data/" + hash + "/holders.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            holders = (ArrayList<Holder>) in.readObject();
            in.close();
            fileIn.close();
         }catch(IOException i) {
            i.printStackTrace();
            return;
         }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
         }
    	
    	try {
            FileInputStream fileIn = new FileInputStream("data/" + hash + "/miners.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            miners = (ArrayList<Miner>) in.readObject();
            in.close();
            fileIn.close();
         }catch(IOException i) {
            i.printStackTrace();
            return;
         }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
         }
    	
    	for(Holder holder : holders)
    		holder.blockchain = currentBlockchain;
    	
    	for(Miner miner : miners)
    		miner.blockchain = currentBlockchain;
    	
    }
    
    // Finds all of the blockchains stored under the data folder
    public void loadBlockchains() {
    	
    	blockchainsList.getItems().clear();
    	blockchains.clear();
    	
    	File file = new File("data/");
    	File filesList[] = file.listFiles();
        System.out.println("List of files and directories in the specified directory:");
        for(File files : filesList) {
        	System.out.println("File name: "+files.getName());
            System.out.println("File path: "+files.getAbsolutePath());
            System.out.println("Size :"+files.getTotalSpace());
            System.out.println(" ");
        	File blockchainFile = new File(files.getAbsolutePath() + "/blockchain.txt");
        	try {
                FileInputStream fileIn = new FileInputStream(blockchainFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                blockchains.add((Blockchain) in.readObject());
                in.close();
                fileIn.close();
             }catch(IOException i) {
                i.printStackTrace();
                return;
             }catch(ClassNotFoundException c) {
                System.out.println("Blockchain class not found");
                c.printStackTrace();
                return;
             }
           
        }
        
        for(Blockchain bc : blockchains)
        	blockchainsList.getItems().add(bc);
        
    }
    
    // Finds all of the cryptocurrencies stored under the crypto folder
    public void loadCryptos() {
    	File cryptoFile = new File("cryptos/cryptocurrencies.txt");
        try {
            FileInputStream fileIn = new FileInputStream(cryptoFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            cryptocurrencies = (ArrayList<Currency>) in.readObject();
            in.close();
            fileIn.close();
         }catch(IOException i) {
            i.printStackTrace();
            return;
         }catch(ClassNotFoundException c) {
            System.out.println("Blockchain class not found");
            c.printStackTrace();
            return;
         }
        
        if(cryptocurrencies.size() > 0) {
        	cryptoCombo.setItems(FXCollections.observableArrayList(cryptocurrencies));
			cryptoCombo.setDisable(false);
			createBlockchainBtn.setDisable(false);
        }
    }
    
    
    
    
    // Sends a transaction between a sender and getter (Just realized i could have optimized this for using just UserType's instead of checking every possiblitiy of a transaction... But it still works).
    public void sendTransaction() {
    	if(getterM == null && senderM == null) {
			try {
				sender.sendTransaction(sender.privateKey, getter, Double.valueOf(amtTx.getText()));
			} catch(NumberFormatException err) {
				alert.setHeaderText("Transaction amount must be a number");
				alert.setContentText("Try Again...");
				alert.show();
			} catch(Exception err2) {
				alert.setHeaderText("Transaction Failed: Insufficient Balance");
				alert.setContentText("Try Again...");
				alert.show();
			}
    	} else if(getter == null && senderM == null) {
			try {
				sender.sendTransaction(sender.privateKey, getterM, Double.valueOf(amtTx.getText()));
			} catch(NumberFormatException err) {
				alert.setHeaderText("Transaction amount must be a number");
				alert.setContentText("Try Again...");
				alert.show();
			} catch(Exception err2) {
				alert.setHeaderText("Transaction Failed: Insufficient Balance");
				alert.setContentText("Try Again...");
				alert.show();
			}
		} else if(getter == null && sender == null) {
			try {
				senderM.sendTransaction(senderM.privateKey, getterM, Double.valueOf(amtTx.getText()));
			} catch(NumberFormatException err) {
				alert.setHeaderText("Transaction amount must be a number");
				alert.setContentText("Try Again...");
				alert.show();
			} catch(Exception err2) {
				alert.setHeaderText("Transaction Failed: Insufficient Balance");
				alert.setContentText("Try Again...");
				alert.show();
			}
		} else if(getterM == null && sender == null) {
			try {
				senderM.sendTransaction(senderM.privateKey, getter, Double.valueOf(amtTx.getText()));
			} catch(NumberFormatException err) {
				alert.setHeaderText("Transaction amount must be a number");
				alert.setContentText("Try Again...");
				alert.show();
			} catch(Exception err2) {
				alert.setHeaderText("Transaction Failed: Insufficient Balance");
				alert.setContentText("Try Again...");
				alert.show();
			}
		}
    	
		sender = null;
		senderM = null;
		getter = null;
		getterM = null;
		senderTxt.setText("Sender: ");
		getterTxt.setText("Getter: ");
		selecting = false;
		confirmBtn.setDisable(true);
		cancelBtn.setDisable(true);
		confirmBtn.setStyle("-fx-background-color: #a4d5cf; -fx-effect: dropshadow(three-pass-box, #74a3b0, 0, 0, 0, 4);");
		cancelBtn.setStyle("-fx-background-color: #a4d5cf; -fx-effect: dropshadow(three-pass-box, #74a3b0, 0, 0, 0, 4);");
    	
    	updateLists();
    }
    
    // Sets default onPressed and onRealeased mouse events to simulate a button being pressed. When Overrided by a new onPressed Event, the style will still have to be included as it gets overriden. 
    void initializeButtons() {
    	for(Button btn : btns) {
    		if(btn != null) {
	    		btn.setOnMousePressed(new EventHandler<MouseEvent>() {
	    			public void handle(MouseEvent e) {
	    				btn.setStyle("-fx-background-color: #759e99; -fx-effect: dropshadow(three-pass-box, #3a5c66, 0, 0, 0, 4);");
	    			}
	    		});
	        	
	        	btn.setOnMouseReleased(new EventHandler<MouseEvent>() {
	    			public void handle(MouseEvent e) {
	    				btn.setStyle("-fx-background-color: #a4d5cf; -fx-effect: dropshadow(three-pass-box, #74a3b0, 0, 0, 0, 4);");
	    			}
	    	    });
    		}
    	}
    }
    
    
    
    
    
    
    
    
    

}