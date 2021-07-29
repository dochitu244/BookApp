package com.example.project1.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project1.Adapter.BookAdapter;
import com.example.project1.Adapter.TopAdapterHome;
import com.example.project1.Model.Book;
import com.example.project1.R;

import java.util.ArrayList;

import static java.util.Collections.reverse;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private ArrayList<Book> listBook =new ArrayList<>();
    private ArrayList<Book> listBook1 =new ArrayList<>();

    private RecyclerView rcvTop;
    private RecyclerView rcvMid;
    private RecyclerView rcvBot;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        rcvTop =view.findViewById(R.id.rcvHome);
        rcvMid=view.findViewById(R.id.rcvHomeMid);
        rcvBot=view.findViewById(R.id.rcvHomeBot);
        //setup RecycleView
        initRecycleViewTop();
        initRecyclerViewMid(view);
        initRecyclerViewBot(view);
        //Set data
        buildData();
        buildData2();

        return view;

//        return inflater.inflate(R.layout.fragment_home, container, false);
    }



    private void buildData() {

        listBook1.add(new Book(R.drawable.book3,"Into the wild"));
        listBook1.add(new Book(R.drawable.book2,"HarryPotter"));
        listBook1.add(new Book(R.drawable.book9,"The Queen"));
    }
    private void buildData2(){

        listBook.add(new Book(R.drawable.book2,"HarryPotter","J.K.Rowling"));
        listBook.add(new Book(R.drawable.book4,"The fault In Our","John Green"));
        listBook.add(new Book(R.drawable.book5,"The Alchemist","Paulo Coe"));
        listBook.add(new Book(R.drawable.book9,"The Queen","D.C.Tus"));
    }

    private void initRecycleViewTop() {
        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rcvTop.setLayoutManager(layoutManager);
        TopAdapterHome adapterTop =  new TopAdapterHome(listBook1);
        rcvTop.setAdapter(adapterTop);
    }
    private void initRecyclerViewMid(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rcvMid.setLayoutManager(layoutManager);
        BookAdapter bookAdapter = new BookAdapter(listBook);
        rcvMid.setAdapter(bookAdapter);
    }

    private void initRecyclerViewBot(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rcvBot.setLayoutManager(layoutManager);
        BookAdapter bookAdapter = new BookAdapter(listBook);
        rcvBot.setAdapter(bookAdapter);

    }
}