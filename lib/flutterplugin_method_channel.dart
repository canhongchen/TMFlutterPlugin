import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutterplugin_platform_interface.dart';

/// An implementation of [FlutterpluginPlatform] that uses method channels.
class MethodChannelFlutterplugin extends FlutterpluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('com.thinmoo.sdk/ble_plugin');

  @override
  Future<String?> openDoor(String devSn, String devMac, String devEkey, int keyType) async {
    final version = await methodChannel.invokeMethod('openDoor', {"devSn": devSn, "devMac":devMac, "devEkey":devEkey, "keyType":keyType});
    return version;
  }
}
