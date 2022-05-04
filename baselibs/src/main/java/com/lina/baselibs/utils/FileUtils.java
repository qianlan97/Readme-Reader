package com.lina.baselibs.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class FileUtils {

    private static final String TAG = "FileUtils";
    public static final String DOCUMENTS_DIR = "documents";
    /**
     * 根据路径创建文件
     *
     * @param filePath 文件路径
     */
    public static File createFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            Log.d(TAG, "createFile filePath empty");
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                if (file.createNewFile()) {
                    Log.d(TAG, "createFile success, filePath = " + filePath);
                    return file;
                } else {
                    Log.e(TAG, "createFile failed, filePath = " + filePath);
                    return null;
                }
            } catch (IOException e) {
                Log.e(TAG, "createFile " + filePath + " IOException : e = " + e.toString());
                return null;
            }
        }
        return file;
    }

    /**
     * 根据路径创建文件夹
     *
     * @param path 文件夹路径
     */
    public static void createPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    /**
     * 重命名文件
     *
     * @param oldPath
     * @param newPath
     */
    public static boolean renameFile(String oldPath, String newPath) {
        if (TextUtils.isEmpty(oldPath)) {
            return false;
        }
        if (TextUtils.isEmpty(newPath)) {
            return false;
        }
        return renameFile(new File(oldPath), new File(newPath));
    }

    /**
     * 重命名文件
     *
     * @param oldFile
     * @param newFile
     */
    public static boolean renameFile(File oldFile, File newFile) {
        if (oldFile == null) {
            return false;
        }
        if (newFile == null) {
            return false;
        }
        return oldFile.renameTo(newFile);
    }

    /**
     * 根据文件链接获取文件名
     *
     * @param url 文件链接
     * @return
     */
    public static String getFileName(String url) {
        if (TextUtils.isEmpty(url)) {
            return System.currentTimeMillis() + "";
        }
        String[] tmp = url.split("/");
        return tmp[tmp.length - 1];
    }

    public static Vector<String> getFileNames(String fileAbsolutePath) {
        Vector<String> vecFile = new Vector<String>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();
        if(null != subFile){
            for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
                // 判断是否为文件夹
                if (!subFile[iFileLength].isDirectory()) {
                    String filename = subFile[iFileLength].getName();
                    vecFile.add(filename);
                }
            }
        }
        return vecFile;
    }

    /**
     * 读取文件内信息
     *
     * @param path 文件路径
     * @return
     */
    public static String readFileContent(String path) {
        return readFileContent(new File(path));
    }

    /**
     * 读取文件内信息
     *
     * @param path 文件路径
     * @param type 编码方式
     * @return
     */
    public static String readFileContent(String path, String type) {
        return readFileContent(new File(path), type);
    }

    /**
     * 读取文件内信息
     *
     * @param file 文件
     * @return
     */
    public static String readFileContent(File file) {
        return readFileContent(file, "UTF-8");
    }

    /**
     * 读取文件内信息
     *
     * @param file 文件
     * @param type 编码方式
     * @return
     */
    public static String readFileContent(File file, String type) {
        BufferedReader br = null;
        try {
            if (!file.exists()) {
                Log.e(TAG, "readFileContent : file is not exist, path = " + file.getAbsolutePath());
                return null;
            }
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), type));
            String content = "";
            String lineTxt;
            while ((lineTxt = br.readLine()) != null) {
                content += lineTxt + "\n";
            }
            return content.trim();
        } catch (IOException e) {
            Log.e(TAG, "readFileContent IOException : e = " + e.toString());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Log.e(TAG, "readFileContent close br IOException : e = " + e.toString());
                }
            }
        }
        return null;
    }

    /**
     * 获取文件夹大小
     *
     * @param path
     * @return long
     */
    public static long getFolderSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        return getFolderSize(new File(path));
    }


    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件（不包含过滤器中的文件类型）
     */
    public static boolean deleteDir(String path, FilenameFilter filter) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        return deleteDir(new File(path), filter);
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件（不包含过滤器中的文件类型）
     */
    public static boolean deleteDir(File file, FilenameFilter filter) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            String[] children = file.list(filter);
            if (children == null) {
                return true;
            }
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(file, children[i]), filter);
                if (!success) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public static boolean deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return true;
        }
        return deleteFile(new File(filePath));
    }

    /**
     * 删除文件
     *
     * @param file 文件对象
     */
    public static boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        return file.delete();
    }

    /**
     * 获取输入流编码方式
     *
     * @param is
     */
    public static void getStreamEncodeType(InputStream is) {
        BufferedReader reader = null;
        String text = "";
        try {
            BufferedInputStream in = new BufferedInputStream(is);
            in.mark(4);
            byte[] first3bytes = new byte[3];
            in.read(first3bytes);
            in.reset();
            if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB && first3bytes[2] == (byte) 0xBF) {
                Log.e(TAG, "utf-8");
                reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            } else if (first3bytes[0] == (byte) 0xFF && first3bytes[1] == (byte) 0xFE) {
                Log.e(TAG, "unicode");
                reader = new BufferedReader(new InputStreamReader(in, "unicode"));
            } else if (first3bytes[0] == (byte) 0xFE && first3bytes[1] == (byte) 0xFF) {
                Log.e(TAG, "utf-16be");
                reader = new BufferedReader(new InputStreamReader(in, "utf-16be"));
            } else if (first3bytes[0] == (byte) 0xFF && first3bytes[1] == (byte) 0xFF) {
                Log.e(TAG, "utf-16le");
                reader = new BufferedReader(new InputStreamReader(in, "utf-16le"));
            } else {
                Log.e(TAG, "GBK");
                reader = new BufferedReader(new InputStreamReader(in, "GBK"));
            }
            String str = reader.readLine();

            while (str != null) {
                text = text + str + "/n";
                str = reader.readLine();
            }
            Log.e(TAG, "txt = " + text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从assert文件夹中读取省市区的json文件
     */
    public static String readText(Context context, String assetPath) {
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open(assetPath)));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                sb.append(line);
            }
            bufReader.close();
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isExist(String path) {
        File _file = new File(path);
        return _file != null && _file.exists();
    }

    /**
     * 对外接口  获取uri对应的路径
     *
     * @param uri
     * @return
     */
    public static String getChooseFileResultPath(Context context, Uri uri) {
        String chooseFilePath = null;
        if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
            chooseFilePath = uri.getPath();
            return chooseFilePath;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
            chooseFilePath = getPath(context, uri);
        }
        return chooseFilePath;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    private static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];

                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                String id = DocumentsContract.getDocumentId(uri);
                if (id.startsWith("raw:")) {
                    final String path = id.replaceFirst("raw:", "");
                    return path;
                }

                // path could not be retrieved using ContentResolver, therefore copy file to accessible cache using streams
                String fileName = getFileName(context, uri);
                File cacheDir = getDocumentCacheDir(context);
                File file = generateFileName(fileName, cacheDir);
                String destinationPath = null;
                if (file != null) {
                    destinationPath = file.getAbsolutePath();
                    saveFileFromUri(context, uri, destinationPath);
                }

                return destinationPath;

                // MediaProvider
            }else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);

            }

        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);

        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            uri.getPath();

        }
        return null;
    }

    public static String getFileName(@NonNull Context context, Uri uri) {
        String mimeType = context.getContentResolver().getType(uri);
        String filename = null;

        if (mimeType == null && context != null) {
            String path = getPath(context, uri);
            if (path == null) {
                filename = getName(uri.toString());
            } else {
                File file = new File(path);
                filename = file.getName();
            }
        } else {
            Cursor returnCursor = context.getContentResolver().query(uri, null,
                    null, null, null);
            if (returnCursor != null) {
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                filename = returnCursor.getString(nameIndex);
                returnCursor.close();
            }
        }

        return filename;
    }
    public static String getName(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.lastIndexOf('/');
        return filename.substring(index + 1);
    }
    private static void saveFileFromUri(Context context, Uri uri, String destinationPath) {
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            bos = new BufferedOutputStream(new FileOutputStream(destinationPath, false));
            byte[] buf = new byte[1024];
            is.read(buf);
            do {
                bos.write(buf);
            } while (is.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File generateFileName(@Nullable String name, File directory) {
        if (name == null) {
            return null;
        }

        File file = new File(directory, name);

        if (file.exists()) {
            String fileName = name;
            String extension = "";
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex > 0) {
                fileName = name.substring(0, dotIndex);
                extension = name.substring(dotIndex);
            }

            int index = 0;

            while (file.exists()) {
                index++;
                name = fileName + '(' + index + ')' + extension;
                file = new File(directory, name);
            }
        }

        try {
            if (!file.createNewFile()) {
                return null;
            }
        } catch (IOException e) {
            Log.w(TAG, e);
            return null;
        }

        logDir(directory);

        return file;
    }
    private static void logDir(File dir) {
        Log.d(TAG, "Dir=" + dir);
        File[] files = dir.listFiles();
        for (File file : files) {
            Log.d(TAG, "File=" + file.getPath());
        }
    }
    public static File getDocumentCacheDir(@NonNull Context context) {
        File dir = new File(context.getCacheDir(), DOCUMENTS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        logDir(context.getCacheDir());
        logDir(dir);

        return dir;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
