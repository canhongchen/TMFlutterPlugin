
import 'flutterplugin_platform_interface.dart';

class Flutterplugin {
  Future<String?> openDoor(String devSn, String devMac, String devEkey, int keyType) {
    return FlutterpluginPlatform.instance.openDoor(devSn, devMac, devEkey, keyType);
  }
}
