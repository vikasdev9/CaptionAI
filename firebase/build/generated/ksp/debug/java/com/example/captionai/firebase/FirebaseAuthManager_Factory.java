package com.example.captionai.firebase;

import com.google.firebase.auth.FirebaseAuth;
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
public final class FirebaseAuthManager_Factory implements Factory<FirebaseAuthManager> {
  private final Provider<FirebaseAuth> authProvider;

  public FirebaseAuthManager_Factory(Provider<FirebaseAuth> authProvider) {
    this.authProvider = authProvider;
  }

  @Override
  public FirebaseAuthManager get() {
    return newInstance(authProvider.get());
  }

  public static FirebaseAuthManager_Factory create(
      javax.inject.Provider<FirebaseAuth> authProvider) {
    return new FirebaseAuthManager_Factory(Providers.asDaggerProvider(authProvider));
  }

  public static FirebaseAuthManager_Factory create(Provider<FirebaseAuth> authProvider) {
    return new FirebaseAuthManager_Factory(authProvider);
  }

  public static FirebaseAuthManager newInstance(FirebaseAuth auth) {
    return new FirebaseAuthManager(auth);
  }
}
