## Versioning and Regression testing

The library is not including any version of Camunda but defines it as a `provided` dependency.
This allows you to use the library with any of supported versions.

The library distinguishes between the version of Camunda it is using as a version during
the compilation and the runtime version used during regression testing.

Current compile version is 2.1.0

Regression test versions are:

<<<<<<< HEAD
| JDK \ CIB seven Version | 1.1.0 | 2.0.0 | 2.1.0 | 
|-------------------------|-------|-------|-------|
| JDK 17 (LTS)            | yes   | yes   | yes   |
| JDK 21 (LTS)            | yes   | yes   | yes   |
=======
| JDK \ Camunda Version | 7.20 | 7.21 | 7.22 | 7.23 | 
|-----------------------|------|------|------|------|
| JDK 17 (LTS)          | yes  | yes  | yes  | yes  |
| JDK 21 (LTS)          | yes  | yes  | yes  | yes  |
>>>>>>> e46ef430 (feat: #636 some cleanup regarding dependency management)
|                       |      |      |      |      |



