import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.util.FileManager;

public class Search extends JPanel{
	JTable table;
	JScrollPane jsp;
	public Search(){
		setLayout(new GridBagLayout());
		
		addComponent(new JLabel("Search :"), 0, 0, 1, 1);
		addGap(1, 0, 10, 1);
		
		JTextField searchbar = new JTextField();
		searchbar.setPreferredSize(new Dimension(500,30));
		addComponent(searchbar,2,0,8,1);
		addGap(11, 0, 10, 1);
		
		JComboBox<String> type = new JComboBox();
		type.addItem("Course");
		type.addItem("Job");
		type.addItem("Applicant");
		
		type.setPreferredSize(new Dimension(100,30));
		addComponent(type,12,0,1,1);
		addGap(13, 0, 10, 1);
		addGap(0, 1, 0, 100);
		
		table = new JTable();
		table.setRowHeight(30); 
		jsp = new JScrollPane(table);
		jsp.setPreferredSize(new Dimension(800, 300));
		
		JButton home = new JButton("Home");
		home.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.showCard("Menu");
			}
		});
		
		
		JButton search = new JButton("Search");
		addComponent(search,14,0,1,1);
		addGap(15, 0, 10, 1);
		addComponent(home,16,0,1,1);
		
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(type.getSelectedIndex()==0){
					String tmp = searchbar.getText();
					//System.out.println("a"+tmp+"a");
					Model portal = ModelFactory.createOntologyModel();
					portal.read(FileManager.get().open("ontologies/portal.owl"),"");
					portal.read(FileManager.get().open("ontologies/mapping.owl"),"");
					try {
						FileInputStream fin;
						fin = new FileInputStream("ontologies/trainer_cnt");
						DataInputStream in = new DataInputStream(fin);
						int fcnt = in.readInt();
						in.close();
						fin.close();
						for(int i=0;i<fcnt;i++)portal.read(FileManager.get().open("ontologies/trainer"+i+".owl"),"");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//portal.read(FileManager.get().open("ontologies/company.owl"),"");
					//portal.read(FileManager.get().open("ontologies/trainer.owl"),"");
					portal = infer(portal);
					String ns = "http://www.iiitb.org/job#";
					
					String queryString =        
					  	      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
					  	        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
					  	        "PREFIX ns: <"+ns+"> "+
					  	        "select ?name ?trainer ?skill ?link "+
					  	        "where { "+
					  	        "?course a ns:Course. "+
					  	        "?course ns:hasName ?name. "+
					  	        "FILTER regex(?name,\""+tmp+"\",\"i\"). "+
					  	        "?course ns:addSkill ?s. "+
					  	        "?s ns:hasName ?skill. "+
					  	        "?course ns:isTaughtBy ?t. "+
					  	        "?t ns:hasName ?trainer. "+
					  	        "?course ns:hasProfileLink ?link. "+
					  	        "} \n ";
					Query query = QueryFactory.create(queryString);

					QueryExecution qe = QueryExecutionFactory.create(query, portal);
					ResultSet rs = qe.execSelect();
					//ResultSetFormatter.out(System.out,rs);
				    int size = 20;
					String data[][] = new String[size][4];
					String names[] = {"Course Name","Taught By","Skill","Link"};
					int i = 0;
					while(rs.hasNext()){
						QuerySolution qs = rs.next();
						data[i][0] = qs.getLiteral("name").getString();
						data[i][1] = qs.getLiteral("trainer").getString();
						data[i][2] = qs.getLiteral("skill").getString();
						data[i][3] = qs.getLiteral("link").getString();
						i++;
					}
					
					table.setModel(new DefaultTableModel(data,names));
					
					addComponent(jsp,0,3,100,100);
					revalidate();
					repaint();
					qe.close();

				}
				else if(type.getSelectedIndex()==1){
					String tmp = searchbar.getText();
					//System.out.println("a"+tmp+"a");
					Model portal = ModelFactory.createOntologyModel();
					portal.read(FileManager.get().open("ontologies/portal.owl"),"");
					//portal.read(FileManager.get().open("ontologies/mapping.owl"),"");
					//portal.read(FileManager.get().open("ontologies/trainer.owl"),"");
					portal.read(FileManager.get().open("ontologies/company.owl"),"");
					//portal.read(FileManager.get().open("ontologies/trainer.owl"),"");
					portal = infer(portal);
					String ns = "http://www.iiitb.org/job#";
					
					String queryString =        
					  	      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
					  	        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
					  	        "PREFIX ns: <"+ns+"> "+
					  	        "select ?name ?company ?skill ?tenth ?twelth ?exp ?link "+
					  	        "where { "+
					  	        "?job a ns:Job. "+
					  	        "?job ns:hasName ?name. "+
					  	        "FILTER regex(?name,\""+tmp+"\",\"i\"). "+
					  	        "?job ns:isOfferedBy ?c. "+
					  	        "?c ns:hasName ?company. "+
					  	        "?job ns:requires ?s. "+
					  	        "?s ns:hasName ?skill. "+
					  	        "?job ns:hasProfileLink ?link. "+
					  	        "?job ns:requiresTenthMarks ?tenth. "+
					  	        "?job ns:requiresTwelthMarks ?twelth. "+
					  	        "?job ns:requiresExperience ?exp. "+
					  	       "} \n ";
					Query query = QueryFactory.create(queryString);

					QueryExecution qe = QueryExecutionFactory.create(query, portal);
					ResultSet rs = qe.execSelect();
					//ResultSetFormatter.out(System.out,rs);
				    int size = 20;
					String data[][] = new String[size][7];
					String names[] = {"Name","Company","Skills","Tenth Marks","Twelth Marks","Experience","Link"};
					int i = 0;
					while(rs.hasNext()){
						QuerySolution qs = rs.next();
						data[i][0] = qs.getLiteral("name").getString();
						data[i][1] = qs.getLiteral("company").getString();
						data[i][2] = qs.getLiteral("skill").getString();
						data[i][3] = qs.getLiteral("tenth").getString();
						data[i][4] = qs.getLiteral("twelth").getString();
						data[i][5] = qs.getLiteral("exp").getString();
						data[i][6] = qs.getLiteral("link").getString();
						i++;
					}
					
					queryString =        
					  	      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
					  	        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
					  	        "PREFIX ns: <"+ns+"> "+
					  	        "select ?name ?company ?skill ?tenth ?twelth ?exp ?link "+
					  	        "where { "+
					  	        "?job a ns:Job. "+
					  	        "?job ns:requires ?s. "+
					  	        "?s ns:hasName ?skill. "+
					  	        "FILTER regex(?skill,\""+tmp+"\",\"i\"). "+
					  	        "?job ns:isOfferedBy ?c. "+
					  	        "?c ns:hasName ?company. "+
					  	        "?job ns:hasName ?name. "+
					  	        "?job ns:hasProfileLink ?link. "+
					  	        "?job ns:requiresTenthMarks ?tenth. "+
					  	        "?job ns:requiresTwelthMarks ?twelth. "+
					  	        "?job ns:requiresExperience ?exp. "+
					  	       "} \n ";
					query = QueryFactory.create(queryString);
					qe = QueryExecutionFactory.create(query, portal);
					rs = qe.execSelect();
					while(rs.hasNext()){
						QuerySolution qs = rs.next();
						data[i][0] = qs.getLiteral("name").getString();
						data[i][1] = qs.getLiteral("company").getString();
						data[i][2] = qs.getLiteral("skill").getString();
						data[i][3] = qs.getLiteral("tenth").getString();
						data[i][4] = qs.getLiteral("twelth").getString();
						data[i][5] = qs.getLiteral("exp").getString();
						data[i][6] = qs.getLiteral("link").getString();
						i++;
					}
					
					queryString =        
					  	      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
					  	        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
					  	        "PREFIX ns: <"+ns+"> "+
					  	        "select ?name ?company ?skill ?tenth ?twelth ?exp ?link "+
					  	        "where { "+
					  	        "?job a ns:Job. "+
					  	        "?job ns:isOfferedBy ?c. "+
					  	        "?c ns:hasName ?company. "+
					  	        "FILTER regex(?company,\""+tmp+"\",\"i\"). "+
					  	        "?job ns:hasName ?name. "+
					  	        "?job ns:requires ?s. "+
					  	        "?s ns:hasName ?skill. "+
					  	        "?job ns:hasProfileLink ?link. "+
					  	        "?job ns:requiresTenthMarks ?tenth. "+
					  	        "?job ns:requiresTwelthMarks ?twelth. "+
					  	        "?job ns:requiresExperience ?exp. "+
					  	       "} \n ";
					query = QueryFactory.create(queryString);
					qe = QueryExecutionFactory.create(query, portal);
					rs = qe.execSelect();
					while(rs.hasNext()){
						QuerySolution qs = rs.next();
						data[i][0] = qs.getLiteral("name").getString();
						data[i][1] = qs.getLiteral("company").getString();
						data[i][2] = qs.getLiteral("skill").getString();
						data[i][3] = qs.getLiteral("tenth").getString();
						data[i][4] = qs.getLiteral("twelth").getString();
						data[i][5] = qs.getLiteral("exp").getString();
						data[i][6] = qs.getLiteral("link").getString();
						i++;
					}


					
					table.setModel(new DefaultTableModel(data,names));
					
					addComponent(jsp,0,3,100,100);
					revalidate();
					repaint();
					qe.close();

				}
				if(type.getSelectedIndex()==2){
					String tmp = searchbar.getText();
					//System.out.println("a"+tmp+"a");
					Model portal = ModelFactory.createOntologyModel();
					portal.read(FileManager.get().open("ontologies/portal.owl"),"");
					//portal.read(FileManager.get().open("ontologies/mapping.owl"),"");
					//portal.read(FileManager.get().open("ontologies/trainer.owl"),"");
					//portal.read(FileManager.get().open("ontologies/company.owl"),"");
					portal.read(FileManager.get().open("ontologies/applicants.owl"),"");
					portal = infer(portal);
					String ns = "http://www.iiitb.org/job#";
					
					String queryString =        
					  	      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
					  	        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
					  	        "PREFIX ns: <"+ns+"> "+
					  	        "select ?name ?skill ?link ?email ?number ?tenth ?twelth ?exp "+
					  	        "where { "+
					  	        "?appl a ns:Applicant. "+
					  	        "?appl ns:hasName ?name. "+
					  	        "FILTER regex(?name,\""+tmp+"\",\"i\"). "+
					  	        "?appl ns:hasSkill ?s. "+
					  	        "?s ns:hasName ?skill. "+
					  	        "?appl ns:hasProfileLink ?link. "+
					  	        "?appl ns:hasEmail ?email. "+
					  	        "?appl ns:hasContactNumber ?number. "+
					  	    	"?appl ns:hasTenthMarks ?tenth. "+
					  	    	"?appl ns:hasTwelthMarks ?twelth. "+
					  	    	"?appl ns:hasExperience ?exp. "+
					  	    "} \n ";
					Query query = QueryFactory.create(queryString);

					QueryExecution qe = QueryExecutionFactory.create(query, portal);
					ResultSet rs = qe.execSelect();
					//ResultSetFormatter.out(System.out,rs);
				    int size = 20;
					String data[][] = new String[size][8];
					String names[] = {"Name","Skill","Link","Email","Contact Number","Tenth Marks","Twelth Marks","Experience"};
					int i = 0;
					while(rs.hasNext()){
						QuerySolution qs = rs.next();
						data[i][0] = qs.getLiteral("name").getString();
						data[i][1] = qs.getLiteral("skill").getString();
						data[i][2] = qs.getLiteral("link").getString();
						data[i][3] = qs.getLiteral("email").getString();
						data[i][4] = qs.getLiteral("number").getString();
						data[i][5] = qs.getLiteral("tenth").getString();
						data[i][6] = qs.getLiteral("twelth").getString();
						data[i][7] = qs.getLiteral("exp").getString();
						i++;
					}
					
					queryString =        
					  	      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
					  	        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
					  	        "PREFIX ns: <"+ns+"> "+
					  	        "select ?name ?skill ?link ?email ?number ?tenth ?twelth ?exp "+
					  	        "where { "+
					  	        "?appl a ns:Applicant. "+
					  	        "?appl ns:hasSkill ?s. "+
					  	        "?s ns:hasName ?skill. "+
					  	        "FILTER regex(?skill,\""+tmp+"\",\"i\"). "+
					  	        "?appl ns:hasName ?name. "+
					  	        "?appl ns:hasProfileLink ?link. "+
					  	        "?appl ns:hasEmail ?email. "+
					  	        "?appl ns:hasContactNumber ?number. "+
					  	    	"?appl ns:hasTenthMarks ?tenth. "+
					  	    	"?appl ns:hasTwelthMarks ?twelth. "+
					  	    	"?appl ns:hasExperience ?exp. "+
					  	    "} \n ";
					query = QueryFactory.create(queryString);
					qe = QueryExecutionFactory.create(query, portal);
					rs = qe.execSelect();
					while(rs.hasNext()){
						QuerySolution qs = rs.next();
						data[i][0] = qs.getLiteral("name").getString();
						data[i][1] = qs.getLiteral("skill").getString();
						data[i][2] = qs.getLiteral("link").getString();
						data[i][3] = qs.getLiteral("email").getString();
						data[i][4] = qs.getLiteral("number").getString();
						data[i][5] = qs.getLiteral("tenth").getString();
						data[i][6] = qs.getLiteral("twelth").getString();
						data[i][7] = qs.getLiteral("exp").getString();
						i++;
					}
					
					table.setModel(new DefaultTableModel(data,names));
					
					addComponent(jsp,0,3,100,100);
					revalidate();
					repaint();
					qe.close();

				}
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
	Model infer(Model schema){
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		return ModelFactory.createInfModel(reasoner, schema);
	}
}
