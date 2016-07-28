# smartJobs
A semantic web enabled portal which provides support for semantically linking various semantic web enabled job websites.
Allows user to get details from all the linked websites.
No changes are made in the linked website.
A one time linking is required between the portal ontology and the linked website's ontology. 
Queries fired using portal ontology, gives result from all the linked websites despite the difference in ontologies.
Registering new website doesn't require any changes in the query,code,etc. 
A GUI is provided for registering new website where the user can upload it's OWL file for linking to the website after which existing queries show results from all connected websites.
Currently the AddTrainer class allows to add ontologies and mapping is created for the same.
Rest data is entered directly in forms.
Seprate OWL files are created for portal ontology, mappings of various registered  websites, and other data.
