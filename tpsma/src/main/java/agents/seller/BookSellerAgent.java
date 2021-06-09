package agents.seller;

import java.util.HashMap;
import java.util.Map;

import agents.buyer.BookBuyerGui;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BookSellerAgent extends Agent{
	
	protected BookSellerGui gui;
	protected Map<String, Double> data=new HashMap<String, Double>();

	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		System.out.println("Hi I am the seller !");
		data.put("XML", new Double(230)); 
		data.put("JAVA", new Double(460));
		data.put("IOT", new Double(540));
		System.out.println(".....Seller "+this.getAID().getName());
		if(getArguments().length==1) {
			gui=(BookSellerGui)getArguments()[0];
			gui.bookSellerAgent=this;
		}
		
		ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				// TODO Auto-generated method stub
				MessageTemplate template=
						MessageTemplate.or(
						MessageTemplate.MatchPerformative(ACLMessage.CFP),
						MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
						
				ACLMessage aclMessage=receive(template);
				if(aclMessage!=null){
					switch(aclMessage.getPerformative()){
					case ACLMessage.CFP :
						System.out.println("Conversation:"+aclMessage.getConversationId());
						gui.logMessage(aclMessage);
					case ACLMessage.ACCEPT_PROPOSAL:
						System.out.println("Conversation:"+aclMessage.getConversationId());
						System.out.println("transaction validation .....");
						ACLMessage reply=aclMessage.createReply();
						reply.setPerformative(ACLMessage.CONFIRM);
						System.out.println("......................");
						send(reply);
						break;
					}
				}
					
				
			}
		});
		
	}
}
