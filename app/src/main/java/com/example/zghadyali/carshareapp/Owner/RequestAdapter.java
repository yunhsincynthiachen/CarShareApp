package com.example.zghadyali.carshareapp.Owner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zghadyali.carshareapp.R;
import com.example.zghadyali.carshareapp.Volley.VolleyRequests;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jordan on 12/15/15.
 */
public class RequestAdapter extends ArrayAdapter<Request> {

    final private String ACCEPT = "1";
    final private String DENY = "2";
    private ArrayList<Request> requests;

    public RequestAdapter(Context context, ArrayList<Request> requests) {
        super(context, 0, requests);
        this.requests = requests;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Request request = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.request_list, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.request_name);
        TextView date = (TextView) convertView.findViewById(R.id.date_text);
        TextView from = (TextView) convertView.findViewById(R.id.from_text);
        TextView to = (TextView) convertView.findViewById(R.id.to_text);
        Button approveButton = (Button) convertView.findViewById(R.id.approve_button);
        final Button denyButton = (Button) convertView.findViewById(R.id.deny_button);

        name.setText(request.getName());
        date.setText(request.getDate());
        from.setText(R.string.from + request.getFromTime());
        to.setText(R.string.to + request.getToTime());

        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyRequests handler = new VolleyRequests(getContext());
                JSONObject requestDeny = new JSONObject();
                try {
                    requestDeny.put("approved", DENY);
                } catch (Exception e) {
                    Log.e("JSONException", e.getMessage());
                }
                handler.editrequest(request.getId(), requestDeny);
                requests.remove(position);
                notifyDataSetChanged();
            }
        });

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyRequests handler = new VolleyRequests(getContext());
                JSONObject requestApprove = new JSONObject();
                try {
                    requestApprove.put("approved", ACCEPT);
                } catch (Exception e) {
                    Log.e("JSONException", e.getMessage());
                }
                handler.editrequest(request.getId(), requestApprove);
                requests.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}
