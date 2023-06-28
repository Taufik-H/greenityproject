package project.greetiny.ucapan;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import project.greetiny.MainActivity;
import project.greetiny.R;
import project.greetiny.auth.SignInActivity;
import project.greetiny.fragment.FragmentList;

public class UlangTahun extends Activity {


    private EditText kirimke, ucapan;
    Button tanggal,buttonback;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    private ImageView ImageContainer;
    public Uri imageUrl,uri;
    private String getSubject, getTanggal, getUcapan, getGambar, getType,type,username;
    private StorageReference reference;
    DatabaseReference getReference;

    FirebaseStorage storage;
    DatabaseReference database;
    StorageReference storageReference;
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int  REQUEST_CODE_GALLERY = 2;
    private View Simpan, getfoto;

    LottieAnimationView animationView;
    TextView btn_text;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulang_tahun);

        //Button Simpan
        buttonback = findViewById(R.id.buttonback);
        Simpan = findViewById(R.id.btn_simpanUcapan);
        animationView = findViewById(R.id.button_animation);
        //Input Foto
        getfoto = findViewById(R.id.btnGetFotoCard);
        ImageContainer = findViewById(R.id.imageContainer);

        //Input Data
        kirimke = findViewById(R.id.ed_subject);
        ucapan = findViewById(R.id.ed_ucapan);

        //Date Picker
        tanggal = findViewById(R.id.ed_tanggal);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy");

        btn_text = findViewById(R.id.btn_text);
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        reference = FirebaseStorage.getInstance().getReference();

        //mendapatkan Instance dari Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        getReference = database.getReference();

        //Fungsi Button Simpan
        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getType = "Ulang tahun";
                getSubject = kirimke.getText().toString();
                getTanggal = tanggal.getText().toString();
                getUcapan = ucapan.getText().toString();
                Simpan.setEnabled(false);
                btn_text.setVisibility(View.GONE);
                animationView.setVisibility(View.VISIBLE);



                checkUser();

            }
        });
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getimage();
            }
        });


    }

    private void getimage(){
        Intent imageIntentGallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(imageIntentGallery, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //menghandle hasil data yang diambil dari kamera atau galeri pada ImageVIew
        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if (resultCode==RESULT_OK){
                    ImageContainer.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ImageContainer.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if (resultCode==RESULT_OK){
                    ImageContainer.setVisibility(View.VISIBLE);
                    uri = data.getData();
                    ImageContainer.setImageURI(uri);
                }
                break;
        }

    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                tanggal.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void checkUser() {

        //mengecek apakah ada data yang kosong
        if(TextUtils.isEmpty(getSubject)||TextUtils.isEmpty(getTanggal)|| TextUtils.isEmpty(getUcapan)||uri == null){
            //Jika ada, maka akan menampilkan pesan singkat
            animationView.setVisibility(View.GONE);
            btn_text.setVisibility(View.VISIBLE);
            Toast.makeText(UlangTahun.this, "Masih ada yang kosong!!", Toast.LENGTH_SHORT).show();
        }else{
            //Mendapatkan data dari ImageView sebagai bytes
            ImageContainer.setDrawingCacheEnabled(true);
            ImageContainer.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) ImageContainer.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            //mengkompres Bitmap menjadi JPG dengan kualitas gambar 100%
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] bytes =stream.toByteArray();

            //Lokasi lengkap dimana gambar akan disimpan
            String namaFile = UUID.randomUUID()+".jpg";
            final String pathImage = "gambar/"+namaFile;
            UploadTask uploadTask = reference.child(pathImage).putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            String currentUserUid = currentUser.getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("kartu");
                            DatabaseReference newCardRef = databaseReference.push();
                            String cardId = newCardRef.getKey();



                            data_ulangTahun ultahBaru = new data_ulangTahun();
                            ultahBaru.setUsername(currentUser.getDisplayName());
                            ultahBaru.setType(getType);
                            ultahBaru.setSubject(getSubject);
                            ultahBaru.setTanggal(getTanggal);
                            ultahBaru.setUcapan(getUcapan);
                            ultahBaru.setGambar(uri.toString());
                            ultahBaru.setUserId(currentUserUid);
                            ultahBaru.setWebsiteUrl("https://greetinyweb.vercel.app/kartu/" + cardId);




                            newCardRef.setValue(ultahBaru).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        kirimke.setText(getSubject);
                                        tanggal.setText(getTanggal);
                                        ucapan.setText(getUcapan);

                                        getGambar = "";
                                        Toast.makeText(UlangTahun.this, "Data Berhasil Tersimpan", Toast.LENGTH_SHORT).show();

                                        animationView.setVisibility(View.GONE);
                                        btn_text.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(UlangTahun.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        animationView.setVisibility(View.GONE);
                                        btn_text.setVisibility(View.VISIBLE);
                                        Toast.makeText(UlangTahun.this, "Data Gagal Tersimpan", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });

                }
            });

        }
    }
}