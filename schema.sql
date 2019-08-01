create table ClientDetails
(
    appId                  varchar(256)  not null primary key,
    resourceIds            varchar(256)  null,
    appSecret              varchar(256)  null,
    scope                  varchar(256)  null,
    grantTypes             varchar(256)  null,
    redirectUrl            varchar(256)  null,
    authorities            varchar(256)  null,
    access_token_validity  int           null,
    refresh_token_validity int           null,
    additionalInformation  varchar(4096) null,
    autoApproveScopes      varchar(256)  null
);

create table oauth_access_token
(
    token_id          varchar(256) null,
    token             blob         null,
    authentication_id varchar(256) not null
        primary key,
    user_name         varchar(256) null,
    client_id         varchar(256) null,
    authentication    blob         null,
    refresh_token     varchar(256) null
);

create table oauth_approvals
(
    userId         varchar(256) null,
    clientId       varchar(256) null,
    scope          varchar(256) null,
    status         varchar(10)  null,
    expiresAt      timestamp    null,
    lastModifiedAt timestamp    null
);

create table oauth_client_details
(
    client_id               varchar(256)  not null primary key,
    resource_ids            varchar(256)  null,
    client_secret           varchar(256)  null,
    scope                   varchar(256)  null,
    authorized_grant_types  varchar(256)  null,
    web_server_redirect_uri varchar(256)  null,
    authorities             varchar(256)  null,
    access_token_validity   int           null,
    refresh_token_validity  int           null,
    additional_information  varchar(4096) null,
    autoapprove             varchar(256)  null
);

create table oauth_client_token
(
    token_id          varchar(256) null,
    token             blob         null,
    authentication_id varchar(256) not null
        primary key,
    user_name         varchar(256) null,
    client_id         varchar(256) null
);

create table oauth_code
(
    code           varchar(256) null,
    authentication blob         null
);

create table oauth_refresh_token
(
    token_id       varchar(256) null,
    token          blob         null,
    authentication blob         null
);

create table student
(
    id                   bigint auto_increment comment '编号' primary key,
    name                 varchar(255)                        null comment '名称',
    spell                varchar(255)                        null comment '名称的全拼',
    user_id              bigint                              null comment '用户编号',
    school_id            bigint                              null comment '学校编号',
    college_id           bigint                              null comment '学院编号',
    dep_id               bigint                              null comment '系部编号',
    specialty_id         bigint                              null comment '专业编号',
    classes_id           bigint                              null comment '班级编号',
    no                   varchar(20)                         null comment '学号',
    gender               varchar(255)                        null comment '性别',
    id_number            varchar(18)                         null comment '身份证号码',
    birthday             date                                null comment '出生日期',
    enter_date           date                                null comment '入校时间',
    academic             varchar(255)                        null comment '最后学历',
    graduation_date      date                                null comment '最后学历毕业时间',
    graduate_institution varchar(255)                        null comment '最后学历毕业院校',
    original_major       varchar(255)                        null comment '最后学历所学专业（若最后学历是高中，则不需要填写
若最后学历是大专，则需要填写）',
    resume               varchar(2048)                       null comment '个人简历',
    sort                 smallint(6)                         null comment '排序',
    create_user          varchar(255)                        null comment '创建用户名称',
    create_time          datetime  default CURRENT_TIMESTAMP null comment '创建日期',
    modify_user          varchar(255)                        null comment '末次更新用户名称',
    modify_time          datetime  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '末次更新时间',
    remark               varchar(255)                        null comment '备注',
    is_enable            binary(1) default 1                 not null comment '是否可用，1：可用，0：不可用'
)
    comment '学生信息表';

create table sys_data
(
    id          bigint auto_increment comment '编号' primary key,
    name        varchar(50)                           not null comment '名称',
    spell       varchar(255)                          null comment '名称的全拼',
    parent_id   bigint      default 0                 null comment '0，代表无上级，即：学校',
    brief       varchar(2048)                         null comment '简介',
    type        tinyint     default 0                 null comment '0：学校，1：学院，2：系部，3：专业，4：班级，5：性别，6：学历，7：学位，8：教师毕业专业，9：民族，10：研究方向，11：职称',
    sort        smallint(6) default 1                 null comment '同一type数据（如：学校）的排序顺序，值大于等于1',
    create_user varchar(255)                          null comment '创建用户名称',
    create_time datetime    default CURRENT_TIMESTAMP null comment '创建日期',
    modify_user varchar(255)                          null comment '末次更新用户名称',
    modify_time datetime    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '末次更新时间',
    remark      varchar(255)                          null comment '备注',
    is_enable   binary(1)   default 1                 not null comment '是否可用，1：可用，0：不可用'
)
    comment '系统基本数据表';

