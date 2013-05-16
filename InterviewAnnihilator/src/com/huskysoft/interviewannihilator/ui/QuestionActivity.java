/**
 * 
 * After a question is clicked on MainActivity, this activity is brought up.
 * It display the question clicked, and a hidden list of solutions that pop
 * up when a "Solutions" button is clicked.
 * 
 * @author Cody Andrews, Phillip Leland, 05/01/2013
 * 
 */

package com.huskysoft.interviewannihilator.ui;

import java.util.ArrayList;
import java.util.List;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.runtime.FetchQuestionsTask;
import com.huskysoft.interviewannihilator.util.Utility;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.runtime.FetchSolutionsTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.content.DialogInterface;
import android.content.Intent;


/**
 * Activity for viewing a question before reading solutions
 */
public class QuestionActivity extends SlidingActivity {
	
	/** Layout that the question and solutions will populate */
	private LinearLayout linearLayout;
	
	/** The question the user is viewing */
	private Question question;
	
	private QuestionActivity context;
	
	/** List of TextViews containing solutions */
	private List<TextView> solutionTextViews;
	
	/** true when the solutions have finished loading */
	private boolean solutionsLoaded;
	
	/** true when the user presses the "show solutions button" */
	private boolean showSolutionsPressed;
	
	/** Thread in which solutions are loaded */
	private FetchSolutionsTask loadingThread;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);	
		setBehindContentView(R.layout.activity_menu);
		getActionBar().setHomeButtonEnabled(true);
		
		buildSlideMenu();
		
		// Get intent
		Intent intent = getIntent();
		question = (Question) intent.getSerializableExtra(
				MainActivity.EXTRA_MESSAGE);
		
		// Grab Linear Layout
		linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
		
		// Create TextView that holds Question
		LinearLayout.LayoutParams llp =  new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
		
		// TODO: Move to XML or constants file - haven't yet figured out how
		llp.setMargins(40, 10, 40, 10);
		llp.gravity = 1; // Horizontal Center

		TextView textview = new TextView(this);
		textview.setBackgroundDrawable(getResources().
				getDrawable( R.drawable.listitem));
		textview.setText(question.getText());
		textview.setLayoutParams(llp);
		
		context = this;
		
		// Add question to layout
		linearLayout.addView(textview, 0);
				
		// Initialize values
		solutionsLoaded = false;
		showSolutionsPressed = false;
		solutionTextViews = new ArrayList<TextView>();
		
		//Start loading solutions. This makes a network call.
		loadSolutions();		
	}
	
	public void buildSlideMenu(){
		SlidingMenu menu = getSlidingMenu();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = (int) ((double) metrics.widthPixels * 0.8);
		menu.setBehindOffset((int) (width * 0.25));
		
		Spinner spinner = (Spinner) findViewById(R.id.diff_spinner);
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(this,
		        R.array.difficulty, 
		        android.R.layout.simple_spinner_item);
		
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		// Handle onClick of Slide-Menu button
		Button button = (Button) findViewById(R.id.slide_menu_button);
		button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	Spinner spinner = (Spinner) findViewById(R.id.diff_spinner);
				String difficulty = spinner.getSelectedItem().toString();
				
				toggle();
				
				Intent intent = new Intent(context, MainActivity.class);
				Utility.DIFFICULTY_MESSAGE = difficulty;
				startActivity(intent);
				
		    }
		});
		
	}
	
	/**
	 * Appends a list of solutions to a hidden list.
	 * If the showSolutions button has already been pressed, it will reveal
	 * the solutions upon completion. If the button has not been pressed,
	 * solutions will be hidden.
	 * 
	 * @param solutions
	 */
	public synchronized void addSolutions(List<Solution> solutions){
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
		llp.setMargins(40, 10, 40, 10);
		llp.gravity = 1; // Horizontal Center
		
		if(solutions == null || solutions.size() <= 0){
			TextView t = new TextView(this);
			
			t.setText("There doesn't seem to be any solutions");
			t.setLayoutParams(llp);
			linearLayout.addView(t);
		} else {
			for(int i = 0; i < solutions.size(); i++){
				Solution solution = solutions.get(i);
				if(solution != null && solution.getText() != null){
					String solutionText = solution.getText();
					
					TextView t = new TextView(this);
					
					t.setText(solutionText);
					t.setBackgroundDrawable(getResources().
							getDrawable( R.drawable.listitem));
					t.setLayoutParams(llp);
					t.setId(solution.getId());
					//Hide solutions
					t.setVisibility(View.GONE);
					
					solutionTextViews.add(t);
					linearLayout.addView(t);
				}
			}
		}
			
		solutionsLoaded = true;
		if(showSolutionsPressed){
			revealSolutions();
		}
	}

	
	/**
	 * Button handler for the "Solutions" button.
	 * Attempts to reveal solutions. If it cannot (solutions have not been
	 * loaded yet), it will set a flag to 
	 * 
	 * @param v 
	 */
	public synchronized void onShowSolutions(View v){
		if(!showSolutionsPressed){
			if(solutionsLoaded){
				revealSolutions();
			}else{
				LinearLayout loadingText =
						(LinearLayout) findViewById(R.id.loading_text_layout);
				loadingText.setVisibility(View.VISIBLE);
				showSolutionsPressed = true;
			}
		}
	}
	
	/**
	 * Reveals solutions. Should only be called once solutions are loaded.
	 */
	private void revealSolutions(){
		// Dismiss loading window
		LinearLayout loadingText =
				(LinearLayout) findViewById(R.id.loading_text_layout);
		loadingText.setVisibility(View.GONE);
		
		// Dismiss show solutions button
		Button showSolutions =
				(Button) findViewById(R.id.show_solutions_button);
		showSolutions.setVisibility(View.GONE);
		
		// Reveal hidden solutions
		for(TextView tv : solutionTextViews){
			tv.setVisibility(View.VISIBLE);
		}
	}
	
	private void loadSolutions(){
		solutionsLoaded = false;
		
		loadingThread = new FetchSolutionsTask(this, question);
		loadingThread.execute();
	}
	
	/**
	 * Pops up a dialog menu with "Retry" and "Cancel" options when a network
	 * operation fails.
	 */
	public void onNetworkError(){	
		// Stop loadingDialog
		LinearLayout loadingText =
				(LinearLayout) findViewById(R.id.loading_text_layout);
		loadingText.setVisibility(View.GONE);
		
		// Create a dialog
		new AlertDialog.Builder(this).setTitle(R.string.retryDialog_title)
		.setPositiveButton(R.string.retryDialog_retry,
		new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				loadSolutions();
			}
		})
		.setNegativeButton(R.string.retryDialog_cancel,
		new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
		.create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
