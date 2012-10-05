package t2.TacticalBreatherHTML5;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


public class StartupActivity extends Activity {
	/** Called when the activity is first created. */


	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		SharedPreferences settings = getSharedPreferences("TBPreferences", MODE_PRIVATE);
		if(settings.getString("eula", "no").equals("no"))
		{
			this.startActivity(new Intent(this, EulaActivity.class));
			this.finish();
		}
		else
		{
			this.startActivity(new Intent(this, TacticalBreatherHTML5Main.class));
			this.finish();
		}

	}
}