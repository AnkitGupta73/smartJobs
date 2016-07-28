
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;

public class Company extends JPanel {
	public Company(){
setLayout(new GridBagLayout());
        
        JLabel title = new JLabel("Enter new Company details :");
        title.setFont(new Font("Century Schoolbook L", Font.BOLD, 32));
        
        JTextField name = new JTextField();
        name.setPreferredSize(new Dimension(300,25));
        name.setToolTipText("name");
        JTextField link = new JTextField();
        link.setPreferredSize(new Dimension(300,25));
        link.setToolTipText("link");
        JTextField number = new JTextField();
        number.setPreferredSize(new Dimension(300,25));
        number.setToolTipText("number");
        JTextField mail = new JTextField();
        mail.setPreferredSize(new Dimension(300,25));
        mail.setToolTipText("Email");
        /*JTextField marks_10 = new JTextField();
        marks_10.setPreferredSize(new Dimension(300,25));
        marks_10.setToolTipText("marks_10");
        JTextField marks_12 = new JTextField();
        marks_12.setPreferredSize(new Dimension(300,25));
        marks_12.setToolTipText("marks_12");
        JTextField exp = new JTextField();
        exp.setPreferredSize(new Dimension(300,25));
        exp.setToolTipText("Experience");
          */      
        JPanel skillListPanel = new JPanel();
        skillListPanel.setLayout(new GridLayout(0, 1));
        String skillListNames[] = {"C","HADOOP","c++","java","dot-net","das","sdedw","Effwew"};
        JCheckBox skills[] = new JCheckBox[skillListNames.length];
        for(int i=0;i<skills.length;i++){
            skills[i] = new JCheckBox(skillListNames[i]);
            skillListPanel.add(skills[i]);
        }
        JScrollPane skillList = new JScrollPane(skillListPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        skillList.setPreferredSize(new Dimension(120, 100));
        
        addComponent(title,1,0,10,1);
        addGap(2,1,100,100);
        int gap = 20;
        addComponent(new JLabel("Enter Name:"),0,2,2,1);
        addComponent(name,4,2,1,1);
        addGap(1,3,0,gap);
        addComponent(new JLabel("Enter Profile Link:"),0,4,2,1);
        addComponent(link,4,4,1,1);
        addGap(1,5,0,gap);
        addComponent(new JLabel("Enter Email ID:"),0,6,2,1);
        addComponent(mail,4,6,1,1);
        addGap(1,7,0,gap);
        addComponent(new JLabel("Enter Contact Number:"),0,8,2,1);
        addComponent(number,4,8,1,1);
        addGap(1,9,0,gap);
        //addComponent(new JLabel("Required 10th marks:"),0,6,2,1);
        //addComponent(marks_10,4,10,1,1);
        //addComponent(marks_10,4,6,1,1);
        //addGap(1,11,0,gap);
        //addGap(1,7,0,gap);
        //addComponent(new JLabel("Required 12th marks:"),0,8,2,1);
        //addComponent(marks_12,4,12,1,1);
        //addComponent(marks_12,4,8,1,1);
        //addGap(1,13,0,gap);
        //addGap(1,9,0,gap);
        //addComponent(new JLabel("Required Experience(in yrs):"),0,10,2,1);
        //addComponent(exp,4,10,1,1);
        //addGap(1,11,0,gap);
        //addComponent(new JLabel("Skill set:"),0,12,2,1);
        //addComponent(skillList,4,12,1,1);
        // addGap(1,13,0,gap);
        
      
     
       // imageButton.setHorizontalTextPosition(AbstractButton.CENTER);
        //imageButton.setVerticalTextPosition(AbstractButton.BOTTOM);
      
      
        
        JButton submit = new JButton("submit");
        addComponent(submit,2,13,1,1);
        JButton Home = new JButton("Home");
        addComponent(Home,3,13,1,1);
        submit.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            	Component cmp[] = getComponents();
				for (Component component : cmp) {
					component.setEnabled(false);
				}
				String ns = "http://www.iiitb.org/job#";
				OntModel portal = ModelFactory.createOntologyModel();
				portal.read(FileManager.get().open("ontologies/portal.owl"),"");
				
				OntClass applicant = portal.getOntClass(ns+"Company");
				Random rd = new Random();
				Individual ind = applicant.createIndividual(ns+"C_"+mail.getText()+"_"+rd.nextInt(10000));
				
				portal.read(FileManager.get().open("ontologies/company.owl"),"");
				
				Property rsc;
				Resource obj;
				rsc = portal.getProperty(ns+"hasName");
				portal.add(ind,rsc,name.getText());
				
				rsc = portal.getProperty(ns+"hasEmail");
				portal.add(ind,rsc,mail.getText());
				
				rsc = portal.getProperty(ns+"hasContactNumber");
				portal.add(ind,rsc,number.getText());
				
				rsc = portal.getProperty(ns+"hasProfileLink");
				portal.add(ind,rsc,link.getText());
				
				try {
					portal.write(new FileOutputStream(new File("ontologies/company.owl")));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				reset();
				Main.showCard("Menu");
            }

			private void reset() {
				Component cmp[] = getComponents();
				for (Component component : cmp) {
					component.setEnabled(true);
				}
				name.setText("");
				mail.setText("");
				number.setText("");
				link.setText("");
			}
        });
        
        Home.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.showCard("Menu");
				
			}
		});
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
