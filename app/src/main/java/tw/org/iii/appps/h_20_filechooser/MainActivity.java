package tw.org.iii.appps.h_20_filechooser;
//目的:用別人寫的檔案上傳管理器
//1.android file chooser github: https://github.com/MostafaNasiri/AndroidFileChooser
//2.加上implementation 'ir.sohreco.androidfilechooser:android-file-chooser:1.3'
//3.權限設置READ_EXTERNAL_STORAGE,及詢問
//4.設置按鈕觸發檔案選擇器
//5.把howto user複製貼上
//6.實作chooserListener,叫出實作方法
//7.FileChooser :extends Fragment,所以你必須做Fragment來玩他,開始弄出Fragment

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ir.sohreco.androidfilechooser.ExternalStorageNotAvailableException;
import ir.sohreco.androidfilechooser.FileChooser;

public class MainActivity extends AppCompatActivity implements FileChooser.ChooserListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.讀取權限詢問
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                   123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void text1(View view) {
        //1.第一段拿到builder物件
        //Builder(ChooserType chooserType, ChooserListener chooserListener)
        FileChooser.Builder builder = new FileChooser.Builder(FileChooser.ChooserType.FILE_CHOOSER, this)
                .setMultipleFileSelectionEnabled(true) //灰色一塊,默認多重選擇器送出按鈕(開/關)
                .setFileFormats(new String[] {".jpg", ".png",".pdf"})//設定讀取的檔案種類
                .setListItemsTextColor(R.color.colorPrimary)
                .setPreviousDirectoryButtonIcon(R.drawable.ic_prev_dir)
                .setDirectoryIcon(R.drawable.ic_directory)
                .setFileIcon(R.drawable.ic_file);

        //2.用build拿到FileChooser,他其實是繼承Fragment,所以你藥用Fragment來玩他
        try {

          //3.FileChooser :extends Fragment,所以你必須做Fragment來玩他
            FileChooser fileChooserFragment = builder.build();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,fileChooserFragment)
                    .commit();
            Log.v("brad","fileChooserFragment成功");
        } catch (ExternalStorageNotAvailableException e) {
            Log.v("bard","FileChooser出錯:" + e.toString());
        }
    }

    @Override
    public void onSelect(String path) {
        Log.v("brad","點選到項目" + path);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
