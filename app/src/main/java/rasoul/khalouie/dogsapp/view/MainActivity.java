package rasoul.khalouie.dogsapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

import rasoul.khalouie.dogsapp.R;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    private static final int PERMISSION_SEND_SMS = 342;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this,navController);

        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,(DrawerLayout) null);
    }

    public void checkSmsPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){

                new AlertDialog.Builder(this)
                        .setTitle("Send SMS Permission")
                        .setMessage("This app require to access to SMS permission")
                        .setPositiveButton("Ask Again !", (dialogInterface, i) -> {
                            requestSmsPermission();
                        })
                        .setNegativeButton("Cancel", (dialogInterface, i) -> {
                            notifyDetailsFragment(false);
                        })
                        .show();

            }else {
                requestSmsPermission();
            }
        }else {
            notifyDetailsFragment(true);
        }
    }

    private void requestSmsPermission() {
        String [] permissions = {Manifest.permission.SEND_SMS};
        ActivityCompat.requestPermissions(this,permissions,PERMISSION_SEND_SMS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_SEND_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    notifyDetailsFragment(true);
                }else {
                    notifyDetailsFragment(false);
                }
                break;
        }
    }

    private void notifyDetailsFragment(boolean permissionGranted) {
        Fragment activeFragment = fragment.getChildFragmentManager().getPrimaryNavigationFragment();
        if(activeFragment instanceof DetailsFragment){
            ((DetailsFragment) activeFragment).onPermissionResult(permissionGranted);
        }
    }

}