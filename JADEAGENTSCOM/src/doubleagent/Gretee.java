/**
 * 
 */
package doubleagent;

import javax.swing.JOptionPane;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * @author ALI
 *
 */
public class Gretee extends Agent {

	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		 System.out.println("Greetee started...");
		 System.out.println("Hello! Greeter "+getAID().getName()+" is ready.");
		 this.addBehaviour(new CyclicBehaviour() {
		         @Override
		         public void action() {
		         ACLMessage msg=receive();
		         boolean received=false;
		                if(msg != null && !received)
		               {
		                    JOptionPane.showMessageDialog(null,"Message Received "+msg.getContent());
		                    received=true;
		                }
		            
		         }
		     });
		
	}
	

}
