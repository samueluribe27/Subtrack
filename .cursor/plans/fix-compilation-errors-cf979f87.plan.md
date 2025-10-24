<!-- cf979f87-b0cf-4999-936d-762255b229b1 11d71029-c867-4a59-8908-27ac9994f2f3 -->
# Plan: Solucionar Errores de Compilación

## Problemas Identificados

1. Room necesita `kapt` (Kotlin Annotation Processing Tool) en lugar de `annotationProcessor`
2. ViewModels con constructores parametrizados necesitan ViewModelFactory
3. NotificationWorker y otros componentes necesitan acceso correcto a la base de datos

## Solución Propuesta

Usar **ViewModelFactory** con acceso a repositorios desde `SubTrackApplication`. Esta es la solución más limpia sin agregar complejidad innecesaria de inyección de dependencias.

## Pasos de Implementación

### 1. Configurar Room correctamente en build.gradle.kts

**Archivo:** `app/build.gradle.kts`

- Agregar plugin `kotlin-kapt` al inicio del archivo
- Cambiar `annotationProcessor` por `kapt` para Room
- Agregar dependencia `kapt("androidx.room:room-compiler:2.6.1")`

### 2. Crear ViewModelFactory genérico

**Archivo nuevo:** `app/src/main/java/com/example/subtrack/ui/ViewModelFactory.kt`

Crear factory que reciba repositorios y cree ViewModels con parámetros:

```kotlin
class ViewModelFactory(
    private val subscriptionRepository: SubscriptionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory
```

### 3. Refactorizar ViewModels para eliminar @Inject

**Archivos a modificar:**

- `DashboardViewModel.kt` - Remover `@Inject`, usar constructor simple
- `SubscriptionsViewModel.kt` - Remover `@Inject`, usar constructor simple  
- `CreateSubscriptionViewModel.kt` - Remover `@Inject`, usar constructor simple
- `AnalysisViewModel.kt` - Remover `@Inject`, usar constructor simple

Cambiar de:

```kotlin
class DashboardViewModel @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel()
```

A:

```kotlin
class DashboardViewModel(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel()
```

### 4. Actualizar Fragmentos para usar ViewModelFactory

**Archivos a modificar:**

- `DashboardFragment.kt`
- `SubscriptionsFragment.kt`
- `CreateSubscriptionFragment.kt`
- `AnalysisFragment.kt`

Cambiar de:

```kotlin
viewModel = ViewModelProvider(this)[ViewModel::class.java]
```

A:

```kotlin
val app = requireActivity().application as SubTrackApplication
val factory = ViewModelFactory(app.subscriptionRepository, app.categoryRepository)
viewModel = ViewModelProvider(this, factory)[ViewModel::class.java]
```

### 5. Refactorizar Repositorios

**Archivos a modificar:**

- `SubscriptionRepository.kt` - Remover anotaciones `@Inject` y `@Singleton`
- `CategoryRepository.kt` - Remover anotaciones `@Inject` y `@Singleton`

### 6. Completar SubTrackApplication

**Archivo:** `app/src/main/java/com/example/subtrack/SubTrackApplication.kt`

- Implementar `initializeDefaultData()` con coroutines para insertar categorías por defecto

### 7. Corregir NotificationWorker

**Archivo:** `app/src/main/java/com/example/subtrack/notification/NotificationWorker.kt`

- El código ya está correcto, solo necesita que Room compile correctamente

## Resultado Esperado

- Compilación exitosa sin errores
- ViewModels funcionando con datos reales
- Room configurado correctamente con kapt
- Arquitectura limpia y mantenible sin dependencias innecesarias
- Aplicación completamente funcional

### To-dos

- [ ] Configurar Room con kapt en build.gradle.kts
- [ ] Crear ViewModelFactory genérico
- [ ] Refactorizar ViewModels eliminando @Inject
- [ ] Actualizar Fragmentos para usar ViewModelFactory
- [ ] Refactorizar Repositorios eliminando anotaciones DI
- [ ] Completar SubTrackApplication con inicialización de datos
- [ ] Verificar compilación y funcionamiento