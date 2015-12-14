package com.example.zghadyali.carshareapp.Borrower;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zghadyali.carshareapp.R;
import com.example.zghadyali.carshareapp.SignUp.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CarsListCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private BorrowerHome borrowerHome;
    private Calendar calendar;
    private int month, year, day, hour, minute, set_hour, set_minute;

    public CarsListCustomAdapter(ArrayList<String> list, BorrowerHome borrowerHome,Context context) {
        this.list = list;
        this.context = context;
        this.borrowerHome = borrowerHome;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
//        return list.get(pos).getId();
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cars_list, null);
        }
        final String car = list.get(position);

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);

        // Displays the actual friend name instead of the id
        listItemText.setText(car);

        Button requestButton = (Button)view.findViewById(R.id.request_btn);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlertDialog(car);
            }
        });

        return view;
    }

    public void displayAlertDialog(String car) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        View alertLayout = inflater.inflate(R.layout.make_request, null);
        final EditText date_request = (EditText) alertLayout.findViewById(R.id.Date_request);
        date_request.setInputType(InputType.TYPE_NULL);
        inputManager.hideSoftInputFromWindow(date_request.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        final EditText from_request = (EditText) alertLayout.findViewById(R.id.from_request);
        from_request.setInputType(InputType.TYPE_NULL);
        inputManager.hideSoftInputFromWindow(from_request.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        final EditText to_request = (EditText) alertLayout.findViewById(R.id.to_request);
        to_request.setInputType(InputType.TYPE_NULL);
        inputManager.hideSoftInputFromWindow(to_request.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        final EditText opt_message = (EditText) alertLayout.findViewById(R.id.message_opt);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date_request.setText((month + 1) + "/" + day + "/" + year);

        date_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int setYear, int setMonth, int setDay) {
                        if (setYear > year) {
                            date_request.setText((setMonth + 1) + "/" + setDay + "/" + setYear);
                        } else if (setYear == setYear && setMonth > month) {
                            date_request.setText((setMonth + 1) + "/" + setDay + "/" + setYear);
                        } else if (setYear == setYear && setMonth == month && setDay > day) {
                            date_request.setText((setMonth + 1) + "/" + setDay + "/" + setYear);
                        } else {
                            Toast toast = Toast.makeText(context, "The date you chose is not valid", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                };
                DatePickerDialog mDatePickerDialog = new DatePickerDialog(context, myDateListener, year, month, day);
                mDatePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                mDatePickerDialog.show();

            }
        });

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        displayTime(from_request, hour, minute);
        set_hour = hour;
        set_minute = minute;

        from_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener mTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int setHourOfDay, int setMinute) {
                        if (setHourOfDay > hour) {
                            displayTime(from_request, setHourOfDay, setMinute);
                            set_hour = setHourOfDay;
                            set_minute = setMinute;
                        } else if (setMinute > minute) {
                            displayTime(from_request, setHourOfDay, setMinute);
                            set_hour = setHourOfDay;
                            set_minute = setMinute;
                        } else {
                            Toast toast = Toast.makeText(context, "The time you chose is not valid", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                };
                TimePickerDialog mTimePickerDialog = new TimePickerDialog(context, mTimeListener, hour, minute, false);
                mTimePickerDialog.show();
            }
        });

        to_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener diffTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int setHourOfDay, int setMinute) {
                        if (setHourOfDay > set_hour) {
                            displayTime(to_request, setHourOfDay, setMinute);
                        } else if (setMinute > set_minute) {
                            displayTime(to_request, setHourOfDay, setMinute);
                        } else {
                            Toast toast = Toast.makeText(context, "The time you chose is not valid", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                };
                TimePickerDialog diffTimePickerDialog = new TimePickerDialog(context, diffTimeListener, hour, minute, false);
                diffTimePickerDialog.show();
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Request " + car);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        alert.setNegativeButton("Cancel", null);


        alert.setPositiveButton("Request", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String date = date_request.getText().toString();
                String from = from_request.getText().toString();
                String to = to_request.getText().toString();
                String message = opt_message.getText().toString();
                Log.d("DATE", date);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void displayTime(EditText editText, int hourOfDay, int minute){
        if (hourOfDay < 12) {
            if (hourOfDay == 0){
                if (minute < 10){
                    editText.setText((hourOfDay + 12) + ":" + "0"+ minute + " AM");
                } else{
                    editText.setText((hourOfDay + 12) + ":" + minute + " AM");
                }
            } else {
                if (minute < 10){
                    editText.setText(hourOfDay + ":" + "0"+ minute + " AM");
                } else{
                    editText.setText(hourOfDay + ":" + minute + " AM");
                }
            }
        } else {
            if (hourOfDay == 12){
                if (minute < 10){
                    editText.setText(hourOfDay + ":" + "0"+ minute + " PM");
                } else{
                    editText.setText(hourOfDay + ":" + minute + " PM");
                }
            } else {
                if (minute < 10){
                    editText.setText((hourOfDay-12) + ":" + "0"+ minute + " PM");
                } else{
                    editText.setText((hourOfDay-12) + ":" + minute + " PM");
                }
            }
        }
    }


}