create table semester
(
    id          bigint auto_increment comment '编号' primary key,
    name        varchar(255)                        null comment '名称',
    spell       varchar(255)                        null comment '名称的全拼',
    school_id   bigint                              not null comment '学校编号',
    start_date  date                                null comment '起始日期',
    end_date    date                                null comment '结束日期',
    sort        smallint(6)                         null comment '排序',
    create_user varchar(255)                        null comment '创建用户名称',
    create_time datetime  default CURRENT_TIMESTAMP null comment '创建日期',
    modify_user varchar(255)                        null comment '末次更新用户名称',
    modify_time datetime  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '末次更新时间',
    remark      varchar(255)                        null comment '备注',
    is_enable   binary(1) default 1                 not null comment '是否可用，1：可用，0：不可用',
    constraint semester_school_ibfk
        foreign key (school_id) references sys_data (id)
            on update cascade on delete cascade
)
    comment '学期';

create index FK_school
    on semester (school_id);

create table sys_role
(
    id          bigint auto_increment comment '编号' primary key,
    name        varchar(255)                          not null comment '名称',
    spell       varchar(255)                          null comment '名称的全拼',
    des         varchar(128)                          null comment '描述',
    icon_cls    varchar(55) default 'status_online'   null comment '图标',
    parent_id   bigint      default 0                 not null comment '父角色编号',
    sort        smallint(6)                           null comment '排序',
    create_user varchar(255)                          null comment '创建用户名称',
    create_time datetime    default CURRENT_TIMESTAMP null comment '创建日期',
    modify_user varchar(255)                          null comment '末次更新用户名称',
    modify_time datetime    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '末次更新时间',
    remark      varchar(255)                          null comment '备注',
    is_enable   binary(1)   default 1                 not null comment '是否可用，1：可用，0：不可用'
)
    comment '系统角色表';

create table sys_user
(
    id            bigint auto_increment comment '编号' primary key,
    name          varchar(55)                                null comment '名称',
    spell         varchar(255) default ''                    null comment '名称的全拼',
    entity_id     bigint                                     null comment '用户主体编号',
    entity_type   int(4)                                     null comment '1：系统管理员、2：教师、3：学生、待添加：系部管理员',
    pwd           varchar(255)                               null comment '密码',
    status        int(2)       default 1                     not null comment '1：正常、2：锁定一小时、3：禁用',
    icon          varchar(255) default '图标：images/guest.jpg' null comment '图标',
    email         varchar(255)                               null comment '电子邮箱',
    phone         varchar(20)                                null comment '联系电话',
    online_status bit          default b'0'                  null comment '在线状态  1-在线 0-离线',
    sort          smallint(6)                                null comment '排序',
    create_user   varchar(255)                               null comment '创建用户名称',
    create_time   datetime     default CURRENT_TIMESTAMP     null comment '创建日期',
    modify_user   varchar(255)                               null comment '末次更新用户名称',
    modify_time   datetime     default CURRENT_TIMESTAMP     null on update CURRENT_TIMESTAMP comment '末次更新时间',
    remark        varchar(255)                               null comment '备注',
    is_enable     tinyint(1)   default 1                     not null comment '是否可用，1：可用，0：不可用',
    constraint sys_user_email_uindex
        unique (email),
    constraint sys_user_name_uindex
        unique (name),
    constraint sys_user_phone_uindex
        unique (phone)
)
    comment '系统用户表';

create index entity_id
    on sys_user (entity_id);

create table sys_user_role
(
    id          bigint auto_increment comment '编号' primary key,
    name        varchar(254)                        null comment '名称',
    spell       varchar(254)                        null comment '名称的全拼',
    user_id     bigint                              not null comment '用户编号',
    role_id     bigint                              not null comment '角色编号',
    sort        smallint(6)                         null comment '排序',
    create_user varchar(255)                        null comment '创建用户名称',
    create_time datetime  default CURRENT_TIMESTAMP null comment '创建日期',
    modify_user varchar(255)                        null comment '末次更新用户名称',
    modify_time datetime  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '末次更新时间',
    remark      varchar(255)                        null comment '备注',
    is_enable   binary(1) default 1                 not null comment '是否可用，1：可用，0：不可用',
    constraint FK_ur_role
        foreign key (role_id) references sys_role (id),
    constraint FK_ur_user
        foreign key (user_id) references sys_user (id)
)
    comment '用户角色关联';

