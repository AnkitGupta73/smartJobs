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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;

public class Job extends JPanel{
	int size=0;
	int csize=0;
	public Job() {
        setLayout(new GridBagLayout());
        
        JLabel title = new JLabel("Enter new job details :");
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
        JTextField marks_10 = new JTextField();
        marks_10.setPreferredSize(new Dimension(300,25));
        marks_10.setToolTipText("marks_10");
        JTextField marks_12 = new JTextField();
        marks_12.setPreferredSize(new Dimension(300,25));
        marks_12.setToolTipText("marks_12");
        JTextField exp = new JTextField();
        exp.setPreferredSize(new Dimension(300,25));
        exp.setToolTipText("Experience");
                
        JPanel skillListPanel = new JPanel();
        skillListPanel.setLayout(new GridLayout(0, 1));
        String skillListNames[] = new String[1000];
        
		String ns = "http://www.iiitb.org/job#";
		OntModel portal = ModelFactory.createOntologyModel();
		portal.read(FileManager.get().open("ontologies/portal.owl"),"");
		String queryString =        
		  	      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
		  	        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
		  	        "PREFIX ns: <"+ns+"> "+
		  	        "select ?user "+
		  	        "where { "+
		  	         "?z a ns:Skill. "+
		  	         "?z ns:hasName ?user "+
		  	        "} \n ";
		  	    Query query = QueryFactory.create(queryString);
		  	    QueryExecution qe = QueryExecutionFactory.create(query, portal);
		  	    ResultSet rs = qe.execSelect();
		  	    while(rs.hasNext()){
		  	    	skillListNames[size++]=rs.next().getLiteral("user").getString();
		  	    }
        
        JCheckBox skills[] = new JCheckBox[skillListNames.length];
        for(int i=0;i<size;i++){
            skills[i] = new JCheckBox(skillListNames[i]);
            skillListPanel.add(skills[i]);
        }
        
        JPanel companyNamesPanel = new JPanel();
        companyNamesPanel.setLayout(new GridLayout(0, 1));
        
        String CompanyNames[] = new String[1000];
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton company[] = new JRadioButton[CompanyNames.length];
        
        portal.read(FileManager.get().open("ontologies/company.owl"),"");
		queryString =        
		  	      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
		  	        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
		  	        "PREFIX ns: <"+ns+"> "+
		  	        "select ?z ?user "+
		  	        "where { "+
		  	         "?z a ns:Company. "+
		  	         "?z ns:hasName ?user "+
		  	        "} \n ";
		  	    query = QueryFactory.create(queryString);
		  	    qe = QueryExecutionFactory.create(query, portal);
		  	    rs = qe.execSelect();
		  	    while(rs.hasNext()){
		  	    	QuerySolution tmp = rs.next();
		  	    	CompanyNames[csize]=tmp.getLiteral("user").getString();
		  	    	company[csize] = new JRadioButton(CompanyNames[csize]);
		  	    	company[csize].setActionCommand(tmp.getResource("z").toString());
		  	    	csize++;
		  	    }
		
        for(int i=0;i<csize;i++){
        	companyNamesPanel.add(company[i]);
        	buttonGroup.add(company[i]);
        }
        JScrollPane skillList = new JScrollPane(skillListPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        skillList.setPreferredSize(new Dimension(120, 100));
        
        JScrollPane companyList = new JScrollPane(companyNamesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        companyList.setPreferredSize(new Dimension(120, 100));
        
        
        addComponent(title,1,0,10,1);
        addGap(2,1,100,100);
        int gap = 20;
        addComponent(new JLabel("Enter Name:"),0,2,2,1);
        addComponent(name,4,2,1,1);
        addGap(1,3,0,gap);
        addComponent(new JLabel("Enter Profile Link:"),0,4,2,1);
        addComponent(link,4,4,1,1);
        addGap(1,5,0,gap);
     //   addComponent(new JLabel("Enter Email ID:"),0,6,2,1);
     //   addComponent(mail,4,6,1,1);
     //   addGap(1,7,0,gap);
     //   addComponent(new JLabel("Enter Contact Number:"),0,8,2,1);
     //   addComponent(number,4,8,1,1);
     //   addGap(1,9,0,gap);
        addComponent(new JLabel("Required 10th marks:"),0,6,2,1);
        //addComponent(marks_10,4,10,1,1);
        addComponent(marks_10,4,6,1,1);
        //addGap(1,11,0,gap);
        addGap(1,7,0,gap);
        addComponent(new JLabel("Required 12th marks:"),0,8,2,1);
        //addComponent(marks_12,4,12,1,1);
        addComponent(marks_12,4,8,1,1);
      //  addGap(1,13,0,gap);
        addGap(1,9,0,gap);
        addComponent(new JLabel("Required Experience(in yrs):"),0,10,2,1);
        addComponent(exp,4,10,1,1);
        addGap(1,11,0,gap);
        addComponent(new JLabel("Skill set:"),0,12,2,1);
        addComponent(skillList,4,12,1,1);
        addGap(1,13,0,gap);
        addComponent(new JLabel("Select Company:"),0,14,2,1);
        addComponent(companyList,4,14,1,1);
        addGap(1,15,0,gap);
      
     
       // imageButton.setHorizontalTextPosition(AbstractButton.CENTER);
        //imageButton.setVerticalTextPosition(AbstractButton.BOTTOM);
      
      
        
        JButton submit = new JButton("submit");
        addComponent(submit,2,18,1,1);
        JButton Home = new JButton("Home");
        addComponent(Home,3,18,1,1);
        submit.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent e) {
            	/*String tmp = buttonGroup.getSelection().getActionCommand();
				System.out.println(tmp);
            	*/
            	Component cmp[] = getComponents();
				for (Component component : cmp) {
					component.setEnabled(false);
				}
				OntClass applicant = portal.getOntClass(ns+"Job");
				Random rd = new Random();
				Individual ind = applicant.createIndividual(ns+"J_"+link.getText()+"_"+rd.nextInt(10000));
				
				Property rsc;
				Resource obj;
				rsc = portal.getProperty(ns+"hasName");
				portal.add(ind,rsc,name.getText());
				
				rsc = portal.getProperty(ns+"requiresExperience");
				portal.add(ind,rsc,exp.getText());
				
				rsc = portal.getProperty(ns+"hasProfileLink");
				portal.add(ind,rsc,link.getText());
				
				rsc = portal.getProperty(ns+"requiresTenthMarks");
				portal.add(ind,rsc,marks_10.getText());
				
				rsc = portal.getProperty(ns+"requiresTwelthMarks");
				portal.add(ind,rsc,marks_12.getText());
				
				String tmp = buttonGroup.getSelection().getActionCommand();
				obj = portal.getResource(tmp);
				rsc = portal.getProperty(ns+"isOfferedBy");
				portal.add(ind,rsc,obj);
				
				
				for(int i=0;i<size;i++){
					if(!skills[i].isSelected())continue;
					rsc = portal.getProperty(ns+"requires");
					obj = portal.getResource(ns+skillListNames[i]);
					portal.add(ind,rsc,obj);
				}
				
				
				
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
				marks_10.setText("");
				marks_12.setText("");
				exp.setText("");
				link.setText("");
				for(int i=0;i<size;i++)skills[i].setSelected(false);
				for(int i=0;i<csize;i++)company[i].setSelected(false);
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