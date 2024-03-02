package com.surakshasathi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Helpline extends Fragment {


    ListView lv;
    ArrayAdapter<String> listadapter;

    public Helpline() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_helpline, container, false );
        lv = view.findViewById( R.id.helplinelist );
        List<DataHolder> datalist = new ArrayList<>();
        datalist.add(new DataHolder("1. Child Helpline:","1098" ));
        datalist.add(new DataHolder("2. Domestic Violence Helpline:","181" ));
        datalist.add(new DataHolder("3. National Commission for Women (NCW) -01:","011-26942369" ));
        datalist.add(new DataHolder("4. National Commission for Women (NCW) -02:","011-26944754" ));
        datalist.add(new DataHolder("5. Women Helpline (Women in Distress):","1091" ));
        datalist.add(new DataHolder("6. Mahila Sahayata Kendra:","181" ));
        datalist.add(new DataHolder("7. Sakshi Violence Intervention Centre:","0124-2562336" ));
        datalist.add(new DataHolder( "8. One Stop Centre (OSC) for Women in Distress:","181" ));
        datalist.add(new DataHolder( "9. Police Helpline: ","100" ));
        datalist.add(new DataHolder( "10. Beti Bachao, Beti Padhao Abhiyan:","1800-111-777"));




        CustomAdapter customAdapter = new CustomAdapter( getContext(),datalist );
        lv.setAdapter( customAdapter );

        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String no = datalist.get( i ).getText2();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData( Uri.parse( "tel:"+no ) );
                startActivity( intent );

            }
        } );




        return view;
    }
}