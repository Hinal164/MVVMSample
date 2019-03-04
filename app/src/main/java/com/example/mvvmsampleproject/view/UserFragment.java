package com.example.mvvmsampleproject.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mvvmsampleproject.R;
import com.example.mvvmsampleproject.Resource;
import com.example.mvvmsampleproject.viewModel.UserProfileViewModel;
import com.example.mvvmsampleproject.database.entity.User;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import dagger.android.support.AndroidSupportInjection;

public class UserFragment extends Fragment {

    private int id;
    private UserProfileViewModel viewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    //@BindView(R.id.userId)
    TextView tvUserId;
    // @BindView(R.id.id)
    TextView tvId;
    // @BindView(R.id.title)
    TextView tvTitle;
    // @BindView(R.id.body)
    TextView tvBody;

    public static UserFragment newInstance(int id) {
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        // ButterKnife.bind(getActivity());

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        tvUserId = (TextView) view.findViewById(R.id.userId);
        tvId = (TextView) view.findViewById(R.id.id);
        tvTitle = (TextView) view.findViewById(R.id.title);
        tvBody = (TextView) view.findViewById(R.id.body);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            id = getArguments().getInt("Id");

            viewModel.observeUser().observe(this, this::consumeResponse);
            viewModel.loadUser(id);
        }
    }

    private void consumeResponse(Resource resource) {
        switch (resource.getCurrentState()) {
            case LOADING: {

            }
            break;
            case SUCCESS: {
                User user = (User) resource.getData();
                tvUserId.setText("UserId: " + user.getUserId());
                tvId.setText("Id: " + user.getId());
                tvTitle.setText("Title: " + user.getTitle());
                tvBody.setText("Body: " + user.getBody());
            }
            break;
            case ERROR: {

            }
            break;
        }
    }
}
