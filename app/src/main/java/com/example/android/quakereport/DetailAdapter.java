package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailAdapter extends ArrayAdapter<EarthQuake> {

    //constructor
    public DetailAdapter(@NonNull Context context, ArrayList<EarthQuake> earthQuakes) {
        super(context, 0, earthQuakes);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //null check for convert view.
        View customListItem = convertView;

        if(customListItem == null ){// create a new one using layoutInflater.
            customListItem = LayoutInflater.from(getContext()).inflate(R.layout.custom_list, parent, false);
        }

        //first get a reference to the earthquake object.
        EarthQuake quake = getItem(position);

        //setting the magnitude to the custom view.
        TextView magnitude =  customListItem.findViewById(R.id.magnitude_view);
        magnitude.setText( formatMagnitude( quake.getMagnitude() ) );

       // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(quake.getMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        //setting the location to the custom view.
        TextView location_offset = customListItem.findViewById(R.id.location_offset_view);
        location_offset.setText( quake.getLocation_offset() );
        TextView location_primary =customListItem.findViewById(R.id.location_primary_view);
        location_primary.setText(quake.getLocation_primary());


        //setting the date of occurrence
        TextView dateOfQuake = customListItem.findViewById( R.id.date_view);
        dateOfQuake.setText((CharSequence) quake.getDateOfOccurrence());

        //setting the time of occurrence
        TextView timeOfQuake = customListItem.findViewById( R.id.time_view);
        timeOfQuake.setText((CharSequence) quake.getTimeOfOccurrence());

        return customListItem;
    }

    public String formatMagnitude(double magnitude){
        //using the decimal formatter to truncate the double values of magnitude to one decimal point.
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
