package com.xlc.okhttplib;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity implements JsonCallback {
	
	OKHttpUtils okHttpUtils;
	String url="http://121.41.43.128:800/Odier/findDevice.action";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		okHttpUtils = new OKHttpUtils.Builder(this).build();
		FormBody.Builder builder = new FormBody.Builder();  
	    /* 添加两个参数 */  
	    builder.add("id","1301");  
	   
	    FormBody body = builder.build();  
		
		Request request = new Request.Builder().url(url).post(body).build();
		
		okHttpUtils.request(request, CacheType.ONLY_NETWORK, this);
//		okHttpUtils.uploadFile(url, file, headers, uploadListener);
		OkHttpClient mHttpClient = new OkHttpClient();  
		 

		mHttpClient.newCall(request).enqueue(new Callback() {
		
		@Override
		public void onResponse(Call call, Response response) throws IOException {
			if(response.isSuccessful())  
			{  
				String str = response.body().string();
			 	Log.i("result2:", str);
			}  
		
			
		}
		
		@Override
		public void onFailure(Call call, IOException e) {
			// TODO Auto-generated method stub
			
		}
	});
	
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		okHttpUtils.getClient().dispatcher().cancelAll();
		super.onDestroy();
	}
	
	 private void upFile(OkHttpClient mHttpClient){
	        /* 第一个要上传的file */
	        File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.jpg");
	        RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream") , file1);
	        String file1Name = "testFile1.txt";

	        /* 第二个要上传的文件,这里偷懒了,和file1用的一个图片 */
	        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.jpg");
	        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
	        String file2Name = "testFile2.txt";


	        /* form的分割线,自己定义 */
	        String boundary = "xx--------------------------------------------------------------xx";

	        MultipartBody mBody = new MultipartBody.Builder(boundary).setType(MultipartBody.FORM)
	                /* 上传一个普通的String参数 , key 叫 "p" */
	                .addFormDataPart("p" , "你大爷666")
	                /* 底下是上传了两个文件 */
	                .addFormDataPart("file" , file1Name , fileBody1)
	                .addFormDataPart("file" , file2Name , fileBody2)
	                .build();

	        /* 下边的就和post一样了 */
	        Request request = new Request.Builder().url("http://192.168.10.117:8080/test").post(mBody).build();
	        mHttpClient.newCall(request).enqueue(new Callback() {
	            public void onResponse(Call call, Response response) throws IOException {
	                final  String bodyStr = response.body().string();
	                final boolean ok = response.isSuccessful();
	                runOnUiThread(new Runnable() {
	                    public void run() {
	                       
	                    }
	                });
	            }
	            public void onFailure(Call call, final IOException e) {
	                runOnUiThread(new Runnable() {
	                    public void run() {
	                    }
	                });
	            }
	        });
	    }
	
	

	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	@Override
	public void onFailure(Call call, Exception e) {
		// TODO Auto-generated method stub
		call.cancel();
		
	}



	@Override
	public void onResponse(Call call, String object) throws IOException {
		Log.i("result:1", object);
		call.cancel();
		
	}



	@Override
	public void onFinish() {
		
	}



	@Override
	public void onStarted() {
		// TODO Auto-generated method stub
		
	}



}
