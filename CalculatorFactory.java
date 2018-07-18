package assign8V2Pack;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.AWTException;
import java.awt.Robot;

public class CalculatorFactory implements ICalcFunctions {
//==============================================================
	//Class employs abstract factory method design to instantiate
	//the buttons required for a calculator to function. Class
	//instantiates several operational variables as well as
	//two ArrayLists used to hold String and double values.
	//numList holds the double valued vars which represent the
	//operands and numBuilderList holds string values of
	//number button presses which is then used to create an
	//operand.
//==============================================================
//--------------------------------------------------------------
	private String operation;
	private double total;
	private JTextArea textArea;
	private ArrayList<Double> numList;
	private ArrayList<String> numBuilderList;
	private final int MAX_OPERANDS = 2;
//--------------------------------------------------------------
	public CalculatorFactory(JTextArea textArea) {
		this.textArea = textArea;
		numList = new ArrayList<Double>(MAX_OPERANDS + 1);
		numBuilderList = new ArrayList<String>();
	}
	
	//===================================================================
	//Method contains an inner class which extends SelfListeningButton.
	//Method instantiates a NumButton with the int value displayed on it.
	//===================================================================
	@Override
	public JButton numbers(int num) {
		class NumButton extends SelfListeningButton{

			public NumButton(String caption) {
				super(caption);
				
			}
			
			//========================================================
			//Method first check to see if the max number of operands
			//are in use. If true, it resets the numList array and 
			//then adds the value of the numberbutton pressed
			//to the numBuilderList. If false, it simply adds it to 
			//that numBuilderList.
			//========================================================
			@Override
			public void actionPerformed(ActionEvent e) {
				if(numList.size() == MAX_OPERANDS + 1) {
					display("\n");
					numList.clear();
					numBuilderList.add(String.valueOf(num));
					display(String.valueOf(num));
				}
				else {
					numBuilderList.add(String.valueOf(num));
					display(String.valueOf(num));
				}
			}	
		}
		SelfListeningButton btn = new NumButton(String.valueOf(num));
		return btn;
	}
	
	//===================================================================
	//Method contains an inner class which extends SelfListeningButton.
	//Method instantiates a OpsButton with the String value displayed
	//on it.
	//===================================================================
	@Override
	public JButton operations(String op) {
		class OpsButton extends SelfListeningButton{
			
			public OpsButton(String caption) {
				super(caption);			
			}
			
			//==============================================================
			//Method first checks to see if max number of operands are in
			//use. If true, it takes the last total, sets it as the first
			//operand, and sets and displays the operational symbol. 
			//If false, it calls the numberBuilder() method to build the
			// first operand, then sets and displays the operational symbol.
			//==============================================================
			@Override
			public void actionPerformed(ActionEvent e) {
				if(numList.size() == MAX_OPERANDS + 1) {
					double temp = numList.get(MAX_OPERANDS);
					numList.clear();
					numList.add(temp);
					display(op);
					operation = op;
				}
				else {
					numberBuilder();
					display(op);
					operation = op;		
				}
			}
		}
		SelfListeningButton btn = new OpsButton(String.valueOf(op));
		return btn;
	}
	//===================================================================
	//Method contains an inner class which extends SelfListeningButton.
	//Method instantiates a DecimalPointBtn with the String value 
	//displayed on it.
	//===================================================================
	@Override
	public JButton decimalPoint(String s) {
		class DecimalPointBtn extends SelfListeningButton{
			
			public DecimalPointBtn(String caption) {
				super(caption);			
			}
			
			//===========================================================
			//Method adds a decimal point to the numBuilderList array
			//and displays a decimal point.
			//===========================================================
			@Override
			public void actionPerformed(ActionEvent e) {
				numBuilderList.add(String.valueOf(s));
				display(String.valueOf(s));
			}
		}
		SelfListeningButton btn = new DecimalPointBtn(String.valueOf(s));
		return btn;
	}
	
	//===================================================================
	//Method contains an inner class which extends SelfListeningButton.
	//Method instantiates a EqualsButton with the String value "="
	//displayed on it.
	//===================================================================
	@Override
	public JButton equals() {
		class EqualsButton extends SelfListeningButton{
			
			public EqualsButton(String caption){
				super(caption);
			}
			
			//===========================================================
			//Method uses a switch statement to determine the correct
			//operation to use. For that operation, the method finds
			//its operands in the numList array in indexes 0 and 1, then
			//performs the required math on those operands and stores the
			//the total in index 2. Method displays the total.
			//===========================================================
			@Override
			public void actionPerformed(ActionEvent e) {
				numberBuilder();
				switch(operation) {
					case "+": 
						total = numList.get(0) + numList.get(1);
						numList.add(total);
						display("\n= " + total);
						break;

					case "-": 
						total =  numList.get(0) - numList.get(1);
						numList.add(total);
						display("\n= " + total);
						break;
						
					case "*": 
						total =  numList.get(0) * numList.get(1);
						numList.add(total);
						display("\n= " + total);
						break;
					
					case "/":
						total = numList.get(0) / numList.get(1);
						numList.add(total);
						display("\n= " + total);
						break;

					default: display("***");
				}
			}
		}
		SelfListeningButton btn = new EqualsButton("=");
		return btn;
	}
	
	//===================================================================
	//Method contains an inner class which extends SelfListeningButton.
	//Method instantiates a BackSpaceBtn with the String value "<"
	//displayed on it.
	//===================================================================
	public JButton backSpace() {
		class BackSpaceBtn extends SelfListeningButton{
			public BackSpaceBtn(String caption){
				super(caption);
			}
			
			//===========================================================
			//Method removes the last digit entered.
			//===========================================================
			@Override
			public void actionPerformed(ActionEvent e) {
				numBuilderList.remove(numBuilderList.size() -1);
				textArea.setText (textArea.getText ().substring (0, textArea.getText ().length () - 1));
			}
		}
		
		SelfListeningButton btn = new BackSpaceBtn("<");
		return btn;
	}

	//===================================================================
	//Method contains an inner class which extends SelfListeningButton.
	//Method instantiates a ClearButton with the String value "C"
	//displayed on it.
	//===================================================================
	@Override
	public JButton clear() {
		class ClearButton extends SelfListeningButton{
			public ClearButton(String caption){
				super(caption);
			}
			
			//============================================================
			//Method clears the textArea as well as the two arrays numList
			//and numBuilderList.
			//============================================================
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				numList.clear();
				numBuilderList.clear();
			}
		}
		
		SelfListeningButton btn = new ClearButton("C");
		return btn;
	}
	
	//===============================================================
	//Method stores the current text displayed in the textArea, then
	//concatenates that text with the parameter string value.
	//===============================================================
	public void display(String s) {
		String currentTxt = textArea.getText();
		textArea.setText(currentTxt + s);
	}
	
	//===============================================================
	//Method instantiates a string, uses a for loop to concatenate 
	//that string with the values stored in numBuilderList, then adds
	//the double valueOf that string to numList.
	//===============================================================
	private void numberBuilder() {
		String number = "";
		for(int i = 0; i < numBuilderList.size(); i++) {
			number += numBuilderList.get(i);
		}
		numBuilderList.clear();
		numList.add(Double.valueOf(number));
	}
}

abstract class SelfListeningButton extends JButton implements ActionListener{
	public SelfListeningButton(String caption){
		super(caption);
		addActionListener(this);
	}
}

interface ICalcFunctions{
	JButton equals();
	JButton clear();
	JButton numbers(int num);
	JButton operations(String op);
	JButton decimalPoint(String s);
}
