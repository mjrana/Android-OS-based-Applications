package ltu.org;

import android.content.Context;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * this class defines how a person object would be represented through a view
 * the layout defined in this class looks like this:
 *  -------------------------------------------------
 *  | |---------| |------------------------------|	|
 *  | |	rotated	| |		Name					 |	|
 *  | |	Photo	| |------------------------------|	|
 *  | |			| |		Age						 |	|
 *  | |---------| |------------------------------|	|
 *  -------------------------------------------------
 *  
 *  This view is used (like a row) to populate a list view using a custom data adapter. That is 
 *  why this class extends the LinearLayout class.
 *  
 */

class PersonView extends LinearLayout{
	
	final int iconWidth = 30;	//this is the width of the icon, height will be calc
	final int baseAlpha = 140;	//this is the base alpha value (transparency) for the icon (dimmed)
	final int maxAlpha = 255;	//this is the maximum alpha value for the icon (bright)
	
	private Context cxt;
	private LinearLayout rootContainer;	//root container that is used to stack the Icon and Text
	private ImageView imgIcon;			//to hold the icon
	private TextView txtDesc;			//to hold the text description (name and age)
	private String toShow;				//text representation for the object data
	private boolean checked;			//to indicate if this row is selected or not 
	
	public PersonView(Context context, Person obj){
		
		super(context);	//initialise the base class constructor 
		
		cxt = context;
		toShow = obj.name + "\n" + obj.age;
		checked = false;
		
		//the layout is stored in an XML file - row.xml, we'll inflate that layout here using an inflater
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		//the three elements of the layout are inflated as follows
		rootContainer = (LinearLayout)inflater.inflate(R.layout.row, (ViewGroup)findViewById(R.id.root_container));				
		imgIcon = (ImageView)rootContainer.findViewById(R.id.icon);
		txtDesc = (TextView)rootContainer.findViewById(R.id.description);
		
		//set the layout parameters for the rootContainer - the root container is a single row. So let it fill its parent
		//along the width and height
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 
																		android.view.ViewGroup.LayoutParams.FILL_PARENT);
		param.setMargins(2, 2, 2, 2);
		rootContainer.setLayoutParams(param);
		
		//now the icon being used is a bit bigger, so we need to scale it down and rotate it a bit
		int origWidth = obj.photo.getWidth();		//get the original width
		int origHeight = obj.photo.getHeight();		//get the original height
		
		float aspRatio = origHeight/origWidth;		//calculate the aspect ratio - height/width
		int newWidth  = iconWidth;					//set the newWidth (constant iconWidth defined above)
		int newHeight = (int)(newWidth*aspRatio);	//using aspect ratio calculate newHeight
		
		float scaleX = ((float)newWidth)/origWidth;	//calculate the scaling factor - new / old (both X and Y)
		float scaleY = ((float)newHeight)/origHeight;
		
		Matrix matrix = new Matrix();				//create a matrix that will allow for the scaling and rotation
		matrix.postScale(scaleX, scaleY);			//set the scaling factors along x and y
		matrix.postRotate(12);						//set the rotation in degrees
		
		//create a bitmap using the Person's photo and apply the matrix transforms on this photo
		Bitmap toSet = Bitmap.createBitmap(obj.photo, 0, 0, origWidth, origHeight, matrix, true);
		
		//convert the bitmap to a drawable -- cuz that is what imageView understands
		BitmapDrawable toSetDrawable = new BitmapDrawable(toSet);
		
		imgIcon.setImageDrawable(toSetDrawable);	//set the drawable to fill the imageView
		imgIcon.setAlpha(baseAlpha);				//set the original transparency (initially a bit dim)
		txtDesc.setText(toShow);					//set the text to display in the text view
		
		this.addView(rootContainer);				//add the rootContainer to "this" layout
		
	}
	
	/*
	 * returns the status of the checked flag
	 */
	public boolean isChecked (){return checked;}	
	
	/*
	 * sets/clears the checked flag, and correspondingly changes the transparency of the 
	 * icon allowing the image to appear bright/dim. The idea is that when the row is selected
	 * the icon appears bright, and when the row is unselected, it appears dimmed
	 */
	public void check(boolean value){			
		checked = value;
		if (checked)	imgIcon.setAlpha(maxAlpha);		//selected = bright
		else			imgIcon.setAlpha(baseAlpha);	//unselected = dim
	}
	
	
	
}

