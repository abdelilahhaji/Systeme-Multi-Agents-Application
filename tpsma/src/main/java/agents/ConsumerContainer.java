package agents;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConsumerContainer extends Application{
	
	ObservableList<String> observableListData;
	protected ConsumerAgent consumerAgent;
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		startContainer();
		primaryStage.setTitle("Consumer Container");
		BorderPane borderPane=new BorderPane();
		HBox hbox=new HBox();
		hbox.setPadding(new Insets(10));
		hbox.setSpacing(10);
		Label labelBookName=new Label("Book Name");
		TextField textFieldBookName=new TextField();
		Button buttonOk=new Button("ok");
		hbox.getChildren().addAll(labelBookName, textFieldBookName,buttonOk);
		borderPane.setTop(hbox);
		
		observableListData=FXCollections.observableArrayList();
		ListView<String> listViewMessages=new ListView<String>(observableListData);
		VBox vbox01=new VBox();
		vbox01.setPadding(new Insets(10));
		vbox01.setSpacing(10);
		vbox01.getChildren().add(listViewMessages);
		borderPane.setCenter(vbox01);
		
		buttonOk.setOnAction(evt->{
			String bookName=textFieldBookName.getText();
			GuiEvent event=new GuiEvent(this, 1);
			event.addParameter(bookName);
			consumerAgent.onGuiEvent(event);
		});
		Scene scene=new Scene(borderPane,600,400);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	public void startContainer() throws Exception {
		Runtime runtime=Runtime.instance();
		ProfileImpl profile=new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		AgentContainer container=runtime.createAgentContainer(profile);
		
		AgentController consumerController=
				container.createNewAgent("consumer", "agents.ConsumerAgent", new Object[] {this});
		consumerController.start();
	}
	public void logMessage(ACLMessage aclMessage) {
		Platform.runLater(()->{
			observableListData.add(aclMessage.getSender().getName()+"->"
		+aclMessage.getContent());
		});
	}

}
