package com.example.sqlite.first;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class OnLongClickListenerStudentRecord implements OnLongClickListener {

	Context context;
	String id;

	@Override
	public boolean onLongClick(View view) {
		// TODO Auto-generated method stub
		context = view.getContext();
		id = view.getTag().toString();

		final CharSequence[] items = { "Edit", "Delete" };

		new AlertDialog.Builder(context).setTitle("Student Record")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						if (item == 0) {
							editRecord(Integer.parseInt(id));
						} else if (item == 1) {

							boolean deleteSuccessful = new TableControllerStudent(
									context).delete(id);

							if (deleteSuccessful) {
								Toast.makeText(context,
										"Student record was deleted.",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(context,
										"Unable to delete student record.",
										Toast.LENGTH_SHORT).show();
							}

							((MainActivity) context).readRecords();

						}
						dialog.dismiss();

					}

					private void editRecord(final int studentId) {
						// TODO Auto-generated method stub
						final TableControllerStudent tableControllerStudent = new TableControllerStudent(
								context);
						ObjectStudent objectStudent = tableControllerStudent
								.readSingleRecord(studentId);
						LayoutInflater inflater = (LayoutInflater) context
								.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						final View formElementsView = inflater.inflate(
								R.layout.student_input_form, null, false);
						final EditText editTextStudentFirstname = (EditText) formElementsView
								.findViewById(R.id.editTextStudentFirstname);
						final EditText editTextStudentEmail = (EditText) formElementsView
								.findViewById(R.id.editTextStudentEmail);
						editTextStudentFirstname
								.setText(objectStudent.firstname);
						editTextStudentEmail.setText(objectStudent.email);

						new AlertDialog.Builder(context)
								.setView(formElementsView)
								.setTitle("Edit Record")
								.setPositiveButton("Save Changes",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												ObjectStudent objectStudent = new ObjectStudent();
												objectStudent.id = studentId;
												objectStudent.firstname = editTextStudentFirstname
														.getText().toString();
												objectStudent.email = editTextStudentEmail
														.getText().toString();

												boolean updateSuccessful = tableControllerStudent
														.update(objectStudent);

												if (updateSuccessful) {
													Toast.makeText(
															context,
															"Student record was updated.",
															Toast.LENGTH_SHORT)
															.show();
													((MainActivity) context)
															.readRecords();
												} else {
													Toast.makeText(
															context,
															"Unable to update student record.",
															Toast.LENGTH_SHORT)
															.show();

													((MainActivity) context)
															.readRecords();
												}

												dialog.cancel();
											}

										}).show();
					}
				}).show();

		return false;
	}

}
