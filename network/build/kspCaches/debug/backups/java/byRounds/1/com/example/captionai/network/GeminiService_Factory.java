package com.example.captionai.network;

import com.google.ai.client.generativeai.GenerativeModel;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class GeminiService_Factory implements Factory<GeminiService> {
  private final Provider<GenerativeModel> generativeModelProvider;

  public GeminiService_Factory(Provider<GenerativeModel> generativeModelProvider) {
    this.generativeModelProvider = generativeModelProvider;
  }

  @Override
  public GeminiService get() {
    return newInstance(generativeModelProvider.get());
  }

  public static GeminiService_Factory create(
      javax.inject.Provider<GenerativeModel> generativeModelProvider) {
    return new GeminiService_Factory(Providers.asDaggerProvider(generativeModelProvider));
  }

  public static GeminiService_Factory create(Provider<GenerativeModel> generativeModelProvider) {
    return new GeminiService_Factory(generativeModelProvider);
  }

  public static GeminiService newInstance(GenerativeModel generativeModel) {
    return new GeminiService(generativeModel);
  }
}
