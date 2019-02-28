package com.example.mvvmsampleproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class UserProfileFragment extends Fragment implements OnCLickListener {


    private RecyclerView recyclerView;
    private UserProfileViewModel viewModel;
    private List<User> users = new ArrayList<>();
    private UserAdapter adapter;

    public UserProfileFragment() {
    }

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.observeUserList().observe(this, this::consumeResponse);
    }

    private void consumeResponse(Resource resource) {
        switch (resource.getCurrentState()) {
            case LOADING: {
                /*
                 * If network operation is going on then we will get the data from
                 * database while the operation is being performed.*/
              //  progressBar.setVisibility(View.VISIBLE);
                List<User> userList = ((List<User>) resource.getData());
                if (userList != null) {
                   // progressBar.setVisibility(View.INVISIBLE);
                    users.addAll(userList);
                    adapter.notifyDataSetChanged();
                    //adapter.setMovieList(movieList);
                }

            }
            break;
            case SUCCESS: {
                /*
                 * Retrieve whatever data you expect from here with just one object.*/
              //  progressBar.setVisibility(View.INVISIBLE);
                users.addAll((List<User>) resource.getData());
                adapter.notifyDataSetChanged();
                //movieListAdapter.setMovieList(((List<User>) resource.getData()));
            }
            break;
            case ERROR: {
                //progressBar.setVisibility(View.INVISIBLE);
            }
            break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_profile_fragment, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new UserAdapter(users, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        //to observe the data and update the UI:
       /* viewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.d("user %s", users.toString());
                UserProfileFragment.this.users.addAll(users);
                adapter.notifyDataSetChanged();

            }
        });*/

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Clicked: " + position, Toast.LENGTH_SHORT).show();
    }

    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

        private OnCLickListener listener;
        private List<User> userList;

        public UserAdapter(List<User> userList, OnCLickListener listener) {
            this.userList = userList;
            this.listener = listener;
        }

        @NonNull
        @Override
        public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_row_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
            User user = userList.get(position);
            holder.tvUserId.setText("UserId: " + user.getUserId());
            holder.tvId.setText("Id: " + user.getId());
            holder.tvTitle.setText("Title: " + user.getTitle());
            holder.tvBody.setText("Body: " + user.getBody());

        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView tvUserId;
            private TextView tvId;
            private TextView tvTitle;
            private TextView tvBody;
            private ConstraintLayout layout;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvUserId= (TextView)itemView.findViewById(R.id.userId);
                tvId= (TextView)itemView.findViewById(R.id.id);
                tvTitle= (TextView)itemView.findViewById(R.id.title);
                tvBody= (TextView)itemView.findViewById(R.id.body);
                layout= (ConstraintLayout)itemView.findViewById(R.id.constraintLayout);
                layout.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {
                listener.onItemClick(getAdapterPosition());
            }
        }
    }
}