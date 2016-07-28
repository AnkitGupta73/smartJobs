import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;

public class AddTrainer extends JPanel {
	
	JPanel th;
	File onto;
	int fcnt;
	/**
	 * Create the panel.
	 */
	public AddTrainer() {
		setLayout(new GridBagLayout());
		th = this;
		JLabel title = new JLabel("Enter mapping for trainer classes :");
		title.setFont(new Font("Century Schoolbook L", Font.BOLD, 28));
		
		JLabel title1 = new JLabel("Enter mapping for trainer properties :");
		title1.setFont(new Font("Century Schoolbook L", Font.BOLD, 28));
		
		
		JComboBox course = new JComboBox();
		course.setPreferredSize(new Dimension(300,25));
		course.setToolTipText("course");
		JComboBox skill = new JComboBox();
		skill.setPreferredSize(new Dimension(300,25));
		skill.setToolTipText("skill");
		JComboBox trainer = new JComboBox();
		trainer.setPreferredSize(new Dimension(300,25));
		trainer.setToolTipText("trainer");
		
		JComboBox add_skill = new JComboBox();
		add_skill.setPreferredSize(new Dimension(300,25));
		JComboBox has_skill = new JComboBox();
		has_skill.setPreferredSize(new Dimension(300,25));
		JComboBox possessed_by = new JComboBox();
		possessed_by.setPreferredSize(new Dimension(300,25));
		JComboBox taught_by = new JComboBox();
		taught_by.setPreferredSize(new Dimension(300,25));
		JComboBox obtained_by = new JComboBox();
		obtained_by.setPreferredSize(new Dimension(300,25));
		JComboBox teaches_course = new JComboBox();
		teaches_course.setPreferredSize(new Dimension(300,25));
		JComboBox has_number = new JComboBox();
		has_number.setPreferredSize(new Dimension(300,25));
		JComboBox has_email = new JComboBox();
		has_email.setPreferredSize(new Dimension(300,25));
		JComboBox has_name = new JComboBox();
		has_name.setPreferredSize(new Dimension(300,25));
		JComboBox has_link = new JComboBox();
		has_link.setPreferredSize(new Dimension(300,25));
		
		course.setEnabled(false);
		skill.setEnabled(false);
		trainer.setEnabled(false);
		add_skill.setEnabled(false);
		has_email.setEnabled(false);
		has_link.setEnabled(false);
		has_name.setEnabled(false);
		has_number.setEnabled(false);
		has_skill.setEnabled(false);
		possessed_by.setEnabled(false);
		taught_by.setEnabled(false);
		obtained_by.setEnabled(false);
		teaches_course.setEnabled(false);
		
		
		
		JTextField flabel = new JTextField("");
		flabel.setPreferredSize(new Dimension(300, 20));
		flabel.setEditable(false);
		
		
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("choose owl file", "owl");
		fc.setFileFilter(filter);
		
		JButton submit = new JButton("SUBMIT");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Component cmp[] = getComponents();
				for (Component component : cmp) {
					component.setEnabled(false);
				}
				
				Model portal = ModelFactory.createOntologyModel();
				portal.read(FileManager.get().open("ontologies/portal.owl"),"");
				portal.read(FileManager.get().open("ontologies/trainer"+fcnt+".owl"),"");
				String ns = "http://www.iiitb.org/job#";
				
				Model mapping = ModelFactory.createOntologyModel();
				Resource rsc,obj;
				
				rsc = portal.getResource(ns+"Course");
				obj = portal.getResource(ns+course.getSelectedItem());
				mapping.add(rsc,OWL.equivalentClass,obj);

				rsc = portal.getResource(ns+"Skill");
				obj = portal.getResource(ns+skill.getSelectedItem());
				mapping.add(rsc,OWL.equivalentClass,obj);

				rsc = portal.getResource(ns+"Trainer");
				obj = portal.getResource(ns+trainer.getSelectedItem());
				mapping.add(rsc,OWL.equivalentClass,obj);

				if(add_skill.getSelectedItem()!="NONE"){
					rsc = portal.getResource(ns+"addSkill");
					obj = portal.getResource(ns+add_skill.getSelectedItem());
					mapping.add(rsc,OWL.equivalentProperty,obj);
				}
				
				if(obtained_by.getSelectedItem()!="NONE"){
					rsc = portal.getResource(ns+"obtainedBy");
					obj = portal.getResource(ns+obtained_by.getSelectedItem());
					mapping.add(rsc,OWL.equivalentProperty,obj);
				}
				
				if(has_skill.getSelectedItem()!="NONE"){
					rsc = portal.getResource(ns+"hasSkill");
					obj = portal.getResource(ns+has_skill.getSelectedItem());
					mapping.add(rsc,OWL.equivalentProperty,obj);
				}
				
				if(possessed_by.getSelectedItem()!="NONE"){
					rsc = portal.getResource(ns+"isPossessedBy");
					obj = portal.getResource(ns+possessed_by.getSelectedItem());
					mapping.add(rsc,OWL.equivalentProperty,obj);
				}
				
				if(taught_by.getSelectedItem()!="NONE"){
					rsc = portal.getResource(ns+"isTaughtBy");
					obj = portal.getResource(ns+taught_by.getSelectedItem());
					mapping.add(rsc,OWL.equivalentProperty,obj);
				}
				
				if(teaches_course.getSelectedItem()!="NONE"){
					rsc = portal.getResource(ns+"teachesCourse");
					obj = portal.getResource(ns+teaches_course.getSelectedItem());
					mapping.add(rsc,OWL.equivalentProperty,obj);
				}
				
				if(has_name.getSelectedItem()!="NONE"){
					rsc = portal.getResource(ns+"hasName");
					obj = portal.getResource(ns+has_name.getSelectedItem());
					mapping.add(rsc,OWL.equivalentProperty,obj);
				}
				
				if(has_number.getSelectedItem()!="NONE"){
					rsc = portal.getResource(ns+"hasContactNumber");
					obj = portal.getResource(ns+has_number.getSelectedItem());
					mapping.add(rsc,OWL.equivalentProperty,obj);
				}
				
				if(has_email.getSelectedItem()!="NONE"){
					rsc = portal.getResource(ns+"hasEmail");
					obj = portal.getResource(ns+has_email.getSelectedItem());
					mapping.add(rsc,OWL.equivalentProperty,obj);
				}
				
				if(has_link.getSelectedItem()!="NONE"){
					rsc = portal.getResource(ns+"hasProfileLink");
					obj = portal.getResource(ns+has_link.getSelectedItem());
					mapping.add(rsc,OWL.equivalentProperty,obj);
				}
				try{
				mapping.read(FileManager.get().open("ontologies/mapping.owl"),"");
				}
				catch(Exception ex){
					
				}
				try {
					mapping.write(new FileOutputStream(new File("ontologies/mapping.owl")));
					FileOutputStream fout = new FileOutputStream("ontologies/trainer_cnt");
					BufferedOutputStream buffOut = new BufferedOutputStream(fout);
					DataOutputStream out = new DataOutputStream(fout);
					out.writeInt(fcnt+1);
					out.close();
					buffOut.close();
					fout.close();
				} catch (IOException e1) {
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
				course.removeAllItems();
				skill.removeAllItems();
				trainer.removeAllItems();
				flabel.setText("");
				add_skill.removeAllItems();
				obtained_by.removeAllItems();
				has_skill.removeAllItems();
				possessed_by.removeAllItems();
				taught_by.removeAllItems();
				teaches_course.removeAllItems();
				has_name.removeAllItems();
				has_number.removeAllItems();
				has_email.removeAllItems();
				has_link.removeAllItems();
				
				course.setEnabled(false);
				skill.setEnabled(false);
				trainer.setEnabled(false);
				add_skill.setEnabled(false);
				has_email.setEnabled(false);
				has_link.setEnabled(false);
				has_name.setEnabled(false);
				has_number.setEnabled(false);
				has_skill.setEnabled(false);
				possessed_by.setEnabled(false);
				taught_by.setEnabled(false);
				obtained_by.setEnabled(false);
				teaches_course.setEnabled(false);
				
			}
		});

		JButton choose = new JButton("choose");
		choose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fc.showOpenDialog(th);
				onto = fc.getSelectedFile();
				flabel.setText(onto.getName());
				try {
					FileInputStream fin = new FileInputStream("ontologies/trainer_cnt");
					DataInputStream in = new DataInputStream(fin);
					fcnt = in.readInt();
					in.close();
					fin.close();
					FileUtils.copyFile(onto,new File("ontologies/trainer"+fcnt+".owl"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				add_skill.addItem("NONE");
				has_email.addItem("NONE");
				has_link.addItem("NONE");
				has_name.addItem("NONE");
				has_number.addItem("NONE");
				has_skill.addItem("NONE");
				possessed_by.addItem("NONE");
				taught_by.addItem("NONE");
				obtained_by.addItem("NONE");
				teaches_course.addItem("NONE");
				
				
				OntModel tmod = ModelFactory.createOntologyModel();
				tmod.read(FileManager.get().open("ontologies/trainer"+fcnt+".owl"),"");
				Iterator it = tmod.listClasses();
				while(it.hasNext()){
					String tmp = ((OntClass)it.next()).getLocalName(); 
					course.addItem(tmp);
					skill.addItem(tmp);
					trainer.addItem(tmp);
				}
				it = tmod.listObjectProperties();
				while(it.hasNext()){
					String tmp = ((ObjectProperty)it.next()).getLocalName(); 
					has_skill.addItem(tmp);
					possessed_by.addItem(tmp);
					obtained_by.addItem(tmp);
					teaches_course.addItem(tmp);
					taught_by.addItem(tmp);
					add_skill.addItem(tmp);
				}
				it = tmod.listDatatypeProperties();
				while(it.hasNext()){
					String tmp = ((DatatypeProperty)it.next()).getLocalName(); 
					has_email.addItem(tmp);
					has_name.addItem(tmp);
					has_link.addItem(tmp);
					has_number.addItem(tmp);
				}
				
				course.setEnabled(true);
				skill.setEnabled(true);
				trainer.setEnabled(true);
				add_skill.setEnabled(true);
				has_email.setEnabled(true);
				has_link.setEnabled(true);
				has_name.setEnabled(true);
				has_number.setEnabled(true);
				has_skill.setEnabled(true);
				possessed_by.setEnabled(true);
				taught_by.setEnabled(true);
				obtained_by.setEnabled(true);
				teaches_course.setEnabled(true);
				
			}
		});
		
		JButton home = new JButton("Home");
		home.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.showCard("Menu");
			}
		});
		
		addComponent(new JLabel("OWL file :"),1,15,1,1);
		addComponent(flabel,2,15,1,1);
		addComponent(choose,4,15,1,1);
		
		
		int gap = 30;
		addComponent(title,0,0,3,1);
		addGap(0,2,0,100);
		addComponent(new JLabel("Course"),1,3,1,1);
		addComponent(course,2,3,1,1);
		addGap(0,4,0,gap);
		addComponent(new JLabel("Skill"),1,5,1,1);
		addComponent(skill,2,5,1,1);
		addGap(0,6,0,gap);
		addComponent(new JLabel("Trainer"),1,7,1,1);
		addComponent(trainer,2,7,1,1);
		
		addComponent(new JLabel("1> All classes are mandatory."),1,10,1,2);
		addComponent(new JLabel("2> Leave all the properties which are not present in your ontology blank."),1,11,2,2);
		addComponent(home,1,17,1,2);
		addComponent(submit,2,17,1,2);
		
		addGap(4,0,200,0);
		

		addComponent(title1,5,0,3,1);
		addGap(5,2,0,100);
		addComponent(new JLabel("addSkill"),5,3,1,1);
		addComponent(add_skill,7,3,1,1);
		addGap(5,4,0,gap);
		addComponent(new JLabel("obtainedBy"),5,5,1,1);
		addComponent(obtained_by,7,5,1,1);
		addGap(5,6,0,gap);
		addComponent(new JLabel("hasSkill"),5,7,1,1);
		addComponent(has_skill,7,7,1,1);
		addGap(5,8,0,gap);
		addComponent(new JLabel("isPossessedBy"),5,9,1,1);
		addComponent(possessed_by,7,9,1,1);
		addGap(5,10,0,gap);
		addComponent(new JLabel("isTaughtBy"),5,11,1,1);
		addComponent(taught_by,7,11,1,1);
		addGap(5,12,0,gap);
		addComponent(new JLabel("teachesCourse"),5,13,1,1);
		addComponent(teaches_course,7,13,1,1);
		addGap(5,14,0,gap);
		addComponent(new JLabel("hasName"),5,15,1,1);
		addComponent(has_name,7,15,1,1);
		addGap(5,16,0,gap);
		addComponent(new JLabel("hasContactNumber"),5,17,1,1);
		addComponent(has_number,7,17,1,1);
		addGap(5,18,0,gap);
		addComponent(new JLabel("hasEmail"),5,19,1,1);
		addComponent(has_email,7,19,1,1);
		addGap(5,20,0,gap);
		addComponent(new JLabel("hasProfileLink"),5,21,1,1);
		addComponent(has_link,7,21,1,1);
		
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
