package assign8V2Pack;

import java.awt.*;
import javax.swing.*;

public class CalculatorBuilder extends JPanel implements ICalcBuilder{
//============================================================
	//Class employs Singleton design concept to ensure frame
	//uniqueness. Class instantiates a static JFrame, and 
	//fills it with number, operator, and textArea panels.
//============================================================	
//------------------------------------------------------------
	static CalculatorBuilder instance;
	private JPanel numPanel, opsPanel, combinedPanel;
	private JTextArea textArea;
	private String[] operations;
	CalculatorFactory calcFact;
//------------------------------------------------------------	
	public CalculatorBuilder() {
		textArea = new JTextArea();
		textArea.setSize(5, 5);
		
		calcFact = new CalculatorFactory(textArea);
		buildNumPanel();
		buildOpsPanel();
		getProduct();
	}
	
	//==================================================
	//Method returns the static instance of this object
	//==================================================
	public static CalculatorBuilder getObject() {
		if(instance != null) return instance;
		else {
			instance = new CalculatorBuilder();
			return instance;
		}
	}
	//=====================================================================
	//Method employs builder design concept. Method instantiates
	//a JPanel, sets a grid layout, adds numbers 1-9 by calling
	//the relevant method in the factory class, then calls additional
	//methods in the factory to create zero, decimal point, and backspace
	//buttons.
	//=====================================================================
	@Override
	public void buildNumPanel() {
		numPanel = new JPanel();
		numPanel.setLayout(new GridLayout(4,4));
		for(int i = 1; i <= 9; i++) {
			numPanel.add(calcFact.numbers(i));
		}
		numPanel.add(calcFact.decimalPoint("."));
		numPanel.add(calcFact.numbers(0));
		numPanel.add(calcFact.backSpace());
	}

	//=====================================================================
	//Method employs builder design concept to construct a operations panel.
	//Method instantiates a JPanel, sets the grid layout, then instantiates
	// an array which holds string values for the operations. Method then
	//uses a loop to create the necessary ops buttons. MEthod then creates
	//an equals button.
	//=====================================================================
	@Override
	public void buildOpsPanel() {
		opsPanel = new JPanel();
		opsPanel.setLayout(new GridLayout(6, 1));
		operations = getOperations();
		
		opsPanel.add(calcFact.clear());
		
		for(int i = 0; i < operations.length; i++) {
			opsPanel.add(calcFact.operations(operations[i]));
		}		
		
		opsPanel.add(calcFact.equals());
	}
	
	//=====================================================================
	//Method employs builder design concept to combine the textArea, 
	//numPanel and opsPanel into a JFrame. JFrame is instantiated, its 
	//gridlayout is set, and the combinedPanel is instantiated and added. 
	//=====================================================================
	@Override
	public JFrame getProduct() {
		combinedPanel = new JPanel();
		JFrame f = new JFrame("Baby's First Calculator");
		f.setLayout(new GridLayout(2,1));
		
		combinedPanel.setLayout(new GridLayout(1, 2));
		combinedPanel.add(numPanel);
		combinedPanel.add(opsPanel);
		
		f.setLayout(new GridLayout(2,1));
		f.add(textArea);
		f.add(combinedPanel);
		f.setVisible(true);
		f.setSize(500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		return f;
	}
	
	//======================================================================
	//Method instantiates and returns a String array which holds the 
	//operational symbols needed for calculations.
	//======================================================================
	private String[] getOperations() {
		operations = new String[] { "+", "-", "*", "/"};
		return operations;
	}
}

interface ICalcBuilder{
	void buildNumPanel();
	void buildOpsPanel();
	JFrame getProduct();
}