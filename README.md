#Disaster Response Trade Study Tool

![image](https://cloud.githubusercontent.com/assets/7001697/17087682/40ff6560-51de-11e6-8767-380ae07b123b.jpg)

> Georgia Institute of Technology

> Professional Masters in Applied Systems Engineering

> 2014 Cohort - Final Capstone Project 2016

> Team Awesome

##Using Disaster Response Trade Study Tool
###Purpose
The Disaster Response Trade Study Tool (DRTST) is designed for use at a state/regional/federal level in order to make long term funding and acquisition decisions on the types of technology solutions to better prepare for disaster response. The use of the tool could be at a local level but will most likely be at the state or federal level, or its equivalents in foreign nations. The user inputs the types of disasters that concern them as well as information about the environment such as the population density, terrain, and transportation infrastructure. The tool will output a list of technology descriptions that best meet the user’s chosen inputs.

###System Configuration
Prior to beginning installation procedures for DRTST, check the system configuration requirements below.  If your system does not meet these requirements, DRTST will not run correctly.  Update your system to align with the requirements below before proceeding. 

####Configuration Requirements
- Windows 7 OS or greater
- Microsoft Office 2007 or later
- Java Runtime Environment (JRE) version 8 or later
 * See http://www.oracle.com/technetwork/java/javase/certconfig-2095354.html for reference 
 DRTST uses Java as its primary interface. The databases containing the technology set options are maintained in Microsoft Excel. Note: The trade study relies on the databases to make proper decisions on recommended technology sets. Any edits to these databases should be made by personnel who understand the capabilities of the various technologies being edited. 

###User Assumptions and Access Levels
####DRTST Program
Anyone can use the DRTST program. There are no administrative controls for the tool. The user should possess a well-rounded understanding of disaster response operations, knowledge of the required information needs of the organization, and geographical information related to both potential disaster locations and their effects (e.g. smoke, ash, etc.). In addition, the user should possess an understanding of how key performance parameters of technology systems impact the data’s usefulness to the disaster response operation.

####DRTST Technology Options Databases
As stated above, the technology options databases are the key source of information for the trade study tool. Only personnel who are domain experts in the technology options and who understand the software and the specific data requirements of the trade study tool should modify these databases. There are currently no administrative controls on the database files that are used to conduct the trade study analysis.

###Installation
Extract the DRTST zip file and save the contents to the appropriate directory for your organization. The unzipped folder will contain contents as shown in Figure 1. Once the file has been saved to the desired location, double click on the Disaster Response Trade Study Tool application file to start the DRTST program.
![image](https://cloud.githubusercontent.com/assets/7001697/17087752/436a1cf4-51df-11e6-8852-2b3b45b74e34.png)

###Usage
Refer to the include user manaul for how to use the Disaster Response Trade Study Tool.
![main window - great success](https://cloud.githubusercontent.com/assets/7001697/17087920/95db3296-51e1-11e6-97bc-93b23bd19715.png)

##Developing Disaster Response Trade Study Tool
###Setup Development Environment
DRTST was developed using a collection of tools. To develop for DRTST the following are required:
- Java JDK 1.8 or greater
- Netbeans IDE 8.1 or greater
- Apache Ant 1.5 or greater
- Apache Ivy 2.4.0 or greater

Notes:
- The Apache Ivy library must be added to the Apache Ant libary folder as per instructions here: https://ant.apache.org/ivy/history/latest-milestone/install.html
- JavaFX comes in the Java JDK as per Java 8
- The project Ant task is setup to build and run only with Netbeans.



