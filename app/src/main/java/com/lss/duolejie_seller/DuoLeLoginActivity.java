package com.lss.duolejie_seller;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lss.duolejie_seller.base.BaseActivity;

/**
 * 入驻页
 *
 * @author lss
 */
public class DuoLeLoginActivity extends BaseActivity {
    public TextView tv_ruzhu, tv_ruzhuxuzhi;
    public TextView tv_denglu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_duolelogin);
        initView();
    }

    private void initView() {
        tv_ruzhu = (TextView) findViewById(R.id.tv_ruzhu);
        tv_ruzhuxuzhi = (TextView) findViewById(R.id.tv_ruzhuxuzhi);
        tv_denglu = (TextView) findViewById(R.id.tv_denglu);
        setListener(tv_ruzhu, tv_ruzhuxuzhi, tv_denglu);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_ruzhu://
                if (Build.VERSION.SDK_INT >= 23) {
                    requestPermission();
                } else {
                    setIntentRuZhu();
                }
                break;
            case R.id.tv_ruzhuxuzhi://
                Intent itdl = new Intent();
                itdl.setClass(DuoLeLoginActivity.this, DuoLeRuZhuActivity.class);
                startActivity(itdl);
                break;
            case R.id.tv_denglu://
                Intent itdlz = new Intent();
                itdlz.setClass(DuoLeLoginActivity.this, B5_1_LoginActivity.class);
                startActivity(itdlz);
                break;
            default:
                break;
        }
        super.onClick(v);
    }

    public void setIntentRuZhu() {
        Intent itdla = new Intent();
        itdla.setClass(DuoLeLoginActivity.this, B_1_1_Edit.class);
        startActivity(itdla);
    }

    public void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                    },
                    1);
        } else {
            setIntentRuZhu();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被用户同意,做相应的事情
                setIntentRuZhu();
            } else {
                //权限被用户拒绝，做相应事情
                Toast.makeText(this,"请手动打开定位权限",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
