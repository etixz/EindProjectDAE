package dae.mob123;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//author: DG
public class MainActivity extends AppCompatActivity {
    // navigeren dmv navhost uit UI
    private NavController navController;
    // navigatie-opties instellen in toolbar uit UI
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // verwijzing maken naar component in UI
        // klasse van androidx importeren
        Toolbar toolbar = findViewById(R.id.toolbar);
        // compatibiliteit met actionbar uit vorige versies
        setSupportActionBar(toolbar);
        // navcontroller binnen huidige activity linken aan de navhost via ID
        navController = Navigation.findNavController(this, R.id.nav_container);
        // instellen van top-level in de navigatie
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.home_fragment).build();
        // navcontroller en de appbarconfig aan elkaar koppelen
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    //de terug-knop uit UI neemt functies over van de fysieke terug-knop van device
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //welk menu-item is geselecteerd
        switch (item.getItemId()){
            //navigatie starten door menu-item in de nav controller aan te spreken
            //ID van fragment in main_nav moet overeenkomen met ID van component in main_menu
            case R.id.settings_fragment: NavigationUI.onNavDestinationSelected(item, navController);
            case R.id.request_mural_dialog : NavigationUI.onNavDestinationSelected(item, navController);
        }
        return super.onOptionsItemSelected(item);
    }

}
