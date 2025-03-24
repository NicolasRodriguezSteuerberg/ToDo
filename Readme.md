# ToDo

## CheckList
### Backend
- [ ] Autorización y autenticación: JWT login + register + correo para inicio/registro de sesión

> [!WARNING] Posiblemente no hiciera falta los roles + permissions
- [ ] CRUD tareas
  - [x] Recoger todas las tareas
  - [ ] Recoger todas las tareas filtradas por completado o no completo
  - [ ] Recoger todas las tareas filtradas por fecha de creación
  - [x] Agregar una tarea
  - [ ] Modificar una tarea identificada por id
  - [x] Eliminar una tarea identificada por id

### Frontend
- [x] Página de inicio de sesión/registro
  - [ ] Modificarlo para que haga llamadas a la api
- [ ] Página para mirar, eliminar y modificar tareas
  - [x] Visualización de tareas
  - [ ] Modificar la visualización de tareas por las tareas del backend
  - [ ] Eliminar tareas
  - [ ] Modificar tareas


## Tecnologías
### Backend
- Spring framework
- PostgreSQL
- Redis -> guardar token de refresco + código de verificación de correo

### Frontend
- React TS


## ENDPOINTS
- /auth
  - /login
  - /register
  - /refresh
  - /confirm?id=x -> codigo de verificación de correo
- /tasks
  - /add  -> POST
  - /update/{id}  -> UPDATE
  - delete/{id}  -> DELETE
  - /all  -> GET
  - /all?orderBy=x  -> GET