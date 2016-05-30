package com.example.sqlite.first;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button btn_create, btn_refresh;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_create = (Button) findViewById(R.id.buttonCreateStudent);
		btn_create.setOnClickListener(new OnClickListenerCreateStudent());
		btn_refresh = (Button) findViewById(R.id.buttonRefresh);
		btn_refresh.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	
		    	context = v.getContext();
		    	readRecords();
		    	Toast.makeText(context, "Student record refreshed", Toast.LENGTH_SHORT).show();
		    }
		});
		

	}

	
	public void readRecords() {
		 
	    LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
	    linearLayoutRecords.removeAllViews();
	 
	    List<ObjectStudent> students = new TableControllerStudent(this).read();
	 
	    if (students.size() > 0) {
	 
	        for (ObjectStudent obj : students) {
	 
	            int id = obj.id;
	            String studentFirstname = obj.firstname;
	            String studentEmail = obj.email;
	 
	            String textViewContents = studentFirstname + " - " + studentEmail;
	 
	            TextView textViewStudentItem= new TextView(this);
	            textViewStudentItem.setPadding(0, 10, 0, 10);
	            textViewStudentItem.setText(textViewContents);
	            textViewStudentItem.setTag(Integer.toString(id));
	            textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());
	            linearLayoutRecords.addView(textViewStudentItem);
	        }
	 
	    }
	 
	    else {
	 
	        TextView locationItem = new TextView(this);
	        locationItem.setPadding(8, 8, 8, 8);
	        locationItem.setText("No records yet.");
	 
	        linearLayoutRecords.addView(locationItem);
	    }
	 
	}
	
}
