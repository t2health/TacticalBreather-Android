package t2.TacticalBreatherHTML5;


import t2.tacticalBreather.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EulaActivity extends ABSWebViewActivity 
{
	public static final int EULA_ACTIVITY = 1537;

	Button btnAccept;
	Button btnDeny;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		btnAccept = (Button) this.findViewById(R.id.leftButton);
		btnAccept.setText(getString(R.string.eula_accept));
		btnAccept.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				AcceptPressed();
			}
		});

		btnDeny = (Button) this.findViewById(R.id.rightButton);
		btnDeny.setText("Decline");
		btnDeny.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				DeclinePressed();
			}
		});
		
		this.setTitle(getString(R.string.eula_title));
		this.setContent(getString(R.string.eula_content));
	}

	public void AcceptPressed() 
	{
		setEULAPreference();

		this.startActivity(new Intent(this, TacticalBreatherHTML5Main.class));
		this.finish();
	}

	public void DeclinePressed() 
	{
		this.finish();
	}
	
	public void setEULAPreference()
	{
		SharedPreferences settings = getSharedPreferences("TBPreferences", MODE_PRIVATE); 
		SharedPreferences.Editor prefEditor = settings.edit();
		prefEditor.putString("eula", "yes");
		prefEditor.commit();

	}
	

}
