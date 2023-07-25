package com.thinmoo.flutterplugin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import com.intelligoo.sdk.LibDevModel;
import com.intelligoo.sdk.LibInterface;

import org.json.JSONObject;

import java.util.HashMap;

/** FlutterpluginPlugin */
public class FlutterpluginPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  private Context context;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "com.thinmoo.sdk/ble_plugin");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("openDoor")) {
//      result.success("Android " + android.os.Build.VERSION.RELEASE + " 哈哈哈了吗？");
//      LibDevModel.enableLog(false);
      openDoor(call, result);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  private void openDoor(MethodCall call, Result result){
    HashMap map = (HashMap) call.arguments;
    String devSn = map.get("devSn").toString();
    String devMac = map.get("devMac").toString();
    String devEkey = map.get("devEkey").toString();
    int keyType = (int)map.get("keyType");
    Log.e("ttttttt", "keyType:" + keyType);
    LibDevModel libDevModel = new LibDevModel();
    libDevModel.devSn = devSn;
    libDevModel.devMac = devMac;
    libDevModel.eKey = devEkey;
    libDevModel.keyType = keyType;
    libDevModel.devType = 10;
    int ret = LibDevModel.openDoor(context, libDevModel, new LibInterface.ManagerCallback() {
      @Override
      public void setResult(int i, Bundle bundle) {
        if (result != null){
          if (i == 0){
            result.success(getRetJson(i, "opendoor success", null).toString());
          }else{
            result.success(getRetJson(i, "opendoor fail", null).toString());
          }
        }
      }
    });
    if (ret != 0){
      if (result != null){
        result.success(getRetJson(ret, "opendoor fail", null).toString());
      }
    }
  }

  private JSONObject getRetJson(int ret, String msg, JSONObject data){
    JSONObject jsonObject = new JSONObject();
    try{
      jsonObject.put("code", ret);
      jsonObject.put("msg", msg);
      if (data == null){
        data = new JSONObject();
      }
      jsonObject.put("data", data);
    }catch (Exception e){
      e.printStackTrace();
    }
    return jsonObject;
  }
}
