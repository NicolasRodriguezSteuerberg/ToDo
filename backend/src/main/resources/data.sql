
insert into permission(id, name) values(1, "READ");
insert into permission(id, name) values(2, "WRITE");
insert into permission(id, name) values(3, "UPDATE");
insert into permission(id, name) values(4, "DELETE");

insert into role (id, name) values (1, "USER");
insert into role (id, name) values (2, "ADMIN");

insert into role_permission(role_id, permission_id) values(1, 1);
insert into role_permission(role_id, permission_id) values(1, 2);
insert into role_permission(role_id, permission_id) values(1, 3);
insert into role_permission(role_id, permission_id) values(1, 4);

insert into role_permission(role_id, permission_id) values(2, 1);
insert into role_permission(role_id, permission_id) values(2, 2);
insert into role_permission(role_id, permission_id) values(2, 3);
insert into role_permission(role_id, permission_id) values(2, 4);

insert into users (id, username, email, password) values(1, "nico", "nico@gmail.com", "1234")
insert into user_role (user_id, role_id) values(1, 1)