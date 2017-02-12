# Instruction-based drawings

[drawings.cfapps.io](https://drawings.cfapps.io)

```kotlin
StartBy()
  .adding().rectangles().inAGridOf(2, 3).withACollapsedMarginOf(20.0)
  .then()
  .duplicate().all()
  .then()
  .shave().all().randomly()
  .then()
  .colorise().all().from(RandomPalette())
```

