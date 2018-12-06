//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Random;
package doubleagent;
import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;

public class Greeter extends Agent{
	AID myAID = new AID("Greeter", AID.ISLOCALNAME);
	ACLMessage message = new ACLMessage(ACLMessage.INFORM);		String greeting = "Habari yako, naitw " + myAID.getLocalName();
	AMSAgentDescription [] agents = null;
	AID myID = getAID();
	
	@Override
	protected void setup() {
		//Add sending behavior 
		System.out.println("Waiting....");
		AID[] neighbours =getRecepients();
		addBehaviour(new WakerBehaviour(this,5000) {
			protected void handleElapsedTimeout() {
				//Print out available agents
				printDiscoveredAgents(myID);
				//Send greeting to greetee
				for(int i = 0; i < neighbours.length; i++) {
					greetGreetee(message, neighbours[i]);
				}
				System.out.println(myAgent.getLocalName() + ":Message sent");
			}
		});
		
		//Add problem solving logic
		addBehaviour(new HandleMessaging(this,5000));
	}	
	
	//Greeting method
	private void greetGreetee(ACLMessage message, AID remoteAMSf) {
		//Set message content
		message.setContent(greeting);
		message.setPerformative(ACLMessage.INFORM);	    
	    
	    message.addReceiver(remoteAMSf);

		//textEvaluator(message);
		this.send(message); 
	}
	
	//Method to search for available agents
	private AMSAgentDescription [] searchAgents() {
		try {
			SearchConstraints agentSearch = new SearchConstraints();
			agentSearch.setMaxResults(new Long(-1)); //Search to find all agents
			// this - refers to the searching agent, append each instance of AMSAgentDescripiton found
			agents = AMSService.search(this, new AMSAgentDescription(), agentSearch);
			return agents;
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	private void printDiscoveredAgents(AID myID) {
		searchAgents(); //Perform agents search
		System.out.println("I found the following agents");
		if(agents != null) {
			for(int i = 0; i < agents.length; i++) {
				AID agent = agents[i].getName();
				//Print out agents found
				System.out.println((i + 1) + " : " + agent.getName());
			}
			System.out.println();
		}
	}
	
/*	private void textEvaluator(ACLMessage message) {
		ACLMessage toEvaluator = new ACLMessage();
		toEvaluator.setContent(this.getLocalName() + " sent: " + message.getContent() + " to " + message.getAllIntendedReceiver().next()
				 + " Message Type " + message.getPerformative());
		
	    AID remoteAMSf = new AID("eval@192.168.137.27:1099/JADE", AID.ISGUID);
	    remoteAMSf.addAddresses("http://192.168.137.27:7778/acc");
		toEvaluator.addReceiver(remoteAMSf);
		
		this.send(toEvaluator);
	}
	*/
	private AID[] getRecepients(){
		
		// CEO
		AID greetee1 = new AID("Gretee@192.168.137.115:1099/JADE", AID.ISGUID);
		greetee1.addAddresses("http://192.168.137.115:7778/acc");
		
	
	      
	    
	   
	//    AID greetee4 = new AID("Gretee2@192.168.137.73:1099/JADE", AID.ISGUID);
	  //  greetee4.addAddresses("http://192.168.137.73:7778/acc");
	    //AID greetee5 = new AID("Gretee1@192.168.137.121:1099/JADE", AID.ISGUID);
	    //greetee5.addAddresses("http://192.168.137.121:7778/acc");
	    
	    
	    
	    return new AID[]{greetee1};
	}
}

//Where the logic is handled
class HandleMessaging extends TickerBehaviour{
	public HandleMessaging(Agent a, long period) {
		super(a, period);
	}

	public void onTick() {
		//Get message from greetee
		ACLMessage receivedMessage = myAgent.receive();
		String message = null;
		if(receivedMessage != null) {
			ACLMessage reply = receivedMessage.createReply();
			
			//If the received message is informative:::
		    System.out.println(myAgent.getLocalName() + " : Received Message: " + 
		    receivedMessage.getContent() + " ***From: " + receivedMessage.getSender().getName() + "***");
		    message = "";
			 
		  if(receivedMessage.getPerformative() == ACLMessage.INFORM && receivedMessage.getConversationId().equals("GREETING")) {  
			  message = "Hello " + receivedMessage.getSender().getLocalName() + " ";
		  }
			  
		  message += " how is Kenya?";
		  
		  reply.setPerformative(ACLMessage.REQUEST);
		  reply.setContent(message);
		  //textEvaluator(reply);
		  myAgent.send(reply);	  
		} else {
			block();
		} 
	}
	
/*	
	private String questionGenerator() {
		Random random = new Random();
		int min = 2, max = 1000;
		int a = min + random.nextInt(max);
		int b = min + random.nextInt(max);
		String question = String.format("max(%s,%s)",a,b);
		return question;
	} */
	
/*	private void textEvaluator(ACLMessage message) {
		ACLMessage toEvaluator = new ACLMessage();
		toEvaluator.setContent(myAgent.getLocalName() + " sent: " + message.getContent() + " to " + message.getAllIntendedReceiver().next()
				 + " Message Type " + message.getPerformative());
	    AID remoteAMSf = new AID("eval@192.168.137.27:1099/JADE", AID.ISGUID);
	    remoteAMSf.addAddresses("http://192.168.137.27:7778/acc");
		toEvaluator.addReceiver(remoteAMSf);
		myAgent.send(toEvaluator);
	}*/
}