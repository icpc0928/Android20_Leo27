package tw.org.iii.leo.leo27;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mesg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mesg = findViewById(R.id.mesg);

    }

    private MyAsyncTask myAsyncTask;
    public void test1(View view) {
        mesg.setText("");
        myAsyncTask = new MyAsyncTask();
        //可放不定個數參數（也可以不放） 所以帶進去會是陣列 看第一個泛型的東西是什麼  (也可以不傳）
        myAsyncTask.execute("Leo","Brad","May");

    }

    public void test2(View view) {
        if(myAsyncTask != null){
            Log.v("leo","status : "+myAsyncTask.getStatus().name());
            if(!myAsyncTask.isCancelled()){
                myAsyncTask.cancel(true);
            }
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Object, String>{
        private int total,i;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v("leo","onPreExecute()");
            mesg.append("start...\n");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.v("leo","onPoseExecute : " +result);
            mesg.append(result);
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            Log.v("leo","onprogressUpdate()" + values[0]);
            mesg.append(values[1]+"..."+values[0]+"%"+"\n");
        }

        @Override
        protected void onCancelled(String result) {
            super.onCancelled(result);
            Log.v("leo","onCancelled(Void) " + result);
            mesg.append(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.v("leo","onCancelled()");
        }

        @Override
        protected String doInBackground(String... names) {
            Log.v("leo","doinbackg()");
            total = names.length;

            for(String name : names){
                i++;
                try {
                    Thread.sleep(1500);
                }catch(Exception e){ }

                Log.v("leo",name);
//                mesg.append(name+"\n");
                publishProgress((int)Math.ceil(i*1.0/total*100),name);
            }
            if(isCancelled()){
                return "Cancel";
            }else
            {
                return "Bye bye";
            }

        }
    }
}
