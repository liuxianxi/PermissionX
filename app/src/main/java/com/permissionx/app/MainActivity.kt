package com.permissionx.app


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.permissionx.liuxianxidev.PermissionX
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeCallBtn.setOnClickListener {
            //只需要调用PermissionX的request()方法，传入当前的Activity和
            //要申请的权限名，然后在Lambda表达式中处理权限的申请结果就可以了。如果allGranted
            //等于true，就说明所有申请的权限都被用户授权了，那么就执行拨打电话操作，否则使用
            //Toast弹出一条失败提示
            PermissionX.request(this,
                android.Manifest.permission.CALL_PHONE) { allGranted, deniedList ->
                if (allGranted) {
                    call()
                }else {
                    Toast.makeText(this, "You denied $deniedList",
                    Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel: 10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}