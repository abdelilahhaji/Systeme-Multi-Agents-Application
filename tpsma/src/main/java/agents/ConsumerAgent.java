package agents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;

public class ConsumerAgent extends GuiAgent{
	
	protected ConsumerContainer consumerContainer;
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		String bookName=null;
		if(this.getArguments().length==1) {
			consumerContainer=(ConsumerContainer) getArguments()[0];
			consumerContainer.consumerAgent=this;
		}
		System.out.println("Initialisation de l'agent "+this.getAID().getName());
		System.out.println("I'm trying to buy the book "+bookName);
		
		ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		
		/*
		addBehaviour(new Behaviour() {
			private int counter=0;
			
			@Override
			public boolean done() {
				// TODO Auto-generated method stub
				return (counter==10);
			}
			
			@Override
			public void action() {
				// TODO Auto-generated method stub
				System.out.println(".................");
				System.out.println("step"+counter);
				System.out.println("..................");
				++counter;
			}
		});
		*/
		
		/*parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {

			@Override
			public void action() {
				// TODO Auto-generated method stub
				System.out.println("One Shot Behaviour");
			}
			
		});*/
		/*
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			private int counter=0;
			@Override
			public void action() {
				// TODO Auto-generated method stub
				System.out.println("counter =>"+counter);
				++counter;
			}
		});
		
		
		 parallelBehaviour.addSubBehaviour(new TickerBehaviour(this, 1000) {
			
			@Override
			protected void onTick() {
				// TODO Auto-generated method stub
				System.out.println("Tic ");
				System.out.println(myAgent.getAID().getLocalName());
			}
		});
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy:HH:mm");
		Date date=null;
		try {
			date = dateFormat.parse("04/06/2021:11:23");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parallelBehaviour.addSubBehaviour(new WakerBehaviour(this, date) {
			@Override
			protected void onWake() {
				// TODO Auto-generated method stub
				System.out.println("Waker Behaviour.......");
			}
		});
		
		*/
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				// TODO Auto-generated method stub
				MessageTemplate messageTemplate=MessageTemplate.and(
						MessageTemplate.MatchPerformative(ACLMessage.CFP),
						MessageTemplate.MatchLanguage("fr")
						);
						
				ACLMessage aclMessage=receive(messageTemplate);
				if(aclMessage!=null) {
					System.out.println("Sender: "+aclMessage.getSender().getName());
					System.out.println("Content: "+aclMessage.getContent());
					System.out.println("Speech act: "+ACLMessage.getPerformative(aclMessage.getPerformative()));
					
					/*
					 * ACLMessage reply=new ACLMessage(ACLMessage.CONFIRM);
					 * reply.addReceiver(aclMessage.getSender()); reply.setContent("Pice=900");
					 * send(reply);
					 */
					consumerContainer.logMessage(aclMessage);
				}else block();
			}
		});
	}
	@Override
	protected void beforeMove() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Before migration from" +
			this.getContainerController().getContainerName());
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void afterMove() {
		// TODO Auto-generated method stub
		try {
			System.out.println("After migration to" +
			this.getContainerController().getContainerName());
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void takeDown() {
		// TODO Auto-generated method stub
		System.out.println("I'm gonna die");
	}
	@Override
	protected void onGuiEvent(GuiEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getType()==1) {
			String bookName=(String) evt.getParameter(0);
			//System.out.println("Agent -> "+getAID().getName()+"->"+bookName);
			ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
			aclMessage.setContent(bookName);
			aclMessage.addReceiver(new AID("BookBuyerAgent",AID.ISLOCALNAME));
			send(aclMessage);
		}
		
	}

}
