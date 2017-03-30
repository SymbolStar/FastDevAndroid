package cn.com.igdj.library.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.ContentBody;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;

public class UploadPictures {

	public static void multiUploadFile(final String address, final List<String> filesPath, final String posString,
			final JSONResultHandler resultHandler) throws UnsupportedEncodingException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// HttpClient对象
				HttpClient httpClient = new DefaultHttpClient();
				// 采用POST的请求方式
				// 这是上传服务地址http://10.0.2.2:8080/WebAppProject/main.do?method=upload2
				HttpPost httpPost = new HttpPost(address);
				// MultipartEntity对象，需要httpmime-4.1.1.jar文件。
				MultipartEntity multipartEntity = new MultipartEntity();
				// StringBody对象，参数
				StringBody param = null;
				try {
					param = new StringBody(posString);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				multipartEntity.addPart("arg", param);
				// filesPath为List<String>对象，里面存放的是需要上传的文件的地址
				if (filesPath != null) {
					for (String path : filesPath) {
						// FileBody对象，需要上传的文件
						ContentBody file = new FileBody(new File(path));
						multipartEntity.addPart("file", file);
					}
				}
				// 将MultipartEntity对象赋值给HttpPost
				httpPost.setEntity(multipartEntity);
				HttpResponse response = null;
				try {
					// 执行请求，并返回结果HttpResponse
					response = httpClient.execute(httpPost);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 上传成功后返回
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					try {
						String result = EntityUtils.toString(response.getEntity());
						resultHandler.onSuccess(result);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("-->success");
				} else {
					System.out.println("-->failure");
				}
			}
		}).start();
	}
}
