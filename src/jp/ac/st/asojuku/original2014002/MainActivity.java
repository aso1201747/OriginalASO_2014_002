package jp.ac.st.asojuku.original2014002;




import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity  implements View.OnClickListener{

	SQLiteDatabase sdb = null;
	MySQLiteOpenHelper helper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}




	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		Button btnch = (Button)findViewById(R.id.btnch);
		btnch.setOnClickListener(this);

		Button btn = (Button)findViewById(R.id.btn);
		btn.setOnClickListener(this);


		Button btntou = (Button)findViewById(R.id.btntou);
		btntou.setOnClickListener(this);


		if(sdb == null) {
			helper = new MySQLiteOpenHelper(getApplicationContext());
		}
		try{
			sdb = helper.getWritableDatabase();
		}catch(SQLiteException e){
			Log.e("ERROR", e.toString());
			// 異常終了
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	public void onClick(View v) {
		int aaa = 0;
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()){ //どのボタンが押されたか判定
			case R.id.btnch: //btnMsgが押された
				aaa = 2;

				break;
			case R.id.btn:
				aaa = 1;
				break;

			case R.id.btntou:
				EditText etv = (EditText)findViewById(R.id.edtMsg);
				String inputMsg = etv.getText().toString();


				if(inputMsg!=null && !inputMsg.isEmpty()){
					helper.insertHitokoto(sdb, inputMsg);
				}

				etv.setText("");
				break;

		}
		// 生成して代入用のIntentインスタンス変数を用意
		Intent intent = null;
		// ranの値によって処理をわける
		switch(aaa){
			case 2:
				String strHitokoto = helper.selectRamdomHitokoto(sdb);
				intent = new Intent(MainActivity.this, HitokotoActivity.class);

				intent.putExtra("hitokoto", strHitokoto);
				// 次画面のアクティビティ起動
				startActivity(intent);
				break;
			case 1:
				intent = new Intent(MainActivity.this, MaintenanceActivity.class);

				startActivity(intent);
				break;

		}
	}

}
