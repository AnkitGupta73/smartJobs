import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel {

	/**
	 * Create the panel.
	 */
	public Menu() {
		Dimension dm = new Dimension(200,50);
		JButton trainer = new JButton("Add Trainer Ontology");
		trainer.setPreferredSize(dm);
		trainer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.showCard("Trainer");
			}
		});
		
		JButton company = new JButton("Add Company's Details");
		company.setPreferredSize(dm);
		company.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.showCard("Company");
			}
		});
		
		JButton search = new JButton("Search");
		search.setPreferredSize(dm);
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.showCard("Search");
			}
		});

		JButton regist = new JButton("Add Applicant's Details");
		regist.setPreferredSize(dm);
		regist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.showCard("Registration");
			}
		});
		
		
		JButton job = new JButton("Add Job's Details");
		job.setPreferredSize(dm);
		job.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.showCard("Job");
			}
		});
		setLayout(new GridBagLayout());
		addComponent(trainer,0,0,1,1);
		addGap(0,2,10,30);
		addComponent(company,0,3,1,1);
		addGap(0,4,10,30);
		addComponent(regist,0,5,1,1);
		addGap(0,6,10,30);
		addComponent(job,0,7,1,1);
		addGap(0,8,10,30);
		addComponent(search,0,9,1,1);
	}
	void addComponent(Component c,int x,int y,int w,int h){
        GridBagConstraints gbc = new GridBagConstraints();
          gbc.fill = GridBagConstraints.NONE;
          gbc.gridx = x;
          gbc.gridy = y;
          gbc.gridwidth = w;
          gbc.gridheight = h;
          add(c,gbc);
    }
    void addGap(int x,int y,int w,int h){
        GridBagConstraints gbc = new GridBagConstraints();
          gbc.fill = GridBagConstraints.NONE;
          gbc.gridx = x;
          gbc.gridy = y;
          gbc.insets = new Insets(h,w,0,0);
          add(new JLabel(""),gbc);
    }
}