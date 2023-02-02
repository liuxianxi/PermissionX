package com.permissionx.liuxianxidev


import androidx.fragment.app.FragmentActivity

//完成了InvisibleFragment的编写，接下来我们需要开始编写对外接口部分的代码了。

//将PermissionX指定成单例类，是为了让PermissionX中的接口能够更加方便地被调用。
object PermissionX {

    private const val TAG = "InvisibleFragment"

    //FragmentActivity是AppCompatActivity的父类
    fun request(activity: FragmentActivity, vararg permissions: String,
                callback: PermissionCallback) {
        //首先获取FragmentManager的实例
        val fragmentManager = activity.supportFragmentManager
        //然后调用findFragmentByTag()方法来判断传入的Activity参数中是否已经包含了指定TAG的
        //Fragment，也就是我们刚才编写的InvisibleFragment。
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment //。如果已经包含则直接使用该Fragment，
        } else {
            //否则就创建一个新的InvisibleFragment实例，并将它添加到Activity中，同时
            //指定一个TAG。注意，在添加结束后一定要调用commitNow()方法，而不能调用commit()方
            //法，因为commit()方法并不会立即执行添加操作，因而无法保证下一行代码执行时
            //InvisibleFragment已经被添加到Activity中了。
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        //有了InvisibleFragment的实例之后，接下来我们只需要调用它的requestNow()方法就能去
        //申请运行时权限了，申请结果会自动回调到callback参数中。
        fragment.requestNow(callback, *permissions) //在permissions参数的前面加上了一个*，这个符号并不是指针的意思，而是表示将一个
        //数组转换成可变长度参数传递过去。
    }
}