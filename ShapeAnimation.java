import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.*;
import java.lang.Math.abs;

/*
Shape Animation - Java Applet Assignment

Author: Sonia Cui
Date: December 18, 2017

Functions:
- draw 4 different shapes of 4 different colours
- chosen by the user via radio buttons
- dimensions changeable via text boxes
- can animate left to right or in a figure-8 pattern
- changed speed between slow/medium/fast
- reset to blank screen

Input: The user can click radio buttons (checkboxes), enter values into text fields, and click various buttons, such as 'draw', 'animation 1', and 'reset'.
Output: When the user clicks 'draw', the program will render a shape with the corresponding attributes (shape, colour, size) based on their radio button choices and dimensions entered
in the text fields. The program will animate the selected shape across the screen (for the animate1 button) or in a figure-8 pattern (for the animate2 button). The program will also change
the speed of the animating shape (from slow/med/fast)if the 'change speed' button is clicked, and will reset to a blank screen if the 'reset' button is clicked.

*/

public class ShapeAnimation extends Applet
    implements ActionListener
{
    TextField tlength = new TextField (5); //add length text field for user input
    TextField twidth = new TextField (5); //add width text field for user input

    CheckboxGroup colors = new CheckboxGroup (); //new checkbox group for colours
    Checkbox red = new Checkbox ("RED", colors, true);
    Checkbox blue = new Checkbox ("BLUE", colors, false);
    Checkbox green = new Checkbox ("GREEN", colors, false);
    Checkbox orange = new Checkbox ("ORANGE", colors, false);

    CheckboxGroup shapes = new CheckboxGroup (); //new checkbox group for shapes
    Checkbox square = new Checkbox ("SQUARE", shapes, true);
    Checkbox rectangle = new Checkbox ("RECTANGLE", shapes, false);
    Checkbox circle = new Checkbox ("CIRCLE", shapes, false);
    Checkbox oval = new Checkbox ("OVAL", shapes, false);

    Button drawShape = new Button ("DRAW"); //make draw button
    Button animateShape1 = new Button ("ANIMATION 1"); //make left to right animation button
    Button animateShape2 = new Button ("ANIMATION 2"); //make figure 8 animation button
    Button reset = new Button ("RESET"); //make reset button
    Button changeSpeed = new Button ("CHANGE SPEED"); //make change speed button

    boolean draw = false; //boolean for if 'draw' button has been clicked

    //variables for animation
    int x = 250; //x coordinate
    int y = 250; //y coordinate
    int l = 100; //length
    int w = 150; //width
    double a = 250; //independent variable for sin function in animate 2
    double c, d; //temp variables, converted to back to int x and y later to manipulate dimensions
    int animateTime1 = 0; //counter/timer for first animation
    int animateTime2 = 0; //counter/timer for second animation
    int speed = 2; //default animation to medium speed

    private Image screen;
    private Graphics db;
    boolean end = false;

    public void init ()
    {
	setBackground (Color.white); //set background to white

	tlength.addActionListener (this); //add length text field
	twidth.addActionListener (this); //add width text field

	drawShape.addActionListener (this); //add action listener for all buttons
	animateShape1.addActionListener (this);
	animateShape2.addActionListener (this);
	reset.addActionListener (this);
	changeSpeed.addActionListener (this);

	add (red); //add colours
	add (blue);
	add (green);
	add (orange);

	add (square); //add shapes
	add (rectangle);
	add (circle);
	add (oval);

	add (tlength); //add length text field
	add (twidth); //add width text field

	add (drawShape); //add draw buton
	add (animateShape1); //add animation buttons
	add (animateShape2);
	add (reset); //add reset button
	add (changeSpeed); //add change speed button

    }


    //prevent animation from blinking
    public void update (Graphics g)
    {
	screen = createImage (getWidth (), getHeight ());
	db = screen.getGraphics (); //gets the frame from the current screen
	paint (db);
	g.drawImage (screen, 0, 0, this); //draw current screen
    }


    //paint method: import Graphics g to draw to the screen
    public void paint (Graphics g)
    {
	//display instructions
	g.setColor (Color.darkGray);
	g.drawString ("INSTRUCTIONS: Select a colour and shape, then click 'draw' to render image. You may enter integer values to change the dimensions of the shapes. If nothing is selected or entered, default settings will be used. ", 50, 50);
	g.drawString ("*Note: for rectangles and ovals, new dimensions will not be used until both fields are filled; for squares and circles, only the first field is used. Click animate once to start, twice to stop. Change speed to adjust slow/med/fast speed. Reset to set back to blank screen.", 85, 70);


	if (draw == true) //if user has clicked 'draw' button
	{
	    try
	    {

		//set shape to selected colour
		if (red.getState ())
		{
		    g.setColor (Color.red);
		    g.drawString ("RED", 20, 750);
		}
		else if (blue.getState ())
		{
		    g.setColor (Color.blue);
		    g.drawString ("BLUE", 20, 750);
		}

		else if (green.getState ())
		{
		    g.setColor (Color.green);
		    g.drawString ("GREEN", 20, 750);
		}

		else if (orange.getState ())
		{
		    g.setColor (Color.orange);
		    g.drawString ("ORANGE", 20, 750);
		}

		String length = tlength.getText (); //get text from text field and convert into a string
		String width = twidth.getText ();
		if (!length.equals (""))
		{
		    l = Integer.parseInt (tlength.getText ());
		}
		if (!width.equals (""))
		{
		    w = Integer.parseInt (twidth.getText ());
		}


		//set shape to selected shape
		if (square.getState ())
		{
		    if (length.equals ("")) //if text field is empty, set length to 100
		    {
			l = 100;
		    }
		    g.fillRect (x, y, l, l); //draw shape
		    g.drawString ("\tSQUARE", 100, 750); //display current shape
		    g.drawString ("\t\t SIZE:" + l + ", " + l, 200, 750); //display current size

		}
		else if (rectangle.getState ())
		{
		    if (length.equals ("") || width.equals (""))
		    {
			l = 100;
			w = 150;
		    }
		    g.fillRect (x, y, l, w);
		    g.drawString ("\tRECTANGLE", 100, 750);
		    g.drawString ("\t\t SIZE:" + l + ", " + w, 200, 750);
		}


		else if (circle.getState ())
		{
		    if (length.equals (""))
		    {
			l = 100;
		    }
		    g.fillOval (x, y, l, l);
		    g.drawString ("\tCIRCLE", 100, 750);
		    g.drawString ("\t\t SIZE:" + l + ", " + l, 200, 750);

		}

		else if (oval.getState ())
		{
		    if (length.equals ("") || width.equals (""))
		    {
			l = 100;
			w = 150;
		    }
		    g.fillOval (x, y, l, w);
		    g.drawString ("\tOVAL", 100, 750);
		    g.drawString ("\t\t SIZE:" + l + ", " + w, 200, 750);

		}


		//if user has clicked one of the animate buttons twice, then stop the animation
		if (animateTime1 > 10000 || animateTime2 > 10000)
		{
		    animateTime1 = 0;
		    animateTime2 = 0;
		}


		//if animate2 button is pushed while animate1 is running, stop the first animation
		if (animateTime2 > animateTime1)
		{
		    animateTime1 = 0;
		}


		//if animate1 button is pushed while animate2 is running, stop the second animation
		else if (animateTime1 > animateTime2)
		{
		    animateTime2 = 0;
		}


		//if animate1 button has been pushed, do the first animation
		if (animateTime1 > 0)
		{
		    animateTime1--;
		    animate1 (); //call animate1 function

		}


		//if animate2 button has been pushed, do the second animation
		else if (animateTime2 > 0)
		{
		    animateTime2--;
		    animate2 (); //call animate2 function
		}
	    }
	    catch (Throwable t)
	    {
		tlength.setText ("");
		twidth.setText ("");
		animateTime1 = 0;
		animateTime2 = 0;
		JOptionPane.showMessageDialog (null, "Exception or error " + t + " has occured!"); //notify user of error
	    }

	}
    }


    //left to right animation
    public void animate1 ()
    {

	if (x >= 1850) //if object has reached the right end of the screen, end = true
	    end = true;
	if (x <= 0) //if object has reached the left side of the screen, end = false
	    end = false;

	if (end == true) //if object has reached the right side, move left
	{
	    if (speed == 1) //slow speed left
		x--;

	    else if (speed == 2) //medium speed left
		x = x - 5;
	    else //fast speed left
		x = x - 15;

	}


	else if (end == false) //if object has reached the left side, move right
	{
	    if (speed == 1) //slow speed right
		x++;

	    else if (speed == 2) //medium speed right
		x = x + 5;
	    else //fast speed right
		x = x + 15;
	}


	try
	{
	    Thread.sleep (15); //pause for 15 ms
	}


	catch (InterruptedException e)
	{
	}


	repaint (); //draw shape in new position

    }


    //figure 8 animation
    public void animate2 ()
    {
	try
	{
	    Thread.sleep (15); //pause for 15 ms
	}


	catch (InterruptedException e)
	{
	}


	c = 500 * Math.sin (a); //calculate x coord
	d = 200 * Math.sin (2 * a); //calculate y coord

	x = ((int) c) + 900; //convert the double into int, and shift x coord 900 to the right so it looks nicer
	y = ((int) d) + 400; //convert double into int, and shift y coord 400 down

	if (speed == 1) //slow speed
	    a = a + 0.008;
	else if (speed == 2) //medium speed
	    a = a + 0.03;
	else //fast speed
	    a = a + 0.08;

	repaint (); //draw shape in new position

    }


    //method to detect if user has interacted with the interface
    public void actionPerformed (ActionEvent evt)
    {
	try
	{
	    if (evt.getSource () == drawShape) //if user has clicked draw button:
	    {
		draw = true; //execute code in draw loop in paint method
		x = 250; //reset x and y to (250, 250) in case it flew off the screen or something
		y = 250;
		a = 250; //reset 'a' value so animation2 matches
		repaint ();
	    }
	    else if (evt.getSource () == animateShape1) //if user has clicked animate1 button:
	    {
		animateTime1 += 10000; //set animation time counter to keep track of what button's been pressed
		repaint ();
	    }
	    else if (evt.getSource () == animateShape2) //if user has clicked aniamte2 button:
	    {
		animateTime2 += 10000; //set animation time counter to keep track of what button's been pressed
		repaint ();
	    }
	    else if (evt.getSource () == reset) //if user has clicked reset button:
	    {
		draw = false; //clear drawings
		animateTime1 = 0; //stop any animations
		animateTime2 = 0;
		speed = 2; //reset speed to medium
		repaint ();
	    }
	    if (evt.getSource () == changeSpeed) //if user has clicked change speed button
	    {
		//set speed to next corresponding speed (i.e. if speed is currently slow, change to medium, if medium, change to fast)
		if (speed == 1)
		    speed = 2;

		else if (speed == 2)
		    speed = 3;

		else
		    speed = 1;
	    }

	}


	catch (Throwable t)  //in case user is trying to crash the program
	{
	    JOptionPane.showMessageDialog (null, "Exception or error " + t + " has occured!!!!", "Exception or Error", 0); //notify user of error
	}
    }
}


