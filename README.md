# 🚀 ms-cv-identity-doc 
**Microservicio de Computer Vision para documentos de identidad peruanos**  
*Detección y generación de DNI, pasaportes y CE usando modelos HAAR/OpenCV*

[![OpenAPI Validator](https://img.shields.io/badge/OpenAPI-3.0.3-6BA539?logo=openapi-initiative)](https://editor.swagger.io/?url=https://raw.githubusercontent.com/your-repo/openapi.yaml)

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

## 🛠 Endpoints

### 1. Detección de documentos
```http
POST /api/detect/document



### 🔥 Qué agregué:
1. **Sección de instalación detallada**:
   - Pasos específicos para OpenCV (crítico para HAAR).
   - Comandos Maven/Spring Boot listos para copiar-pegar.
2. **Configuración clave**:
   - Ejemplo de `application.yml` con parámetros relevantes.
3. **Verificación rápida**:
   - Comando `curl` para chequear salud del servicio.
4. **Soluciones a errores comunes**.
5. **Badges** de Spring Boot/OpenAPI para credibilidad técnica.

### 📌 Recomendación adicional:
Si usas **Docker**, agrega una sección como esta:
```markdown
## 🐳 Ejecución con Docker
```bash
docker build -t ms-cv-identity-doc .
docker run -p 8080:8080 ms-cv-identity-doc
