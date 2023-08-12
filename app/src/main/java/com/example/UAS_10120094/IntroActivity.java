package com.example.UAS_10120094;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.uas_10120094.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

// Nim  : 10120094
// Nama : Tiara Trisanti Ramadhani
// Kelas: IF3

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private TabLayout tabIndicator;
    private Button btnNext;
    private int position = 0;
    private Button btnGetStarted;
    private Animation btnAnim;
    private TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Jika sudah login, langsung ke MainActivity
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
            return;
        }

        if (notePrefData()) {
            // Jika sudah pernah menampilkan IntroActivity sebelumnya, langsung ke LoginActivity
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_intro);

        // sembunyikan bilah tindakan
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // inisialisasi tampilan
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);

        // isi layar daftar
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Tampilan Interaktif", "Lihat catatan harian Anda disusun dalam kalender interaktif, memudahkan untuk melacak dan mengeksplorasi peristiwa serta perasaan pada tanggal-tanggal tertentu.", R.drawable.img4));
        mList.add(new ScreenItem("Peta Emosi Visual", "Saksikan perjalanan emosi Anda melalui peta warna yang mencerminkan perasaan pada setiap catatan harian, membantu Anda mengenali pola dan momen penting dalam hidup Anda.", R.drawable.img5));
        mList.add(new ScreenItem("Galeri Multimedia", "Berikan sentuhan hidup pada catatan harian dengan galeri multimedia, memungkinkan Anda mengunggah gambar, video, dan catatan suara yang terhubung dengan momen-momen spesial.", R.drawable.img6));

        // atur viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // atur tablayout dengan viewpager
        tabIndicator.setupWithViewPager(screenPager);

        // klik tombol berikutnya Pendengar
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == mList.size() - 1) { // saat kita mencapai layar terakhir
                    // tampilkan tombol GETSTARTED dan sembunyikan indikator dan tombol next
                    loadLastScreen();
                }
            }
        });

        // tablayout menambahkan pendengar perubahan
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1) {
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Memulai klik tombol pendengar
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser == null) {
                    Intent loginIntent = new Intent(IntroActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                } else {
                    Intent mainIntent = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }

                // Simpan preferensi bahwa layar intro telah ditampilkan
                savePrefsData();

                // Selesaikan IntroActivity
                finish();
            }
        });



        // lewati tombol klik pendengar
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });
    }

    private boolean notePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        return pref.getBoolean("isIntroOpnend", false);
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend", true);
        editor.apply();
    }

    // tampilkan Tombol GETSTARTED dan sembunyikan indikator dan tombol berikutnya
    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO: ADD an animation to the getstarted button
        // mengatur animasi
        btnGetStarted.setAnimation(btnAnim);
    }
}
