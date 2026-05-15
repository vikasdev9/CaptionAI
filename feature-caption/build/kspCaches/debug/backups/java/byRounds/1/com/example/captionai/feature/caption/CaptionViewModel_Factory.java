package com.example.captionai.feature.caption;

import com.example.captionai.domain.repository.CaptionAIRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class CaptionViewModel_Factory implements Factory<CaptionViewModel> {
  private final Provider<CaptionAIRepository> repositoryProvider;

  public CaptionViewModel_Factory(Provider<CaptionAIRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CaptionViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static CaptionViewModel_Factory create(
      javax.inject.Provider<CaptionAIRepository> repositoryProvider) {
    return new CaptionViewModel_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static CaptionViewModel_Factory create(Provider<CaptionAIRepository> repositoryProvider) {
    return new CaptionViewModel_Factory(repositoryProvider);
  }

  public static CaptionViewModel newInstance(CaptionAIRepository repository) {
    return new CaptionViewModel(repository);
  }
}
