package com.fidenz.chami.dev.bhooztapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fidenz.chami.dev.bhooztapp.activities.BhooztActivity;
import com.fidenz.chami.dev.bhooztapp.activities.CocaColaActivity;
import com.fidenz.chami.dev.bhooztapp.models.Products;
import com.fidenz.chami.dev.bhooztapp.R;
import com.fidenz.chami.dev.bhooztapp.adapter.ItemAdapter;
import com.fidenz.chami.dev.bhooztapp.database.SharedPreference;

import java.util.ArrayList;
import java.util.List;


public class Tab2 extends Fragment implements ItemAdapter.GetInterface {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Products> productsList;
    private SharedPreference sharedPreference;
    int[] imageNames = new int[]{R.drawable.cocacolapng, R.drawable.bhooztlogo, R.drawable.kelloggslogo, R.drawable.pepsinew, R.drawable.johnwest, R.drawable.heinekenlogo, R.drawable.coleslogo};

    public Tab2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tab2, container, false);

        recyclerView = v.findViewById(R.id.idrecylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        productsList = new ArrayList<>();

        productsList.add(new Products("12", "3/9", imageNames[0]));
        productsList.add(new Products("12", "3/9", imageNames[1]));
        productsList.add(new Products("12", "3/9", imageNames[2]));
        productsList.add(new Products("12", "3/9", imageNames[3]));
        productsList.add(new Products("12", "3/9", imageNames[4]));
        productsList.add(new Products("12", "3/9", imageNames[5]));
        productsList.add(new Products("12", "3/9", imageNames[6]));

        adapter = new ItemAdapter(productsList, getActivity(), this);
        recyclerView.setAdapter(adapter);

        sharedPreference = new SharedPreference();

        String cCocaCola = sharedPreference.getRemainGamePieces(getActivity().getApplicationContext());
        String bBhoozt = sharedPreference.getBhooztRemainGamePieces(getActivity().getApplicationContext());

        if (cCocaCola != null) {
            productsList.set(0, new Products("12", cCocaCola + "/9", imageNames[0]));
            adapter.notifyItemChanged(0);
        }

        if (bBhoozt != null) {
            productsList.set(1, new Products("12", bBhoozt + "/9", imageNames[1]));
            adapter.notifyItemChanged(1);
        }

        return v;

    }

    @Override
    public void mgetposition(int Position) {

        switch (Position) {

            case 0:
                Intent intentGame1 = new Intent(getActivity(), CocaColaActivity.class);
                startActivity(intentGame1);
                break;
            case 1:
                Intent intentGame2 = new Intent(getActivity(), BhooztActivity.class);
                startActivity(intentGame2);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPreference = new SharedPreference();

        String cCocaCola = sharedPreference.getRemainGamePieces(getActivity().getApplicationContext());
        String bBhoozt = sharedPreference.getBhooztRemainGamePieces(getActivity().getApplicationContext());

        if (cCocaCola != null) {
            productsList.set(0, new Products("12", cCocaCola + "/9", imageNames[0]));
            adapter.notifyItemChanged(0);
        }

        if (bBhoozt != null) {
            productsList.set(1, new Products("12", bBhoozt + "/9", imageNames[1]));
            adapter.notifyItemChanged(1);
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
