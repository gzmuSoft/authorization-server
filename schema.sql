create database if not exists `gzmu-auth`;

create table if not exists ClientDetails
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

create table if not exists oauth_access_token
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

create table if not exists oauth_approvals
(
    userId         varchar(256) null,
    clientId       varchar(256) null,
    scope          varchar(256) null,
    status         varchar(10)  null,
    expiresAt      timestamp    null,
    lastModifiedAt timestamp    null
);

create table if not exists oauth_client_details
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

create table if not exists oauth_client_token
(
    token_id          varchar(256) null,
    token             blob         null,
    authentication_id varchar(256) not null
        primary key,
    user_name         varchar(256) null,
    client_id         varchar(256) null
);

create table if not exists oauth_code
(
    code           varchar(256) null,
    authentication blob         null
);

create table if not exists oauth_refresh_token
(
    token_id       varchar(256) null,
    token          blob         null,
    authentication blob         null
);

create table if not exists sys_data
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

create table if not exists semester
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

create table if not exists sys_role
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

create table if not exists sys_user
(
    id            bigint auto_increment comment '编号' primary key,
    name          varchar(55)                                null comment '名称',
    spell         varchar(255) default ''                    null comment '名称的全拼',
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


create table if not exists sys_user_role
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


create table if not exists student
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


create table if not exists teacher
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


CREATE  TABLE if not exists `sys_res`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `name`        varchar(255)         DEFAULT NULL,
    `spell`       varchar(255)         DEFAULT NULL,
    `url`         varchar(255)         DEFAULT NULL,
    `describe`    varchar(255)         DEFAULT NULL,
    `method`      varchar(255)         DEFAULT 'GET',
    `sort`        smallint(6) NOT NULL DEFAULT '1',
    `create_user` varchar(255)         DEFAULT NULL,
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP,
    `modify_user` varchar(255)         DEFAULT NULL,
    `modify_time` datetime             DEFAULT CURRENT_TIMESTAMP,
    `remark`      varchar(255)         DEFAULT NULL,
    `is_enable`   tinyint(4)  NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8 COMMENT ='资源表';

CREATE TABLE if not exists `sys_role_res`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) DEFAULT NULL,
    `spell`       varchar(255) DEFAULT NULL,
    `role_id`     bigint(20)   DEFAULT NULL,
    `res_id`      bigint(20)   DEFAULT NULL,
    `sort`        smallint(6)  DEFAULT NULL,
    `create_user` varchar(255) DEFAULT NULL,
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP,
    `modify_user` varchar(255) DEFAULT NULL,
    `modify_time` datetime     DEFAULT CURRENT_TIMESTAMP,
    `remark`      varchar(255) DEFAULT NULL,
    `is_enable`   bit(1)       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `sys_role_res_sys_role_id_fk` (`role_id`),
    KEY `sys_role_res_sys_res_id_fk` (`res_id`),
    CONSTRAINT `sys_role_res_sys_res_id_fk` FOREIGN KEY (`res_id`) REFERENCES `sys_res` (`id`),
    CONSTRAINT `sys_role_res_sys_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 22
  DEFAULT CHARSET = utf8 COMMENT ='角色资源关联';




INSERT INTO `gzmu-auth`.oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES ('lesson-cloud', 'lesson-cloud', '$2a$10$as.fmt5JMJSaIjSW5JKPe.CtSpiwLK7a1dsVAVWlcAOvW/UwpTBSC', 'all', 'password,refresh_token,sms,email,authorization_code', 'http://example.com', null, 600000, 600000, '{"a":"1"}', null);
INSERT INTO `gzmu-auth`.student (id, name, spell, user_id, school_id, college_id, dep_id, specialty_id, classes_id, no, gender, id_number, birthday, enter_date, academic, graduation_date, graduate_institution, original_major, resume, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, '小明', null, 2, 1, 2, 3, 4, 5, '20160202020123', null, null, null, null, null, null, null, null, null, null, null, '2019-08-05 22:46:10', null, '2019-08-07 16:00:41', null, 0x31);
INSERT INTO `gzmu-auth`.student (id, name, spell, user_id, school_id, college_id, dep_id, specialty_id, classes_id, no, gender, id_number, birthday, enter_date, academic, graduation_date, graduate_institution, original_major, resume, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (2, '小花', null, null, 1, 2, 3, 4, 5, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-07 16:40:28', null, '2019-08-07 16:40:28', null, 0x31);
INSERT INTO `gzmu-auth`.sys_data (id, name, spell, parent_id, brief, type, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, '学校测试', null, 0, null, 0, 1, null, '2019-08-07 15:58:37', null, '2019-08-07 15:58:37', null, 0x31);
INSERT INTO `gzmu-auth`.sys_data (id, name, spell, parent_id, brief, type, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (2, '学院测试', null, 1, null, 1, 1, null, '2019-08-07 15:59:35', null, '2019-08-12 00:27:08', null, 0x31);
INSERT INTO `gzmu-auth`.sys_data (id, name, spell, parent_id, brief, type, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (3, '系部测试', null, 2, null, 2, 1, null, '2019-08-07 15:59:35', null, '2019-08-12 00:27:08', null, 0x31);
INSERT INTO `gzmu-auth`.sys_data (id, name, spell, parent_id, brief, type, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (4, '专业测试', null, 3, null, 3, 1, null, '2019-08-07 15:59:35', null, '2019-08-12 00:27:08', null, 0x31);
INSERT INTO `gzmu-auth`.sys_data (id, name, spell, parent_id, brief, type, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (5, '智商学班', null, 4, null, 4, 1, null, '2019-08-07 15:59:35', null, '2019-08-12 00:27:08', null, 0x31);
INSERT INTO `gzmu-auth`.sys_data (id, name, spell, parent_id, brief, type, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (6, '能力学班', null, 4, null, 4, 1, null, '2019-08-07 16:40:58', null, '2019-08-12 00:27:08', null, 0x31);
INSERT INTO `gzmu-auth`.sys_data (id, name, spell, parent_id, brief, type, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (7, '绝望学班', null, 4, null, 4, 1, null, '2019-08-07 16:41:11', null, '2019-08-12 00:27:08', null, 0x31);
INSERT INTO `gzmu-auth`.semester (id, name, spell, school_id, start_date, end_date, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, '小学期1', null, 1, '2019-08-13', '2019-08-29', null, null, '2019-08-07 16:03:26', null, '2019-08-07 16:47:36', null, 0x31);
INSERT INTO `gzmu-auth`.semester (id, name, spell, school_id, start_date, end_date, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (2, '小学期2', null, 1, null, null, null, null, '2019-08-07 16:47:36', null, '2019-08-07 16:47:36', null, 0x31);
INSERT INTO `gzmu-auth`.semester (id, name, spell, school_id, start_date, end_date, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (3, '小学期3', null, 1, null, null, null, null, '2019-08-07 16:47:37', null, '2019-08-07 16:47:37', null, 0x31);
INSERT INTO `gzmu-auth`.semester (id, name, spell, school_id, start_date, end_date, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (4, '小学期4', null, 1, null, null, null, null, '2019-08-07 16:47:37', null, '2019-08-07 16:47:37', null, 0x31);
INSERT INTO `gzmu-auth`.semester (id, name, spell, school_id, start_date, end_date, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (5, '别看了还是小学期', null, 1, null, null, null, null, '2019-08-07 16:47:37', null, '2019-08-07 16:47:37', null, 0x31);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, 'home', null, '/', 'api 主界面', 'ALL', 1, null, '2019-08-06 06:55:21', null, '2019-08-06 06:11:23', null, 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (2, 'Oauth2', null, '/oauth/**', 'oauth2 授权相关', 'ALL', 1, null, '2019-08-06 06:11:23', null, '2019-08-06 06:11:23', null, 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (3, 'Oauth2-提交', null, '/authorization/form', 'oauth2 授权码模式表单提交路径', 'POST', 1, null, '2019-08-06 06:23:57', null, '2019-08-06 06:23:57', null, 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (4, '学生管理', null, '/student/self', '获取自己的学生信息', 'GET', 1, null, '2019-08-06 06:55:21', null, '2019-08-06 06:55:21', null, 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (5, '教师管理', null, '/teacher/self', '获取自己的教师信息', 'GET', 1, null, '2019-08-07 15:14:35', null, '2019-08-07 15:14:35', null, 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (6, '学生管理', null, '/student/one/*', '获取指定id学生信息', 'GET', 1, null, '2019-08-06 06:11:23', null, '2019-08-06 06:11:23', null, 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (7, '数据管理', null, '/sysData/one/*', '获取指定id数据信息', 'GET', 1, null, '2019-08-06 06:55:21', null, '2019-08-06 06:55:21', null, 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (8, '教师管理', null, '/teacher/one/*', '获取指定id教师信息', 'GET', 1, null, '2019-08-06 06:55:21', null, '2019-08-06 06:55:21', null, 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (9, '学期管理', null, '/semester/one/*', '获取指定id学期信息', 'GET', 1, null, '2019-08-07 15:15:49', null, '2019-08-07 15:15:49', null, 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (10, '学生管理', null, '/students', '获取指定多个id学生信息', 'GET', 1, null, '2019-08-06 06:23:57', null, '2019-08-06 06:23:57', 'id 使用数组形式', 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (11, '教师管理', null, '/teachers', '获取指定多个id教师信息', 'GET', 1, null, '2019-08-07 15:09:02', null, '2019-08-07 15:09:02', 'id 使用数组形式', 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (12, '学期管理', null, '/semester', '获取指定多个id学期信息', 'GET', 1, null, '2019-08-07 15:15:49', null, '2019-08-07 15:15:49', 'id 使用数组形式', 1);
INSERT INTO `gzmu-auth`.sys_res (id, name, spell, url, `describe`, method, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (13, '数据管理', null, '/sysData', '获取指定多个id数据信息', 'GET', 1, null, '2019-08-07 15:16:12', null, '2019-08-07 15:16:12', 'id 使用数组形式', 1);
INSERT INTO `gzmu-auth`.sys_role (id, name, spell, des, icon_cls, parent_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, 'ROLE_PUBLIC', null, null, 'status_online', 0, 1, null, '2019-08-06 14:37:01', null, '2019-08-07 15:19:51', null, 0x31);
INSERT INTO `gzmu-auth`.sys_role (id, name, spell, des, icon_cls, parent_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (2, 'ROLE_ADMIN', null, null, 'status_online', 0, 2, null, '2019-06-23 10:48:42', null, '2019-08-07 15:20:27', null, 0x31);
INSERT INTO `gzmu-auth`.sys_role (id, name, spell, des, icon_cls, parent_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (3, 'ROLE_TEACHER', null, null, 'status_online', 0, 3, null, '2019-06-23 10:48:42', null, '2019-08-07 15:20:27', null, 0x31);
INSERT INTO `gzmu-auth`.sys_role (id, name, spell, des, icon_cls, parent_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (4, 'ROLE_STUDENT', null, null, 'status_online', 0, 4, null, '2019-06-23 10:48:42', null, '2019-08-07 15:20:27', null, 0x31);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, null, null, 1, 1, null, null, '2019-08-07 15:21:12', null, '2019-08-07 15:21:12', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (2, null, null, 1, 2, null, null, '2019-08-07 15:21:20', null, '2019-08-07 15:21:20', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (3, null, null, 1, 3, null, null, '2019-08-07 15:21:29', null, '2019-08-07 15:21:29', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (4, null, null, 4, 4, null, null, '2019-08-07 15:21:47', null, '2019-08-07 15:21:47', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (5, null, null, 3, 5, null, null, '2019-08-07 15:21:56', null, '2019-08-07 15:21:56', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (6, null, null, 3, 6, null, null, '2019-08-07 15:22:18', null, '2019-08-07 15:22:18', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (7, null, null, 4, 6, null, null, '2019-08-07 15:22:18', null, '2019-08-07 15:22:18', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (8, null, null, 3, 7, null, null, '2019-08-07 15:22:37', null, '2019-08-07 15:22:37', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (9, null, null, 4, 7, null, null, '2019-08-07 15:22:42', null, '2019-08-07 15:22:42', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (10, null, null, 3, 8, null, null, '2019-08-07 15:22:58', null, '2019-08-07 15:22:58', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (11, null, null, 4, 8, null, null, '2019-08-07 15:23:05', null, '2019-08-07 15:23:05', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (12, null, null, 3, 9, null, null, '2019-08-07 15:24:04', null, '2019-08-07 15:24:04', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (13, null, null, 4, 9, null, null, '2019-08-07 15:24:04', null, '2019-08-07 15:24:04', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (14, null, null, 3, 10, null, null, '2019-08-07 15:24:04', null, '2019-08-07 15:24:04', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (15, null, null, 4, 10, null, null, '2019-08-07 15:24:04', null, '2019-08-07 15:24:04', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (16, null, null, 3, 11, null, null, '2019-08-07 15:24:04', null, '2019-08-07 15:24:04', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (17, null, null, 4, 11, null, null, '2019-08-07 15:24:05', null, '2019-08-07 15:24:05', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (18, null, null, 3, 12, null, null, '2019-08-07 15:24:05', null, '2019-08-07 15:24:05', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (19, null, null, 4, 12, null, null, '2019-08-07 15:24:05', null, '2019-08-07 15:24:05', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (20, null, null, 3, 13, null, null, '2019-08-07 15:24:05', null, '2019-08-07 15:24:05', null, null);
INSERT INTO `gzmu-auth`.sys_role_res (id, name, spell, role_id, res_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (21, null, null, 4, 13, null, null, '2019-08-07 15:24:05', null, '2019-08-07 15:24:05', null, null);
INSERT INTO `gzmu-auth`.sys_user (id, name, spell, pwd, status, icon, email, phone, online_status, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, 'admin', '', '$2a$10$TtwVLF9OMb5/LhZYgC1mAepnQMPoG.rdNWKIrUvn6NlzHvD.jRITW', 1, '图标：images/guest.jpg', 'lizhongyue248@163.com', '13765308262', false, 1, 'admin', '2019-04-20 17:07:50', 'admin', '2019-08-01 12:29:27', null, 1);
INSERT INTO `gzmu-auth`.sys_user (id, name, spell, pwd, status, icon, email, phone, online_status, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (2, 'student', '', '$2a$10$TtwVLF9OMb5/LhZYgC1mAepnQMPoG.rdNWKIrUvn6NlzHvD.jRITW', 1, '图标：images/guest.jpg', 'lizhongyue246@163.com', '13765308261', false, 1, 'admin', '2019-04-20 17:07:50', 'admin', '2019-08-01 12:29:27', null, 1);
INSERT INTO `gzmu-auth`.sys_user (id, name, spell, pwd, status, icon, email, phone, online_status, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (3, 'teacher', '', '$2a$10$TtwVLF9OMb5/LhZYgC1mAepnQMPoG.rdNWKIrUvn6NlzHvD.jRITW', 1, '图标：images/guest.jpg', 'lizhongyue247@163.com', '13765308260', false, 1, 'admin', '2019-04-20 17:07:50', 'admin', '2019-08-01 12:29:27', null, 1);
INSERT INTO `gzmu-auth`.sys_user_role (id, name, spell, user_id, role_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, null, null, 1, 2, null, null, '2019-06-23 10:49:00', null, '2019-08-07 15:21:00', null, 0x31);
INSERT INTO `gzmu-auth`.sys_user_role (id, name, spell, user_id, role_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (2, null, null, 1, 4, null, null, '2019-06-23 10:49:00', null, '2019-08-07 15:21:00', null, 0x31);
INSERT INTO `gzmu-auth`.sys_user_role (id, name, spell, user_id, role_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (3, null, null, 2, 4, null, null, '2019-08-05 22:44:37', null, '2019-08-07 15:21:00', null, 0x31);
INSERT INTO `gzmu-auth`.sys_user_role (id, name, spell, user_id, role_id, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (4, null, null, 3, 3, null, null, '2019-08-05 22:44:43', null, '2019-08-07 15:21:00', null, 0x31);
INSERT INTO `gzmu-auth`.teacher (id, name, spell, user_id, school_id, college_id, dep_id, gender, birthday, nation, degree, academic, graduation_date, major, graduate_institution, major_research, resume, work_date, prof_title, prof_title_ass_date, is_academic_leader, subject_category, id_number, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (1, '老毕', 'teacher', 3, 1, 2, 3, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-01 12:29:21', null, '2019-08-07 16:41:39', null, 0x31);
INSERT INTO `gzmu-auth`.teacher (id, name, spell, user_id, school_id, college_id, dep_id, gender, birthday, nation, degree, academic, graduation_date, major, graduate_institution, major_research, resume, work_date, prof_title, prof_title_ass_date, is_academic_leader, subject_category, id_number, sort, create_user, create_time, modify_user, modify_time, remark, is_enable) VALUES (2, '骚男', null, null, 1, 2, 3, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-07 16:41:39', null, '2019-08-07 16:41:39', null, 0x31);