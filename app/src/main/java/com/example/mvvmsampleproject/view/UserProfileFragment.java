package com.example.mvvmsampleproject.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvmsampleproject.R;
import com.example.mvvmsampleproject.Resource;
import com.example.mvvmsampleproject.viewModel.UserProfileViewModel;
import com.example.mvvmsampleproject.database.entity.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;


public class UserProfileFragment extends Fragment/*extends BaseFragment<UserProfileViewModel>*/ implements OnCLickListener {


    private RecyclerView recyclerView;
    private List<User> users = new ArrayList<>();
    private UserAdapter adapter;
    private UserProfileViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_profile_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new UserAdapter(users, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getUserList()
                .observe(this, this::consumeResponse);
    }
    private void consumeResponse(Resource resource) {
        switch (resource.getCurrentState()) {
            case LOADING: {
                /*
                 * If network operation is going on then we will get the data from
                 * database while the operation is being performed.*/
                List<User> userList = ((List<User>) resource.getData());
                if (userList != null) {
                    users.addAll(userList);
                    adapter.notifyDataSetChanged();
                }

            }
            break;
            case SUCCESS: {
                /*
                 * Retrieve whatever data you expect from here with just one object.*/
                users.addAll((List<User>) resource.getData());
                adapter.notifyDataSetChanged();
            }
            break;
            case ERROR: {
                Toast.makeText(getActivity(), "Error while retrieving the data", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
    @Override
    public void onItemClick(int position) {

        Toast.makeText(getActivity(), "Clicked: " + position, Toast.LENGTH_SHORT).show();

        User user=users.get(position);
        Log.d("TAG", "onItemClick: "+user.toString());

        UserFragment fragment=UserFragment.newInstance(user.getId());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main_activity, fragment)
                .addToBackStack(null)
                .commit();
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
                tvUserId = (TextView) itemView.findViewById(R.id.userId);
                tvId = (TextView) itemView.findViewById(R.id.id);
                tvTitle = (TextView) itemView.findViewById(R.id.title);
                tvBody = (TextView) itemView.findViewById(R.id.body);
                layout = (ConstraintLayout) itemView.findViewById(R.id.constraintLayout);
                layout.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {
                listener.onItemClick(getAdapterPosition());
            }
        }
    }
}