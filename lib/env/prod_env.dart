import 'package:envied/envied.dart';

import 'env.dart';

part 'prod_env.g.dart';

@Envied(allowOptionalFields: true, path: '.env')
final class ProdEnv implements EnvFields {
  ProdEnv();

  @override
  @EnviedField(varName: 'OPENAI_API_KEY', obfuscate: true)
  final String? openAIAPIKey = _ProdEnv.openAIAPIKey;

  @override
  @EnviedField(varName: 'INSTABUG_API_KEY', obfuscate: true)
  final String? instabugApiKey = _ProdEnv.instabugApiKey;

  @override
  @EnviedField(varName: 'API_BASE_URL', obfuscate: true)
  final String? apiBaseUrl = _ProdEnv.apiBaseUrl;

  @override
  @EnviedField(varName: 'WEB_APP_URL', obfuscate: true)
  final String? webAppUrl = _ProdEnv.webAppUrl;

}
