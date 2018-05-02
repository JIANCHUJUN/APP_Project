package com.example.frank.group;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StockFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TextView name;
    public String symbol;
    TextView price;
    Button delete;
    int mode = 0;//0 is normal, 1 is game mode, 2 is history mode

    private OnFragmentInteractionListener mListener;

    public StockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StockFragment newInstance(String param1, String param2) {
        StockFragment fragment = new StockFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        name = getView().findViewById(R.id.name);
        price = getView().findViewById(R.id.price);
        delete = (Button)getView().findViewById(R.id.delete);
        delete.setOnClickListener(this);
        name.setOnClickListener(this);
        mode = getArguments().getInt("mode");
        if (mode == 0 || mode == 1){
            symbol = getArguments().getString("symbol");
            String com_name = getArguments().getString("name");
            String com_price = String.valueOf(getArguments().getDouble("price"));

            name.setText(symbol);
            name.setWidth(200);
            price.setText("$"+com_price);

            if(mode == 1){
                int number = getArguments().getInt("number");
                delete.setText("Buy/Sell");
                price.setText("$"+com_price + " /   own: " + number);
            }
        }
        else if (mode == 2){
            symbol = getArguments().getString("symbol");
            double com_price = getArguments().getDouble("price");
            String type = getArguments().getString("type");
            int nubmer = getArguments().getInt("number");
            double total = getArguments().getDouble("total");
            //name.setText(symbol);
            delete.setVisibility(View.INVISIBLE);

            String sign = "";
            if(type.equals("BUY")){
                sign= "-";
            } else {
                sign = "+";
            }

            name.setMaxWidth(name.getMaxWidth()*4);
            name.setText(symbol + ", "+"price: " + com_price + ", Number: " + nubmer + ",type: " + type + ", total: "+sign+"$" + total);
            price.setText("");
        }

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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.name){
            ((FragListen)getActivity()).fragText(symbol);
        }
        else if(v.getId() == R.id.delete) {
            ((FragListen)getActivity()).fragButton(symbol);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
