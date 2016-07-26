package com.android.privatemessenger.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.privatemessenger.R;
import com.android.privatemessenger.data.model.Chat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.BaseViewHolder> {

    private final String TAG = ChatListAdapter.this.getClass().getSimpleName();

    private Context context;
    private ArrayList<Chat> dataSet;
    private RecyclerItemClickListener recyclerItemClickListener;

    public ChatListAdapter(Context context, ArrayList<Chat> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.root_view)
        public RelativeLayout LLRootView;

        @BindView(R.id.tv_name)
        public TextView TVName;

        @BindView(R.id.tv_last_message)
        public TextView TVLastMessage;

        @BindView(R.id.iv_image)
        public ImageView IVImage;

        @BindView(R.id.tv_date)
        public TextView TVDate;

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_chat, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        RelativeLayout LLRootView = holder.LLRootView;
        TextView TVName = holder.TVName;
        TextView TVLastMessage = holder.TVLastMessage;
        TextView TVDate = holder.TVDate;
        ImageView IVImage = holder.IVImage;
        Chat chat = dataSet.get(position);

        if (chat.getParticipantsCount() == 2) {
            TVName.setText(chat.getParticipants().get(0).getName());
            IVImage.setImageResource(R.drawable.ic_person_primary_24dp);
        } else {
            TVName.setText(chat.getName());
            IVImage.setImageResource(R.drawable.ic_group_primary_24dp);
        }

        TVLastMessage.setText(chat.getLastMessage() == null ? "" : chat.getLastMessage().getMessage());
        TVDate.setText(chat.getFormattedDate());

        LLRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerItemClickListener != null) {
                    recyclerItemClickListener.onClick(holder.getAdapterPosition());
                }
            }
        });
        LLRootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (recyclerItemClickListener != null) {
                    recyclerItemClickListener.onLongClick(holder.getAdapterPosition());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void addItem(Chat chat) {
        dataSet.add(chat);
    }

    public ArrayList<Chat> getDataSet() {
        return dataSet;
    }

    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }
}
