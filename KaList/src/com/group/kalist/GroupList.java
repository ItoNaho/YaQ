package com.group.kalist;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
/**
 * 選手名鑑
 *
 * 第一画面表示用アクティビティクラス
 * 球団リストを表示する *
 * @author ohs05032
*/

public class GroupList extends Activity implements OnClickListener {

	//定数の作成
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_list);

    	Button speakButton = (Button) findViewById(R.id.button1);
		speakButton.setOnClickListener(this);

		ListView lvtemp = (ListView) findViewById(R.id.lv_temple);

		List<String> templeList = createPrefectureList();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,templeList);

		lvtemp.setAdapter(adapter);
		lvtemp.setOnItemClickListener(new  ListItemClickListener());

	}

	/**
	 * 音声認識ボタンが押されたときの処理
	* 第3ページへ処理を移管する
	*/
	 public void onClick(View v) {

	      PackageManager pm = getPackageManager();
	      List<ResolveInfo> activities = pm.queryIntentActivities(
	      new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
         startVoiceRecognitionActivity();
}



    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.JAPAN.toString());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "選手名を話す");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
    	String resultsString="";
        ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        resultsString=results.get(0);

		Intent intent = new Intent(GroupList.this,ThirdListActivity.class);
		intent.putExtra("resultString", resultsString);
		startActivity(intent);

        }

        super.onActivityResult(requestCode, resultCode, data);
        }

	/**
	 *球団を生成するメソッド
	 *@author ishibiki
	 * @return 球団リストオブジェクト
	 */
	private  List<String> createPrefectureList(){
				List<String> groupList = new ArrayList<String>();

				groupList.add("読売ジャイアンツ");
				groupList.add("阪神タイガース");
				groupList.add("広島東洋カープ");
				groupList.add("中日ドラゴンズ");
				groupList.add("横浜DeNAベイスターズ");
				groupList.add("東京ヤクルトスワローズ");

				groupList.add("東北楽天ゴールデンイーグルス");
				groupList.add("埼玉西武ライオンズ");
				groupList.add("千葉ロッテマリーンズ");
				groupList.add("福岡ソフトバンクホークス");
				groupList.add("オリックス・バファローズ");
				groupList.add("北海道日本ハムファイターズ");

				return  groupList;
	}
	/**
	 * リストが選択されたときの処理
	 * 第2画面へ処理を移管する
	 *
	 * @author kaneda
	 */

	private class ListItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?>  parent, View view, int position,long id){
			ListView listView = (ListView) parent;
			String group = (String) listView.getItemAtPosition(position);

			Intent intent = new Intent(GroupList.this,SecondListActivity.class);
			intent.putExtra("selectedgroup", group);
			startActivity(intent);
		}
	}
}