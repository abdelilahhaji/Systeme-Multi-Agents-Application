package agents.buyer;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BookBuyerAgent extends GuiAgent{
	
	protected BookBuyerGui gui;
	
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		System.out.println("Hi I am the buyer !");
		System.out.println("My Name is "+this.getAID().getName());
		System.out.println("I'm preparing.....");
		if(getArguments().length==1) {
			gui=(BookBuyerGui)getArguments()[0];
			gui.bookBuyerAgent=this;
		}
		
		
		ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage aclMessage=receive();
				if(aclMessage!=null) {
					gui.logMessage(aclMessage);
					ACLMessage reply=aclMessage.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("Trying to buy ->"+aclMessage.getContent());
					send(reply);
				}else {
					block();
				}
			}
		});
	}

	@Override
	protected void onGuiEvent(GuiEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
