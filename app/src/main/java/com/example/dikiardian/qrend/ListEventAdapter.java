package com.example.dikiardian.qrend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dikiardian.qrend.model.Event;

import java.util.List;

/**
 * Created by Diki on 4/24/2018.
 */

public class ListEventAdapter extends ArrayAdapter<Event> {

    List<Event> eventList;
    Context context;
    int resource;

    public ListEventAdapter(Context context, int resource, List<Event> eventList) {
        super(context, resource, eventList);
        this.eventList = eventList;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        TextView title = view.findViewById(R.id.title_list_event);
        TextView subtitle = view.findViewById(R.id.subtitle_list_event);

        //getting the hero of the specified position
        Event event = eventList.get(position);

        //adding values to the list item
        title.setText(event.getEventName());
        subtitle.setText(event.getEventSubname());

        //finally returning the view
        return view;
    }
}
