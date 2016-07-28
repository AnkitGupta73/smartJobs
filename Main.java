import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {

	static private JPanel cards;
	static private CardLayout cl;
	
	static void showCard(String crd){
		cl.show(cards, crd);
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setBounds(100, 100, 450, 300);
		cards = new JPanel();
		cards.setLayout(new CardLayout());
		cards.add(new AddTrainer(),"Trainer");
		cards.add(new Menu(),"Menu");
		cards.add(new Registration(),"Registration");
		cards.add(new Company(),"Company");
		cards.add(new Job(),"Job");
		cards.add(new Search(),"Search");
		
		setContentPane(cards);
		cl = (CardLayout)cards.getLayout();
		showCard("Menu");
		pack();
		setVisible(true);
	}
}
