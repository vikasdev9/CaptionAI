package com.example.captionai.feature.bio;

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
public final class BioViewModel_Factory implements Factory<BioViewModel> {
  private final Provider<CaptionAIRepository> repositoryProvider;

  public BioViewModel_Factory(Provider<CaptionAIRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public BioViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static BioViewModel_Factory create(
      javax.inject.Provider<CaptionAIRepository> repositoryProvider) {
    return new BioViewModel_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static BioViewModel_Factory create(Provider<CaptionAIRepository> repositoryProvider) {
    return new BioViewModel_Factory(repositoryProvider);
  }

  public static BioViewModel newInstance(CaptionAIRepository repository) {
    return new BioViewModel(repository);
  }
}
