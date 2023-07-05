package project.greetiny.Update;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import project.greetiny.MainActivity;
import project.greetiny.R;
import project.greetiny.fragment.FragmentList;

public class UpdateHariRaya extends AppCompatActivity {
    private EditText updateKirimke, updateUcapan;
    private Button backButton, chooseDateButton;
    private ImageView imageContainer;
    private RelativeLayout updateButton;
    private Uri selectedImageUri;
    private static final int REQUEST_CODE_GALLERY = 2;
    private MaterialCardView chooseImageButton;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String cardId;
    private String subject;
    private String tanggal;
    private String image;
    private String imageUrl;
    private String ucapan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hari_raya);

        backButton = findViewById(R.id.buttonback);
        updateButton = findViewById(R.id.btn_simpanUcapan);
        chooseImageButton = findViewById(R.id.btnGetFotoCard);
        chooseDateButton = findViewById(R.id.update_tanggal);
        imageContainer = findViewById(R.id.imageContainer);
        updateKirimke = findViewById(R.id.update_subject);
        updateUcapan = findViewById(R.id.update_ucapan);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to FragmentList
                Intent intent = new Intent(UpdateHariRaya.this, FragmentList.class);
                startActivity(intent);
                finish();
            }
        });

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to choose image
                openGallery();
            }
        });

        chooseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show date picker dialog
                showDatePickerDialog();
            }
        });

        // Retrieve the data passed from myadapter using getIntent().getStringExtra() and getIntent().getSerializableExtra()
        Intent intent = getIntent();
        cardId = intent.getStringExtra("cardId");
        subject = intent.getStringExtra("subject");
        tanggal = intent.getStringExtra("tanggal");
        image = intent.getStringExtra("image");
        ucapan = intent.getStringExtra("ucapan");
        imageUrl = intent.getStringExtra("image");

        // Set the existing values to the EditText fields
        updateKirimke.setText(subject);
        updateUcapan.setText(ucapan);
        chooseDateButton.setText(tanggal);

        if (image != null) {
            Log.d("Received Image", image); // Add this line to log the value of "image"

            // Load the previous image using Glide
            Glide.with(this)
                    .load(image)
                    .into(imageContainer);
        } else {
            Log.d("Received Image", "Image URL is null"); // Add this line to log if the image URL is null
        }
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCard();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Glide.with(this)
                    .load(selectedImageUri)
                    .into(imageContainer);

            image = selectedImageUri.toString();
        }
    }

    private void showDatePickerDialog() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
                chooseDateButton.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    private void updateCard() {
        String updatedSubject = updateKirimke.getText().toString().trim();
        String updatedUcapan = updateUcapan.getText().toString().trim();
        String updatedTanggal = chooseDateButton.getText().toString().trim();

        if (TextUtils.isEmpty(updatedSubject) || TextUtils.isEmpty(updatedUcapan) || TextUtils.isEmpty(updatedTanggal)) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get a reference to the card in the database
        DatabaseReference cardRef = FirebaseDatabase.getInstance().getReference("kartu").child(cardId);

        // Update only the necessary fields using a HashMap
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("subject", updatedSubject);
        updates.put("tanggal", updatedTanggal);
        updates.put("ucapan", updatedUcapan);

        // Update the card in the database
        cardRef.updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(UpdateHariRaya.this, "Card updated successfully", Toast.LENGTH_SHORT).show();

                    // Return to FragmentList
                    Intent intent = new Intent(UpdateHariRaya.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(UpdateHariRaya.this, "Failed to update card", Toast.LENGTH_SHORT).show();
                    Log.e("UpdateCard", "Failed to update card", e); // Log the error message
                });

    }

}