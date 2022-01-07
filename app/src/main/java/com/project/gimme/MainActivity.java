package com.project.gimme;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.project.gimme.DataBase.DataBaseManager;
import com.project.gimme.databinding.ActivityMainBinding;
import com.project.gimme.pojo.User;

/**
 * @author DrGilbert
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DataBaseManager dataBaseManager = DataBaseManager.getInstance(this);
//        User user = new User();
//        user.setBirthday(new Date());
//        user.setAvatar("111");
//        user.setCity(1);
//        user.setCompany("111");
//        user.setMail("111");
//        user.setMotto("11");
//        user.setNick("11");
//        user.setId(1);
//        user.setOccupation(1);
//        user.setProvince(1);
//        user.setCountry(1);
//        System.out.println("创建一个用户...");
//        dataBaseManager.insertUser(user);
        User user1 = dataBaseManager.getUser(1);
        System.out.println(user1.getAvatar());
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}