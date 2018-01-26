create table room (id  bigserial not null, name varchar(255), hotel_id int8, primary key (id));
alter table room add constraint FKdosq3ww4h9m2osim6o0lugng8 foreign key (hotel_id) references hotel;