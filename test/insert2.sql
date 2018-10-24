
/* Delete Old Data */

delete from resource_properties;
delete from resources;
select setval ('resources_resource_id_seq', 1, false);
delete from properties;
select setval ('properties_property_id_seq', 1, false);
delete from offices;
select setval ('offices_office_id_seq', 1, false);
delete from categories;
select setval ('categories_category_id_seq', 1, false);
delete from users;


/* Insert Sample Data */

insert into users(id, password, last_name, first_name, user_level, phone, email) values ('u0000001', 'pa55w0rd', '仲町台', '太郎', 2, '0123456789', 'u0000001@example.com');
insert into users(id, password, last_name, first_name, user_level, phone, email) values ('u0000002', 'pa55w0rd', '都築', '花子', 1, '0987654321', 'u0000002@example.com');
insert into users(id, password, last_name, first_name, user_level, phone, email) values ('u0000003', 'pa55w0rd', 'Center', 'Minami', 1, '0123456789', 'u0000003@example.com');
insert into users(id, password, last_name, first_name, user_level, phone, email) values ('u0000004', 'pa55w0rd', 'Center', 'Kita', 0, '0987654321', 'u0000004@example.com');

insert into categories (category_name) values ('会議室');
insert into categories (category_name) values ('セミナールーム');
insert into categories (category_name) values ('UCS');
insert into categories (category_name) values ('プロジェクター');

insert into offices (office_name, office_location) values ('本社事業所', '神奈川県横浜市');
insert into offices (office_name, office_location) values ('北北海道事業所', '北海道北見市');
insert into offices (office_name, office_location) values ('南北海道事業所', '北海道札幌市');
insert into offices (office_name, office_location) values ('東北事業所', '秋田県秋田市');
insert into offices (office_name, office_location) values ('関東事業所', '神奈川県横浜市');
insert into offices (office_name, office_location) values ('中部事業所', '石川県金沢市');
insert into offices (office_name, office_location) values ('西日本事業所', '鳥取県鳥取市');
insert into offices (office_name, office_location) values ('九州事業所', '鹿児島県鹿児島市');

insert into properties (property_name) values ('ホワイトボード有');
insert into properties (property_name) values ('プロジェクター有');
insert into properties (property_name) values ('OAボード有');
insert into properties (property_name) values ('来客優先');
insert into properties (property_name) values ('UCS常設');
insert into properties (property_name) values ('TV会議システム');

insert into resources (resource_name, category_id, capacity, office_id, note, suspend_start, suspend_end, deleted) values ('仲町台101会議室', 1, 999, 1, '利用の際は事前に仲町台までご連絡ください。', to_timestamp('2018-12-01 00:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2018-12-31 23:00', 'YYYY-MM-DD HH24:MI'), 0);
insert into resources (resource_name, category_id, capacity, office_id, note, suspend_start, suspend_end, deleted) values ('仲町台102会議室', 1, 1, 1, null, null, null, 1);

insert into resource_properties (resource_id, property_id) values (1, 1);
