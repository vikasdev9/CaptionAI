package com.example.captionai.feature.hashtag;

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
public final class HashtagViewModel_Factory implements Factory<HashtagViewModel> {
  private final Provider<CaptionAIRepository> repositoryProvider;

  public HashtagViewModel_Factory(Provider<CaptionAIRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public HashtagViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static HashtagViewModel_Factory create(
      javax.inject.Provider<CaptionAIRepository> repositoryProvider) {
    return new HashtagViewModel_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static HashtagViewModel_Factory create(Provider<CaptionAIRepository> repositoryProvider) {
    return new HashtagViewModel_Factory(repositoryProvider);
  }

  public static HashtagViewModel newInstance(CaptionAIRepository repository) {
    return new HashtagViewModel(repository);
  }
}
