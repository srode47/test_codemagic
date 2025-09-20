import 'package:envied/envied.dart';

import 'env.dart';

part 'dev_env.g.dart';

@Envied(allowOptionalFields: true, path: '.dev.env')
final class DevEnv implements EnvFields {
  DevEnv();

  @override
  @EnviedField(varName: 'OPENAI_API_KEY', obfuscate: true)
  final String? openAIAPIKey = _DevEnv.openAIAPIKey;

  @override
  @EnviedField(varName: 'INSTABUG_API_KEY', obfuscate: true)
  final String? instabugApiKey = _DevEnv.instabugApiKey;

  @override
  @EnviedField(varName: 'API_BASE_URL', obfuscate: true)
  final String? apiBaseUrl = _DevEnv.apiBaseUrl;

  @override
  @EnviedField(varName: 'WEB_APP_URL', obfuscate: true)
  final String? webAppUrl = _DevEnv.webAppUrl;

}
