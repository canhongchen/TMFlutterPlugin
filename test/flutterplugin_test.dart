import 'package:flutter_test/flutter_test.dart';
import 'package:flutterplugin/flutterplugin.dart';
import 'package:flutterplugin/flutterplugin_platform_interface.dart';
import 'package:flutterplugin/flutterplugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterpluginPlatform
    with MockPlatformInterfaceMixin
    implements FlutterpluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterpluginPlatform initialPlatform = FlutterpluginPlatform.instance;

  test('$MethodChannelFlutterplugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterplugin>());
  });

  test('getPlatformVersion', () async {
    Flutterplugin flutterpluginPlugin = Flutterplugin();
    MockFlutterpluginPlatform fakePlatform = MockFlutterpluginPlatform();
    FlutterpluginPlatform.instance = fakePlatform;

    expect(await flutterpluginPlugin.getPlatformVersion(), '42');
  });
}
