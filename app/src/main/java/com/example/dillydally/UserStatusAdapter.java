
package com.example.dillydally;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class UserStatusAdapter extends RecyclerView.Adapter<UserStatusAdapter.UserStatusViewHolder> {

    private List<User> userList;

    public UserStatusAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_status, parent, false);
        return new UserStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserStatusViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.displayName);
        holder.userStatus.setText(user.isOnline ? "Online" : "Offline");
        holder.statusIndicator.setImageResource(user.isOnline ? R.drawable.ic_online : R.drawable.ic_offline);
        Glide.with(holder.itemView.getContext()).load(user.photoUrl).into(holder.userAvatar);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserStatusViewHolder extends RecyclerView.ViewHolder {
        ImageView userAvatar;
        TextView userName;
        TextView userStatus;
        ImageView statusIndicator;

        public UserStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            userName = itemView.findViewById(R.id.user_name);
            userStatus = itemView.findViewById(R.id.user_status);
            statusIndicator = itemView.findViewById(R.id.status_indicator);
        }
    }
}
