package com.taobao.dexposed;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.hotpatch.IPatchInfoRequest;
import com.hotpatch.OnRequestCallBackListener;
import com.hotpatch.PatchInfo;
import com.hotpatch.RequestManager;
import com.hotpatch.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * The default implement of IPatchInfoRequest,which used to get @see PatchInfo.
 * Created by renxuan on 15/7/29.
 */
public class DefaultPatchInfoRequest implements IPatchInfoRequest {
    private String hostUrl="http://192.168.0.100:8080/PathServer/patch?version=";
    private Context mContext;
    public DefaultPatchInfoRequest(Context ctx){
        this.mContext=ctx;
    }
    @Override
    public void getPatchInfo(OnRequestCallBackListener listener, String currentVersion, Object... objects) {
        String version= Utils.getVersionName(mContext);
        hostUrl=hostUrl+version;
        GetPatchInfoTask task=new GetPatchInfoTask(hostUrl,listener);
        task.execute();
    }

    class GetPatchInfoTask extends AsyncTask<String,Integer,String>{
        private String requestUrl;
        private OnRequestCallBackListener mOnRequestCallBackListener;
        public GetPatchInfoTask(String url,OnRequestCallBackListener listener){
            this.requestUrl=url;
            this.mOnRequestCallBackListener=listener;
        }
        @Override
        protected String doInBackground(String... params) {
            StringBuilder result=new StringBuilder();
            InputStream inStream = null;
            try {
                URL url = new URL(requestUrl);
                URLConnection urlConnection = url.openConnection();
                inStream = urlConnection.getInputStream();

                int byteread = 0;
                byte[] buffer = new byte[1024];
                InputStreamReader reader=new InputStreamReader(inStream);
                BufferedReader bufferedReader=new BufferedReader(reader);
                String line=null;

                while((line=bufferedReader.readLine())!=null){
                    result.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != inStream) {
                    try {
                        inStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!TextUtils.isEmpty(s)){
                try {
                    JSONObject object=new JSONObject(s);
                    String md5= object.get("md5").toString();
                    String url=object.get("patchApkUrl").toString();
                    if(null!=mOnRequestCallBackListener){
                        PatchInfo info=new PatchInfo();
                        info.apkMd5=md5;
                        info.apkFileUrl=url;
                        mOnRequestCallBackListener.onRequest(info);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
