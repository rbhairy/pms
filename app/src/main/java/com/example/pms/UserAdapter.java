package com.example.pms;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<UserModel> userModelList;
    Context mContext;

    public UserAdapter(Context context, List<UserModel> userModelList) {
        this.userModelList = userModelList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        int pic_id = userModelList.get(position).getUserPic();
        String user_name = userModelList.get(position).getName();
        String user_serial_no = userModelList.get(position).getSerialNo();
        String user_pass_no = userModelList.get(position).getPassNo();
        String user_mobile_no = userModelList.get(position).getMobileNumber();

        if (false) {
            holder.setData(pic_id, user_name, user_serial_no, user_pass_no, user_mobile_no);
        } else {
            byte[] dp_thumbnail = userModelList.get(position).getDpThumbnail();
            holder.setData(pic_id, user_name, user_serial_no, user_pass_no, user_mobile_no, dp_thumbnail);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SingleUserViewActivity.class);

                /* pass bytes using bundle */
                byte[] dp_thumbnail = userModelList.get(position).getDpThumbnail();
                Bundle bundle = new Bundle();
                bundle.putByteArray("dp_thumbnail", dp_thumbnail);
                intent.putExtras(bundle);
                //intent.putExtra("db_thumbnail", userModelList.get(position).getDpThumbnail());

                intent.putExtra("serial_no", userModelList.get(position).getSerialNo());
                intent.putExtra("pass_no", userModelList.get(position).getPassNo());
                intent.putExtra("adhaar_card_no", userModelList.get(position).getAdhaarCardNo());
                intent.putExtra("election_id", userModelList.get(position).getElectionId());
                intent.putExtra("name", userModelList.get(position).getName());
                intent.putExtra("father_name", userModelList.get(position).getFatherName());
                intent.putExtra("age", userModelList.get(position).getAge());
                intent.putExtra("sex", userModelList.get(position).getSex());
                intent.putExtra("permanent_address", userModelList.get(position).getPermanentAddress());
                intent.putExtra("temp_address", userModelList.get(position).getTempAddress());
                intent.putExtra("mobile_number", userModelList.get(position).getMobileNumber());
                mContext.startActivity(intent);
            }
        });
    }

    public static String getMD5(byte[] source) {
        StringBuilder sb = new StringBuilder();
        java.security.MessageDigest md5 = null;
        try {
            md5 = java.security.MessageDigest.getInstance("MD5");
            md5.update(source);
        } catch (NoSuchAlgorithmException e) {
        }
        if (md5 != null) {
            for (byte b : md5.digest()) {
                sb.append(String.format("%02X", b));
            }
        }
        return sb.toString();
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_picture;
        private TextView tv_name;
        private TextView tv_serialNo;
        private TextView tv_passNo;
        private TextView tv_mobileNo;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_picture = itemView.findViewById(R.id.ulPicture);
            tv_name = itemView.findViewById(R.id.ulName);
            tv_serialNo = itemView.findViewById(R.id.ulSerialNo);
            tv_passNo = itemView.findViewById(R.id.ulPassNo);
            tv_mobileNo= itemView.findViewById(R.id.ulMobileNo);
            parentLayout = itemView.findViewById(R.id.ulImageView);
        }

        private void setData(int user_pic, String user_name, String user_serial_no,
                             String user_pass_no, String user_mobile_no, byte[] dp_thumbnail) {
            Bitmap bmp = BitmapFactory.decodeByteArray(dp_thumbnail, 0, dp_thumbnail.length);
            iv_picture.setImageBitmap(bmp);
            tv_name.setText(user_name);
            tv_serialNo.setText(user_serial_no);
            tv_passNo.setText(user_pass_no);
            tv_mobileNo.setText(user_mobile_no);
        }

        private void setData(int user_pic, String user_name, String user_serial_no,
                             String user_pass_no, String user_mobile_no) {
            iv_picture.setImageResource(user_pic);
            tv_name.setText(user_name);
            tv_serialNo.setText(user_serial_no);
            tv_passNo.setText(user_pass_no);
            tv_mobileNo.setText(user_mobile_no);
        }
    }
}
