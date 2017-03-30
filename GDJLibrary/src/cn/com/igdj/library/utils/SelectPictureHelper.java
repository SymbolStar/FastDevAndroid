package cn.com.igdj.library.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/*******************************************************************************
 *
 *  * Copyright (C) 2014. 邹林凌
 *  * 版权所有
 *  *
 *  * 功能描述：提供了从相册选择照片以及从相册拍照的使用照片的功能
 *  * 使用方法 1：使用构造函数，构建helper对象，根据是在Activity中使用还是Fragment中使用选择不同的构造函数
 *  *		 2：在合适的地方，调用执行函数，buildDialog
 *  *        3：重写Activity或者Fragment的onActivityResult方法，调用SelectPictureHelper.onActivityResult,
 *  *			在onResult中根据返回的图片路径进行UI的更新
 *  *	注意：dialog上的文字，都可以通过setter方法进行更改，需要在执行buildDialog之前修改
 *  		如果剪辑的效果不好或者有问题，可以继承该类并重写onActivityResult方法来自行修改
 *  * 作者：邹林凌
 *  * 创建时间：8/29/14 3:49 PM
 *  *
 *  * 修改人：
 *  * 修改描述：
 *  * 修改日期
 * /
 ******************************************************************************/

public class SelectPictureHelper {
    boolean isLimit = true; //动态空值是否使用剪辑
    public static final int REQUEST_CAMERA = 1024;
    public static final int REQUEST_ALBUM  = 1025;
    private String ERROR_NO_SD = "请插入SD卡";
    private String SELECT_PHOTO = "选择照片";
    private String SELECT_FROM_ALBUM = "从相册选取";
    private String SELECT_FROM_CAMERA = "重新拍摄";
    File imageFile;

    Activity activity;
    Fragment fragment;

    String captureImagePath;

    public boolean isLimit() {
        return isLimit;
    }

    public void setLimit(boolean isLimit) {
        this.isLimit = isLimit;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    /**
     * 构造函数
     * @param isLimit 是否剪辑图片
     * @param activity 如果是Activity中使用，使用这个构造函数
     */
    public SelectPictureHelper(boolean isLimit, Activity activity) {
        this.isLimit = isLimit;
        this.activity = activity;
        this.fragment = null;
    }
    /**
     * 构造函数
     * @param isLimit 是否分割图片
     * @param fragment 如果是Fragment中使用，则使用这个构造函数
     */
    public SelectPictureHelper(boolean isLimit, Fragment fragment) {
        this.isLimit = isLimit;
        this.fragment = fragment;
        this.activity = fragment.getActivity();
    }

    public void buildDialog(){
        new AlertDialog.Builder(activity)
                .setTitle(SELECT_PHOTO)
                .setItems(
                        new String[] { SELECT_FROM_ALBUM,
                        		SELECT_FROM_CAMERA },
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0: // 点击相册
                                        dialog.dismiss();
                                        startAlbumPicture();
                                        break;
                                    case 1: // 点击拍照
                                        dialog.dismiss();
                                        startCameraPicture();
                                    default:
                                        break;
                                }
                            }
                        }).create().show();
    }

    public void startCameraPicture(){    //相机
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(activity,ERROR_NO_SD,Toast.LENGTH_LONG).show();
            return;
        }
        imageFile = newFile();
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile)); //设置拍照完成之后的照片保存路径，不使用默认的路径

        if (fragment != null) {
            fragment.startActivityForResult(camera, REQUEST_CAMERA);
        } else {
            activity.startActivityForResult(camera, REQUEST_CAMERA);
        }
    }

    public void startAlbumPicture(){    //相册
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(activity,ERROR_NO_SD,Toast.LENGTH_LONG).show();
            return;
        }
        imageFile = newFile();
        Intent album = new Intent(Intent.ACTION_PICK, null);
        album.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (isLimit) {
            album.putExtra("crop", "true");
            album.putExtra("aspectX", 1);
            album.putExtra("aspectY", 1);
            album.putExtra("outputX", 150);
            album.putExtra("outputY", 150);
            album.putExtra("return-data", false);
        }
        album.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        album.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        if (fragment != null) {
            fragment.startActivityForResult(album, REQUEST_ALBUM);
        } else {
            activity.startActivityForResult(album, REQUEST_ALBUM);
        }
    }

    private File newFile() {
        String fileName = System.currentTimeMillis()+".jpg";
        String path = Environment.getExternalStorageDirectory() + "/cache/" + fileName;
        File imageFile = new File(path);
        if (!imageFile.getParentFile().exists()) {
            imageFile.getParentFile().mkdirs();
        }
        captureImagePath = path;
        return imageFile;
    }

    public String getFileDir(Uri uri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null, null);

        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        actualimagecursor.moveToFirst();

        String img_path = actualimagecursor.getString(actual_image_column_index);
        return img_path;
    }

    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, final Intent data, final Result result) {
        switch (requestCode) {
            case SelectPictureHelper.REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    if (isLimit) {
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(Uri.fromFile(getImageFile()), "image/*");
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("outputX", 150);
                        intent.putExtra("outputY", 150);
                        intent.putExtra("return-data", false);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getImageFile()));
                        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                        if (fragment != null) {
                            fragment.startActivityForResult(intent, REQUEST_ALBUM);
                        } else {
                            activity.startActivityForResult(intent, REQUEST_ALBUM);
                        }
                    } else {
                    	captureImagePath = getImageFile().getAbsolutePath();
                        result.onResult(getImageFile().getAbsolutePath());
                    }
                }

                break;
            case SelectPictureHelper.REQUEST_ALBUM:									// 调用相册，相机拍完后，剪辑返回
                if (resultCode == Activity.RESULT_OK) {
                    new Thread() {
                        @Override
                        public void run() {
                            if (!isLimit) {
                                copyFile(getFileDir(data.getData()), getImageFile().getAbsolutePath()); //把选中的文件拷贝到指定路径
                            }
                            //这里放到线程里面，以防止文件没有复制完成
                            result.onResult(getImageFile().getAbsolutePath());
                        }
                    }.start();
                }
                break;
            default:
                break;
        }
    }

    public static abstract class Result {
        public abstract void onResult(String imagePath);
    }

	public String getERROR_NO_SD() {
		return ERROR_NO_SD;
	}

	public void setERROR_NO_SD(String eRROR_NO_SD) {
		ERROR_NO_SD = eRROR_NO_SD;
	}

	public String getSELECT_PHOTO() {
		return SELECT_PHOTO;
	}

	public void setSELECT_PHOTO(String sELECT_PHOTO) {
		SELECT_PHOTO = sELECT_PHOTO;
	}

	public String getSELECT_FROM_ALBUM() {
		return SELECT_FROM_ALBUM;
	}

	public void setSELECT_FROM_ALBUM(String sELECT_FROM_ALBUM) {
		SELECT_FROM_ALBUM = sELECT_FROM_ALBUM;
	}

	public String getSELECT_FROM_CAMERA() {
		return SELECT_FROM_CAMERA;
	}

	public void setSELECT_FROM_CAMERA(String sELECT_FROM_CAMERA) {
		SELECT_FROM_CAMERA = sELECT_FROM_CAMERA;
	}

	public String getCaptureImagePath() {
		return captureImagePath;
	}
}
