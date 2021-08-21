package com.example.volunteer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.volunteer.Objects.Opportunity;

public class OpportunityItemView extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opportunity_item_view);

        Opportunity opportunity = Account.currentOpportunity;
        ((TextView) findViewById(R.id.opportunity_name_item_view)).setText(
                new String(((TextView) findViewById(R.id.opportunity_name_item_view)).getText() + " " + opportunity.getOpportunityName()));
        ((TextView) findViewById(R.id.organizer_name_item_view)).setText(
                new String(((TextView) findViewById(R.id.organizer_name_item_view)).getText() + " " + opportunity.getOrganizerName()));
        ((TextView) findViewById(R.id.description_item_view)).setText(
                new String(((TextView) findViewById(R.id.description_item_view)).getText() + " " + opportunity.getDescription()));
        ((TextView) findViewById(R.id.address_item_view)).setText(
                new String(((TextView) findViewById(R.id.address_item_view)).getText() + " " + opportunity.getAddress()));
        ((TextView) findViewById(R.id.start_date_item_view)).setText(
                new String(((TextView) findViewById(R.id.start_date_item_view)).getText() + " " + opportunity.getStartDate()));
        ((TextView) findViewById(R.id.start_time_item_view)).setText(
                new String(((TextView) findViewById(R.id.start_time_item_view)).getText() + " " + opportunity.getStartTime()));
        ((TextView) findViewById(R.id.end_time_item_view)).setText(
                new String(((TextView) findViewById(R.id.end_time_item_view)).getText() + " " + opportunity.getEndTime()));
    }
}
