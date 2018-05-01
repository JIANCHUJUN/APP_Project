package com.example.frank.group;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ChartFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<PieEntry> pieEntries;
    PieChart chart;
    PieDataSet dataSet;
    PieData pieData;

    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        pieEntries = new ArrayList<PieEntry>();
        chart = view.findViewById(R.id.pieChatR);
        return view;
    }

    public void UpdateChat() {

        if(pieEntries != null && dataSet != null) {
            pieEntries.clear();
            dataSet.clear();
            pieData.clearValues();
            chart.clear();
        }

        float[] inputExample = new float[]{98.8f,123.2f,148.3f,52.3f};
        ArrayList<Company> currList = MainActivity.stockList;
        for(int i = 0; i <currList.size(); i++){

            //TODO: delete it when the app done
            if(i > 1) {
                currList.get(i).boughtTotal = 100;
            }
            //end of delete

            if(TradeMode.showAllStock) {
                pieEntries.add(new PieEntry((float) currList.get(i).boughtTotal, currList.get(i).companyName));
            } else {
                if(currList.get(i).boughtTotal != 0) {
                    pieEntries.add(new PieEntry((float) currList.get(i).boughtTotal, currList.get(i).companyName));
                }
            }
        }

        dataSet = new PieDataSet(pieEntries,"You stocks");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData = new PieData(dataSet);

        chart.setData(pieData);
        chart.animateY(1000);
        chart.invalidate();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

    }
}
