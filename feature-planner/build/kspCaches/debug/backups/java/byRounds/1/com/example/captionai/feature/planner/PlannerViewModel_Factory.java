package com.example.captionai.feature.planner;

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
public final class PlannerViewModel_Factory implements Factory<PlannerViewModel> {
  private final Provider<CaptionAIRepository> repositoryProvider;

  public PlannerViewModel_Factory(Provider<CaptionAIRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PlannerViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static PlannerViewModel_Factory create(
      javax.inject.Provider<CaptionAIRepository> repositoryProvider) {
    return new PlannerViewModel_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static PlannerViewModel_Factory create(Provider<CaptionAIRepository> repositoryProvider) {
    return new PlannerViewModel_Factory(repositoryProvider);
  }

  public static PlannerViewModel newInstance(CaptionAIRepository repository) {
    return new PlannerViewModel(repository);
  }
}
