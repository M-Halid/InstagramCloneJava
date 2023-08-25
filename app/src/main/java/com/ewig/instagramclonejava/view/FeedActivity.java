package com.ewig.instagramclonejava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ewig.instagramclonejava.R;
import com.ewig.instagramclonejava.databinding.ActivityFeedBinding;
import com.ewig.instagramclonejava.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Post> postArrayList;
    private ActivityFeedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding=ActivityFeedBinding.inflate(getLayoutInflater());
    View view= binding.getRoot();
    setContentView(view);

        postArrayList= new ArrayList<>();
        auth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        getData();
    }

    private void getData(){
        //DocumentReference documentReference = firebaseFirestore.collection("Posts");
        firebaseFirestore.collection("Posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
           if (error!=null){
               Toast.makeText(FeedActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
           }
           if(value!=null){
               for (DocumentSnapshot snapshot: value.getDocuments()){
                   Map<String,Object> data =snapshot.getData();

                   String userEmail= (String) data.get("userEmail");
                    String comment= (String) data.get("comment");
                    String downloadUrl= (String) data.get("downloadUrl");

                    Post post = new Post(userEmail, comment,downloadUrl);
                    postArrayList.add(post);

               }
           }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater menuInflater = getMenuInflater();
       menuInflater.inflate(R.menu.options_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            //Add Post
        if (item.getItemId()==R.id.addpost){
            Intent intentToUpload= new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intentToUpload);
        }
        // Sign Out
        if (item.getItemId()==R.id.signout){
            auth.signOut();
            Intent intentToMain= new Intent(FeedActivity.this,MainActivity.class);
            startActivity(intentToMain);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}