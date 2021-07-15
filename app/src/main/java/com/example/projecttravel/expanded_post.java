package com.example.projecttravel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



import static com.example.projecttravel.blog_layout.EXTRA_URL;
import static com.example.projecttravel.blog_layout.EXTRA_TITLE;
import static com.example.projecttravel.blog_layout.EXTRA_DESCRIPTION;
import static com.example.projecttravel.blog_layout.EXTRA_UIDI;
import static com.example.projecttravel.blog_layout.EXTRA_POSTID;

public class expanded_post extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private Button deleteBtn;
    private FirebaseAuth mAuth;

    private FirebaseStorage storage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_post);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String post_uid = intent.getStringExtra(EXTRA_UIDI);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);

        String post_key = intent.getStringExtra(EXTRA_POSTID);

        ImageView imageView = findViewById(R.id.addPostImage);
        TextView textTitle = findViewById(R.id.Title);
        TextView textDescript = findViewById(R.id.desc);

        Glide.with(this).load(imageUrl).into(imageView);
        textTitle.setText(title);
        textDescript.setText(description);

        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        mAuth = FirebaseAuth.getInstance();
        deleteBtn.setVisibility(View.INVISIBLE);
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
        storage = FirebaseStorage.getInstance();



//        FirebaseDatabase.getInstance().getReference().child("uploads")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        dataSnapshot.child("uploads");
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                            //here is your every post
//                            String key = snapshot.getKey();
//                            Object value = snapshot.getValue();
//                            Toast.makeText(getApplicationContext(),value.toString() , Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });



//        Toast.makeText(getApplicationContext(),mDatabase.child(post_key).getKey() , Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getUid(), Toast.LENGTH_LONG).show();

        if (mAuth.getCurrentUser().getUid().equals(post_uid)){
            deleteBtn.setVisibility(View.VISIBLE);
        }


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(post_key).removeValue();
                storage.getReferenceFromUrl(imageUrl).delete();
                Intent mainintent = new Intent(expanded_post.this, blog_layout.class);
                startActivity(mainintent);
            }
        });

    }
}