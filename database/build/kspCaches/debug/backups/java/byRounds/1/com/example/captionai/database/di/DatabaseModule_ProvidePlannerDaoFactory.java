package com.example.captionai.database.di;

import com.example.captionai.database.CaptionAIDatabase;
import com.example.captionai.database.dao.PlannerDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvidePlannerDaoFactory implements Factory<PlannerDao> {
  private final Provider<CaptionAIDatabase> dbProvider;

  public DatabaseModule_ProvidePlannerDaoFactory(Provider<CaptionAIDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public PlannerDao get() {
    return providePlannerDao(dbProvider.get());
  }

  public static DatabaseModule_ProvidePlannerDaoFactory create(
      javax.inject.Provider<CaptionAIDatabase> dbProvider) {
    return new DatabaseModule_ProvidePlannerDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvidePlannerDaoFactory create(
      Provider<CaptionAIDatabase> dbProvider) {
    return new DatabaseModule_ProvidePlannerDaoFactory(dbProvider);
  }

  public static PlannerDao providePlannerDao(CaptionAIDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePlannerDao(db));
  }
}
