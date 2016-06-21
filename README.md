# KPSmart
This is a collaborative project for the (fictional) Kelburn Postal Service for Assignment 2 of SWEN301 at Victoria University of Wellington. We used using Scrum as our software methodology. The program is written in Java using the JavaFX library and the GUIs are written in FXML.  

*Project Leader:*	Donald Tang  
*Chief Programmer:* Peter Scriven  
*Chief Designer:* Kaszandra Semilla  
*Chief Analyst:* Michelle O'Neill  
*Chief Tester:* Priyanka Bhula  

## Compilation and Running
### Command Line
First ensure you are in the correct directory.
```
cd SWEN301Project2
```
Next use ant to the compile the .java files into .class files. This command uses *build.xml* and requires that you have *ant* installed.
```
ant
```
If all compiled correctly, you should now be able tyo run the program using this command:
```
java -cp ./KPSmart/bin controller.KPSgui
```

### Eclipse
Open Eclipse. Import the project by goin to *File > Import* then selecting *General > Existing Projects into Workspace*. From there select the *SWEN301Project2* directory as the root directory.

Once the project is imported, inside Eclipse simply navigate to *KPSmart/src/controller/KPSgui.java* and run that class.
