# ToDo

## CheckList
### Backend
- [ ] Autorización y autenticación: JWT login + register + correo para inicio/registro de sesión
- [ ] CRUD tareas
  - [ ] Recoger todas las tareas
  - [ ] Recoger todas las tareas filtradas por completado o no completo
  - [ ] Recoger todas las tareas filtradas por fecha de creación
  - [ ] Añadir una tarea
  - [ ] Modificar una tarea identificada por id
  - [ ] Eliminar una tarea identificada por id

### Frontend
- [ ] Página de inicio de sesión/registro
- [ ] Página para mirar, eliminar y modificar tareas


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