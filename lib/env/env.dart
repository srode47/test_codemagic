import 'package:codemagic/env/dev_env.dart';

abstract class Env {
  static late final EnvFields _instance;

  static void init([EnvFields? instance]) {
    _instance = instance ?? DevEnv() as EnvFields;
  }

  static String? get openAIAPIKey => _instance.openAIAPIKey;

  static String? get instabugApiKey => _instance.instabugApiKey;

  static String? get apiBaseUrl => _instance.apiBaseUrl;

  static String? get webAppUrl => _instance.webAppUrl;

}

abstract class EnvFields {
  String? get openAIAPIKey;

  String? get instabugApiKey;

  String? get apiBaseUrl;

  String? get webAppUrl;

}
