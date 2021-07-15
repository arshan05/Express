package com.example.projecttravel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class blog_layout extends AppCompatActivity implements travelAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_UIDI= "uid";
    public static final String EXTRA_POSTID = "postid";

    private RecyclerView recyclerView;

    private travelAdapter adapter;

    private DatabaseReference mDatabase;

    private ProgressDialog progressDialog;
    private List<Upload> uploads;
    Button AddPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_layout);
        AddPost = findViewById(R.id.addPost);

        AddPost.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        adding_item.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        SnapHelper startSnapHelper = new PagerSnapHelper();
        startSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);



        progressDialog = new ProgressDialog(this);

        uploads = new ArrayList<>();

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS );


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {progressDialog.dismiss();


                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                adapter = new travelAdapter(getApplicationContext(), uploads);

                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(blog_layout.this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onItemClick(int position) {

        Intent detailIntent = new Intent(this, expanded_post.class);
        Upload clickedItem = uploads.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getUrl());
        detailIntent.putExtra(EXTRA_POSTID, clickedItem.getUploadId());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_DESCRIPTION, clickedItem.getDescript());
        detailIntent.putExtra(EXTRA_UIDI, clickedItem.getUid());

        startActivity(detailIntent);
    }
}
