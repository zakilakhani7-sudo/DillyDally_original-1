
package com.example.dillydally;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class UserStatusFragment extends Fragment {

    private List<User> userList = new ArrayList<>();
    private UserStatusAdapter userStatusAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_status, container, false);

        RecyclerView recyclerUserStatus = view.findViewById(R.id.recycler_user_status);
        recyclerUserStatus.setLayoutManager(new LinearLayoutManager(getContext()));
        userStatusAdapter = new UserStatusAdapter(userList);
        recyclerUserStatus.setAdapter(userStatusAdapter);

        // The Firebase Database code has been removed.
        // You can now populate the userList from a local source if you wish.

        return view;
    }
}