create table teacher
(
    id                   bigint auto_increment comment '编号' primary key,
    name                 varchar(255)                        null comment '名称',
    spell                varchar(255)                        null comment '名称的全拼',
    user_id              bigint                              null comment '用户编号',
    school_id            bigint                              null comment '学校编号',
    college_id           bigint                              null comment '学院编号',
    dep_id               bigint                              null comment '系部编号',
    gender               varchar(255)                        null comment '性别',
    birthday             date                                null comment '出生日期',
    nation               varchar(255)                        null comment '民族',
    degree               varchar(255)                        null comment '学位',
    academic             varchar(255)                        null comment '最后学历',
    graduation_date      date                                null comment '最后学历毕业时间',
    major                varchar(255)                        null comment '最后学历所学专业',
    graduate_institution varchar(255)                        null comment '最后学历毕业院校',
    major_research       varchar(255)                        null comment '主要研究方向',
    resume               varchar(2048)                       null comment '个人简历',
    work_date            date                                null comment '参加工作时间',
    prof_title           varchar(255)                        null comment '职称',
    prof_title_ass_date  date                                null comment '职称评定时间',
    is_academic_leader   binary(1)                           null comment '是否学术学科带头人',
    subject_category     varchar(255)                        null comment '所属学科门类',
    id_number            varchar(18)                         null comment '身份证号码',
    sort                 smallint(6)                         null comment '排序',
    create_user          varchar(255)                        null comment '创建用户名称',
    create_time          datetime  default CURRENT_TIMESTAMP null comment '创建日期',
    modify_user          varchar(255)                        null comment '末次更新用户名称',
    modify_time          datetime  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '末次更新时间',
    remark               varchar(255)                        null comment '备注',
    is_enable            binary(1) default 1                 not null comment '是否可用，1：可用，0：不可用'
)
    comment '教师信息表';

INSERT INTO `gzmu-auth`.sys_user (id, name, spell, entity_id, entity_type, pwd, status, icon, email, phone, online_status, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, 'admin', '', 1, 2, '$2a$10$TtwVLF9OMb5/LhZYgC1mAepnQMPoG.rdNWKIrUvn6NlzHvD.jRITW', 1, '图标：images/guest.jpg', 'lizhongyue248@163.com', '13765308262', false, 1, 'admin', '2019-04-20 17:07:50', 'admin', '2019-08-01 12:29:27', null, 1);
INSERT INTO `gzmu-auth`.sys_role (id, name, spell, des, icon_cls, parent_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, 'ROLE_ADMIN', null, null, 'status_online', 0, null, null, '2019-06-23 10:48:42', null, '2019-06-23 10:48:49', null, 0x31);
INSERT INTO `gzmu-auth`.sys_role (id, name, spell, des, icon_cls, parent_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (2, 'ROLE_TEACHER', null, null, 'status_online', 0, null, null, '2019-06-23 10:48:42', null, '2019-06-23 10:48:49', null, 0x31);
INSERT INTO `gzmu-auth`.sys_role (id, name, spell, des, icon_cls, parent_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (3, 'ROLE_STUDENT', null, null, 'status_online', 0, null, null, '2019-06-23 10:48:42', null, '2019-06-23 10:48:49', null, 0x31);
INSERT INTO `gzmu-auth`.teacher (id, name, spell, user_id, school_id, college_id, dep_id, gender, birthday, nation, degree, academic, graduation_date, major, graduate_institution, major_research, resume, work_date, prof_title, prof_title_ass_date, is_academic_leader, subject_category, id_number, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, '教师', 'teacher', 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-01 12:29:21', null, '2019-08-01 12:32:35', null, 0x31);
INSERT INTO `gzmu-auth`.oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES ('lesson-cloud', 'lesson-cloud', '$2a$10$as.fmt5JMJSaIjSW5JKPe.CtSpiwLK7a1dsVAVWlcAOvW/UwpTBSC', 'all', 'password,refresh_token,sms,email', null, null, 60000, 60000, '{"a":"1"}', null);
INSERT INTO `gzmu-auth`.sys_user_role (id, name, spell, user_id, role_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, null, null, 1, 1, null, null, '2019-06-23 10:49:00', null, '2019-07-31 16:27:42', null, 0x31);
INSERT INTO `gzmu-auth`.sys_user_role (id, name, spell, user_id, role_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (2, null, null, 1, 2, null, null, '2019-06-23 10:49:00', null, '2019-07-31 16:27:44', null, 0x31);
