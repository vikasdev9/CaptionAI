package com.example.captionai.database.di;

import com.example.captionai.database.CaptionAIDatabase;
import com.example.captionai.database.dao.SavedContentDao;
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
public final class DatabaseModule_ProvideSavedContentDaoFactory implements Factory<SavedContentDao> {
  private final Provider<CaptionAIDatabase> dbProvider;

  public DatabaseModule_ProvideSavedContentDaoFactory(Provider<CaptionAIDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SavedContentDao get() {
    return provideSavedContentDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSavedContentDaoFactory create(
      javax.inject.Provider<CaptionAIDatabase> dbProvider) {
    return new DatabaseModule_ProvideSavedContentDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideSavedContentDaoFactory create(
      Provider<CaptionAIDatabase> dbProvider) {
    return new DatabaseModule_ProvideSavedContentDaoFactory(dbProvider);
  }

  public static SavedContentDao provideSavedContentDao(CaptionAIDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSavedContentDao(db));
  }
}
