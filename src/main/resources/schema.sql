create table device_upload_count
(
    id                int auto_increment
        primary key,
    device_imei       varchar(50)   not null,
    trace_count       int           not null,
    locator           int           not null comment '记录上一次最后上传的数据id，为下次一上传做索引',
    trace_num_history int default 0 not null,
    record_time       datetime      not null,
    constraint unique_device_imei
        unique (device_imei)
);

create table latest_geo_location_info
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    imei         varchar(50)  not null comment '机器人特殊标识',
    longitude    double       not null comment '经度',
    latitude     double       not null comment '纬度',
    province     varchar(50)  null comment '省份',
    city         varchar(50)  null comment '城市',
    district     varchar(50)  null comment '区/县',
    township     varchar(50)  null comment '乡/镇',
    full_address varchar(255) null comment '完整地址（如高德返回）',
    update_time  datetime     null,
    create_time  datetime     null,
    constraint imei
        unique (imei)
)
    comment '通过经纬度获取的地理位置信息';

create table seeding_robot_general_data
(
    id                int auto_increment
        primary key,
    device_id         varchar(50)          not null,
    device_iemi       varchar(50)          not null,
    device_type       varchar(10)          null,
    record_timestamp  datetime             null,
    organization_code varchar(10)          null,
    speed             decimal(10, 2)       null,
    seeding_area      decimal(10, 2)       null,
    created_at        datetime             null,
    on_duty           tinyint(1) default 0 not null
);

create table geo_location_info
(
    id          int      not null
        primary key,
    latitude    double   not null,
    longitude   double   not null,
    record_time datetime not null,
    constraint fk_geo_seeding
        foreign key (id) references seeding_robot_general_data (id)
);

create table seeding_robot_sensor_data
(
    id       int auto_increment
        primary key,
    robot_id int          not null,
    name     varchar(100) null,
    value    double       null,
    constraint seeding_robot_sensor_data_ibfk_1
        foreign key (robot_id) references seeding_robot_general_data (id)
            on delete cascade
);

create index robot_id
    on seeding_robot_sensor_data (robot_id);

create table seeding_robot_user_info
(
    id           int auto_increment
        primary key,
    device_imei  varchar(50) not null,
    owner_name   varchar(10) null,
    owner_number varchar(20) null,
    created_at   datetime    null,
    constraint device_imei
        unique (device_imei)
);

create table users
(
    id         bigint auto_increment
        primary key,
    username   varchar(50)                        not null,
    password   varchar(100)                       not null,
    role       varchar(20)                        not null,
    created_at datetime default CURRENT_TIMESTAMP null,
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint username
        unique (username)
);

