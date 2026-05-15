package com.example.captionai.data.repository;

import com.example.captionai.database.dao.PlannerDao;
import com.example.captionai.database.dao.SavedContentDao;
import com.example.captionai.network.GeminiService;
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
public final class CaptionAIRepositoryImpl_Factory implements Factory<CaptionAIRepositoryImpl> {
  private final Provider<GeminiService> geminiServiceProvider;

  private final Provider<SavedContentDao> savedContentDaoProvider;

  private final Provider<PlannerDao> plannerDaoProvider;

  public CaptionAIRepositoryImpl_Factory(Provider<GeminiService> geminiServiceProvider,
      Provider<SavedContentDao> savedContentDaoProvider, Provider<PlannerDao> plannerDaoProvider) {
    this.geminiServiceProvider = geminiServiceProvider;
    this.savedContentDaoProvider = savedContentDaoProvider;
    this.plannerDaoProvider = plannerDaoProvider;
  }

  @Override
  public CaptionAIRepositoryImpl get() {
    return newInstance(geminiServiceProvider.get(), savedContentDaoProvider.get(), plannerDaoProvider.get());
  }

  public static CaptionAIRepositoryImpl_Factory create(
      javax.inject.Provider<GeminiService> geminiServiceProvider,
      javax.inject.Provider<SavedContentDao> savedContentDaoProvider,
      javax.inject.Provider<PlannerDao> plannerDaoProvider) {
    return new CaptionAIRepositoryImpl_Factory(Providers.asDaggerProvider(geminiServiceProvider), Providers.asDaggerProvider(savedContentDaoProvider), Providers.asDaggerProvider(plannerDaoProvider));
  }

  public static CaptionAIRepositoryImpl_Factory create(
      Provider<GeminiService> geminiServiceProvider,
      Provider<SavedContentDao> savedContentDaoProvider, Provider<PlannerDao> plannerDaoProvider) {
    return new CaptionAIRepositoryImpl_Factory(geminiServiceProvider, savedContentDaoProvider, plannerDaoProvider);
  }

  public static CaptionAIRepositoryImpl newInstance(GeminiService geminiService,
      SavedContentDao savedContentDao, PlannerDao plannerDao) {
    return new CaptionAIRepositoryImpl(geminiService, savedContentDao, plannerDao);
  }
}
