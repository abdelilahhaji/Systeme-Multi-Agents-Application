package agents.seller;

import agents.buyer.BookBuyerAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookSellerGui extends Application{
	protected BookSellerAgent bookSellerAgent;
	protected ListView<String> listViewMessages;
	protected ObservableList<String> observableListData;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		startContainer();
		primaryStage.setTitle("Book Seller Gui");
		BorderPane borderPane=new BorderPane();
		VBox vbox=new VBox();
		vbox.setPadding(new Insets(10));
		observableListData=FXCollections.observableArrayList();
		listViewMessages=new ListView<String>(observableListData);
		vbox.getChildren().add(listViewMessages);
		Scene scene=new Scene(borderPane,400,500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private void startContainer() throws Exception {
		// TODO Auto-generated method stub
		Runtime runtime=Runtime.instance();
		ProfileImpl profile=new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		AgentContainer agentContainer=runtime.createAgentContainer(profile);
		AgentController agentController=agentContainer.createNewAgent(
				"BookSellerAgent", BookSellerAgent.class.getName(), new Object[] {this}); 
		agentController.start();
		
	}
	
	public void logMessage(ACLMessage aclMessage) {
		Platform.runLater(()->{
			observableListData.add(aclMessage.getSender().getName()+"->"
		+aclMessage.getContent());
		});
	}
}
