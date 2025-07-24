# 🚀 ms-cv-identity-doc 
**Microservicio de Computer Vision para documentos de identidad peruanos**  
*Detección y generación de DNI, pasaportes y CE usando modelos HAAR/OpenCV*

[![OpenAPI Validator](https://img.shields.io/badge/OpenAPI-3.0.3-6BA539?logo=openapi-initiative)](https://editor.swagger.io/?url=https://raw.githubusercontent.com/your-repo/openapi.yaml)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=spring)](https://spring.io/projects/spring-boot)

## 📌 Características principales
- **Detección de documentos** (DNI, pasaporte, CE) en imágenes/PDF
- **Generación de datasets sintéticos** para entrenamiento
- **Validación de formatos** específicos para Perú
- **HAAR Cascade models** para procesamiento eficiente

## 🔍 Documentación relevante
| Documento | Enlace | Estado |
|-----------|--------|--------|
| DNI Perú | [Tipos de DNI](https://oficinasreniec.pe/tipos-de-dni/) | ⚠️ Bajo investigación |
| Pasaporte Perú | [Tipos de pasaporte](https://www.gob.pe/161-tipos-de-pasaporte) | ❌ No disponible aún |
| CE (Extranjería) | [Documentos válidos](https://dgrs.unmsm.edu.pe/2023/05/15/cuales-son-los-documentos-validos-para-personas-extranjeras-que-residen-en-el-peru/) | ✅ Confirmado |

# Compilar
mvn clean install

# Ejecutar (puerto 9091 por defecto)
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=9091

# Alternativa con jar
java -jar target/ms-cv-identity-doc-1.0.0.jar --server.port=9091

