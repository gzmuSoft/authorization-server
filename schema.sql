DROP TABLE IF EXISTS `oauth_client_details`;
create table oauth_client_details
(
    client_id               VARCHAR(256) PRIMARY KEY,
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(256)
);

DROP TABLE IF EXISTS `oauth_client_token`;
create table oauth_client_token
(
    token_id          VARCHAR(256),
    token             BLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
);

DROP TABLE IF EXISTS `oauth_access_token`;
create table oauth_access_token
(
    token_id          VARCHAR(256),
    token             BLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    BLOB,
    refresh_token     VARCHAR(256)
);

DROP TABLE IF EXISTS `oauth_refresh_token`;
create table oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          BLOB,
    authentication BLOB
);

DROP TABLE IF EXISTS `oauth_code`;
create table oauth_code
(
    code           VARCHAR(256),
    authentication BLOB
);

DROP TABLE IF EXISTS `oauth_approvals`;
create table oauth_approvals
(
    userId         VARCHAR(256),
    clientId       VARCHAR(256),
    scope          VARCHAR(256),
    status         VARCHAR(10),
    expiresAt      TIMESTAMP,
    lastModifiedAt TIMESTAMP
);

DROP TABLE IF EXISTS `ClientDetails`;
create table ClientDetails
(
    appId                  VARCHAR(256) PRIMARY KEY,
    resourceIds            VARCHAR(256),
    appSecret              VARCHAR(256),
    scope                  VARCHAR(256),
    grantTypes             VARCHAR(256),
    redirectUrl            VARCHAR(256),
    authorities            VARCHAR(256),
    access_token_validity  INTEGER,
    refresh_token_validity INTEGER,
    additionalInformation  VARCHAR(4096),
    autoApproveScopes      VARCHAR(256)
);

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`                 varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '名称',
    `spell`                varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '名称的全拼',
    `user_id`              bigint(20)                                               DEFAULT NULL COMMENT '用户编号',
    `school_id`            bigint(20)                                               DEFAULT NULL COMMENT '学校编号',
    `college_id`           bigint(20)                                               DEFAULT NULL COMMENT '学院编号',
    `dep_id`               bigint(20)                                               DEFAULT NULL COMMENT '系部编号',
    `specialty_id`         bigint(20)                                               DEFAULT NULL COMMENT '专业编号',
    `classes_id`           bigint(20)                                               DEFAULT NULL COMMENT '班级编号',
    `no`                   varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci   DEFAULT NULL COMMENT '学号',
    `gender`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '性别',
    `id_number`            varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci   DEFAULT NULL COMMENT '身份证号码',
    `birthday`             date                                                     DEFAULT NULL COMMENT '出生日期',
    `enter_date`           date                                                     DEFAULT NULL COMMENT '入校时间',
    `academic`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '最后学历',
    `graduation_date`      date                                                     DEFAULT NULL COMMENT '最后学历毕业时间',
    `graduate_institution` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '最后学历毕业院校',
    `original_major`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '最后学历所学专业（若最后学历是高中，则不需要填写\r\n若最后学历是大专，则需要填写）',
    `resume`               varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '个人简历',
    `sort`                 smallint(6)                                              DEFAULT NULL COMMENT '排序',
    `create_user`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '创建用户名称',
    `create_time`          datetime                                                 DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `modify_user`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '末次更新用户名称',
    `modify_time`          datetime                                                 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '末次更新时间',
    `remark`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '备注',
    `is_enable`            binary(1)  NOT NULL                                      DEFAULT '1' COMMENT '是否可用，1：可用，0：不可用',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='学生信息表';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
    `spell`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '名称的全拼',
    `des`         varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '描述',
    `icon_cls`    varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT 'status_online' COMMENT '图标',
    `parent_id`   bigint(20)                                              NOT NULL DEFAULT '0' COMMENT '父角色编号',
    `sort`        smallint(6)                                                      DEFAULT NULL COMMENT '排序',
    `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户名称',
    `create_time` datetime                                                         DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `modify_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '末次更新用户名称',
    `modify_time` datetime                                                         DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '末次更新时间',
    `remark`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '备注',
    `is_enable`   binary(1)                                               NOT NULL DEFAULT '1' COMMENT '是否可用，1：可用，0：不可用',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='系统角色表';

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`          varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '名称',
    `spell`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '名称的全拼',
    `entity_id`     bigint(20)                                              DEFAULT NULL COMMENT '用户主体编号',
    `entity_type`   int(4)                                                  DEFAULT NULL COMMENT '0：系统管理员、1：教务管理员、2：课程管理员、3：教师、4：学生',
    `pwd`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
    `status`        int(2)     NOT NULL                                     DEFAULT '1' COMMENT '1：正常、2：锁定一小时、3：禁用',
    `icon`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '图标：images/guest.jpg' COMMENT '图标',
    `email`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电子邮箱',
    `phone`         varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '联系电话',
    `online_status` bit(1)                                                  DEFAULT b'0' COMMENT '在线状态  1-在线 0-离线',
    `sort`          smallint(6)                                             DEFAULT NULL COMMENT '排序',
    `create_user`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户名称',
    `create_time`   datetime                                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `modify_user`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '末次更新用户名称',
    `modify_time`   datetime                                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '末次更新时间',
    `remark`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
    `is_enable`     tinyint(1) NOT NULL                                     DEFAULT '1' COMMENT '是否可用，1：可用，0：不可用',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `sys_user_email_uindex` (`email`) USING BTREE,
    UNIQUE KEY `sys_user_name_uindex` (`name`) USING BTREE,
    UNIQUE KEY `sys_user_phone_uindex` (`phone`) USING BTREE,
    KEY `entity_id` (`entity_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='系统用户表';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`        varchar(254) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
    `spell`       varchar(254) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称的全拼',
    `user_id`     bigint(20) NOT NULL COMMENT '用户编号',
    `role_id`     bigint(20) NOT NULL COMMENT '角色编号',
    `sort`        smallint(6)                                             DEFAULT NULL COMMENT '排序',
    `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户名称',
    `create_time` datetime                                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `modify_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '末次更新用户名称',
    `modify_time` datetime                                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '末次更新时间',
    `remark`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
    `is_enable`   binary(1)  NOT NULL                                     DEFAULT '1' COMMENT '是否可用，1：可用，0：不可用',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `FK_ur_role` (`role_id`) USING BTREE,
    KEY `FK_ur_user` (`user_id`) USING BTREE,
    CONSTRAINT `FK_ur_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `FK_ur_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='用户角色关联';

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`                 varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '名称',
    `spell`                varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '名称的全拼',
    `user_id`              bigint(20)                                               DEFAULT NULL COMMENT '用户编号',
    `school_id`            bigint(20)                                               DEFAULT NULL COMMENT '学校编号',
    `college_id`           bigint(20)                                               DEFAULT NULL COMMENT '学院编号',
    `dep_id`               bigint(20)                                               DEFAULT NULL COMMENT '系部编号',
    `gender`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '性别',
    `birthday`             date                                                     DEFAULT NULL COMMENT '出生日期',
    `nation`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '民族',
    `degree`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '学位',
    `academic`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '最后学历',
    `graduation_date`      date                                                     DEFAULT NULL COMMENT '最后学历毕业时间',
    `major`                varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '最后学历所学专业',
    `graduate_institution` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '最后学历毕业院校',
    `major_research`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '主要研究方向',
    `resume`               varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '个人简历',
    `work_date`            date                                                     DEFAULT NULL COMMENT '参加工作时间',
    `prof_title`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '职称',
    `prof_title_ass_date`  date                                                     DEFAULT NULL COMMENT '职称评定时间',
    `is_academic_leader`   binary(1)                                                DEFAULT NULL COMMENT '是否学术学科带头人',
    `subject_category`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '所属学科门类',
    `id_number`            varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci   DEFAULT NULL COMMENT '身份证号码',
    `sort`                 smallint(6)                                              DEFAULT NULL COMMENT '排序',
    `create_user`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '创建用户名称',
    `create_time`          datetime                                                 DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `modify_user`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '末次更新用户名称',
    `modify_time`          datetime                                                 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '末次更新时间',
    `remark`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '备注',
    `is_enable`            binary(1)  NOT NULL                                      DEFAULT '1' COMMENT '是否可用，1：可用，0：不可用',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='教师信息表';

DROP TABLE IF EXISTS `sys_data`;
CREATE TABLE `sys_data`
(
    `id`          bigint(20)                                               NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '名称',
    `spell`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '名称的全拼',
    `parent_id`   bigint(20)                                               NULL     DEFAULT 0 COMMENT '0，代表无上级，即：学校',
    `brief`       varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '简介',
    `type`        tinyint(4)                                               NULL     DEFAULT 0 COMMENT '0：学校，1：学院，2：系部，3：专业，4：班级，5：性别，6：学历，7：学位，8：教师毕业专业，9：民族，10：研究方向，11：职称',
    `sort`        smallint(6)                                              NULL     DEFAULT 1 COMMENT '同一type数据（如：学校）的排序顺序，值大于等于1',
    `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '创建用户名称',
    `create_time` datetime(0)                                              NULL     DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建日期',
    `modify_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '末次更新用户名称',
    `modify_time` datetime(0)                                              NULL     DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '末次更新时间',
    `remark`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '备注',
    `is_enable`   binary(1)                                                NOT NULL DEFAULT 1 COMMENT '是否可用，1：可用，0：不可用',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '系统基本数据表'
  ROW_FORMAT = Dynamic;